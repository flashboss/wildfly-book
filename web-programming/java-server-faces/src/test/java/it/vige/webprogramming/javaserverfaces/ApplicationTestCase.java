package it.vige.webprogramming.javaserverfaces;

import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

@RunWith(Arquillian.class)
public class ApplicationTestCase {

	private static final Logger logger = getLogger(ApplicationTestCase.class.getName());

	private static final String WEBAPP_SRC = "src/main/webapp";

	@ArquillianResource
	private URL base;

	private HtmlForm loginForm;

	private WebClient webClient;

	public static WebArchive createDeployment() {
		WebArchive war = create(WebArchive.class).addPackages(true, ApplicationTestCase.class.getPackage())
				.addAsWebResource(new File(WEBAPP_SRC + "/views/category", "viewcategory_body.xhtml"),
						"views/category/viewcategory_body.xhtml")
				.addAsWebResource(new File(WEBAPP_SRC + "/views/admin", "index.xhtml"), "views/admin/index.xhtml")
				.addAsWebResource(new File(WEBAPP_SRC + "/views/admin", "deletecategory.xhtml"),
						"views/admin/deletecategory.xhtml")
				.addAsWebInfResource((new File(WEBAPP_SRC + "/WEB-INF", "web.xml")), "web.xml")
				.addAsWebInfResource((new File(WEBAPP_SRC + "/WEB-INF", "jboss-web.xml")), "jboss-web.xml")
				.addAsWebInfResource((new File(WEBAPP_SRC + "/WEB-INF", "faces-config.xml")), "faces-config.xml")
				.addAsWebInfResource((new File(WEBAPP_SRC + "/WEB-INF", "forums.taglib.xml")), "forums.taglib.xml")
				.addAsWebInfResource((new File(WEBAPP_SRC + "/WEB-INF", "beans.xml")), "beans.xml");
		return war;
	}

	@Before
	public void setup() throws IOException {
		webClient = new WebClient();
		HtmlPage page = webClient.getPage(base + "view/secure-form.jsp");
		loginForm = page.getForms().get(0);
	}

	@Test
	public void testGetWithIncorrectCredentials() throws Exception {
		logger.info("start get request with incorrect credentials");
		loginForm.getInputByName("j_username").setValueAttribute("random");
		loginForm.getInputByName("j_password").setValueAttribute("random");
		HtmlSubmitInput submitButton = loginForm.getInputByName("submitButton");
		HtmlPage page2 = submitButton.click();
		assertEquals("Form-Based Login Error Page", page2.getTitleText());
	}
}
