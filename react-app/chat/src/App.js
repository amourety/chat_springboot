import React, { Component } from 'react';

import Header from './components/Header';
import Footer from './components/Footer';
import Router from './components/Router';

import RegistrationPage from './components/RegistrationPage/RegistrationPage'
import ChatPage from './components/ChatPage/ChatPage';
import LoginPage from './components/LoginPage/LoginPage'
import Page403 from './components/ErrorPages/Page403';
import Page404 from './components/ErrorPages/Page404';

export default class App extends Component {

  render(){
    return <div>
      <Header/>
      <Router/>
      <Footer/>
    </div>
  }
  componentDidMount(){
      let url = 'http://localhost:8080/chat/valid_token';
      fetch(url, {
        method: 'post',
        mode: 'cors',
        headers: {
          Accept: 'application/json',
         'Content-Type': 'application/json',
         'Access-Control-Allow-Origin':'*',
       },
        body:  JSON.stringify({ value: localStorage.getItem('AUTH')})
    })
    .then(resp => resp.json())
    .then(data => {
      this.setState({
          valid: data.valid
      })
    }
    ).catch(err => {
      // Do something for an error here
      console.log("Error Reading data " + err);
    });
    }
  }
