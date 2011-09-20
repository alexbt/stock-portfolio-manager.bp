package com.proserus.stocks.bp.services;

import com.proserus.stocks.bo.common.DBVersion;


public interface DatabaseVersionningBp {
	public DBVersion check();
	
	public void upgrade(DBVersion version); 

}
