package it.vige.businesscomponents.transactions;

import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.businesscomponents.transactions.concurrent.MyCallableTask;

@RunWith(Arquillian.class)
public class ModelsTestCase {

	private static final Logger logger = getLogger(ModelsTestCase.class.getName());

	@Resource(lookup = "java:jboss/datasources/ExampleDS")
	private DataSource myData;

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private UserTransaction userTransaction;

	@Resource(name = "DefaultManagedExecutorService")
	private ManagedExecutorService defaultExecutor;

	@Deployment
	public static JavaArchive createJavaDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "models-test.jar");
		jar.addPackage(MyCallableTask.class.getPackage());
		jar.addClass(Account.class);
		jar.addAsManifestResource(new FileAsset(new File("src/test/resources/META-INF/persistence-test.xml")),
				"persistence.xml");
		jar.addAsManifestResource(INSTANCE, "beans.xml");
		return jar;
	}

	@Test
	public void testFTM() throws Exception {
		logger.info("start ftm and savepoints test");
		userTransaction.begin();
		Account account = new Account(48111, 436564.87);
		Account account2 = new Account(49111, 436164.87);
		Account account3 = new Account(19111, 136164.87);
		try {
			entityManager.persist(account);
			entityManager.persist(account2);
			entityManager.persist(account3);
			userTransaction.commit();
		} catch (Exception e) {
			userTransaction.rollback();
		}
		@SuppressWarnings("unchecked")
		List<Account> accounts = entityManager.createNamedQuery("SelectAll").getResultList();
		assertEquals("the commit succeed and all accounts are created. The FTM transaction is end", 3, accounts.size());
	}

	@Test
	public void testSavepoints() throws Exception {
		logger.info("start savepoints test");
		String insert = "INSERT INTO ACCOUNT(NUMBER, CREDIT) VALUES (";
		String get = "SELECT NUMBER FROM ACCOUNT WHERE NUMBER = ";

		try (Connection conn = myData.getConnection()) {
			// it is not permitted to use savepoint methods if we have an XA
			// tx...
			conn.setAutoCommit(false);
			Statement insertAccount = null;
			Statement getAccount = null;
			boolean gotExpectedException = false;
			Savepoint savepoint = null;
			Savepoint mySavepoint = null;
			try {
				savepoint = conn.setSavepoint();
				insertAccount = conn.createStatement();
				insertAccount.executeUpdate(insert + "48112, 436564.87)");
			} catch (SQLException e) {
				gotExpectedException = true;
			}
			if (gotExpectedException) {
				fail("Failed to get expected exception from setSavepoint inside tx");
			}
			gotExpectedException = false;
			try {
				mySavepoint = conn.setSavepoint("mySavepoint");
				insertAccount.executeUpdate(insert + "48113, 436564.87)");
			} catch (SQLException e) {
				gotExpectedException = true;
			}
			if (gotExpectedException) {
				fail("Failed to get expected exception from setSavepoint(String) inside tx");
			}
			gotExpectedException = false;
			try {
				insertAccount.executeUpdate(insert + "48114, 436564.87)");
				conn.rollback(mySavepoint);
				getAccount = conn.createStatement();
				assertTrue(getAccount.executeQuery(get + "48112").first());
				assertFalse(getAccount.executeQuery(get + "48113").first());
				assertFalse(getAccount.executeQuery(get + "48114").first());
			} catch (SQLException e) {
				gotExpectedException = true;
			}
			if (gotExpectedException) {
				fail("Failed to get expected exception from rollback(String)");
			}
			try {
				conn.releaseSavepoint(savepoint);
			} catch (SQLException e) {
				gotExpectedException = true;
			}
			if (gotExpectedException) {
				fail("Failed to get expected exception from releaseSavepoint(String)");
			}
		} catch (SQLException e) {
			fail("We need a working connection here");
			logger.info("I cannot connect");
		}
	}

	@Test
	public void testNTM() throws Exception {
		logger.info("start NTM test");
	}

	@Test
	public void testChainedTransaction() throws Exception {
		logger.info("start chainded transactions test");
	}
}
