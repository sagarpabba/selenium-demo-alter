package com.hp.utility;

/**
 * ClassName: RandomUtils 
 * Function: TODO ADD FUNCTION. 
 * Reason: TODO ADD REASON(optional). 
 * date: Aug 20, 2013 12:41:17 PM 
 *
 * @author huchan
 * @version 
 * @since JDK 1.6
 */
public class RandomUtils {

	/**
	 * getRandomNumber:(get an randomnumber from the range). 
	 * TODO(here describle this function where used– optional).
	 * TODO(here describle this function flow – optional).
	 * TODO(here describle this function how to use– optional).
	 * TODO(here describle this fuction Precautions– optional).
	 *
	 * @author huchan
	 * @return
	 * @since JDK 1.6
	 */
	public static int getRandomNumber() {
		int minimum = 1;
		int maximum = 100000000;
		int returnvalue = minimum + (int) (Math.random() * maximum);
		return returnvalue;
	}

}
