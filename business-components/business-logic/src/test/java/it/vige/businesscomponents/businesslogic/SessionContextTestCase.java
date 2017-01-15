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

import it.vige.businesscomponents.businesslogic.context.EngineLocal;
import it.vige.businesscomponents.businesslogic.context.EngineRemote;
import it.vige.businesscomponents.businesslogic.context.MyData;
import it.vige.businesscomponents.businesslogic.context.old.Ejb21Local;
import it.vige.businesscomponents.businesslogic.context.old.Ejb21Remote;

@RunWith(Arquillian.class)
public class SessionContextTestCase {

	private static final Logger logger = getLogger(SessionContextTestCase.class.getName());

	@EJB
	private EngineRemote engineRemote;

	@EJB
	private Ejb21Remote stateEngineRemote;

	@EJB
	private EngineLocal engineLocal;

	@EJB
	private Ejb21Local stateEngineLocal;

	@Deployment
	public static JavaArchive createEJBDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "session-context-test.jar");
		jar.addPackages(true, EngineRemote.class.getPackage());
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
		logger.info("starting session stateful test");

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
		logger.info("starting session local stateful test");

		logger.info(stateEngineLocal + "");
		int result = stateEngineLocal.go(1);
		assertEquals(stateEngineLocal.getSpeed(), 1);
		logger.info(result + "");
		logger.info(stateEngineLocal + "");
		assertEquals(stateEngineLocal.getSpeed(), 1);
		stateEngineLocal.add(new MyData());
		stateEngineLocal.log();
	}
}
