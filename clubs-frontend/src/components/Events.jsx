import { useEffect , useState} from "react";
import { Link } from "react-router-dom";
import axios from "axios";
function Events(){
    const [events , setEvents] = useState([]);

    useEffect(()=>{
       async function fetch(){
            await axios.get("http://localhost:5000/api/v1/events/allevents").
            then((res)=>{
                console.log(res.data);
                setEvents(res.data);
            })
        }
        fetch();
    },[])

    return(
      <div className="bg-gray-200 min-h-screen py-8 px-4">
    <h1 className="text-3xl font-bold mb-8 text-center">All Events Page</h1>
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
      {events.map((item) => (
        <div key={item.id} className="bg-white shadow-lg rounded-lg overflow-hidden">
          <div className="h-64 bg-gray-200">
            <img
              src="https://placehold.co/600x400"
              alt={item.title}
              className="object-cover w-full h-full"
            />
          </div> {/* Image Holder */}
          <div className="p-6">
          <h2 className="text-xl font-bold mb-2">{item.eventName}</h2>
          <p className="text-gray-700 text-sm mb-4">{item.description}</p>
          <div className="grid grid-cols-2 gap-2">
            <div className="flex items-center">
              <span className="text-gray-700 text-sm">Start Date:</span>
              <span className="ml-2 text-sm">{item.startDate}</span>
            </div>
            <div className="flex items-center">
              <span className="text-gray-700 text-sm">End Date:</span>
              <span className="ml-2 text-sm">{item.endDate}</span>
            </div>
          </div>
          <Link
            to={`/event/${item.eventId}`}
            className="block mt-4 bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded-md text-center w-full transition duration-300"
          >
            View Event
          </Link>
            
             </div>
         
        </div>
      ))}
    </div>
  </div>

  
);

}

export default Events;