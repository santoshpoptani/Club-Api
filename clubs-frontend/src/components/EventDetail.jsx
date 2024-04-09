import axios from "axios"
import { useParams } from "react-router-dom"
import { useEffect , useState } from "react"

function EventDetail(){
    const[event , setEvent] = useState("")

    const { id } = useParams()

    useEffect(()=>{
        async function fetch(){
            await axios.get(`http://localhost:5000/api/v1/events/event-detail/${id}`).
            then((res)=>{
                console.log(res.data);
                setEvent(res.data);

            })
        }
        fetch();
    },[]);

    return(<div className="container mx-auto px-4 py-8">
    <img
      src="https://placehold.co/600x400"
      alt="Club Image"
      className="mx-auto mb-6 rounded-lg shadow-md"
      style={{ maxHeight: '500px', objectFit: 'cover', width: '100vw' }}
    />
    <h1 className="text-3xl font-bold mb-4">{event.eventName}</h1>
    <p className="text-lg text-gray-700 mb-6">{event.description}</p>
    <div className="flex flex-col md:flex-row mb-6">
      <div className="md:w-4/5">
        <p className="text-sm text-gray-600">Belongs to:</p>
        <p className="text-lg font-semibold">{event.clubName}</p>
        <p className="text-sm text-gray-600">Start Date:</p>
        <p className="text-lg">{event.startDate}</p>
        <p className="text-sm text-gray-600">End Date:</p>
        <p className="text-lg">{event.endDate}</p>
      </div>
      <div className="md:w-1/5 pr-4 overflow-y-auto">
        <h3 className="text-lg font-semibold mb-4">Users Joined in Event</h3>
        <ul>
          {event.users?.map(user => (
            <li key={user.id} className="mb-2">{user.username}</li>
          ))}
        </ul>
      </div>
    </div>
  </div>
  )

}

export default EventDetail