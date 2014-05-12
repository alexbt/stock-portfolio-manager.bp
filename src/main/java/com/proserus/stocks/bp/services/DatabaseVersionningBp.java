package com.proserus.stocks.bp.services;

import java.util.Properties;

import com.proserus.stocks.bo.common.DBVersion;


public interface DatabaseVersionningBp {
	public DBVersion retrieveCurrentVersion();
	
	public void upgrade(DBVersion version); 
	
	public boolean isNewerVersion(double latestVersion);
	
	public Double retrieveLatestVersion(String url);
	
	public void writeVersion(Properties pro, String version);

	void setIgnorePopop(boolean ignorePopop);
	
}
