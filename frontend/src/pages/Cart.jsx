import { Link, useNavigate } from 'react-router-dom'
import { useCart } from '../context/CartContext'
import { useAuth } from '../context/AuthContext'
import './Cart.css'

const API = 'http://localhost:5050'

export default function Cart() {
  const { items, removeFromCart, updateQty, total, clearCart } = useCart()
  const { token } = useAuth()
  const navigate = useNavigate()

  const handleCheckout = async () => {
    if (!token) { navigate('/login'); return }
    try {
      const res = await fetch(`${API}/orders/place`, {
        method: 'POST', headers: { Authorization: `Bearer ${token}` }
      })
      if (res.ok) { clearCart(); navigate('/orders') }
      else { const d = await res.json(); alert(d.message || 'Checkout failed') }
    } catch (e) { alert('Checkout failed') }
  }

  if (items.length === 0) return (
    <main className="page cart-page">
      <div className="container">
        <h1 className="cart-title">Your Cart</h1>
        <div className="cart-empty">
          <div className="empty-icon">
            <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="var(--mint-mid)" strokeWidth="1">
              <path d="M6 2L3 6v14a2 2 0 002 2h14a2 2 0 002-2V6l-3-4z"/><line x1="3" y1="6" x2="21" y2="6"/><path d="M16 10a4 4 0 01-8 0"/>
            </svg>
          </div>
          <p>Your cart is empty</p>
          <Link to="/products" className="btn-primary">Continue Shopping</Link>
        </div>
      </div>
    </main>
  )

  return (
    <main className="page cart-page">
      <div className="container">
        <div className="cart-head">
          <h1 className="cart-title">Your Cart <span>({items.length} {items.length === 1 ? 'item' : 'items'})</span></h1>
          <button className="clear-btn" onClick={clearCart}>Clear all</button>
        </div>
        <div className="cart-layout">
          <div className="cart-items">
            {items.map(item => (
              <div key={item.cartItemId} className="cart-item">
                <img src={item.imageUrl || `https://picsum.photos/seed/${item.productId}/80/80`} alt={item.name} className="cart-item-img" />
                <div className="cart-item-info">
                  <h3 className="cart-item-name">{item.name}</h3>
                  <span className="cart-item-cat">{item.category}</span>
                  <p className="cart-item-price">₹{item.price?.toLocaleString('en-IN')}</p>
                </div>
                <div className="cart-item-actions">
                  <div className="qty-ctrl">
                    <button onClick={() => updateQty(item.cartItemId, item.qty - 1)}>−</button>
                    <span>{item.qty}</span>
                    <button onClick={() => updateQty(item.cartItemId, item.qty + 1)}>+</button>
                  </div>
                  <p className="cart-subtotal">₹{(item.price * item.qty).toLocaleString('en-IN')}</p>
                  <button className="remove-btn" onClick={() => removeFromCart(item.cartItemId)}>
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                      <polyline points="3,6 5,6 21,6"/><path d="M19,6l-1,14a2,2,0,0,1-2,2H8a2,2,0,0,1-2-2L5,6"/>
                      <path d="M10,11v6M14,11v6M9,6V4h6v2"/>
                    </svg>
                  </button>
                </div>
              </div>
            ))}
          </div>
          <div className="cart-summary">
            <h2 className="summary-title">Order Summary</h2>
            <div className="summary-row"><span>Subtotal</span><span>₹{total.toLocaleString('en-IN')}</span></div>
            <div className="summary-row"><span>Shipping</span><span className="free-tag">Free</span></div>
            <div className="summary-row total-row"><span>Total</span><span>₹{total.toLocaleString('en-IN')}</span></div>
            <button className="btn-primary checkout-btn" onClick={handleCheckout}>Place Order</button>
            <Link to="/products" className="continue-link">← Continue Shopping</Link>
          </div>
        </div>
      </div>
    </main>
  )
}
