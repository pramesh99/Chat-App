import React from 'react';
import Cookies from 'universal-cookie';

function RejectFriend(props) {
    const [toUser, setToUser] = React.useState('');
    const [error, setError] = React.useState('');
    const [message, setMessage] = React.useState('');
    const [friends, setFriends] = React.useState([]);
    const [incomingRequests, setIncomingRequests] = React.useState([]);
    React.useEffect(() => {
        if (toUser === '') {
        // incoming
        getFriendRequests(1);
        // outgoing
        getFriendRequests(0);
        }
    }, [toUser]);
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
    function getFriendRequests(incoming){
        console.log("Retrieving friend requests...");
        const cookies = new Cookies();
        fetch(`/getFriendRequest?incoming=${incoming}`, {
            method: "GET",
            headers: {
                auth: cookies.get("auth"),
            }
        })
        .then(res => res.json())
        .then(apiRes => {
            console.log(apiRes);
            if (apiRes.status){
              if (incoming === 1) {
                setIncomingRequests(apiRes.data);
              } else {
                setFriends(apiRes.data);
              }
            }
        })
        .catch(e => {
          console.log(e);
        });
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
          <div>
          <div class= "flex-container">
            <div class="section-header">Friend Requests</div>
            {incomingRequests.map(request => (
              <div class="userNameWrap">
                <span >{request.fromId}</span>
              </div>
            ))}
          </div >
          <div class= "flex-container">
            <div class="section-header">Friends</div>
            {friends.map(friend => {
              let friendName = friend.fromId === props.loggedInUser
                ? friend.toId
                : friend.fromId;
              return (
              <div class="userNameWrap">
                <span>{friendName}</span>
              </div>
            )})}
          </div>
          </div>
        </div>
      );
}

export default RejectFriend;