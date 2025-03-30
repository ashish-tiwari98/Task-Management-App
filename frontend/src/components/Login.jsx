import { useState, useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const { login } = useContext(AuthContext); // Use `login` from AuthContext
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null); // Track login errors
  const navigate = useNavigate();

  const handleLogin = async () => {
    setError(null); // Reset error before login attempt
    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
        credentials: "include",
      });

      const text = await response.text();
      const data = text ? JSON.parse(text) : {}; 

      if (response.ok) {
        login(data.token, { username: data.username, role: data.role }); // Store auth state
        navigate("/dashboard");
      } else {
        setError(data.message || "Invalid credentials");
      }
    } catch (error) {
      setError("Failed to connect to the server. Please try again later.");
      console.error("Login error:", error);
    }
  };

  return (
    <div className="p-4 max-w-md mx-auto bg-white shadow-lg rounded-md">
      <h2 className="text-xl font-bold mb-4">Login</h2>

      {error && <p className="text-red-500">{error}</p>}

      <input
        className="border p-2 w-full mb-2"
        placeholder="Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
      <input
        className="border p-2 w-full mb-4"
        placeholder="Password"
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      
      <button 
        onClick={handleLogin} 
        className="bg-blue-500 text-white px-4 py-2 w-full rounded-md"
      >
        Login
      </button>

      <p className="mt-2 text-center">
        Don&apos;t have an account? 
        <button 
          onClick={() => navigate("/register")} 
          className="text-blue-500 underline ml-1"
        >
          Register
        </button>
      </p>
    </div>
  );
};

export default Login;
