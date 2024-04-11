import axios from "axios";
import { useEffect , useState } from "react";
import Sidebar from "./Profile/Sidebar";
import ProfileInfoCards from "./Profile/ProfileInfoCards";
import CreateClubModal from "./Profile/CreateClubModal";
import CreateEventModal from "./Profile/CreateEventModal";
function Profile(){
    const auth = JSON.parse(localStorage.getItem("user"));
    const [userData, setUserData] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const handelCreateClub = ()=>{
        setIsModalOpen(true);
    }
    const handleCreateEvent = () => {
        setIsModalOpen(true);
      };
    const handleModalClose = () => {
        setIsModalOpen(false);
        
      };
    useEffect(()=>{
        async function fetch(){
            await axios.get("http://localhost:5000/api/v1/user/profile",{
                headers: {
                    "Authorization": "Bearer "  + auth.Token
                }
            })
            .then((res)=>{
                console.log(res.data)
                setUserData(res.data);
            })

        }
        fetch();
    },[])
    const ownedClubTitles = userData?.clubsOwnedTitle?.map((club) => Object.keys(club)[0]) ?? [];

    return (
        <div className="grid grid-cols-3 gap-4">
  <div className="col-span-1">
    <Sidebar   roles = {auth.roles} handleCreateClub={handelCreateClub} handelCreateEvent={handleCreateEvent}/>
  </div>
  <div className="col-span-2">
    {userData && (
      <div className="container mx-auto px-4 py-8">
        <h1 className="text-3xl font-semibold mb-4">
          {userData.name}'s Profile
        </h1>
        <ProfileInfoCards Data={userData} roles={auth.roles} />

        {/* Conditional rendering for owned club and event data */}
        {auth.roles.includes("ROLE_MODERATOR") && (
          <>
            <div className="mt-8">
              <h2 className="text-2xl font-semibold mb-4">
                Owned Clubs and Events
              </h2>
              <div className="flex flex-wrap gap-4">
                {userData.clubsOwnedTitle.map((club, index) => (
                  <div key={index} className="bg-white rounded-lg shadow-md p-2">
                    <h3 className="text-lg font-semibold mb-2">{Object.keys(club)[0]}</h3>
                    <ul className="list-disc list-inside">
                      {club[Object.keys(club)[0]].map((event, eventIndex) => (
                        <li key={eventIndex}>{event.title}</li>
                      ))}
                    </ul>
                  </div>
                ))}
              </div>
            </div>

            <div className="mt-8">
              <h2 className="text-2xl font-semibold mb-4">
                Clubs Joined
              </h2>
              <div className="flex flex-wrap gap-4">
                {userData.clubJoinedTitle.map((club, index) => (
                  <div key={index} className="bg-white rounded-lg shadow-md p-2">
                    <h3 className="text-lg font-semibold mb-2">{club.title}</h3>
                  </div>
                ))}
              </div>
            </div>

            <div className="mt-8">
              <h2 className="text-2xl font-semibold mb-4">
                Events Joined
              </h2>
              <div className="flex flex-wrap gap-4">
                {userData.eventsJoinedTitle.map((event, index) => (
                  <div key={index} className="bg-white rounded-lg shadow-md p-2">
                    <h3 className="text-lg font-semibold mb-2">{event.title}</h3>
                  </div>
                ))}
              </div>
            </div>
          </>
        )}

        {/* Render remaining data without owned club and event data */}
        {!auth.roles.includes("ROLE_MODERATOR") && (
          <>
            <div className="mt-8">
              <h2 className="text-2xl font-semibold mb-4">
                Remaining Data
              </h2>
             
                <h3 className="text-lg font-semibold mb-2">Clubs Joined</h3>
                <div className="flex flex-wrap gap-4">
                  {userData.clubJoinedTitle.map((club, index) => (
                    <div key={index} className="bg-white rounded-lg shadow-md p-2">
                      <h3 className="text-lg font-semibold mb-2">{club.title}</h3>
                    </div>
                  ))}
                </div>
              
             
                <h3 className="text-lg font-semibold mb-2">Events Joined</h3>
                <div className="flex flex-wrap gap-4">
                  {userData.eventsJoinedTitle.map((event, index) => (
                    <div key={index} className="bg-white rounded-lg shadow-md p-2">
                      <h3 className="text-lg font-semibold mb-2">{event.title}</h3>
                    </div>
                  ))}
                </div>
              
            </div>
          </>
        )}
      </div>
    )}
  </div>
  {isModalOpen && (
        <div className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-white p-4 rounded-lg shadow-md">
            <h2 className="text-xl font-semibold mb-4">Create Club Modal</h2>
           <CreateClubModal onClose={handleModalClose}/>
           <CreateEventModal onClose={handleModalClose} ownedClubTitles={ownedClubTitles} />
            <button onClick={handleModalClose} className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md">
              Close Modal
            </button>
          </div>
        </div>
      )}
</div>

      
    );
}

export default Profile;