import React from 'react';
import Message from './Message';


class MessageContainer extends React.Component {
  constructor(props){
    super(props);
    this.state = {
      MessageLists: [],
    }
  }

  componentDidMount() {
    let url = 'http://localhost:8080/chat/' + localStorage.getItem('CHAT_ID') + '/messages';
    fetch(url, {
      method: 'get',
      mode: 'cors',
      headers:{
        'AUTH': localStorage.getItem('AUTH'),
        'Access-Control-Allow-Origin':'*'
      },
    }).then(response => response.json())
    .then(data => {
      this.setState({
        MessageLists: data
      })
      //auto-scrolling
      document.getElementById('messages').scroll({
        top: 100000,
        left: 0,
        behavior: 'smooth'
      });
    }
    )
  }
  render(){
    return <MessageList messages={this.state.MessageLists}/>
  }
}


class MessageList extends React.Component {
  render(){
    var messages = []
    var messages2 = []
    messages = Array.from(this.props.messages);
    messages.map((m,index) => messages2.push(<Message item= {m} key = {index}/>));
    return messages2;
  }
}

export default MessageContainer
