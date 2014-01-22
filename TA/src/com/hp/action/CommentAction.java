package com.hp.action;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

public class CommentAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5538869578879271759L;
	public JSONObject listcommentobj=null;
	//list the latest 10 comments
	//SQL :select * from build_comments
	public String  listLatestComments(){
		
		
		return SUCCESS;
	}

}
