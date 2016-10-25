package com.ait.grindsregister;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


@Path("/grinds")
public class GrindResource {

	private GrindDAO dao = new GrindDAO();
	
	@GET@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Grind> getGrindById(@PathParam("id") int id){
		return dao.findById(id);
	}
	
	@GET@Path("select/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Grind> getGrindByTutorId(@PathParam("id") int id){
		return dao.findByTutorId(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveGrind(Grind grind){
		//grind.setGrindId(dao.getNextId());
		dao.saveGrind(grind);
		return Response.status(Status.OK).entity("hey").build();
	}
	
	@GET@Path("getGrind/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Grind getGrindByGrindId(@PathParam("id") int id){
		return dao.findGrindById(id);
	}
	
	@PUT @Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateGrind(Grind grind) {
		System.out.println("Updating Grind Details");
		dao.updateGrind(grind);
		return Response.status(Status.OK).entity("hey").build();
	}
	
	@DELETE @Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("id") int id) {
		dao.remove(id);
	}
	
	@DELETE @Path("allGrinds/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response removeAllGrinds(@PathParam("id") int id) {
		dao.removeAllGrinds(id);
		return Response.status(Status.OK).entity("hey").build();
	}

}
