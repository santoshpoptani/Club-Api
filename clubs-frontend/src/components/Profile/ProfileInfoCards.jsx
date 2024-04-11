
function ProfileInfoCards({Data, roles}){
    const isModerator = roles.includes("ROLE_MODERATOR");


    return (
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {isModerator && (
          <>
            <div className="bg-blue-200 rounded-lg p-4 relative">
              <h3 className="text-xl font-semibold mb-2">Clubs Joined</h3>
              <p>{Data.clubsJoined}</p>
            </div>
            <div className="bg-green-200 rounded-lg p-4 relative">
              <h3 className="text-xl font-semibold mb-2">Events Joined</h3>
              <p>{Data.eventsJoined}</p>
            </div>
            <div className="bg-yellow-200 rounded-lg p-4 relative">
              <h3 className="text-xl font-semibold mb-2">Owned Clubs</h3>
              <p>{Data.ownedClub}</p>
            </div>
            <div className="bg-pink-200 rounded-lg p-4 relative">
              <h3 className="text-xl font-semibold mb-2">Owned Events</h3>
              <p>{Data.ownedEvent}</p>
            </div>
          </>
        )}
        {!isModerator && (
          <>
            <div className="bg-blue-200 rounded-lg p-4 relative">
              <h3 className="text-xl font-semibold mb-2">Clubs Joined</h3>
              <p>{Data.clubsJoined}</p>
            </div>
            <div className="bg-green-200 rounded-lg p-4 relative">
              <h3 className="text-xl font-semibold mb-2">Events Joined</h3>
              <p>{Data.eventsJoined}</p>
            </div>
          </>
        )}
      </div>
    );
}

export default ProfileInfoCards;