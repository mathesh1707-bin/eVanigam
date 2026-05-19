import { useState, useEffect } from 'react'
import { useSearchParams } from 'react-router-dom'
import ProductCard from '../components/ProductCard'
import './Products.css'

const API = 'http://localhost:5050'
const CATEGORIES = ['All', 'Electronics', 'Fashion', 'Home & Kitchen', 'Sports', 'Books']

export default function Products() {
  const [products, setProducts] = useState([])
  const [loading, setLoading] = useState(true)
  const [search, setSearch] = useState('')
  const [searchParams] = useSearchParams()
  const [activeCategory, setActiveCategory] = useState(searchParams.get('category') || 'All')

  useEffect(() => {
    fetch(`${API}/products`)
      .then(r => r.json())
      .then(data => { setProducts(data); setLoading(false) })
      .catch(() => setLoading(false))
  }, [])

  const filtered = products.filter(p => {
    const matchCat = activeCategory === 'All' || p.category === activeCategory
    const matchSearch = p.name.toLowerCase().includes(search.toLowerCase()) ||
                        p.description?.toLowerCase().includes(search.toLowerCase())
    return matchCat && matchSearch
  })

  return (
    <main className="page products-page">
      <div className="container">
        <div className="products-header">
          <div>
            <h1 className="products-title">All Products</h1>
            <p className="products-count">{filtered.length} items found</p>
          </div>
          <div className="search-wrap">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
              <circle cx="11" cy="11" r="8"/><path d="m21 21-4.35-4.35"/>
            </svg>
            <input
              type="text"
              placeholder="Search products..."
              value={search}
              onChange={e => setSearch(e.target.value)}
              className="search-input"
            />
          </div>
        </div>

        <div className="category-tabs">
          {CATEGORIES.map(cat => (
            <button
              key={cat}
              className={`cat-tab ${activeCategory === cat ? 'active' : ''}`}
              onClick={() => setActiveCategory(cat)}
            >
              {cat}
            </button>
          ))}
        </div>

        {loading ? (
          <div className="products-grid">
            {[1,2,3,4,5,6].map(i => <div key={i} className="skeleton-card" style={{height: 360}} />)}
          </div>
        ) : filtered.length > 0 ? (
          <div className="products-grid">
            {filtered.map(p => <ProductCard key={p.productId} product={p} />)}
          </div>
        ) : (
          <div className="no-results">
            <p>No products found for "<strong>{search || activeCategory}</strong>"</p>
          </div>
        )}
      </div>
    </main>
  )
}
