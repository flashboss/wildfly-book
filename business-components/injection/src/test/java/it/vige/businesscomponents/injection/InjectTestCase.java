package it.vige.businesscomponents.injection;

import static it.vige.businesscomponents.injection.inject.any.BankName.BankOfAmerica;
import static it.vige.businesscomponents.injection.inject.any.BankName.Chase;
import static it.vige.businesscomponents.injection.inject.any.BankName.HSBC;
import static it.vige.businesscomponents.injection.inject.model.StateBook.DRAFT;
import static it.vige.businesscomponents.injection.inject.model.StateBook.PUBLISHED;
import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.businesscomponents.injection.inject.Draft;
import it.vige.businesscomponents.injection.inject.Published;
import it.vige.businesscomponents.injection.inject.Service;
import it.vige.businesscomponents.injection.inject.Writer;
import it.vige.businesscomponents.injection.inject.any.Bank;
import it.vige.businesscomponents.injection.inject.any.BankProducer;
import it.vige.businesscomponents.injection.inject.any.BankType;
import it.vige.businesscomponents.injection.inject.impl.BookService;
import it.vige.businesscomponents.injection.inject.impl.CommentService;
import it.vige.businesscomponents.injection.inject.impl.CommentWriter;
import it.vige.businesscomponents.injection.inject.model.Book;
import it.vige.businesscomponents.injection.inject.produces.UserNumberBean;

@RunWith(Arquillian.class)
public class InjectTestCase {

	private static final Logger logger = getLogger(InjectTestCase.class.getName());

	@Inject
	private Writer writer;

	@Inject
	private Service service;

	@Inject
	private UserNumberBean userNumberBean;

	@Inject
	@Published
	private List<Book> publishedBooks;

	@Inject
	@Draft
	private List<Book> draftBooks;

	@Inject
	@BankType(BankOfAmerica)
	@BankProducer
	private Bank bankOfAmerica;

	@Inject
	@BankType(Chase)
	@BankProducer
	private Bank chase;

	@Inject
	@BankType(HSBC)
	@BankProducer
	private Bank hsbc;

	@Deployment
	public static JavaArchive createJavaDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "inject-test.jar");
		jar.addPackage(Service.class.getPackage());
		jar.addPackage(CommentService.class.getPackage());
		jar.addPackage(Book.class.getPackage());
		jar.addPackage(UserNumberBean.class.getPackage());
		jar.addPackage(Bank.class.getPackage());
		jar.addAsManifestResource(INSTANCE, "beans.xml");
		return jar;
	}

	/**
	 * Tests default annotation in a jar archive
	 */
	@Test
	public void testInjectWriter() {
		logger.info("starting inject test");
		assertTrue("it takes the default annotated writer", writer instanceof CommentWriter);
		assertTrue("it takes the default annotated service", service instanceof BookService);
		assertEquals("the published is called", PUBLISHED, publishedBooks.get(0).getState());
		assertEquals("I injected the draft books, so the method with any annotation is called", DRAFT,
				draftBooks.get(0).getState());
		assertEquals("the BankFactory is not used", "Deposit to Bank of America", bankOfAmerica.deposit());
		assertEquals("the BankFactory is used", "Deposit to Chase", chase.deposit());
		assertEquals("the BankFactory is used", "Deposit to HSBC", hsbc.deposit());
	}

	/**
	 * Tests the produces a jar archive
	 */
	@Test
	public void testProduces() {
		logger.info("starting produces test");
		int myChosenNumber = 20;
		userNumberBean.validateNumberRange(myChosenNumber);
		userNumberBean.setUserNumber(myChosenNumber);
		userNumberBean.check();
		userNumberBean.reset();
	}
}
