package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 淘宝物流公司信息
 * 使用淘宝API的taobao.logistics.companies.get接口手动获取，如有更新，请及时更新此类。（20110310）
 * http://my.open.taobao.com/apitools/apiTools.htm?catId=7&apiName=taobao.logistics.companies.get
 * @author chenyan 2011/03/10
 */
public enum EnumTaobaoExpress {
    TAOBAO_EXPRESS_POST("POST", "中国邮政平邮"),
    TAOBAO_EXPRESS_EMS("EMS", "EMS"),
    TAOBAO_EXPRESS_YTO("YTO", "圆通速递"),
    TAOBAO_EXPRESS_ZTO("ZTO", "中通速递"),
    TAOBAO_EXPRESS_HZABC("HZABC", "杭州爱彼西"),
    TAOBAO_EXPRESS_ZJS("ZJS", "宅急送"),
    TAOBAO_EXPRESS_YUNDA("YUNDA", "韵达快运"),
    TAOBAO_EXPRESS_TTKDEX("TTKDEX", "天天快递"),
    TAOBAO_EXPRESS_FEDEX("FEDEX", "联邦快递"),
    TAOBAO_EXPRESS_EBON("EBON", "一邦速递"),
    TAOBAO_EXPRESS_STARS("STARS", "星晨急便"),
    TAOBAO_EXPRESS_DBL("DBL", "德邦物流"),
    TAOBAO_EXPRESS_CRE("CRE", "中铁快运"),
    TAOBAO_EXPRESS_HTKY("HTKY", "汇通快运"),
    TAOBAO_EXPRESS_SF("SF", "顺丰速运"),
    TAOBAO_EXPRESS_AIRFEX("AIRFEX", "亚风"),
    TAOBAO_EXPRESS_APEX("APEX", "全一"),
    TAOBAO_EXPRESS_LBEX("LBEX", "龙邦"),
    TAOBAO_EXPRESS_CYEXP("CYEXP", "长宇"),
    TAOBAO_EXPRESS_DTW("DTW", "大田"),
    TAOBAO_EXPRESS_YUD("YUD", "长发"),
    TAOBAO_EXPRESS_ANTO("ANTO", "安得"),
    TAOBAO_EXPRESS_CCES("CCES", "CCES"),
    TAOBAO_EXPRESS_STO("STO", "申通E物流"),
    TAOBAO_EXPRESS_ZY("ZY", "中远"),
    TAOBAO_EXPRESS_DFH("DFH", "东方汇"),
    TAOBAO_EXPRESS_YC("YC", "远长"),
    TAOBAO_EXPRESS_XINB("XINB", "新邦物流"),
    TAOBAO_EXPRESS_SY("SY", "首业"),
    TAOBAO_EXPRESS_XFHONG("XFHONG", "鑫飞鸿快递"),
    TAOBAO_EXPRESS_NEDA("NEDA", "港中能达"),
    TAOBAO_EXPRESS_OTHER("OTHER", "其他"),;

    private String code;

    private String name;

    EnumTaobaoExpress(String code,String name){
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
        enumDataMap.put(TAOBAO_EXPRESS_POST.getKey(), TAOBAO_EXPRESS_POST.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_EMS.getKey(), TAOBAO_EXPRESS_EMS.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_YTO.getKey(), TAOBAO_EXPRESS_YTO.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_ZTO.getKey(), TAOBAO_EXPRESS_ZTO.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_HZABC.getKey(), TAOBAO_EXPRESS_HZABC.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_ZJS.getKey(), TAOBAO_EXPRESS_ZJS.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_YUNDA.getKey(), TAOBAO_EXPRESS_YUNDA.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_TTKDEX.getKey(), TAOBAO_EXPRESS_TTKDEX.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_FEDEX.getKey(), TAOBAO_EXPRESS_FEDEX.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_EBON.getKey(), TAOBAO_EXPRESS_EBON.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_STARS.getKey(), TAOBAO_EXPRESS_STARS.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_DBL.getKey(), TAOBAO_EXPRESS_DBL.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_CRE.getKey(), TAOBAO_EXPRESS_CRE.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_HTKY.getKey(), TAOBAO_EXPRESS_HTKY.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_SF.getKey(), TAOBAO_EXPRESS_SF.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_AIRFEX.getKey(), TAOBAO_EXPRESS_AIRFEX.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_APEX.getKey(), TAOBAO_EXPRESS_APEX.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_LBEX.getKey(), TAOBAO_EXPRESS_LBEX.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_CYEXP.getKey(), TAOBAO_EXPRESS_CYEXP.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_DTW.getKey(), TAOBAO_EXPRESS_DTW.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_YUD.getKey(), TAOBAO_EXPRESS_YUD.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_ANTO.getKey(), TAOBAO_EXPRESS_ANTO.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_CCES.getKey(), TAOBAO_EXPRESS_CCES.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_STO.getKey(), TAOBAO_EXPRESS_STO.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_ZY.getKey(), TAOBAO_EXPRESS_ZY.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_DFH.getKey(), TAOBAO_EXPRESS_DFH.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_YC.getKey(), TAOBAO_EXPRESS_YC.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_XINB.getKey(), TAOBAO_EXPRESS_XINB.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_SY.getKey(), TAOBAO_EXPRESS_SY.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_XFHONG.getKey(), TAOBAO_EXPRESS_XFHONG.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_NEDA.getKey(), TAOBAO_EXPRESS_NEDA.getValue());
        enumDataMap.put(TAOBAO_EXPRESS_OTHER.getKey(), TAOBAO_EXPRESS_OTHER.getValue());
        
        return enumDataMap;
    }
}
