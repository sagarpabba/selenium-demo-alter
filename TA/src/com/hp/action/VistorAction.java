package com.hp.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.hp.model.Location;
import com.hp.utility.DBUtils;
import com.opensymphony.xwork2.ActionSupport;

public class VistorAction extends ActionSupport{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4158811700783148578L;
	public JSONObject listVistorsObj=null;
	
	private JSONObject newVistorObj=null;
	private Location location;
	
	//database object
    private Connection con=null;
	private PreparedStatement ps=null;
			
    private ResultSet rs=null;
  //jdbc database object
  	String sql="insert into visitors (VISITOR_NAME,IP,COUNTRY_CODE,COUNTRY_NAME,REGION_CODE,REGION_NAME,CITY,ZIPCODE,LATITUDE,LONGITUDE,METRO_CODE,AREACODE) values(?,?,?,?,?,?,?,?,?,?,?,?)";
  	DBUtils db=new DBUtils();
	
	
	public String listVistors(){
				
		return SUCCESS;
	}
	
	public String newVistor(){
		System.out.println("ip:"+location.getIp());
		
		// insert this record
		Map<String, Object> datamap=new HashMap<String, Object>();
		try {
				con=db.getConnection();
				ps=db.getPreparedStatement(con, sql);
				ps.setString(1,location.getUsername());
				ps.setString(2, location.getIp());
				ps.setString(3, location.getCountrycode());
				ps.setString(4, location.getCountryname());
				ps.setString(5, location.getRegioncode());
				ps.setString(6, location.getRegionname());
				ps.setString(7, location.getCity());
				ps.setString(8, location.getZipcode());
				ps.setString(9, location.getLatitude());
				ps.setString(10, location.getLongitude());
				ps.setString(11, location.getMetro_code());
				ps.setString(12, location.getAreacode());
				int successnum=ps.executeUpdate();
				if(successnum==1){
					datamap.put("success", true);
				}else{
					datamap.put("success", false);
				}
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.closeResultSet(rs);
			db.closePreparedStatement(ps);
			db.closeConnction(con);
		}
		newVistorObj=JSONObject.fromObject(datamap);
		return SUCCESS;
	}

	public JSONObject getNewVistorObj() {
		return newVistorObj;
	}

	public void setNewVistorObj(JSONObject newVistorObj) {
		this.newVistorObj = newVistorObj;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
