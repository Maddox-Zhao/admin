package com.huaixuan.network.common.util.remote;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.huaixuan.network.biz.domain.taobao.FenxiaoOrders;
import com.huaixuan.network.biz.domain.taobao.FenxiaoProductCats;
import com.huaixuan.network.biz.domain.taobao.FenxiaoOrders.PurchaseOrder;
import com.huaixuan.network.biz.domain.taobao.FenxiaoOrders.PurchaseOrder.Receiver;
import com.huaixuan.network.biz.domain.taobao.FenxiaoOrders.PurchaseOrder.SubPurchaseOrder;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.enums.EnumExpressDistPayment;
import com.huaixuan.network.biz.enums.EnumTradeStatus;


import com.taobao.api.TaobaoApiException;
import com.taobao.api.TaobaoJsonRestClient;
import com.taobao.api.TaobaoRestClient;

import com.taobao.api.fenxiao.TaobaoFenxiaoJsonRestClient;
import com.taobao.api.fenxiao.model.DistributorsGetRequest;
import com.taobao.api.fenxiao.model.DistributorsGetResponse;
import com.taobao.api.fenxiao.model.FenxiaoProduct;
import com.taobao.api.fenxiao.model.OrdersGetRequest;
import com.taobao.api.fenxiao.model.OrdersGetResponse;
import com.taobao.api.fenxiao.model.ProductAddRequest;
import com.taobao.api.fenxiao.model.ProductAddResponse;
import com.taobao.api.fenxiao.model.ProductUpdateRequest;
import com.taobao.api.fenxiao.model.ProductUpdateResponse;
import com.taobao.api.fenxiao.model.ProductcatsGetRequest;
import com.taobao.api.fenxiao.model.ProductcatsGetResponse;
import com.taobao.api.fenxiao.model.ProductsGetRequest;
import com.taobao.api.fenxiao.model.ProductsGetResponse;

import com.taobao.api.model.ItemCatsGetRequest;
import com.taobao.api.model.ItemCatsResponse;
import com.taobao.api.model.ItemPropValuesGetRequest;
import com.taobao.api.model.ItemPropValuesResponse;
import com.taobao.api.model.ItemPropsRequest;
import com.taobao.api.model.ItemPropsResponse;
import com.taobao.api.model.PostagesGetRequest;
import com.taobao.api.model.PostagesGetResponse;

import com.taobao.api.util.DateUtil;

public class TaobaoFenXiaoUtils {

    public static String                       APP_KEY;                                                               //应用唯一标识ID
    public static String                       APP_SERCET;                                                            //应用协议签名密钥
    public static String                       NICK;                                                                  //用户名称
    public static String                       URL;                                                                   //环境地址

    public static String                       FENXIAO_ORDERS_GET_RESPONSE      = "fenxiao_orders_get_response";
    public static String                       FENXIAO_PRODUCTCATS_GET_RESPONSE = "fenxiao_productcats_get_response";
    public static String                       FENXIAO_PRODUCT_UPDATE_RESPONSE  = "fenxiao_product_update_response";
    public static String                       ERROR_RESPONSE                   = "error_response";
    public static String                       ERROR_CODE                       = "27";
    public static String                       ERROR_MSG                        = "connect timed out";

    protected final static Log                 logger                           = LogFactory
                                                                                    .getLog(TaobaoFenXiaoUtils.class);

    private static TaobaoRestClient            taobaoClient;
    private static TaobaoFenxiaoJsonRestClient clientJson;
    private static TaobaoFenxiaoJsonRestClient clientXml;

    private static Properties                  properties;

    private static String                      session                          = "session";

    public static String                       TAOBAO_APP_KEY;                                                        //应用唯一标识ID
    public static String                       TAOBAO_APP_SERCET;                                                     //应用协议签名密钥

    static {
            if (BooleanUtils.toBoolean(false)) {
                APP_KEY = "test";
                APP_SERCET = "test";
                URL = "http://gw.api.tbsandbox.com/router/rest";
            } else {
                APP_KEY = "12139725";
                APP_SERCET = "23f1c67af6f4c5c85df00b7dabbad477";
                URL = "http://gw.api.taobao.com/router/rest";

                TAOBAO_APP_KEY = "12149422";
                TAOBAO_APP_SERCET = "82c90fab315784225bb0127e67f676c0";
            }
            logger.info("APP_KEY..." + APP_KEY);
            logger.info("APP_SERCET..." + APP_SERCET);
            logger.info("NICK..." + NICK);
            logger.info("URL..." + URL);
    }

