package com.huaixuan.network.biz.enums;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *2012-6-19 下午02:05:26
 *Mr_Yang
 */
public enum EnumClientPermission
{
	CLIENT_SHOWWELC("001","欢迎页",true,null),
	CILENT_F_CHANNEL_NETWORK("001.001","渠道_网络",false,CLIENT_SHOWWELC),
	CILENT_F_CHANNEL_HUAIXUAN("001.002","渠道_尚上形象店",false,CLIENT_SHOWWELC),
	CILENT_F_CHANNEL_SHANXISOUTH("001.003","渠道_陕西南路店",false,CLIENT_SHOWWELC),
	CILENT_F_CHANNEL_LUSSOMODA("001.004","渠道_LUSSO&MODA",false,CLIENT_SHOWWELC),
	CILENT_F_CHANNEL_ZIZHONG("001.005","渠道_自中路店",false,CLIENT_SHOWWELC),
	CILENT_F_CHANNEL_HUANGJIN("001.006","渠道_黄金城道店",false,CLIENT_SHOWWELC),
	CILENT_F_CHANNEL_JIUGUANG("001.007","渠道_久光店",false,CLIENT_SHOWWELC),
	CILENT_F_CURRENCY_RMB("001.008","币种_人民币",false,CLIENT_SHOWWELC),
	CILENT_F_CURRENCY_HKD("001.009","币种_港币",false,CLIENT_SHOWWELC),
	CILENT_F_HXPRICE("001.010","尚上_价",false,CLIENT_SHOWWELC),
	CILENT_F_HXHKPRICE("001.011","HK_价",false,CLIENT_SHOWWELC),
	CILENT_SITE_SHANXISOUTH("001.012","地点_陕西南路店",false,CLIENT_SHOWWELC),
	CILENT_SITE_HK("001.013","地点_香港总仓",false,CLIENT_SHOWWELC),
	CILENT_SITE_HX("001.014","地点_上海分库",false,CLIENT_SHOWWELC),
	CILENT_SITE_ZIZHONG("001.015","地点_自忠路店",false,CLIENT_SHOWWELC),
	CILENT_SITE_HUANGJIN("001.016","地点_黄金城道店",false,CLIENT_SHOWWELC),
	CILENT_SITE_JIUGUANG("001.017","地点_久光店",false,CLIENT_SHOWWELC),
	CILENT_SHOWSUPPLYNAME("001.018","显示供应商的名字",false,CLIENT_SHOWWELC),
	CILENT_SHOWSUPPLYID("001.019","显示供应商ID",false,CLIENT_SHOWWELC),
	CILENT_HIDE_INDATE("001.020","隐藏入库时间",false,CLIENT_SHOWWELC),
	CILENT_SHOW_COST("001.021","显示成本",false,CLIENT_SHOWWELC),
	CILENT_SHOW_COSTCURRENCY("001.022","显示成本单位",false,CLIENT_SHOWWELC),
	CILENT_UPDATEDATA("001.023","更新库存",false,CLIENT_SHOWWELC),
	CILENT_SHOW_CUSTOMER_ID("001.024","只显示客户ID",false,CLIENT_SHOWWELC),
	CILENT_SHOW_CUSTOMER_NAME("001.025","只显示客户姓名",false,CLIENT_SHOWWELC),
	CILENT_WHEN_CUSTOMER_N1("001.026","当客户ID 等于－1",false,CLIENT_SHOWWELC),
	
	
	CILENT_SHOWPRODUCT("002","产品页",true,null),
	CILENT_SHOWDISCOUNTPRICE_BEYOND_60("002.001","是否 可以看60天 后 的打折",false,CILENT_SHOWPRODUCT),
	CILENT_SHOW_STORAGE_IN_BUTTON("002.002","显示入库按钮",false,CILENT_SHOWPRODUCT),
	CILENT_SHOW_PREPARE_STORAGE_IN_BUTTON("002.003","显示提交按钮",false,CILENT_SHOWPRODUCT),
	CILENT_COMPLETE_STORAGE_IN("002.004","完成入库",false,CILENT_SHOWPRODUCT),
	CILENT_COMPLETE_PREPARE_STORAGE_IN("002.005","完成提交",false,CILENT_SHOWPRODUCT),
	CILENT_OPERATE_IMAGE("002.006","是否可以操作图片（上传＆＆更新）",false,CILENT_SHOWPRODUCT),
	CILENT_CONFIRM_STORAGE_IN("002.007","是否可以确认入库",false,CILENT_SHOWPRODUCT),
	CILENT_UPDATE_VALID_CARD("002.008","更新真品卡",false,CILENT_SHOWPRODUCT),
	CILENT_HK_PRODUCT_TO_MAINLAND("002.009","香港产品调到大陆",false,CILENT_SHOWPRODUCT),
	CILENT_MAINLAND_PRODUCT_TO_HK("002.010","大陆产品调到香港",false,CILENT_SHOWPRODUCT),
	CILENT_EDIT_PRODUCTINFO_WHEN_PREPARE_IN("002.011","当是准入库,是否可以编辑产品信息（默认是最高admin）",false,CILENT_SHOWPRODUCT),
	CILENT_SOURCE_PURCHASE("002.012","采购产品",false,CILENT_SHOWPRODUCT),
	CILENT_SOURCE_NEW("002.013","新品",false,CILENT_SHOWPRODUCT),
	CILENT_SOURCE_SECOND("002.014","二手",false,CILENT_SHOWPRODUCT),
	CILENT_SOURCE_SECOND_SELL("002.015","二手寄卖",false,CILENT_SHOWPRODUCT),
	CILENT_SET_PRODUCT_STATUS_TO_SELL("002.016","可售_产品信息页面",false,CILENT_SHOWPRODUCT),
	CILENT_SET_PRODUCT_STATUS_TO_PREARE_IN("002.017","准入库_产品信息页面",false,CILENT_SHOWPRODUCT),
	CILENT_VALIDCARD_999("002.018","是否设置validcard 为 999",false,CILENT_SHOWPRODUCT),
	CILENT_MIX_HK("002.019","sql 语句太复杂   ((curSiteId!=? and idStatus!=? and idStatus!=?) or curSiteId=?) and",false,CILENT_SHOWPRODUCT),
	CILENT_MIX_SHOP("002.020","sql 语句太复杂   (curSiteId=? or (lastSiteId = ? and idStatus = 2) or (curSiteId!=? and city=? and idStatus=?)) and",false,CILENT_SHOWPRODUCT),
	CILENT_MIX_HXSTOLCK("002.021","sql 语句太复杂   (curSiteId=? or curSiteId=? or curSiteId=? or curSiteId=? or curSiteId=?) and",false,CILENT_SHOWPRODUCT),
	CILENT_ONLY_SHOW_CURSITE("002.022","只显示当前位置",false,CILENT_SHOWPRODUCT),
	CILENT_SET_PRODUCT_RED_OVER_60("002.023","超过60天显示红色",false,CILENT_SHOWPRODUCT),
	CILENT_SHOW_SELLOUT("002.024","显示销售出库",false,CILENT_SHOWPRODUCT),
	CILENT_SHOW_BOOK("002.025","显示预留",false,CILENT_SHOWPRODUCT),
	CILENT_SHOW_UNBOOK("002.026","显示取消预留",false,CILENT_SHOWPRODUCT),
	CILENT_SHOW_PROMATION("002.027","显示已参加活动",false,CILENT_SHOWPRODUCT),
	CILENT_SHOW_SHOPLIST("002.028","显示加入购物车",false,CILENT_SHOWPRODUCT),
	CILENT_SHOW_OUT("002.029","显示调货出库",false,CILENT_SHOWPRODUCT),
	CILENT_SHOW_IN("002.030","显示调货入库",false,CILENT_SHOWPRODUCT),
	CILENT_SHOW_SELLIN("002.031","显示销售入库",false,CILENT_SHOWPRODUCT),
	CILENT_SHOW_CANCEL("002.032","显示退货",false,CILENT_SHOWPRODUCT),
	CILENT_SHOW_CLEAR("002.033","显示结清",false,CILENT_SHOWPRODUCT),
	CILENT_SHOW_ORDER("002.034","显示订单",false,CILENT_SHOWPRODUCT),
	CILENT_SHOW_HISTORY("002.035","显示历史",false,CILENT_SHOWPRODUCT),
	
	
	
