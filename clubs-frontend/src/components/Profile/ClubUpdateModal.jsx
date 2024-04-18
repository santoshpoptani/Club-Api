import  { useState , useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function ClubUpdateModal({onClose, club_Title}){
    const [clubTitle, setClubTitle] = useState('');
    const [clubDescription, setClubDescription] = useState('');
    const [clubId , setClubId] = useState('')
    const auth = JSON.parse(localStorage.getItem("user"))

    useEffect(()=>{
      function fetch(){
        axios.get("http://localhost:5000/api/v1/clubs/club-detail/",{
          params: {clubName:club_Title}
      }).then((res)=>{
        console.log(res.data)
        setClubTitle(res.data.title)
        setClubDescription(res.data.content)
        setClubId(res.data.id);
        
      })
      }
      fetch()
    },[])
  
    const handleUpdateClub = () => {
      axios.patch(`http://localhost:5000/api/v1/clubs/update/${clubId}`,{
        clubName:clubTitle,
        content:clubDescription
      },{
        headers :{ Authorization :'Bearer '+ auth?.Token}
      }).then((res) =>{
        console.log(res.data)
        toast.success("Club Updated Successfylly")
      })

      onClose(); // Close the modal after updating
    };
  
    return (
      <div>
        <h2 className="text-xl font-semibold mb-4">Update Club</h2>
        {/* Input box for club title */}
        <div className="mb-4">
          <input
            type="text"
            placeholder="Club Title"
            value={clubTitle}
            onChange={(e) => setClubTitle(e.target.value)}
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500"
          />
        </div>
        {/* Input box for club description */}
        <div className="mb-4">
          <textarea
            placeholder="Club Description"
            value={clubDescription}
            onChange={(e) => setClubDescription(e.target.value)}
            rows="4"
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500"
          ></textarea>
        </div>
        {/* Update and Cancel buttons */}
        <div>
          <button
            onClick={handleUpdateClub}
            className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md mr-2"
          >
            Update Club
          </button>
          <button
            onClick={onClose}
            className="bg-gray-300 hover:bg-gray-400 text-gray-800 px-4 py-2 rounded-md"
          >
            Cancel
          </button>
        </div>
      </div>
    );
}

export default ClubUpdateModal;