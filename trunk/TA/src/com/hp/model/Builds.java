package com.hp.model;

public class Builds {
	
	private Integer id;
	private String name;
	
	private String status;
	private String type;
	//private String valid;
	private String starttime;
	private String endtime;
	private String olddefects;
	private String newdefects;
	private String comments;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public String getOlddefects() {
		return olddefects;
	}
	public void setOlddefects(String olddefects) {
		this.olddefects = olddefects;
	}
	public String getNewdefects() {
		return newdefects;
	}
	public void setNewdefects(String newdefects) {
		this.newdefects = newdefects;
	}
	/**
	 * @return the comment
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer  id) {
		this.id = id;
	}
	/**
	 * @return the starttime
	 */
	public String getStarttime() {
		return starttime;
	}
	/**
	 * @param starttime the starttime to set
	 */
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	/**
	 * @return the endtime
	 */
	public String getEndtime() {
		return endtime;
	}
	/**
	 * @param endtime the endtime to set
	 */
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}


}
