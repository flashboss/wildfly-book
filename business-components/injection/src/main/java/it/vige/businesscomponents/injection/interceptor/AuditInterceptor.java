package it.vige.businesscomponents.injection.interceptor;

import static it.vige.businesscomponents.injection.interceptor.service.History.getItemHistory;
import static java.text.DateFormat.getTimeInstance;

import java.util.Date;

import javax.annotation.Priority;
import javax.enterprise.inject.Intercepted;
import javax.enterprise.inject.spi.Bean;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * AuditInterceptor binds to {@link @Audit} annotation, so methods or beans
 * which @Audit annotation is applied to, will be intercepted.
 *
 * @author ievgen.shulga
 */
@Interceptor
@Audit
@Priority(2)
public class AuditInterceptor {

	@Inject
	@Intercepted
	private Bean<?> bean;

	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic) throws Exception {
		String methodName = ic.getMethod().getName();
		String time = getTimeInstance().format(new Date());
		if (methodName.equals("create")) {
			getItemHistory().add(String.format("Item created at %s", time));
		} else if (methodName.equals("getList")) {
			getItemHistory().add(String.format("List of Items retrieved at %s", time));
		}
		return ic.proceed();
	}

	public Bean<?> getBean() {
		return bean;
	}
}
