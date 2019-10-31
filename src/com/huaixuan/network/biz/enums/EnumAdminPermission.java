package com.huaixuan.network.biz.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






/**
 * 后台管理员权限
 *
 */
public enum EnumAdminPermission {
	A_COMMON_USER("1", "系统通用权限", true, null, "erp"),
	A_CRM_COMMON_USER("99", "CRM系统通用权限", true, null, "crm"),
	 
	A_GOODS_MANAGER("100000", "商品管理", true, null, "erp"),
	A_GOODS_ADD_USER("100101", "商品发布(新增)权限", false, A_GOODS_MANAGER, "erp"),
	A_GOODS_DELETE_USER("100201", "商品删除权限", false, A_GOODS_MANAGER, "erp"),
	A_GOODS_MODIFY_USER("100202", "商品修改权限", false, A_GOODS_MANAGER, "erp"),
	A_GOODS_VIEW_USER("100203", "商品查询权限", false, A_GOODS_MANAGER, "erp"),
	A_GOODS_INSTANCE_MODIFY_USER("100301", "产品修改权限", false, A_GOODS_MANAGER, "erp"),
	A_GOODS_INSTANCE_VIEW_USER("100302", "产品查询权限", false, A_GOODS_MANAGER, "erp"),
	A_GOODS_DOLIST_USER("100303", "商品上架", false, A_GOODS_MANAGER, "erp"),
	A_GOODS_DOLIST_GB_USER("100304", "商品批量上架", false, A_GOODS_MANAGER, "erp"),
	A_CM_ADD_USER("100401", "类目新增权限", false, A_GOODS_MANAGER, "erp"),
	A_CM_DELETE_USER("100402", "类目删除权限", false, A_GOODS_MANAGER, "erp"),
	A_CM_MODIFY_USER("100403", "类目修改权限", false, A_GOODS_MANAGER, "erp"),
	A_CM_VIEW_USER("100404", "类目查询权限", false, A_GOODS_MANAGER, "erp"),
	A_AM_ADD_USER("100501", "属性新增权限", false, A_GOODS_MANAGER, "erp"),
	A_AM_DELETE_USER("100502", "属性删除权限", false, A_GOODS_MANAGER, "erp"),
	A_AM_MODIFY_USER("100503", "属性修改权限", false, A_GOODS_MANAGER, "erp"),
	A_AM_VIEW_USER("100504", "属性查询权限", false, A_GOODS_MANAGER, "erp"),
	A_PROMATION_ADD_USER("100601", "套餐新增权限", false, A_GOODS_MANAGER, "erp"),
	A_PROMATION_DELETE_USER("100602", "套餐删除权限", false, A_GOODS_MANAGER, "erp"),
	A_PROMATION_MODIFY_USER("100603", "套餐修改权限", false, A_GOODS_MANAGER, "erp"),
	A_PROMATION_VIEW_USER("100604", "套餐查询权限", false, A_GOODS_MANAGER, "erp"),
	A_COLLECTION_VIEW_USER("100605", "收藏管理权限", false, A_GOODS_MANAGER, "erp"),

