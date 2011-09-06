package com.proserus.stocks.bp;

import java.util.Collection;
import java.util.Date;

import org.joda.time.DateTime;

import com.google.inject.Singleton;
import com.proserus.stocks.model.symbols.Symbol;
import com.proserus.stocks.model.transactions.Label;
import com.proserus.stocks.model.transactions.TransactionType;

@Singleton
public class Filter{
	
	public boolean isFiltered(){
		return (getDate()!=null ||
				!getLabelsValues().isEmpty() ||
				getSymbol()!=null ||
				getType()!=null);
	}
	
	public boolean isLabelsFiltered(){
		return !getLabelsValues().isEmpty();
	}
	
	public boolean isDateFiltered(){
		return getDate()!=null;
	}
	
	public boolean isFilteredYearAfter(DateTime date) {
		//TODO Manage Date better
		return isDateFiltered() && (getDate().getYear() > date.getYear());
	}

	
    public Date getDate() {
	    // TODO Auto-generated method stub
	    return null;
    }


	
    public Collection<Label> getLabelsValues() {
	    // TODO Auto-generated method stub
	    return null;
    }

	
    public Symbol getSymbol() {
	    // TODO Auto-generated method stub
	    return null;
    }

	
    public TransactionType getType() {
	    // TODO Auto-generated method stub
	    return null;
    }


	
    public void setDate(Date date) {
	    // TODO Auto-generated method stub
	    
    }

	
    public void setSymbol(Symbol symbol) {
	    // TODO Auto-generated method stub
	    
    }

	
    public void setType(TransactionType type) {
	    // TODO Auto-generated method stub
	    
    }

	
    public void addLabel(Label label) {
	    // TODO Auto-generated method stub
	    
    }

	
    public void removeLabel(Label label) {
	    // TODO Auto-generated method stub
	    
    }

	
    public void setLabels(Collection<Label> labels) {
	    // TODO Auto-generated method stub
	    
    }
	
}
