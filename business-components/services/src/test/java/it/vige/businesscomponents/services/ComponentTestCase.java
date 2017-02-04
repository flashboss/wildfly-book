package it.vige.businesscomponents.services;

import static java.util.logging.Logger.getLogger;
import static javax.ws.rs.RuntimeType.CLIENT;
import static javax.ws.rs.client.ClientBuilder.newClient;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.businesscomponents.services.components.MyComponent;

@RunWith(Arquillian.class)
public class ComponentTestCase {

	private static final Logger logger = getLogger(ComponentTestCase.class.getName());

	@ArquillianResource
	private URL url;

	@Deployment
	public static WebArchive createWebDeployment() {
		final WebArchive war = create(WebArchive.class, "component-test.war");
		war.addPackage(MyComponent.class.getPackage());
		war.addAsWebInfResource(INSTANCE, "beans.xml");
		return war;
	}

	@Test
	public void testConfiguration() throws Exception {
		logger.info("start REST Configuration test");
		Client client = newClient();
		Configuration configuration = client.getConfiguration();
		Set<Class<?>> classes = configuration.getClasses();
		for (Class<?> clazz : classes) {
			assertTrue("verify if the class is a rest component or provider",
					MessageBodyReader.class.isAssignableFrom(clazz) || MessageBodyWriter.class.isAssignableFrom(clazz)
							|| clazz.isAnnotationPresent(Provider.class)
							|| DynamicFeature.class.isAssignableFrom(clazz));
			Map<Class<?>, Integer> contracts = configuration.getContracts(clazz);
			assertFalse("each class has different contracts", contracts.isEmpty());
			for (Class<?> contract : contracts.keySet()) {
				int value = contracts.get(contract);
				assertTrue("verify if the contract is a rest component or provider",
						value == 5000 || value == 4000 || value == 3000 || value == 0);
			}
		}
		Set<Object> instances = configuration.getInstances();
		assertTrue("by default there are not instances", instances.isEmpty());
		Map<String, Object> properties = configuration.getProperties();
		assertTrue("by default there are not properties", properties.isEmpty());
		MyComponent myComponent = new MyComponent();
		client.register(myComponent);
		instances = configuration.getInstances();
		assertFalse("Added instance", instances.isEmpty());
		for (Object instance : instances) {
			if (instance instanceof MyComponent)
				assertTrue("MyComponent is registered and active", configuration.isEnabled((Feature) instance));
		}
		assertEquals("Added property through MyComponent", 1, properties.size());
		boolean property = (Boolean) properties.get("configured_myComponent");
		assertEquals("configured_myComponent ok!", true, property);
		assertEquals("type CLIENT by default", CLIENT, configuration.getRuntimeType());
	}

	@Test
	public void testRegister() throws Exception {
		logger.info("start Register test");
		Client client = newClient();
		Map<Class<?>, Integer> myContracts = new HashMap<Class<?>, Integer>();
		myContracts.put(Feature.class, 1200);
		client.register(MyComponent.class, myContracts);
		Configuration configuration = client.getConfiguration();
		Set<Class<?>> classes = configuration.getClasses();
		for (Class<?> clazz : classes) {
			if (MyComponent.class.isAssignableFrom(clazz)) {
				Map<Class<?>, Integer> contracts = configuration.getContracts(clazz);
				int priority = contracts.get(Feature.class);
				assertTrue("Only standard: Feature, DynamicFeature, WriterInterceptor, "
						+ "ReaderInterceptor, ContainerResponseFilter, " + "ContainerRequestFilter, "
						+ "ClientResponseFilter, ClientRequestFilter, " + "ExceptionMapper, MessageBodyWriter, "
						+ "MessageBodyReader,ParamConverterProvider or implemented: InjectorFactory, "
						+ "StringParameterUnmarshaller, StringConverter, " + "ContextResolver, PostProcessInterceptor, "
						+ "PreProcessInterceptor, ClientExecutionInterceptor, ClientExceptionMapper"
						+ "can be registered as contracts. Registered priority", priority == 1200);
			}
		}
	}

}
