import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import Login from "./components/Login";
import Dashboard from "./components/Dashboard";
import Register from "./components/Register";

const App = () => {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/dashboard" element={<Dashboard />} />
        </Routes>
      </Router>
    </AuthProvider>
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
