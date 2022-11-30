import React from 'react';
import Cookies from 'universal-cookie';

function FriendRequest(props) {
    const [toUser, setToUser] = React.useState('');
    const [error, setError] = React.useState('');
    const [message, setMessage] = React.useState('');

    function handleRequest() {

        const cookies = new Cookies();
        const friendRequestDto = {
            fromId: props.loggedInUser,
            toId: toUser
        };
        
        console.log(friendRequestDto);

        // make friend request api call and handle response
        fetch('/friendRequest', {
            method: 'POST',
            body: JSON.stringify(friendRequestDto),
            headers: {
                auth: cookies.get('auth'),
            }
        })
        .then(res => {
            console.log(res);

            // friend request successful
            if (res.status === 200) {
                setMessage(`Friend request sent to ${toUser}`);
                setError(``);
                
            } else if (res.status === 401) {
                setError(`Missing auth header`);
                setMessage(``);

            } else if (res.status === 402) {
                setError(`User ${toUser} does not exist`);
                setMessage(``);

            } else if (res.status === 403) {
                setError(`Can't send request to self`);
                setMessage(``);    
            } 
        })
        .catch(e => {
            console.log(e);
        })
    }


    return (
        <div>
          <h1>Friend Request</h1>
          <div>
            <input value={toUser} onChange={(e) => setToUser(e.target.value)} />
            <button onClick={handleRequest}>Send</button>
          </div>
          <div>{error}</div>
          <div>{message}</div>
        </div>
      );
}

export default FriendRequest;