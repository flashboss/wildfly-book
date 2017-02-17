package it.vige.realtime.websocket;

import static java.nio.ByteBuffer.allocate;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import static javax.websocket.ContainerProvider.getWebSocketContainer;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.websocket.EncodeException;
import javax.websocket.Extension;
import javax.websocket.Extension.Parameter;
import javax.websocket.MessageHandler;
import javax.websocket.RemoteEndpoint;
import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.SendHandler;
import javax.websocket.SendResult;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.realtime.websocket.session.SessionMessageHandler;
import it.vige.realtime.websocket.session.SessionSocketClient;
import it.vige.realtime.websocket.session.SessionSocketServer;

@RunWith(Arquillian.class)
public class SessionTestCase {

	private static final Logger logger = getLogger(SessionTestCase.class.getName());

	@ArquillianResource
	private URL url;

	@Deployment
	public static WebArchive createWebDeployment() {
		final WebArchive war = create(WebArchive.class, "session-test.war");
		war.addPackage(SessionSocketServer.class.getPackage());
		war.addAsWebInfResource(INSTANCE, "beans.xml");
		war.addAsWebInfResource(new FileAsset(new File("src/main/webapp/WEB-INF/web.xml")), "web.xml");
		return war;
	}

	@Test
	public void testSessionServer() throws Exception {
		logger.info("start websocket server session test");

		final WebSocketContainer serverContainer = getWebSocketContainer();
		serverContainer.connectToServer(SessionSocketClient.class, url.toURI().resolve("session"));

		session(SessionSocketClient.sessionClient);
	}

	private void session(Session session) {
		logger.info("id: " + session.getId());
		logger.info("negotiatedSubprotocol: " + session.getNegotiatedSubprotocol());
		Set<Session> sessions = session.getOpenSessions();
		for (Session s : sessions) {
			session(s);
		}
		Map<String, String> pathParameters = session.getPathParameters();
		for (String key : pathParameters.keySet()) {
			logger.info("pathParameters: " + key + " - " + pathParameters.get(key));
		}
		logger.info("protocolVersion: " + session.getProtocolVersion());
		logger.info("queryString: " + session.getQueryString());
		Map<String, List<String>> requestParameters = session.getRequestParameterMap();
		for (String key : requestParameters.keySet()) {
			logger.info("requestParameters: " + key + " - " + requestParameters.get(key));
		}
		logger.info("requestURI: " + session.getRequestURI());
		logger.info("userPrincipal: " + session.getUserPrincipal());
		Map<String, Object> userProperties = session.getUserProperties();
		for (String key : userProperties.keySet()) {
			logger.info("userProperties: " + key + " - " + userProperties.get(key));
		}
		logger.info("secure: " + session.isSecure());
		logger.info("open: " + session.isOpen());
		container(session);
		messageHandlers(session);
		max(session);
		extensions(session.getNegotiatedExtensions().iterator());
		async(session);
		basic(session);
	}

	private void container(Session session) {
		WebSocketContainer container = session.getContainer();
		logger.info("defaultAsyncSendTimeout: " + container.getDefaultAsyncSendTimeout());
		logger.info("defaultMaxBinaryMessageBufferSize: " + container.getDefaultMaxBinaryMessageBufferSize());
		logger.info("defaultMaxSessionIdleTimeout: " + container.getDefaultMaxSessionIdleTimeout());
		logger.info("defaultMaxTextMessageBufferSize: " + container.getDefaultMaxTextMessageBufferSize());
		container.setAsyncSendTimeout(4);
		container.setDefaultMaxBinaryMessageBufferSize(5);
		container.setDefaultMaxSessionIdleTimeout(6);
		container.setDefaultMaxTextMessageBufferSize(7);
		logger.info("defaultAsyncSendTimeout: " + container.getDefaultAsyncSendTimeout());
		logger.info("defaultMaxBinaryMessageBufferSize: " + container.getDefaultMaxBinaryMessageBufferSize());
		logger.info("defaultMaxSessionIdleTimeout: " + container.getDefaultMaxSessionIdleTimeout());
		logger.info("defaultMaxTextMessageBufferSize: " + container.getDefaultMaxTextMessageBufferSize());
		extensions(container.getInstalledExtensions().iterator());
	}

