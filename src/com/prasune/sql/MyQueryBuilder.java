package com.prasune.sql;

import static com.prasune.sql.builder.Criteria.LIKE;

import java.util.ArrayList;
import java.util.List;

import com.prasune.search.SampleSearchCriteria;
import com.prasune.sql.builder.SelectQuery;
import com.prasune.sql.builder.Table;

public class MyQueryBuilder {
	private static String WILDCARD = "%";
	
	public static String build(SampleSearchCriteria searchCriteria,
			                   List<String> parameterValues) {
		SelectQuery sql = new SelectQuery();        
        Table table1 = new Table("TABLE1", "t1");
        table1.addColumnsToSelect("column1");
        table1.addColumnsToSelect("column2");   
        
        Table table2 = new Table("TABLE2", "t2");
        table2.addGroupFunctions("count(columnx) x_count");
        
        sql.addTable(table1);
        sql.addTable(table2);
        sql.addJoin(table2, "column1", table1, "column1");
        
        Table table3 = new Table("TABLE3", "t3");
		sql.addTable(table3);
		sql.addJoin(table3, "columny", table1, "column1");
		sql.addCriteria(table3, "columnz", LIKE, "?");
		parameterValues.add(searchCriteria.getValue() + WILDCARD);

        sql.addGroupByColumn(table1, "column1");
        sql.addGroupByColumn(table1, "column2");
		return sql.toString();
	}
	
	public static void main(String[] args) {
		SampleSearchCriteria searchCriteria
			= new SampleSearchCriteria();
		searchCriteria.setValue("sample");
		List<String> parameters = new ArrayList<String>();
        System.out.println(build(searchCriteria,parameters));
	}
}
