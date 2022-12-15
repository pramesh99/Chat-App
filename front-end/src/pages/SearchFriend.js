import React, { useState } from 'react';
import Cookies from "universal-cookie";

function SearchFriends() {
    const cookies = new Cookies();

    const [prevSearchedFriends, setPrevSearchedFriends] = useState(cookies.get('prevSearchedFriends') || []);
    const [searchQuery, setSearchQuery] = useState('');
    const [friendsList, setFriendsList] = useState([]);
    const [filteredFriendsList, setFilteredFriendsList] = useState(friendsList);

    // Retrieve the list of friends from the API
    React.useEffect(() => {
        fetch('/api/friends')
            .then(res => res.json())
            .then(friendsList => setFriendsList(friendsList))
            .catch(err => console.log(err));
    }, []);

    const handleSearch = (event) => {
        const searchQuery = event.target.value;
        setSearchQuery(searchQuery);

        // Filter the friends list to only include the given search query
        const filteredFriendsList = friendsList.filter((friend) => friend.name.includes(searchQuery));
        setFilteredFriendsList(filteredFriendsList);
    };

    // Show a list of previously searched friends
    if (!prevSearchedFriends.includes(searchQuery) && searchQuery !== '') {
        setPrevSearchedFriends([...prevSearchedFriends, searchQuery]);
        cookies.set('prevSearchedFriends', [...prevSearchedFriends, searchQuery], { path: '/' });
    };

    return (
        <div>
            <input type="text" value={searchQuery} onChange={handleSearch} />
            <ul>
                {filteredFriendsList.length > 0 ? (
                    filteredFriendsList.map((friend) => (
                        <li key={friend.id}>{friend.name}</li>
                    ))
                ) : (
                    <li>No matching friends found.</li>
                )}
            </ul>
            <div>
                <h3>Previously searched friends:</h3>
                <ul>
                    {prevSearchedFriends.map(friend => (
                        <li key={friend}>{friend}</li>
                    ))}
                </ul>
            </div>
        </div>
    );
}

export default SearchFriends;