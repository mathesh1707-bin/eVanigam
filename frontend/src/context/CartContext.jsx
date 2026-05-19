import { createContext, useContext, useState, useEffect } from 'react'

const CartContext = createContext(null)
const API = 'http://localhost:5050'

export function CartProvider({ children }) {
  const [items, setItems] = useState([])

  // Fetch cart from backend on load
  const fetchCart = async (token) => {
    if (!token) { setItems([]); return }
    try {
      const res = await fetch(`${API}/cart`, {
        headers: { Authorization: `Bearer ${token}` }
      })
      if (res.ok) {
        const data = await res.json()
        // Backend returns CartItemDTOs — map to local format
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
    } catch (err) {
      console.error('Failed to fetch cart', err)
    }
  }

  // Load cart on mount if token exists
  useEffect(() => {
    const token = localStorage.getItem('token')
    if (token) fetchCart(token)
  }, [])

  const addToCart = async (product) => {
    const token = localStorage.getItem('token')
    if (!token) { alert('Please login to add items to cart'); return }
    try {
      await fetch(`${API}/cart/add/${product.productId}?quantity=1`, {
        method: 'POST',
        headers: { Authorization: `Bearer ${token}` }
      })
      fetchCart(token)  // refresh from backend
    } catch (err) {
      console.error('Failed to add to cart', err)
    }
  }

  const removeFromCart = async (cartItemId) => {
    const token = localStorage.getItem('token')
    try {
      await fetch(`${API}/cart/${cartItemId}`, {
        method: 'DELETE',
        headers: { Authorization: `Bearer ${token}` }
      })
      fetchCart(token)
    } catch (err) {
      console.error('Failed to remove from cart', err)
    }
  }

  const updateQty = async (cartItemId, qty) => {
    const token = localStorage.getItem('token')
    if (qty <= 0) { removeFromCart(cartItemId); return }
    try {
      await fetch(`${API}/cart/update/${cartItemId}?quantity=${qty}`, {
        method: 'POST',
        headers: { Authorization: `Bearer ${token}` }
      })
      fetchCart(token)
    } catch (err) {
      console.error('Failed to update qty', err)
    }
  }

  const clearCart = () => setItems([])

  const total = items.reduce((sum, i) => sum + i.price * i.qty, 0)
  const count = items.reduce((sum, i) => sum + i.qty, 0)

  return (
    <CartContext.Provider value={{ items, addToCart, removeFromCart, updateQty, clearCart, fetchCart, total, count }}>
      {children}
    </CartContext.Provider>
  )
}

export const useCart = () => useContext(CartContext)