import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { Link, useNavigate } from "react-router-dom";

const Navbar = () => {
  const { token, user, setToken } = useContext(AuthContext);
  const role = user?.role;
  const navigate = useNavigate();

  const handleLogout = () => {
    setToken(null);
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    navigate("/");
  };

  return (
    <nav style={{ display: "flex", gap: "10px", padding: "10px", borderBottom: "1px solid #ccc" }}>
      <Link to="/">Home</Link>
      {token && <Link to="/dashboard">Dashboard</Link>}
      {role === "ROLE_ADMIN" && <Link to="/manage-users">Manage Users</Link>}
      {token ? (
        <button onClick={handleLogout}>Logout</button>
      ) : (
        <>
          <Link to="/">Login</Link>
          <Link to="/register">Register</Link>
        </>
      )}
    </nav>
  );
};

export default Navbar;
