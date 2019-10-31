package com.huaixuan.network.common.util.remote;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.common.util.MoneyUtil;
import com.huaixuan.network.common.util.Result;
import com.hundsun.network.melody.common.util.StringUtil;


public class Billing_Deal_DetailsUtil {

    protected static Log       log                               = LogFactory
                                                                     .getLog(Billing_Deal_DetailsUtil.class);
    // 订单已付款状态
    public static final String DS_WAIT_SELLER_DELIVERY           = "DS_WAIT_SELLER_DELIVERY";

    public static final String DATE_FOMAT_STR                    = "yyyy-MM-dd HH:mm:ss";

    // 卖家查找订单详情接口地址
    public static final String DEAL_DETAIL_URL                   = "/deal/getDealDetail.xhtml";
    // 卖家查找订单列表 接口地址
    public static final String DEAL_LIST_URL                     = "/deal/sellerSearchDealList.xhtml";
    // 卖家标记订单配货中
    public static final String DEAL_PREPARING_URL                = "/deal/sellerSignDealPreparing.xhtml";
    // 卖家标记订单发货
    public static final String DEAL_CONSIGN_URL                  = "/deal/sellerConsignDealItem.xhtml";
    // 卖家延长订单收货时间
    public static final String DELAY_CONSIGNMENT_TIME_URL        = "/deal/sellerDelayConsignmentTime.xhtml";
    // 卖家添加或者修改订单备注
    public static final String MODIFY_SELLER_NOTE_URL            = "/deal/modifySellerNote.xhtml";
    // 卖家取消订单
    public static final String SELLER_CANCEL_DEAL_URL            = "/deal/sellerCancelDeal.xhtml";
    // 卖家标记订单缺货
    public static final String SELLER_SIGN_DEAL_OUT_OF_STOCK_URL = "/deal/sellerSignDealOutOfStock.xhtml";

    /**
     * 卖家查找订单详情接口地址
     * 
     * @param sellerUin  卖家QQ号
     * @param dealCode   订单编码
     * @param listItem   是否列出商品:  0（默认）：不列出订单相关商品
     * @param sign       所有非文件字段name和value的校验值，防止重要数据被中途篡改
     * @return
     */
    public static String CreateDealDetailUrl(String url, String sellerUin, String dealCode,
                                             int listItem) {

        TreeMap<String, String> signParams = new TreeMap<String, String>();
        Contants.setPublicParams(signParams);
        signParams.put("sellerUin", Contants.UIN.toString());
        signParams.put("dealCode", dealCode);
        if (StringUtil.isBlank(String.valueOf(listItem))) {
            listItem = 0;
        }
        signParams.put("listItem", String.valueOf(listItem));

        return Contants.createNewUrl(url, signParams);
    }

