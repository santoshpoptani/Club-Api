import React from 'react';

const UsersJoined = ({ users }) => {
console.log(users)
  return(
<div className="w-full md:w-1/4 pr-4 overflow-y-auto">
    <div className="bg-white shadow-md rounded-lg p-6">
      <h3 className="text-lg font-semibold mb-4">Users Joined in Event</h3>
      <ul>
        {users?.map(user => (
          <li key={user.id} className="mb-2">{user.username}</li>
        ))}
      </ul>
    </div>
  </div>
  )
}
    
;

export default UsersJoined;
