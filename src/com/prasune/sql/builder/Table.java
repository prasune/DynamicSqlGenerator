package com.prasune.sql.builder;

import java.util.ArrayList;
import java.util.List;

public class Table {
	
    private final String name;
    private final String alias;
    private List<String> columnsWithAlias = new ArrayList<String>();
    private List<String> groupFunctions = new ArrayList<String>();

    public Table(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }
    
    public void addColumnsToSelect(String column){
    	columnsWithAlias.add(alias + "." + column);
    }
    
    public List<String> getColumnsWithAlias(){
    	return columnsWithAlias;
    }
    
    public void addGroupFunctions(String groupFunction){
    	groupFunctions.add(groupFunction);
    }
    
    public List<String> getGroupFunctions(){
    	return groupFunctions;
    }
}