    /**
     * 卖家查找订单列表接口地址
     * 
     * @param pureData
     *            number 0,1 默认值为0。
     * @param timeType
     *            string 空字符串（默认） 时间类型。 空字符串（默认）:表示不使用时间筛选项
     *            CREATE:表示timeBegin和timeEnd是下单时间
     *            UPDATE:表示timeBegin和timeEnd是订单最后更新时间
     *            如果timeType的类型为CREATE、UPDATE时，timeBegin和timeEnd字段必须赋值
     * @param timeBegin
     *            time 格式: yyyy-MM-dd HH:mm:ss 起始时间
     *            如果字段timeType的类型为CREATE、UPDATE时，该字段必须赋值 
     * @param timeEnd 
     *            time 格式:yyyy-MM-dd HH:mm:ss 结束时间
     *            如果字段timeType的类型为CREATE、UPDATE时，该字段必须赋值
     * @param dealState
     *            string 默认为空字符串 订单状态:dealState
     * @param itemCode
     *            string 商品编码 这里需要主要不要填写订单中的商品快照编码，是要填写商品编码
     *            4A42403200000001004A39AB068030E3 订单中的商品快照编码
     *            4A42403200000000004A39AB068030E3 商品编码
     *            商品快照编码：当买家下单后，如果商家修改了商品信息，那么订单中查看的商品编码，
     *            是快照编码，会在商品编码的中间的某一位累加一,中间的00000001中间这8位是快照编码序列号，不断累加的
     * @param itemNameKey
     *            string 商品名称关键字 目标订单的商品标题，表示查找和该商品标题相关的订单，
     *            例如如果设置为“森林”，那么商品标题中带有“森林”词的订单列表就会被查找出来
     * @param listItem
     *            number 0（默认） 是否列出商品 0（默认）：不列出订单相关商品 非0：列出订单相关商品（速度比较慢）
     * @param dealCode
     *            string 订单编码 可查询单个订单的信息
     * @param orderField
     *            string 排序基准列 UPDATE_TIME（默认）：按最后更新时间排序 CREATE_TIME：按下单时间排序
     *            暂时不支持，指定timeType时同时用对应时间字段作为排序字段；不指定timeType时没有排序。
     * @param orderDesc
     *            number 非0（默认） 是否逆序排列 非0（默认）：逆序排列 0：顺序排列
     * @param pageIndex
     *            number 1（默认） 页数索引，从1开始
     * @param pageSize
     *            number 默认为10 页大小 每页返回的订单记录数，不要超过20。默认为10
     * @return
     */
    public static String CreateDealListUrl(TreeMap<String, String> signParams, String pageIndex,
                                           String pageSize, String timeType, String timeBegin,
                                           String timeEnd, String dealState, String itemCode,
                                           String itemNameKey, String listItem, String dealCode,
                                           String orderField, String orderDesc) {

        //      TreeMap<String, String> signParams = new TreeMap<String, String>();
        Contants.setPublicParams(signParams);
        //      signParams.put("sellerUin", Contants.UIN.toString());
        if (StringUtil.isNotBlank(timeType)) {
            signParams.put("timeType", timeType);
        }
        if (StringUtil.isNotBlank(timeBegin)) {
            signParams.put("timeBegin", timeBegin);
        }
        if (StringUtil.isNotBlank(timeEnd)) {
            signParams.put("timeEnd", timeEnd);
        }
        if (StringUtil.isNotBlank(dealState)) {
            signParams.put("dealState", dealState);
        }
        if (StringUtil.isNotBlank(itemCode)) {
            signParams.put("itemCode", itemCode);
        }
        if (StringUtil.isNotBlank(itemNameKey)) {
            signParams.put("itemNameKey", itemNameKey);
        }
        if (StringUtil.isNotBlank(listItem)) {
            signParams.put("listItem", listItem);
        }
        if (StringUtil.isNotBlank(orderField)) {
            signParams.put("orderField", orderField);
        }
        if (StringUtil.isNotBlank(orderDesc)) {
            signParams.put("orderDesc", orderDesc);
        }
        if (StringUtil.isNotBlank(dealCode)) {
            signParams.put("dealCode", dealCode);
        }
        if (StringUtil.isNotBlank(pageIndex)) {
            signParams.put("pageIndex", pageIndex);
        }
        if (StringUtil.isNotBlank(pageSize)) {
            signParams.put("pageSize", pageSize);
        }

        return Contants.createNewUrl(DEAL_LIST_URL, signParams);
    }

    /**
     * 卖家标记订单配货中
     * 
     * @param sellerUin  卖家QQ号
     * @param dealCode string  订单编码
     * @return
     */
    public static String CreateDealPreparingUrl(TreeMap<String, String> signParams, String dealCode) {
        Contants.setPublicParams(signParams);
        signParams.put("dealCode", dealCode);
        return Contants.createNewUrl(DEAL_PREPARING_URL, signParams);
    }

