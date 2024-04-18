import { useState , useEffect } from "react";
import { format } from 'date-fns';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import axios from "axios";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function EventupdateModal({onClose, eventid}){
    const [eventName, setEventName] = useState('');
    const [eventDescription, setEventDescription] = useState('');
    const [eventDate, setEventDate] = useState(new Date());
    const auth = JSON.parse(localStorage.getItem("user"))

    useEffect(()=>{
        function fetch(){
            axios.get(`http://localhost:5000/api/v1/events/event-detail/${eventid}`,{
                headers :{ Authorization :'Bearer '+ auth?.Token}
            }).then((res)=>{
                console.log(res.data);
                setEventName(res.data.eventName)
                setEventDescription(res.data.description)
                setEventDate(res.data.endDate)
            })
        }
        fetch()
    },[])
  
    const handleUpdateEvent = () => {
    axios.patch(`http://localhost:5000/api/v1/events/update/${eventid}`,{
        eventname :eventName,
        content:eventDescription,
        enddate:format(eventDate, 'yyyy-MM-dd')

    },{

        headers :{ Authorization :'Bearer '+ auth?.Token}
    }).then((res)=>{
        console.log(res.data);
        toast.success("Event Update Successfully")
        onClose();
    })  
    };
  
    return (
      <div>
        <div className="mb-4">
          <input
            type="text"
            id="eventName"
            value={eventName}
            onChange={(e) => setEventName(e.target.value)}
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500"
          />
        </div>
        <div className="mb-4">
          <label htmlFor="eventDescription" className="block text-sm font-semibold mb-2">
            Event Description:
          </label>
          <textarea
            id="eventDescription"
            value={eventDescription}
            onChange={(e) => setEventDescription(e.target.value)}
            rows="4"
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500"
          ></textarea>
        </div>
        <div className="mb-4">
          <label htmlFor="eventDate" className="block text-sm font-semibold mb-2">
            Event Date:
          </label>
          <DatePicker
            id="eventDate"
            selected={eventDate}
            onChange={(date) => setEventDate(date)}
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500"
          />
        </div>
        <button
          onClick={handleUpdateEvent}
          className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md mr-2"
        >
          Update Event
        </button>
        <button
          onClick={onClose}
          className="bg-gray-300 hover:bg-gray-400 text-gray-800 px-4 py-2 rounded-md"
        >
          Cancel
        </button>
      </div>
    );
}

export default EventupdateModal;