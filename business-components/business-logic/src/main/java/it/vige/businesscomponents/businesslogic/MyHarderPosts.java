package it.vige.businesscomponents.businesslogic;

import static javax.ejb.ConcurrencyManagementType.CONTAINER;
import static javax.ejb.LockType.READ;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.Lock;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Remote
@Harder
@Startup
@ConcurrencyManagement(CONTAINER)
@Lock(READ)
public class MyHarderPosts extends MyPosts {

	public MyHarderPosts() {

	}
}
