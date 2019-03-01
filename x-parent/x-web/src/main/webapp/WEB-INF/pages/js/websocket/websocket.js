var socket;
if(!window.WebSocket){
  window.WebSocket = window.MozWebSocket;
  alert()
}

if(window.WebSocket){
  socket = new WebSocket("ws://127.0.0.1:8888/websocket");
  socket.onmessage = function(event){
    alert(event.data)
  };

  socket.onopen = function(event){
    alert("你当前的浏览器支持WebSocket,请进行后续操作")
  };

  socket.onclose = function(event){
    alert("WebSocket连接已经关闭")
  };
}else{
  alert("您的浏览器不支持WebSocket");
}


function send(message){
  if(!window.WebSocket){
    return;
  }
  if(socket.readyState == WebSocket.OPEN){
    socket.send(message);
  }else{
    alert("WebSocket连接没有建立成功！！");
  }
}