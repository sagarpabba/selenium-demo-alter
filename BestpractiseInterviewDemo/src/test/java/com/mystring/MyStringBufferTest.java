package com.mystring;

import org.testng.annotations.Test;

public class MyStringBufferTest {
	
	/*
	 * If your code works and people aren't complaining about performance,
	 *  I wouldn't worry about it. You aren't going to get a lot of bang for your buck.
	 *   However, if you are writing new code, or are updating code that uses StringBuffer,
	 *    I'd suggest converting them StringBuilder at the same time.
	 */
  @Test
  public void f() {
	  
	  long time1=System.nanoTime();
	  StringBuffer sb1=new StringBuffer();
	  sb1.append("test");
	  System.out.println("take is:"+(System.nanoTime()-time1));
	  long time2=System.nanoTime();
	  StringBuilder sb2=new StringBuilder();
	  sb2.append("test");
	  System.out.println("take is:"+(System.nanoTime()-time2));
  }
}
