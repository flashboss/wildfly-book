package it.vige.businesscomponents.services.components;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/simple")
@Stateless
public class SimpleService {

	@GET
	@Path("/valuesget")
	@Produces(TEXT_PLAIN)
	public String valuesget(@QueryParam("OrderID") String orderId, @QueryParam("UserName") String userName) {
		return orderId + "-" + userName;

	}

}
