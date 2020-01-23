import React, { Component } from 'react';

import Router from './components/Router';
import Header from './components/Header';
import Footer from './components/Footer';

class App extends Component {
  constructor(props) {
        super(props);

        this.state = {
            valid: ""
        };
  }
  render() {
    return (
        <div>
          <Header />
          <Router valid = {this.state.valid}/>
          <Footer />
        </div>
    );
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

export default App;
