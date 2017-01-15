package it.vige.businesscomponents.businesslogic.context;

import static java.util.logging.Logger.getLogger;

import java.rmi.RemoteException;
import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.Init;
import javax.ejb.Remote;
import javax.ejb.RemoteHome;
import javax.ejb.RemoveException;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;

import it.vige.businesscomponents.businesslogic.context.old.Ejb21Remote;
import it.vige.businesscomponents.businesslogic.context.old.Ejb21RemoteHome;

@Stateful(name = "stateRemoteEngine")
@RemoteHome(value = Ejb21RemoteHome.class)
@Remote(value = Ejb21Remote.class)
public class StateRemoteEngineBean implements Ejb21Remote {

	private static final Logger logger = getLogger(StateRemoteEngineBean.class.getName());
	private int speed;

	@Resource
	private SessionContext context;

	@Init
	public void init() {
		speed = 1;
	}

	@Init
	public void init(String message) {
		speed = 1;
	}

	@Init
	public void init(Collection<?> messages) {
		speed = 1;
	}

	@Override
	public int go(int speed) throws RemoteException {
		return this.speed += speed;
	}

	@Override
	public int retry(int speed) throws RemoteException {
		return this.speed -= speed;
	}

	@Override
	public int getSpeed() throws RemoteException {
		return speed;
	}

	@Override
	public void add(Object data) throws RemoteException {
		context.getContextData().put("state_engine_data", data);
	}

	@Override
	public void log() throws RemoteException {
		Principal principal = context.getCallerPrincipal();
		Map<String, Object> contextData = context.getContextData();
		EJBHome ejbHome = context.getEJBHome();
		EJBObject ejbObject = context.getEJBObject();
		Ejb21Remote stateRemoteEngine = context.getBusinessObject(Ejb21Remote.class);
		boolean isCallerInRole = context.isCallerInRole("admin");
		logger.info("stateRemoteEngineBean principal: " + principal);
		logger.info("stateRemoteEngineBean contextData:" + contextData);
		logger.info("stateRemoteEngineBean ejbHome:" + ejbHome);
		logger.info("stateRemoteEngineBean ejbObject:" + ejbObject);
		logger.info("stateRemoteEngineBean stateRemoteEngineBean:" + stateRemoteEngine);
		logger.info("stateRemoteEngineBean isCallerInRole:" + isCallerInRole);
	}

	@Override
	public EJBHome getEJBHome() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrimaryKey() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() throws RemoteException, RemoveException {
		// TODO Auto-generated method stub

	}

	@Override
	public Handle getHandle() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isIdentical(EJBObject ejbo) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

}
