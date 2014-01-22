package org.utils;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.tmatesoft.svn.core.wc2.SvnCommit;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;
import org.tmatesoft.svn.core.wc2.SvnTarget;

public class SVNKitCore {
	
	
	public static String url=Bundle.getString("svn.project.url");
	public static String username=Bundle.getString("svn.project.username");
	public static String password=Bundle.getString("svn.project.password");
	
	//public static long startrevision=Long.valueOf(Bundle.getString("svn.startrevision"));
	//public static long endrevision=Long.valueOf(Bundle.getString("svn.endrevision"));
	  /*
     * Initializes the library to work with a repository via 
     * different protocols.
     */
    public static void setupLibrary() {
	        /*
	         * For using over http:// and https://
	         */
	        DAVRepositoryFactory.setup();
	        /*
	         * For using over svn:// and svn+xxx://
	         */
	        SVNRepositoryFactoryImpl.setup();
	        
	        /*
	         * For using over file:///
	         */
	        FSRepositoryFactory.setup();
    }
    
    /*
     * get the reversion history
     */
    @SuppressWarnings({ "rawtypes" })
	public static Iterator getSVNLogs(long startrevision,long endrevision){
    	
    	 setupLibrary();
    	 //get the history log
    	 SVNRepository repository = null;
	        try {
	            /*
	             * Creates an instance of SVNRepository to work with the repository.
	             * All user's requests to the repository are relative to the
	             * repository location used to create this SVNRepository.
	             * SVNURL is a wrapper for URL strings that refer to repository locations.
	             */
	            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
	        } catch (SVNException svne) {
	            /*
	             * Perhaps a malformed URL is the cause of this exception.
	             */
	            System.err.println("error while creating an SVNRepository for the location '"
	                            + url + "': " + svne.getMessage());
	            System.exit(1);
	        }

	        /*
	         * User's authentication information (name/password) is provided via  an 
	         * ISVNAuthenticationManager  instance.  SVNWCUtil  creates  a   default 
	         * authentication manager given user's name and password.
	         * 
	         * Default authentication manager first attempts to use provided user name 
	         * and password and then falls back to the credentials stored in the 
	         * default Subversion credentials storage that is located in Subversion 
	         * configuration area. If you'd like to use provided user name and password 
	         * only you may use BasicAuthenticationManager class instead of default 
	         * authentication manager:
	         * 
	         *  authManager = new BasicAuthenticationsManager(userName, userPassword);
	         *  
	         * You may also skip this point - anonymous access will be used. 
	         */
	        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
	        repository.setAuthenticationManager(authManager);

	        /*
	         * Gets the latest revision number of the repository
	         */
	      
	        Collection logEntries = null;
	        try{
	        	logEntries = repository.log(new String[] {""}, null,
	        			startrevision, endrevision, true, true);
	        }catch (SVNException svne) {
	            System.out.println("error while collecting log information for '"
	                    + url + "': " + svne.getMessage());
	            System.exit(1);
	        }
    	    // the collection data
		    Iterator entries = logEntries.iterator();		    
		    return entries;
//		    while(entries.hasNext()) {
//		    	//gets a next SVNLogEntry
//		    	 SVNLogEntry logEntry = (SVNLogEntry) entries.next();
//		    	 
//		    	  /*
//				    */
//		            System.out.println("revision: " + logEntry.getRevision());
//		            /*
//		             * gets the author of the changes made in that revision
//		             */
//		            System.out.println("author: " + logEntry.getAuthor());
//		            /*
//		             * gets the time moment when the changes were committed
//		             */
//		            System.out.println("date: " + logEntry.getDate());
//		            /*
//		             * gets the commit log message
//		             */
//		            System.out.println("log message: " + logEntry.getMessage());
//		            
//		            
//		    }
		  
	    	
	    }
    
