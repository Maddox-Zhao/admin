package com.autocreate;



import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * JDBC数据库工具<p>
 * 可从数据库表生成xml、pojo，带中文注释，具体功能可以执行运行Main方法看看便知。
 * 可以根据表名生成，也可生成这个数据库所有表对应的信息，
 * 如果表有_(下划线)就把下划线前面的比如hx,emall去掉并然后把第一个字母大写作为类名
 * 表对应的字段生成方式和类名生成方式大同小异,不过字段是第一个字母小写,后面的首字母大写
 * 
 * @author Mr_Yang  
 */
public class MyGenerateCodeUtil {
    private static String url;
    private static String driverClassName;
    private static Properties dbproperties;
    private static Map<String, String> dataMap = new HashMap<String, String>();

    static {
        InputStream ins_db = MyGenerateCodeUtil.class.getResourceAsStream("db.properties");
        dbproperties = new Properties();
        try {
            dbproperties.load(ins_db);
            url = dbproperties.getProperty("url");
            driverClassName = dbproperties.getProperty("driverClassName");
        } catch (IOException e) {
            System.err.println("读取db.properties文件失败！" + e.getMessage());
            e.printStackTrace();
        }
        //初始化SQL类型到java类型的映射
        dataMap.put("2003", "String");
        dataMap.put("-5", "Long");
        dataMap.put("-2", "Blob");
        dataMap.put("-7", "boolean");
        dataMap.put("2004", "Blob");
        dataMap.put("16", "boolean");
        dataMap.put("1", "String");
        dataMap.put("2005", "String");
        dataMap.put("70", "String");
        dataMap.put("91", "Date");
        dataMap.put("3", "BigDecimal");
        dataMap.put("2001", "String");
        dataMap.put("8", "String");
        dataMap.put("6", "Double");
        dataMap.put("4", "Long");
        dataMap.put("2000", "String");
        dataMap.put("-4", "Blob");
        dataMap.put("-1", "String");
        dataMap.put("0", "String");
        dataMap.put("2", "Double");
        dataMap.put("1111", "String");
        dataMap.put("7", "String");
        dataMap.put("2006", "String");
        dataMap.put("5", "Integer");
        dataMap.put("2002", "String");
        dataMap.put("92", "Date");
        dataMap.put("93", "Date");
        dataMap.put("-6", "Integer");
        dataMap.put("-3", "String");
        dataMap.put("12", "String");
    }

    /**
     * 私有构造方法，禁止实例化
     */
    private MyGenerateCodeUtil() {
    }

