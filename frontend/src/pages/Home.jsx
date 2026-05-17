import { Link } from 'react-router-dom'
import { useState, useEffect } from 'react'
import ProductCard from '../components/ProductCard'
import './Home.css'

const API = 'http://localhost:8080'

const CATEGORIES = ['Electronics', 'Fashion', 'Home & Kitchen', 'Sports', 'Books']

export default function Home() {
  const [featured, setFeatured] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    fetch(`${API}/products`)
      .then(r => r.json())
      .then(data => {
        setFeatured(data.slice(0, 4))
        setLoading(false)
      })
      .catch(() => setLoading(false))
  }, [])

  return (
    <main className="page">
      {/* Hero */}
      <section className="hero">
        <div className="hero-grain" />
        <div className="container hero-inner">
          <div className="hero-tag">New Season Drop</div>
          <h1 className="hero-title">
            Shop What<br />
            <span className="hero-accent">Matters.</span>
          </h1>
          <p className="hero-sub">
            Premium products. Unmatched quality.<br />
            Everything you need, nothing you don't.
          </p>
          <div className="hero-cta">
            <Link to="/products" className="btn-primary">Explore Store</Link>
            <Link to="/register" className="btn-ghost">Create Account</Link>
          </div>
        </div>
        <div className="hero-scroll-hint">
          <span>Scroll</span>
          <div className="scroll-line" />
        </div>
      </section>

      {/* Categories */}
      <section className="categories-section container">
        <div className="section-header">
          <h2 className="section-title">Browse Categories</h2>
        </div>
        <div className="categories-grid">
          {CATEGORIES.map((cat, i) => (
            <Link to={`/products?category=${cat}`} key={cat} className="cat-card">
              <div className="cat-num">0{i + 1}</div>
              <span className="cat-name">{cat}</span>
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                <path d="M5 12h14M12 5l7 7-7 7"/>
              </svg>
            </Link>
          ))}
        </div>
      </section>

      {/* Featured Products */}
      <section className="featured-section container">
        <div className="section-header">
          <h2 className="section-title">Featured Products</h2>
          <Link to="/products" className="view-all">View All →</Link>
        </div>

        {loading ? (
          <div className="loading-grid">
            {[1,2,3,4].map(i => <div key={i} className="skeleton-card" />)}
          </div>
        ) : featured.length > 0 ? (
          <div className="products-grid">
            {featured.map(p => <ProductCard key={p.productId} product={p} />)}
          </div>
        ) : (
          <div className="empty-state">
            <p>No products yet. <Link to="/admin">Add some from the dashboard →</Link></p>
          </div>
        )}
      </section>

      {/* CTA Banner */}
      <section className="cta-banner container">
        <div className="cta-inner">
          <div>
            <h2 className="cta-title">Ready to Start Shopping?</h2>
            <p className="cta-sub">Create your account and enjoy seamless shopping.</p>
          </div>
          <Link to="/register" className="btn-primary">Get Started</Link>
        </div>
      </section>

      {/* Footer */}
      <footer className="footer container">
        <div className="footer-brand">
          <span className="logo-e">e</span>Vanigam
        </div>
        <p className="footer-copy">© 2024 eVanigam. All rights reserved.</p>
      </footer>
    </main>
  )
}