	A_DEPOT_MANAGER("110000", "仓库管理", true, null, "erp"),
	A_DEPOSITORY_ADD_USER("110101", "仓库管理新增权限", false, A_DEPOT_MANAGER, "erp"),
	A_DEPOSITORY_MODIFY_USER("110102", "仓库管理修改权限", false, A_DEPOT_MANAGER, "erp"),
	A_DEPOSITORY_VIEW_USER("110103", "仓库管理查看权限", false, A_DEPOT_MANAGER, "erp"),
	A_DEPLOCATION_ADD_USER("110201", "库位管理新增权限", false, A_DEPOT_MANAGER, "erp"),
	A_DEPLOCATION_DELETE_USER("110202", "库位管理删除权限", false, A_DEPOT_MANAGER, "erp"),
	A_DEPLOCATION_MODIFY_USER("110203", "库位管理修改权限", false, A_DEPOT_MANAGER, "erp"),
	A_DEPLOCATION_VIEW_USER("110204", "库位管理查看权限(删除)", false, A_DEPOT_MANAGER, "erp"),
	A_IN_DEPOSITORY_MODIFY_USER("110301", "入库单管理修改权限", false, A_DEPOT_MANAGER, "erp"),
	A_IN_DEPOSITORY_VIEW_USER("110302", "入库单管理查看权限", false, A_DEPOT_MANAGER, "erp"),
	A_OUT_DEPOSITORY_USER("110401", "出库单管理权限", false, A_DEPOT_MANAGER, "erp"),
	A_STORAGE_VIEW_USER("110501", "库存查询权限(财务)", false, A_DEPOT_MANAGER, "erp"),
	A_STORAGE_DEPOTVIEW_USER("110502", "库存查询权限(仓库)", false, A_DEPOT_MANAGER, "erp"),
	A_ZERO_STORAGE_VIEW_USER("110601", "零库存查询权限", false, A_DEPOT_MANAGER, "erp"),
	A_STORAGE_CHECK_ADD_USER("110701", "库存盘点新增权限", false, A_DEPOT_MANAGER, "erp"),
	A_STORAGE_CHECK_MODIFY_USER("110702", "库存盘点修改权限", false, A_DEPOT_MANAGER, "erp"),
	A_STORAGE_CHECK_VIEW_USER("110703", "库存盘点查询权限", false, A_DEPOT_MANAGER, "erp"),
	A_STORAGE_WARN_VIEW_USER("110801", "库存预警查询权限", false, A_DEPOT_MANAGER, "erp"),
	A_DAMAGED_ADD_USER("110901", "报残单管理新增权限", false, A_DEPOT_MANAGER, "erp"),
	A_DAMAGED_VIEW_USER("110902", "报残单管理查询权限", false, A_DEPOT_MANAGER, "erp"),
	A_DAMAGED_EDIT_USER("110903", "报残单管理编辑权限", false, A_DEPOT_MANAGER, "erp"),
	A_DAMAGED_GOODS_VIEW_USER("111001", "报残单商品查询权限", false, A_DEPOT_MANAGER, "erp"),
	A_GETHER_IN_DEP_VIEW_USER("111101", "入库单成本汇总统计权限", false, A_DEPOT_MANAGER, "erp"),
	A_GETHER_OUT_DEP_VIEW_USER("111201", "出库单成本汇总统计权限", false, A_DEPOT_MANAGER, "erp"),
	A_STORAGE_LEDGER_USER("111301", "库存台帐查询权限", false, A_DEPOT_MANAGER, "erp"),
	A_STORAGE_MOVE_USER("111401", "移库外借管理权限", false, A_DEPOT_MANAGER, "erp"),
	A_STORAGE_RETURN_USER("111501", "库存归还权限", false, A_DEPOT_MANAGER, "erp"),
	A_STOCKOUT_SELECT("111601", "缺货登记查询权限", false, A_DEPOT_MANAGER, "erp"),
	A_STORAGE_AGE_USER("111701", "库龄查询权限", false, A_DEPOT_MANAGER, "erp"),
	A_ZERO_CHECK_USER("111801", "零库存盘盈权限", false, A_DEPOT_MANAGER, "erp"),
	A_DEP_FIRST_USER("111901", "一级仓库查询权限", false, A_DEPOT_MANAGER, "erp"),
	A_DEP_FIRST_MODIFY("111902", "一级仓库修改权限", false, A_DEPOT_MANAGER, "erp"),
	A_STORAGE_REFUND_APPLY_USER("112001", "库存退货申请查询权限", false, A_DEPOT_MANAGER, "erp"),
	A_STORAGE_REFUND_APPLY_DEPOSITORY("112002", "库存退货申请仓库权限", false, A_DEPOT_MANAGER, "erp"),
	A_STORAGE_REFUND_APPLY_PROCURE("112003", "库存退货申请采购权限", false, A_DEPOT_MANAGER, "erp"),
	A_ACTUAL_INVENTORY_USER("112101", "实际运费管理权限", false, A_DEPOT_MANAGER, "erp"),
	A_OUT_DEP_ANALYSIS("112201", "出库统计权限", false, A_DEPOT_MANAGER, "erp"),
	A_STORAGE_MOVE_DETAIL_USER("900105", "移库外借详情权限", false, A_DEPOT_MANAGER, "erp"),

	A_EXPRESS_MANAGER("120000", "物流管理", true, null, "erp"),
	A_EXPRESS_SET_USER("120101", "物流信息设置权限", false, A_EXPRESS_MANAGER, "erp"),
	A_EXPRESS_VIEW_USER("120102", "物流信息查看权限", false, A_EXPRESS_MANAGER, "erp"),
	A_EXPRESS_DIST_SET_USER("120201", "物流范围设置权限", false, A_EXPRESS_MANAGER, "erp"),
	A_EXPRESS_DIST_VIEW_USER("120202", "物流范围查看权限", false, A_EXPRESS_MANAGER, "erp"),
	A_EXPRESS_MOTOR_TRANS_INFO("120301", "汽运信息", false, A_EXPRESS_MANAGER, "erp"),
	A_EXPRESS_ANALYSIS_BY_EXP("120401", "物流分析(物流公司)权限", false, A_EXPRESS_MANAGER, "erp"),
	A_EXPRESS_ANALYSIS_BY_PRO("120402", "物流分析(省份)权限", false, A_EXPRESS_MANAGER, "erp"),
	A_CASTWEIGHT_USER("120501", "计抛重量管理权限", false, A_EXPRESS_MANAGER, "erp"),

