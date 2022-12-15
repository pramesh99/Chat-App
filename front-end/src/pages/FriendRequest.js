import React from 'react';
import Cookies from 'universal-cookie';

function FriendRequest(props) {
    const [toUser, setToUser] = React.useState('');
    const [toId, setToId] = React.useState('');
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
            } else if (res.status === 404){
                setError('Error 404');
                setMessage(``);
            }
        })
        .then(() => setToUser(''))
        .catch(e => {
            console.log(e);
        })
    }

    function handleRemoveRequest() {

        const cookies = new Cookies();
        const friendRequestDto = {
            fromId: props.loggedInUser,
            toId: toId
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
                setMessage(`Friend ${toId} removed`);
                setError(``);
                
            } else if (res.status === 401) {
                setError(`Missing auth header`);
                setMessage(``);

            } else if (res.status === 402) {
                setError(`User ${toId} does not exist`);
                setMessage(``);

            } else if (res.status === 403) {
                setError(`Cannot unfriend self`);
                setMessage(``);    
            } 
        })
        .then(() => setToUser(''))
        .catch(e => {
            console.log(e);
        })
    }

    function handleAcceptRequest() {

        const cookies = new Cookies();
        const friendRequestDto = {
            fromId: props.loggedInUser,
            toId: toId
        };
        
        console.log(friendRequestDto);

        // makes api call to handle remove friend response
        fetch('/friendAccept', {
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
                setMessage(`Friend ${toId} Accepted`);
                setError(``); 
                
            } else if (res.status === 401) {
                setError(`Missing auth header`);
                setMessage(``);

            } else if (res.status === 402) {
                setError(`User ${toId} does not exist`);
                setMessage(``);

            } else if (res.status === 403) {
                setError(`Cannot unfriend self`);
                setMessage(``);    
            } 
        })
        .then(() => setToUser(''))
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
    
    function friendRequestAction(a,b){
      setToId(a);
      if(b===-1){
        handleRemoveRequest();
      }
      if(b===1){
        handleAcceptRequest();
      }
    }

    return ( // for some reason the first action doesn't seem to load properly 
        <div>
          <h1>Friend Request</h1>
          <div>
            <input class="text-input" value={toUser} onChange={(e) => setToUser(e.target.value)} />
            <button class="submit-button" onClick={handleRequest}>Send</button>
          </div>
          <div>{message}</div>
          <div>{error}</div>
          <div class= "flex-container">
            <div class="section-header">Friend Requests</div>
            {incomingRequests.map(request => (
              <div>
                <div class="userNameWrap">
                <span class="userName">{request.fromId}</span>
                </div>
                <button class="send-request"
                    onClick={() => {setToId(request.fromId);
                      handleAcceptRequest()
                      friendRequestAction(request.fromId, 1)//Bug in accept takes a reload of user for it to show up.
                    }}>
                  Accept
                </button>
                <button class="send-request"
                    onClick={() => {
                    friendRequestAction(request.fromId, -1)}}>
                  Decline
                </button>
              </div>
            ))}
          </div>
          <div class= "flex-container">
            <div class="section-header">Friends</div>
            {friends.map(friend => {
              let friendName = friend.fromId === props.loggedInUser
                ? friend.toId
                : friend.fromId;
              return (
              <div>
                <div class = "userNameWrap">
                    <span class="userName">{friendName}</span>
                </div>
                
                <button class="send-request"
                    onClick={() => { setToId(friendName);
                      friendRequestAction(friendName, -1)}}>
                  {friend.status ? "Remove Friend" : "Delete Pending Request"}
                </button>
              </div>
            )})}
          </div>
        </div>
      );
}

export default FriendRequest;