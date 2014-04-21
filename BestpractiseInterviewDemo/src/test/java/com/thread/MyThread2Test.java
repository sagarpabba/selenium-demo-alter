package com.thread;

import org.testng.annotations.Test;

public class MyThread2Test {

/*
 * http://mars914.iteye.com/blog/1508429
 * 
 * mulitly to complete the same work for the JVM 
 * http://blog.chinaunix.net/uid-20665441-id-310538.html
 */
  @Test
  public void runThread() {
    //throw new RuntimeException("Test not implemented");
	  System.out.println("Run thread ......");
	  MyThread window1=new MyThread("Thread First window"); //run 20 times for its work
	  MyThread window2=new MyThread("Thread Second window");
	  MyThread window3=new MyThread("Thread Third window");
	  
	 // window1.run(); run the thread as you specified. JNI with order
	  window1.start();
	  window2.start();
	  window3.start();
  }
  /*
   * Mutlitply to complete the same one task
   * Can implement mulitply interface 
   * public class Thread extends Object implements Runnable
   */
  @Test
  public void runRunnable(){
	  System.out.println("Run Runnable thread...");
	  MyThread2 runthread=new MyThread2();
	  Thread window1=new Thread(runthread,"Runnable First Window"); //run random times for the 20 times
	  Thread window2=new Thread(runthread,"Runnable Second Window");
	  Thread window3=new Thread(runthread,"Runnable Third Window");
	  window1.start();
	  window2.start();
	  window3.start();
	  
  }
  
}
