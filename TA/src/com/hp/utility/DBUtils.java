package com.hp.utility;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBUtils {
	
	
	

	public static Connection con=null;
	public static PreparedStatement ps=null;
	public static ResultSet rs=null;
	
    public static String drivername=Bundle.getString("jdbc.driver.name");
    public static String connectionurl=Bundle.getString("jdbc.driver.url");
    public static String username=Bundle.getString("jdbc.driver.username");
    public static String password=Bundle.getString("jdbc.driver.password");
	
   public  Connection getConnection(){
	  try {
		Class.forName(drivername);
		con=DriverManager.getConnection(connectionurl,username,password);
		System.out.println("Absolute path in current system :"+new File(".").getAbsolutePath());
		//con=DriverManager.getConnection(url);
		if(con==null){
			System.out.println("Cannot connect to the sqlite database driver...");
			return null;
		}
	   } catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return con;
   }
   
   public  PreparedStatement getPreparedStatement(Connection con,String sql)
	{
		try {
			ps=con.prepareStatement(sql);
//			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("use the prepare statement met the sql error.");
			e.printStackTrace();
		}
		return ps;
   }

   
//   get the database's table total rows
   public  int getTotalRows(Connection con,String tablename)
   {
   	int size = 0;
   	try{
	    	ps=getPreparedStatement(con,"select count(*) as num from "+tablename);	    	
	    	rs=ps.executeQuery();	    	
	    	if(rs!=null)
	    	{
	    		if(rs.next())
	    		{
	    		  size=rs.getInt("num");	
	    		}
	    		else
	    		{
	    		  size=0;
	    		}
	    	}
	    	
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
   	return size;
   }
   

//   close all the existing connection 
	public   void closeConnction(Connection con)
	{
		
	  try {
			if(con!=null)
			{
				con.close();
				con=null;
			}		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
// close all the existing connection 
	public   void closePreparedStatement(PreparedStatement ps)
	{		
	  try {		
			if(ps!=null)
			{
				ps.close();
				ps=null;
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
// close all the existing connection 
	public  void closeResultSet(ResultSet rs)
	{
		
	  try {
			if(rs!=null)
			{
				rs.close();
				rs=null;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
  
   
}
