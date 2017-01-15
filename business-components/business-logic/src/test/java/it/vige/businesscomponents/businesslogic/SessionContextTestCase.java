package it.vige.businesscomponents.businesslogic;

import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;

import java.util.logging.Logger;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.businesscomponents.businesslogic.context.MyData;
import it.vige.businesscomponents.businesslogic.context.nnn.EngineLocal;
import it.vige.businesscomponents.businesslogic.context.nnn.EngineRemote;
import it.vige.businesscomponents.businesslogic.context.nnn.StateEngineLocal;
import it.vige.businesscomponents.businesslogic.context.nnn.StateEngineRemote;
import it.vige.businesscomponents.businesslogic.context.old.Ejb21StateLocal;
import it.vige.businesscomponents.businesslogic.context.old.Ejb21StateRemote;

@RunWith(Arquillian.class)
public class SessionContextTestCase {

	private static final Logger logger = getLogger(SessionContextTestCase.class.getName());

	@EJB
	private EngineRemote engineRemote;

	@EJB
	private StateEngineRemote stateEngineRemote;

	@EJB
	private Ejb21StateRemote ejb21StateEngineRemote;

	@EJB
	private EngineLocal engineLocal;

	@EJB
	private StateEngineLocal stateEngineLocal;

	@EJB
	private Ejb21StateLocal ejb21StateEngineLocal;

	@Deployment
	public static JavaArchive createEJBDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "session-context-test.jar");
		jar.addPackages(true, MyData.class.getPackage());
		return jar;
	}

	@Test
	public void testStatelessRemoteNaming() throws Exception {
		logger.info("starting session stateless test");

		logger.info(engineRemote + "");
		int result = engineRemote.go(1);
		assertEquals(engineRemote.getSpeed(), 1);
		logger.info(result + "");
		logger.info(engineRemote + "");
		assertEquals(engineRemote.getSpeed(), 1);
		engineRemote.add(new MyData());
		engineRemote.log();
	}

	@Test
	public void testStatefulRemoteNaming() throws Exception {
		logger.info("starting session stateful remote test");

		logger.info(stateEngineRemote + "");
		int result = stateEngineRemote.go(1);
		assertEquals(stateEngineRemote.getSpeed(), 1);
		logger.info(result + "");
		logger.info(stateEngineRemote + "");
		assertEquals(stateEngineRemote.getSpeed(), 1);
		stateEngineRemote.add(new MyData());
		stateEngineRemote.log();
	}

	@Test
	public void testEjb21StatefulRemoteNaming() throws Exception {
		logger.info("starting session stateful test");

		logger.info(ejb21StateEngineRemote + "");
		int result = ejb21StateEngineRemote.go(1);
		assertEquals(ejb21StateEngineRemote.getSpeed(), 1);
		logger.info(result + "");
		logger.info(ejb21StateEngineRemote + "");
		assertEquals(ejb21StateEngineRemote.getSpeed(), 1);
		ejb21StateEngineRemote.add(new MyData());
		ejb21StateEngineRemote.log();
	}

	@Test
	public void testStatelessLocalNaming() throws Exception {
		logger.info("starting session local stateless test");

		logger.info(engineLocal + "");
		int result = engineLocal.go(1);
		assertEquals(engineLocal.getSpeed(), 1);
		logger.info(result + "");
		logger.info(engineLocal + "");
		assertEquals(engineLocal.getSpeed(), 1);
		engineLocal.add(new MyData());
		engineLocal.log();
	}

	@Test
	public void testStatefulLocalNaming() throws Exception {
		logger.info("starting session local stateless test");

		logger.info(stateEngineLocal + "");
		int result = stateEngineLocal.go(1);
		assertEquals(stateEngineLocal.getSpeed(), 1);
		logger.info(result + "");
		logger.info(stateEngineLocal + "");
		assertEquals(stateEngineLocal.getSpeed(), 1);
		stateEngineLocal.add(new MyData());
		stateEngineLocal.log();
	}

	@Test
	public void testEjb21StatefulLocalNaming() throws Exception {
		logger.info("starting session local stateful test");

		logger.info(ejb21StateEngineLocal + "");
		int result = ejb21StateEngineLocal.go(1);
		assertEquals(ejb21StateEngineLocal.getSpeed(), 1);
		logger.info(result + "");
		logger.info(ejb21StateEngineLocal + "");
		assertEquals(ejb21StateEngineLocal.getSpeed(), 1);
		ejb21StateEngineLocal.add(new MyData());
		ejb21StateEngineLocal.log();
	}
}