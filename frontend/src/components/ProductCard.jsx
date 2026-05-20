import { useCart } from '../context/CartContext'
import './ProductCard.css'

export default function ProductCard({ product }) {
  const { addToCart, loading } = useCart()

  return (
    <div className="pcard">
      <div className="pcard-img-wrap">
        <img src={product.imageUrl || `https://picsum.photos/seed/${product.productId}/400/300`} alt={product.name} className="pcard-img" />
        <div className="pcard-overlay">
          <button className="btn-primary" onClick={() => addToCart(product)} disabled={loading}>
            {loading ? '...' : 'Add to Cart'}
          </button>
        </div>
        <span className="pcard-cat">{product.category}</span>
        {product.stock < 10 && product.stock > 0 && <span className="pcard-low">Only {product.stock} left</span>}
        {product.stock === 0 && <span className="pcard-out">Out of Stock</span>}
      </div>
      <div className="pcard-info">
        <h3 className="pcard-name">{product.name}</h3>
        <p className="pcard-desc">{product.description}</p>
        <div className="pcard-footer">
          <span className="pcard-price">₹{product.price?.toLocaleString('en-IN')}</span>
          <button className="pcard-add" onClick={() => addToCart(product)} disabled={product.stock === 0}>
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
              <line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/>
            </svg>
          </button>
        </div>
      </div>
    </div>
  )
}
