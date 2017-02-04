package it.vige.businesscomponents.services.otherhttp;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/receiver")
@Stateless
public class HttpReceiver {

	@OPTIONS
	@Path("/options")
	@Produces(TEXT_PLAIN)
	public double options() {
		return 88.99;
	}

	@DELETE
	@Path("/delete")
	@Produces(TEXT_PLAIN)
	public double delete() {
		return 99.66;

	}

}
