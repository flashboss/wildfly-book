package it.vige.businesscomponents.injection;

import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.businesscomponents.injection.inject.Service;
import it.vige.businesscomponents.injection.inject.Writer;
import it.vige.businesscomponents.injection.inject.impl.BookService;
import it.vige.businesscomponents.injection.inject.impl.CommentService;
import it.vige.businesscomponents.injection.inject.impl.CommentWriter;

@RunWith(Arquillian.class)
public class InjectTestCase {

	private static final Logger logger = getLogger(InjectTestCase.class.getName());

	@Inject
	private Writer writer;

	@Inject
	private Service service;

	@Deployment
	public static JavaArchive createJavaDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "inject-test.jar");
		jar.addPackage(Service.class.getPackage());
		jar.addPackage(CommentService.class.getPackage());
		jar.addAsManifestResource(INSTANCE, "beans.xml");
		return jar;
	}

	/**
	 * Tests default annotation in a jar archive
	 */
	@Test
	public void testInjectWriter() {
		logger.info("starting util event test");
		assertTrue("it takes the default annotated writer", writer instanceof CommentWriter);
		assertTrue("it takes the default annotated service", service instanceof BookService);
	}
}
