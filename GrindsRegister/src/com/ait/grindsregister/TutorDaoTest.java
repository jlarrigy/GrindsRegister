package com.ait.grindsregister;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class TutorDaoTest {
	
	private TutorDAO tutorDao = new TutorDAO();
	
	@Test
	public void testFindAllTutors() {
		Tutor tutor = new Tutor();
		tutor.setId(1234);
		tutor.setName("TUTORS TEST");
		tutor.setEmail("TUTORS@GRINDSREGISTER.TEST");
		tutor.setPhone("1234567");
		tutor.setUsername("tutor1");
		tutor.setPassword("password1");
		
		tutorDao.saveTutor(tutor);
		
		List<Tutor> tutorList = new ArrayList<Tutor>();
		tutorList = tutorDao.findAllTutors();
		
		Tutor newTutor = new Tutor();
		for(int i=0; i<(tutorList.size()); i++){
			newTutor = tutorList.get(i);
			if(newTutor.getEmail() == "TUTORS@GRINDSREGISTER.TEST"){
				break;
			}
		}
		
		Assert.assertEquals("TUTORS TEST", newTutor.getName());
		Assert.assertEquals("TUTORS@GRINDSREGISTER.TEST", newTutor.getEmail());
		Assert.assertEquals("1234567", newTutor.getPhone());
		Assert.assertEquals("tutor1", newTutor.getUsername());
		Assert.assertEquals("password1", newTutor.getPassword());
		
		tutorDao.remove(newTutor.getId());
	}
	
	@Test
	public void testFindById(){
		Tutor tutor = new Tutor();
		tutor.setId(1234);
		tutor.setName("TUTORS TEST");
		tutor.setEmail("TUTORS@GRINDSREGISTER.TEST");
		tutor.setPhone("1234567");
		tutor.setUsername("tutor1");
		tutor.setPassword("password1");
		
		tutorDao.saveTutor(tutor);
		
		List<Tutor> tutorList = new ArrayList<Tutor>();
		tutorList = tutorDao.findAllTutors();
		
		Tutor newTutor = new Tutor();
		for(int i=0; i<(tutorList.size()); i++){
			newTutor = tutorList.get(i);
			if(newTutor.getEmail() == "TUTORS@GRINDSREGISTER.TEST"){
				break;
			}
		}
		
		int tutorId = newTutor.getId();
		
		Tutor testTutor = tutorDao.findById(tutorId);
		
		Assert.assertEquals("TUTORS TEST", testTutor.getName());
		Assert.assertEquals("1234567", testTutor.getPhone());
		Assert.assertEquals("tutor1", testTutor.getUsername());
		Assert.assertEquals("password1", testTutor.getPassword());
		
		tutorDao.remove(tutorId);
	}
	
	@Test
	public void testSaveTutor(){
		Tutor tutor = new Tutor();
		tutor.setId(1234);
		tutor.setName("TUTORS TEST");
		tutor.setEmail("TUTORS@GRINDSREGISTER.TEST");
		tutor.setPhone("1234567");
		tutor.setUsername("tutor1");
		tutor.setPassword("password1");
		
		tutorDao.saveTutor(tutor);
		
		List<Tutor> tutorList = new ArrayList<Tutor>();
		tutorList = tutorDao.findAllTutors();
		
		Tutor newTutor = new Tutor();
		for(int i=0; i<(tutorList.size()); i++){
			newTutor = tutorList.get(i);
			if(newTutor.getEmail() == "TUTORS@GRINDSREGISTER.TEST"){
				break;
			}
		}
		
		int tutorId = newTutor.getId();
		
		Assert.assertEquals("TUTORS TEST", newTutor.getName());
		Assert.assertEquals("TUTORS@GRINDSREGISTER.TEST", newTutor.getEmail());
		Assert.assertEquals("1234567", newTutor.getPhone());
		Assert.assertEquals("tutor1", newTutor.getUsername());
		Assert.assertEquals("password1", newTutor.getPassword());
		
		tutorDao.remove(tutorId);
	}
	
	@Test
	public void testUpdateTutor(){
		Tutor tutor = new Tutor();
		tutor.setId(1234);
		tutor.setName("TUTORS TEST");
		tutor.setEmail("TUTORS@GRINDSREGISTER.TEST");
		tutor.setPhone("1234567");
		tutor.setUsername("tutor1");
		tutor.setPassword("password1");
		
		tutorDao.saveTutor(tutor);
		
		List<Tutor> tutorList = new ArrayList<Tutor>();
		tutorList = tutorDao.findAllTutors();
		
		Tutor newTutor = new Tutor();
		for(int i=0; i<(tutorList.size()); i++){
			newTutor = tutorList.get(i);
			if(newTutor.getEmail() == "TUTORS@GRINDSREGISTER.TEST"){
				break;
			}
		}
		
		int tutorId = newTutor.getId();
		
		newTutor.setName("NAME CHANGED");
		newTutor.setEmail("email@changed.test");
		newTutor.setPhone("1234567");
		newTutor.setUsername("Username Changed");
		newTutor.setPassword("Password Changed");
		
		tutorDao.updateTutor(newTutor);
		
		Tutor testTutor = tutorDao.findById(tutorId);
		
		Assert.assertEquals("NAME CHANGED", testTutor.getName());
		Assert.assertEquals("email@changed.test", testTutor.getEmail());
		Assert.assertEquals("1234567", testTutor.getPhone());
		Assert.assertEquals("Username Changed", testTutor.getUsername());
		Assert.assertEquals("Password Changed", testTutor.getPassword());
		
		tutorDao.remove(tutorId);
	}
	
	@Test
	public void testRemove(){
		Tutor tutor = new Tutor();
		tutor.setId(1234);
		tutor.setName("TUTORS TEST");
		tutor.setEmail("TUTORS@GRINDSREGISTER.TEST");
		tutor.setPhone("1234567");
		tutor.setUsername("tutor1");
		tutor.setPassword("password1");
		
		tutorDao.saveTutor(tutor);
		
		List<Tutor> tutorList = new ArrayList<Tutor>();
		tutorList = tutorDao.findAllTutors();
		
		Tutor newTutor = new Tutor();
		for(int i=0; i<(tutorList.size()); i++){
			newTutor = tutorList.get(i);
			if(newTutor.getEmail() == "TUTORS@GRINDSREGISTER.TEST"){
				break;
			}
		}
		
		int tutorId = newTutor.getId();
		tutorDao.remove(tutorId);
		
		Assert.assertNull(tutorDao.findById(tutorId));
	}
	
	@Test
	public void testGenerateTempPassword(){
		Tutor tutor = new Tutor();
		tutor.setId(1234);
		tutor.setName("TUTORS TEST");
		tutor.setEmail("TUTORS@GRINDSREGISTER.TEST");
		tutor.setPhone("1234567");
		tutor.setUsername("tutor1");
		tutor.setPassword("password1");
		
		tutorDao.saveTutor(tutor);
		
		List<Tutor> tutorList = new ArrayList<Tutor>();
		tutorList = tutorDao.findAllTutors();
		
		Tutor newTutor = new Tutor();
		for(int i=0; i<(tutorList.size()); i++){
			newTutor = tutorList.get(i);
			if(newTutor.getEmail() == "TUTORS@GRINDSREGISTER.TEST"){
				break;
			}
		}
		
		int tutorId = newTutor.getId();
		
		
		Assert.assertEquals("password1", newTutor.getPassword());
		
		tutorDao.generateTempPassword(tutor);
		
		Tutor testTutor = tutorDao.findById(tutorId);
		
		Assert.assertNotSame("Password Changed", testTutor.getPassword());
		
		tutorDao.remove(tutorId);
	}

}
