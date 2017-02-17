package it.vige.realtime.websocket.session;

import java.io.IOException;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint
public class SessionSocketClient {

	public static Session sessionClient;

	@OnOpen
	public void open(Session session) throws IOException {
		sessionClient = session;
	}

	@OnClose
	public void close(final Session session) throws IOException {
	}
}