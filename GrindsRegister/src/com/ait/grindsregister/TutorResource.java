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



@Path("/tutor")
public class TutorResource {
	private TutorDAO dao = new TutorDAO();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Tutor> getAll(){
		return dao.findAllTutors();
	}
	@GET @Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Tutor getTutorById(@PathParam("id") String id){
		return dao.findById(Integer.parseInt(id));
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveTutor(Tutor tutor){
		dao.saveTutor(tutor);
		return Response.status(Status.OK).entity("hey").build();
	}
	@PUT @Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTutor(Tutor tutor) {
		System.out.println("Updating Tutor: " + tutor.getName());
		dao.updateTutor(tutor);
		return Response.status(Status.OK).entity("hey").build();
	}
	
	@DELETE @Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("id") int id) {
		dao.remove(id);
	}
	
	@PUT @Path("tempPword/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response tempPasswordChange(Tutor tutor) {
		dao.generateTempPassword(tutor);
		return Response.status(Status.OK).entity("hey").build();
	}
	@GET @Path("tempPword/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Tutor getTutById(@PathParam("id") String id){
		return dao.findById(Integer.parseInt(id));
	}
}
