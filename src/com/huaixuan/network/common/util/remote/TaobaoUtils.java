package com.huaixuan.network.common.util.remote;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.huaixuan.network.biz.domain.agent.InterfaceUserTrade;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.common.util.TaobaoTradeJSONConvert;
import com.hundsun.network.melody.common.util.StringUtil;

import com.taobao.api.TaobaoApiException;
import com.taobao.api.TaobaoJsonRestClient;
import com.taobao.api.TaobaoRestClient;
import com.taobao.api.json.JSONArray;
import com.taobao.api.model.DeliverySendRequest;
import com.taobao.api.model.DeliverySendResponse;
import com.taobao.api.model.TradeGetRequest;
import com.taobao.api.model.TradeGetResponse;
import com.taobao.api.model.TradesGetResponse;
import com.taobao.api.model.TradesSoldGetRequest;
import com.taobao.api.model.UserGetRequest;
import com.taobao.api.model.UserGetResponse;

public class TaobaoUtils {

    protected static Log      logger        = LogFactory.getLog(TaobaoUtils.class);

    private static String     AUTHORIZE_URL = "http://open.taobao.com/isv/authorize.php?appkey=";

    private static String     SANDBOX_URL   = "http://container.api.tbsandbox.com/container?authcode=";

    private static Properties properties;

    public static boolean     IS_SANDBOX;                                                              // true:测试环境 false:正式环境

