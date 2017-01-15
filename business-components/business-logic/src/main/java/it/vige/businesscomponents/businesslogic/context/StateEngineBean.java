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
import javax.ejb.Stateful;

@Stateful(name = "stateMachine")
public class StateEngineBean implements StateEngine {

	private static final Logger logger = getLogger(StateEngineBean.class.getName());
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
		context.getContextData().put("state_engine_data", data);
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
		StateEngineBean stateMachineBean = context.getBusinessObject(getClass());
		boolean isCallerInRole = context.isCallerInRole("admin");
		logger.info("stateMachineBean principal: " + principal);
		logger.info("stateMachineBean contextData:" + contextData);
		logger.info("stateMachineBean ejbHome:" + ejbHome);
		logger.info("stateMachineBean ejbLocalHome:" + ejbLocalHome);
		logger.info("stateMachineBean ejbLocalObject:" + ejbLocalObject);
		logger.info("stateMachineBean ejbObject:" + ejbObject);
		logger.info("stateMachineBean environment:" + environment);
		logger.info("stateMachineBean invokedBusinessInterface:" + invokedBusinessInterface);
		logger.info("stateMachineBean stateMachineBean:" + stateMachineBean);
		logger.info("stateMachineBean isCallerInRole:" + isCallerInRole);
	}

}
