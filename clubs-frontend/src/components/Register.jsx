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


    return( <div className="bg-gradient-to-r from-blue-400 via-blue-500 to-blue-600 min-h-screen flex items-center justify-center">
    <div className="bg-white shadow-md rounded-lg px-8 py-6 w-full max-w-md"> {/* Change max-w-xl to max-w-lg */}
        <h2 className="text-2xl font-bold mb-6 text-center">Register</h2>
        <form onSubmit={handleSubmit}>
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
            <div className="mb-4">
                <label htmlFor="roles" className="block font-semibold mb-1">Roles:</label>
                <select
                    id="roles"
                    multiple
                    value={roles}
                    onChange={(e) => setRoles(Array.from(e.target.selectedOptions, option => option.value))}
                    className="w-full py-2 px-3 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500"
                    required
                >
                    <option value="user">User</option>
                    <option value="ROLE_ADMIN">Admin</option>
                    <option value="mod">Moderator</option>
                </select>
            </div>
            <button
                type="submit"
                className="w-full py-2 px-4 bg-blue-500 text-white rounded-md hover:bg-blue-600 focus:outline-none"
            >
                Register
            </button>
        </form>
    </div>
</div>

)
}

export default Register;