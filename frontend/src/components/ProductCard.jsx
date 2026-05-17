import { useCart } from '../context/CartContext'
import './ProductCard.css'

export default function ProductCard({ product }) {
  const { addToCart } = useCart()

  return (
    <div className="product-card">
      <div className="product-img-wrap">
        <img
          src={product.imageUrl || `https://picsum.photos/seed/${product.productId}/400/300`}
          alt={product.name}
          className="product-img"
        />
        <div className="product-overlay">
          <button className="btn-primary" onClick={() => addToCart(product)}>
            Add to Cart
          </button>
        </div>
        <span className="product-category">{product.category}</span>
      </div>
      <div className="product-info">
        <h3 className="product-name">{product.name}</h3>
        <p className="product-desc">{product.description}</p>
        <div className="product-footer">
          <span className="product-price">₹{product.price?.toLocaleString('en-IN')}</span>
          <span className={`product-stock ${product.stock < 10 ? 'low' : ''}`}>
            {product.stock < 10 ? `Only ${product.stock} left` : 'In Stock'}
          </span>
        </div>
      </div>
    </div>
  )
}
