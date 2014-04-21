package com.mynumber;

import org.testng.annotations.Test;

public class NumberTesterTest {

  NumberTester tester=new NumberTester();
  @Test
  public void getRandomNumber() {
    //throw new RuntimeException("Test not implemented");
    int x=tester.getRandomNumber(1, 100);
    int y=tester.getRandomNumber(1, 100);
    System.out.println("random number from 1 to 100 is :"+x+","+y);
  }

  @Test
  public void maxNumber() {
  //  throw new RuntimeException("Test not implemented");
    int maxnumber=tester.maxNumber();
    System.out.println("max number is :"+maxnumber);
  }

  @Test
  public void minNumber() {
   // throw new RuntimeException("Test not implemented");
	  int minumber=tester.minNumber();
	  System.out.println("min number is:"+minumber);
  }
  
  @Test
  public void roud(){
	  double a=3.5222;
	  System.out.println("Round number is:"+tester.myRound(a));
  }
}
