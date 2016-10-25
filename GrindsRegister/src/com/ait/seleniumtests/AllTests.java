package com.ait.seleniumtests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddTutorTest.class, DisplayGrindsBySubjectTest.class,
		DynamicallyDisplayedTabsTest.class, RetrieveTutorTest.class,
		SubjectsListSearchTest.class, TutorLoginTest.class,
		UpdateTutorTest.class })
public class AllTests {

}
