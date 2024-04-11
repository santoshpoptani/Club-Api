import React from 'react';

const EventDetails = ({ clubName, startDate, endDate , description, eventName }) => (
    <div className="w-full md:w-4/5 mb-6 md:mb-0 pr-4">
  <div className="bg-white shadow-md rounded-lg p-6">

    <h1 className="text-3xl font-bold mb-4">{eventName}</h1>
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

  
);

export default EventDetails;