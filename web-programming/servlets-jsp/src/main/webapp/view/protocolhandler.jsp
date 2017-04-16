<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Protocol Handler using Servlet 3.1</title>
<script language="javascript" type="text/javascript">
	var wsUri = "${pageContext.request.contextPath}/UpgradeServlet";
	var output;

	function init() {
		output = document.getElementById("output");
		testWebSocket();
	}

	function testWebSocket() {
		websocket = new WebSocket(wsUri);
		websocket.onopen = function(evt) {
			onOpen(evt)
		};
		websocket.onclose = function(evt) {
			onClose(evt)
		};
		websocket.onmessage = function(evt) {
			onMessage(evt)
		};
		websocket.onerror = function(evt) {
			onError(evt)
		};
	}

	function onOpen(evt) {
		writeToScreen("CONNECTED");
		doSend("WebSocket rocks");
	}

	function onClose(evt) {
		writeToScreen("DISCONNECTED");
	}

	function onMessage(evt) {
		writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data
				+ '</span>');
		websocket.close();
	}

	function onError(evt) {
		writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
	}

	function doSend(message) {
		writeToScreen("SENT: " + message);
		websocket.send(message);
	}

	function writeToScreen(message) {
		var pre = document.createElement("p");
		pre.style.wordWrap = "break-word";
		pre.innerHTML = message;
		output.appendChild(pre);
	}

	window.addEventListener("load", init, false);
</script>

</head>
<body>
	<h1>Protocol Handler using Servlet 3.1</h1>
	Invoke
	<button onclick="httpGet()">protocol upgrade</button>
	<div id="output"></div>
	<br />
</body>
</html>
