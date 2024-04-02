import React,{useState} from 'react';
import axios from 'axios';

function Register(){
    const[username, setUsername] = useState("");
    const[password, setPassword] = useState("");
    const[roles, setRoles] = useState([]);

    const handleSubmit = (e) => {
        e.preventDefault();

        axios.post("http://localhost:5000/api/v1/auth/signup",{
            username,
            password,
            roles
        })
        .then((res)=>{
            console.log(res.data)})
    };


    return(<div>
        <h2>Register</h2>
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
                <div>
                    <label htmlFor="roles">Roles:</label>
                    <select
                        id="roles"
                        multiple
                        value={roles}
                        onChange={(e) => setRoles(Array.from(e.target.selectedOptions, option => option.value))}
                        required
                    >
                        <option value="user">User</option>
                        <option value="ROLE_ADMIN">Admin</option>
                        <option value="mod">Moderator</option>
                    </select>
                </div>
                <button type="submit">Register</button>
            </form>
    </div>)
}

export default Register;