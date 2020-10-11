package com.prasune.sql.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class SelectQuery
{

    private final List<Table> tables = new ArrayList<Table>();
    private final List<Criteria> criterias = new ArrayList<Criteria>();
    private final List<String> groupByColumns = new ArrayList<String>();

    private boolean isDistinct = false;

    public void setDistinct(boolean isDistinct)
    {
        this.isDistinct = isDistinct;
    }

    public void addJoin(Table table1, String column1, Table table2,
                        String column2)
    {
        String left = getColumnWithAlias(table1, column1);
        String right = getColumnWithAlias(table2, column2);
        Criteria joinCriteria = new Criteria(left, Criteria.EQUALS, right);
        criterias.add(joinCriteria);
    }

    public void addCriteria(Table table, String column, String operator,
                            Object value)
    {
        String left = getColumnWithAlias(table, column);
        Criteria simpleCriteria =
            new Criteria(left, operator, value.toString());
        criterias.add(simpleCriteria);
    }

    public void addCriteria(Table table, String column, String operator,
                            List<Object> values)
    {
        String left = getColumnWithAlias(table, column);
        StringBuilder right = new StringBuilder();
        ListIterator<Object> valueIterator = values.listIterator();
        right.append("(");
        while (valueIterator.hasNext())
        {
            right.append(valueIterator.next().toString());
            if (valueIterator.hasNext())
            {
                right.append(",");
            }
        }
        right.append(")");

        Criteria simpleCriteria =
            new Criteria(left, operator, right.toString());
        criterias.add(simpleCriteria);
    }

    public void addGroupByColumn(Table table, String column)
    {
        String columnWithAlias = getColumnWithAlias(table, column);
        groupByColumns.add(columnWithAlias);
    }

    public void addTable(Table table)
    {
        tables.add(table);
    }

    public String toString()
    {
        StringBuilder sql = new StringBuilder();
        appendColumnSelect(sql);
        appendTables(sql);
        appendCriterias(sql);
        appendGroupBy(sql);
        return sql.toString();
    }

    private void appendGroupBy(StringBuilder sql)
    {
        if (groupByColumns.size() > 0)
        {
            sql.append(" GROUP BY ");
        }
        ListIterator<String> columnIterator =
            groupByColumns.listIterator();
        while (columnIterator.hasNext())
        {
            String column = columnIterator.next();
            sql.append(column);
            if (columnIterator.hasNext())
            {
                sql.append(",");
            }
            sql.append(" ");
        }
    }

    private void appendCriterias(StringBuilder sql)
    {
        if (criterias.size() > 0)
        {
            sql.append("\n");
            sql.append("WHERE ");
        }
        ListIterator<Criteria> criteriaIterator = criterias.listIterator();
        while (criteriaIterator.hasNext())
        {
            Criteria criteria = criteriaIterator.next();
            sql.append(criteria);
            sql.append("\n");
            if (criteriaIterator.hasNext())
            {
                sql.append(" AND ");
            }
        }
    }

    private void appendTables(StringBuilder sql)
    {
        sql.append("\n");
        sql.append("FROM ");
        ListIterator<Table> tableIterator = tables.listIterator();
        while (tableIterator.hasNext())
        {
            Table table = tableIterator.next();
            sql.append(table.getName());
            sql.append(" ");
            sql.append(table.getAlias());
            if (tableIterator.hasNext())
            {
                sql.append(",");
            }
            sql.append(" ");
        }
    }

    private void appendColumnSelect(StringBuilder sql)
    {
        sql.append("SELECT ");
        if (isDistinct)
        {
            sql.append("DISTINCT ");
        }
        ListIterator<Table> tableIterator = tables.listIterator();
        List<String> selectValues = new ArrayList<String>();
        while (tableIterator.hasNext())
        {
            Table table = tableIterator.next();
            selectValues.addAll(table.getColumnsWithAlias());
            selectValues.addAll(table.getGroupFunctions());
        }
        appendSelectValues(sql, selectValues);
    }

    private void appendSelectValues(StringBuilder sql,
                                    List<String> selectValues)
    {
        ListIterator<String> selectValueIterator = selectValues.listIterator();
        while (selectValueIterator.hasNext())
        {
            String selectValue = selectValueIterator.next();
            sql.append(selectValue);
            if (selectValueIterator.hasNext())
            {
                sql.append(",");
            }
            sql.append(" ");
        }
    }

    private String getColumnWithAlias(Table table1, String column1)
    {
        return table1.getAlias() + "." + column1;
    }
}
