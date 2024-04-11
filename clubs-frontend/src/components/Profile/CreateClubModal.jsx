import { useState } from "react";
import axios from "axios";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
function CreateClubModal({onClose}){
    const auth = JSON.parse(localStorage.getItem("user"));
    const [clubName, setClubName] = useState('');
    const [clubContent, setClubContent] = useState('');
  
    const handleCreateClub = () => {
     axios.post(`http://localhost:5000/api/v1/clubs/new`,{
        clubName: clubName,
        clubContent: clubContent,
      }, {
        headers: {
          "Authorization": "Bearer " + auth.Token
        }
      }).then((res)=>{
        console.log(res.data);
        toast.success(`Club created successfully: ${clubName}`);
      })
      onClose();
    };
  
    return (
      <div className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 flex justify-center items-center">
        <div className="bg-white p-4 rounded-lg shadow-md">
          <h2 className="text-xl font-semibold mb-4">Create Club</h2>
          <div className="mb-4">
            <label htmlFor="clubName" className="block text-sm font-semibold mb-2">Club Name:</label>
            <input
              type="text"
              id="clubName"
              value={clubName}
              onChange={(e) => setClubName(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500"
            />
          </div>
          <div className="mb-4">
            <label htmlFor="clubContent" className="block text-sm font-semibold mb-2">Club Content:</label>
            <textarea
              id="clubContent"
              value={clubContent}
              onChange={(e) => setClubContent(e.target.value)}
              rows="4"
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500"
            ></textarea>
          </div>
          <button onClick={handleCreateClub} className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md mr-2">Create Club</button>
          <button onClick={onClose} className="bg-gray-300 hover:bg-gray-400 text-gray-800 px-4 py-2 rounded-md">Cancel</button>
        </div>
      </div>
    );
}

export default CreateClubModal;