<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Chat</title>
    <link rel="shortcut icon"
          href="https://www.freepngimg.com/thumb/telephone/68708-ipma-message-icon-email-telephone-png-image-high-quality.png"
          type="image/x-icon">

    <script src="/js/chat_script.js"></script>

    <link href="/css/style.css" rel="stylesheet" type="text/css">

    <link href="/css/chat_wheel_style.css" rel="stylesheet" type="text/css">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
</head>
<body onload="login();">
<div id="header"><h1 style="font-size: 100px;border-bottom: 2px solid;">CHAT<img id="mail_img"
                                                                                 style="height: 100px;width: 100px;padding-bottom: 10px;"
                                                                                 src="https://www.freepngimg.com/thumb/telephone/68708-ipma-message-icon-email-telephone-png-image-high-quality.png">ROOM
    </h1></div>
<div class="container">
    <div class="row">
        <div class="col-lg-4 col-md-3"></div>
        <div class="col-lg-4 col-md-6 col-sm-12" style="border: 2px solid black;">
            <h2 style="text-align: center; font-family: 'Courier New';padding-top: 35px;border-bottom: 2px solid;">
                FLAT|${user_username}</h2>
            <ul id="messages" class="list-group">
                <#list messages as m>
                    <li class="list-group-item" style="font-family: 'Courier New'"><img
                                style="height: 25px; width: 25px"
                                src="https://www.freepngimg.com/thumb/telephone/68708-ipma-message-icon-email-telephone-png-image-high-quality.png"> ${m.username}
                        : ${m.text} <p style="font-size: 12px;color: #c1c0c0;">${m.time}</p></li>
                </#list>
            </ul>
            <div class="container">
                <div class="row" style="border-top: 2px solid;
    padding-top: 10px;">

                    <div class="col-10">
                        <div class="input-group mb-3">
                            <input id="message" type="text" class="form-control" placeholder="Ваше сообщение"
                                   aria-label="Recipient's username" aria-describedby="basic-addon2">
                            <div class="input-group-append">

                                <button onclick="sendMessage($('#message').val())" type="button"
                                        class="btn btn-outline-dark">Send
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="col-2" style="margin-top: 5px">
                        <div class="windows8" id="wheel">
                            <div class="wBall" id="wBall_1">
                                <div class="wInnerBall"></div>
                            </div>
                            <div class="wBall" id="wBall_2">
                                <div class="wInnerBall"></div>
                            </div>
                            <div class="wBall" id="wBall_3">
                                <div class="wInnerBall"></div>
                            </div>
                            <div class="wBall" id="wBall_4">
                                <div class="wInnerBall"></div>
                            </div>
                            <div class="wBall" id="wBall_5">
                                <div class="wInnerBall"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div style="height: 20px;"></div>
        </div>
        <div class="col-lg-4 col-md-3"></div>
    </div>
</div>
</body>
<script>
    document.querySelector('#message').addEventListener('keypress', e => {
        if (e.charCode == 13) {
            sendMessage($('#message').val());
        }
    });
</script>
</html>