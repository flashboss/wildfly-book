package it.vige.realtime.asynchronousrest;

import static java.util.logging.Logger.getLogger;
import static javax.ws.rs.client.ClientBuilder.newClient;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ReceiveMessagesTestCase {

	private static final Logger logger = getLogger(ReceiveMessagesTestCase.class.getName());

	@ArquillianResource
	private URL url;

	@Deployment
	public static WebArchive createWebDeployment() {
		final WebArchive war = create(WebArchive.class, "asynch-rest-test.war");
		war.addPackage(AsyncResource.class.getPackage());
		war.addAsWebInfResource(INSTANCE, "beans.xml");
		war.addAsWebInfResource(new FileAsset(new File("src/test/resources/web.xml")), "web.xml");
		return war;
	}

	@Test
	public void testMagicNumber() throws Exception {
		logger.info("start rest receive messages test");
		Client client = newClient();
		WebTarget target = client.target(url + "async/resource");
		Response response = target.request().get();
		String magicNumber = response.readEntity(String.class);
		assertEquals("status of response: ", 200, response.getStatus());
		assertEquals("magic number is: ", "MagicNumber [value=3]", magicNumber);
		response.close();
	}

}
