package it.vige.businesscomponents.businesslogic.context;

import static java.util.logging.Logger.getLogger;

import java.security.Principal;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

@Stateless(name = "machine")
public class EngineBean implements Engine {

	private static final Logger logger = getLogger(EngineBean.class.getName());

	private int speed;

	@Resource
	private SessionContext context;

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

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public void add(Object data) {
		context.getContextData().put("engine_data", data);
	}

	@Override
	public void log() {
		Principal principal = context.getCallerPrincipal();
		Map<String, Object> contextData = context.getContextData();
		EJBHome ejbHome = context.getEJBHome();
		EJBLocalHome ejbLocalHome = context.getEJBLocalHome();
		EJBLocalObject ejbLocalObject = context.getEJBLocalObject();
		EJBObject ejbObject = context.getEJBObject();
		Properties environment = context.getEnvironment();
		Class<?> invokedBusinessInterface = context.getInvokedBusinessInterface();
		EngineBean stateMachineBean = context.getBusinessObject(getClass());
		boolean isCallerInRole = context.isCallerInRole("admin");
		logger.info("machineBean principal: " + principal);
		logger.info("machineBean contextData:" + contextData);
		logger.info("machineBean ejbHome:" + ejbHome);
		logger.info("machineBean ejbLocalHome:" + ejbLocalHome);
		logger.info("machineBean ejbLocalObject:" + ejbLocalObject);
		logger.info("machineBean ejbObject:" + ejbObject);
		logger.info("machineBean environment:" + environment);
		logger.info("machineBean invokedBusinessInterface:" + invokedBusinessInterface);
		logger.info("machineBean stateMachineBean:" + stateMachineBean);
		logger.info("machineBean isCallerInRole:" + isCallerInRole);
	}

}
