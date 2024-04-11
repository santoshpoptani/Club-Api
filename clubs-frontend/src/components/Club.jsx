import React,{useEffect,useState} from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import ClubDetail from './Card/ClubDetail';
function Club(){

    const[clubData, setClubData] =useState("")
    const auth = JSON.parse(localStorage.getItem("user"))

    // const params = useParams();
    // const id = params.id;

    //Its called Destructuing the ID
    const {id} = useParams();

    useEffect(()=>{
        async function fetch(){
            await axios.get(`http://localhost:5000/api/v1/clubs/club-detail/${id}`,{
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
    <h1 className="text-3xl font-bold mb-4">{clubData.title}</h1>
    <p className="text-lg text-gray-700 mb-6">{clubData.content}</p>
    <div className="flex flex-col md:flex-row md:space-x-6">
    <ClubDetail clubData={clubData} />
      <div className="w-full md:w-1/4 p-4 bg-gray-100 rounded-lg">
        {/* Right Partition for Other Content */}
        {/* Add your other content here */}
      </div>
    </div>
  </div>
  
  
)

}

export default Club