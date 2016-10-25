package com.ait.seleniumtests;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class DisplayGrindsBySubjectTest {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8080/");
		selenium.start();
	}

	@Test
	public void testDisplayGrindsBySubject() throws Exception {
		selenium.open("/GrindsRegister/index.html");
		selenium.click("id=table");
		selenium.click("id=3");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}