	private void messageHandlers(Session session) {
		Set<MessageHandler> messageHandlers = session.getMessageHandlers();
		for (MessageHandler messageHandler : messageHandlers) {
			logger.info("messageHandler: " + messageHandler);
		}
		MessageHandler messageHandler = new SessionMessageHandler();
		session.addMessageHandler(messageHandler);
		session.removeMessageHandler(messageHandler);
	}

	private void max(Session session) {
		logger.info("maxBinaryMessageBufferSize: " + session.getMaxBinaryMessageBufferSize());
		logger.info("maxIdleTimeout: " + session.getMaxIdleTimeout());
		logger.info("maxTextMessageBufferSize: " + session.getMaxTextMessageBufferSize());
		session.setMaxBinaryMessageBufferSize(4);
		session.setMaxIdleTimeout(7);
		session.setMaxTextMessageBufferSize(8);
		logger.info("maxBinaryMessageBufferSize: " + session.getMaxBinaryMessageBufferSize());
		logger.info("maxIdleTimeout: " + session.getMaxIdleTimeout());
		logger.info("maxTextMessageBufferSize: " + session.getMaxTextMessageBufferSize());
	}

	private void extensions(Iterator<Extension> extensions) {
		while (extensions.hasNext()) {
			Extension extension = extensions.next();
			logger.info("extension name: " + extension.getName());
			List<Parameter> parameters = extension.getParameters();
			for (Parameter parameter : parameters) {
				logger.info("extension parameter name: " + parameter.getName());
				logger.info("extension parameter value: " + parameter.getValue());
			}
		}
	}

	private void async(Session session) {
		Async async = session.getAsyncRemote();
		remoteEndpoint(async);
		logger.info("async getSendTimeout: " + async.getSendTimeout());
		async.setSendTimeout(45);
		logger.info("async getSendTimeout: " + async.getSendTimeout());
		ByteBuffer byteBuffer = allocate(11);
		async.sendBinary(byteBuffer);
		SendHandler sendHandler = new SendHandler() {
			@Override
			public void onResult(SendResult result) {
				logger.info("session sendHandler result ok: " + result.isOK());
				logger.info("session sendHandler result exception: " + result.getException());
			}
		};
		async.sendBinary(byteBuffer, sendHandler);
		async.sendObject(666);
		async.sendObject("my test 2", sendHandler);
		async.sendText("my text");
		async.sendText("my text 2", sendHandler);
	}

	private void basic(Session session) {
		Basic basic = session.getBasicRemote();
		remoteEndpoint(basic);
		ByteBuffer byteBuffer = allocate(23);
		try {
			basic.sendBinary(byteBuffer);
			basic.sendBinary(byteBuffer, true);
			basic.sendObject(555);
			basic.sendText("my text");
			basic.sendText("my text 2", false);
			logger.info("basic sendStream: " + basic.getSendStream());
			logger.info("basic sendWriter: " + basic.getSendWriter());
		} catch (IOException | EncodeException e) {
			logger.log(SEVERE, "error", e);
		}
	}

	private void remoteEndpoint(RemoteEndpoint remoteEndpoint) {
		try {
			remoteEndpoint.flushBatch();
			ByteBuffer byteBuffer = allocate(23);
			remoteEndpoint.sendPing(byteBuffer);
			remoteEndpoint.sendPong(byteBuffer);
			logger.info("remoteEndpoint batchingAllowed: " + remoteEndpoint.getBatchingAllowed());
			remoteEndpoint.setBatchingAllowed(false);
			logger.info("remoteEndpoint batchingAllowed: " + remoteEndpoint.getBatchingAllowed());
		} catch (IOException e) {
			logger.log(SEVERE, "error", e);
		}
	}
}
