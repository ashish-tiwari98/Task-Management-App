import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import TaskList from "./TaskList";

const Dashboard = () => {
  const { token, user } = useContext(AuthContext);
  const navigate = useNavigate();
  const role = user?.role;

  // ✅ State for users (for admins)
  const [users, setUsers] = useState([]);
  const [error, setError] = useState(null); // ✅ Error state added

  // ✅ Fetch users if admin
  useEffect(() => {
    if (role === "ROLE_ADMIN") {
      fetch("http://localhost:8080/admin/users", { 
        headers: { Authorization: `Bearer ${token}` },
      })
        .then((res) => {
          if (res.status === 403) throw new Error("Access Denied! You are not authorized.");
          return res.json();//Extracts the response body and converts it from JSON to a JavaScript object.
        })
        .then((data) => setUsers(data)) //`data` contains the JSON object returned in the previous step.
        .catch((err) => setError(err.message)); // ✅ Capture error
    }
  }, [role, token]);

  if (!token) {
    return (
      <div>
        <h2>Please Login to Continue</h2>
        <button onClick={() => navigate("/")}>Go to Login</button>
      </div>
    );
  }

  return (
    <div>
      <h2>Welcome to Dashboard!</h2>
      <p>You are authenticated as <strong>{role}</strong></p>

      {role === "ROLE_ADMIN" && (
        <div>
          <h3>Admin Panel</h3>
          <button onClick={() => navigate("/manage-users")}>Manage Users</button>

          {/* ✅ Display error message if access is denied */}
          {error && <p className="text-red-500">{error}</p>}

          <h3 className="mt-4">All Users:</h3>
          {users.length > 0 ? (
            <ul className="border p-2">
              {users.map((usr) => (
                <li key={usr.id} className="p-2 border-b">
                  {usr.username} - <strong>{usr.role}</strong>
                </li>
              ))}
            </ul>
          ) : (
            !error && <p>No users found.</p> // Only show "No users" if no error occurred
          )}
        </div>
      )}

      {role === "ROLE_USER" && (
        <div>
          <h3>User Panel</h3>
          <p>Access your tasks and projects here.</p>
          <TaskList />
        </div>
      )}
    </div>
  );
};

export default Dashboard;
