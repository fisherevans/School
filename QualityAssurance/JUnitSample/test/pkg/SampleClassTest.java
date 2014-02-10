package pkg;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Assert;


public class SampleClassTest {

	private static SampleClass _test;
	
	@BeforeClass
	static public void beforeClass()
	{
		System.out.println("Starting testing.");
		_test = new SampleClass();
	}
	
	@Before
	public void before()
	{
		System.out.println("Before tests");
	}
	
	@After
	public void after()
	{
		System.out.println("After tests");
	}
	
	@Test
	public void test()
	{
		System.out.println("Testing.");
		assertTrue(_test.isEven(4));
		assertEquals(8, _test.multiply(2, 4));
	}
	
	@Test
	public void test2()
	{
		System.out.println("Testing.2");
		assertTrue(_test.isEven(4));
		assertEquals(3, _test.multiply(2, 4));
		fail("Fails");
	}

}
