import React from 'react';
import { useNavigate } from 'react-router-dom';


function CreateAccount() {
  const navigate = useNavigate();
  const [userName, setUserName] = React.useState('');
  const [password, setPassword] = React.useState('');
  const [error, setError] = React.useState('');
  const [status, setStatus] = React.useState(false);

  function handleSubmit() {
    const userDto = {
      userName: userName,
      password: password,
    };
    fetch('/createUser', {
      method: 'POST',
      body: JSON.stringify(userDto),
    })
      .then(res => res.json())
      .then(apiRes => {
        console.log(apiRes);
        if (!apiRes.status) {
          setError(apiRes.message);
        } else {
          navigate('/login');
        }
      })
      .catch(e => {
        console.log(e);
      })
  }

  return (
    <div>
      <h1> Create Account Page</h1>
      <div>
        <input value={userName} onChange={(e) => setUserName(e.target.value)} />
        <input value={password} onChange={(e) => setPassword(e.target.value)} type="password" />
        <button onClick={handleSubmit}>Login</button>
      </div>
      <div>{error}</div>
    </div>
  );
}

export default CreateAccount;