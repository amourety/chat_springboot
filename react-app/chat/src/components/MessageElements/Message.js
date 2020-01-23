import React from 'react';

export default class Message extends React.Component {
  render (){
    if(this.props.item.username === localStorage.getItem('USERNAME')){
      return (
        <div className="blockMessage">
        <li className="list-group-item rightMessages">
        <div className="msgright">
        <img alt = "" className="mailImage" src="https://www.freepngimg.com/thumb/telephone/68708-ipma-message-icon-email-telephone-png-image-high-quality.png" /><span className="chatNickName"> {this.props.item.username}</span><p className="chatMessageTime"> {this.props.item.time.split('.')[0].split(' ')[1]} </p> <p className="textMessage"> {this.props.item.text}</p>
        </div>
        </li>
        </div>
      )
    } else {
    return (
      <div className="blockMessage">
      <li className="list-group-item leftMessages">
      <div className="msgleft">
      <img alt = ""  className="mailImage" src="https://www.freepngimg.com/thumb/telephone/68708-ipma-message-icon-email-telephone-png-image-high-quality.png" /><span className="chatNickName"> {this.props.item.username}</span><p className="chatMessageTime"> {this.props.item.time.split('.')[0].split(' ')[1]} </p> <p className="textMessage"> {this.props.item.text}</p>
      </div>
      </li>
      </div>
    );
  }
}
}
