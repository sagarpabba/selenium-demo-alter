package com.myrecursion;

import org.testng.annotations.Test;

public class RecursionTest {

  @Test
  public void fb() {
    //throw new RuntimeException("Test not implemented");
	  
	  Recursion re=new Recursion();
	  int n=re.fb(5);
	  System.out.println(n);
  }
}