    public static String getSession() {
        if (BooleanUtils.toBoolean(properties.getProperty("taobao.environment"))) {
            if (session != null) {
                FenxiaoProductCats pc = getTestProductCats(session);
                Map<String, String> map = pc.getMap();
                if (StringUtils.equals(map.get("state"), "false")) {
                    session = LoginUtil.getTestSession("alipublic21", "test"); // 分销测试供应商账号
                }
            } else {
                session = LoginUtil.getTestSession("alipublic21", "test");
            }
        }
        logger.info("session..." + session);
        return session;
    }

    public static void setSession(String session) {
        TaobaoFenXiaoUtils.session = session;
    }

    public static TaobaoFenxiaoJsonRestClient getFenxiaoXml() throws TaobaoApiException {
        if (clientXml == null) {
            clientXml = new TaobaoFenxiaoJsonRestClient(URL, "2.0", APP_KEY, APP_SERCET);
            clientXml.setFormat("xml");
        }
        return clientXml;
    }

    public static TaobaoFenxiaoJsonRestClient getFenxiaoJson() throws TaobaoApiException {
        if (clientJson == null) {
            clientJson = new TaobaoFenxiaoJsonRestClient(URL, "2.0", APP_KEY, APP_SERCET);
            clientJson.setFormat("json");
        }
        return clientJson;
    }

    public static TaobaoRestClient getTaobaoClient() throws TaobaoApiException {
        if (taobaoClient == null) {
            taobaoClient = new TaobaoJsonRestClient(URL, "2.0", TAOBAO_APP_KEY, TAOBAO_APP_SERCET);
        }
        return taobaoClient;
    }

    /**
     * 查询采购单
     * @throws ParseException 
     * @throws TaobaoApiException 
     * @throws ParseException 
     * @throws DocumentException 
     */
    public static FenxiaoOrders getFenxiaoOrders() {
        FenxiaoOrders fo = null; //new FenxiaoOrders();
        TaobaoFenxiaoJsonRestClient client;
        String xml = null;
        try {
            client = getFenxiaoXml();
            client.setFormat("xml");
            OrdersGetRequest request = new OrdersGetRequest();

            DateTime dateTime = new DateTime(); //2010,10,13,0,0,0,0
            // 取得当前日期的第一天和最后一天
            Date beginDate = dateTime.hourOfDay().withMinimumValue().minuteOfHour()
                .withMinimumValue().secondOfMinute().withMinimumValue().toDate();
            Date endDate = dateTime.hourOfDay().withMaximumValue().minuteOfHour()
                .withMaximumValue().secondOfMinute().withMaximumValue().toDate();

            request.setStartCreated(beginDate);
            request.setEndCreated(endDate);
            request.setPageNo(1);
            request.setPageSize(50);
            // 查询状态为等待卖家发货,即:买家已付款
            request.setStatus("WAIT_SELLER_SEND_GOODS");

            OrdersGetResponse response = client.ordersGet(request, getSession());

            xml = response.getBody();

            if (StringUtils.equals(check(xml), FENXIAO_ORDERS_GET_RESPONSE)) {
                //使用使用Jaxb2.0实现XML转Object
                JaxbBinder jxbinder = new JaxbBinder(FenxiaoOrders.class);
                fo = jxbinder.fromXml(xml);
                Map<String, String> map = new HashMap<String, String>();
                map.put("state", "true");
                fo.setMap(map);
            } else if (StringUtils.equals(check(xml), ERROR_RESPONSE)) {
                fo = new FenxiaoOrders();
                fo.setMap(getErrorCode(xml)); // fo.setErrorCode(getErrorCode(xml));
            }

            return fo;

        } catch (TaobaoApiException e) {
            logger.error("调用淘宝分销接口异常..." + e.getMessage());
            fo = new FenxiaoOrders();
            fo.setMap(getErrorCode(xml)); //fo.setErrorCode(e.getMessage());
        } finally {
            logger.info("xml = " + xml);
        }

        return fo;

    }

