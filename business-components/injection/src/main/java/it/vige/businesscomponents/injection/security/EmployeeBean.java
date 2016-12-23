package it.vige.businesscomponents.injection.security;

import java.util.concurrent.Callable;

import javax.annotation.security.RunAs;
import javax.ejb.Stateless;

@Stateless
@RunAs("Employee")
public class EmployeeBean implements Caller {

	public <V> V call(Callable<V> callable) throws Exception {
		return callable.call();
	}
}
