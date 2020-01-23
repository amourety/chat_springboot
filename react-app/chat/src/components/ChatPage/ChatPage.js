import React from 'react';
import ReactDOM from 'react-dom'
import MessageContainer from '../MessageElements/ListMessages';
import SubscriberContainer from '../SubscribeComponents/Subscribe';
import Subscribe from '../SubscribeComponents/SubscribeElement';
import WSMessage from '../MessageElements/WSMessage';
import ReactDOMServer from 'react-dom/server';
import SockJS from 'sockjs-client';
import $ from 'jquery';
export default class ChatPage extends React.Component {
  render(){

  return (
    <div className="container" onLoad={connect()}>
  <div className="row">
    <div className="col-lg-3">
    <h2 className="shadowText4">IN ROOM</h2>
    <ul id="subscribers" className="list-group">
    <SubscriberContainer />
    </ul>
    </div>
    <div className="col-lg-6 col-md-12 col-sm-12" id="messager">
      <h2 className="shadowText">
        ROOM#{localStorage.getItem('CHAT_ID')}|{localStorage.getItem('USERNAME')} </h2>
      <ul id="messages" className="list-group">
        <MessageContainer />
      </ul>
      <div className="container">
        <div className="row" style={{borderTop: '2px solid', paddingTop: '10px'}}>
          <div className="col-1" style={{ padding: 0,paddingTop: '5px', paddingLeft: '5px'}}>
          </div>
          <div className="col-10">
            <div id = "textInput" className="input-group mb-3">
                <span id="span" tooltip="" flow="down"><input id="message" autoComplete="off" type="text" className="form-control" placeholder="Write your message..." aria-label="Recipient's username" aria-describedby="basic-addon2" /></span>
              <div className="input-group-append" style={{width: "10%" }}>
                <button id="send_button" onClick ={sendMessage} type="button" className="btn btn-outline-dark">Send
                </button>
              </div>
            </div>
          </div>
          <div className="col-1" style={{ padding: 0,paddingTop: '5px', paddingLeft: '5px'}}>
            <div className="windows8" id="wheel">
              <div className="wBall" id="wBall_1">
                <div className="wInnerBall" />
              </div>
              <div className="wBall" id="wBall_2">
                <div className="wInnerBall" />
              </div>
              <div className="wBall" id="wBall_3">
                <div className="wInnerBall" />
              </div>
              <div className="wBall" id="wBall_4">
                <div className="wInnerBall" />
              </div>
              <div className="wBall" id="wBall_5">
                <div className="wInnerBall" />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div style={{height: '20px'}} />
    </div>
    <div className="col-lg-3">
    <h2 className="shadowText4">SEARCH ROOM</h2>

    <ul id="searcher" className="list-group">
    <li className="list-group-item nopadding">
    <div id = "textInput" className="input-group mb-3">
        <span id="span" tooltip="" flow="down"><input id="searcherBox" autoComplete="off" type="text" className="form-control" placeholder="Write chat id..." aria-label="Recipient's username" aria-describedby="basic-addon2" /></span>
      <div className="input-group-append" style={{width: "10%" }}>
        <button id="send_button" onClick ={searching} type="button" className="btn btn-outline-dark">Search
        </button>
      </div>
    </div>
    </li>
    <li className="list-group-item nopadding">
    <h2 className="shadowText5"> Maximum chat id: 1000</h2>
    <h2 className="shadowText5"> Minimum chat id: 1</h2>
    </li>
    </ul>
    </div>
  </div>

</div>
  )
}

componentDidMount(){
  document.getElementById('leftRouter').addEventListener('click',left)
  document.getElementById('rightRouter').addEventListener('click',right)
  if (localStorage.getItem('CHAT_ID') == 1000) {
    document.getElementById('rightRouter').style.visibility = "hidden"
  }
  if (localStorage.getItem('CHAT_ID') == 1) {
    document.getElementById('leftRouter').style.visibility = "hidden"
  }
  document.getElementById('message').addEventListener('keypress', e => {
          if (document.getElementById('send_button').style.backgroundColor === "rgb(255, 46, 46)") {
              document.getElementById('send_button').style.backgroundColor = "#fff"
          }
          if (e.charCode === 13) {
              sendMessage($('#message').val());
          }
          if (document.getElementById('message').value.length >= 0) {
          document.getElementById('span').setAttribute('tooltip',document.getElementById('message').value);
          }
      });
  document.getElementById('message').addEventListener('keydown', e => {
          if (document.getElementById('send_button').style.backgroundColor == "rgb(255, 46, 46)") {
            document.getElementById('send_button').style.backgroundColor = "#fff"
          }
          if (document.getElementById('message').value.length === 0){
              document.getElementById('span').setAttribute('tooltip',"");
          }
          if (document.getElementById('message').value.length > 0) {
              document.getElementById('span').setAttribute('tooltip',document.getElementById('message').value);
          }
    });
}
}

