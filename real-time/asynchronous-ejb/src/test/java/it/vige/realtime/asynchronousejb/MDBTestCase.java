package it.vige.realtime.asynchronousejb;

import static java.util.logging.Logger.getLogger;
import static javax.jms.Session.AUTO_ACKNOWLEDGE;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.realtime.asynchronousejb.messagebean.WorkingBean;

@RunWith(Arquillian.class)
public class MDBTestCase {

	private static final Logger logger = getLogger(MDBTestCase.class.getName());

	@Deployment
	public static JavaArchive createEJBDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "mdb-ejb-test.jar");
		jar.addPackage(WorkingBean.class.getPackage());
		return jar;
	}
	
	private final static String QUEUE_LOOKUP = "java:/jms/queue/ExpiryQueue";

	@EJB
	private WorkingBean workingBean;

    @Resource(mappedName = "/ConnectionFactory")
    private ConnectionFactory cf;

	@Resource(mappedName = QUEUE_LOOKUP)
	private Queue queue;

	@Test
	public void test() throws Exception {
		logger.info("starting message driven bean test");
		JMSContext context = cf.createContext(AUTO_ACKNOWLEDGE);
		context.createProducer().send(queue, "need a pause");
	}
}
