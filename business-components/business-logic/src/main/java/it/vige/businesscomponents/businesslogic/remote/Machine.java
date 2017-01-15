package it.vige.businesscomponents.businesslogic.remote;

import javax.ejb.Remote;

@Remote
public interface Machine {

	int go(int speed);

	int retry(int speed);
}
