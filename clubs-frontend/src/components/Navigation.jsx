import React from "react";
import { Link, useLocation } from "react-router-dom";
function Navigation(){
    const auth = JSON.parse(localStorage.getItem("user"));
    useLocation();

    const handelLogout=()=>{
        localStorage.removeItem("user");
    }

    return (
      <>
        <nav>
          {auth !== null ? (
            <>
              <Link to="/">Home</Link>
              <Link to="/register" onClick={handelLogout}>Logout</Link>
            </>
          ) : (
            <>
              <Link to="/login">Login</Link>
              <Link to="/register">Register</Link>
              <Link to="/">Home</Link>
            </>
          )}
        </nav>
      </>
    );
}

export default Navigation;