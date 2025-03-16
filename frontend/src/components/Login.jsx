import { useState, useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom"

const Login = () => {
  const { setToken } = useContext(AuthContext);
  const { setUser } = useContext(AuthContext);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({ username, password }),
        credentials: "include",
      });

      const text = await response.text();
      const data = text ? JSON.parse(text) : {}; 
      if(response.ok) {
        setToken(data.token);
        localStorage.setItem("token", data.token);
        setUser({username: data.username, role: data.role})
        localStorage.setItem("user", JSON.stringify({ username: data.username, role: data.role }));
        navigate("/dashboard");
      } else {
        alert(data.message || "Invalid credentials");
      }
    } catch (error) {
      console.error("Login failed:", error);
    }
  };

  return (
    <div>
      <h2>Login</h2>
      <input
        placeholder="Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
      <input
        placeholder="Password"
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button onClick={handleLogin}>Login</button>
      <p>Don't have an account? <button onClick={() => navigate("/register")}>Register</button></p>
    </div>
  );
};

export default Login;