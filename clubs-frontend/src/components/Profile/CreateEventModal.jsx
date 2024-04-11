import { useState } from "react";
import { format } from 'date-fns';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import axios from "axios";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


function CreateEventModal({ onClose , ownedClubTitles }){
    const [eventName, setEventName] = useState('');
    const [eventDescription, setEventDescription] = useState('');
    const [selectedClub, setSelectedClub] = useState('');
    const [eventDate, setEventDate] = useState(null);
    const auth = JSON.parse(localStorage.getItem("user"));

    const handleCreateEvent = () => {
        axios.post(`http://localhost:5000/api/v1/events/new/${selectedClub}`,{
            eventname:eventName,
            content:eventDescription,
            enddate:format(eventDate, 'yyyy-MM-dd')
        },
       {
        headers:{
             "Authorization": "Bearer " + auth.Token
        }
       })
       .then((res)=>{
        console.log(res.data);
        toast.success(res.data.Message)
       })
      onClose();
    };
  
    return (
      <div className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 flex justify-center items-center">
        <div className="bg-white p-4 rounded-lg shadow-md">
          <h2 className="text-xl font-semibold mb-4">Create Event</h2>
          <div className="mb-4">
            <label htmlFor="eventName" className="block text-sm font-semibold mb-2">Event Name:</label>
            <input
              type="text"
              id="eventName"
              value={eventName}
              onChange={(e) => setEventName(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500"
            />
          </div>
          <div className="mb-4">
            <label htmlFor="eventDescription" className="block text-sm font-semibold mb-2">Event Description:</label>
            <textarea
              id="eventDescription"
              value={eventDescription}
              onChange={(e) => setEventDescription(e.target.value)}
              rows="4"
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500"
            ></textarea>
          </div>
          <div className="mb-4">
            <label htmlFor="selectedClub" className="block text-sm font-semibold mb-2">Select Club:</label>
            <select
              id="selectedClub"
              value={selectedClub}
              onChange={(e) => setSelectedClub(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500"
            >
              <option value="">Select a club</option>
              {ownedClubTitles.map((club, index) => (
              <option key={index} value={club}>
                {club}
              </option>
            ))}
            </select>
          </div>
          <div className="mb-4">
          <label htmlFor="eventDate" className="block text-sm font-semibold mb-2">Event Date:</label>
          <DatePicker
            id="eventDate"
            placeholderText="Choose a Date"
            selected={eventDate}
            onChange={(date) => setEventDate(date)}
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500"
          />
        </div>
          <button onClick={handleCreateEvent} className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md mr-2">Create Event</button>
          <button onClick={onClose} className="bg-gray-300 hover:bg-gray-400 text-gray-800 px-4 py-2 rounded-md">Cancel</button>
        </div>
      </div>
    );
}

export default CreateEventModal;