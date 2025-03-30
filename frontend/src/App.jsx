import { BrowserRouter as Router, Routes, Route, Navigate, BrowserRouter } from "react-router-dom";
import { AuthProvider, AuthContext } from "./context/AuthContext";
import { useContext } from "react";
import Login from "./components/Login";
import Dashboard from "./components/Dashboard";
import Register from "./components/Register";
import ProtectedRoute from "./components/ProtectedRoute";
import Navbar from "./components/Navbar";
import TaskList from "./components/TaskList";

const App = () => {
  return (
    <BrowserRouter>
      <AuthProvider>
        <AppContent />
      </AuthProvider>
    </BrowserRouter>
  ); 
};

const AppContent = () => {
  const { token } = useContext(AuthContext);

  return (
    <>
      {token && <Navbar />}  {/* Show Navbar only when authenticated */}
      <Routes>
        <Route path="/" element={token ? <Navigate to="/dashboard" replace /> : <Navigate to="/login" replace />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route 
          path="/dashboard" 
          element={<ProtectedRoute element={<Dashboard />} allowedRoles={["ROLE_USER", "ROLE_ADMIN"]} />}
        />
        <Route
          path="/tasks"
          element={<ProtectedRoute element={<TaskList />} allowedRoles={["ROLE_USER"]} />}
        />
        {/* Remove this if you haven't created ManageUsers yet */}
        {/* <Route 
          path="/manage-users" 
          element={<ProtectedRoute element={<ManageUsers />} allowedRoles={["ADMIN"]} />}
        /> */}
        <Route path="/unauthorized" element={<h2>Unauthorized Access</h2>} />
      </Routes>
    </>
  );
};

export default App;


// import { useEffect, useState } from "react";

// function App() {
//   const [tasks, setTasks] = useState([]);
//   const [title, setTitle] = useState("");

//   useEffect(() => {
//     fetch("http://localhost:8080/tasks")
//       .then((res) => res.json())
//       .then((data) => setTasks(data));
//   }, []);

//   const addTask = async () => {
//     const response = await fetch("http://localhost:8080/tasks", {
//       method: "POST",
//       headers: { "Content-Type": "application/json" },
//       body: JSON.stringify({ title, status: "Pending" }),
//     });

//     const newTask = await response.json(); //it waits for response and then extracts in newtask to update in ui
//     setTasks([...tasks, newTask]); // Update state
//     setTitle(""); // Clear input field
//   };

//   return (
//     <div className="p-4">
//       <h1 className="text-xl font-bold mb-4">Task List</h1>
      
//       <div className="mb-4">
//         <input
//           type="text"
//           placeholder="Enter Task"
//           value={title}
//           onChange={(e) => setTitle(e.target.value)}
//           className="border p-2 mr-2"
//         />
//         <button onClick={addTask} className="bg-blue-500 text-white px-4 py-2">
//           Add Task
//         </button>
//       </div>

//       <ul>
//         {tasks.map((task) => (
//           <li key={task.id} className="border p-2 mb-2">
//             {task.title} - {task.status}
//           </li>
//         ))}
//       </ul>
//     </div>
//   );
// }

// export default App;
