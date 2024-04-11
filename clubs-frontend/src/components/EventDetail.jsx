import axios from "axios"
import { useParams } from "react-router-dom"
import { useEffect , useState } from "react"
import EventDetails from "./Card/EventDetail";
import UsersJoined from "./Card/UserJoined";

function EventDetail(){
    const[event , setEvent] = useState("")

    const { id } = useParams()
    const auth = JSON.parse(localStorage.getItem("user"))

    useEffect(()=>{
        async function fetch(){
            await axios.get(`http://localhost:5000/api/v1/events/event-detail/${id}`,{
                headers :{ Authorization :'Bearer '+ auth?.Token}
            }).
            then((res)=>{
                console.log(res.data);
                setEvent(res.data);

            })
        }
        fetch();
    },[]);

    return( <div className="bg-gray-200 container mx-auto px-4 py-8">
    <img
      src="https://placehold.co/600x400"
      alt="Club Image"
      className="mx-auto mb-6 rounded-lg shadow-md"
      style={{ maxHeight: '500px', objectFit: 'cover', width: '100vw' }}
    />
    <div className="flex flex-col md:flex-row mb-6">
      <EventDetails
        eventName = {event.eventName}
        description = {event.description}
        clubName={event.clubName}
        startDate={event.startDate}
        endDate={event.endDate}
      />
      <UsersJoined users={event.users} />
    </div>
  </div>
  )

}

export default EventDetail