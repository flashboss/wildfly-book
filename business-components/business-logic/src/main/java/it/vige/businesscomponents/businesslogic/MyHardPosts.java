package it.vige.businesscomponents.businesslogic;

import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Remote
@Hard
public class MyHardPosts extends MyPosts {
	
	public MyHardPosts() {
		
	}
}
