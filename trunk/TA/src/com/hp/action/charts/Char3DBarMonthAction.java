package com.hp.action.charts;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import com.hp.utility.DBUtils;
import com.hp.utility.TimeUtils;
import com.opensymphony.xwork2.ActionSupport;





public class Char3DBarMonthAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8319957947558022776L;
	
	private Connection con=null;
	private PreparedStatement ps=null;
	private ResultSet rs=null;

	DBUtils db=new DBUtils();
	// get the 3D Barchart
	public JFreeChart getChart() {
		
		
		String begindate=TimeUtils.getFirstdayofMonth();
		String enddate=TimeUtils.getLastdayofMonth();
		String chartitle="Execution Status From "+begindate+" to "+enddate;
		
			
		CategoryDataset dataset=createDataset(TimeUtils.getCurrentMonthday());
	//createBarChart3D
		JFreeChart chart = ChartFactory.createBarChart3D(chartitle,   
		                  "ExecutionReport Status",  
		                  "Status number",  
		                  dataset,  
		                  PlotOrientation.VERTICAL,  
		                  true,  
		                  true,  
		                  false);  
		chart.setBackgroundPaint(new Color(0xBB, 0xBB, 0xDD));
		CategoryPlot plot = chart.getCategoryPlot();    
		//设置网格背景颜色  
		plot.setBackgroundPaint(Color.white);  
		//设置网格竖线颜色  
		plot.setDomainGridlinePaint(Color.BLUE);  
		//设置网格横线颜色  
		plot.setRangeGridlinePaint(Color.GRAY);  
		 
		final NumberAxis rangeAxis = new NumberAxis("Y-Axis");
		rangeAxis.setRange(0,20);
		//显示�?个柱的数值，并修改该数值的字体属性  
		BarRenderer3D renderer = new BarRenderer3D();  
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());  
		renderer.setBaseItemLabelsVisible(true);  
		  
		//默认的数字显示在柱�?中，通过如下两�?��?�调整数字的显示  
		//注�?：此�?�很关键，若无此�?�，那数字的显示会被覆盖，给人数字没有显示出�?�的问题  
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));  
		renderer.setItemLabelAnchorOffset(10D);  
		  
		//设置�?个地区所包�?�的平行柱的之间�?离  
		renderer.setItemMargin(0);  
		//renderer.
		renderer.setSeriesPaint(2, Color.GREEN);
		//renderer.setseriesp
		renderer.setSeriesPaint(1, Color.RED);
		renderer.setSeriesPaint(0, Color.ORANGE);
		plot.setRenderer(renderer);  
		  
		//设置地区�?销�?的显示�?置  
		//将下方的“肉类�?放到上方  
		//plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);  
		//将默认放在左边的“销�?�?放到�?�方  
		//plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);  
		  
		return chart;
	}

	public void setChart(JFreeChart chart) {
	}
     
	
	public CategoryDataset createDataset(List<String> timerange)
	{
		
	//	double[][] data = new double[][] {{1230,1110,1120}, {720,750,860}, {830,780,790}};  
		//String[] rowKeys = {"Passed", "Failed", "NoRun"};  
//		String[] columnKeys = {"henan","xian","shenzhen"};  
		//CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);   
		DefaultCategoryDataset dataset=new DefaultCategoryDataset();
		HashMap<String, Map<String,Integer>> status=new LinkedHashMap<String, Map<String,Integer>>();
		try
		{
			con=db.getConnection();
			for(int listindex=0;listindex<timerange.size();listindex++)
			{
				   String sql="select * from build_report where date_format(CREATE_TIME,'%Y-%m-%d')='"+timerange.get(listindex)+"'";
			       System.out.println("current date is :"+timerange.get(listindex));
					ps=db.getPreparedStatement(con, sql);
					//ps.setString(1, );
				//	System.out.println("sql string is ;"+sql);
					rs=ps.executeQuery();
					System.out.println("execute the SQL query to get the char");
					if(rs.next())
					{
					  //  while(rs.next())
					   // {
					    	Map<String,Integer> allstatus=new HashMap<String,Integer>();
					    	//System.out.println(rs.getInt("PASS_TOTAL"));
					    	allstatus.put("PASS",rs.getInt("PASSED"));
					    	allstatus.put("FAIL",rs.getInt("FAILED"));
					    	allstatus.put("WARNING",rs.getInt("WARNING"));
					    	String runtime=String.valueOf(rs.getInt("BUILD_ID"))+new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("CREATE_TIME"));
					    	System.out.println("current data from database is :"+allstatus);
					    	status.put(runtime, allstatus);
					    	//System.out.println("current record is :"+rs.getRow());
					   // }
					}
					else
					{
						System.out.println("Sorry this day we had not got any data");
						Map<String,Integer> allstatus2=new HashMap<String,Integer>();
						allstatus2.put("PASS",0);
				    	allstatus2.put("FAIL",0);
				    	allstatus2.put("WARNING",0);
				    	//String runtime=new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("RUN_TIME"));
				    	status.put(timerange.get(listindex), allstatus2);
						
					}
			}
		    System.out.println("list status is :"+status);
		
				    
		   
		}
		catch(Exception e)
		{
		     System.out.println(e);	
		}finally{
		    db.closeResultSet(rs);
		    db.closePreparedStatement(ps);
		    db.closeConnction(con);
			
		}
		
		
		for(String statusdate:status.keySet()){
		//  System.out.println(statusdate+"   :"+status.get(statusdate));	
		  Map<String, Integer> innermap=status.get(statusdate);
		  for(String eachstatus:innermap.keySet())
		  {
			  dataset.addValue(innermap.get(eachstatus), eachstatus, statusdate);
		  }
		}
		
//		dataset.addValue(10, "Pass", "123");
//		dataset.addValue(11, "Failed", "123");
//		dataset.addValue(100,"norun","123");
		return dataset;
		
	}
	
}
