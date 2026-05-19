import { useState, useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import { useNavigate } from 'react-router-dom'
import './AdminDashboard.css'

const API = 'http://localhost:5050'
const EMPTY_FORM = { name: '', description: '', price: '', imageUrl: '', stock: '', category: '' }
const CATEGORIES = ['Electronics', 'Fashion', 'Home & Kitchen', 'Sports', 'Books']

export default function AdminDashboard() {
  const { token, isAdmin } = useAuth()
  const navigate = useNavigate()
  const [products, setProducts] = useState([])
  const [loading, setLoading] = useState(true)
  const [form, setForm] = useState(EMPTY_FORM)
  const [editId, setEditId] = useState(null)
  const [showForm, setShowForm] = useState(false)
  const [submitting, setSubmitting] = useState(false)

  useEffect(() => {
    if (!isAdmin) { navigate('/'); return }
    loadProducts()
  }, [isAdmin])

  const loadProducts = () => {
    fetch(`${API}/products`, {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(r => r.json())
      .then(data => { setProducts(data); setLoading(false) })
      .catch(() => setLoading(false))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setSubmitting(true)
    const method = editId ? 'PUT' : 'POST'
    const url = editId ? `${API}/products/${editId}` : `${API}/products`
    try {
      await fetch(url, {
        method,
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`
        },
        body: JSON.stringify({ ...form, price: Number(form.price), stock: Number(form.stock) })
      })
      setForm(EMPTY_FORM)
      setEditId(null)
      setShowForm(false)
      loadProducts()
    } finally {
      setSubmitting(false)
    }
  }

  const handleEdit = (p) => {
    setForm({ name: p.name, description: p.description, price: p.price, imageUrl: p.imageUrl || '', stock: p.stock, category: p.category })
    setEditId(p.productId)
    setShowForm(true)
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }

  const handleDelete = async (id) => {
    if (!confirm('Delete this product?')) return
    await fetch(`${API}/products/${id}`, {
      method: 'DELETE',
      headers: { Authorization: `Bearer ${token}` }
    })
    loadProducts()
  }

  return (
    <main className="page admin-page">
      <div className="container">
        <div className="admin-header">
          <div>
            <h1 className="admin-title">Dashboard</h1>
            <p className="admin-sub">Manage your product catalog</p>
          </div>
          <button className="btn-primary" onClick={() => { setForm(EMPTY_FORM); setEditId(null); setShowForm(!showForm) }}>
            {showForm ? 'Cancel' : '+ Add Product'}
          </button>
        </div>

        {/* Stats */}
        <div className="stats-grid">
          <div className="stat-card">
            <span className="stat-label">Total Products</span>
            <span className="stat-value">{products.length}</span>
          </div>
          <div className="stat-card">
            <span className="stat-label">In Stock</span>
            <span className="stat-value">{products.filter(p => p.stock > 0).length}</span>
          </div>
          <div className="stat-card">
            <span className="stat-label">Low Stock</span>
            <span className="stat-value low">{products.filter(p => p.stock < 10 && p.stock > 0).length}</span>
          </div>
          <div className="stat-card">
            <span className="stat-label">Out of Stock</span>
            <span className="stat-value danger">{products.filter(p => p.stock === 0).length}</span>
          </div>
        </div>

        {/* Product Form */}
        {showForm && (
          <div className="admin-form-card">
            <h2 className="form-title">{editId ? 'Edit Product' : 'Add New Product'}</h2>
            <form onSubmit={handleSubmit} className="admin-form">
              <div className="form-row">
                <div className="field">
                  <label>Product Name</label>
                  <input type="text" placeholder="e.g. Wireless Headphones" value={form.name} onChange={e => setForm({...form, name: e.target.value})} required />
                </div>
                <div className="field">
                  <label>Category</label>
                  <select value={form.category} onChange={e => setForm({...form, category: e.target.value})} required>
                    <option value="">Select category</option>
                    {CATEGORIES.map(c => <option key={c} value={c}>{c}</option>)}
                  </select>
                </div>
              </div>
              <div className="field">
                <label>Description</label>
                <input type="text" placeholder="Short product description" value={form.description} onChange={e => setForm({...form, description: e.target.value})} required />
              </div>
              <div className="form-row">
                <div className="field">
                  <label>Price (₹)</label>
                  <input type="number" placeholder="0" min="0" step="0.01" value={form.price} onChange={e => setForm({...form, price: e.target.value})} required />
                </div>
                <div className="field">
                  <label>Stock</label>
                  <input type="number" placeholder="0" min="0" value={form.stock} onChange={e => setForm({...form, stock: e.target.value})} required />
                </div>
              </div>
              <div className="field">
                <label>Image URL (optional)</label>
                <input type="url" placeholder="https://..." value={form.imageUrl} onChange={e => setForm({...form, imageUrl: e.target.value})} />
              </div>
              <div className="form-actions">
                <button type="submit" className="btn-primary" disabled={submitting}>
                  {submitting ? 'Saving...' : editId ? 'Update Product' : 'Add Product'}
                </button>
                <button type="button" className="btn-ghost" onClick={() => { setShowForm(false); setEditId(null); setForm(EMPTY_FORM) }}>
                  Cancel
                </button>
              </div>
            </form>
          </div>
        )}

        {/* Products Table */}
        <div className="admin-table-card">
          <h2 className="form-title">Products ({products.length})</h2>
          {loading ? (
            <p className="table-loading">Loading...</p>
          ) : products.length === 0 ? (
            <p className="table-empty">No products yet. Add your first product above.</p>
          ) : (
            <div className="table-wrap">
              <table className="admin-table">
                <thead>
                  <tr>
                    <th>Product</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Stock</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {products.map(p => (
                    <tr key={p.productId}>
                      <td>
                        <div className="table-product">
                          <img src={p.imageUrl || `https://picsum.photos/seed/${p.productId}/40/40`} alt={p.name} />
                          <span>{p.name}</span>
                        </div>
                      </td>
                      <td><span className="table-cat">{p.category}</span></td>
                      <td className="table-price">₹{p.price?.toLocaleString('en-IN')}</td>
                      <td>
                        <span className={`stock-badge ${p.stock === 0 ? 'out' : p.stock < 10 ? 'low' : 'ok'}`}>
                          {p.stock}
                        </span>
                      </td>
                      <td>
                        <div className="table-actions">
                          <button className="edit-btn" onClick={() => handleEdit(p)}>Edit</button>
                          <button className="delete-btn" onClick={() => handleDelete(p.productId)}>Delete</button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      </div>
    </main>
  )
}