	A_AGENT_MANAGER("130000", "代销管理", true, null, "erp"),
	A_AGENT_VIEW_USER("130101", "代销查看权限", false, A_AGENT_MANAGER, "erp"),
	A_INTERFACE_APPLY_USER("130102", "网店接入申请管理", false, A_AGENT_MANAGER, "erp"),
	A_TAOBAO_INTERFACE_APPLY_USER("130103", "淘宝网店接入申请管理", false, A_AGENT_MANAGER, "erp"),
	A_INTERFACE_DATA_USER("130104", "接口同步数据查询", false, A_AGENT_MANAGER, "erp"),

	A_FINANCE_MANAGE("140000", "财务管理", true, null, "erp"),
	A_FINANCE_MANAGE_USER("140101", "财务管理权限", false, A_FINANCE_MANAGE, "erp"),

	A_RETURN_MANAGE("150000", "返点管理", true, null, "erp"),
	A_RETURN_POINT_USER("150101", "返点规则管理权限", false, A_RETURN_MANAGE, "erp"),
	A_RETURN_POINT_APPLY_USER("150201", "会员返点审核权限", false, A_RETURN_MANAGE, "erp"),

	A_ACCOUNT_MANAGE("160000", "账户管理", true, null, "erp"),
	A_INNER_ACCOUNT_ADD("160101", "添加内部账户", false, A_ACCOUNT_MANAGE, "erp"),
	A_SHOW_ACCOUNT_QUERY("160201", "账户查询", false, A_ACCOUNT_MANAGE, "erp"),

	A_COUNTER_MANAGE("170000", "对账管理", true, null, "erp"),
	A_COUNTER_FILE_IMPORT("170101", "对账文件导入", false, A_COUNTER_MANAGE, "erp"),
	A_COUNTER_FILE_SHOW("170201", "文件对账", false, A_COUNTER_MANAGE, "erp"),
	A_OPEN_BANK_RECOVER("170301", "银行意外数据恢复", false, A_COUNTER_MANAGE, "erp"),

	A_FINANCIAL_MANAGE("180000", "账务管理", true, null, "erp"),
	A_PAYONLINE_ADMIN_SEARCH("180101", "支付流水查看", false, A_FINANCIAL_MANAGE, "erp"),
	A_TRANS_LOG_CANCEL_SEARCH("180201", "账务冲正", false, A_FINANCIAL_MANAGE, "erp"),
	A_ADD_ACC_TRANS("180301", "账务补账", false, A_FINANCIAL_MANAGE, "erp"),
	A_MANAGER_TRANS_APP("180401", "账务补账申请管理", false, A_FINANCIAL_MANAGE, "erp"),
	A_MANAGER_TRANS_LOG_APP("180501", "账务冲正申请管理", false, A_FINANCIAL_MANAGE, "erp"),

	A_ORDER_MANAGE("200000", "交易管理", true, null, "erp"),
	A_ORDER_MODIFY_USER("200101", "订单管理修改权限", false, A_ORDER_MANAGE, "erp"),
	A_ORDER_REF_CHA_USER("200102", "订单管理退换货权限", false, A_ORDER_MANAGE, "erp"),
	A_ORDER_ASSORT_USER("200103", "订单管理配货权限", false, A_ORDER_MANAGE, "erp"),
	A_ORDER_VIEW_USER("200104", "订单管理查看权限", false, A_ORDER_MANAGE, "erp"),
	A_ORDER_GOODS_EXCEL_USER("200105", "ERP订单商品Excel导出", false, A_ORDER_MANAGE, "erp"),
	A_ORDER_DEPFIRST_MODIFY("200106", "订单一级仓库修改权限", false, A_ORDER_MANAGE, "erp"),
	A_ORDER_ONRESUME_USER("200107", "订单管理恢复权限", false, A_ORDER_MANAGE, "erp"),
	A_ORDER_RECEIVE_MODIFY("200108", "订单收货人信息修改权限", false, A_ORDER_MANAGE, "erp"),
	A_REF_MODIFY_USER("200201", "退货/退款管理权限", false, A_ORDER_MANAGE, "erp"),
	A_REF_FINANCE_USER("200202", "退货/退款管理权限(财务)", false, A_ORDER_MANAGE, "erp"),
	A_REF_PASS_USER("200203", "退换货申请(验收通过)权限", false, A_ORDER_MANAGE, "erp"),
	A_REF_NO_PASS_USER("200204", "退换货申请(验收不通过)权限", false, A_ORDER_MANAGE, "erp"),
	A_REF_GOODS_VIEW_USER("200301", "退货产品查询权限", false, A_ORDER_MANAGE, "erp"),
	A_WHOLESALE_APPLY_VIEW_USER("200401", "批发申请单查询权限", false, A_ORDER_MANAGE, "erp"),
	A_WHOLESALE_MANAGE_USER("200501", "批发订单管理权限", false, A_ORDER_MANAGE, "erp"),
	A_STOCKOUT_TRADE_CLOSE_USER("200601", "缺货订单强制关闭权限", false, A_ORDER_MANAGE, "erp"),
	A_STOCKOUT_TRADE_MANAGE_USER("200701", "缺货订单管理权限", false, A_ORDER_MANAGE, "erp"),

