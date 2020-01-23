import React from 'react';

export default function RegistrationPage() {
  return (
    <div className="container">
  <div className="main">
    <div className="maincentre">
      <form className="form-signin" onSubmit = {registration} method="post" style={{minWidth: '300px', maxWidth: '300px', paddingTop: '50px'}}>
        <h2  className="shadowText" style={{marginBottom: '2px'}}>Registration</h2>
        <input id="username" name="username" type="text" className="form-control" placeholder="Login" required autoFocus style={{marginBottom: '3px'}} />
        <input id="password" name="password" type="password" className="form-control" placeholder="Password" required />
        <br />
        <button className="btn btn-lg btn-dark btn-block" type="submit">Sign up</button>
        <a href="/login">to login page</a>
      </form>
      <br />
    </div>
  </div>
</div>
  )
}
function registration(e) {
  e.preventDefault();
  let url = 'http://localhost:8080/registration';
  fetch(url, {
    method: 'post',
    mode: 'cors',
    headers: {
     'Accept': 'application/json',
     'Content-Type': 'application/json',
     'Access-Control-Allow-Origin':'*',
   },
    body:  JSON.stringify({ username: document.getElementById('username').value, password: document.getElementById('password').value })
})
.then(data => {
    window.location.replace(window.location.protocol + '/login');
}
).catch(err => {
          // Do something for an error here
          console.log("Error Reading data " + err);
});


}
