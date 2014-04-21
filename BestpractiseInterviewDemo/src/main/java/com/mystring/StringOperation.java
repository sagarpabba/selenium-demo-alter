package com.mystring;

import java.util.StringTokenizer;

public class StringOperation {
	
	/*
	 * http://blog.sina.com.cn/s/blog_4c19795f0100geib.html
	 */
	// . or | ,\is the regress express
	public int splitString(String s){
		return s.split("\\|").length;
	}

	public int splitStringtoken(String s){
		return new StringTokenizer(s, "\\|").countTokens();
	}
}
