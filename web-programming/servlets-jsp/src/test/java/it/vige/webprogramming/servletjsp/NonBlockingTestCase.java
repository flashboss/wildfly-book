package it.vige.webprogramming.servletjsp;

import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.By.xpath;

import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import it.vige.webprogramming.servletjsp.nonblocking.ReadingServlet;

@RunWith(Arquillian.class)
@RunAsClient
public class NonBlockingTestCase {

	private static final Logger logger = getLogger(NonBlockingTestCase.class.getName());

	@ArquillianResource
	private URL url;

	@Drone
	private WebDriver driver;

	@Deployment
	public static WebArchive createWebDeployment() {
		final WebArchive war = create(WebArchive.class, "nonblocking-test.war");
		war.addPackage(ReadingServlet.class.getPackage());
		war.addAsWebInfResource(INSTANCE, "beans.xml");
		war.addAsWebResource(new FileAsset(new File("src/main/webapp/index.jsp")), "index.jsp");
		war.addAsWebResource(new FileAsset(new File("src/main/webapp/view/nonblocking.jsp")), "view/nonblocking.jsp");
		war.addAsWebInfResource(new FileAsset(new File("src/test/resources/web.xml")), "web.xml");
		return war;
	}

	@Test
	public void testNonBlocking() throws Exception {
		logger.info("start non blocking test");
		driver.get(url + "");
		driver.findElement(xpath("html/body/a")).click();
		assertTrue("the page result is: ",
				driver.findElement(xpath("html/body")).getText().startsWith("Non-blocking I/O with Servlet 3.1"));
	}

}
