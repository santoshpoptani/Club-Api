function ClubDetail({ clubData }){
    return (
        <div className="w-full md:w-3/4 p-4 mb-8 md:mb-0">
          <div className="grid grid-cols-1 gap-4">
            {clubData.eventList?.map((card) => (
              <div key={card.id} className="bg-white shadow-md rounded-lg p-6">
                <h2 className="text-xl font-bold mb-4">{card.title}</h2>
                <p className="text-gray-700">{card.content}</p>
              </div>
            ))}
          </div>
        </div>
      );
}

export default ClubDetail;