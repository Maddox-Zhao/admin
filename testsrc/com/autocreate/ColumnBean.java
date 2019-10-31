package com.autocreate;

/**
 * ÁÐ×Ö¶Î
 * @author Mr_Yang
 *
 */
public class ColumnBean {
    private String columnName;
    private String columnComment;
    private String SqlType;
    private String sqlColumnName;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getSqlType() {
        return SqlType;
    }

    public void setSqlType(String sqlType) {
        SqlType = sqlType;
    }

	public String getSqlColumnName()
	{
		return sqlColumnName;
	}

	public void setSqlColumnName(String sqlColumnName)
	{
		this.sqlColumnName = sqlColumnName;
	}
    
}
