package it.vige.businesscomponents.injection;

import static java.util.logging.Logger.getLogger;
import static javax.enterprise.inject.spi.CDI.current;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.logging.Logger;

import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.CDIProvider;
import javax.enterprise.inject.spi.Producer;
import javax.enterprise.inject.spi.ProducerFactory;
import javax.enterprise.inject.spi.Unmanaged;
import javax.enterprise.inject.spi.Unmanaged.UnmanagedInstance;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.businesscomponents.injection.inject.spi.AnotherFactory;
import it.vige.businesscomponents.injection.inject.spi.Car;
import it.vige.businesscomponents.injection.inject.spi.NamedAnnotation;
import it.vige.businesscomponents.injection.inject.spi.Taxi;
import it.vige.businesscomponents.injection.inject.spi.TaxiManager;
import it.vige.businesscomponents.injection.inject.spi.Toy;

@RunWith(Arquillian.class)
public class InjectSPITestCase {

	private static final Logger logger = getLogger(InjectSPITestCase.class.getName());

	@Deployment
	public static JavaArchive createJavaDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "inject-spi-test.jar");
		jar.addPackage(Car.class.getPackage());
		jar.addAsManifestResource(INSTANCE, "beans.xml");
		return jar;
	}

	/**
	 * Tests default annotation in a jar archive
	 */
	@Test
	public void testCDI() {
		logger.info("starting inject spi test");
		CDI<Object> cdi = current();
		BeanManager beanManager = cdi.select(BeanManager.class).get();
		assertNotNull("exists", beanManager);
		BeanManager beanManager2 = cdi.getBeanManager();
		assertEquals("same instance", beanManager, beanManager2);
		assertNotNull("exists", beanManager);
		Car car = cdi.select(Car.class).get();
		assertNotNull("exists", car);
		Car car2 = cdi.select(Car.class).get();
		assertNotNull("exists", car2);
		assertNotEquals("different instances. Car is a simple managed bean", car, car2);

		Taxi taxi = current().select(Taxi.class, new NamedAnnotation("taxi")).get();
		assertNotNull("exists", taxi);

		Iterator<Object> iterator = cdi.iterator();
		while (iterator.hasNext()) {
			try {
				Object instance = iterator.next();
				logger.info("from CDI iterator: " + instance + "");
			} catch (Exception ex) {

			}
		}
		assertTrue("all is ok", cdi.isAmbiguous());
		assertFalse("all is ok", cdi.isUnsatisfied());

		CDIProvider cdiProvider = new CDIProvider() {

			@Override
			public CDI<Object> getCDI() {
				return cdi;
			}

		};
		assertEquals(cdiProvider.getCDI(), cdi);
	}

	@Test
	public void testUnmanaged() {
		Unmanaged<Car> unmanaged = new Unmanaged<Car>(Car.class);
		UnmanagedInstance<Car> unmanagedCar = unmanaged.newInstance();
		assertNotNull("exists", unmanagedCar);
		UnmanagedInstance<Car> carProduced = unmanagedCar.produce();
		Car car = carProduced.inject().get();
		Car car2 = carProduced.inject().get();
		assertEquals("same instances. Car is a simple managed bean", car, car2);

		Unmanaged<TaxiManager> unmanaged2 = new Unmanaged<TaxiManager>(TaxiManager.class);
		UnmanagedInstance<TaxiManager> unmanagedTaxi = unmanaged2.newInstance();
		assertNotNull("exists", unmanagedTaxi);
		UnmanagedInstance<TaxiManager> taxiProduced = unmanagedTaxi.produce();
		TaxiManager taxiManager = taxiProduced.inject().get();
		TaxiManager taxiManager2 = taxiProduced.inject().get();
		assertEquals("same instances. TaxiManager is a simple singleton", taxiManager, taxiManager2);

		Unmanaged<TaxiManager> unmanaged3 = new Unmanaged<TaxiManager>(TaxiManager.class);
		UnmanagedInstance<TaxiManager> unmanagedTaxi3 = unmanaged3.newInstance();
		UnmanagedInstance<TaxiManager> unmanagedTaxi4 = unmanaged3.newInstance();
		assertNotNull("exists", unmanagedTaxi3);
		assertNotNull("exists", unmanagedTaxi4);
		TaxiManager taxiManager3 = unmanagedTaxi3.produce().inject().get();
		TaxiManager taxiManager4 = unmanagedTaxi4.produce().inject().get();
		assertNotEquals("different instances. TaxiManager is a new singleton", unmanagedTaxi3, unmanagedTaxi4);
		assertNotEquals("different instances. TaxiManager is a new singleton", taxiManager3, taxiManager4);

	}

	@Test
	public void testProducer() {
		logger.info("starting producer spi test");
		CDI<Object> cdi = current();
		BeanManager beanManager = cdi.select(BeanManager.class).get();

		AnnotatedField<? super AnotherFactory> field = this
				.<AnotherFactory, AnnotatedField<AnotherFactory>>getAnnotatedMember(AnotherFactory.class, "jessie",
						beanManager);
		Bean<AnotherFactory> declaringBean = cast(beanManager.resolve(beanManager.getBeans(AnotherFactory.class)));
		ProducerFactory<AnotherFactory> producerFactory = beanManager.getProducerFactory(field, declaringBean);
		Producer<Toy> producer = cast(producerFactory.createProducer(null));
		assertNotNull(producer);
		assertTrue(producer.getInjectionPoints().isEmpty());
		Toy jessie = producer.produce(beanManager.<Toy>createCreationalContext(null));
		assertEquals("Jessie", jessie.getName());
	}

	@SuppressWarnings("unchecked")
	private <X, A extends AnnotatedMember<? super X>> A getAnnotatedMember(Class<X> javaClass, String memberName,
			BeanManager beanManager) {
		AnnotatedType<X> type = beanManager.createAnnotatedType(javaClass);
		for (AnnotatedField<? super X> field : type.getFields()) {
			if (field.getJavaMember().getName().equals(memberName)) {
				return (A) field;
			}
		}
		for (AnnotatedMethod<? super X> method : type.getMethods()) {
			if (method.getJavaMember().getName().equals(memberName)) {
				return (A) method;
			}
		}
		throw new IllegalArgumentException("Member " + memberName + " not found on " + javaClass);
	}

	@SuppressWarnings("unchecked")
	private <T> T cast(Object obj) {
		return (T) obj;
	}
}
