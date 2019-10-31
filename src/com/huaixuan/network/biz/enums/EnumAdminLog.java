/**
 * created since 2009-7-20
 */
package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangwy
 */
public enum EnumAdminLog {

	//struts-admin-admin
	ADDUSER("addUser", "跳转到增加用户页面"),INSERTUSER("insertUser", "增加用户"),EDITUSER("editUser","跳转到修改用户页面"),MODIFYUSER("modifyUser","修改用户"),
	FREEZEUSER("freezeUser","冻结用户"),THAWUSER("thawUser","解冻用户"),USERROLELIST("userRoleList","用户角色列表"),MODIFYUSERROLE("modifyUserRole","修改用户角色"),
	ROLELIST("roleList","角色列表"),ADDROLE("addRole","跳转到增加角色页面"),INSERTROLE("insertRole","增加角色"),EDITROLE("editRole","跳转到修改角色页面"),
	MODIFYROLE("modifyRole","修改角色"),ROLEAUTHORITYLIST("roleAuthorityList","角色权限列表"),MODIFYROLEAUTHORITY("modifyRoleAuthority","修改角色权限"),
	EDITPASSWORD("editPassword","跳转到修改密码页面"),MODIFYUSERPASSWORD("modifyUserPassword","修改密码"),EDITCURUSER("editCurUser","跳转到用户信息修改页面"),
	MODIFYCURUSER("modifyCurUser","用户信息修改"),
	//struts-admin-attribute
	ADDATTR("addattr","跳转到添加属性页面"),ADDA("adda","添加属性"),EDITA("edita","跳转到编辑属性页面"),EA("ea","编辑属性"),RA("ra","删除属性"),BATCHDEL("batchdel","批量删除属性"),
	//struts-admin-category
	MODIFY("modify","修改类目"),REMOVEC("removec","删除类目"),ADDCAT("addcat","跳转到新增类目页面"),ADDC("addc","新增类目"),ADDCA("addca","添加属性关联"),
	//struts-admin-goods
	PUBLISH("init_publish","跳转到发布商品页面"),CREATEGOODSINSTANCEDATA("createGoodsInstanceData","根据商品生成产品数据"),DP("dp","发布商品"),DOAGENT("doagent","设置商品为代销商品"),
	DODISAGENT("dodisagent","取消商品为代销商品"),BATCHGOODS("batchGoods","批量导入商品"),DOBATCHPUBLIS("doBatchPublis","批量上传商品"),EDITG("editg","跳转到修改商品页面"),
	DOEG("doeg","修改商品"),DELISTINGGB("delistinggb","批量下架商品"),DELISTING("delisting","单个下架商品"),DELETEG("deleteg","删除商品"),CUTPRICE("cutPrice","设置商品为特价商品"),
	DOAGENTGOODS("doAgentGoods","批量设置代销商品"),DOCANELAGENTGOODS("doCanelAgentGoods","批量取消代销商品"),DECUTPRICE("deCutPrice","取消商品为特价商品"),CUTPRICEGOODS("cutPriceGoods","批量设置特价商品"),
	CANELCUTPRICEGOODS("canelCutPriceGoods","批量取消特价商品"),DOACTIVITYGOODS("doActivityGoods","设置商品为活动商品"),UPDATEPROMATION("updatePromation","修改套餐"),ADDPRO("addPro","增加套餐"),
	PROMATION("promation","添加满就减规则"),ADDFULLGIVE("addFullGive","添加满就送规则"),UPDATEFULLREDUCE("updateFullReduce","修改满就减规则"),UPDATEFULLGIVE("updateFullGive","修改满就送规则"),
	PORTSALE("portsale","跳转到添加套餐页面"),ADDSALE("addsale","添加套餐"),ADDSALEGOODS("addsalegoods","添加套餐商品"),UPDATEPSP("updatepsp","跳转到套餐修改页面"),UPDATESALE("updatesale","修改套餐"),
	ADDGIFT("addGift","添加买就赠"),UPDATEGB("updategb","跳转到修改买就赠页面"),UPDATEGIFT("updategift","修改买就赠"),CREATEINSTANCE("create_instance","添加产品"),
	UPDATEINSTANCE("update_instance","修改产品"),UPDATEINSTANCELOCATION("update_instance_location","修改产品库位"),UPDATEINSTANCESUPPLIER("update_instance_supplier","修改产品供应商"),
	REMOVEINSTANCESUPPLIER("remove_instance_supplier","删除产品供应商"),ADDINSTANCESUPPLIER("add_instance_supplier","跳转到新增产品供应商页面"),CREATEINSTANCESUPPLIER("create_instance_supplier","新增产品供应商"),
    LISTING("listing","商品上架"),LISTINGGB("listinggb","商品批量上架"),
    //struts-admin-ios
	ADDSUP("addSup","添加供应商"),DISABLE("disable","供应商失效"),ENABLE("enable","供应商激活"),DELSUPPLIERGOODS("delSupplierGoods","删除供应商"),EDITSUP("editSup","更新供应商"),
	ADDSUPGOODS("addSupGoods","添加供应商"),ADDDAMAGED("addDamaged","新增报残单"),ADDDAMAGEDGOODS("addDamagedGoods","新增报残单商品信息"),EDITDAMAGED("editDamaged","编辑报残单"),
	EDITDAMAGEDATTRIBUTE("editDamagedAttribute","编辑报残单属性"),DELETEDAMAGEDGOODS("deleteDamagedGoods","删除报残商品"),ADDDEPOSITORY("addDepository","新增仓库"),
	EDITDEPOSITORY("editDepository","编辑仓库信息"),ADDDEPLOCATION("addDepLocation","新增库位"),EDITDEPLOCATION("editDepLocation","编辑库位信息"),STOCKADD("stockAdd","新增采购订单"),
	ADDSTOCKGOODS("addStockGoods","增加采购订单商品信息"),REFUND("refund","退货操作"),STOCKEDIT("stockEdit","编辑采购订单"),EDITSTOCKATTRIBUTE("editStockAttribute","编辑采购订单状态"),
	STOCKDELETE("stockDelete","删除采购商品"),CHECKSTOK("checkStock","验收修改采购订单"),COPYADD("copyAdd","复制新增采购订单"),ADDSREFUND("addSRefund","新增退货单"),
	ADDOUTDEP("addOutDep","生成出库单"),INDEPOSITORYOPT("inDepositoryOpt","产品库位分配"),GATHEROUTDEPOSITORY("getherOutDepository","出库单成本汇总统计查询"),
	REFUNDSTORAGES("refundStorages","库存退货"),RETURNSTOCK("returnStock","库存归还记录查询"),MOVELOGRETURN("moveLogReturn","外借回调操作页面"),RETURNONESTOCK("returnOneStock","单个记录做归还操作"),
	ADDSTORCHECK("addStorCheck","新增盘存信息"),UPDATESTORAGE("updateStorage","更新盘存信息"),FINISHSTORECHECK("finishStoreCheck","完成盘点"),IMPORTSUPPLIERGOODS("importSupplierGoods","excel导入供应商商品信息"),
	BATCHMODIFYEXPRESSDIST("batchModifyExpressDist","批量修改物流范围"),
    FINANCEOUTDEPOSITORYCONFIRM("financeOutDepositoryConfirm","出库单财务确认"),
    FINANCEINDEPOSITORYCONFIRM("financeInDepositoryConfirm","入库单财务确认"),
    EDITFINANCESTATUS("editFinanceStatus","采购单付款确认"),
	//struts-admin-shop
	ADDN("addn","新增公告"),DELETEN("deleten","批量删除公告"),UPDATEN("updaten","修改公告"),DELETEADMINWEBSITE("deleteAdminWebSite","删除站内信"),UPDATEADMINWEBSITE("updateAdminWebSite","修改站内信"),
	SAVEWEBSITE("saveWebSite","添加站内信"),DELETEAD("deletead","删除广告"),ADDAD("addad","新增广告"),UPDATEAD("updatead","更新广告"),DELETEADP("deleteadp","删除广告位"),
	ADDFL("addfl","新增友情链接"),UPDATEFL("updatefl","更新友情链接"),DELETEKW("deletekw","删除热门关键字"),ADDKW("addkw","新增热门关键字"),UPDATEKW("updatekw","更新热门关键字"),
	DELETESC("deletesc","删除橱窗推荐位"),DELETEBR("deletebr","删除品牌"),ADDBR("addbr","新增品牌"),ADDAR("addar","添加资讯"),DELETEAR("deletear","删除资讯"),TOP("top","置顶资讯"),
	USHOW("ushow","显示资讯"),UPDATEAR("updatear","更新资讯"),ADDNAV("addnav","新增导航"),UPDATENAV("updatenav","修改导航"),SBARD("sbard","修改榜单"),INSERTACTIVITY("insertActivity","新增专场"),
	UPDATEACTITY("updateActity","修改专场信息"),
	//struts-admin-trade
	ADDTRADE("addTrade","后台添加订单"),CHANGE("change","换货操作"),REFUNDGOODS("refundGoods","退货操作"),REFUSEREFUNDGOODS("refuseRefundGoods","拒绝退货"),AGREEREFUNDGOODS("agreeRefundGoods","同意退货"),
	SENDFB("sendfb","发送留言"),CONREFUND("conRefund","查看处理退货申请"),REFCHA("refcha","退款退货申请"),CONGOO("congoo","卖家确认收到退货"),
	//struts-admin-users
	REPLYCOMM("replycomm","编辑用户评论"),REPLYFB("replyfb","编辑留言"),REGUSERS("regusers","注册用户管理"),ANA("ana","新增普通管理员"),EDITAP("editap","管理员密码修改");