	A_SHOP_MANAGE("300000", "网站管理", true, null, "erp"),
	A_AFFICHE_ADD_USER("300101", "公告管理新增权限", false, A_SHOP_MANAGE, "erp"),
	A_AFFICHE_DELETE_USER("300102", "公告管理删除权限", false, A_SHOP_MANAGE, "erp"),
	A_AFFICHE_MODIFY_USER("300103", "公告管理修改权限", false, A_SHOP_MANAGE, "erp"),
	A_AFFICHE_VIEW_USER("300104", "公告管理查看权限", false, A_SHOP_MANAGE, "erp"),
	A_AD_ADD_USER("300201", "广告管理新增权限", false, A_SHOP_MANAGE, "erp"),
	A_AD_DELETE_USER("300202", "广告管理删除权限", false, A_SHOP_MANAGE, "erp"),
	A_AD_MODIFY_USER("300203", "广告管理修改权限", false, A_SHOP_MANAGE, "erp"),
	A_AD_VIEW_USER("300204", "广告管理查看权限", false, A_SHOP_MANAGE, "erp"),
	A_FL_ADD_USER("300301", "友情链接管理新增权限", false, A_SHOP_MANAGE, "erp"),
	A_FL_DELETE_USER("300302", "友情链接管理删除权限", false, A_SHOP_MANAGE, "erp"),
	A_FL_MODIFY_USER("300303", "友情链接管理修改权限", false, A_SHOP_MANAGE, "erp"),
	A_FL_VIEW_USER("300304", "友情链接管理查看权限", false, A_SHOP_MANAGE, "erp"),
	A_KW_ADD_USER("300401", "热门关键字新增权限", false, A_SHOP_MANAGE, "erp"),
	A_KW_DELETE_USER("300402", "热门关键字删除权限", false, A_SHOP_MANAGE, "erp"),
	A_KW_MODIFY_USER("300403", "热门关键字修改权限", false, A_SHOP_MANAGE, "erp"),
	A_KW_VIEW_USER("300404", "热门关键字查看权限", false, A_SHOP_MANAGE, "erp"),
	A_CAB_ADD_USER("300501", "橱窗推荐位管理新增权限", false, A_SHOP_MANAGE, "erp"),
	A_CAB_DELETE_USER("300502", "橱窗推荐位管理删除权限", false, A_SHOP_MANAGE, "erp"),
	A_CAB_MODIFY_USER("300503", "橱窗推荐位管理修改权限", false, A_SHOP_MANAGE, "erp"),
	A_CAB_VIEW_USER("300504", "橱窗推荐位管理查看权限", false, A_SHOP_MANAGE, "erp"),
	A_BRAND_ADD_USER("300601", "品牌管理新增权限", false, A_SHOP_MANAGE, "erp"),
	A_BRAND_DELETE_USER("300602", "品牌管理删除权限", false, A_SHOP_MANAGE, "erp"),
	A_BRAND_MODIFY_USER("300603", "品牌管理修改权限", false, A_SHOP_MANAGE, "erp"),
	A_BRAND_VIEW_USER("300604", "品牌管理查看权限", false, A_SHOP_MANAGE, "erp"),
	A_AR_ADD_USER("300701", "资讯管理新增权限", false, A_SHOP_MANAGE, "erp"),
	A_AR_DELETE_USER("300702", "资讯管理删除权限", false, A_SHOP_MANAGE, "erp"),
	A_AR_MODIFY_USER("300703", "资讯管理修改权限", false, A_SHOP_MANAGE, "erp"),
	A_AR_VIEW_USER("300704", "资讯管理查看权限", false, A_SHOP_MANAGE, "erp"),
	A_SINFO_MODIFY_USER("300801", "店铺信息设置权限", false, A_SHOP_MANAGE, "erp"),
	A_NAV_ADD_USER("300901", "导航管理新增权限", false, A_SHOP_MANAGE, "erp"),
	A_NAV_DELETE_USER("300902", "导航管理删除权限", false, A_SHOP_MANAGE, "erp"),
	A_NAV_MODIFY_USER("300903", "导航管理修改权限", false, A_SHOP_MANAGE, "erp"),
	A_NAV_VIEW_USER("300904", "导航管理查看权限", false, A_SHOP_MANAGE, "erp"),
	A_BARDL_MODIFY_USER("301001", "榜单管理修改权限", false, A_SHOP_MANAGE, "erp"),
	A_ACTIVITY_ADD_USER("301101", "专场管理新增权限", false, A_SHOP_MANAGE, "erp"),
	A_ACTIVITY_MODIFY_USER("301102", "专场管理修改权限", false, A_SHOP_MANAGE, "erp"),
	A_ACTIVITY_VIEW_USER("301103", "专场管理查看权限", false, A_SHOP_MANAGE, "erp"),
	A_WEBMSG_MANAGE_USER("301201", "站内信管理权限", false, A_SHOP_MANAGE, "erp"),

