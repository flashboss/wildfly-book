package it.vige.realtime.websocket;

import static it.vige.realtime.websocket.intro.WebSocketClient.getMessage;
import static it.vige.realtime.websocket.intro.WebSocketClient.getName;
import static it.vige.realtime.websocket.intro.WebSocketClient.injectionOK;
import static it.vige.realtime.websocket.intro.WebSocketClient.postConstructCalled;
import static it.vige.realtime.websocket.intro.WebSocketClient.reset;
import static java.util.logging.Logger.getLogger;
import static javax.websocket.ContainerProvider.getWebSocketContainer;
import static javax.ws.rs.client.ClientBuilder.newClient;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.By.linkText;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.logging.Logger;

import javax.websocket.WebSocketContainer;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import it.vige.realtime.websocket.intro.WebSocketClient;
import it.vige.realtime.websocket.intro.WebSocketServer;

@RunWith(Arquillian.class)
public class IntroTestCase {

	private static final Logger logger = getLogger(IntroTestCase.class.getName());

	@ArquillianResource
	private URL url;

	@Drone
	private WebDriver driver;

	@Deployment
	public static WebArchive createWebDeployment() {
		final WebArchive war = create(WebArchive.class, "intro-test.war");
		war.addPackage(WebSocketServer.class.getPackage());
		war.addAsWebInfResource(INSTANCE, "beans.xml");
		war.addAsWebResource(new FileAsset(new File("src/test/resources/index.html")), "index.html");
		war.addAsWebInfResource(new FileAsset(new File("src/test/resources/web.xml")), "web.xml");
		return war;
	}

	@Test
	@RunAsClient
	public void testClientFirstCall() throws Exception {
		logger.info("start websocket first call test");
		driver.get(url + "");
		WebElement element = driver.findElement(linkText("Send message"));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		Client client = newClient();
		WebTarget target = client.target(url + "rest_test/message/sent");
		Date today = new Date();
		Date date = target.request().get(Date.class);
		assertEquals("the message is sent: ", today, date);
	}
/*
	@Test
	public void testServerFirstCall() throws Exception {
		reset();

		final WebSocketContainer serverContainer = getWebSocketContainer();
		serverContainer.connectToServer(WebSocketClient.class, url.toURI().resolve("chat"));

		assertEquals("Hello cruel World", getMessage());

		assertTrue("Client endpoint's injection not correct.", injectionOK);

		assertTrue("PostConstruct method on client endpoint instance not called.", postConstructCalled);

		assertEquals("AroundConstruct interceptor method not invoked for client endpoint.",
				"AroundConstructInterceptor#Joe#WebSocketClient", getName());
	}*/
}
