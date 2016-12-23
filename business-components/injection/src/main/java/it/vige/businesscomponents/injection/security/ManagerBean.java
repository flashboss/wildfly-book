package it.vige.businesscomponents.injection.security;

import java.util.concurrent.Callable;

import javax.annotation.security.RunAs;
import javax.inject.Named;

@RunAs("Manager")
@Named("manager")
public class ManagerBean implements Caller {

    public <V> V call(Callable<V> callable) throws Exception {
        return callable.call();
    }
}