	A_AFTERSALES_MANAGE("400000", "售后管理", true, null, "erp"),
	A_REVIEW_REVERT_TO_USER("400101", "评论管理回复权限", false, A_AFTERSALES_MANAGE, "erp"),
	A_REVIEW_VIEW_USER("400102", "评论管理查看权限", false, A_AFTERSALES_MANAGE, "erp"),
	A_FB_REVERT_TO_USER("400201", "留言管理回复权限", false, A_AFTERSALES_MANAGE, "erp"),
	A_FB_VIEW_USER("400202", "留言管理查看权限", false, A_AFTERSALES_MANAGE, "erp"),

	A_STATISTICS_MANAGE("500000", "统计报表管理", true, null, "erp"),
	A_OANALYSIS_VIEW_USER("500101", "订单统计查询权限", false, A_STATISTICS_MANAGE, "erp"),
	A_SANALYSIS_VIEW_USER("500201", "销售统计查询权限", false, A_STATISTICS_MANAGE, "erp"),
	A_SANALYSIS_DETAIL_USER("500301", "销售明细查询权限", false, A_STATISTICS_MANAGE, "erp"),
	A_HOTGOODS_VIEW_USER("500401", "热销商品统计权限", false, A_STATISTICS_MANAGE, "erp"),
	A_SLOWGOODS_VIEW_USER("500501", "滞销商品统计权限", false, A_STATISTICS_MANAGE, "erp"),
	A_CATSUM_VIEW_USER("500601", "类目销售汇总权限", false, A_STATISTICS_MANAGE, "erp"),
	A_STORAGECOST_VIEW_USER("500701", "类目库存成本汇总", false, A_STATISTICS_MANAGE, "erp"),
	A_INSTORAGE_VIEW_USER("500801", "在库产品统计", false, A_STATISTICS_MANAGE, "erp"),
	A_IN_OUT_STAT_REPORT_USER("500901", "当月进出货统计报表", false, A_STATISTICS_MANAGE, "erp"),
	A_CANALYSIS_VIEW_USER("501001", "一级目录销售金额统计查询权限", false, A_STATISTICS_MANAGE, "erp"),
	A_CATAORDANALYSIS_VIEW_USER("501101", "类别订单统计查询权限", false, A_STATISTICS_MANAGE, "erp"),

	A_USER_MANAGE("600000", "用户管理", true, null, "erp"),
	A_POINTS_VIEW_USER("600101", "会员积分管理查看权限", false, A_USER_MANAGE, "erp"),
	A_REG_USER_MODIFY_USER("600201", "会员管理修改权限", false, A_USER_MANAGE, "erp"),
	A_REG_USER_VIEW_USER("600202", "会员管理查看权限", false, A_USER_MANAGE, "erp"),
	A_ADMINISTRATORS_ADD_USER("600301", "管理员帐户管理新增权限", false, A_USER_MANAGE, "erp"),
	A_ADMINISTRATORS_DELETE_USER("600302", "管理员帐户管理删除权限", false, A_USER_MANAGE, "erp"),
	A_ADMINISTRATORS_MODIFY_USER("600303", "管理员帐户管理修改权限", false, A_USER_MANAGE, "erp"),
	A_ADMINISTRATORS_VIEW_USER("600304", "管理员帐户管理查看权限", false, A_USER_MANAGE, "erp"),
	A_BACK_ORDER_USER("600305", "管理员后台下订单权限", false, A_USER_MANAGE, "erp"),
	A_ROLE_ADD_USER("600601", "系统角色新增权限", false, A_USER_MANAGE, "erp"),
	A_ROLE_MODIFY_USER("600603", "系统角色修改权限", false, A_USER_MANAGE, "erp"),
	A_ROLE_VIEW_USER("600604", "系统角色查询权限", false, A_USER_MANAGE, "erp"),
	A_LOG_VIEW_USER("600701", "系统日志查询权限", false, A_USER_MANAGE, "erp"),
	A_LOG_VIEW_DAIXIAODENGLUCHAKAN("600701", "代销客户登录查看", false, A_USER_MANAGE, "erp"),

	A_INDEX_MANAGE("700000", "索引管理", true, null, "erp"),
	A_INDEX_BUILD_USER("700101", "手动BUILD索引权限", false, A_INDEX_MANAGE, "erp"),

