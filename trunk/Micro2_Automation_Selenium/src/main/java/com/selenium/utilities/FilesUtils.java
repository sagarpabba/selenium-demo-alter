package com.selenium.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;


/**
* @ClassName: FilesUtils
* @Description: TODO
* @author alterhu2020@gmail.com
* @date Apr 9, 2014 5:57:15 PM
* 
*/

public class FilesUtils {

	private static Logger logger = Logger.getLogger(FilesUtils.class);
	
	
	/**
	 * create a new text file
	 * @param filename
	
	 * @return boolean
	 */
	public static boolean newFile(String filename) {
		boolean iscreated=false;
		File file = new File(filename);	
		if(!file.exists())
		{
			try {
				iscreated = file.createNewFile();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return iscreated;

	}
	/**
	 * create a new folder
	
	
	 * @param folderpath String
	 * @return boolean
	 */
	public static boolean newFolder(String folderpath) {
		boolean iscreated=false;
		
		File file = new File(folderpath);	
		if(!file.exists()){
		  iscreated = file.mkdir();
		}
		return iscreated;

	}
	/**
	 * write some strings into the file
	 * @param filepath
	 * @param Contents
	
	 * @return boolean
	 */
	public static boolean writeContents(String filepath, String Contents) {

		try {
			File file = new File(filepath);
			if (!file.exists()) {
				file.createNewFile();
			}
			logger.debug("New a reporter file content now ...");
			FileWriter writer;
			writer = new FileWriter(file);
			BufferedWriter bufferedwriter = new BufferedWriter(writer);
			logger.debug("now write the content into the html file...... ");
			bufferedwriter.write(Contents);

			bufferedwriter.close();
			writer.close();
			logger.debug("completed writing the email's html content now ");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * get the value string in file ,the value will splite with every line to get it
	 * @param filepath
	 * @param begintext
	 * @param spliteseparator
	 * @param spliteindex
	
	 * @return String
	 */
	public static String getSpliteValue(String filepath,String begintext,String spliteseparator,int spliteindex) {

		File logfile = new File(filepath);
		String result = null;
		FileReader reader = null;
		BufferedReader bufferedreader = null;
		if (!logfile.exists()) {
			logger.debug("we cannot find the reporter.log file so we return the found value is null");
			return null;
		}

		try {
			reader = new FileReader(logfile);
			bufferedreader = new BufferedReader(reader);
			String strline = null;
			while ((strline = bufferedreader.readLine()) != null) {
				if (strline.contains(begintext)) {
					logger.debug("the Search line content is " + strline);
					result = strline.split("\\"+spliteseparator)[spliteindex].trim();
					break;
				}
			}
			bufferedreader.close();
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("File met Exception:"+e.getMessage());
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("file met Exception:"+e.getMessage());
			return null;
		}

		return result;

	}

	/**
	 * get the sub files number in a directory ,if not find ,will return 0;
	 * @param directory
	 * @param filenamefilter
	
	 * @return int
	 */
	public static int getSubFilesSize(String directory,
			final String filenamefilter) {
		
		File dir = new File(directory);
		if(dir.exists()){
			FilenameFilter filter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					// TODO Auto-generated method stub
					return name.endsWith(filenamefilter);
				}
			};
			File[] errorshotfile = dir.listFiles(filter);
			int filesize = errorshotfile.length;
			if (filesize > 0) {
				return filesize;
			} else {
				return 0;
			}
		}else{
			return 0;
		}
	}

	/**
	 * get all the sub files in a directory, the return is the files object
	 * @param directory
	 * @param filenamefilter
	
	 * @return File[]
	 */
	public static File[] listFilesEndsWith(String directory, final String filenamefilter) {
		File dir = new File(directory);
		if(dir.exists()){
					
			FilenameFilter filter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					// TODO Auto-generated method stub
					return name.endsWith(filenamefilter);
				}
			};
			File[] filelists = dir.listFiles(filter);
			int filesize = filelists.length;
			if (filesize > 0) {
				return filelists;
			} else {
				return null;
			}
		}else{
			return null;
		}
	}
	
