package com.ait.seleniumtests;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class RetrieveTutorTest {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8080/");
		selenium.start();
	}

	@Test
	public void testRetrieveTutor() throws Exception {
		selenium.open("/GrindsRegister/index.html");
		selenium.click("id=ui-id-2");
		selenium.type("id=reg_name", "Selenium Retrieve Test");
		selenium.type("id=contact_email", "retrieveTest@test.ie");
		selenium.type("id=phone", "1234");
		selenium.type("id=username", "retrieveTest");
		selenium.type("id=password", "password");
		selenium.type("id=confirm_password", "password");
		selenium.click("id=register");
		assertEquals("Tutor Registered successfully", selenium.getAlert());
		selenium.click("id=ui-id-3");
		selenium.type("id=login_username", "retrieveTest");
		selenium.type("id=login_password", "password");
		selenium.click("id=loginButton");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}

