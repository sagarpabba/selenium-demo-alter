package com.myexception;

import org.testng.annotations.Test;

public class ExceptionTesterTest {

  @Test
  public void tryException() {
    //throw new RuntimeException("Test not implemented");
	  ExceptionTester tester=new ExceptionTester();
	  int z=tester.tryException();
	  System.out.println(z);
	  
  }
}