	/**
	 * get all the sub files in a directory ,the return is the files object ,the sub file name need start with the filter name
	 * @param directory
	 * @param filenamefilter
	
	 * @return File[]
	 */
	public static File[] listFilesStartWith(String directory, final String filenamefilter) {
		File dir = new File(directory);
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.startsWith(filenamefilter);
			}
		};
		File[] filelists = dir.listFiles(filter);
		int filesize = filelists.length;
		if (filesize > 0) {
			return filelists;
		} else {
			return null;
		}

	}

	/**
	 * return the file's content with string
	 * @param filepath
	
	 * @return String
	 */
	public static String returnFileContents(String filepath) {
		StringBuffer htmlcontent = new StringBuffer("");
		BufferedReader file = null;

		try {
			file = new BufferedReader(new InputStreamReader(
					new FileInputStream(filepath)));
			String strline = null;
			while ((strline = file.readLine()) != null) {
				htmlcontent.append(strline);
				// logger.debug("now read the  file,this line content is : "+strline);
			}
			file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("we cannot find this  file :" + filepath+",Exception:"+e.getMessage());
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		//
		return htmlcontent.toString();
	}
	/**
	 * get the sub files lists in a directory
	 * @param listfolder
	
	 * @return List<String>
	 */
	public static List<String> getSubFiles(String listfolder){
		List<String> filelists=new ArrayList<String>();
		File folder=new File(listfolder);
		File[] files=folder.listFiles();
		for(File subfile:files){
			String filenamepath=subfile.getAbsolutePath();
			filelists.add(filenamepath);
		}	
		return filelists;
	}
	
	
	/**
	 * replace the content in the file with new string we need to update,it will not change the content format
	 * @param filepath
	 * @param replacedstr
	 * @param newstr
	
	 * @throws IOException */
	public static void replaceStringOfFile(String filepath,String replacedstr,String newstr) throws IOException{
		File file=new File(filepath);
		List<String> sb=new ArrayList<String>();
		BufferedReader br=new BufferedReader(new FileReader(file));
		 String strline = null;
		    while ((strline = br.readLine()) != null) {
				if (strline.contains(replacedstr)) {
					//find the status report we had reported before
	                strline=strline.replace(replacedstr, newstr);	                				
				}
				sb.add(strline);
				sb.add("\n");
				
			}
		// System.out.print(sb);
		 br.close();
		 BufferedWriter bw=new BufferedWriter(new FileWriter(file));
		 for(String e:sb ){
			// if(e=="\n"){
			//	 bw.newLine();
			// }
			//{
				 bw.write(e);
			// }
		 }
	
        bw.close();
      }
	
	
	 /**
     * Unzip it
     * @param zipFile input zip file
    
     * @param outputFolder String
	  */
    public static void unZipIt(String zipFile, String outputFolder){
 
     byte[] buffer = new byte[1024];
 
     try{
 
    	//create output directory is not exists
    	File folder = new File(outputFolder);
    	if(!folder.exists()){
    		folder.mkdir();
    	}
 
    	//get the zip file content
    	ZipInputStream zis = 
    		new ZipInputStream(new FileInputStream(zipFile));
    	//get the zipped file list entry
    	ZipEntry ze = zis.getNextEntry();
 
    	while(ze!=null){
 
    	   String fileName = ze.getName();
           File newFile = new File(outputFolder + File.separator + fileName);
 
           logger.info("file unzip : "+ newFile.getAbsoluteFile());
 
            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
            new File(newFile.getParent()).mkdirs();
 
            FileOutputStream fos = new FileOutputStream(newFile);             
 
            int len;
            while ((len = zis.read(buffer)) > 0) {
       		fos.write(buffer, 0, len);
            }
 
            fos.close();   
            ze = zis.getNextEntry();
    	}
 
        zis.closeEntry();
    	zis.close();
 
    	logger.info("Done unzipped the file...");
 
    }catch(IOException ex){
       logger.error("unzip file met error:"+ex.getMessage()); 
    }
   }   //unzip file    
}
