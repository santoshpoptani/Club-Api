import React,{useState,useEffect} from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import '../css/home.css';
function Home(){
    const[clubs, setSlubs] = useState([]);

    useEffect(()=>{
        async function fetch(){
           await axios.get("http://localhost:5000/api/v1/clubs/allclubs")
            .then((res)=>{
                    setSlubs(res.data);
            })
        }
        
    fetch();
    },[])

    
    return(<>
    <h1>Home Page</h1>
    <div className="card-container">
                {clubs.map(item => (
                    <div key={item.id} className="card">
                        <h2>{item.title}</h2>
                        <p>{item.contenet}</p>
                        <Link to={`/club/${item.id}`}>View Details</Link>
                    </div>
                ))}
            </div>
    </>)
}

export default Home;