import React from 'react';
import { useParams } from 'react-router-dom';
function Club(){

    // const params = useParams();
    // const id = params.id;
    
    //Its called Destructuing the ID
    const {id} = useParams();

    return(<>
    <h1>club Page</h1>
    <h2>Club Id id {id}</h2>
    </>)

}

export default Club