package it.vige.businesscomponents.services;

import static it.vige.businesscomponents.services.Operation.DIV;
import static it.vige.businesscomponents.services.Operation.MULTI;
import static it.vige.businesscomponents.services.Operation.SUB;
import static it.vige.businesscomponents.services.Operation.SUM;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/calculator")
@Stateless
public class Calculator {

	@GET
	@Produces("text/plain")
	public double sum(double... values) {
		return execute(SUM, values);
	}

	@GET
	@Produces("text/plain")
	public double sub(double... values) {
		return execute(SUB, values);

	}

	@GET
	@Produces("text/plain")
	public double multi(double... values) {
		return execute(MULTI, values);

	}

	@GET
	@Produces("text/plain")
	public double divide(double... values) {
		return execute(DIV, values);

	}

	private double execute(Operation operation, double... values) {
		double result = 0;
		for (double value : values)
			result = calculate(operation, result, value);
		return result;
	}

	private double calculate(Operation operation, double result, double value) {
		switch (operation) {
		case SUM:
			result += value;
			break;
		case SUB:
			result -= value;
			break;
		case MULTI:
			result *= value;
			break;
		case DIV:
			result /= value;
			break;
		}
		return result;
	}

}
