package it.vige.webprogramming.servletjsp;

import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.By.xpath;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(Arquillian.class)
public class WebFragmentTestCase {

	private static final Logger logger = getLogger(WebFragmentTestCase.class.getName());

	@Deployment(testable = false)
	public static WebArchive deploy() throws URISyntaxException {
		WebArchive war = create(WebArchive.class).addAsLibrary(new File("src/main/webapp/WEB-INF/lib/web-fragment.jar"),
				"web-fragment.jar");
		war.addAsWebResource(new FileAsset(new File("src/main/webapp/index.jsp")), "index.jsp");
		war.addAsWebResource(new FileAsset(new File("src/main/webapp/view/webfragment.jsp")), "view/webfragment.jsp");
		return war;
	}

	@ArquillianResource
	private URL url;

	@Drone
	private WebDriver driver;

	@Test
	public void testWebFragment() throws Exception {
		logger.info("start web fragment test");
		driver.get(url + "");
		driver.findElement(xpath("html/body/a")).click();
		assertTrue("the page result is: ",
				driver.findElement(xpath("html/body")).getText().startsWith("Non-blocking I/O with Servlet 3.1"));
	}

}
