import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import ProductCard from '../components/ProductCard'
import './Home.css'

const API = 'http://localhost:5050'
const CATEGORIES = ['Electronics', 'Fashion', 'Home & Kitchen', 'Sports', 'Books']

export default function Home() {
  const [featured, setFeatured] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    fetch(`${API}/products`)
      .then(r => r.json())
      .then(data => { setFeatured(data.slice(0, 4)); setLoading(false) })
      .catch(() => setLoading(false))
  }, [])

  return (
    <main className="page">
      {/* Hero */}
      <section className="hero">
        <div className="hero-bg" />
        <div className="container hero-inner">
          <div className="hero-left">
            <span className="hero-tag">New Arrivals 2026</span>
            <h1 className="hero-title">
              Discover<br />
              <span className="hero-italic">Premium</span><br />
              Products
            </h1>
            <p className="hero-sub">Curated collections for the modern lifestyle. Quality you can feel, prices you'll love.</p>
            <div className="hero-cta">
              <Link to="/products" className="btn-primary">Shop Now</Link>
              <Link to="/register" className="btn-outline">Join Free</Link>
            </div>
            <div className="hero-stats">
              <div className="stat"><span className="stat-num">500+</span><span className="stat-label">Products</span></div>
              <div className="stat-divider" />
              <div className="stat"><span className="stat-num">10k+</span><span className="stat-label">Happy Customers</span></div>
              <div className="stat-divider" />
              <div className="stat"><span className="stat-num">Free</span><span className="stat-label">Shipping</span></div>
            </div>
          </div>
          <div className="hero-right">
            <div className="hero-card hero-card-1">
              <div className="hero-card-img" style={{background:'var(--mint-light)'}}>
                <svg width="60" height="60" viewBox="0 0 24 24" fill="none" stroke="var(--mint-mid)" strokeWidth="1"><rect x="2" y="3" width="20" height="14" rx="2"/><line x1="8" y1="21" x2="16" y2="21"/><line x1="12" y1="17" x2="12" y2="21"/></svg>
              </div>
              <p className="hero-card-label">Electronics</p>
              <p className="hero-card-sub">From ₹999</p>
            </div>
            <div className="hero-card hero-card-2">
              <div className="hero-card-img" style={{background:'#fff0f0'}}>
                <svg width="60" height="60" viewBox="0 0 24 24" fill="none" stroke="#ffaaaa" strokeWidth="1"><path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/></svg>
              </div>
              <p className="hero-card-label">Fashion</p>
              <p className="hero-card-sub">Trending Now</p>
            </div>
            <div className="hero-card hero-card-3">
              <div className="hero-card-img" style={{background:'var(--mint-light)'}}>
                <svg width="60" height="60" viewBox="0 0 24 24" fill="none" stroke="var(--mint-mid)" strokeWidth="1"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/><polyline points="9 22 9 12 15 12 15 22"/></svg>
              </div>
              <p className="hero-card-label">Home</p>
              <p className="hero-card-sub">Up to 40% off</p>
            </div>
          </div>
        </div>
      </section>

      {/* Categories */}
      <section className="cats-section">
        <div className="container">
          <div className="section-head">
            <h2 className="section-title">Shop by Category</h2>
            <Link to="/products" className="section-link">View all →</Link>
          </div>
          <div className="cats-grid">
            {CATEGORIES.map((cat, i) => (
              <Link to={`/products?category=${cat}`} key={cat} className="cat-pill">
                <span className="cat-num">0{i+1}</span>
                <span className="cat-name">{cat}</span>
                <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5"><path d="M5 12h14M12 5l7 7-7 7"/></svg>
              </Link>
            ))}
          </div>
        </div>
      </section>

      {/* Featured */}
      <section className="featured-section">
        <div className="container">
          <div className="section-head">
            <h2 className="section-title">Featured Products</h2>
            <Link to="/products" className="section-link">View all →</Link>
          </div>
          {loading ? (
            <div className="products-grid">
              {[1,2,3,4].map(i => <div key={i} className="skeleton" />)}
            </div>
          ) : featured.length > 0 ? (
            <div className="products-grid">
              {featured.map(p => <ProductCard key={p.productId} product={p} />)}
            </div>
          ) : (
            <div className="empty"><p>No products yet. <Link to="/admin">Add from dashboard →</Link></p></div>
          )}
        </div>
      </section>

      {/* Banner */}
      <section className="banner-section">
        <div className="container">
          <div className="banner">
            <div className="banner-text">
              <h2 className="banner-title">Free Shipping on All Orders</h2>
              <p className="banner-sub">No minimum order value. Shop freely across all categories.</p>
            </div>
            <Link to="/products" className="btn-primary">Start Shopping</Link>
          </div>
        </div>
      </section>

      <footer className="footer">
        <div className="container footer-inner">
          <span className="nav-logo"><span className="logo-e">e</span>Vanigam</span>
          <p style={{fontSize:'0.8rem', color:'var(--text-light)'}}>© 2026 eVanigam. All rights reserved.</p>
        </div>
      </footer>
    </main>
  )
}
