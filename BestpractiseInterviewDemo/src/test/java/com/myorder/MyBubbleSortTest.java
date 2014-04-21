package com.myorder;

import org.testng.annotations.Test;

public class MyBubbleSortTest {

  @Test
  public void sortUp() {
   // throw new RuntimeException("Test not implemented");
	  MyBubbleSort sort=new MyBubbleSort();
	  int testarray[]={1,20,23,45,12,832,4,7,534};
	  int c[]=sort.sortUp(testarray);
	  
	  for(int k=0;k<c.length;k++){
		  System.out.println("k["+k+"]:"+c[k]);
	  }
  }
}
