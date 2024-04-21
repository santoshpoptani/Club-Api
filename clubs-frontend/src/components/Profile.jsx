import axios from "axios";
import { useEffect , useState } from "react";
import Sidebar from "./Profile/Sidebar";
import ProfileInfoCards from "./Profile/ProfileInfoCards";
import CreateClubModal from "./Profile/CreateClubModal";
import CreateEventModal from "./Profile/CreateEventModal";
import EventupdateModal from "./Profile/EventUpdateModal";
import ClubUpdateModal from "./Profile/ClubUpdateModal";
import ChangePasswordModal from "./Profile/ChangePasswordModal";


function Profile(){
    const auth = JSON.parse(localStorage.getItem("user"));
    const [userData, setUserData] = useState(null);
    const [isCreateClubModalOpen, setIsCreateClubModalOpen] = useState(false);
    const [isCreateEventModalOpen, setIsCreateEventModalOpen] = useState(false);
    const [isEventUpdateModalOpen, setIsEventUpdateModalOpen] = useState(false);
    const [isClubUPdateModalOpen, setIsClubUpdateModalOpen] = useState(false);
    const [isChangePasswordModalOpen, setIsChangePassword] = useState(false);
    const [eventID , setEventID] = useState(null);
    const [clubTitle , setClubTitle] = useState(null);

    const handelChangePassword = ()=>{
      setIsChangePassword(true);
    }

    const hadelEventUpdateModal =(eventindex) => {
        setEventID(eventindex)
        setIsEventUpdateModalOpen(true);
    }

    const handelClubUpdateModal=(clubtitile)=>{
        setClubTitle(clubtitile)
        setIsClubUpdateModalOpen(true)
    }
  
    const handelCreateClub = ()=>{
        setIsCreateClubModalOpen(true);
    }
    const handleCreateEvent = () => {
        setIsCreateEventModalOpen(true);
      };
    const handleModalClose = () => {
        setIsCreateClubModalOpen(false);
        setIsCreateEventModalOpen(false);
        setIsClubUpdateModalOpen(false)
        setIsEventUpdateModalOpen(false)
        setIsChangePassword(false)
        
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
          <Sidebar
            roles={auth.roles}
            handleCreateClub={handelCreateClub}
            handelCreateEvent={handleCreateEvent}
            handelChangePassword={handelChangePassword}
          />
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
                        <div
                          key={index}
                          className="bg-white rounded-lg shadow-md p-2"
                        >
                          <h3 className="text-lg font-semibold mb-2">
                            <a className="cursor-pointer hover:text-blue-500" onClick={()=>handelClubUpdateModal(Object.keys(club)[0])}> {Object.keys(club)[0]}</a>
                          </h3>
                          <ul className="list-disc list-inside">
                            {club[Object.keys(club)[0]].map(
                              (event, eventIndex) => (
                                <li key={eventIndex}>
                                  <a className="cursor-pointer hover:text-blue-500" onClick={()=>hadelEventUpdateModal(event.id)}>{event.title}</a>
                                </li>
                              )
                            )}
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
                        <div
                          key={index}
                          className="bg-white rounded-lg shadow-md p-2"
                        >
                          <h3 className="text-lg font-semibold mb-2">
                            {club.title}
                          </h3>
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
                        <div
                          key={index}
                          className="bg-white rounded-lg shadow-md p-2"
                        >
                          <h3 className="text-lg font-semibold mb-2">
                            {event.title}
                          </h3>
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
                        <div
                          key={index}
                          className="bg-white rounded-lg shadow-md p-2"
                        >
                          <h3 className="text-lg font-semibold mb-2">
                            {club.title}
                          </h3>
                        </div>
                      ))}
                    </div>

                    <h3 className="text-lg font-semibold mb-2">
                      Events Joined
                    </h3>
                    <div className="flex flex-wrap gap-4">
                      {userData.eventsJoinedTitle.map((event, index) => (
                        <div
                          key={index}
                          className="bg-white rounded-lg shadow-md p-2"
                        >
                          <h3 className="text-lg font-semibold mb-2">
                            {event.title}
                          </h3>
                        </div>
                      ))}
                    </div>
                  </div>
                </>
              )}
            </div>
          )}
        </div>
        {isCreateClubModalOpen && (
          <div className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 flex justify-center items-center">
            <div className="bg-white p-4 rounded-lg shadow-md">
              <h2 className="text-xl font-semibold mb-4">Create Club Modal</h2>
              <CreateClubModal onClose={handleModalClose} />
              <button
                onClick={handleModalClose}
                className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md"
              >
                Close Modal
              </button>
            </div>
          </div>
        )}
        {isCreateEventModalOpen && (
          <div className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 flex justify-center items-center">
            <div className="bg-white p-4 rounded-lg shadow-md">
              <h2 className="text-xl font-semibold mb-4">Create Event Modal</h2>
              <CreateEventModal
                onClose={handleModalClose}
                ownedClubTitles={ownedClubTitles}
              />
              <button
                onClick={handleModalClose}
                className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md"
              >
                Close Modal
              </button>
            </div>
          </div>
        )}
        {isEventUpdateModalOpen && (
          <div className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 flex justify-center items-center">
            <div className="bg-white p-4 rounded-lg shadow-md">
              <h2 className="text-xl font-semibold mb-4">Update Event</h2>
             <EventupdateModal onClose={handleModalClose} eventid = {eventID}/>
            </div>
          </div>
        )}
        {isClubUPdateModalOpen && (
          <div className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 flex justify-center items-center">
            <div className="bg-white p-4 rounded-lg shadow-md">
             <ClubUpdateModal onClose={handleModalClose} club_Title = {clubTitle}/>
            </div>
          </div>
        )}
        {isChangePasswordModalOpen && (
          <div className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 flex justify-center items-center">
            <div className="bg-white p-4 rounded-lg shadow-md">
             < ChangePasswordModal onClose={handleModalClose} username = {userData.name} />
            </div>
          </div>
        )}
      </div>
    );
}

export default Profile;