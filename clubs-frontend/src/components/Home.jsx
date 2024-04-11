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
                    console.log(res.data)
                    setSlubs(res.data);
            })
        }
        
    fetch();
    },[])

    
    return (
        <div className="bg-gradient-to-r from-blue-400 via-blue-500 to-blue-600 min-h-screen py-8 px-4">
    <h1 className="text-3xl font-bold mb-8 text-center text-white">Home Page</h1>
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-10">
      {clubs.map((item) => (
        <div key={item.id} className="bg-white shadow-md rounded-lg overflow-hidden">
          <div className="h-50 bg-gray-200">
            <img
              src="https://placehold.co/600x400"
              alt={item.title}
              className="object-cover w-full h-full"
            />
          </div> {/* Image Holder */}
          <div className="p-6">
            <h2 className="text-xl font-bold mb-2">{item.title}</h2>
            <p className="text-gray-700 mb-4">{item.contenet}</p>
            <div className="flex justify-end">
              <Link
                to={`/club/${item.id}`}
                className="text-blue-500 font-semibold hover:text-blue-700 transition duration-300"
              >
                View Details
              </Link>
            </div>
          </div>
        </div>
      ))}
    </div>
  </div>
    );
}

export default Home;