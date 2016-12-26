package it.vige.businesscomponents.injection.util;

import static it.vige.businesscomponents.injection.util.ConfigurationKey.PRODUCER;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;

import org.jboss.shrinkwrap.api.Configuration;

public class ConfigurationBean {

	@Produces
	@ConfigurationValue(key = PRODUCER)
	public String produceConfigurationValue(InjectionPoint injectionPoint) {
		Annotated annotated = injectionPoint.getAnnotated();
		ConfigurationValue annotation = annotated.getAnnotation(ConfigurationValue.class);
		if (annotation != null) {
			ConfigurationKey key = annotation.key();
			if (key != null) {
				/*switch (key) {
				case DEFAULT_DIRECTORY:
					return System.getProperty("user.dir");
				case VERSION:
					return JB5n.createInstance(Configuration.class).version();
				case BUILD_TIMESTAMP:
					return JB5n.createInstance(Configuration.class).timestamp();
				default:
					return null;
				}*/
			}
		}
		throw new IllegalStateException("No key for injection point: " + injectionPoint);
	}
}
