package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 权限模块
 *
 * @author zhengwei
 */
public enum EnumAuthorityModel {
    COMMON(0,"系统通用权限"),GOODS_MANAGE(1, "商品管理"), TRADE_MANAGE(2, "交易管理"), SHOP_MANAGE(3, "店铺管理"), AFTER_SALE_MANAGE(4, "售后管理"),
    STAT_REPORT_FORMS_MANAGE(5, "统计报表管理"), USER_MANAGE(6, "用户管理"), INDEX_MANAGE(7, "索引管理"), STOCK_MANAGE(8, "采购管理"),
    SUPPLIER_MANAGE(9, "供应商管理"), DEPOSITORY_MANAGE(11, "仓库管理"), LOGISTICS_MANAGE(12, "物流管理"), AGENT_MANAGE(13, "代销管理"),
    FINANCE_MANAGE(14, "财务管理"), RETURN_POINT(15, "返点管理"), ACCOUNT_MANAGE(16, "账户管理"), ACCOUNT_FILE_MANAGE(17, "对账管理"), 
    TRANS_MANAGE(18, "账务管理"),CRM_USER_MANAGE(50, "CRM用户管理"),CRM_CUR_USER(51,"CRM个人信息管理"),CRM_TRADE_MANAGE(52,"CRM订单管理"),
    CRM_TRADE_RANK(53,"CRM订单统计"),CRM_COMMON(99, "CRM系统通用权限");

    private EnumAuthorityModel(int modelId, String desc) {
        this.modelId = modelId;
        this.desc = desc;
    }

    private int                                     modelId;

    private String                                  desc;

    private static Map<Integer, EnumAuthorityModel> map = new HashMap<Integer, EnumAuthorityModel>();

    static {
        for (EnumAuthorityModel model : EnumAuthorityModel.values()) {
            map.put(Integer.valueOf(model.getModelId()), model);
        }
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static EnumAuthorityModel getModel(int modelId) {
        return map.get(Integer.valueOf(modelId));
    }

    public static String getModelDesc(int modelId) {
        EnumAuthorityModel model = getModel(modelId);
        return model == null ? null : model.getDesc();
    }

}
