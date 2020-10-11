package com.prasune.sql.builder;

public class Criteria {

	public static final String EQUALS = "=";
    public static final String GREATER = ">";
    public static final String GREATEREQUAL = ">=";
    public static final String LESS = "<";
    public static final String LESSEQUAL = "<=";
    public static final String LIKE = "LIKE";
    public static final String NOTEQUAL = "<>";
    public static final String IN = "IN";

    private final String left;
    private final String operator;
    private final String right;

    public Criteria(String left, String operator, String right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    
    public String toString(){
    	StringBuilder criteria = new StringBuilder();
    	criteria.append(left);
    	criteria.append(" ");
    	criteria.append(operator);
    	criteria.append(" ");
    	criteria.append(right);
    	return criteria.toString();
    }
}