    /**
     * 采购单转订单集合
     * @param orders
     * @return
     */
    public static List<Trade> getTradeList(FenxiaoOrders orders) {

        List<Trade> tradeList = new ArrayList<Trade>();// 订单集合
        List<PurchaseOrder> porderList = orders.getPurchaseOrders();
        if (CollectionUtils.isNotEmpty(porderList)) {
            for (PurchaseOrder purcOrder : porderList) {
                Trade trade = new Trade();
                trade.setPurchaseId(purcOrder.getId());
                //取得买家详细的信息
                Receiver receiver = purcOrder.getReceiver();
                trade.setReceiver(receiver.getName());
                trade.setProvince(receiver.getState());
                trade.setCity(receiver.getCity());
                trade.setDistrict(receiver.getDistrict());
                trade.setAddress(receiver.getAddress());
                trade.setZipcode(receiver.getZip());
                if (StringUtils.isNotBlank(receiver.getMobilePhone())) {
                    trade.setMobile(receiver.getMobilePhone());
                } else {
                    trade.setMobile(receiver.getPhone());
                }

                // 日期类型转换
                DateTime dt1 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(
                    purcOrder.getCreated());
                DateTime dt2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(
                    purcOrder.getPayTime());
                trade.setCreateTime(dt1.toDate());
                trade.setPayTime(dt2.toDate()); // .setDealPayTime();

                trade.setTid(purcOrder.getId());
                trade.setBuyNick("淘宝分销平台");
                trade.setStatus(EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey());
                // 设置订单支付方式
                trade.setExpressPayment(EnumExpressDistPayment.PAYMENT_FIRST.getKey());
                // 设置订单类型TRADE_TYPE(0：前台下单；1：后台下单；2：拍拍订单 3:淘宝订单 4：淘宝分销平台)
                trade.setTradeType(4);

                List<SubPurchaseOrder> subOrderList = purcOrder.getSubPurchaseOrders();
                if (CollectionUtils.isNotEmpty(subOrderList)) {
                    List<Order> orderList = new ArrayList<Order>(); // 产品集合
                    for (SubPurchaseOrder subOrder : subOrderList) {
                        Order order = new Order();
                        order.setItemId(subOrder.getItemId());
                        order.setCode(subOrder.getItemOuterId());
                        order.setGoodsNumber(subOrder.getNum());
                        orderList.add(order);
                    }
                    trade.setOrderList(orderList);
                }
                tradeList.add(trade);
            }
        }

        return tradeList;
    }

    /**
     * 从淘宝分销平台查询产品线列表
     * @return FenxiaoProductCats
     */
    public static FenxiaoProductCats getProductCats() {
        TaobaoFenxiaoJsonRestClient client;
        FenxiaoProductCats productCats = null;
        try {
            client = getFenxiaoXml();
            ProductcatsGetRequest req = new ProductcatsGetRequest();
            ProductcatsGetResponse rsp = client.productcatsGet(req, getSession());
            String xml = rsp.getBody();
            if (StringUtils.equals(check(xml), FENXIAO_PRODUCTCATS_GET_RESPONSE)) {
                JaxbBinder jxbinder = new JaxbBinder(FenxiaoProductCats.class);
                productCats = jxbinder.fromXml(xml);
                Map<String, String> map = new HashMap<String, String>();
                map.put("state", "true");
                productCats.setMap(map);
            } else if (StringUtils.equals(check(xml), ERROR_RESPONSE)) {
                productCats = new FenxiaoProductCats();
                productCats.setMap(getErrorCode(xml));
            }
        } catch (TaobaoApiException e) {
            logger.error("xml解析异常..." + e.getMessage());
        }
        return productCats;
    }

    /**
     * 测试接口
     * @param appKey
     * @param appSercet
     * @return
     */
    public static FenxiaoProductCats getTestProductCats(String session) {
        TaobaoFenxiaoJsonRestClient client;
        FenxiaoProductCats productCats = null;
        try {
            client = getFenxiaoXml();
            ProductcatsGetRequest req = new ProductcatsGetRequest();
            ProductcatsGetResponse rsp = client.productcatsGet(req, session);
            String xml = rsp.getBody();
            if (StringUtils.equals(check(xml), FENXIAO_PRODUCTCATS_GET_RESPONSE)) {
                JaxbBinder jxbinder = new JaxbBinder(FenxiaoProductCats.class);
                productCats = jxbinder.fromXml(xml);
                Map<String, String> map = new HashMap<String, String>();
                map.put("state", "true");
                productCats.setMap(map);
            } else if (StringUtils.equals(check(xml), ERROR_RESPONSE)) {
                productCats = new FenxiaoProductCats();
                productCats.setMap(getErrorCode(xml));
            }
        } catch (TaobaoApiException e) {
            logger.error("xml解析异常..." + e.getMessage());
        }
        return productCats;
    }

