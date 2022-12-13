import React from 'react';
import Cookies from 'universal-cookie';

function FriendRequest(props) {
    const [toUser, setToUser] = React.useState('');
    const [error, setError] = React.useState('');
    const [message, setMessage] = React.useState('');

    const [friends, setFriends] = React.useState([]);
    const [incomingRequests, setIncomingRequests] = React.useState([]);

    React.useEffect(() => {
        if (toUser == '') {
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

    }

    return (
        <div>
          <h1>Friend Request</h1>
          <div>
            <input class="text-input" value={toUser} onChange={(e) => setToUser(e.target.value)} />
            <button class="submit-button" onClick={handleRequest}>Send</button>
          </div>
          <div>{message}</div>
          <div>{error}</div>
          <div>
            <div class="section-header">Friend Requests</div>
            {incomingRequests.map(request => (
              <div>
                <span class="userName">{request.fromId}</span>
                <button class="accept-request"
                    onClick={() => friendRequestAction(request.fromId, 1)}>
                  Accept
                </button>
                <button class="decline-request"
                    onClick={() => friendRequestAction(request.fromId, -1)}>
                  Decline
                </button>
              </div>
            ))}
          </div>
          <div>
            <div class="section-header">Friends</div>
            {friends.map(friend => {
              let friendName = friend.fromId == props.loggedInUser
                ? friend.toId
                : friend.fromId;
              return (
              <div>
                <span class="userName">{friendName}</span>
                <button class="decline-request"
                    onClick={() => friendRequestAction(friendName, -1)}>
                  {friend.status ? "Remove Friend" : "Delete Pending Request"}
                </button>
              </div>
            )})}
          </div>
        </div>
      );
}

export default FriendRequest;