package com.hp.utility;

import java.io.File;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;


public abstract class CoreUtils {

	private static Logger logger=Logger.getLogger(CoreUtils.class);
	public static String separator = System.getProperty("file.separator");

	/**
	 * This can load a gradle resource, such as a .properties file.
	 * 
	 * @param fileName
	 * @return
	 */
	public static File loadGradleResource( String fileName ) {
		File junitFile = new File( fileName );
		if ( junitFile.exists() ) {
			logger.info( "The file '" + junitFile.getAbsolutePath() + "' exists." );
		} else {
			logger.info( "Problem loading Gradle resource: " + junitFile.getAbsolutePath() );
		}	
		return junitFile;
	}
	
	public static void waitTimer( int units, int mills ) {
    	DecimalFormat df = new DecimalFormat("###.##");
		double totalSeconds = ((double)units*mills)/1000;
		logger.info("Explicit pause for " + df.format(totalSeconds) + " seconds divided by " + units + " units of time: ");
		try {
			Thread.currentThread();		
			int x = 0;
			while( x < units ) {
				Thread.sleep( mills );
				logger.info(".");
				x = x + 1;
			}
		} catch ( InterruptedException ex ) {
			ex.printStackTrace();
		}	
	}

	protected CoreUtils() {
        // do nothing
	}

}