    /**
     * 卖家标记订单发货
     * 
     * @param dealCode
     *            （必填） string 订单编码
     * @param logisticsName
     *            （必填） string 256个字节 物流公司名称 自定义的发货公司名称
     * @param logisticsDesc
     *            string 0-100字节 卖家填写的发货说明
     * @param logisticsCode
     *            （必填） string 20字节 发货单号
     * @param arriveDays
     *            （必填） number [3,5,7,10] 预计几天后到货
     * @return
     */
    public static String CreateDealConsignUrl(TreeMap<String, String> signParams, String dealCode,
                                              String logisticsName, String logisticsDesc,
                                              String logisticsCode, int arriveDays) {

        Contants.setPublicParams(signParams);
        signParams.put("dealCode", dealCode);
        signParams.put("logisticsName", logisticsName);
        if (StringUtil.isNotBlank(logisticsDesc)) {
            signParams.put("logisticsDesc", logisticsDesc);
        }
        signParams.put("logisticsCode", logisticsCode);
        signParams.put("arriveDays", String.valueOf(arriveDays));

        return Contants.createNewUrl(DEAL_CONSIGN_URL, signParams);
    }

    /**
     * 卖家延长订单收货时间
     * 
     * @param sellerUin  卖家QQ号
     * @param dealCode string  订单编码
     * @param days number 3，5，7，10 延长收货的天数 
     * @return 
     */
    public static String CreateDelayConsignmentTimeUrl(String url, String dealCode, int days) {

        TreeMap<String, String> signParams = new TreeMap<String, String>();
        Contants.setPublicParams(signParams);
        signParams.put("sellerUin", Contants.UIN.toString());
        signParams.put("dealCode", dealCode);
        signParams.put("days", String.valueOf(days));

        return Contants.createNewUrl(url, signParams);
    }

    /**
     * 卖家添加或者修改订单备注
     * 
     * @param sellerUin  卖家QQ号
     * @param dealCode string  订单编码
     * @param dealNote string  订单备注内容 不超过254个字符  
     * @return 
     */
    public static String CreateModifySellerNoteUrl(String url, String dealCode, String dealNote) {

        TreeMap<String, String> signParams = new TreeMap<String, String>();
        Contants.setPublicParams(signParams);
        signParams.put("sellerUin", Contants.UIN.toString());
        signParams.put("dealCode", dealCode);
        signParams.put("dealNote", dealNote);

        return Contants.createNewUrl(url, signParams);
    }

    /**
     * 卖家添加或者修改订单备注
     * 
     * @param sellerUin
     *            卖家QQ号
     * @param dealCode
     *            string 订单编码
     * @param closeReason
     *            number 填写的取消说明 0：无法联系上买家 1：买家误拍或重拍了 2：买家无诚意完成交易 3：已通过银行线下汇款
     *            4：已通过同城见面交易 5：已通过货到付款交易 6：已通过网上银行直接汇款 7：已经缺货无法交易
     * @return
     */
    public static String CreateSellerCancelDealUrl(String url, String dealCode, int closeReason) {

        TreeMap<String, String> signParams = new TreeMap<String, String>();
        Contants.setPublicParams(signParams);
        signParams.put("sellerUin", Contants.UIN.toString());
        signParams.put("dealCode", dealCode);
        signParams.put("closeReason", String.valueOf(closeReason));

        return Contants.createNewUrl(url, signParams);
    }

    /**
    *  卖家标记订单缺货
    * 
    * @param sellerUin
    *            卖家QQ号
    * @param dealCode
    *            string 订单编码
    * @return
    */
    public static String CreateSellerSignDealOutOfStockUrl(String url, String dealCode) {

        TreeMap<String, String> signParams = new TreeMap<String, String>();
        Contants.setPublicParams(signParams);
        signParams.put("sellerUin", Contants.UIN.toString());
        signParams.put("dealCode", dealCode);

        return Contants.createNewUrl(url, signParams);
    }

