import { Link } from "react-router-dom";

function Navbar() {
  return (
    <nav className="navbar">
      <h2>eVanigam</h2>

      <div className="nav-links">
        <Link to="/">Home</Link>
        <Link to="/products">Products</Link>
        <Link to="/cart">Cart</Link>
        <Link to="/login">Login</Link>
      </div>
    </nav>
  );
}

export default Navbar;