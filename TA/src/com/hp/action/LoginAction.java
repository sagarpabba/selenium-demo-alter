package com.hp.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.SessionAware;

import com.hp.model.User;
import com.hp.utility.DBUtils;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements SessionAware{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3505032419170996287L;
	
	private User user;
    private String msg;
    
    private Connection con=null;
    private PreparedStatement ps=null;
    private ResultSet rs=null;
    String sql="select count(*) as usernumber from users where ( NAME=? or EMAIL=?) and PASSWORD=?";
	String insertsql="insert into users (name,password,email,phone,sex,type) values(?,?,?,?,?,?)";
	
    DBUtils db=new DBUtils();
    
    private Map<String, Object> session;
    
    private JSONObject objRegister=null;

	public String loginSystem(){
		
		System.out.println("currently login user is :"+user.getName());
		System.out.println("currently login password is:"+user.getPassword());
		
		if(user.getName().equals("") && user.getPassword().equals("")){
			addActionError("Sorry User Name and Password is empty ,please input it!");
			return "input";
		}
		else if(user.getPassword().equals("")){
			addActionError("Sorry User's Password is empty ,please input it!");
			return "input";
		}else if(user.getName().equals("")){
			addActionError("Sorry User's Name is empty ,please input it!");
			return "input";
		}
			
	    try{
				
				con=db.getConnection();
				ps=db.getPreparedStatement(con, sql);
				ps.setString(1, user.getName());
				ps.setString(2, user.getName());
				ps.setString(3, user.getPassword());
				rs=ps.executeQuery();
				while(rs.next()){
						System.out.println("execute the SQL query the username and password");			
						int loguser=rs.getInt("usernumber");
						System.out.println("**********************Login user count is:"+loguser);
						if(loguser>0){
							System.out.println("good, we found the username and password from database,we will add this session id with the usename"+getUser().getName());
							// ActionContext.getContext().getSession().put("username",user.getName());  
						   //ActionContext.getContext().getSession().put("password",user.getPassword()); 
						    session.put("userid", user.getName().trim());
						}else{
							System.out.println("the user log count found from database is:"+loguser);
							msg="sorry,no this user";
							//mapsession.put("LOGIN_TYPE","0");
							addActionError("Sorry,we cannot find this User:"+user.getName()+" with this password:"+user.getPassword()+",please retry again!");
							//addActionMessage
							return "input";
						}
				}
				
							
			}
	    
		   catch(SQLException e)
			{
				System.out.println("sorry ,met the bad error now"+e.getMessage()+"/"+e.getClass());
			}finally{			
				db.closeResultSet(rs);
				db.closePreparedStatement(ps);
				db.closeConnction(con);				
			}
			
	    
		return SUCCESS;
	}
	public String home()
	   {
		   return SUCCESS;
	   }
	   
	public String logOut()
	   {
		   session.remove("userid");
		   System.out.println("You had removed the sesion from the current host,logid is :"+session.get("userid"));
		   return SUCCESS;
	   }
	
	public String newRegister(){
		
		Map<String, Object> newmap=new HashMap<String, Object>();
		System.out.println("get user name:"+user.getName());
		System.out.println("get the user password:"+user.getPassword());
		System.out.println("get the email is:"+user.getEmail());
		System.out.println("get the phone is:"+user.getPhone());
		System.out.println("get the sex is:"+user.getSex());
		System.out.println("get the type is:"+user.getType());
		  try{
				
			  
				con=db.getConnection();
				ps=db.getPreparedStatement(con, insertsql);
				ps.setString(1, user.getName());
				ps.setString(2, user.getPassword());
				ps.setString(3, user.getEmail());
				ps.setInt(4, user.getPhone());
				ps.setString(5, user.getSex());
				ps.setInt(6,user.getType());
				int returnvalue=ps.executeUpdate();
				if(returnvalue==1){
			    	  newmap.put("success", true);
			    	  System.out.println("Insert this record into database correctly now ...");
			    }else{
			    	  newmap.put("success",false);
			    	  System.out.println("Failed, cannot insert this record");
			    }
							
			}
	    
		   catch(SQLException e)
			{
				System.out.println("sorry ,met the bad error now"+e.getMessage()+"/"+e.getClass());
			}finally{			
				db.closeResultSet(rs);
				db.closePreparedStatement(ps);
				db.closeConnction(con);				
			}
		objRegister=JSONObject.fromObject(newmap);
		System.out.println("register user json is:"+objRegister);
		return SUCCESS;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public JSONObject getObjRegister() {
		return objRegister;
	}
	public void setObjRegister(JSONObject objRegister) {
		this.objRegister = objRegister;
	}

	
}
