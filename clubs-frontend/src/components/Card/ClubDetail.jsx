import { Link } from 'react-router-dom';
function ClubDetail({ clubData }){
  return (
    <div className="w-full md:w-3/4 p-4 mb-8 md:mb-0">
      <div className="grid grid-cols-1 gap-4">
        {clubData.eventList?.map((card) => (
          <div key={card.id} className="bg-white shadow-md rounded-lg p-6 relative">
            <h2 className="text-xl font-bold mb-4">{card.title}</h2>
            <p className="text-gray-700">{card.content}</p>
            <div className="flex justify-end items-center mt-4">
              <Link to={`/event/${card.id}`} className="absolute top-1/2 right-2 transform -translate-y-1/2 bg-blue-500 hover:bg-blue-600 text-white px-3 py-1 rounded-md mr-4">
                View Details
              </Link>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default ClubDetail;