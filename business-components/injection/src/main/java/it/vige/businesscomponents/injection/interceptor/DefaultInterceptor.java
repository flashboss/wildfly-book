package it.vige.businesscomponents.injection.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import it.vige.businesscomponents.injection.DefaultInterceptorsTestCase;

public class DefaultInterceptor {

	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic) throws Exception {
		DefaultInterceptorsTestCase.executed = true;
		return ic.proceed();
	}
}
