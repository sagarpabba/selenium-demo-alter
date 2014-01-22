package com.hp.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.hp.utility.Bundle;
import com.hp.utility.DBUtils;

public class TestAction {

	/*
	   * args parameter is used to obtain a repository location URL, a start
	     * revision number, an end revision number, user's account name & password
	     * to authenticate him to the server.
	     */
	    public static void main(String[] args) {
	    	String starttime="2013-11-14 01:58:40";
	    	long startseconds = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
					starttime, new ParsePosition(0)).getTime();
	    	System.out.println("time is:"+startseconds);
	     
	    }

}