	A_PURCHASE_MANAGE("800000", "采购管理", true, null, "erp"),
	A_STOCK_ADD_USER("800101", "采购订单管理新增(复制)权限", false, A_PURCHASE_MANAGE, "erp"),
	A_STOCK_MODIFY_USER("800102", "采购订单管理修改权限", false, A_PURCHASE_MANAGE, "erp"),
	A_STOCK_CHECK_USER("800103", "采购订单管理验收权限", false, A_PURCHASE_MANAGE, "erp"),
	A_STOCK_EXPORT_USER("800104", "采购订单管理导出条形码权限", false, A_PURCHASE_MANAGE, "erp"),
	A_STOCK_VIEW_USER("800105", "采购订单管理查看权限", false, A_PURCHASE_MANAGE, "erp"),
	A_STOCK_REF_TRADE_USER("800106", "采购订单管理单据/拒收退货权限", false, A_PURCHASE_MANAGE, "erp"),
	A_STOCK_DETAILSEARCH_USER("800201", "采购产品明细查询权限", false, A_PURCHASE_MANAGE, "erp"),
	A_STOCK_GATHERSEARCH_USER("800301", "采购产品汇总查询权限", false, A_PURCHASE_MANAGE, "erp"),
	A_REFUND_ADD_USER("800401", "退货订单管理新增权限", false, A_PURCHASE_MANAGE, "erp"),
	A_REFUND_VIEW_USER("800402", "退货订单管理查看权限", false, A_PURCHASE_MANAGE, "erp"),
	A_REFUND_DETAIL_VIEW_USER("800501", "退货产品明细查询权限", false, A_PURCHASE_MANAGE, "erp"),
	A_REFUND_GATHER_VIEW_USER("800601", "退货产品汇总查询权限", false, A_PURCHASE_MANAGE, "erp"),
	A_ORDER_STORAGE_QUERY("800602", "订单库存查询", false, A_PURCHASE_MANAGE, "erp"),
	A_SUPPLIER_SHOPPING_VIEW_USER("800701", "供应商供货查询权限", false, A_PURCHASE_MANAGE, "erp"),
	
	A_HUAIXUAN_MANAGE("1210000", "尚上管理", true, null, "erp"),
	A_HUAIXUAN_STORE("1210001", "库存统计", false, A_HUAIXUAN_MANAGE, "erp"),

	A_SUPPLIER_MANAGE("900000", "供应商管理", true, null, "erp"),
	A_SUPPLIER_ADD_USER("900101", "供应商管理新增权限", false, A_SUPPLIER_MANAGE, "erp"),
	A_SUPPLIER_DELETE_USER("900102", "供应商管理激活失效权限", false, A_SUPPLIER_MANAGE, "erp"),
	A_SUPPLIER_MODIFY_USER("900103", "供应商管理修改权限", false, A_SUPPLIER_MANAGE, "erp"),
	A_SUPPLIER_VIEW_USER("900104", "供应商管理查看权限", false, A_SUPPLIER_MANAGE, "erp"),

	A_CRMUSER_MANAGE("99500000", "CRM用户管理", true, null, "crm"),
	A_CRM_REG_USER_SEARCH_USER("99500101", "CRM用户管理查询权限", false, A_CRMUSER_MANAGE, "crm"),
	A_CRM_USER_MODIFY_USER("99500102", "CRM用户管理编辑权限(冻结)", false, A_CRMUSER_MANAGE, "crm"),
	A_CRM_USER_SCRAP_REASSERT_USER("99500103", "CRM用户管理废弃维护权限", false, A_CRMUSER_MANAGE, "crm"),
	A_CRM_USER_MODIFY_QUOTA_USER("99500104", "CRM用户管理修改限额权限", false, A_CRMUSER_MANAGE, "crm"),
	A_CRM_USER_MODIFY_PERIOD_USER("99500105", "CRM用户管理周期结算权限", false, A_CRMUSER_MANAGE, "crm"),
	A_CRM_USER_MODIFY_AUDIT_USER("99500106", "CRM用户管理审核权限", false, A_CRMUSER_MANAGE, "crm"),
	A_CRM_USER_MODIFY_EXPAND_USER("99500107", "CRM用户管理修改拓展人员权限", false, A_CRMUSER_MANAGE, "crm"),
	A_CRM_USER_MODIFY_VIP_USER("99500108", "CRM用户管理升级为VIP权限", false, A_CRMUSER_MANAGE, "crm"),
	A_CRM_CONNECT_RECORD_SHOW("99500109", "CRM沟通记录添加与查看权限", false, A_CRMUSER_MANAGE, "crm"),
	A_CRM_CONNECT_RECORD_EDIT("99500110", "CRM沟通记录编辑权限", false, A_CRMUSER_MANAGE, "crm"),
	A_CRM_USER_MODIFY_ALL_RECORD_USER("99500111", "CRM用户管理修改沟通记录预约记录权限", false, A_CRMUSER_MANAGE, "crm"),
	A_CRM_NEW_USER_DISPLAY("99500112", "CRM用户管理新注册会员查看权限", false, A_CRMUSER_MANAGE, "crm"),
	A_CRM_SCRAP_USER_DISPLAY("99500113", "CRM用户管理废弃会员查看权限", false, A_CRMUSER_MANAGE, "crm"),
	A_CRM_SEE_ALL_USER("99500114", "CRM查看全部客户权限", false, A_CRMUSER_MANAGE, "crm"),
	A_CRM_USER_MODIFY_PASSWD("99500115", "CRM登录密码初始化", false, A_CRMUSER_MANAGE, "crm"),
	A_CRM_USER_MODIFY_PAY_PASSWD("99500116", "CRM支付密码初始化", false, A_CRMUSER_MANAGE, "crm"),
	A_CRM_INVOICEMODIFY_USER("99500117", "CRM会员索要发票修改权限", false, A_CRMUSER_MANAGE, "crm"),
	A_CRM_IMPORT_ORDER("99500118", "CRM会员导入订单权限", false, A_CRMUSER_MANAGE, "crm"),

