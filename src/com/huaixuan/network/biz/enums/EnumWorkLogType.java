package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

public enum EnumWorkLogType {

    // 添加新的type后，对应的toMap()方法请更新
    TRADE(1, "订单"), SHOP(2, "店铺"), GOODS(3, "商品"), USER(4, "会员"), Nav(5, "导航"), FriendLink(6, "友情链接"),
    Cabinet(7, "橱窗"), Notice(8, "公告"), HELP(9, "帮助中心"),Account(10,"账户"), Import(11,"文件导入"),Compare(12,"对帐"), 
    Recover(13,"数据恢复"),Withdraw(14,"提现"), Acctrans(15,"帐务处理") , Inst(16,"渠道相关"),Login(17,"前台登录"),CreditRate(18,"信用评价")
    ,AdminLogin(19,"后台登录"),Agencies(20,"机构"),ImageInfo(21,"图片管理"),Template(22,"模板管理"),IDLEGOOD(23,"闲置商品"),FARE(24,"运费模版");


    private int    code;
    private String name;

    EnumWorkLogType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getKey() {
        return this.code;
    }

    public String getValue() {
        return this.name;
    }

    public static Map<Integer, String> toMap() {
        Map<Integer, String> enumDataMap = new HashMap<Integer, String>();
        // enumDataMap.put(TRADE.getKey(), TRADE.getValue());
        // enumDataMap.put(SHOP.getKey(), SHOP.getValue());
        // enumDataMap.put(GOODS.getKey(), GOODS.getValue());
        // enumDataMap.put(USER.getKey(), USER.getValue());
        // enumDataMap.put(Nav.getKey(), Nav.getValue());
        // enumDataMap.put(FriendLink.getKey(), FriendLink.getValue());
        // enumDataMap.put(Cabinet.getKey(), Cabinet.getValue());
        // enumDataMap.put(Notice.getKey(), Notice.getValue());
        for (EnumWorkLogType type : EnumWorkLogType.values()) {
            enumDataMap.put(type.getKey(), type.getValue());
        }
        return enumDataMap;
    }

    public static void main(String[] args) {
        System.out.println(toMap());
    }
}
