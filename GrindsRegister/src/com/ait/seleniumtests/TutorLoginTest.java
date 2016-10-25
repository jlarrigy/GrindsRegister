package com.ait.seleniumtests;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class TutorLoginTest {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8080/");
		selenium.start();
	}

	@Test
	public void testTutorLogin() throws Exception {
		selenium.open("/GrindsRegister/index.html");
		selenium.click("id=ui-id-3");
		selenium.type("id=login_username", "cshea");
		selenium.type("id=login_password", "password");
		selenium.click("id=loginButton");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}

