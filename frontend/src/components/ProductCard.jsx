function ProductCard() {
  return (
    <div className="card">
      <img
        src="https://via.placeholder.com/200"
        alt="product"
      />

      <h3>Product Name</h3>
      <p>₹999</p>

      <button>Add to Cart</button>
    </div>
  );
}

export default ProductCard;