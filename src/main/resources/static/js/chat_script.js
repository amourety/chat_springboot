function sendMessage(text) {
    initializeBlink();
    initializeBlink();
    initializeBlink();
    $('#wheel').show();
    $('#message').val("");
    let body = {
        text: text
    };

    $.ajax({
        url: "/messages",
        method: "POST",
        data: JSON.stringify(body),
        contentType: "application/json",
        dataType: "json",
        complete: function () {
        }
    });
}

function receiveMessage() {
    $.ajax({
        url: "/messages",
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (response) {
            stopBlink();
            let html = "";
            for (let i = 0; i < response.length; i++) {
                html = html + '<li class="list-group-item" style="font-family: \'Courier New\'"><img style="height: 25px; width: 25px" src="https://www.freepngimg.com/thumb/telephone/68708-ipma-message-icon-email-telephone-png-image-high-quality.png">  ' + response[i].username + ': ' + response[i].text + ' <p style="font-size: 12px;color: #c1c0c0;">' + response[i].time + '</p></li>';
            }
            $('#messages').html(html);
            $('#wheel').hide();
            receiveMessage();
        }
    })
}

function login() {
    document.getElementById('messages').scrollTop = 9999;
    let body = {
        text: 'зашел в чат...',
        username: '123'
    };

    $.ajax({
        url: "/messages",
        method: "POST",
        data: JSON.stringify(body),
        contentType: "application/json",
        dataType: "json",
        complete: function () {
            receiveMessage();
        }
    });
}

let anim_state = false;

function initializeBlink() {
    anim_state = true;
    $('#mail_img').fadeToggle('slow', fadeToggleBlinker);
}
function stopBlink() {
    $('#mail_img').show();
    anim_state = false;
}

function fadeToggleBlinker() {
    if (anim_state) {
        var timeout = 2000;
        if (this.style.display == 'none')
            timeout = 1000;
        var tmp = $(this);
        window.setTimeout(function () {
            tmp.fadeToggle('slow', fadeToggleBlinker)
        }, timeout);

    } else {
        $('#mail_img').show();
    }
}



