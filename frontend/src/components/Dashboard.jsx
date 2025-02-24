import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

const Dashboard = () => {
  const { token } = useContext(AuthContext);
  const navigate = useNavigate();

  if(!token) {
    return (
      <div>
        <h2>Please Login to Continue</h2>
        <button onClick={() => navigate("/")}>Go to Login</button>
      </div>
    )
  }
  return <h2>Welcome to Dashboard! You are authenticated.</h2>;
};

export default Dashboard;