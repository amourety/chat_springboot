import React, { Component } from 'react';

import RegistrationPage from './RegistrationPage/RegistrationPage'
import ChatPage from './ChatPage/ChatPage';
import LoginPage from './LoginPage/LoginPage'
import Page403 from './ErrorPages/Page403';
import Page404 from './ErrorPages/Page404';

class Router extends Component {

  render() {
    var isNotFound = 0;
    const url = window.location.pathname;
    if (url === "/registration") {
      window.document.title = "Registration";
      return (
            <RegistrationPage />
          );
    } else {
      isNotFound++;
    }
    if (url.split("/",2)[1] === "chat" && url.split("/",3)[2] >= 1 && url.split("/",3)[2] <= 1000) {
      if (localStorage.getItem('CHAT_ID') !== url.split("/",3)[2]){
        localStorage.setItem('CHAT_ID',url.split("/",3)[2])
      }
      if (localStorage.getItem('AUTH') != null) {
        if(this.props.valid === "valid") {
        window.document.title = "Chat";
        return (
          <ChatPage />
          );
        }
        else {
          if(this.props.valid === "invalid"){
            return (
              <Page403 />
            )
          }
        }
      } else {
        window.location.replace(window.location.protocol + '/login');
      }
    } else {
      isNotFound++;
    }
    if (url.split("/",2)[1] === "chatroom") {
      if (localStorage.getItem('CHAT_ID') !== url.split("/",3)[2]){
        localStorage.setItem('CHAT_ID',url.split("/",3)[2])
      }
      if (localStorage.getItem('AUTH') != null) {
        if(this.props.valid === "valid") {
        window.document.title = "Chat";
        return (
          <ChatPage />
          );
        }
        else {
          if(this.props.valid === "invalid"){
            return (
              <Page403 />
            )
          }
        }
      } else {
        window.location.replace(window.location.protocol + '/login');
      }
    } else {
      isNotFound++;
    }
    if (url === "/login" || url === "/") {
      window.document.title = "Login";
      return (
          <LoginPage />
          );
        }
        else {
          isNotFound++;
        }

    if (isNotFound === 4) {
          return (
            <Page404 />
          )
        }

        return <div> </div>
}
}
export default Router;
