package com.hp.utility;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 1L;

    @Override
    /*
  
     */
    public String intercept(ActionInvocation invocation) throws Exception {
    	Map<String,Object> sessionattributes = invocation.getInvocationContext().getSession(); 
    	
      //  String username = (String) sessionattributes.get("username");       
        //System.out.println("begin to intercept ,user is :"+username);
       
        if(sessionattributes==null||sessionattributes.get("userid")==null){
        	System.out.println("Now the user logged is null so still back to login page,logid ="+(String)sessionattributes.get("logid"));
            return "login";
        }
        else{
        	if (!((String)sessionattributes.get("userid")).equals(null))
        	{
        	System.out.println("Error,you had not logged before ,or your session in this host is expiration.....");
        
        	
            return invocation.invoke();
        	}
        	else
        	{
        		return Action.LOGIN;
        		
        	}
        }            
    }
    @Override
    public void init() {
    	// TODO Auto-generated method stub
    	//super.init();
    }
    
    @Override
    public void destroy() {
    	// TODO Auto-generated method stub
    	//super.destroy();
    }

}