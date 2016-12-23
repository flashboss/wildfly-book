package it.vige.businesscomponents.injection.interceptor;

import static it.vige.businesscomponents.injection.interceptor.service.History.getItemHistory;
import static java.text.DateFormat.getTimeInstance;

import java.util.Date;

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
public class AuditInterceptor {

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
}