	CILENT_SHOWCUSTOMER("003","客户页",true,null),
	CILENT_CUSTOMER_DISPLAY("003.001","客户界面显示",false,CILENT_SHOWCUSTOMER),
	CILENT_CUSTOMER_SEARCH("003.002","查询客户",false,CILENT_SHOWCUSTOMER),
	CILENT_CUSTOMER_ADD("003.003","添加新客户",false,CILENT_SHOWCUSTOMER),
	CILENT_CUSTOMER_INFORMATION("003.004","客户信息",false,CILENT_SHOWCUSTOMER),
	CILENT_CUSTOMER_TYPE_PERSONAL("003.005","零售客户",false,CILENT_SHOWCUSTOMER),
	CILENT_CUSTOMER_TYPE_WHOLESALER("003.006","批发客户",false,CILENT_SHOWCUSTOMER),
	CILENT_CUSTOMER_TYPE_COMISSION("003.007","代销客户",false,CILENT_SHOWCUSTOMER),
	CILENT_CUSTOMER_ALL_SHOW("003.008","页面全部显示",false,CILENT_SHOWCUSTOMER),
	CILENT_CUSTOMER_TYPE_DISPLAY("003.009","支持客户类型的查询",false,CILENT_SHOWCUSTOMER),
	
	
	CILENT_SHOWORDER("004","订单页",true,null),
	CILENT_F_PRINT_HX_PAGE("004.001","尚上_打印页数",false,CILENT_SHOWORDER),
	CILENT_F_PRINT_HK_PAGE("004.002","HK_打印页数",false,CILENT_SHOWORDER),
	
	
	CILENT_SHOWPURCHASE("005","采购页",true,null),
	CILENT_PURCHASE_405("005.001","买手_405",false,CILENT_SHOWPURCHASE),
	CILENT_START_PURCHASE("005.002","创建采购任务",false,CILENT_SHOWPURCHASE),
	CILENT_PURCHASE_404("005.006","买手_404",false,CILENT_SHOWPURCHASE),
	CILENT_PURCHASE_403("005.005","买手_403",false,CILENT_SHOWPURCHASE),
	CILENT_PURCHASE_402("005.004","买手_402",false,CILENT_SHOWPURCHASE),
	CILENT_PURCHASE_401("005.003","买手_401",false,CILENT_SHOWPURCHASE),
	CILENT_PURCHASE_ROLE("005.007","是不是买手",false,CILENT_SHOWPURCHASE),
	CILENT_PURCHASE_ADMIN_ROLE("005.008","是不是管理员身份",false,CILENT_SHOWPURCHASE),
	
	
	CILENT_SHOWSUPPLY("006","供应商页",true,null),
	CILENT_SUPPLY_SEARCH("006.001","供应商搜索",false,CILENT_SHOWSUPPLY),
	CILENT_SUPPLY_ADD("006.002","添加供应商",false,CILENT_SHOWSUPPLY),
	CILENT_SUPPLY_DISPLAY("006.003","全部显示供应商",false,CILENT_SHOWSUPPLY),
	
	
	CILENT_SHOWSUPPLYORDER("007","采购订单页",true,null),
	CILENT_PURCHASEORDER_ALL("007.001","支持查看所有买手的采购订单",false,CILENT_SHOWSUPPLYORDER),
	
	
	
