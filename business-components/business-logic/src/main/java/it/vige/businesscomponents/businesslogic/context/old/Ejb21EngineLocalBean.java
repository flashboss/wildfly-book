package it.vige.businesscomponents.businesslogic.context.old;

import static java.util.logging.Logger.getLogger;

import java.security.Principal;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.Init;
import javax.ejb.Local;
import javax.ejb.LocalHome;
import javax.ejb.RemoveException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

@Stateless(name = "ejb21EngineLocal")
@LocalHome(value = Ejb21LocalHome.class)
@Local(value = Ejb21Local.class)
public class Ejb21EngineLocalBean implements Ejb21Local {

	private static final Logger logger = getLogger(Ejb21EngineLocalBean.class.getName());
	private int speed;

	@Resource
	private SessionContext context;

	@Init
	public void init() {
		speed = 1;
	}

	@Override
	public int go(int speed) {
		return this.speed += speed;
	}

	@Override
	public int retry(int speed) {
		return this.speed -= speed;
	}

	@Override
	public int getSpeed() {
		return speed;
	}

	@Override
	public void add(Object data) {
		context.getContextData().put("_engine_data", data);
	}

	public void log() {
		Principal principal = context.getCallerPrincipal();
		Map<String, Object> contextData = context.getContextData();
		EJBLocalHome ejbLocalHome = context.getEJBLocalHome();
		EJBLocalObject ejbLocalObject = context.getEJBLocalObject();
		Ejb21Local LocalEngine = context.getBusinessObject(Ejb21Local.class);
		boolean isCallerInRole = context.isCallerInRole("admin");
		logger.info("ejb21LocalEngineBean principal: " + principal);
		logger.info("ejb21LocalEngineBean contextData:" + contextData);
		logger.info("ejb21LocalEngineBean ejbLocalHome:" + ejbLocalHome);
		logger.info("ejb21LocalEngineBean ejbLocalObject:" + ejbLocalObject);
		logger.info("ejb21LocalEngineBean LocalEngineBean:" + LocalEngine);
		logger.info("ejb21LocalEngineBean isCallerInRole:" + isCallerInRole);
	}

	@Override
	public EJBLocalHome getEJBLocalHome() throws EJBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrimaryKey() throws EJBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() throws RemoveException, EJBException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isIdentical(EJBLocalObject obj) throws EJBException {
		// TODO Auto-generated method stub
		return false;
	}

}
