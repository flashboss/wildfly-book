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

import it.vige.businesscomponents.businesslogic.context.Engine;
import it.vige.businesscomponents.businesslogic.context.MyData;
import it.vige.businesscomponents.businesslogic.context.StateEngine;

@RunWith(Arquillian.class)
public class SessionContextTestCase {

	private static final Logger logger = getLogger(SessionContextTestCase.class.getName());

	@EJB
	private Engine engine;

	@EJB
	private StateEngine stateEngine;

	@Deployment
	public static JavaArchive createEJBDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "session-context-test.jar");
		jar.addPackage(Engine.class.getPackage());
		return jar;
	}

	@Test
	public void testStatelessRemoteNaming() throws Exception {
		logger.info("starting session stateless test");

		logger.info(engine + "");
		int result = engine.go(1);
		assertEquals(engine.getSpeed(), 1);
		logger.info(result + "");
		logger.info(engine + "");
		assertEquals(engine.getSpeed(), 1);
		engine.add(new MyData());
		engine.log();
	}

	@Test
	public void testStatefulRemoteNaming() throws Exception {
		logger.info("starting session stateful test");

		logger.info(stateEngine + "");
		int result = stateEngine.go(1);
		assertEquals(stateEngine.getSpeed(), 1);
		logger.info(result + "");
		logger.info(stateEngine + "");
		assertEquals(stateEngine.getSpeed(), 0);
		stateEngine.add(new MyData());
		stateEngine.log();
	}
}
