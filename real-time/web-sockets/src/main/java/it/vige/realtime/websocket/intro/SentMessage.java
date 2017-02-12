package it.vige.realtime.websocket.intro;

import static it.vige.realtime.websocket.intro.WebSocketServer.sentMessage;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/message")
public class SentMessage {

	@GET
	@Path("sent")
	public Date sent() {
		return sentMessage;
	}
}
