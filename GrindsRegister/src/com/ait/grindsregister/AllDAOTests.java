package com.ait.grindsregister;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GrindDaoTest.class, SubjectDaoTest.class, TutorDaoTest.class })
public class AllDAOTests {

}
