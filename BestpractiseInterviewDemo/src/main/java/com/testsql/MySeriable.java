package com.testsql;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class MySeriable implements Serializable {

	/**
	* @Fields serialVersionUID : TODO
	* http://zhidao.baidu.com/link?url=ObJnMPOHeIawvYb4W53PB_ebNzIalW6QblbHrtPdOaY0QAUKIPoGHxj8L_c8z7KiefL5wC7av_OuYE0QlfX2ha
	* 
	* the current instance is in memory and access it from memory any time
	* when u want to store the object in database or file system
	* Before
	* Store heap 
	*
	*/
	
	private static final long serialVersionUID = 1L;
	
	private int width;
	private int height;
	/** 
	* @return height 
	*/ 
	
	
	public int getHeight() {
		return height;
	}
	/** 
	* @param height need to setter value: height 
	*/ 
	
	public void setHeight(int height) {
		this.height = height;
	}
	/** 
	* @return width 
	*/ 
	
	
	public int getWidth() {
		return width;
	}
	/** 
	* @param width need to setter value: width 
	*/ 
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public static void main(String[] args) {
		
		
		MySeriable myBox = new MySeriable(); 
		myBox.setWidth(50); 
		myBox.setHeight(30); 
        
		//input
		try{ 
		FileOutputStream fs = new FileOutputStream("foo.ser"); 
		ObjectOutputStream os = new ObjectOutputStream(fs); 
		os.writeObject(myBox); 
	
		os.close(); 
		}catch(Exception ex){ 
		ex.printStackTrace(); 
		} 
		
		//output
		 try {
             FileInputStream fis = new FileInputStream("foo.ser");
             ObjectInputStream ois = new ObjectInputStream(fis);
             myBox =(MySeriable) ois.readObject();
             System.out.println("output" +myBox.getHeight());
             ois.close();
         } catch (Exception ex) {
             ex.printStackTrace();
     }
	}

}
