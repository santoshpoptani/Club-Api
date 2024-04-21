import { useState } from "react";
import axios from "axios";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {useNavigate } from "react-router-dom";

function ChangePasswordModal({ onClose , username }){
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmNewPassword, setConfirmNewPassword] = useState('');

    const auth = JSON.parse(localStorage.getItem("user"))
    const navigate = useNavigate();
  
    const handleChangePassword = () => {
        if (newPassword !== confirmNewPassword) {
            alert("New password and confirm new password do not match");
            return;
    }
    else{
        axios.post(`http://localhost:5000/api/v1/user/changepassword`,{
            username:username,
            oldPassword : oldPassword,
            newPassword :newPassword
        
          },{
            headers:{
            "Authorization": "Bearer " + auth.Token
          }}).then((res)=>{
            toast.success(res.data.message +"\n Login in Again...")
            localStorage.removeItem("user");
            navigate("/")
            console.log(res.data)
          })
          .catch((err)=>{
            toast.error(err.response.data.message)
          })
    }
      
      onClose(); // Close the modal after changing password
    };
  
    return (
      <div className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 flex justify-center items-center">
        <div className="bg-white p-4 rounded-lg shadow-md">
          <h2 className="text-xl font-semibold mb-4 text-red-500">Do you really want to change the password?</h2>
          <div className="mb-4">
            <label htmlFor="oldPassword" className="block text-sm font-semibold mb-2">Old Password:</label>
            <input
              type="password"
              id="oldPassword"
              value={oldPassword}
              onChange={(e) => setOldPassword(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500"
            />
          </div>
          <div className="mb-4">
            <label htmlFor="newPassword" className="block text-sm font-semibold mb-2">New Password:</label>
            <input
              type="password"
              id="newPassword"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500"
            />
          </div>
          <div className="mb-4">
            <label htmlFor="confirmNewPassword" className="block text-sm font-semibold mb-2">Confirm New Password:</label>
            <input
              type="password"
              id="confirmNewPassword"
              value={confirmNewPassword}
              onChange={(e) => setConfirmNewPassword(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500"
            />
          </div>
          <div className="flex justify-end">
            <button
              onClick={handleChangePassword}
              className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md mr-2"
            >
              Change
            </button>
            <button
              onClick={onClose}
              className="bg-gray-300 hover:bg-gray-400 text-gray-800 px-4 py-2 rounded-md"
            >
              Cancel
            </button>
          </div>
        </div>
      </div>
    );
}

export default ChangePasswordModal;