    private String code;

    private String name;

    EnumAdminLog(String code,String name){
        this.code=code;
        this.name=name;
    }

    public String getKey() {
        return this.code;
    }
    public String getValue(){
       return this.name;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        enumDataMap.put(ADDUSER.getKey(), ADDUSER.getValue());
        enumDataMap.put(INSERTUSER.getKey(), INSERTUSER.getValue());
        enumDataMap.put(EDITUSER.getKey(), EDITUSER.getValue());
        enumDataMap.put(MODIFYUSER.getKey(), MODIFYUSER.getValue());
        enumDataMap.put(FREEZEUSER.getKey(), FREEZEUSER.getValue());
        enumDataMap.put(THAWUSER.getKey(), THAWUSER.getValue());
        enumDataMap.put(USERROLELIST.getKey(), USERROLELIST.getValue());
        enumDataMap.put(MODIFYUSERROLE.getKey(), MODIFYUSERROLE.getValue());
        enumDataMap.put(ROLELIST.getKey(), ROLELIST.getValue());
        enumDataMap.put(ADDROLE.getKey(), ADDROLE.getValue());
        enumDataMap.put(INSERTROLE.getKey(), INSERTROLE.getValue());
        enumDataMap.put(EDITROLE.getKey(), EDITROLE.getValue());
        enumDataMap.put(MODIFYROLE.getKey(), MODIFYROLE.getValue());
        enumDataMap.put(ROLEAUTHORITYLIST.getKey(), ROLEAUTHORITYLIST.getValue());
        enumDataMap.put(MODIFYROLEAUTHORITY.getKey(), MODIFYROLEAUTHORITY.getValue());
        enumDataMap.put(EDITPASSWORD.getKey(), EDITPASSWORD.getValue());
        enumDataMap.put(MODIFYUSERPASSWORD.getKey(), MODIFYUSERPASSWORD.getValue());
        enumDataMap.put(EDITCURUSER.getKey(), EDITCURUSER.getValue());
        enumDataMap.put(MODIFYCURUSER.getKey(), MODIFYCURUSER.getValue());
        enumDataMap.put(ADDATTR.getKey(), ADDATTR.getValue());
        enumDataMap.put(ADDA.getKey(), ADDA.getValue());
        enumDataMap.put(EDITA.getKey(), EDITA.getValue());
        enumDataMap.put(EA.getKey(), EA.getValue());
        enumDataMap.put(RA.getKey(), RA.getValue());
        enumDataMap.put(BATCHDEL.getKey(), BATCHDEL.getValue());
        enumDataMap.put(MODIFY.getKey(), MODIFY.getValue());
        enumDataMap.put(REMOVEC.getKey(), REMOVEC.getValue());
        enumDataMap.put(ADDCAT.getKey(), ADDCAT.getValue());
        enumDataMap.put(ADDC.getKey(), ADDC.getValue());
        enumDataMap.put(ADDCA.getKey(), ADDCA.getValue());
        enumDataMap.put(PUBLISH.getKey(), PUBLISH.getValue());
        enumDataMap.put(CREATEGOODSINSTANCEDATA.getKey(), CREATEGOODSINSTANCEDATA.getValue());
        enumDataMap.put(DP.getKey(), DP.getValue());
        enumDataMap.put(DOAGENT.getKey(), DOAGENT.getValue());
        enumDataMap.put(DODISAGENT.getKey(), DODISAGENT.getValue());
        enumDataMap.put(BATCHGOODS.getKey(), BATCHGOODS.getValue());
        enumDataMap.put(DOBATCHPUBLIS.getKey(), DOBATCHPUBLIS.getValue());
        enumDataMap.put(EDITG.getKey(), EDITG.getValue());
        enumDataMap.put(DOEG.getKey(), DOEG.getValue());
        enumDataMap.put(DELISTINGGB.getKey(), DELISTINGGB.getValue());
        enumDataMap.put(DELISTING.getKey(), DELISTING.getValue());
        enumDataMap.put(DELETEG.getKey(), DELETEG.getValue());
        enumDataMap.put(CUTPRICE.getKey(), CUTPRICE.getValue());
        enumDataMap.put(DOAGENTGOODS.getKey(), DOAGENTGOODS.getValue());
        enumDataMap.put(DOCANELAGENTGOODS.getKey(), DOCANELAGENTGOODS.getValue());
        enumDataMap.put(DECUTPRICE.getKey(), DECUTPRICE.getValue());
        enumDataMap.put(CUTPRICEGOODS.getKey(), CUTPRICEGOODS.getValue());
        enumDataMap.put(CANELCUTPRICEGOODS.getKey(), CANELCUTPRICEGOODS.getValue());
        enumDataMap.put(DOACTIVITYGOODS.getKey(), DOACTIVITYGOODS.getValue());
        enumDataMap.put(UPDATEPROMATION.getKey(), UPDATEPROMATION.getValue());
        enumDataMap.put(ADDPRO.getKey(), ADDPRO.getValue());
        enumDataMap.put(PROMATION.getKey(), PROMATION.getValue());
        enumDataMap.put(ADDFULLGIVE.getKey(), ADDFULLGIVE.getValue());
        enumDataMap.put(UPDATEFULLREDUCE.getKey(), UPDATEFULLREDUCE.getValue());
        enumDataMap.put(UPDATEFULLGIVE.getKey(), UPDATEFULLGIVE.getValue());
        enumDataMap.put(PORTSALE.getKey(), PORTSALE.getValue());
        enumDataMap.put(ADDSALE.getKey(), ADDSALE.getValue());
        enumDataMap.put(ADDSALEGOODS.getKey(), ADDSALEGOODS.getValue());
        enumDataMap.put(UPDATEPSP.getKey(), UPDATEPSP.getValue());
        enumDataMap.put(UPDATESALE.getKey(), UPDATESALE.getValue());
        enumDataMap.put(ADDGIFT.getKey(), ADDGIFT.getValue());
        enumDataMap.put(UPDATEGB.getKey(), UPDATEGB.getValue());
        enumDataMap.put(UPDATEGIFT.getKey(), UPDATEGIFT.getValue());
        enumDataMap.put(CREATEINSTANCE.getKey(), CREATEINSTANCE.getValue());
        enumDataMap.put(UPDATEINSTANCE.getKey(), UPDATEINSTANCE.getValue());
        enumDataMap.put(UPDATEINSTANCELOCATION.getKey(), UPDATEINSTANCELOCATION.getValue());
        enumDataMap.put(UPDATEINSTANCESUPPLIER.getKey(), UPDATEINSTANCESUPPLIER.getValue());
        enumDataMap.put(REMOVEINSTANCESUPPLIER.getKey(), REMOVEINSTANCESUPPLIER.getValue());
        enumDataMap.put(ADDINSTANCESUPPLIER.getKey(), ADDINSTANCESUPPLIER.getValue());
        enumDataMap.put(CREATEINSTANCESUPPLIER.getKey(), CREATEINSTANCESUPPLIER.getValue());
        enumDataMap.put(ADDSUP.getKey(), ADDSUP.getValue());
        enumDataMap.put(DISABLE.getKey(), DISABLE.getValue());
        enumDataMap.put(ENABLE.getKey(), ENABLE.getValue());
        enumDataMap.put(DELSUPPLIERGOODS.getKey(), DELSUPPLIERGOODS.getValue());
        enumDataMap.put(EDITSUP.getKey(), EDITSUP.getValue());
        enumDataMap.put(ADDSUPGOODS.getKey(), ADDSUPGOODS.getValue());
        enumDataMap.put(ADDDAMAGED.getKey(), ADDDAMAGED.getValue());
        enumDataMap.put(ADDDAMAGEDGOODS.getKey(), ADDDAMAGEDGOODS.getValue());
        enumDataMap.put(EDITDAMAGED.getKey(), EDITDAMAGED.getValue());
        enumDataMap.put(EDITDAMAGEDATTRIBUTE.getKey(), EDITDAMAGEDATTRIBUTE.getValue());
        enumDataMap.put(DELETEDAMAGEDGOODS.getKey(), DELETEDAMAGEDGOODS.getValue());
        enumDataMap.put(ADDDEPOSITORY.getKey(), ADDDEPOSITORY.getValue());
        enumDataMap.put(EDITDEPOSITORY.getKey(), EDITDEPOSITORY.getValue());
        enumDataMap.put(ADDDEPLOCATION.getKey(), ADDDEPLOCATION.getValue());
        enumDataMap.put(EDITDEPLOCATION.getKey(), EDITDEPLOCATION.getValue());
        enumDataMap.put(STOCKADD.getKey(), STOCKADD.getValue());
        enumDataMap.put(ADDSTOCKGOODS.getKey(), ADDSTOCKGOODS.getValue());
        enumDataMap.put(REFUND.getKey(), REFUND.getValue());
        enumDataMap.put(STOCKEDIT.getKey(), STOCKEDIT.getValue());
        enumDataMap.put(EDITSTOCKATTRIBUTE.getKey(), EDITSTOCKATTRIBUTE.getValue());
        enumDataMap.put(STOCKDELETE.getKey(), STOCKDELETE.getValue());
        enumDataMap.put(CHECKSTOK.getKey(), CHECKSTOK.getValue());
        enumDataMap.put(COPYADD.getKey(), COPYADD.getValue());
        enumDataMap.put(ADDSREFUND.getKey(), ADDSREFUND.getValue());
        enumDataMap.put(ADDOUTDEP.getKey(), ADDOUTDEP.getValue());
        enumDataMap.put(INDEPOSITORYOPT.getKey(), INDEPOSITORYOPT.getValue());
        enumDataMap.put(GATHEROUTDEPOSITORY.getKey(), GATHEROUTDEPOSITORY.getValue());
        enumDataMap.put(REFUNDSTORAGES.getKey(), REFUNDSTORAGES.getValue());
        enumDataMap.put(RETURNSTOCK.getKey(), RETURNSTOCK.getValue());
        enumDataMap.put(MOVELOGRETURN.getKey(), MOVELOGRETURN.getValue());
        enumDataMap.put(RETURNONESTOCK.getKey(), RETURNONESTOCK.getValue());
        enumDataMap.put(ADDSTORCHECK.getKey(), ADDSTORCHECK.getValue());
        enumDataMap.put(UPDATESTORAGE.getKey(), UPDATESTORAGE.getValue());
        enumDataMap.put(FINISHSTORECHECK.getKey(), FINISHSTORECHECK.getValue());
        enumDataMap.put(IMPORTSUPPLIERGOODS.getKey(), IMPORTSUPPLIERGOODS.getValue());
        enumDataMap.put(BATCHMODIFYEXPRESSDIST.getKey(), BATCHMODIFYEXPRESSDIST.getValue());
        enumDataMap.put(ADDN.getKey(), ADDN.getValue());
        enumDataMap.put(DELETEN.getKey(), DELETEN.getValue());
        enumDataMap.put(UPDATEN.getKey(), UPDATEN.getValue());
        enumDataMap.put(DELETEADMINWEBSITE.getKey(), DELETEADMINWEBSITE.getValue());
        enumDataMap.put(UPDATEADMINWEBSITE.getKey(), UPDATEADMINWEBSITE.getValue());
        enumDataMap.put(SAVEWEBSITE.getKey(), SAVEWEBSITE.getValue());
        enumDataMap.put(DELETEAD.getKey(), DELETEAD.getValue());
        enumDataMap.put(ADDAD.getKey(), ADDAD.getValue());
        enumDataMap.put(UPDATEAD.getKey(), UPDATEAD.getValue());
        enumDataMap.put(DELETEADP.getKey(), DELETEADP.getValue());
        enumDataMap.put(ADDFL.getKey(), ADDFL.getValue());
        enumDataMap.put(UPDATEFL.getKey(), UPDATEFL.getValue());
        enumDataMap.put(DELETEKW.getKey(), DELETEKW.getValue());
        enumDataMap.put(ADDKW.getKey(), ADDKW.getValue());
        enumDataMap.put(UPDATEKW.getKey(), UPDATEKW.getValue());
        enumDataMap.put(DELETESC.getKey(), DELETESC.getValue());
        enumDataMap.put(DELETEBR.getKey(), DELETEBR.getValue());
        enumDataMap.put(ADDBR.getKey(), ADDBR.getValue());
        enumDataMap.put(ADDAR.getKey(), ADDAR.getValue());
        enumDataMap.put(DELETEAR.getKey(), DELETEAR.getValue());
        enumDataMap.put(TOP.getKey(), TOP.getValue());
        enumDataMap.put(USHOW.getKey(), USHOW.getValue());
        enumDataMap.put(UPDATEAR.getKey(), UPDATEAR.getValue());
        enumDataMap.put(ADDNAV.getKey(), ADDNAV.getValue());
        enumDataMap.put(UPDATENAV.getKey(), UPDATENAV.getValue());
        enumDataMap.put(SBARD.getKey(), SBARD.getValue());
        enumDataMap.put(INSERTACTIVITY.getKey(), INSERTACTIVITY.getValue());
        enumDataMap.put(UPDATEACTITY.getKey(), UPDATEACTITY.getValue());
        enumDataMap.put(ADDTRADE.getKey(), ADDTRADE.getValue());
        enumDataMap.put(CHANGE.getKey(), CHANGE.getValue());
        enumDataMap.put(REFUNDGOODS.getKey(), REFUNDGOODS.getValue());
        enumDataMap.put(REFUSEREFUNDGOODS.getKey(), REFUSEREFUNDGOODS.getValue());
        enumDataMap.put(AGREEREFUNDGOODS.getKey(), AGREEREFUNDGOODS.getValue());
        enumDataMap.put(SENDFB.getKey(), SENDFB.getValue());
        enumDataMap.put(CONREFUND.getKey(), CONREFUND.getValue());
        enumDataMap.put(REFCHA.getKey(), REFCHA.getValue());
        enumDataMap.put(CONGOO.getKey(), CONGOO.getValue());
        enumDataMap.put(REPLYCOMM.getKey(), REPLYCOMM.getValue());
        enumDataMap.put(REPLYFB.getKey(), REPLYFB.getValue());
        enumDataMap.put(REGUSERS.getKey(), REGUSERS.getValue());
        enumDataMap.put(ANA.getKey(), ANA.getValue());
        enumDataMap.put(EDITAP.getKey(), EDITAP.getValue());
        enumDataMap.put(LISTING.getKey(), LISTING.getValue());
        enumDataMap.put(LISTINGGB.getKey(), LISTINGGB.getValue());
        enumDataMap.put(FINANCEOUTDEPOSITORYCONFIRM.getKey(), FINANCEOUTDEPOSITORYCONFIRM.getValue());
        enumDataMap.put(FINANCEINDEPOSITORYCONFIRM.getKey(), FINANCEINDEPOSITORYCONFIRM.getValue());
        enumDataMap.put(EDITFINANCESTATUS.getKey(), EDITFINANCESTATUS.getValue());
        return enumDataMap;
    }
    
    
    private static Map<String,EnumAdminLog> logmap = new HashMap<String, EnumAdminLog>();

    static {
        for (EnumAdminLog enumAdminLog : EnumAdminLog.values()) {
        	logmap.put(enumAdminLog.getKey(), enumAdminLog);
        }
    }

    public static EnumAdminLog findLog(String key) {
        return logmap.get(key);
    };
    
}
