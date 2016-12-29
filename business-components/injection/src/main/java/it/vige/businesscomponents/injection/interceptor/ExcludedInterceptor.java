package it.vige.businesscomponents.injection.interceptor;

import static java.util.logging.Logger.getLogger;

import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import it.vige.businesscomponents.injection.interceptor.service.Item;

@Interceptor
public class ExcludedInterceptor {

	private static final Logger logger = getLogger(ExcludedInterceptor.class.getName());

	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic) throws Exception {
		String methodName = ic.getMethod().getName();
		logger.info("Executing " + ic.getTarget().getClass().getSimpleName() + "." + methodName + " method");
		Object[] parameters = (Object[]) ic.getParameters();
		logger.info("parameters are: " + parameters.length);
		if (parameters.length == 1) {
			Item item = (Item) parameters[0];
			logger.info("item: " + item.getName());
		}
		return ic.proceed();
	}

	@AroundTimeout
	public Object aroundTimeout(InvocationContext ic) throws Exception {
		logger.info("Executing " + ic.getTimer());
		Object[] parameters = (Object[]) ic.getParameters();
		logger.info("parameters are: " + parameters.length);
		return ic.proceed();
	}
}
