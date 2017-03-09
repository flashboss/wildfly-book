package it.vige.realtime.asynchronousejb;

import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.realtime.asynchronousejb.scheduler.SchedulerBean;

@RunWith(Arquillian.class)
public class SchedulerTestCase {

	private static final Logger logger = getLogger(SchedulerTestCase.class.getName());

	@Deployment
	public static JavaArchive createEJBDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "scheduler-ejb-test.jar");
		jar.addPackage(SchedulerBean.class.getPackage());
		return jar;
	}

	@Inject
	private SchedulerBean schedulerBean;

	@Test
	public void testIgnoreResult() {
		logger.info("start test scheduler");
		schedulerBean.getLastAutomaticTimeout();
		schedulerBean.getLastProgrammaticTimeout();
		logger.info("End test scheduler.");
	}
}
