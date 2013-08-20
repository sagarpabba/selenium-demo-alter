package com.hp.tests;

import org.junit.Assert;
import org.testng.annotations.Test;

public class TestNGRun2 {

	@Test
	public void test1() {
		Assert.fail();
		System.out.println("Demo2 :run1");
	}

	@Test
	public void test2() {
		System.out.println("Demo2 :run2");
	}

}
