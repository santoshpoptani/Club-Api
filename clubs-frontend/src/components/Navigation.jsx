import React from "react";
import { Link, useLocation ,useNavigate } from "react-router-dom";
function Navigation(){
    const auth = JSON.parse(localStorage.getItem("user"));
    useLocation();

    const navigate = useNavigate();

    const handleLogout=()=>{
        localStorage.removeItem("user");
        navigate("/")
    }

    return (
      <>
        <nav className="bg-gradient-to-r from-blue-400 via-blue-500 to-blue-600 py-2 px-6 flex justify-between items-center shadow-lg">
            <div>
                <Link to="/" className="text-white text-lg font-extrabold tracking-tight">Your Logo</Link>
            </div>
            <div className="flex items-center space-x-4">
                {auth !== null ? (
                    <>
                        <Link to="/" className="text-white hover:text-gray-200 transition duration-300">Home</Link>
                        <Link to="/events" className="text-white hover:text-gray-200 transition duration-300">Events</Link>
                        <Link to="/profile" className="text-white hover:text-gray-200 transition duration-300">Profile</Link>
                        <button onClick={handleLogout} className="bg-red-500 text-white px-3 py-1 rounded-md hover:bg-red-600 transition duration-300">Logout</button>
                    </>
                ) : (
                    <>
                        <Link to="/login" className="text-white hover:text-gray-200 transition duration-300">Login</Link>
                        <Link to="/register" className="text-white hover:text-gray-200 transition duration-300">Register</Link>
                        <Link to="/" className="text-white hover:text-gray-200 transition duration-300">Home</Link>
                        <Link to="/events" className="text-white hover:text-gray-200 transition duration-300">Events</Link>
                    </>
                )}
            </div>
        </nav>
      </>
    );
}

export default Navigation;