      public static long getTotalRevisions(){
    	
           	     setupLibrary();
    	    	 //get the history log
    	    	 SVNRepository repository = null;
    		        try {
    		            /*
    		             * Creates an instance of SVNRepository to work with the repository.
    		             * All user's requests to the repository are relative to the
    		             * repository location used to create this SVNRepository.
    		             * SVNURL is a wrapper for URL strings that refer to repository locations.
    		             */
    		            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
    		        } catch (SVNException svne) {
    		            /*
    		             * Perhaps a malformed URL is the cause of this exception.
    		             */
    		            System.err.println("error while creating an SVNRepository for the location '"
    		                            + url + "': " + svne.getMessage());
    		            System.exit(1);
    		        }

    		        /*
    		         * User's authentication information (name/password) is provided via  an 
    		         * ISVNAuthenticationManager  instance.  SVNWCUtil  creates  a   default 
    		         * authentication manager given user's name and password.
    		         * 
    		         * Default authentication manager first attempts to use provided user name 
    		         * and password and then falls back to the credentials stored in the 
    		         * default Subversion credentials storage that is located in Subversion 
    		         * configuration area. If you'd like to use provided user name and password 
    		         * only you may use BasicAuthenticationManager class instead of default 
    		         * authentication manager:
    		         * 
    		         *  authManager = new BasicAuthenticationsManager(userName, userPassword);
    		         *  
    		         * You may also skip this point - anonymous access will be used. 
    		         */
    		        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
    		        repository.setAuthenticationManager(authManager);

    		        /*
    		         * Gets the latest revision number of the repository
    		         */
    		        long latestrevison=0l;
    		        try {
    		        	 latestrevison = repository.getLatestRevision();
    		        	 
    		        } catch (SVNException svne) {
    		            System.err.println("error while fetching the latest repository revision: " + svne.getMessage());
    		            System.exit(1);
    		        }
    		        return latestrevison;
      }
      
      
      public static void checkinOneFile(String filepath,String commitmessage){

    	     setupLibrary();
	    	 //get the history log
	    	 SVNRepository repository = null;
		     try {
		            /*
		             * Creates an instance of SVNRepository to work with the repository.
		             * All user's requests to the repository are relative to the
		             * repository location used to create this SVNRepository.
		             * SVNURL is a wrapper for URL strings that refer to repository locations.
		             */
		            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
		        } catch (SVNException svne) {
		            /*
		             * Perhaps a malformed URL is the cause of this exception.
		             */
		            System.err.println("error while creating an SVNRepository for the location '"
		                            + url + "': " + svne.getMessage());
		            System.exit(1);
		        }

		        /*
		         * User's authentication information (name/password) is provided via  an 
		         * ISVNAuthenticationManager  instance.  SVNWCUtil  creates  a   default 
		         * authentication manager given user's name and password.
		         * 
		         * Default authentication manager first attempts to use provided user name 
		         * and password and then falls back to the credentials stored in the 
		         * default Subversion credentials storage that is located in Subversion 
		         * configuration area. If you'd like to use provided user name and password 
		         * only you may use BasicAuthenticationManager class instead of default 
		         * authentication manager:
		         * 
		         *  authManager = new BasicAuthenticationsManager(userName, userPassword);
		         *  
		         * You may also skip this point - anonymous access will be used. 
		         */
		        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
		        repository.setAuthenticationManager(authManager);

		        final SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
		        svnOperationFactory.setAuthenticationManager(repository.getAuthenticationManager());
		        svnOperationFactory.setOptions(new DefaultSVNOptions());  
	    	    try {
	    		  
		    	      final SvnCommit commit = svnOperationFactory.createCommit();
		    	      commit.setSingleTarget(SvnTarget.fromFile(new File(filepath)));
		    	      commit.setCommitMessage(commitmessage);
					  final SVNCommitInfo commitInfo = commit.run();
				    } catch (SVNException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				    } finally {
	    	            svnOperationFactory.dispose();
	    	        }
      }
 }