	A_CRMTRADE_MANAGE("99520000", "CRM订单管理", true, null, "crm"),
	A_CRM_SHOW_L("99520101", "CRM订单管理查看权限", false, A_CRMTRADE_MANAGE, "crm"),
	A_CRM_DETAIL("99520102", "CRM订单管理详细信息查看权限", false, A_CRMTRADE_MANAGE, "crm"),
	A_CRM_EXPORT_ORDER("99520103", "CRM订单管理Excel导出权限", false, A_CRMTRADE_MANAGE, "crm"),
	A_CRM_EXPORT_ORDER_TWO("99520104", "CRM订单管理订单商品Excel权限", false, A_CRMTRADE_MANAGE, "crm"),
	A_CRM_MESSAGE_CONSERVE("99520105", "CRM订单管理保存留言权限", false, A_CRMTRADE_MANAGE, "crm"),

	A_CRMTRADESTATS_MANAGE("99530000", "CRM订单统计管理", true, null, "crm"),
	A_CRM_USER_RANK_SEARCH_USER("99530101", "CRM客户销售排行查询权限", false, A_CRMTRADESTATS_MANAGE, "crm"),
	A_CRM_PRODUCT_RANK_SEARCH_USER("99530102", "CRM产品销售排行查询权限", false, A_CRMTRADESTATS_MANAGE, "crm"),
	A_CRM_SALE_RANK_SEARCH_USER("99530103", "CRM销售人员销售排行查询权限", false, A_CRMTRADESTATS_MANAGE, "crm"),
	
	
	
	A_ORDER_MANAGER("98000000", "订单管理", true, null, "erp"),
	A_ORDER_PAY("98000003", "能否确认付款", false, A_ORDER_MANAGER, "erp"),
	A_ORDER_DELIVERY("98000004", "能否发货", false, A_ORDER_MANAGER, "erp"),
	A_ORDER_CANCEL("98000005", "能否取消订单", false, A_ORDER_MANAGER, "erp"),
	A_ORDER_UPDATE_PRICE("98000006", "修改订单金额", false, A_ORDER_MANAGER, "erp"),
	A_ORDER_YKD("98000007", "预开单订单查看", false, A_ORDER_MANAGER, "erp"),
	A_ORDER_MEILIHUITUISONG("98000008", "魅力惠推送订单", false, A_ORDER_MANAGER, "erp"),
	A_ORDER_FAHUOTONGZHI("98000009", "魅力惠发货通知", false, A_ORDER_MANAGER, "erp"),
	A_ORDER_DINGDANXIANGQING("98000010", "订单详情", false, A_ORDER_MANAGER, "erp"),
	
