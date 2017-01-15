package it.vige.businesscomponents.businesslogic.context.old;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface Ejb21RemoteHome extends EJBHome {
	  public Ejb21Remote create() throws CreateException, RemoteException;
	  public Ejb21Remote create(String message) throws CreateException, RemoteException;
	  public Ejb21Remote create(Collection<?> messages) throws CreateException, RemoteException;
}