	CILENT_SHOWPROMOTION("008","活动页",true,null),
	CILENT_MF_NEW_MOVEFRAME("008.001","新建活动",false,CILENT_SHOWPROMOTION),
	CILENT_MF_CHANGE_MOVEFRAME("008.002","修改活动",false,CILENT_SHOWPROMOTION),
	CILENT_MF_ADD_DEL_MODIFY_PRODUCT("008.003","增加删除修改活动产品",false,CILENT_SHOWPROMOTION),
	CILENT_MF_SEARCH_MOVEFRAME("008.004","查询活动",false,CILENT_SHOWPROMOTION),
	CILENT_MF_AREA_HK("008.005","地区为香港的活动",false,CILENT_SHOWPROMOTION),
	CILENT_MF_AREA_MAINLAND("008.006","地区为大陆的活动",false,CILENT_SHOWPROMOTION),
	CILENT_MF_STATU_OPEN("008.007","状态为开始的活动",false,CILENT_SHOWPROMOTION),
	CILENT_MF_STATU_CLOSE("008.008","状态为关闭的活动",false,CILENT_SHOWPROMOTION),
	CILENT_MF_STATU_WAIT("008.009","状态为等待的活动",false,CILENT_SHOWPROMOTION),
	CILENT_MF_DOUBLECLICK_MFINFO("008.010","双击查看活动产品的信息",false,CILENT_SHOWPROMOTION),
	CILENT_MF_COPY_MOVEFRAME("008.011","复制活动",false,CILENT_SHOWPROMOTION),
	CILENT_MF_SELECT_HK("008.012","可添加香港的产品",false,CILENT_SHOWPROMOTION),
	CILENT_MF_SELECT_MAINLAND("008.013","可添加大陆的产品",false,CILENT_SHOWPROMOTION),
	CILENT_MF_SELECT_EACHSITE("008.014","添加各自店的产品",false,CILENT_SHOWPROMOTION),
	CILENT_MF_MODIFY_DEPS("008.015","支持修改或添加部门",false,CILENT_SHOWPROMOTION);
	


	


