import React from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const EventDetails = ({ clubName, startDate, endDate , description, eventName }) => {
  
  const auth = JSON.parse(localStorage.getItem("user"))

  const handelEventJoin = async()=>{
    try{   
    const response = await axios.post(`http://localhost:5000/api/v1/events/join/${eventName}`,null,{
      headers: { Authorization: 'Bearer ' + auth?.Token }
    })

    if(response.status == 200)
    {
      toast.success(response.data.Message)
    }
  }
  catch(error){
    if(error.response.status == 400){
      console.log(error)
      toast.error(error.response.data.Message)
    }
    else if(error.response.status == 409){
      console.log(error)
      toast.error(error.response.data.Message)
    }
  }
  }

  return(
    <div className="w-full md:w-4/5 mb-6 md:mb-0 pr-4 relative">
    <div className="bg-white shadow-md rounded-lg p-6">
      <div className="flex justify-between items-center mb-4">
        <h1 className="text-3xl font-bold">{eventName}</h1>
        <button className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md max-w-full" onClick={handelEventJoin}>Join Event</button>
      </div>
      <p className="text-lg text-gray-700 mb-6">{description}</p>
      
      <h3 className="text-lg font-semibold mb-4">Event Details</h3>
  
      <p className="text-sm text-gray-600 mb-1">Belongs to:</p>
      <p className="text-lg font-semibold mb-4">{clubName}</p>
  
      <div className="flex justify-start items-center">
        <p className="text-sm text-gray-600 mr-2">Start Date:</p>
        <p className="text-lg">{startDate}</p>
        <p className="text-sm text-gray-600 mx-2">End Date:</p>
        <p className="text-lg">{endDate}</p>
      </div>
      
    </div>
  </div>
  )
}


export default EventDetails;