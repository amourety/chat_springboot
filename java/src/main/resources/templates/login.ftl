<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="shortcut icon"
          href="https://www.freepngimg.com/thumb/telephone/68708-ipma-message-icon-email-telephone-png-image-high-quality.png"
          type="image/x-icon">


    <script src="/js/chat_script.js"></script>
    <link href="/css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
</head>
<body>
<div id="header"><h1 style="font-size: 100px;border-bottom: 2px solid">CHAT<img
                style="height: 100px;width: 100px;padding-bottom: 10px;"
                src="https://www.freepngimg.com/thumb/telephone/68708-ipma-message-icon-email-telephone-png-image-high-quality.png">ROOM
    </h1></div>
<div class="container">
    <div class="main">
        <div class="maincentre">
            <form class="form-signin" role="form" method="post" action="login"
                  style="padding-top: 50px;min-width: 300px;max-width: 300px;">
                <h2 class="form-signin-heading" style="font-family: 'Courier New';">Please sign in</h2>
                <input id="login" name="login" type="text" class="form-control" placeholder="Login" required=""
                       autof ocus="" style="margin-bottom: 3px">
                <input id="password" name="password" type="password" class="form-control" placeholder="Password"
                       required="">
                <br>

                <button class="btn btn-lg btn-dark btn-block" type="submit">Sign in</button>
                <a href="/registration">to registration page</a>
            </form>
            <br>
        </div>
    </div>
</div>

</body>
</html>