package com.proserus.stocks.bp;

import com.proserus.stocks.bp.FilterBp;

public class SharedFilter extends FilterBp {
	private static SharedFilter sharedFilter = new SharedFilter();

	private SharedFilter() {

	}
	
	public static SharedFilter getInstance() {
		return sharedFilter;
	}
	
}
