import { useEffect, useState, useContext } from "react";
import { AuthContext } from "../context/AuthContext";

const TaskList = () => {
  const { token, logout } = useContext(AuthContext);
  const [tasks, setTasks] = useState([]);
  const [title, setTitle] = useState("");
  const [error, setError] = useState(null); // New: Error handling

  useEffect(() => {
    if (token) {
      fetchTasks();
    }
  }, [token]);

  const fetchTasks = async () => {
    try {
      const response = await fetch("http://localhost:8080/tasks", {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (response.status === 401) {
        logout(); // Expired token, logout user
      } else if (!response.ok) {
        throw new Error("Failed to fetch tasks");
      }

      const data = await response.json();
      setTasks(data);
    } catch (err) {
      setError(err.message);
    }
  };

  const addTask = async () => {
    if (!title.trim()) return;
    try {
      const response = await fetch("http://localhost:8080/tasks", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ title, status: "Pending" }),
      });

      if (response.status === 401) {
        logout();
      } else if (!response.ok) {
        throw new Error("Failed to add task");
      }

      const newTask = await response.json();
      setTasks([...tasks, newTask]);
      setTitle("");
    } catch (err) {
      setError(err.message);
    }
  };

  const updateTask = async (taskId) => {
    try {
      const response = await fetch(`http://localhost:8080/tasks/${taskId}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ status: "Completed" }),
      });

      if (response.status === 401) {
        logout();
      } else if (!response.ok) {
        throw new Error("Failed to update task");
      }

      setTasks(tasks.map((task) =>
        task.id === taskId ? { ...task, status: "Completed" } : task
      ));
    } catch (err) {
      setError(err.message);
    }
  };

  const deleteTask = async (taskId) => {
    try {
      const response = await fetch(`http://localhost:8080/tasks/${taskId}`, {
        method: "DELETE",
        headers: { Authorization: `Bearer ${token}` },
      });

      if (response.status === 401) {
        logout();
      } else if (!response.ok) {
        throw new Error("Failed to delete task");
      }

      setTasks(tasks.filter((task) => task.id !== taskId));
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="p-4">
      <h1 className="text-xl font-bold mb-4">Task List</h1>

      {error && <p className="text-red-500">{error}</p>}

      <div className="mb-4">
        <input
          type="text"
          placeholder="Enter Task"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          className="border p-2 mr-2"
        />
        <button onClick={addTask} className="bg-blue-500 text-white px-4 py-2">
          Add Task
        </button>
      </div>

      <ul>
        {tasks.length > 0 ? (
          tasks.map((task) => (
            <li key={task.id} className="border p-2 mb-2 flex justify-between items-center">
              <span>
                {task.title} - <strong>{task.status}</strong>
              </span>
              <div>
                {task.status !== "Completed" && (
                  <button onClick={() => updateTask(task.id)} className="bg-green-500 text-white px-2 py-1 mr-2">
                    ✅ Complete
                  </button>
                )}
                <button onClick={() => deleteTask(task.id)} className="bg-red-500 text-white px-2 py-1">
                  ❌ Delete
                </button>
              </div>
            </li>
          ))
        ) : (
          <p>No tasks available.</p>
        )}
      </ul>
    </div>
  );
};

export default TaskList;
