package com.ait.grindsregister;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class SubjectDaoTest {
	
	private SubjectDAO subjectDao = new SubjectDAO();
	
	@Test
	public void testFindAllSubjects() {
		List<Subject> subjectList = new ArrayList<Subject>();
		subjectList	= subjectDao.findAllSubjects();
		
		Assert.assertEquals(6, subjectList.size());
		Subject subject = subjectList.get(0);
		
		Assert.assertEquals("Accounting", subject.getSubject());
	}
	
	@Test
	public void testFindById(){
		Subject subject = subjectDao.findById(1);
		
		Assert.assertEquals("Accounting", subject.getSubject());
	}

}
