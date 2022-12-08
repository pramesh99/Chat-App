import React from 'react';
import Cookies from 'universal-cookie';

function RejectFriend(props) {
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

        // makes api call to handle remove friend response
        fetch('/friendRequestRemove', {
            method: 'POST',
            body: JSON.stringify(friendRequestDto),
            headers: {
                auth: cookies.get('auth'),
            }
        })
        .then(res => {
            console.log(res);

            // removal of friend logic checks
            if (res.status === 200) {
                setMessage(`Friend ${toUser} removed`);
                setError(``);
                
            } else if (res.status === 401) {
                setError(`Missing auth header`);
                setMessage(``);

            } else if (res.status === 402) {
                setError(`User ${toUser} does not exist`);
                setMessage(``);

            } else if (res.status === 403) {
                setError(`Cannot unfriend self`);
                setMessage(``);    
            } 
        })
        .catch(e => {
            console.log(e);
        })
    }


    return (
        <div>
          <h1>Remove Friend Request</h1>
          <div>
            <input value={toUser} onChange={(e) => setToUser(e.target.value)} />
            <button onClick={handleRequest}>Send</button>
          </div>
          <div>{error}</div>
          <div>{message}</div>
        </div>
      );
}

export default RejectFriend;