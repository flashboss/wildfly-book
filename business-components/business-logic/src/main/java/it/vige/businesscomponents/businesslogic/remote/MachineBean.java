package it.vige.businesscomponents.businesslogic.remote;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

@Stateless(name="machine")
public class MachineBean implements Machine {
	
	@Resource
	private SessionContext context;

	@Override
	public int go(int speed) {
		return speed++;
	}

	@Override
	public int retry(int speed) {
		return speed--;
	}

}
