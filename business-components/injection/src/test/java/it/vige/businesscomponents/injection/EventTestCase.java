package it.vige.businesscomponents.injection;

import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;

import java.util.logging.Logger;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.businesscomponents.injection.event.Bill;

@RunWith(Arquillian.class)
public class EventTestCase {

	private static final Logger logger = getLogger(EventTestCase.class.getName());

    @Inject
    private Event<Bill> billEvent;
    
	@Deployment
	public static JavaArchive createWebDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "event-test.jar");
		jar.addPackage(Bill.class.getPackage());
		jar.addAsManifestResource(INSTANCE, "beans.xml");
		return jar;
	}

	/**
	 * Tests simple sql injection in a jar archive
	 */
	@Test
	public void testTransaction() {
		logger.info("staring transaction event test");
		Bill bill = new Bill();
		bill.setId(1);
		bill.setTitle("ticket for the concert of Franco Battiato");
		billEvent.fire(bill);
	}
}