    /**
     * 查询 确认发货 返回结果若错误返回错误详情，如正确返回订单详情
     * 
     * @return String
     */
    public static Result<String> parseDeliveryConfirmationURL(String in) {
        Result<String> result = new Result<String>();
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(new URL(in).openStream());
            Element root = doc.getRootElement();
            String is_success = root.elementText("is_success");
            if (is_success.equalsIgnoreCase("F")) {
                String is_error = root.elementText("error");
                result.setResult(1);
                result.setMessage(EnumBilling_Detail.ILLEGAL_ARGUMENT.getValue(is_error));
                return result;
            } else {
                result.setMessage("");
                result.setResult(0);
                return result;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setResult(1);
            result.setMessage("操作出错");
            return result;
        }
    }

    /**
     * 解析订单列表的XML文档
     * @param ItemUrl
     * @return Map errorCode:错误代码；errorMessage:错误信息；tradeList:订单列表；userTradeList:同步订单信息列表
     */

    @SuppressWarnings("unchecked")
    public static Map parseInterfaceTradeListXml(String ItemUrl) {
        Map resultMap = new HashMap();
        List<Trade> tradeList = new ArrayList<Trade>();// 订单集合
        try {
            SAXReader reader = new SAXReader();
            String[] str = { "yyyy-MM-dd HH:mm:ss" };
            Document doc = reader.read(new URL(ItemUrl).openStream());
            Element root = doc.getRootElement();
            String errorCode = root.elementText("errorCode");
            resultMap.put("errorCode", errorCode);// 错误代码
            if (!errorCode.equalsIgnoreCase("0")) {
                resultMap.put("errorMessage", EnumBilling_Paipai_Detail.No_Permission.getValue(root
                    .elementText("errorMessage")));// 错误信息
                return resultMap;
            } else {
                Iterator dealList = root.elementIterator("dealList");// 订单列表
                while (dealList.hasNext()) {
                    Element dealInfo = (Element) dealList.next();
                    Iterator deal = dealInfo.elementIterator("dealInfo");// 订单信息
                    while (deal.hasNext()) {
                        Element ele = (Element) deal.next();
                        Trade trade = new Trade();

                        // 订单信息
                        trade.setBuyerNote(ele.elementText("buyerRemark"));
                        trade.setAddress(ele.elementText("receiverAddress"));
                        trade.setMobile(ele.elementText("receiverMobile"));
                        trade.setReceiver(ele.elementText("receiverName"));
                        trade.setZipcode(ele.elementText("receiverPostcode"));
                        // 接口订单属性
                        trade.setDealCode(ele.elementText("dealCode"));
                        if (StringUtils.isNotBlank(ele.elementText("createTime"))) {
                            trade.setCreateTime(DateUtils.parseDate(ele.elementText("createTime"),
                                str));
                        }
                        if (StringUtils.isNotBlank(ele.elementText("payTime"))) {
                            trade.setDealPayTime(DateUtils.parseDate(ele.elementText("payTime"),
                                str));
                        }

                        Element element = ele.element("itemList");// 商品列表
                        List<Element> itemLocalCodeList = element.elements("itemLocalCode");// 商家编码集合
                        List<Element> stockLocalCodeList = element.elements("stockLocalCode");// 产品编码集合
                        List<Element> itemDealCountList = element.elements("itemDealCount");// 购买数量集合
                        List<Element> itemDealPriceList = element.elements("itemDealPrice");// 买家下单时的商品价格
                        List<Element> itemAdjustPriceList = element.elements("itemAdjustPrice");// 订单的调整价格:正数为订单加价,负数为订单减价
                        List<Element> itemDiscountFeeList = element.elements("itemDiscountFee");// 购买商品的红包值、折扣优惠价
                        List<Order> orderList = new ArrayList<Order>();
                        for (int i = 0; i < stockLocalCodeList.size(); i++) {
                            Element element1 = (Element) stockLocalCodeList.get(i);
                            Element element2 = (Element) itemDealCountList.get(i);
                            Element element3 = (Element) itemLocalCodeList.get(i);
                            Element element4 = (Element) itemDealPriceList.get(i);
                            Element element5 = (Element) itemAdjustPriceList.get(i);
                            Element element6 = (Element) itemDiscountFeeList.get(i);
                            Order order = new Order();
                            
                            // 订单中商品信息
                            order.setCode(StringUtils.isBlank(element1.getText()) ? element3
                                .getText() : element1.getText());
                            if (StringUtils.isNotBlank(element2.getText())) {
                                order.setGoodsNumber(Long.parseLong(element2.getText()));
                                //抓取拍拍订单的商品价格
                                if (element5.getText() != null) {
                                    order.setGoodsPrice(MoneyUtil.div(MoneyUtil.add(MoneyUtil.mul(element4.getText(),element2.getText()),Double.parseDouble(element5.getText())),Double.parseDouble(element2.getText()))/100);
                                } else {
                                    order.setGoodsPrice(Double.parseDouble(element4.getText())/100);
                                }
                            } else {
                                order.setGoodsNumber(0);
                            }
                            orderList.add(order);
                        }
                        trade.setOrderList(orderList);
                        tradeList.add(trade);
                    }
                }

                resultMap.put("tradeList", tradeList);
                return resultMap;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            resultMap.put("errorCode", "1");
            resultMap.put("errorMessage", "本地操作出错");
            return resultMap;
        }

    }

    /**
     * 解析卖家修改订单状态（配货中，已发货）XML
     * @param ItemUrl
     * @return
     */
    public static Map<String, String> parseInterfaceDealStatusXml(String ItemUrl) {
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            SAXReader reader = new SAXReader();
            // Document doc = DocumentHelper.parseText(in);
            Document doc = reader.read(new URL(ItemUrl).openStream());
            Element root = doc.getRootElement();
            String errorCode = root.elementText("errorCode");
            resultMap.put("errorCode", errorCode);// 错误代码
            if (errorCode.equalsIgnoreCase("1")) {
                resultMap.put("errorMessage", EnumBilling_Paipai_Detail.No_Permission.getValue(root
                    .elementText("errorMessage")));// 错误信息
            } else {
                resultMap.put("dealCode",
                    EnumBilling_Paipai_Detail.No_Permission.getValue(root.elementText("dealCode")));// 订单编号
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            resultMap.put("errorCode", "1");
            resultMap.put("errorMessage", "本地操作出错");
        }
        return resultMap;
    }

    public static void main(String[] args) throws Exception {

        //      String ItemUrl = CreateDealDetailUrl(DEAL_DETAIL_URL, Contants.UIN.toString(), "855008924-20100804-359568958", "0");
        /**
         * String url,String pageIndex,String pageSize,
            String timeType,String timeBegin,String timeEnd, String dealState, String itemCode,
            String itemNameKey,String listItem,String dealCode,String orderField,String orderDesc
         */
        //      String ItemUrl = CreateDealListUrl(DEAL_LIST_URL,"","","","","","","","","1","","","");
        //      String ItemUrl = CreateDealPreparingUrl(DEAL_PREPARING_URL,"855008924-20100804-359568958");
        //      String ItemUrl = CreateDealConsignUrl(DEAL_CONSIGN_URL,"855008924-20100804-359568958","申通快递","已发货","19291828131",2);
        //      String ItemUrl = CreateDelayConsignmentTimeUrl(DELAY_CONSIGNMENT_TIME_URL,"855008924-20100804-359568958",5);
        //      String ItemUrl = CreateModifySellerNoteUrl(MODIFY_SELLER_NOTE_URL,"855008924-20100804-359568958","修改订单备注信息接口测试");
        //      String ItemUrl = CreateSellerCancelDealUrl(SELLER_CANCEL_DEAL_URL,"855008924-20100804-359568958",0);
        String ItemUrl = CreateSellerSignDealOutOfStockUrl(SELLER_SIGN_DEAL_OUT_OF_STOCK_URL,
            "855008924-20100804-359568958");
        System.out.println(ItemUrl);
        Result<String> result = parseDeliveryConfirmationURL(ItemUrl);
        System.out.println(result.getMessage());
    }
}
