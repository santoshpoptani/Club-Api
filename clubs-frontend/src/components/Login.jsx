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

    return(<div>
        <h1>Login Page</h1>
        <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="username">Username:</label>
                    <input
                        type="text"
                        id="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Login</button>
            </form>
        
    </div>)
}

export default Login