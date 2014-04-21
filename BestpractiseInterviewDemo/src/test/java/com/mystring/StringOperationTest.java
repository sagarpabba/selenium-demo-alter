package com.mystring;

import org.testng.annotations.Test;

public class StringOperationTest {

	StringOperation so=new StringOperation();
  @Test
  public void splitString() {
   // throw new RuntimeException("Test not implemented");
	 String str="asdewe|sddwA";
	 int len= so.splitString(str);
	 System.out.println("len is:"+len);
	 
	 int len2=so.splitStringtoken(str);
	 System.out.println("len2 is:"+len2);
	 String str1="abccdd\\dsssdsd";
	 System.out.println("len3 is:"+str1.split("\\\\").length);
  }
  
  
}
