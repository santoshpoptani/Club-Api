import React,{useEffect,useState} from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
function Club(){

    const[clubData, setClubData] =useState("")

    // const params = useParams();
    // const id = params.id;

    //Its called Destructuing the ID
    const {id} = useParams();

    useEffect(()=>{
        async function fetch(){
            await axios.get(`http://localhost:5000/api/v1/clubs/id/${id}`)
            .then((res)=>{
                console.log(res.data)
                setClubData(res.data);
            })
        }
        fetch();
    },[])


    return(<>
    <h1>club Page</h1>
    <h2>{clubData.title}</h2>
    <p>{clubData.contenet}</p>
    {clubData.eventList?.map(item =>(
        <div key={item.id}>
            <h3>{item.title}</h3>
            <p>{item.content}</p>
        </div>
    ))}
    </>)

}

export default Club