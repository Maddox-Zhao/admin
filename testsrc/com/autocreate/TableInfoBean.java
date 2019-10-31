package com.autocreate;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *表信息
 * @author Mr_Yang
 */
public class TableInfoBean {
    private String tableName;
    private String tableComment;
    private String sqlTableName;
    private List<ColumnBean> columnList = new ArrayList<ColumnBean>();

    
    public String getSqlTableName()
	{
		return sqlTableName;
	}

	public void setSqlTableName(String sqlTableName)
	{
		this.sqlTableName = sqlTableName;
	}

	public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public List<ColumnBean> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ColumnBean> columnList) {
        this.columnList = columnList;
    }
}
