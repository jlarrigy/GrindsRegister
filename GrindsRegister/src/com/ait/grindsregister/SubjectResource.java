package com.ait.grindsregister;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/subjects")
public class SubjectResource {
	private SubjectDAO dao = new SubjectDAO();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Subject> getAll(){
		return dao.findAllSubjects();
	}
	
	@GET @Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Subject getSubjectById(@PathParam("id") int id){
		return dao.findById(id);
	}

}
