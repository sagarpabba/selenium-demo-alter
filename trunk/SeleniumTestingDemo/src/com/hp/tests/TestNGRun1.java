package com.hp.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.hp.utility.HostUtils;

public class TestNGRun1 {

	@Test
	public void test1() {
		//Assert.assertEquals(1, 3);
		System.out.println("Demo1 :run1");
        System.out.println(HostUtils.getOperatingSystemName());
        System.out.println(HostUtils.getOperatingSystemVersion());
        System.out.println(HostUtils.getOSType());
	}

	@Test(dependsOnMethods = "test1")
	public void test2() {
		System.out.println("Demo1 :run2");
	}

}