var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    // создаем объект SockJs
    var socket = new SockJS('http://localhost:8080/chat');
    var Stomp = require('stompjs');
    // создаем stomp-клиент поверх sockjs
    stompClient = Stomp.over(socket);

    // при подключении
    stompClient.connect({"USERNAME":localStorage.getItem('USERNAME')}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        var user = {
          "username": localStorage.getItem('USERNAME'),
          "chatId": localStorage.getItem('CHAT_ID')
        }

        stompClient.send('/messageStomp/register', {}, JSON.stringify(user))
        // подписываемся на url
        stompClient.subscribe('/chat/' + localStorage.getItem('CHAT_ID') + '/subscribers', function (message) {
          //ReactDOM.render(<SubscriberContainer />, document.getElementById('subscribers'))
          newSubscriber(JSON.parse(message.body))
        });
        // подписываемся на url
        stompClient.subscribe('/chat/' + localStorage.getItem('CHAT_ID'), function (message) {
          showMessage(JSON.parse(message.body))
        });
        let url = 'http://localhost:8080/chat/' + localStorage.getItem('CHAT_ID') + '/subscribers';
        // fetch(url, {
        //   method: 'POST',
        //   mode: 'cors',
        //   headers:{
        //     'AUTH': localStorage.getItem('AUTH'),
        //     'Access-Control-Allow-Origin':'*'
        //   },
        // }).then(response => response.json())
        // .then(data => {
        // }
        // )
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

// отправка
function sendMessage() {

    if($('#message').val().trim() != ""){
    //start animation wheel
    animationWheel();
    //drop tooltip value
    document.getElementById('span').setAttribute('tooltip',"");
        let message = {
            "username": localStorage.getItem('USERNAME'),
            "text": $('#message').val(),
            "chatId": localStorage.getItem('CHAT_ID')
        };

        //drop value of input
        $('#message').val("");
        // отправляем на определенный url
        stompClient.send("/messageStomp/message", {}, JSON.stringify(message));
    } else {
      document.getElementById('send_button').style.backgroundColor = "rgb(255, 46, 46)";
    }
}
function searching(){
    let chatId = $('#searcherBox').val();
    if(chatId > 0 && chatId <= 1000) {
      window.location.replace(window.location.protocol + '/chat/' + chatId);
    }
}

function newSubscriber(response) {
  //adding message to list

  document.getElementById('subscribers').innerHTML = ''
  console.log(response)
  let html = "";
  for(var i = 0; i < response.length; i++) {
    html += '<li class="list-group-item blockSub">'
    html += '<div class="blockSubscriber">'
    html += '<div>'
    html += '<h1 class="shadowText3">' + response[i] + '</h1>'
    html += '</div>'
    html += '</div>'
    html += '</li>'
  }
  document.getElementById('subscribers').innerHTML = html;
}
function showMessage(response) {

          //adding message to list
          let li = document.createElement('li');
          let div = document.createElement('div');
          div.className = "blockMessage";
          li.className = "list-group-item leftMessages";
          if(response.username === localStorage.getItem('USERNAME')){
            li.className = "list-group-item rightMessages";
          }
          li.innerHTML = ReactDOMServer.renderToStaticMarkup(<WSMessage item={response} />);
          div.appendChild(li);
          document.getElementById('messages').append(div);

          //auto-scrolling
          autoscrolling();

          //stop wheel
          setTimeout(stopAnimationWheel,  1000);
}

function animationWheel(){

  //start animation wheel
  $('#wheel').show("slow");
}
function stopAnimationWheel(){

  //stop animation wheel
  $('#wheel').hide()
}
function autoscrolling(){
  document.getElementById('messages').scroll({
    top: 100000,
    left: 0,
    behavior: 'smooth'
  });
}


function left(){
    if (localStorage.getItem('CHAT_ID') !== 1) {
      var nextChatId = parseInt(localStorage.getItem('CHAT_ID')) - 1;
      window.location.replace(window.location.protocol + '/chat/' + nextChatId);
    }
}
function right(){
    if (localStorage.getItem('CHAT_ID') !== 10) {
      var nextChatId = parseInt(localStorage.getItem('CHAT_ID')) + 1;
      window.location.replace(window.location.protocol + '/chat/' + nextChatId);
    }
}


// function sendMessage() {
//
//    $('#wheel').show();
//    let text = document.getElementById('message').value;
//    document.getElementById('message').value = "";
//    let body = {
//        text: text
//    };
//
//    $.ajax({
//        url: 'http://localhost:8080/messages',
//        method: 'POST',
//        data: JSON.stringify(body),
//        contentType: 'application/json',
//        dataType: 'json',
//        headers: {
//          "AUTH": localStorage.getItem('AUTH')
//        },
//        complete: function () {
//
//        }
//    });
// }
// function receiveMessage() {
//    $.ajax({
//        url: 'http://localhost:8080/messages',
//        method: 'GET',
//        dataType: 'json',
//        contentType: 'application/json',
//        headers: {
//          "AUTH": localStorage.getItem('AUTH')
//        },
//        success: function (response) {
//            let html = "";
//            for (let i = 0; i < response.length; i++) {
//                html = html + '<li class="list-group-item" style="font-family: \'Courier New\'"><img style="height: 25px; width: 25px" src="https://www.freepngimg.com/thumb/telephone/68708-ipma-message-icon-email-telephone-png-image-high-quality.png">  ' + response[i].username + ': ' + response[i].text + ' <p style="font-size: 12px;color: #c1c0c0;">' + response[i].time + '</p></li>';
//            }
//            $('#messages').html(html);
//            $('#wheel').hide();
//            receiveMessage();
//        }
//    })
// }
// let flagIsNotLogin = true
//
// function login() {
//     if (flagIsNotLogin) {
//     flagIsNotLogin = false;
//     document.getElementById('messages').scrollTop = 9999;
//     let body = {
//         text: 'зашел в чат...',
//         username: '123'
//     };
//     connect(localStorage.getItem('USERNAME'));
//
//     $.ajax({
//         url: 'http://localhost:8080/messages',
//         method: 'POST',
//         data: JSON.stringify(body),
//         contentType: 'application/json',
//         dataType: 'json',
//         headers: {
//           "AUTH": localStorage.getItem('AUTH')
//         },
//         complete: function () {
//             receiveMessage();
//         }
//     });
//   }
// }
