package it.vige.businesscomponents.injection.interceptor;

import static java.util.logging.Logger.getLogger;

import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Logging
@Priority(1)
public class TraceInterceptor {

	private static final Logger logger = getLogger(TraceInterceptor.class.getName());

	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic) throws Exception {
		String methodName = ic.getMethod().getName();
		logger.info("Tracing " + ic.getTarget().getClass().getSimpleName() + "." + methodName + " method");
		return ic.proceed();
	}

}
