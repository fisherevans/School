package com.fisherevans.school.qa.junitexample;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExampleTest {

	@Test
	public void testMultiply() {
		Example tester = new Example();
		assertEquals("Result", 50, tester.multiply(5, 10));
	}

}
