package it.vige.businesscomponents.businesslogic.context.old;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface Ejb21LocalHome extends EJBLocalHome {
	public Ejb21Local create() throws CreateException;
	public Ejb21Local create(String message) throws CreateException;
	public Ejb21Local create(Collection<?> messages) throws CreateException;
}
