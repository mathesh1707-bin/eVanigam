import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import './Orders.css'

const API = 'http://localhost:5050'

const STATUS_COLOR = {
  PLACED: { bg: '#fff3cd', color: '#856404' },
  CONFIRMED: { bg: '#d1ecf1', color: '#0c5460' },
  SHIPPED: { bg: '#d4edda', color: '#155724' },
  DELIVERED: { bg: '#c3e6cb', color: '#155724' },
  CANCELLED: { bg: '#f8d7da', color: '#721c24' },
}

export default function Orders() {
  const [orders, setOrders] = useState([])
  const [loading, setLoading] = useState(true)
  const { token } = useAuth()

  useEffect(() => {
    if (!token) return
    fetch(`${API}/orders`, { headers: { Authorization: `Bearer ${token}` } })
      .then(r => r.json())
      .then(data => { setOrders(data); setLoading(false) })
      .catch(() => setLoading(false))
  }, [token])

  if (!token) return (
    <main className="page orders-page">
      <div className="container orders-empty">
        <p>Please <Link to="/login" style={{color:'var(--red)'}}>login</Link> to view your orders.</p>
      </div>
    </main>
  )

  return (
    <main className="page orders-page">
      <div className="orders-hero"><div className="container"><h1 className="orders-title">My Orders</h1></div></div>
      <div className="container orders-body">
        {loading ? (
          <div style={{display:'flex',flexDirection:'column',gap:'1rem'}}>
            {[1,2,3].map(i => <div key={i} className="order-skeleton" />)}
          </div>
        ) : orders.length === 0 ? (
          <div className="orders-empty">
            <p>No orders yet.</p>
            <Link to="/products" className="btn-primary">Start Shopping</Link>
          </div>
        ) : (
          <div className="orders-list">
            {orders.map(order => {
              const s = STATUS_COLOR[order.status] || STATUS_COLOR.PLACED
              return (
                <div key={order.orderId} className="order-card">
                  <div className="order-card-head">
                    <div>
                      <span className="order-id">Order #{order.orderId}</span>
                      <span className="order-date">{new Date(order.orderDate).toLocaleDateString('en-IN', {day:'numeric',month:'short',year:'numeric'})}</span>
                    </div>
                    <span className="order-status" style={{background:s.bg, color:s.color}}>{order.status}</span>
                  </div>
                  <div className="order-items-list">
                    {order.orderItems?.map(item => (
                      <div key={item.orderItemId} className="order-item-row">
                        <img src={item.product?.imageUrl || `https://picsum.photos/seed/${item.product?.productId}/60/60`} alt={item.product?.name} className="order-item-img" />
                        <div className="order-item-info">
                          <p className="order-item-name">{item.product?.name}</p>
                          <p className="order-item-meta">Qty: {item.quantity} · ₹{item.priceAtPurchase?.toLocaleString('en-IN')} each</p>
                        </div>
                        <p className="order-item-total">₹{item.itemTotal?.toLocaleString('en-IN')}</p>
                      </div>
                    ))}
                  </div>
                  <div className="order-card-foot">
                    <span className="order-total-label">Total</span>
                    <span className="order-total-val">₹{order.totalAmount?.toLocaleString('en-IN')}</span>
                  </div>
                </div>
              )
            })}
          </div>
        )}
      </div>
    </main>
  )
}
