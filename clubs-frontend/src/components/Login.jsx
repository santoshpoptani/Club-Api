import { useState } from "react"
import { useNavigate } from "react-router-dom";
import axios from "axios";
function Login(){
    const[username, setUsername] = useState("");
    const[password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();

        axios.post("http://localhost:5000/api/v1/auth/signin",{
            username: username,
            password: password
        }).then((response) => {
                console.log(response.data);
                localStorage.setItem("user", JSON.stringify(response.data));
                navigate("/")

        });
        
    };

    return(<div className="flex justify-center items-center h-screen bg-gradient-to-r from-blue-400 via-blue-500 to-blue-600">
    <div className="bg-white p-8 rounded-lg shadow-md" style={{ width: "28rem" }}> {/* Set width using inline style */}
        <h1 className="text-2xl font-bold mb-4 text-center">Login Page</h1>
        <form onSubmit={handleSubmit} className="login-form">
            <div className="mb-4">
                <label htmlFor="username" className="block font-semibold mb-1">Username:</label>
                <input
                    type="text"
                    id="username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    className="w-full py-2 px-3 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500"
                    required
                />
            </div>
            <div className="mb-4">
                <label htmlFor="password" className="block font-semibold mb-1">Password:</label>
                <input
                    type="password"
                    id="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="w-full py-2 px-3 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500"
                    required
                />
            </div>
            <button
                type="submit"
                className="w-full py-2 px-4 bg-blue-500 text-white rounded-md hover:bg-blue-600 focus:outline-none"
            >
                Login
            </button>
        </form>
    </div>
</div>


)
}

export default Login