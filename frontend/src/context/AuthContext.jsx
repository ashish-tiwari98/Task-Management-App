//Manages Auth state globally
import { createContext, useState, useEffect} from "react";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(null);
  const [user, setUser] = useState({username:null, role:null});

  //Automatically fetch token from local storage on page reload
  useEffect(() => {
    const storedToken = localStorage.getItem("token")
    const storedUser = localStorage.getItem("user")
    if (storedToken) setToken(storedToken);
    if (storedUser) setUser(JSON.parse(storedUser))
  }, []);

  return (
    <AuthContext.Provider value={{ token, setToken, user, setUser }}>
      {children}
    </AuthContext.Provider>
  );
};