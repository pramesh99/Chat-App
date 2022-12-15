import React, { useState } from 'react';

function SearchFriends() {
  const [searchQuery, setSearchQuery] = useState('');
  const [friendsList, setFriendsList] = useState([]);
  const [filteredFriendsList, setFilteredFriendsList] = useState(friendsList);
  const handleSearch = (event) => {
    const searchQuery = event.target.value;
    setSearchQuery(searchQuery);

    // Filter the friends list to only include the given search query
    const filteredFriendsList = friendsList.filter((friend) => friend.name.includes(searchQuery));
    setFilteredFriendsList(filteredFriendsList);
  };

  return (
    <div>
      <input type="text" value={searchQuery} onChange={handleSearch} />
      <ul>
        {filteredFriendsList.map((friend) => (
          <li key={friend.id}>{friend.name}</li>
        ))}
      </ul>
    </div>
  );
}

export default SearchFriends;