    /**
     * 数据连接获取工具
     *
     * @return 一个数据连接
     */
    public static Connection makeConnection() {
        Connection conn = null;
        try {
            Class.forName(driverClassName);
            conn = DriverManager.getConnection(url, dbproperties.getProperty("username"),dbproperties.getProperty("password"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 读取所有表的信息
     *
     * @return 所有表的信息
     * @throws SQLException
     */
    public static List<TableInfoBean> getAllTableInfo() throws SQLException {
        List<TableInfoBean> tableInfoBeanList = new ArrayList<TableInfoBean>();
        Connection conn = makeConnection();
        DatabaseMetaData databaseMetaData = conn.getMetaData();
        //获取表信息
        ResultSet tableSet = databaseMetaData.getTables(null, "%", "%", new String[]{"TABLE"});
        for (; tableSet.next();) {
            TableInfoBean tableInfoBean = new TableInfoBean();
            String tableName = tableSet.getString("TABLE_NAME");
            String tableComment = tableSet.getString("REMARKS");
            tableInfoBean.setTableName(splitString(tableName,"table")); //将hx去掉，并把第一个字母转化为大写
            tableInfoBean.setTableComment(tableComment);
            
            //获取表列信息
            ResultSet columnSet = databaseMetaData.getColumns(null, "%", tableName, "%");
            for (; columnSet.next();) {
                ColumnBean columnBean = new ColumnBean();
                String columnName = columnSet.getString("COLUMN_NAME");
                String columnComment = columnSet.getString("REMARKS");
                String sqlType = columnSet.getString("DATA_TYPE");
                columnBean.setColumnName(splitString(columnName,"column")); //将_下划线去掉，并把第二个字母的首字母大写
                columnBean.setColumnComment(columnComment);
                columnBean.setSqlType(sqlType);
                tableInfoBean.getColumnList().add(columnBean);
            }
            tableInfoBeanList.add(tableInfoBean);
        }
        conn.close();
        return tableInfoBeanList;
    }
    
    
    /**
     * 读取某个表的信息
     *
     * @return 某个表的信息
     * @throws SQLException
     */
    public static TableInfoBean getTableInfoByTableName(String tableName) throws SQLException {
        Connection conn = makeConnection();
        DatabaseMetaData databaseMetaData = conn.getMetaData();
        ResultSet columnSet = databaseMetaData.getColumns(null, "%", tableName, "%");
        TableInfoBean tableInfo = new TableInfoBean();
        tableInfo.setTableName(splitString(tableName, "table"));
        tableInfo.setSqlTableName(tableName);
         while (columnSet.next()) {
                ColumnBean columnBean = new ColumnBean();
                String columnName = columnSet.getString("COLUMN_NAME");
                columnBean.setSqlColumnName(columnName);
                String columnComment = columnSet.getString("REMARKS");
                String sqlType = columnSet.getString("DATA_TYPE");
                columnBean.setColumnName(splitString(columnName,"column")); //将_下划线去掉，并把第二个字母的首字母大写
                columnBean.setColumnComment(columnComment);
                columnBean.setSqlType(sqlType);
                tableInfo.getColumnList().add(columnBean);
            }
        conn.close();
        return tableInfo;
    }
    
    private static String splitString(String s,String type)
    {
    	String result = "";
    	String[] splitString = s.split("_");
    	if(splitString.length >=2)
    	{
    		if(type.equals("table"))
    		{
    			result = splitString[1].substring(0,1).toUpperCase() + splitString[1].substring(1);
    		}
    		else
    		{
    			result = splitString[0] + splitString[1].substring(0,1).toUpperCase() + splitString[1].substring(1);
    		}
    	}
    	else
    	{
    		if(type.equals("table"))
    		{
    			result = s.substring(0,1).toUpperCase() + s.substring(1);
    		}
    		else
    		{
    			result = s.toLowerCase();
    		}
    	}
    	return result;
    }

    /**
     * 将表信息转换为XML
     *
     * @return 表信息的XML展示
     * @throws SQLException
     */
    public static String table2Xml() throws SQLException {
        StringBuilder sb = new StringBuilder();
        List<TableInfoBean> tableInfoBeanList = getAllTableInfo();
        sb.append("<tables>").append("\n");
        for (TableInfoBean tbean : tableInfoBeanList) {
            sb.append("<" + tbean.getTableName() + " cn=\"" + tbean.getTableComment() + "\">").append("\n");
            for (ColumnBean cbean : tbean.getColumnList()) {
                sb.append("\t<" + cbean.getColumnName() + " cn=\"" + cbean.getColumnComment() + "\"/>").append("\n");
            }
            sb.append("</" + tbean.getTableName() + ">").append("\n");
            sb.append("\n");
        }
        sb.append("</tables>");
        return sb.toString();
    }

    public static String table2Cvs() throws SQLException {
        StringBuilder sb = new StringBuilder("\n\n\n");
        List<TableInfoBean> tableInfoBeanList = getAllTableInfo();
        for (TableInfoBean tbean : tableInfoBeanList) {
            sb.append(tbean.getTableName()).append("\t").append(tbean.getTableComment()).append("\t\n");
            for (ColumnBean cbean : tbean.getColumnList()) {
                sb.append(cbean.getColumnName()).append("\t").append(cbean.getColumnComment()).append("\t\n");
            }
            sb.append("\n\n\n");
        }
        return sb.toString();
    }

    /**
     * 将表转换为Java Bean的属性定义
     *
     * @return Bean成员的属性列表
     */
    public static String table2BeanProperties() throws SQLException {
        StringBuilder sb = new StringBuilder();
        List<TableInfoBean> tableInfoBeanList = getAllTableInfo();
        for (TableInfoBean tbean : tableInfoBeanList) {
            sb.append("public class " + tbean.getTableName() + " implements Serializable {\n");
            for (ColumnBean cbean : tbean.getColumnList()) {
//                sb.append("\tprivate " + dataMap.get(cbean.getSqlType()) + " " + cbean.getColumnName() + "\t\t//" + cbean.getSqlType() + ":" + cbean.getColumnComment() + "\n");
                sb.append("\tprivate " + dataMap.get(cbean.getSqlType()) + " " + cbean.getColumnName() + ";\t\t//" + cbean.getColumnComment() + "\n");
            }
            sb.append("\n}");
            sb.append("\n");
        }
        return sb.toString();
    }
    

    /**
     * 通过表名将表转换为Java Bean的属性定义
     *
     * @return 生产的字符串
     */
    public static String table2BeanPropertiesByTableName(String tableName) throws SQLException 
    {
        StringBuilder sb = new StringBuilder();
        TableInfoBean tableInfo = getTableInfoByTableName(tableName);
            sb.append("public class " + tableInfo.getTableName() + " implements Serializable {\n");
            for (ColumnBean cbean : tableInfo.getColumnList()) {
                sb.append("\tprivate " + dataMap.get(cbean.getSqlType()) + " " + cbean.getColumnName() + ";\t\t//" + cbean.getColumnComment() + "\n");
            }
            sb.append("\n}");
            sb.append("\n");
        return sb.toString();
    }

    /**
     * 从数据库中的表生成iBatis的SqlMap工具
     *
     * @return 生成的SQLMap
     * @throws SQLException
     */
    public static String table2Sqlmap() throws SQLException {
        StringBuilder sb = new StringBuilder();
        List<TableInfoBean> tableInfoBeanList = getAllTableInfo();
        for (TableInfoBean tbean : tableInfoBeanList) {
            StringBuilder cstr = new StringBuilder();
            StringBuilder cpstr = new StringBuilder();
            StringBuilder upstr = new StringBuilder();

            sb.append("<!-- 表名:" + tbean.getSqlTableName() + " -->\n");
            sb.append("<sqlMap namespace=\"" + tbean.getTableName() + "\">\n");
            sb.append("\t<typeAlias alias=\"AliasName\" type=\"MyFullClassName\"/>\n");
            sb.append("\t<resultMap id=\"result_base\"  class=\"AliasName\">\n");

            for (ColumnBean cbean : tbean.getColumnList()) {
                sb.append("\t\t<result property=\"" + cbean.getColumnName() + "\" column=\"" + cbean.getColumnName() + "\"/>\n");
            }
            sb.append("\t</resultMap>\n");

            int _index = 1;
            int csize = tbean.getColumnList().size();
            for (Iterator cit = tbean.getColumnList().iterator(); cit.hasNext(); _index++) {
                ColumnBean cbean = (ColumnBean) cit.next();
                cstr.append("\t\t\t" + cbean.getColumnName());
                cpstr.append("\t\t\t#").append(cbean.getColumnName()).append("#");
                upstr.append("\t\t\t" + cbean.getColumnName()).append(" = #" + cbean.getColumnName() + "#");
                if (_index < csize) {
                    cstr.append(",\n");
                    cpstr.append(",\n");
                    upstr.append(",\n");
                } else {
                    cstr.append("\n");
                    cpstr.append("\n");
                }
            }

            sb.append("\t<!-- 根据ID获取 -->\n");
            sb.append("\t<select id=\"findById\" parameterClass=\"long\" resultMap=\"result\">\n");
            sb.append("\t\tselect *\n");
            sb.append("\t\t  from " + tbean.getSqlTableName() + "\n\t\t where id = #value#\n");
            sb.append("\t</select>\n");
            
            
            sb.append("\t<!-- 添加 -->\n");
            sb.append("\t<insert id=\"insert\" parameterClass=\"AliasName\">\n");
            sb.append("\t\tinsert into " + tbean.getSqlTableName()+ "(\n");
            sb.append(" " + cstr.toString()).
                    append("\t\t) values (\n");
            sb.append(" " + cpstr.toString()).append("\t\t)\n");
            sb.append("\t\t<selectKey keyProperty=\"id\" resultClass=\"long\">\n");
            sb.append("\t\t\tselect LAST_INSERT_ID()\n");
            sb.append("\t\t</selectKey>\n");
            sb.append("\t</insert>\n");

            sb.append("\t<!-- 更新 -->\n");
            sb.append("\t<update id=\"update\" parameterClass=\"AliasName\">\n");
            sb.append("\t\tupdate " + tbean.getSqlTableName() + " set\n");
            sb.append(upstr.toString());
            sb.append("\n\t\t where id = #id#\n");
            sb.append("\t</update>\n");

            sb.append("\t<!-- 删除 -->\n");
            sb.append("\t<delete id=\"deleteById\" parameterClass=\"long\">\n");
            sb.append("\t\tdelete from " + tbean.getSqlTableName()+ "\n\t\t where id = #value#\n");
            sb.append("\t</delete>\n");

          

            sb.append("\t<!-- 总记录数查询 -->\n");
            sb.append("\t<select id=\"getAllCount\" resultClass=\"int\">\n");
            sb.append("\t\tselect count(*)\n");
            sb.append("\t\t  from " + tbean.getSqlTableName() + "\n");
            sb.append("\t</select>\n");

            sb.append("\t<!-- 动态条件分页查询 -->\n");
            sb.append("\t<sql id=\"sql_count\">\n");
            sb.append("\t\tselect count(*)\n");
            sb.append("\t</sql>\n");
            sb.append("\t<sql id=\"sql_select\">\n");
            sb.append("\t\tselect *\n");
            sb.append("\t</sql>\n");
            sb.append("\t<sql id=\"sql_where\">\n");
            sb.append("\t\t<![CDATA[\n");
            sb.append("\t\t  from " + tbean.getSqlTableName()+ "\n");
            sb.append("\t\t where 1 = 1\n");
            sb.append("\t\t   and '$name' like '%$varName$%'\n");
            sb.append("\t\t   and '$name' like '%' || #$name# || '%'\n");
            sb.append("\t\t order by id asc\n");
            sb.append("\t\t]]>\n");
            sb.append("\t\t<dynamic prepend=\"\">\n");
            sb.append("\t\t\t<isNotNull property=\"_start\">\n");
            sb.append("\t\t\t\t<isNotNull property=\"_size\">\n");
            sb.append("\t\t\t\t\tlimit #_start#, #_size#\n");
            sb.append("\t\t\t\t</isNotNull>\n");
            sb.append("\t\t\t</isNotNull>\n");
            sb.append("\t\t</dynamic>\n");
            sb.append("\t</sql>\n");
            sb.append("\t<select id=\"findByParamsForCount\" parameterClass=\"map\" resultClass=\"int\">\n");
            sb.append("\t\t<include refid=\"sql_count\"/>\n");
            sb.append("\t\t<include refid=\"sql_where\"/>\n");
            sb.append("\t</select>\n");
            sb.append("\t<select id=\"findByParams\" parameterClass=\"map\" resultMap=\"result\">\n");
            sb.append("\t\t<include refid=\"sql_select\"/>\n");
            sb.append("\t\t<include refid=\"sql_where\"/>\n");
            sb.append("\t</select>\n");

//            sb.append("\t<!-- 静态不分页查询 -->\n");
//            sb.append("\t<select id=\"findByCondition\" parameterClass=\"map\" resultClass=\"AliasName\">\n");
//            sb.append("\t\t<![CDATA[\n");
//            sb.append("\t\tselect *\n");
//            sb.append("\t\t  from " + tbean.getTableName() + "\n");
//            sb.append("\t\t where 1 = 1\n");
//            sb.append("\t\t   and 2 > 1\n");
//            sb.append("\t\t]]>\n");
//            sb.append("\t</select>\n");

//            sb.append("\t<!-- 动态条件查询 -->\n");
//            sb.append("\t<select id=\"findByDynamic\" parameterClass=\"AliasName\" resultClass=\"AliasName\">\n");
//            sb.append("\t\tselect *\n");
//            sb.append("\t\t  from " + tbean.getTableName() + "\n");
//            sb.append("\t\t<dynamic prepend=\"where\">\n");
//            sb.append("\t\t\t<isNotEmpty prepend=\"and\" property=\"$$$$$\">\n");
//            sb.append("\t\t\t\t$name like '%'|| #$name# ||'%'\n");
//            sb.append("\t\t\t</isNotEmpty>\n");
//            sb.append("\t\t\t<isGreaterThan prepend=\"and\" property=\"$$$$$\" compareValue=\"$$$number\">\n");
//            sb.append("\t\t\t\t$code like '%'|| #$code# ||'%'\n");
//            sb.append("\t\t\t</isGreaterThan>\n");
//            sb.append("\t\t</dynamic>\n");
//            sb.append("\t</select>\n");

            sb.append("</sqlMap>\n\n\n");
        }
        return sb.toString();
    }
    
    
    /**
     * 从数据库中的表生成iBatis的SqlMap工具
     *
     * @return 生成的SQLMap
     * @throws SQLException
     */
    public static String table2SqlmapByTableName(String tableName) throws SQLException {
        StringBuilder sb = new StringBuilder();
        TableInfoBean tableInfoBean =  getTableInfoByTableName(tableName);
            StringBuilder cstr = new StringBuilder();
            StringBuilder cpstr = new StringBuilder();
            StringBuilder upstr = new StringBuilder();
            StringBuilder fields = new StringBuilder();

            sb.append("<!-- 表名:" + tableInfoBean.getSqlTableName() + " -->\n");
            sb.append("<sqlMap namespace=\"" + tableInfoBean.getTableName().toLowerCase() + "\">\n");
            sb.append("\t<typeAlias alias=\"" + tableInfoBean.getTableName() + "\" type=\"MyFullClassName\"/>\n");
            sb.append("\t<resultMap id=\"" +  tableInfoBean.getTableName()+ "Result\"" + " class=\""+ tableInfoBean.getTableName() +  "\">\n");

            for (ColumnBean cbean : tableInfoBean.getColumnList()) {
                sb.append("\t\t<result property=\"" + cbean.getColumnName() + "\" column=\"" + cbean.getSqlColumnName() + "\"/>\n");
            }
            sb.append("\t</resultMap>\n");

            int _index = 1;
            int csize = tableInfoBean.getColumnList().size();
            
            for (Iterator cit = tableInfoBean.getColumnList().iterator(); cit.hasNext(); _index++) {
                ColumnBean cbean = (ColumnBean) cit.next();
                cstr.append("\t\t\t" + cbean.getSqlColumnName());
                cpstr.append("\t\t\t#").append(cbean.getColumnName()).append("#");
                upstr.append("\t\t\t<isNotNull prepend=\",\" property=\"" + cbean.getColumnName()+ "\">\n");
                upstr.append("\t\t\t\t" + cbean.getSqlColumnName() + "=#" +cbean.getColumnName()+"#\n");
                upstr.append("\t\t\t</isNotNull>\n");
                fields.append(cbean.getSqlColumnName());
                if (_index < csize) {
                    cstr.append(",\n");
                    cpstr.append(",\n");
                    fields.append(",");
                } else {
                    cstr.append("\n");
                    cpstr.append("\n");
                }
            }
          
            sb.append("\t<sql id=\"" + tableInfoBean.getTableName() + "Fields\">\n\t\t\t" + fields.toString() + "\n\t</sql>\n");
            
            
            sb.append("\t<!-- 根据ID获取 -->\n");
            sb.append("\t<select id=\"select" + tableInfoBean.getTableName()+ "ById\" parameterClass=\"java.lang.Long\" resultMap=\"" + tableInfoBean.getTableName() + "Result\">\n");
            sb.append("\t\tselect <include refid=\"" + tableInfoBean.getTableName() + "Fields\"/>\n");
            sb.append("\t\t  from " + tableInfoBean.getSqlTableName() + "\n\t\t where id = #id#\n");
            sb.append("\t</select>\n");
            
            
            sb.append("\t<!-- 添加 -->\n");
            sb.append("\t<insert id=\"insert" + tableInfoBean.getTableName()+"\" parameterClass=\"" +tableInfoBean.getTableName() +"\">\n");
            sb.append("\t\tinsert into " + tableInfoBean.getTableName() + "(\n");
            sb.append(" " + cstr.toString()).
                    append("\t\t) values (\n");
            sb.append(" " + cpstr.toString()).append("\t\t)\n");
            sb.append("\t\t<selectKey keyProperty=\"id\" resultClass=\"Long\">\n");
            sb.append("\t\t\tselect LAST_INSERT_ID()\n");
            sb.append("\t\t</selectKey>\n");
            sb.append("\t</insert>\n");

            sb.append("\t<!-- 更新 -->\n");
            sb.append("\t<update id=\"update"+ tableInfoBean.getTableName()+ "ByNotNull\"" +  " parameterClass=\""+ tableInfoBean.getTableName()+"\">\n");
            sb.append("\t\tupdate " + tableInfoBean.getSqlTableName() + "\n");
            sb.append("\t\t<dynamic prepend=\"set\">\n");
            sb.append(upstr.toString());
            sb.append("\t\t where id = #id#\n");
            sb.append("\t\t</dynamic>\n");
            sb.append("\t</update>\n");

            sb.append("\t<!-- 删除 -->\n");
            sb.append("\t<delete id=\"delete" + tableInfoBean.getTableName() + "ById\" parameterClass=\"java.lang.Long\">\n");
            sb.append("\t\tdelete from " + tableInfoBean.getSqlTableName() + "\n\t\t where id = #id#\n");
            sb.append("\t</delete>\n");

        

            
            sb.append("</sqlMap>\n\n\n");
            return sb.toString();
    }

    


    public static String gbk2Utf8(String gbkstr) {
        String utf8str = null;
        try {
            utf8str = new String(gbkstr.getBytes("GBK"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return utf8str;
    }

    public static String utf82Gbk(String utf8str) {
        String gbkstr = null;
        try {
            utf8str = new String(utf8str.getBytes("UTF-8"), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return utf8str;
    }
    
    
    
    
    
    
 
    
    /**
     * 简单的测试
     *
     * @param args
     * @throws SQLException
     */
    public static void main(String args[]) throws SQLException, UnsupportedEncodingException {
    	/*
        Connection conn = makeConnection();
        String dbname = conn.getCatalog();
        System.out.println("\n\n>>>>>>>>>>>>>>>下面是从表生成XML配置代码>>>>>>>>>>>>>>>\n");
        //System.out.println(table2Xml());
        //System.out.println(table2Cvs());
        List<TableInfoBean> tableInfoBeanList = getAllTableInfo();
        System.out.println("\n\n>>>>>>>>>>>>>>>下面是从表生成POJO的框架代码>>>>>>>>>>>>>>>\n");
        System.out.println(table2BeanProperties());
        System.out.println(table2Sqlmap());

        System.out.println("\n\n>>>>>>>>>>>>>>>Very good! Develop with pleasure!>>>>>>>>>>>>>>>\n");
        */
    	
    	String str = MyGenerateCodeUtil.table2SqlmapByTableName("hx_stock_not_update");
    	System.out.println(str);
    }
    
    
}