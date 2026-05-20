import { createContext, useContext, useState, useEffect } from 'react'

const CartContext = createContext(null)
const API = 'http://localhost:5050'

export function CartProvider({ children }) {
  const [items, setItems] = useState([])
  const [loading, setLoading] = useState(false)

  const fetchCart = async (token) => {
    if (!token) { setItems([]); return }
    try {
      const res = await fetch(`${API}/cart`, { headers: { Authorization: `Bearer ${token}` } })
      if (res.ok) {
        const data = await res.json()
        setItems(data.map(item => ({
          cartItemId: item.cartItemId,
          productId: item.product.productId,
          name: item.product.name,
          price: item.product.price,
          imageUrl: item.product.imageUrl,
          category: item.product.category,
          qty: item.quantity,
          itemTotal: item.itemTotal
        })))
      }
    } catch (e) { console.error(e) }
  }

  useEffect(() => {
    const token = localStorage.getItem('token')
    if (token) fetchCart(token)
  }, [])

  const addToCart = async (product) => {
    const token = localStorage.getItem('token')
    if (!token) { alert('Please login to add items to cart'); return }
    setLoading(true)
    try {
      await fetch(`${API}/cart/add/${product.productId}?quantity=1`, {
        method: 'POST', headers: { Authorization: `Bearer ${token}` }
      })
      await fetchCart(token)
    } catch (e) { console.error(e) }
    setLoading(false)
  }

  const removeFromCart = async (cartItemId) => {
    const token = localStorage.getItem('token')
    try {
      await fetch(`${API}/cart/${cartItemId}`, {
        method: 'DELETE', headers: { Authorization: `Bearer ${token}` }
      })
      await fetchCart(token)
    } catch (e) { console.error(e) }
  }

  const updateQty = async (cartItemId, qty) => {
    const token = localStorage.getItem('token')
    if (qty <= 0) { removeFromCart(cartItemId); return }
    try {
      await fetch(`${API}/cart/update/${cartItemId}?quantity=${qty}`, {
        method: 'POST', headers: { Authorization: `Bearer ${token}` }
      })
      await fetchCart(token)
    } catch (e) { console.error(e) }
  }

  const clearCart = () => setItems([])
  const total = items.reduce((s, i) => s + i.price * i.qty, 0)
  const count = items.reduce((s, i) => s + i.qty, 0)

  return (
    <CartContext.Provider value={{ items, addToCart, removeFromCart, updateQty, clearCart, fetchCart, total, count, loading }}>
      {children}
    </CartContext.Provider>
  )
}

export const useCart = () => useContext(CartContext)