    /**
     * @param id        权限ID
     * @param name      权限名称
     * @param isMenu    是否菜单
     * @param parent    父权限
     * @param system    系统
     */
	EnumClientPermission(String id, String name, boolean isMenu,EnumClientPermission parent) 
	{
        this.id = id;
        this.name = name;
        this.isMenu = isMenu;
        this.parent = parent;
    }

	    private String              id;
	    private String            name;
	    private Boolean           isMenu;
	    private EnumClientPermission parent;
		public String getId()
		{
			return id;
		}
		public void setId(String id)
		{
			this.id = id;
		}
		public String getName()
		{
			return name;
		}
		public void setName(String name)
		{
			this.name = name;
		}
		public Boolean getIsMenu()
		{
			return isMenu;
		}
		public void setIsMenu(Boolean isMenu)
		{
			this.isMenu = isMenu;
		}
		public EnumClientPermission getParent()
		{
			return parent;
		}
		public void setParent(EnumClientPermission parent)
		{
			this.parent = parent;
		}
		
		

	    public static List<EnumClientPermission> getParents() {
	        List<EnumClientPermission> list = new ArrayList<EnumClientPermission>();
	        for (EnumClientPermission enumClientPermission : EnumClientPermission.values()) {
	            if(enumClientPermission.getParent() == null) {
	                list.add(enumClientPermission);
	            }
	        }
	        return list;
	    }
		
		

	    public static List<EnumClientPermission> toList() {
	        List<EnumClientPermission> list = new ArrayList<EnumClientPermission>();
	        for (EnumClientPermission enumClientPermission : EnumClientPermission.values()) {
	            list.add(enumClientPermission);
	        }
	        return list;
	    }
		
		
		//从数据库获取数据
		public static void main(String[] args) throws Exception
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.28:3306/HYBAK?characterEncoding=utf-8","huaixuan","huaixuan");
			PreparedStatement ps = conn.prepareStatement("select * from hx_client_function where code like '002%' order by code");
			ResultSet rs = ps.executeQuery();
			String t = "";
			while(rs.next())
			{
				String code = rs.getString("code");
				String name = rs.getString("name");
				String desc = rs.getString("desc");
				String s = "";
				if(code.length() >3)
				{
					s = "CILENT_" + name.toUpperCase() + "(\"" + code + "\",\"" + desc + "\",false," + t + "),";
				}
				else
				{
					s = "CILENT_" + name.toUpperCase() + "(\"" + code + "\",\"" + desc + "\",true,null),";
					t = "CILENT_" + name.toUpperCase();
				}
				
				System.out.println(s);
			}
		}

}
