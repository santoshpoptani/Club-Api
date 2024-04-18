import React,{useEffect,useState} from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import ClubDetail from './Card/ClubDetail';
import UsersJoined from './Card/UserJoined';
function Club(){

    const[clubData, setClubData] =useState("")
    const auth = JSON.parse(localStorage.getItem("user"))

    // const params = useParams();
    // const id = params.id;

    //Its called Destructuing the ID
    const {id} = useParams();

    useEffect(()=>{
        async function fetch(){
            await axios.get(`http://localhost:5000/api/v1/clubs/club-detail/`,{
              params:{
                clubId:id
              }
            },{
              headers :{ Authorization :'Bearer '+ auth?.Token}
          })
            .then((res)=>{
                console.log(res.data)
                setClubData(res.data);
            })
        }
        fetch();
    },[])


    return( <div className="bg-gray-200 container mx-auto px-4 py-8">
    <img
      src="https://placehold.co/600x400"
      alt="Club Image"
      className="mx-auto mb-6 rounded-lg shadow-md"
      style={{ maxHeight: '500px', objectFit: 'cover', width: '100vw' }}
    />
    <div className="flex justify-between items-center mb-4">
      <h1 className="text-3xl font-bold">{clubData.title}</h1>
      <button className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md">Join Club</button>
    </div>
    <p className="text-lg text-gray-700 mb-6">{clubData.content}</p>
    <div className="flex flex-col md:flex-row">
      <ClubDetail clubData={clubData} />
      <UsersJoined users={clubData.users} />
    </div>
  </div>
  
)

}

export default Club