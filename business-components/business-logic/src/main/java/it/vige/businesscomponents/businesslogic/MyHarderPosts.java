package it.vige.businesscomponents.businesslogic;

import javax.ejb.Remote;
import javax.ejb.Singleton;

@Singleton
@Remote
@Harder
public class MyHarderPosts extends MyPosts {
	
	public MyHarderPosts() {
		
	}
}