    public static void getDistributors() {
        TaobaoFenxiaoJsonRestClient client;
        try {
            client = getFenxiaoXml();
            DistributorsGetRequest req = new DistributorsGetRequest();
            String nicks = "sandbox_c_1,sandbox_c_3";
            req.setNicks(nicks);

            DistributorsGetResponse rsp = client.distributorsGet(req,
                "23077b6e11932d0d943483788bcb653a75ad123"); //client.distributorsGet(req,"session");
            System.out.println(rsp.getBody());

        } catch (TaobaoApiException e) {
            logger.error(e.getMessage());
        }
    }

    public static JSONObject getTaobaoClass(String parentCid) {
        TaobaoRestClient taobaoClient;
        try {
            taobaoClient = getTaobaoClient();
            ItemCatsGetRequest req = new ItemCatsGetRequest();
            req.setFields("cid,name,is_parent");
            req.setParentCid(parentCid);
            ItemCatsResponse res = taobaoClient.itemCatsGet(req);
            JSONObject taobaoClassList = JSONObject.fromObject(res.getBody());
            return taobaoClassList;
        } catch (TaobaoApiException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static JSONObject loadTaobaoProps(String leafCid) throws ParseException {
        TaobaoRestClient taobaoClient;
        try {
            taobaoClient = getTaobaoClient();
            ItemPropsRequest req = new ItemPropsRequest();
            req.setFields("pid,name,is_key_prop,is_sale_prop,is_color_prop,is_enum_prop,is_input_prop,is_item_prop,child_template,must,multi,parent_pid,parent_vid,status,sort_order");
            req.setCid(leafCid);
            req.setDatetime(DateUtil.strToDate("2005-01-01 00:00:00"));
            ItemPropsResponse rsp = taobaoClient.itemPropsGet(req);
            JSONObject propsJson = JSONObject.fromObject(rsp.getBody());
            return propsJson;
        } catch (TaobaoApiException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static JSONObject loadTaobaoPropValues(String leafCid) {
        TaobaoRestClient taobaoClient;
        try {
            taobaoClient = getTaobaoClient();
            ItemPropValuesGetRequest req = new ItemPropValuesGetRequest();
            req.setFields("cid,pid,prop_name,vid,name,name_alias,status,sort_order");
            req.setCid(leafCid);
            req.setDatetime(DateUtil.strToDate("1970-1-1 00:00:00"));
            ItemPropValuesResponse res = taobaoClient.itemPropValuesGet(req);
            JSONObject propValuesJson = JSONObject.fromObject(res.getBody());
            return propValuesJson;
        } catch (TaobaoApiException e) {
            logger.error(e.getMessage());
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static JSONObject getTaobaoFxProductCats() {
        TaobaoFenxiaoJsonRestClient client;
        try {
            client = getFenxiaoJson();
            ProductcatsGetRequest req = new ProductcatsGetRequest();
            ProductcatsGetResponse rsp = client.productcatsGet(req, getSession());
            JSONObject taobaoProductCats = JSONObject.fromObject(rsp.getBody());
            return taobaoProductCats;
        } catch (TaobaoApiException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static JSONObject getTaobaoPostages() {
        TaobaoRestClient taobaoClient;
        try {
            taobaoClient = getTaobaoClient();
            PostagesGetRequest request = new PostagesGetRequest();
            request.setFields("postage_id,name");
            PostagesGetResponse response = taobaoClient.postagesGet(request, getSession());
            JSONObject taobaoPostages = JSONObject.fromObject(response.getBody());
            return taobaoPostages;
        } catch (TaobaoApiException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static JSONObject getTaobaoFxProducts(ProductsGetRequest req) {
        TaobaoFenxiaoJsonRestClient client;
        try {
            client = getFenxiaoJson();
            ProductsGetResponse rsp = client.productsGet(req, getSession());
            JSONObject taobaoFxProducts = JSONObject.fromObject(rsp.getBody());
            return taobaoFxProducts;
        } catch (TaobaoApiException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static JSONObject addTaobaoFxProduct(FenxiaoProduct fenxiaoProduct) {
        TaobaoFenxiaoJsonRestClient client;
        try {
            client = getFenxiaoJson();
            ProductAddRequest request = new ProductAddRequest();
            request.setProduct(fenxiaoProduct);
            ProductAddResponse response = client.productAdd(request, getSession());
            JSONObject fenxiaoProductAddRes = JSONObject.fromObject(response.getBody());
            return fenxiaoProductAddRes;
        } catch (TaobaoApiException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static JSONObject updateTaobaoFxProduct(FenxiaoProduct fenxiaoProduct) {
        TaobaoFenxiaoJsonRestClient client;
        try {
            client = getFenxiaoJson();
            ProductUpdateRequest request = new ProductUpdateRequest();
            request.setProduct(fenxiaoProduct);
            ProductUpdateResponse response = client.productUpdate(request, getSession());
            JSONObject fenxiaoProductUpdateRes = JSONObject.fromObject(response.getBody());
            return fenxiaoProductUpdateRes;
        } catch (TaobaoApiException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static Map<String, String> updateTaobaoProduct(FenxiaoProduct fenxiaoProduct) {
        TaobaoFenxiaoJsonRestClient client;
        try {
            client = getFenxiaoXml();
            ProductUpdateRequest request = new ProductUpdateRequest();
            request.setProduct(fenxiaoProduct);
            ProductUpdateResponse response = client.productUpdate(request, getSession());
            String xml = response.getBody();
            logger.info("xml..." + getSession() + "==" + xml);
            System.out.println("xml..." + xml);
            if (StringUtils.equals(check(xml), FENXIAO_PRODUCT_UPDATE_RESPONSE)) {
                return getUpdateInfo(xml);
            } else if (StringUtils.equals(check(xml), ERROR_RESPONSE)) {
                return getErrorCode(xml);
            }
        } catch (TaobaoApiException e) {
            logger.error("updateTaobaoProduct..." + e.getMessage());
        }
        return null;
    }

    public static String check(String xml) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            logger.error("xml解析异常..." + e.getMessage());
        }
        return doc.getRootElement().getName();
    }

    public static Map<String, String> getErrorCode(String xml) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            logger.error("xml解析异常..." + e.getMessage());
        }
        Element element = doc.getRootElement();
        Map<String, String> map = new HashMap<String, String>();
        map.put("state", "false");
        map.put("code", element.element("code").getText());
        map.put("msg", element.element("msg").getText());
        map.put("sub_msg", element.element("msg").getText());
        return map;
    }

    public static Map<String, String> getUpdateInfo(String xml) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            logger.error("xml解析异常..." + e.getMessage());
        }
        Element element = doc.getRootElement();
        Map<String, String> map = new HashMap<String, String>();
        map.put("state", "true");
        map.put("pid", element.element("pid").getText());
        map.put("modified", element.element("modified").getText());
        return map;
    }

    public static StandardToStringStyle getStringStyle() {
        StandardToStringStyle stringStyle = new StandardToStringStyle();
        stringStyle.setUseClassName(false);
        stringStyle.setUseIdentityHashCode(false);
        stringStyle.setNullText("\"\"");
        return stringStyle;
    }

    public static void main(String[] args) throws TaobaoApiException, ParseException,
                                          DocumentException, FailingHttpStatusCodeException,
                                          MalformedURLException, IOException {

        //      List<Trade> tradeList = getTradeList();
        //      
        //      System.out.println("tradeList = " + tradeList);
        //      
        //      System.out.println("===================");
        //      
        //      FenxiaoProductCats productCatsList = getProductCats();
        //      
        //      System.out.println("productCatsList = " + productCatsList);

        FenxiaoProduct p = new FenxiaoProduct();
        p.setPid(438086l); ///.setPid(1113l);  //438075L   //438087l
        p.setName("桃心花纹发圈头绳111"); // 汉语词典testp
        TaobaoFenXiaoUtils.setSession("11"); //LoginUtil.getTestSession("alipublic21", "test")
        Map<String, String> j = TaobaoFenXiaoUtils.updateTaobaoProduct(p);
        System.out.println(j);

        //      getDistributors();

    }

}
