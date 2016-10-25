package com.ait.grindsregister;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class GrindDaoTest {
	
	private GrindDAO grindDao = new GrindDAO();
	private TutorDAO tutorDao = new TutorDAO();
	
	@Test
	public void testFindById() {
		List<Grind> grindList = new ArrayList<Grind>();
		
		grindList = grindDao.findById(1);//Finds grinds by subject id
		
		Grind grind = grindList.get(0);
		
		Assert.assertEquals(1, grind.getGrindId());
		Assert.assertEquals(1, grind.getTutorId());
		Assert.assertEquals(1, grind.getSubjectId());
		Assert.assertEquals("€30",grind.getPrice());
		Assert.assertEquals("Financial & Management Accounting, Most Undergraduate Subjects", grind.getDescription());
	}
	
	@Test
	public void testFindByTutId(){
		List<Grind> grindList = new ArrayList<Grind>();
		
		grindList = grindDao.findByTutorId(1);
		
		Grind grind = grindList.get(0);
		
		Assert.assertEquals(1, grind.getGrindId());
		Assert.assertEquals(1, grind.getTutorId());
		Assert.assertEquals(1, grind.getSubjectId());
		Assert.assertEquals("€30",grind.getPrice());
		Assert.assertEquals("Financial & Management Accounting, Most Undergraduate Subjects", grind.getDescription());
	}
	
	@Test
	public void testGetGrindByGrindId(){
		Grind grind = grindDao.findGrindById(1);
		Assert.assertEquals(1, grind.getGrindId());
		Assert.assertEquals(1, grind.getTutorId());
		Assert.assertEquals(1, grind.getSubjectId());
		Assert.assertEquals("€30",grind.getPrice());
		Assert.assertEquals("Financial & Management Accounting, Most Undergraduate Subjects", grind.getDescription());
	}
	
	@Test
	public void testSaveGrind(){
		Grind grind = new Grind();
		grind.setGrindId(1234);
		grind.setTutorId(1);
		grind.setSubjectId(5);
		grind.setPrice("TEST");
		grind.setDescription("TEST SAVE GRIND");
		
		grindDao.saveGrind(grind);
		
		List<Grind> grindList = new ArrayList<Grind>();
		grindList = grindDao.findByTutorId(1);
		
		Grind newGrind = new Grind();
		for(int i=0; i<(grindList.size()); i++){
			newGrind = grindList.get(i);
			if(newGrind.getPrice() == "TEST" && newGrind.getDescription() == "TEST SAVE GRIND"){
				break;
			}
		}
		
		Assert.assertEquals(1, newGrind.getTutorId());
		Assert.assertEquals(5, newGrind.getSubjectId());
		Assert.assertEquals("TEST", newGrind.getPrice());
		Assert.assertEquals("TEST SAVE GRIND", newGrind.getDescription());
		
		int grindId = newGrind.getGrindId();
		
		grindDao.remove(grindId);
	}
	
	@Test
	public void testUpdateGrind(){
		Grind grind = new Grind();
		grind.setGrindId(1234);
		grind.setTutorId(1);
		grind.setSubjectId(5);
		grind.setPrice("TEST");
		grind.setDescription("TEST SAVE GRIND");
		
		grindDao.saveGrind(grind);
		
		List<Grind> grindList = new ArrayList<Grind>();
		grindList = grindDao.findByTutorId(1);
		
		Grind newGrind = new Grind();
		for(int i=0; i<(grindList.size()); i++){
			newGrind = grindList.get(i);
			if(newGrind.getPrice() == "TEST" && newGrind.getDescription() == "TEST SAVE GRIND"){
				break;
			}
		}

		Assert.assertEquals(1, newGrind.getTutorId());
		Assert.assertEquals(5, newGrind.getSubjectId());
		Assert.assertEquals("TEST", newGrind.getPrice());
		Assert.assertEquals("TEST SAVE GRIND", newGrind.getDescription());
		
		newGrind.setPrice("GRIND UPDATE TEST");
		newGrind.setDescription("GRIND UPDATE TEST");
		grindDao.updateGrind(newGrind);
		
		int grindId = newGrind.getGrindId();
		
		newGrind = grindDao.findGrindById(grindId);
		Assert.assertEquals(1, newGrind.getTutorId());
		Assert.assertEquals(5, newGrind.getSubjectId());
		Assert.assertEquals("GRIND UPDATE TEST",newGrind.getPrice());
		Assert.assertEquals("GRIND UPDATE TEST", newGrind.getDescription());
		
		grindDao.remove(grindId);
	}
	
	@Test
	public void testRemove(){
		Grind grind = new Grind();
		grind.setGrindId(1234);
		grind.setTutorId(1);
		grind.setSubjectId(5);
		grind.setPrice("TEST REMOVE GRIND");
		grind.setDescription("TEST REMOVE GRIND");
		
		grindDao.saveGrind(grind);
		
		List<Grind> grindList = new ArrayList<Grind>();
		grindList = grindDao.findByTutorId(1);
		
		Grind newGrind = new Grind();
		for(int i=0; i<(grindList.size()); i++){
			newGrind = grindList.get(i);
			if(newGrind.getPrice() == "TEST REMOVE GRIND"){
				break;
			}
		}
		
		Assert.assertEquals(1, newGrind.getTutorId());
		Assert.assertEquals(5, newGrind.getSubjectId());
		Assert.assertEquals("TEST REMOVE GRIND",newGrind.getPrice());
		Assert.assertEquals("TEST REMOVE GRIND", newGrind.getDescription());
		
		int grindId = newGrind.getGrindId();
		
		grindDao.remove(grindId);
		
		Assert.assertNull(grindDao.findGrindById(grindId));
		
	}
	
	@Test
	public void testRemoveAllGrinds(){
		Tutor tutor = new Tutor();
		tutor.setId(1234);
		tutor.setName("REMOVE ALL GRINDS TEST");
		tutor.setEmail("TUTORS@GRINDSREGISTER.TEST");
		tutor.setPhone("1234567");
		tutor.setUsername("tutor1");
		tutor.setPassword("password1");
		
		tutorDao.saveTutor(tutor);
		
		Tutor newTutor = new Tutor();
		
		List<Tutor> tutorList = new ArrayList<Tutor>();
		tutorList = tutorDao.findAllTutors();
		
		for(int i=0; i<(tutorList.size()); i++){
			newTutor = tutorList.get(i);
			if(newTutor.getName() == "REMOVE ALL GRINDS TEST"){
				break;
			}
		}
		
		int tutorId = newTutor.getId();
		
		Grind grind = new Grind();
		grind.setGrindId(1234);
		grind.setTutorId(tutorId);
		grind.setSubjectId(5);
		grind.setPrice("TEST REMOVE ALL GRINDS1");
		grind.setDescription("TEST REMOVE ALL GRIND1");
		
		grindDao.saveGrind(grind);
		
		List<Grind> grindList = new ArrayList<Grind>();
		grindList = grindDao.findByTutorId(tutorId);
		
		Grind newGrind = new Grind();
		for(int i=0; i<(grindList.size()); i++){
			newGrind = grindList.get(i);
			if(newGrind.getPrice() == "TEST REMOVE ALL GRINDS2"){
				break;
			}
		}
		
		grindDao.removeAllGrinds(tutorId);
		
		int grindId = newGrind.getGrindId();
		
		Assert.assertNull(grindDao.findGrindById(grindId));
	}
}
