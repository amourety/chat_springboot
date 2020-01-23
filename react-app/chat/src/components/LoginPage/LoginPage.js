import React from 'react';

export default function LoginPage() {
  return(
    <div className="container">
  <div className="main">
    <div className="maincentre">
      <form onSubmit = {login} className="form-signin" style={{paddingTop: '50px', minWidth: '300px', maxWidth: '300px'}}>
        <h2 className="shadowText" style={{marginBottom: '2px'}}>Please sign in</h2>
        <input id="login" name="login" type="text" className="form-control" placeholder="Login" required autoFocus style={{marginBottom: '3px'}} />
        <input id="password" name="password" type="password" className="form-control" placeholder="Password" required />
        <br />
        <button className="btn btn-lg btn-dark btn-block" type="submit">Sign in</button>
        <a href="/registration">to registration page</a>
      </form>
      <br />
    </div>
  </div>
</div>
  )
}
function login(e) {
  e.preventDefault();
  let url = 'http://localhost:8080/login';
  fetch(url, {
    method: 'post',
    mode: 'cors',
    headers: {
     'Accept': 'application/json',
     'Content-Type': 'application/json',
     'Access-Control-Allow-Origin':'*'
   },
    body:  JSON.stringify({login: document.getElementById('login').value, password: document.getElementById('password').value })
}).then(response => response.json())
.then(data =>{
  localStorage.setItem('AUTH', data.value)
  localStorage.setItem('USERNAME', document.getElementById('login').value)
  fetch('http://localhost:8080/chat/alivechat', {
    method: 'post',
    mode: 'cors',
    headers: {
     'Accept': 'application/json',
     'Content-Type': 'application/json',
     'Access-Control-Allow-Origin':'*'
   }
  }).then(response => response.json())
    .then(data =>{
      console.log(data);
      localStorage.setItem('CHAT_ID', data)
      window.location.replace(window.location.protocol + '/chat/' + data);
    })
}
)
}