	A_ORDER_MANAGER_ONLY_BY_ORDERID("98800000", "订单管理-只能通过订单ID查询", true, null, "erp"),
	A_ORDER_MANAGER_ONLY_BY_ORDERID_SUB("98800001", "订单管理-只能通过订单ID查询", false, A_ORDER_MANAGER_ONLY_BY_ORDERID, "erp"),
	
	
	A_PRODUCT_MANAGER("97000000", "产品管理", true, null, "erp"),
	A_PRODUCT_SKU_UPDATE("97000001", "型号,材质,颜色修改", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_COST_UPDATE("97000002", "成本修改", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_PRICE_UPDATE("97000003", "价格修改", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_INSTOCK("97000004", "产品入库", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_COST_SHOW("97000005", "成本显示", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_UPDATE("97000006", "产品更新", false, A_PRODUCT_MANAGER, "erp"),
	A_SHOPPINGCAR_ADD("97000007", "添加本站点产品到购物车", false, A_PRODUCT_MANAGER, "erp"),
	A_SHOPPINGCAR_SHOW("97000008", "显示购物车", false, A_PRODUCT_MANAGER, "erp"),
	A_SHOPPINGCAR_SALE("97000009", "销售出库", false, A_PRODUCT_MANAGER, "erp"),
	A_SHOPPINGCAR_DIAOHUOCHUKU("97000010", "调货出库", false, A_PRODUCT_MANAGER, "erp"),
	A_SHOPPINGCAR_DIAOHUORUKU("97000011", "调货入库", false, A_PRODUCT_MANAGER, "erp"),
	A_SHOPPINGCAR_SUOKU("97000012", "锁库", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_SALE_INSTOCK("97000013", "销售入库", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_CANCEL_YULIU("97000014", "取消预留", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_ACTIVE_PRICE("97000015", "修改活动价", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_SITE_MANAGER("97000016", "站点管理", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_YKD_SCAN("97000017", "预开单扫描", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_YKD_2REALYORDER("97000018", "预开单生成真实订单", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_SUOKU("97000019", "锁库查询", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_YKD("97000020", "产品预开单", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_STOCK_SYN_LOG("97000021", "平台库存更新日志查询", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_SHOPPINGCAR_ADD("97000022", "添加任意站点产品加购物车", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_PINGTAIRIZHI("97000023", "平台库存更新详细日志查询", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_SKUKUCUNBIAO("97000024", "SKU库存对印关系", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_DIAOHUOMINGXI("97000025", "调货明细", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_DIANYUANBUKEDAOCHU("97000026", "可导出", false, A_PRODUCT_MANAGER, "erp"),
	A_PRODUCT_JIESUO("97000027", "解锁", false, A_PRODUCT_MANAGER, "erp"),
	
	
	A_PURCHASE_MANAGER("99000000", "采购管理", true, null, "erp"),
	A_PURCHASE_MANAGER_INSTOCK("99000001", "采购入库", false, A_PURCHASE_MANAGER, "erp"),
	
	
	A_CUSTOMER_MANAGER("88000000", "客户管理", true, null, "erp"),
	A_CUSTOMER_MANAGER_CHOOSEMANAGER("88000001", "选择客户经理", false, A_CUSTOMER_MANAGER, "erp"),
	
	
	
	A_DX_PRODUCT_MANAGER("77000000", "代销客户产品管理", true, null, "erp"),
	A_DX_PRODUCTLIST_MANAGER("77000001", "产品列表", false, A_DX_PRODUCT_MANAGER, "erp");
	

	
	
    /**
     * @param id        权限ID
     * @param name      权限名称
     * @param isMenu    是否菜单
     * @param parent    父权限
     * @param system    系统
     */
	EnumAdminPermission(String id, String name, boolean isMenu,
			EnumAdminPermission parent, String system) {
        this.id = id;
        this.name = name;
        this.isMenu = isMenu;
        this.parent = parent;
        this.system = system;
    }

    private String              id;
    private String            name;
    private Boolean           isMenu;
    private EnumAdminPermission parent;
    private String            system;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Boolean isMenu) {
        this.isMenu = isMenu;
    }

    public EnumAdminPermission getParent() {
        return parent;
    }

    public void setParent(EnumAdminPermission parent) {
        this.parent = parent;
    }

    public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public static Integer getEnumOrdinal(Long id) {
        Integer ordinal = null;
        for (EnumAdminPermission permission : EnumAdminPermission.values()) {
            if (permission.id.equals(id.longValue())) {
                ordinal = permission.ordinal();
                break;
            }
        }
        return ordinal;
    }

    public static List<EnumAdminPermission> toList() {
        List<EnumAdminPermission> list = new ArrayList<EnumAdminPermission>();
        for (EnumAdminPermission enumCrmPermission : EnumAdminPermission.values()) {
            list.add(enumCrmPermission);
        }
        return list;
    }

    private static Map<String,EnumAdminPermission> id2Permission = new HashMap<String, EnumAdminPermission>();

    static {
        for (EnumAdminPermission enumCrmPermission : EnumAdminPermission.values()) {
            id2Permission.put(enumCrmPermission.getId(), enumCrmPermission);
        }
    }

    public static List<EnumAdminPermission> getParents() {
        List<EnumAdminPermission> list = new ArrayList<EnumAdminPermission>();
        for (EnumAdminPermission enumCrmPermission : EnumAdminPermission.values()) {
            if(enumCrmPermission.getParent() == null) {
                list.add(enumCrmPermission);
            }
        }
        return list;
    }
    public static EnumAdminPermission findPermission(String id) {
        return id2Permission.get(id);
    };
    
    
   /* public static void main(String[] args) {
    	
    	 System.out.println(id2Permission.get("1"));
	}*/

}






