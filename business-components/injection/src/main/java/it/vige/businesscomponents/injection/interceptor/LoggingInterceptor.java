package it.vige.businesscomponents.injection.interceptor;

import static java.util.logging.Logger.getLogger;

import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * LoggingInterceptor binds to {@link @Logging} annotation, so methods or beans
 * which @Logging annotation is applied to, will be intercepted.
 *
 * @author ievgen.shulga
 */
@Interceptor
@Logging
@Priority(0)
public class LoggingInterceptor {

	private static final Logger logger = getLogger(LoggingInterceptor.class.getName());

	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic) throws Exception {
		String methodName = ic.getMethod().getName();
		logger.info("Executing " + ic.getTarget().getClass().getSimpleName() + "." + methodName + " method");
		return ic.proceed();
	}
}
