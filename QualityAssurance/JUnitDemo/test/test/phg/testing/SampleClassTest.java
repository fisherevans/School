package test.phg.testing;

import org.junit.BeforeClass;

import test.pkg.SampleClass;

public class SampleClassTest
{
	private SampleClass _test;
	
	@BeforeClass
	public void beforeClass()
	{
		System.out.println("Starting testing.");
		_test = new SampleClass();
	}
}
