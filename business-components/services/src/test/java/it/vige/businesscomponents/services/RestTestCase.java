package it.vige.businesscomponents.services;

import static java.util.logging.Logger.getLogger;
import static javax.ws.rs.client.ClientBuilder.newClient;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.net.URL;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class RestTestCase {

	private static final Logger logger = getLogger(RestTestCase.class.getName());

	@ArquillianResource
	private URL url;

	@Deployment
	public static WebArchive createWebDeployment() {
		final WebArchive war = create(WebArchive.class, "rest-test.war");
		war.addPackage(Calculator.class.getPackage());
		war.addAsWebInfResource(INSTANCE, "beans.xml");
		return war;
	}

	@Test
	public void testJaxRSGet() throws Exception {
		logger.info("start JaxRS Get test");
		Client client = newClient();
		WebTarget target = client.target(url + "services/calculator/sum?value=4&value=6");
		Response response = target.request().get();
		double value = response.readEntity(Double.class);
		response.close(); // You should close connections!
		assertEquals("sum implemented: ", 10.0, value, 0.0);
	}

	@Test
	public void testRestEasyGet() throws Exception {
		logger.info("start REST Easy Get test");
		ResteasyClient client = new ResteasyClientBuilder().build();
		WebTarget target = client.target(url + "services/calculator/sub?value=40&value=6");
		Response response = target.request().get();
		double value = response.readEntity(Double.class);
		response.close(); // You should close connections!
		assertEquals("subtract implemented: ", 34.0, value, 0.0);
	}

	@Test
	public void testSSLContext() throws Exception {
		logger.info("start SSL Context test");
		Client client = newClient();
		HostnameVerifier hostnameVerifier = client.getHostnameVerifier();
		assertNotNull("Java Utility", hostnameVerifier);
		assertNull("no SSL by default", client.getSslContext());
	}

}
