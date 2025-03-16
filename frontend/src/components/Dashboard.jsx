import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

const Dashboard = () => {
  const { token, user } = useContext(AuthContext);
  const navigate = useNavigate();
  const role = user?.role;

  if(!token) {
    return (
      <div>
        <h2>Please Login to Continue</h2>
        <button onClick={() => navigate("/")}>Go to Login</button>
      </div>
    )
  }
  return(
    <div>
      <h2>Welcome to Dashboard!</h2>
      <p>You are authenticated as <strong>{role}</strong></p>

      {role === "ROLE_ADMIN" && (
        <div>
          <h3>Admin Panel</h3>
          <button onClick={() => navigate("/manage-users")}>Manage Users</button>
        </div>
      )}

      {role === "ROLE_USER" && (
        <div>
          <h3>User Panel</h3>
          <p>Access your tasks and projects here.</p>
        </div>
      )}

    </div>
  );
};

export default Dashboard;