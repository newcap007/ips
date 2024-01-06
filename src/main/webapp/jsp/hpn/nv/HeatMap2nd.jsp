<!DOCTYPE html>  
<html>  
<head>  
<meta charset=UTF-8>  
<title>Tomcat WebSocket Chat</title>  
<script>     
//建立客户端websocket    
        var ws = new WebSocket("ws://localhost:8080/ips/websocket/chat");    
        //定义ws的一些回调函数    
ws.onopen = function() {    
};    
    //当有消息传过来时执行的操作    
        ws.onmessage = function(message) {    
            document.getElementById("chatlog").textContent += message.data + "\n";    
        };    
        function postToServer() {    
            ws.send(document.getElementById("msg").value);    
            document.getElementById("msg").value = "";    
        }    
        function closeConnect() {    
            ws.close();    
        }    
    </script>  
</head>  
<body>  
    <textarea id="chatlog" readonly></textarea>  
    <br />  
    <input id="msg" type="text" />  
    <button type="submit" id="sendButton" onClick="postToServer()">Send!</button>  
    <button type="submit" id="sendButton" onClick="closeConnect()">End</button>  
</body>  
</html> 