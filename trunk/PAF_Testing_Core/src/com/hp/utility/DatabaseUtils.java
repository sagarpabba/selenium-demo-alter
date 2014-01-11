/**
 * Project Name:PAF_HC
 * File Name:DatabaseUtils.java
 * Package Name:com.hp.utility
 * Date:Sep 7, 20139:34:48 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.utility;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

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
	
	public static String drivername="com.mysql.jdbc.Driver";
	public static String driverurl="jdbc:mysql://HUCHAN3.asiapacific.hpqcorp.net:3306/qtpresult";
	public static String user="root";
	public static String password="root";
	public static Connection con=null;
	public static ResultSet rs=null;
	
	private static final String CHART_FILE_DIR = SeleniumCore.getProjectWorkspace() + "reporter";
	
	/**
	 * Method getConnection.
	 * @return Connection
	 */
	public static Connection getConnection(){
		
		try
		{
		   Class.forName(drivername);	
		   con=DriverManager.getConnection(driverurl, user, password);
		   logger.info("Build the driver connection successfully....");
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
			logger.error("Sorry cannot find the sql resultset  ,cannot execute the sql"+sql+"..."+e.getMessage());
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
    
     
   /**
    * generate a 3D Bar Chart
 * @param begindate
 * @param enddate
 * @param rangedate
 * @param width
 * @param height
 * @param charname

 * @throws IOException */
public static void generate3DBarChart(String begindate,String enddate,List<String> rangedate,int width,int height,String charname) throws IOException{
    	
    	String chartitle="PAF Automation Execution Report status From "+begindate+" to "+enddate;		
		CategoryDataset dataset=createDataset(rangedate);
	//createBarChart3D
		JFreeChart chart = ChartFactory.createBarChart3D(chartitle,   
		                  "Report Status",  
		                  "Status number",  
		                  dataset,  
		                  PlotOrientation.VERTICAL,  
		                  true,  
		                  true,  
		                  false);  
		chart.setBackgroundPaint(new Color(0xBB, 0xBB, 0xDD));
		CategoryPlot plot = chart.getCategoryPlot();    
		//backgroud color
		plot.setBackgroundPaint(Color.white);  
		//grid line color 
		plot.setDomainGridlinePaint(Color.BLUE);  
		//gride line range color
		plot.setRangeGridlinePaint(Color.GRAY);  
		 
		final NumberAxis rangeAxis = new NumberAxis("Y-Axis");
		rangeAxis.setRange(0,20);
		//show bar
		BarRenderer3D renderer = new BarRenderer3D();  
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());  
		renderer.setBaseItemLabelsVisible(true);  
		  
		//show the number in bar
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));  
		renderer.setItemLabelAnchorOffset(10D);  
		  
		//every item margin
		renderer.setItemMargin(0);  
		//renderer.
		renderer.setSeriesPaint(2, Color.GREEN);
		//renderer.setseriesp
		renderer.setSeriesPaint(1, Color.RED);
		renderer.setSeriesPaint(0, Color.ORANGE);
		plot.setRenderer(renderer);  
		
		//save the picture
		ChartUtilities.saveChartAsJPEG(new File(CHART_FILE_DIR+File.separator+charname), chart, width, height);
    }
    
    /**
     * generate the 3D line chart
     * @param begindate
     * @param enddate
     * @param rangedate
     * @param width
     * @param height
     * @param charname
    
     * @throws IOException */
    public static void generate3DLineChart(String begindate,String enddate,List<String> rangedate,int width,int height,String charname) throws IOException{
    		String chartitle="PAF Automation Execution Report status From "+begindate+" to "+enddate;	
    		CategoryDataset dataset=createDataset(rangedate);
    		//createBarChart3D
    		JFreeChart chart = ChartFactory.createLineChart3D(chartitle,   
    			                  "QTP Report Status",  
    			                  "Status number",  
    			                  dataset,  
    			                  PlotOrientation.VERTICAL,  
    			                  true,  
    			                  true,  
    			                  false);  
    			chart.setBackgroundPaint(new Color(0xBB, 0xBB, 0xDD));

    	        CategoryPlot plot = (CategoryPlot) chart.getPlot();

    	        // customise the range axis...
    	        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    	        rangeAxis.setAutoRangeIncludesZero(false);
    	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    	        
    	        LineRenderer3D lineRenderer = (LineRenderer3D) plot.getRenderer();
    	        lineRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());// 显示�?个柱的数值
    	        lineRenderer.setBaseItemLabelsVisible(true);
    	        //to show the number in the line
    	        ItemLabelPosition itemLabelPosition = new ItemLabelPosition(
    	        ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER);
    	        lineRenderer.setBasePositiveItemLabelPosition(itemLabelPosition);
    	        lineRenderer.setItemLabelAnchorOffset(10D);// the distance between the number 
    	        lineRenderer.setBaseItemLabelsVisible(true);
    	        
    	        lineRenderer.setSeriesPaint(0, Color.ORANGE);
    	        lineRenderer.setSeriesPaint(1, Color.RED);
    	        lineRenderer.setSeriesPaint(2, Color.GREEN);
    	      //save the picture
    			ChartUtilities.saveChartAsJPEG(new File(CHART_FILE_DIR+File.separator+charname), chart, width, height);
    	   
    	}
    	
    	   
        /**
         * 
         * @param timerange
        
         * @return CategoryDataset
         */
        public static CategoryDataset createDataset(List<String> timerange)
    	{
    		
    	//	double[][] data = new double[][] {{1230,1110,1120}, {720,750,860}, {830,780,790}};  
    		//String[] rowKeys = {"Passed", "Failed", "NoRun"};  
//    		String[] columnKeys = {"henan","xian","shenzhen"};  
    		//CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);   
    		DefaultCategoryDataset dataset=new DefaultCategoryDataset();
    		HashMap<String, Map<String,Integer>> status=new LinkedHashMap<String, Map<String,Integer>>();
    		try
    		{
    			ResultSet rs=null;
    			Connection con=getConnection();
    			for(int listindex=0;listindex<timerange.size();listindex++)
    			{
    				   String sql="select * from qtp_status where date_format(run_time,'%Y-%m-%d')='"+timerange.get(listindex)+"'";
    			       logger.debug("current date is :"+timerange.get(listindex));
    				   rs=getResultSet(con, sql);
    				   logger.debug("execute the SQL query to get the char");
    					if(rs.next())
    					{
    					    	Map<String,Integer> allstatus=new HashMap<String,Integer>();
    					    	//System.out.println(rs.getInt("PASS_TOTAL"));
    					    	allstatus.put("PASS",rs.getInt("PASS_TOTAL"));
    					    	allstatus.put("FAIL",rs.getInt("FAIL_TOTAL"));
    					    	allstatus.put("NORUN",rs.getInt("NORUN_TOTAL"));
    					    	String runtime=new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("RUN_TIME"));
    					    	logger.debug("current data from database is :"+allstatus);
    					    	status.put(runtime, allstatus);
    					}
    					else
    					{
    					    logger.debug("Sorry this day we had not got any data");
    						Map<String,Integer> allstatus2=new HashMap<String,Integer>();
    						allstatus2.put("PASS",0);
    				    	allstatus2.put("FAIL",0);
    				    	allstatus2.put("NORUN",0);
    				    	//String runtime=new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("RUN_TIME"));
    				    	status.put(timerange.get(listindex), allstatus2);
    						
    					}
    			}
    		    logger.debug("list status is :"+status);
    		   
    		    closeAllConnections(con, rs);
    		}
    		catch(Exception e)
    		{
    		     logger.debug("met the char api data exception:"+e.getMessage());
    		}
    		
    		
    		for(String statusdate:status.keySet()){
    		//  System.out.println(statusdate+"   :"+status.get(statusdate));	
    		  Map<String, Integer> innermap=status.get(statusdate);
    		  for(String eachstatus:innermap.keySet())
    		  {
    			  dataset.addValue(innermap.get(eachstatus), eachstatus, statusdate);
    		  }
    		}
    		
//    		dataset.addValue(10, "Pass", "123");
//    		dataset.addValue(11, "Failed", "123");
//    		dataset.addValue(100,"norun","123");
    		return dataset;
    		
    	}
        
}