    static {//初始化读取配置文件
        try {
            properties = new Properties();
            PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
            Resource resource = new DefaultResourceLoader()
                .getResource("classpath:/taobao.properties"); //"classpath:/taobao.properties"  "file:c:/xml/taobao.properties"
            propertiesPersister.load(properties, new InputStreamReader(resource.getInputStream(),
                "UTF-8"));
            IS_SANDBOX = BooleanUtils.toBoolean(properties.getProperty("taobao.environment"));

            logger.info("现在使用的是" + (IS_SANDBOX ? "沙箱测试环境" : "正式环境"));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 获取测试环境session,正式环境下返回"session"
     * @param nick
     * @param appkey
     * @return
     */
    public static String getTestSession(String nick, String appkey) {
        if (!IS_SANDBOX) {//自用型的Appkey可以不用取session
            return "session";
        }

        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3);
        HtmlPage loginPage = null;
        try {
            loginPage = webClient.getPage(AUTHORIZE_URL + appkey);
        } catch (FailingHttpStatusCodeException e1) {
            logger.error(e1.getMessage());
        } catch (MalformedURLException e1) {
            logger.error(e1.getMessage());
        } catch (IOException e1) {
            logger.error(e1.getMessage());
        }
        HtmlForm loginForm = loginPage.getForms().get(1);
        HtmlSelect select = loginForm.getSelectByName("nick");
        HtmlOption option = select.getOptionByValue(nick);
        select.setSelectedAttribute(option, true);
        HtmlSubmitInput authorizeButton = loginForm.getInputByValue("获取授权码");
        HtmlPage authorizePage = null;

        String session = null;

        try {
            Page rsp = authorizeButton.click();
            authorizePage = (HtmlPage) rsp;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return session;
        }

        HtmlElement element = authorizePage.getDocumentElement();
        HtmlElement autoInput = element.getElementById("autoInput");
        String authorizeCode = autoInput.getAttribute("value");

        if (authorizeCode != null && authorizeCode.length() > 1) {
            try {
                InputStream in = new URL(SANDBOX_URL + authorizeCode).openStream();
                session = StringUtils.substringBetween(IOUtils.toString(in), "top_session=", "&");
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

        return session;
    }

    /**
     * 测试用户申请信息是否正确
     * @param app_key
     * @param secret
     * @param nick
     * @return
     */
    public static String testApply(String app_key, String secret, String nick) {
        //      java.util.logging.Logger.getLogger("com.hundsun.bible.webapp.util.taobao").setLevel(java.util.logging.Level.SEVERE);

        String message = "";
        /* public TaobaoXmlRestClient(String appkey, String secret, boolean isSandbox) throws TaobaoApiException */
        TaobaoRestClient client;
        try {
            client = new TaobaoJsonRestClient(IS_SANDBOX ? "test" : app_key, IS_SANDBOX ? "test"
                : secret, IS_SANDBOX);

            UserGetRequest req = new UserGetRequest();
            //String publicFields = "nick,sex,buyer_credit,seller_credit,location.city,location.state,location.country,created,last_visit";
            //String privateFields = "location.zip,birthday";
            //req.setFields(publicFields + "," + privateFields);
            req.setFields("location.zip,type");//location.zip为隐私数据，只有本人登陆才能查询到
            req.setNick(nick);
            UserGetResponse rsp = client.userGet(req);
            com.taobao.api.model.User user = MyTaobaoJsonCovertUtil.json2Modle(rsp.getBody(),
                com.taobao.api.model.User.class, "taobao.user.get");

            logger.debug(rsp.getBody());

            if (rsp.getErrorCode() != null) {
                String errorMsg = rsp.getSubMsg() == null ? rsp.getMsg() : rsp.getSubMsg();
                if (errorMsg != null) {
                    if ("Invalid app Key".equals(errorMsg)) {
                        message = "['false','提交信息有误，App Key不能识别。']";
                    } else if ("Invalid signature".equals(errorMsg)) {
                        message = "['false','提交信息有误，App Secret出错。']";
                    } else {
                        message = "['false','" + "提交信息有误，" + errorMsg + "']";
                    }
                } else {
                    message = "['false','" + "未知错误" + "']";
                }
            } else if (user != null) {
                if (StringUtil.isNotBlank(user.getLocation().getZip())) {
                    message = "['true','测试正常!']";
                } else {
                    message = "['true','" + "但是该账号的信息未填写完整（邮编可能为空）或者用户提供的昵称可能与Appkey不匹配" + "']";
                }
            } else {
                message = "['false','" + "得到的返回数据不正确" + "']";
            }
        } catch (TaobaoApiException e) {
            logger.error("[TaobaoUtils.testApply]淘宝接口调用发生异常", e);
            message = "接口调用发生异常!";
        } finally {

        }
        return message;
    }

	/**
	 * 取一个用户在一段时间内的淘宝订单
	 * @param app_key
	 * @param app_secret
	 * @param nick
	 * @param startDate 开始时间
	 * @param endDate   结束时间
	 * @param status    订单状态，不指定则取所以状态的订单
	 * @param pageSize  每页大小
	 * @param pageNo    当前页数
	 * @return null 或 Map对象
	 */
	/* modify by shenzh Nov 10, 2010 说明： 不再一次取完用户所有的淘宝订单，调用程序一次处理一页的订单*/
	public static Map<String, Object> getTradeListFromTaobaoByApply(String app_key, String app_secret, String nick, Date startDate, Date endDate, String status, int pageSize, int pageNo){

	    List<Trade> tradeListFromTaobao = new ArrayList<Trade>();
        TaobaoRestClient client;
        int total_results = 0;
        Map<String, Object> retMap = null;
        try {
            client = new TaobaoJsonRestClient(IS_SANDBOX?app_key="test":app_key, IS_SANDBOX?"test":app_secret, IS_SANDBOX);

            TradesSoldGetRequest req = new TradesSoldGetRequest();
            req.setFields("seller_nick, buyer_nick, title, type, created, sid, tid, seller_rate, buyer_rate, status, payment, discount_fee, adjust_fee, post_fee, total_fee, pay_time, end_time, modified, consign_time, buyer_obtain_point_fee, point_fee, real_point_fee, received_payment, commission_fee, pic_path, iid, num, price, cod_fee, cod_status, shipping_type, receiver_name, receiver_state, receiver_city, receiver_district, receiver_address, receiver_zip, receiver_mobile, receiver_phone,orders");
            req.setStartCreated(startDate);
            req.setEndCreated(endDate);
            req.setPageSize(pageSize);
            req.setStatus(StringUtils.upperCase(status));//等待卖家发货,即:买家已付款

            //设置session_key（正式环境下返回"session"）
            String session_key = getTestSession(nick, app_key);

            //System.out.println(session_key);
            TradesGetResponse rsp;
            req.setPageNo(pageNo);
            rsp = client.tradesSoldGet(req, session_key);
            logger.debug(rsp.getBody());
            //转化淘宝返回的JSON对象
            Map<String,Object>  resultMap = MyTaobaoJsonCovertUtil.json2ResultListMap(rsp.getBody(), "Trade", "taobao.trades.sold.get");
            if(resultMap==null) return null;

            total_results = (Integer)resultMap.get("total_results");
            //System.out.println("total_results:"+total_results);

            JSONArray jsonArray = (JSONArray)resultMap.get("JSONArray");
            if(jsonArray==null) return null;

            //得到的是淘宝交易信息列表，每条交易信息可能包含多个订单
            List<com.taobao.api.model.Trade> list = TaobaoTradeJSONConvert.convertJsonArrayToTradeList(jsonArray);
            List<Trade> tradeList = convertTaobaoTradeList(list);
            if(CollectionUtils.isNotEmpty(tradeList)){
                tradeListFromTaobao.addAll(tradeList);
            }

            retMap = new HashMap<String, Object>();
            retMap.put("tradeListFromTaobao", tradeListFromTaobao);
            retMap.put("total_results", total_results);

        } catch (TaobaoApiException e) {
            logger.error("[TaobaoUtils.getTradeListFromTaobaoByApply]淘宝接口调用发生异常",e);
        } catch (Exception e) {
            logger.error("[TaobaoUtils.getTradeListFromTaobaoByApply]发生异常:"+"app_key="+app_key+"，nick="+nick,e);
        } finally{

        }
        return retMap;
	}

    /**
     * 将淘宝店铺订单转化为熙浪订单
     * @param taobaoTradeList
     * @return
     */
    public static List<Trade> convertTaobaoTradeList(List<com.taobao.api.model.Trade> taobaoTradeList) {

        List<Trade> tradeList = new ArrayList<Trade>();// 订单集合

        if (CollectionUtils.isNotEmpty(taobaoTradeList)) {

            for (com.taobao.api.model.Trade taobaoTrade : taobaoTradeList) {

                Trade trade = new Trade();
                trade.setReceiver(taobaoTrade.getReceiverName());
                trade.setProvince(taobaoTrade.getReceiverState());
                trade.setCity(taobaoTrade.getReceiverCity());
                trade.setDistrict(taobaoTrade.getReceiverDistrict());
                trade.setAddress(taobaoTrade.getReceiverAddress());
                trade.setZipcode(taobaoTrade.getReceiverZip());
                /* begin add by shenzh Oct 29, 2010 说明： 使用淘宝的物流费用*/
                try {
                    trade.setShippingAmount(Double.parseDouble(taobaoTrade.getPostFee()));
                } catch (NumberFormatException e) {
                    logger.error("物流费用格式转换出错", e);
                }
                /* end by shenzh Oct 29, 2010 */
                /* begin add by shenzh Nov 12, 2010 说明：取淘宝的商品总价及交易总价 */
                try {
                    trade.setAmount(Double.parseDouble(taobaoTrade.getPayment()));//买家实付金额（包含物流费用）。
                    trade.setGoodsAmount(Double.parseDouble(taobaoTrade.getPayment())
                                         - Double.parseDouble(taobaoTrade.getPostFee()));//商品总金额(熙浪这边显示，包含折扣但不包含邮费)
                } catch (NumberFormatException e) {
                    logger.error("金额格式转换出错", e);
                }
                /* end by shenzh Nov 12, 2010 */
                if (StringUtils.isNotBlank(taobaoTrade.getReceiverMobile())) {
                    trade.setMobile(taobaoTrade.getReceiverMobile());
                } else {
                    trade.setMobile(taobaoTrade.getReceiverPhone());
                }

                // 日期类型转换
                trade.setCreateTime(taobaoTrade.getCreated());
                trade.setDealPayTime(taobaoTrade.getPayTime());
                trade.setDealCode(taobaoTrade.getTid());//淘宝父订单编号
                //              trade.setTid(taobaoTrade.getId());
                //              trade.setBuyNick("淘宝平台");
                // 设置订单支付方式（delete： 在后面设置支付方式）
                //trade.setExpressPayment(EnumExpressDistPayment.PAYMENT_FIRST.getKey());
                /* begin add by shenzh Nov 12, 2010 说明：淘宝买家备注 */
                trade.setBuyerNote(taobaoTrade.getBuyerMessage());
                /* end by shenzh Nov 12, 2010 */
                List<com.taobao.api.model.Order> taobaoOrderList = taobaoTrade.getOrders();
                if (CollectionUtils.isNotEmpty(taobaoOrderList)) {
                    List<Order> orderList = new ArrayList<Order>();
                    for (com.taobao.api.model.Order taobaoOrder : taobaoOrderList) {
                        Order order = new Order();
                        order.setItemId(taobaoOrder.getOid());//淘宝子订单编号
                        order.setCode(taobaoOrder.getOuterSkuId() == null ? taobaoOrder
                            .getOuterIid() : taobaoOrder.getOuterSkuId());//熙浪产品编码或商品编码 必须唯一确定出售的产品
                        order.setGoodsNumber(taobaoOrder.getNum());
                        /* begin add by shenzh Oct 29, 2010 说明： */
                        /* modify by shenzh 2010-11-12 改为实付金额除以商品数量*/
                        try {
                            double payment = Double.parseDouble(taobaoOrder.getPayment());
                            if (CollectionUtils.size(taobaoOrderList) == 1) {
                                payment -= trade.getShippingAmount();//当只包含一个order时要减去物流费
                            }
                            order.setGoodsPrice(payment / order.getGoodsNumber());
                        } catch (NumberFormatException e) {
                            logger.error("商品价格格式转换出错", e);
                        }
                        /* end by shenzh Oct 29, 2010 */
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
     * 同步更新淘宝订单的物流信息并更改订单状态
     * @param app_key
     * @param app_sercet
     * @param nick
     * @param interfaceUserTrade    含需要更新的同步订单信息
     * @param depositoryFirst       出货仓库地址信息
     * @param sellerMsg             含物流信息
     *                              remark                备注
     *                              codeOfExpress         物流公司代码
     *                              nameOfExpress         物流公司名称
     *                              logisticsCode         物流单号
     *                              sellerName            物流显示的卖家姓名
     *                              sellerZip             物流显示的卖家邮编
     *                              sellerPhone           物流显示的卖家联系电话
     * @return
     */
    public static String updateTaobaoLogisticsMsg(String app_key, String app_sercet, String nick,
                                                  InterfaceUserTrade interfaceUserTrade,
                                                  DepositoryFirst depositoryFirst,
                                                  Map<String, String> sellerMsg) {

        Map<String, String> errorCodeToChinese = new HashMap<String, String>();
        errorCodeToChinese.put("10", "服务不可用");
        errorCodeToChinese.put("11", "第三方程序权限不够");
        errorCodeToChinese.put("12", "用户权限不够");
        errorCodeToChinese.put("15", "执行远程服务时出错");

        String message = null;
        try {
            TaobaoRestClient client = new TaobaoJsonRestClient(IS_SANDBOX ? app_key = "test"
                : app_key, IS_SANDBOX ? "test" : app_sercet, IS_SANDBOX);

            String session_key = getTestSession(nick, app_key);

            TradeGetRequest req3 = new TradeGetRequest();
            req3.setFields("status");//查询当前订单状态和一些需要的卖家信息，供接口调用
            req3.setTid(interfaceUserTrade.getPaipaiTradeId());//得到淘宝平台的交易ID

            TradeGetResponse rsp3 = client.tradeGet(req3, session_key);
            com.taobao.api.model.Trade taobaoTrade = MyTaobaoJsonCovertUtil.json2Modle(
                rsp3.getBody(), com.taobao.api.model.Trade.class, "taobao.trade.get");
            //System.out.println(taobaoTrade.getStatus());

            if (taobaoTrade == null
                || !"WAIT_SELLER_SEND_GOODS".equalsIgnoreCase(taobaoTrade.getStatus())) {
                message = "远程订单[淘宝订单号："
                          + interfaceUserTrade.getPaipaiTradeId()
                          + ",本地订单号："
                          + interfaceUserTrade.getTradeId()
                          + "]同步出错："
                          + (taobaoTrade != null ? "淘宝上的订单状态[" + taobaoTrade.getStatus()
                                                   + "]不是等待卖家发货" : "无法得到远程订单");
                logger.error(message + " "
                             + (rsp3.getSubMsg() == null ? rsp3.getMsg() : rsp3.getSubMsg()));
                return message;//如果状态不为买家已付款，则不进行之后的操作
            }

            /*UserGetRequest req = new UserGetRequest();
            String fields = "location.zip";

            req.setFields(fields);
            req.setNick(nick);

            UserGetResponse rsp = client.userGet(req,session_key);
            com.taobao.api.model.User user = MyTaobaoJsonCovertUtil.json2Modle(rsp.getBody(), com.taobao.api.model.User.class, "taobao.user.get");
             */
            DeliverySendRequest req2 = new DeliverySendRequest();

            String expressCode = sellerMsg.get("codeOfExpress");
            //如果没设置，则默认为"YWSTO"进行发货物流的更新
            if (StringUtil.isBlank(expressCode)) {
                expressCode = "YWSTO";
            }
            
            req2.setTid(interfaceUserTrade.getPaipaiTradeId()); //交易ID
            req2.setCompanyCode(IS_SANDBOX ? sellerMsg.get("nameOfExpress") : sellerMsg
                .get("codeOfExpress")); //可以通过taobao.logisticcompanies.get查询物流公司代码
            req2.setMemo(sellerMsg.get("remark")); //卖家备注
            req2.setOutSid(sellerMsg.get("logisticsCode")); //运单号 具体一个物流公司的运单号码
            req2.setOrderType("delivery_needed");
            /*以下填写熙浪信息*/
            String address = IS_SANDBOX ? depositoryFirst.getAddress() + " "
                : "" + depositoryFirst.getParticularAddress();
            req2.setSellerAddress(address); //正式环境下卖家地址(详细地址)省、市、区不需要提供
            req2.setSellerAreaId(depositoryFirst.getRegionCode()); //卖家所在地国家公布标准地区码通过taobao.areas.get获取
            //req2.setSellerMobile(taobaoTrade.getSellerMobile());
            req2.setSellerName(sellerMsg.get("sellerName"));
            req2.setSellerPhone(sellerMsg.get("sellerPhone"));
            req2.setSellerZip(sellerMsg.get("sellerZip"));

            DeliverySendResponse response = client.deliverySend(req2, session_key);
            //            System.out.println(response.getBody());
            //            System.out.println("发货是否成功 : "+response.isDeliverSuccess()); //注意:通过SDK里面发货API是否成功的方法为 isDeliverSucce
            if (response.getErrorCode() == null) {
                logger.info("淘宝订单：" + interfaceUserTrade.getPaipaiTradeId() + " 物流信息添加是否正确: "
                            + response.isDeliverSuccess());
                return message;
            } else {
                message = "远程订单[淘宝订单号："
                          + interfaceUserTrade.getPaipaiTradeId()
                          + ",本地订单号："
                          + interfaceUserTrade.getTradeId()
                          + "]同步出错："
                          + (response.getSubMsg() == null ? (errorCodeToChinese.get(response
                              .getErrorCode()) == null ? response.getMsg() : errorCodeToChinese
                              .get(response.getErrorCode()) + "!需要登录淘宝完成本次订单状态同步操作。") : response
                              .getSubMsg());
                logger.error(message);
                return message;
            }
        } catch (TaobaoApiException e) {
            logger.error(
                "[TaobaoUtils.updateTaobaoLogisticsMsg]" + "远程订单[淘宝订单号："
                        + interfaceUserTrade.getPaipaiTradeId() + ",本地订单号："
                        + interfaceUserTrade.getTradeId() + "]同步出错，" + "淘宝接口调用发生异常", e);
            message = "淘宝接口调用发生异常，订单状态未同步到淘宝";
        } finally {

        }
        return message;
    }

    /**
     *
     * 功能：增加买家备注信息<br>
     *
     * @param trade 订单信息，缺少买家备注信息
     * @param userTrade 含淘宝订单ID
     * @param app_key
     * @param app_sercet
     * @param nick
     * @return trade 订单信息，增加买家备注信息
     * @author shenzh
     * Nov 12, 2010
     */
    public static Trade addBuyerNoteToTrade(Trade trade, InterfaceUserTrade userTrade,
                                            String app_key, String app_sercet, String nick) {

        try {
            TaobaoRestClient client = new TaobaoJsonRestClient(IS_SANDBOX ? app_key = "test"
                : app_key, IS_SANDBOX ? "test" : app_sercet, IS_SANDBOX);

            String session_key = getTestSession(nick, app_key);

            TradeGetRequest req3 = new TradeGetRequest();
            req3.setFields("buyer_message");//查询当前订单状态和一些需要的卖家信息，供接口调用
            req3.setTid(userTrade.getPaipaiTradeId());//得到淘宝平台的交易ID

            TradeGetResponse rsp3 = client.tradeGet(req3, session_key);
            com.taobao.api.model.Trade taobaoTrade = MyTaobaoJsonCovertUtil.json2Modle(
                rsp3.getBody(), com.taobao.api.model.Trade.class, "taobao.trade.get");
            if (rsp3.getErrorCode() == null) {
                trade.setBuyerNote(taobaoTrade.getBuyerMessage());
            } else {
                String message = "远程订单[淘宝订单号：" + userTrade.getPaipaiTradeId() + "]获取买家留言信息出错："
                                 + (rsp3.getSubMsg() == null ? rsp3.getMsg() : rsp3.getSubMsg());
                logger.error(message);
            }
        } catch (TaobaoApiException e) {
            logger.error("[TaobaoUtils.addBuyerNoteToTrade]淘宝接口调用发生异常，未取得买家留言", e);
        } finally {

        }
        return trade;
    }

    /**
     * 功能：增加发票抬头的同步获取
     *
     * @param trade 订单信息
     * @return trade
     * @author chenhang
     * 2011-01-05
     */
    public static Trade addInvoiceNameToTrade(Trade trade, InterfaceUserTrade userTrade,
                                              String app_key, String app_secret, String nick) {
        try {
            TaobaoRestClient client = new TaobaoJsonRestClient(IS_SANDBOX ? app_key = "test"
                : app_key, IS_SANDBOX ? "test" : app_secret, IS_SANDBOX);
            String session_key = getTestSession(nick, app_key);
            TradeGetRequest req4 = new TradeGetRequest();
            req4.setFields("invoice_name");//
            req4.setTid(userTrade.getPaipaiTradeId());//得到淘宝平台的交易ID
            TradeGetResponse rsp4 = client.tradeFullInfoGet(req4, session_key);
            com.taobao.api.model.Trade taobaoTrade = MyTaobaoJsonCovertUtil.json2Modle(
                rsp4.getBody(), com.taobao.api.model.Trade.class, "taobao.trade.fullinfo.get");
            if (rsp4.getErrorCode() == null) {
                trade.setInvoiceName(taobaoTrade.getInvoiceName());
            } else {
                String message = "远程订单[淘宝订单号：" + userTrade.getPaipaiTradeId() + "]获取发票抬头出错："
                                 + (rsp4.getSubMsg() == null ? rsp4.getMsg() : rsp4.getSubMsg());
                logger.error(message);
            }
        } catch (TaobaoApiException e) {
            logger.error("[TaobaoUtils.addInvoiceName]淘宝借口调用发生异常，未取得发票抬头", e);
        } finally {

        }
        return trade;
    }

    public static void main(String[] args) throws TaobaoApiException, ParseException,
                                          DocumentException, FailingHttpStatusCodeException,
                                          MalformedURLException, IOException {

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

}
