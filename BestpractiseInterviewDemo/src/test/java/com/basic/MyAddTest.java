package com.basic;

import org.testng.annotations.Test;

public class MyAddTest {

  MyAdd add=new MyAdd();
  @Test
  public void add() {
    //throw new RuntimeException("Test not implemented");
	  int k=add.add(3, 2);
	  System.out.println("output is:"+k);
	  int k2=add.add2(2, 7);
	  System.out.println("output 2 is:"+k2);
  }
}
