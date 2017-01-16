package it.vige.businesscomponents.businesslogic;

import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Remote
@Harder
@Startup
public class MyHarderPosts extends MyPosts {
	
	public MyHarderPosts() {
		
	}
}
