import React from 'react';


class SubscriberContainer extends React.Component {
  constructor(props){
    super(props);
    this.state = {
      subscriberLists: [],
    }
  }

  componentDidMount() {
    let url = 'http://localhost:8080/chat/' + localStorage.getItem('CHAT_ID') + '/subscribers';
    fetch(url, {
      method: 'post',
      mode: 'cors',
      headers:{
        'AUTH': localStorage.getItem('AUTH'),
        'Access-Control-Allow-Origin':'*'
      },
    }).then(response => response.json())
    .then(data => {
      this.setState({
        subscriberLists: data
      })
    }
    )
  }
  render(){
    return <SubscriberList subscribers={this.state.subscriberLists}/>
  }
}


class SubscriberList extends React.Component {
  render(){
    var messages = []
    var messages2 = []
    messages = Array.from(this.props.subscribers);
    messages.map((m,index) => messages2.push(<Subscribe item= {m} key = {index}/>));
    return messages2;
  }
}

class Subscribe extends React.Component {
  render (){
    return <div>
    <h1> {this.props.item.username} </h1>
    </div>
  }
}
export default SubscriberContainer
