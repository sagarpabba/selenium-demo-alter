package com.ocr;


import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class Test {

	/**
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try {
			 
			
		    String imagepath="C:\\workspace\\buyticket\\randcode";
		   
		    
		    File randdir=new File(imagepath);
		    File[] subfile=randdir.listFiles();
			for(int k=0;k<subfile.length;k++){
				    String parentpath=subfile[k].getParent();
				    String outputfilename=parentpath+File.separator+subfile[k].getName()+k+"a.png";
				    BufferedImage imageobj=Tools.getImage(subfile[k].getAbsolutePath());
				    
				    Filter.blackAndWhiteFilter(imageobj);				  
				    Tools.writeImageToFile(outputfilename,imageobj);
				    
				    String outputfilename2=parentpath+File.separator+subfile[k].getName()+k+"b.png";
				    Filter.dotFilter(imageobj);
				    Tools.writeImageToFile(outputfilename2,imageobj);
				    
				    
					String maybe2 = OCR.recognizeText(subfile[k], "png");
					System.out.println("Crack Code is: "+maybe2);
					System.out.println("**********");
			}
	 
			
			//MyString str=new MyString();
			//System.out.println(str.getString(maybe2));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
//		SoundServer s=new SoundServer();
//		s.playSound("E:\\111\\HOOK1.wav");
	}

}
