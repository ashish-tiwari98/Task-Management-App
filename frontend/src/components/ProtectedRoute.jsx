import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { Navigate } from "react-router-dom";

const ProtectedRoute = ({ element, allowedRoles }) => {
  const { token, user } = useContext(AuthContext)

  const role = user?.role;//this is optional chaining  to avoid errors if user is null
  
  if(!token) { //checking authentication
    return <Navigate to="/" replace />;
  }

  if (!role || !allowedRoles.includes(role)) { //checking authorization
    return <Navigate to="/unauthorized" replace />;
  }

  return element;
};

export default ProtectedRoute;
