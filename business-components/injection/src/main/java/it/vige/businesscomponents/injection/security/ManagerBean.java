package it.vige.businesscomponents.injection.security;

import java.util.concurrent.Callable;

import javax.annotation.security.RunAs;
import javax.ejb.Stateless;

@Stateless
@RunAs("Manager")
public class ManagerBean implements Caller {

	public <V> V call(Callable<V> callable) throws Exception {
		return callable.call();
	}
}
