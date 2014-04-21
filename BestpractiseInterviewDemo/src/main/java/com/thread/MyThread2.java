package com.thread;



/* the answers cleared up a lot of misconceptions I had.
   In general, I would recommend using something like Runnable rather than 
   Thread because it allows you to keep your work only loosely coupled with your 
   choice of concurrency. For example, if you use a Runnable and decide later on that
    this doesn't in fact require it's own Thread, you can just call threadA.run().
    ticket book in different window at the same time
*/
public class MyThread2 implements Runnable {
    private int ticketnumber=20;
    
	public void run() {
		// TODO Auto-generated method stub
		for(int x=0;x<50;x++){
			if(ticketnumber>0){
			System.out.println("book Window: "+Thread.currentThread().getName()
					+",ticket is :"+this.ticketnumber--);
			}
		}
	}

}
