import { useState, useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import { useNavigate } from 'react-router-dom'
import './AdminDashboard.css'

const API = 'http://localhost:5050'
const EMPTY = { name: '', description: '', price: '', imageUrl: '', stock: '', category: '' }
const CATS = ['Electronics', 'Fashion', 'Home & Kitchen', 'Sports', 'Books']
const STATUS_OPTS = ['PLACED', 'CONFIRMED', 'SHIPPED', 'DELIVERED', 'CANCELLED']

export default function AdminDashboard() {
  const { token, isAdmin } = useAuth()
  const navigate = useNavigate()
  const [tab, setTab] = useState('products')
  const [products, setProducts] = useState([])
  const [orders, setOrders] = useState([])
  const [stats, setStats] = useState(null)
  const [form, setForm] = useState(EMPTY)
  const [editId, setEditId] = useState(null)
  const [showForm, setShowForm] = useState(false)
  const [saving, setSaving] = useState(false)

  useEffect(() => { if (!isAdmin) navigate('/') }, [isAdmin])

  const headers = { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' }

  useEffect(() => {
    fetch(`${API}/products`).then(r => r.json()).then(setProducts).catch(() => {})
    fetch(`${API}/admin/orders`, { headers }).then(r => r.json()).then(setOrders).catch(() => {})
    fetch(`${API}/admin/stats`, { headers }).then(r => r.json()).then(setStats).catch(() => {})
  }, [])

  const saveProduct = async (e) => {
    e.preventDefault(); setSaving(true)
    const url = editId ? `${API}/products/${editId}` : `${API}/products`
    const method = editId ? 'PUT' : 'POST'
    await fetch(url, { method, headers, body: JSON.stringify({ ...form, price: Number(form.price), stock: Number(form.stock) }) })
    const data = await fetch(`${API}/products`).then(r => r.json())
    setProducts(data); setForm(EMPTY); setEditId(null); setShowForm(false); setSaving(false)
  }

  const deleteProduct = async (id) => {
    if (!confirm('Delete this product?')) return
    const res = await fetch(`${API}/products/${id}`, { method: 'DELETE', headers })
    if (res.ok) setProducts(products.filter(p => p.productId !== id))
    else { const d = await res.json(); alert(d.message) }
  }

  const updateStatus = async (orderId, status) => {
    await fetch(`${API}/admin/orders/${orderId}/status?status=${status}`, { method: 'PUT', headers })
    setOrders(orders.map(o => o.orderId === orderId ? { ...o, status } : o))
  }

  const startEdit = (p) => {
    setForm({ name: p.name, description: p.description, price: p.price, imageUrl: p.imageUrl || '', stock: p.stock, category: p.category })
    setEditId(p.productId); setShowForm(true); window.scrollTo({ top: 0, behavior: 'smooth' })
  }

  return (
    <main className="page admin-page">
      <div className="container">
        <div className="admin-head">
          <div><h1 className="admin-title">Dashboard</h1><p className="admin-sub">Manage your store</p></div>
          {tab === 'products' && <button className="btn-primary" onClick={() => { setForm(EMPTY); setEditId(null); setShowForm(!showForm) }}>{showForm ? 'Cancel' : '+ Add Product'}</button>}
        </div>

        {/* Stats */}
        {stats && (
          <div className="stats-grid">
            <div className="stat-card"><span className="stat-label">Total Users</span><span className="stat-val">{stats.totalUsers}</span></div>
            <div className="stat-card"><span className="stat-label">Total Orders</span><span className="stat-val">{stats.totalOrders}</span></div>
            <div className="stat-card"><span className="stat-label">Products</span><span className="stat-val">{stats.totalProducts}</span></div>
            <div className="stat-card accent"><span className="stat-label">Revenue</span><span className="stat-val">₹{stats.totalRevenue?.toLocaleString('en-IN')}</span></div>
          </div>
        )}

        {/* Tabs */}
        <div className="admin-tabs">
          <button className={`admin-tab ${tab === 'products' ? 'active' : ''}`} onClick={() => setTab('products')}>Products</button>
          <button className={`admin-tab ${tab === 'orders' ? 'active' : ''}`} onClick={() => setTab('orders')}>Orders</button>
        </div>

        {/* Product Form */}
        {tab === 'products' && showForm && (
          <div className="admin-form-card">
            <h2 className="form-head">{editId ? 'Edit Product' : 'Add New Product'}</h2>
            <form onSubmit={saveProduct} className="admin-form">
              <div className="form-row">
                <div className="field"><label>Product Name</label><input type="text" placeholder="e.g. iPhone 15" value={form.name} onChange={e => setForm({...form, name: e.target.value})} required /></div>
                <div className="field"><label>Category</label>
                  <select value={form.category} onChange={e => setForm({...form, category: e.target.value})} required>
                    <option value="">Select category</option>
                    {CATS.map(c => <option key={c} value={c}>{c}</option>)}
                  </select>
                </div>
              </div>
              <div className="field"><label>Description</label><input type="text" placeholder="Short description" value={form.description} onChange={e => setForm({...form, description: e.target.value})} required /></div>
              <div className="form-row">
                <div className="field"><label>Price (₹)</label><input type="number" min="0" step="0.01" value={form.price} onChange={e => setForm({...form, price: e.target.value})} required /></div>
                <div className="field"><label>Stock</label><input type="number" min="0" value={form.stock} onChange={e => setForm({...form, stock: e.target.value})} required /></div>
              </div>
              <div className="field"><label>Image URL</label><input type="url" placeholder="https://..." value={form.imageUrl} onChange={e => setForm({...form, imageUrl: e.target.value})} /></div>
              <div className="form-actions">
                <button type="submit" className="btn-primary" disabled={saving}>{saving ? 'Saving...' : editId ? 'Update' : 'Add Product'}</button>
                <button type="button" className="btn-outline" onClick={() => { setShowForm(false); setEditId(null); setForm(EMPTY) }}>Cancel</button>
              </div>
            </form>
          </div>
        )}

        {/* Products Table */}
        {tab === 'products' && (
          <div className="admin-table-card">
            <h2 className="form-head">Products ({products.length})</h2>
            {products.length === 0 ? <p className="table-empty">No products yet.</p> : (
              <div className="table-wrap">
                <table className="admin-table">
                  <thead><tr><th>Product</th><th>Category</th><th>Price</th><th>Stock</th><th>Actions</th></tr></thead>
                  <tbody>
                    {products.map(p => (
                      <tr key={p.productId}>
                        <td><div className="table-product"><img src={p.imageUrl || `https://picsum.photos/seed/${p.productId}/40/40`} alt={p.name} /><span>{p.name}</span></div></td>
                        <td><span className="table-cat">{p.category}</span></td>
                        <td className="table-price">₹{p.price?.toLocaleString('en-IN')}</td>
                        <td><span className={`stock-badge ${p.stock === 0 ? 'out' : p.stock < 10 ? 'low' : 'ok'}`}>{p.stock}</span></td>
                        <td><div className="table-actions"><button className="edit-btn" onClick={() => startEdit(p)}>Edit</button><button className="delete-btn" onClick={() => deleteProduct(p.productId)}>Delete</button></div></td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </div>
        )}

        {/* Orders Table */}
        {tab === 'orders' && (
          <div className="admin-table-card">
            <h2 className="form-head">All Orders ({orders.length})</h2>
            {orders.length === 0 ? <p className="table-empty">No orders yet.</p> : (
              <div className="table-wrap">
                <table className="admin-table">
                  <thead><tr><th>Order ID</th><th>Customer</th><th>Items</th><th>Total</th><th>Status</th></tr></thead>
                  <tbody>
                    {orders.map(o => (
                      <tr key={o.orderId}>
                        <td className="table-price">#{o.orderId}</td>
                        <td><div style={{display:'flex',flexDirection:'column'}}><span style={{fontWeight:600,fontSize:'0.875rem'}}>{o.user?.name}</span><span style={{fontSize:'0.75rem',color:'var(--text-light)'}}>{o.user?.email}</span></div></td>
                        <td style={{color:'var(--text-mid)'}}>{o.orderItems?.length} item(s)</td>
                        <td className="table-price">₹{o.totalAmount?.toLocaleString('en-IN')}</td>
                        <td>
                          <select className="status-select" value={o.status} onChange={e => updateStatus(o.orderId, e.target.value)}>
                            {STATUS_OPTS.map(s => <option key={s} value={s}>{s}</option>)}
                          </select>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </div>
        )}
      </div>
    </main>
  )
}
