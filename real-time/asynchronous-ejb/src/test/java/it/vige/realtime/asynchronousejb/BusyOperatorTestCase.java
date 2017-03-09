package it.vige.realtime.asynchronousejb;

import static java.lang.System.nanoTime;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.realtime.asynchronousejb.timer.BusyOperator;

@RunWith(Arquillian.class)
public class BusyOperatorTestCase {

	private static final Logger logger = getLogger(BusyOperatorTestCase.class.getName());

	@Deployment
	public static JavaArchive createEJBDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "operator-ejb-test.jar");
		jar.addPackage(BusyOperator.class.getPackage());
		return jar;
	}
	
	@EJB
	private BusyOperator busyOperator;

	@Test
	public void test() throws Exception {
		final CountDownLatch ready = new CountDownLatch(1);

		// This asynchronous method will never exit
		busyOperator.stayBusy(ready);

		// Are you working yet little operator?
		ready.await();

		// OK, The operator is busy

		{ // Timeout Immediately
			final long start = nanoTime();

			try {
				busyOperator.doItNow();

				fail("The operator should be busy");
			} catch (Exception e) {
				logger.log(SEVERE, "", e);
			}

			assertEquals(0, seconds(start));
		}

		{ // Timeout in 5 seconds
			final long start = nanoTime();

			try {
				busyOperator.doItSoon();

				fail("The operator should be busy");
			} catch (Exception e) {
				// the operator is still too busy as expected
			}

			assertEquals(5, seconds(start));
		}

		// This will wait forever, give it a try if you have that long
		// busyOperator.justDoIt();
	}

	private long seconds(long start) {
		return NANOSECONDS.toSeconds(nanoTime() - start);
	}
}
