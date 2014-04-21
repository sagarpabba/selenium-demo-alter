/**
 * Project Name:PAF_HC
 * File Name:DatabaseUtils.java
 * Package Name:com.hp.utility
 * Date:Sep 7, 20139:34:48 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.selenium.utilities;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.apache.log4j.Logger;

/**
 * ClassName:DatabaseUtils 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 Used for operating the mysql database
 * Date:     Sep 7, 2013 9:34:48 AM 
 * @author   huchan

 * @since    JDK 1.6
 * @see 	 
 * @version $Revision: 1.0 $
 */
public class DatabaseUtils {
	
	public static Logger logger=Logger.getLogger(DatabaseUtils.class);
	
	public static String drivername=GlobalDefinition.DB_DRIVER_NAME;
	public static String driverurl=GlobalDefinition.DB_URL;
	public static String user=GlobalDefinition.DB_USER;
	public static String password=GlobalDefinition.DB_PASSWORD;
	public static Connection con=null;
	public static ResultSet rs=null;
	
	
	/**
	 * Method getConnection.
	 * @return Connection
	 */
	public static Connection getConnection(){
		
		try
		{
		   Class.forName(drivername);	
		   con=DriverManager.getConnection(driverurl, user, password);
		   logger.info("Build the JDBC driver connection successfully....");
		}
		catch(ClassNotFoundException e)
		{
			logger.error("Sorry cannot find the database driver file,cannot connect to the database..."+e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Sorry cannot find the sql url host ,cannot connect to the database..."+e.getMessage());
		}
		
		return con;
	}
	
	/**
	 * Method getResultSet.
	 * @param con Connection
	 * @param sql String
	 * @return ResultSet
	 */
	public static ResultSet getResultSet(Connection con,String sql){
		try {
			rs=con.prepareStatement(sql).executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Sorry cannot find the sql resultset  ,cannot execute the sql:"+sql+"..."+e.getMessage());
		}
		
		return rs;
	}
    /**
     * Method updateRecord.
     * @param con Connection
     * @param deletesql String
     * @return int
     */
    public static int updateRecord(Connection con,String deletesql){
    	int updaterows=0;
		try {
			updaterows = con.prepareStatement(deletesql).executeUpdate();
			//logger.info("Had update the row from database:"+updaterows);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("we had totally updated the "+updaterows+" rows records");
		return updaterows;
    	
    }
    /**
     * Method closeAllConnections.
     * @param con Connection
     * @param rs ResultSet
     */
    public static void closeAllConnections(Connection con,ResultSet rs){
    	try {
			rs.close();
			con.close();
			logger.info("had disconnect all the connections from the database now ...");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("met error to close the connections..."+e.getMessage());
		}
    	
    }
           
}
