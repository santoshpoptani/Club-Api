import { Link } from "react-router-dom";
function Sidebar({roles , handleCreateClub , handelCreateEvent ,handelChangePassword }){
    
return(<>
  <div className="fixed left-0 h-full w-64 bg-gradient-to-r from-blue-900 to-blue-800 text-white flex flex-col items-center justify-start py-6 transition-all duration-300 shadow-lg">
  <div className="mb-4 transform transition-transform duration-300 hover:scale-105">
    <img
      src="https://placehold.co/600x400"
      alt="Profile"
      className="w-24 h-24 rounded-full object-cover border-4 border-white hover:border-blue-300"
    />
    <p className="mt-2 text-lg font-semibold">username</p>
  </div>
  {/* Sidebar links */}
  <ul className="text-white text-lg">
    {roles.includes("ROLE_MODERATOR") && (
      <>
        <li className="mb-4 hover:text-blue-300 transition-colors duration-300 cursor-pointer">
        <Link onClick={handleCreateClub} >Create Club</Link>
        </li>
        <li className="mb-4 hover:text-blue-300 transition-colors duration-300 cursor-pointer"> 
        <Link onClick={handelCreateEvent}>Create Event</Link>
        </li>
      </>
    )}
    <li className="hover:text-blue-300 transition-colors duration-300 cursor-pointer">
      <Link onClick={handelChangePassword}>Change Password</Link>
      </li>

  </ul>
</div>

</>)

}

export default Sidebar;