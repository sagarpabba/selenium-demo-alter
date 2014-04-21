package com.thread;

public class MyThread extends Thread{
	 private int ticketnumber=20;
	    private String bookname;
	    
	    public MyThread(String bookname){
	    	this.bookname=bookname;
	    }
	    
	    
		public void run() {
			// TODO Auto-generated method stub
			for(int x=0;x<50;x++){
				if(ticketnumber>0){
				 System.out.println("book Window: "+this.bookname+",ticket is :"+this.ticketnumber--);
			
				}
			}
		}
}
