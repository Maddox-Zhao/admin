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

    public static boolean     IS_SANDBOX;                                                              // true:���Ի��� false:��ʽ����

    static {//��ʼ����ȡ�����ļ�
        try {
            properties = new Properties();
            PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
            Resource resource = new DefaultResourceLoader()
                .getResource("classpath:/taobao.properties"); //"classpath:/taobao.properties"  "file:c:/xml/taobao.properties"
            propertiesPersister.load(properties, new InputStreamReader(resource.getInputStream(),
                "UTF-8"));
            IS_SANDBOX = BooleanUtils.toBoolean(properties.getProperty("taobao.environment"));

            logger.info("����ʹ�õ���" + (IS_SANDBOX ? "ɳ����Ի���" : "��ʽ����"));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * ��ȡ���Ի���session,��ʽ�����·���"session"
     * @param nick
     * @param appkey
     * @return
     */
    public static String getTestSession(String nick, String appkey) {
        if (!IS_SANDBOX) {//�����͵�Appkey���Բ���ȡsession
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
        HtmlSubmitInput authorizeButton = loginForm.getInputByValue("��ȡ��Ȩ��");
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
     * �����û�������Ϣ�Ƿ���ȷ
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
            req.setFields("location.zip,type");//location.zipΪ��˽���ݣ�ֻ�б��˵�½���ܲ�ѯ��
            req.setNick(nick);
            UserGetResponse rsp = client.userGet(req);
            com.taobao.api.model.User user = MyTaobaoJsonCovertUtil.json2Modle(rsp.getBody(),
                com.taobao.api.model.User.class, "taobao.user.get");

            logger.debug(rsp.getBody());

            if (rsp.getErrorCode() != null) {
                String errorMsg = rsp.getSubMsg() == null ? rsp.getMsg() : rsp.getSubMsg();
                if (errorMsg != null) {
                    if ("Invalid app Key".equals(errorMsg)) {
                        message = "['false','�ύ��Ϣ����App Key����ʶ��']";
                    } else if ("Invalid signature".equals(errorMsg)) {
                        message = "['false','�ύ��Ϣ����App Secret����']";
                    } else {
                        message = "['false','" + "�ύ��Ϣ����" + errorMsg + "']";
                    }
                } else {
                    message = "['false','" + "δ֪����" + "']";
                }
            } else if (user != null) {
                if (StringUtil.isNotBlank(user.getLocation().getZip())) {
                    message = "['true','��������!']";
                } else {
                    message = "['true','" + "���Ǹ��˺ŵ���Ϣδ��д�������ʱ����Ϊ�գ������û��ṩ���ǳƿ�����Appkey��ƥ��" + "']";
                }
            } else {
                message = "['false','" + "�õ��ķ������ݲ���ȷ" + "']";
            }
        } catch (TaobaoApiException e) {
            logger.error("[TaobaoUtils.testApply]�Ա��ӿڵ��÷����쳣", e);
            message = "�ӿڵ��÷����쳣!";
        } finally {

        }
        return message;
    }

	/**
	 * ȡһ���û���һ��ʱ���ڵ��Ա�����
	 * @param app_key
	 * @param app_secret
	 * @param nick
	 * @param startDate ��ʼʱ��
	 * @param endDate   ����ʱ��
	 * @param status    ����״̬����ָ����ȡ����״̬�Ķ���
	 * @param pageSize  ÿҳ��С
	 * @param pageNo    ��ǰҳ��
	 * @return null �� Map����
	 */
	/* modify by shenzh Nov 10, 2010 ˵���� ����һ��ȡ���û����е��Ա����������ó���һ�δ���һҳ�Ķ���*/
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
            req.setStatus(StringUtils.upperCase(status));//�ȴ����ҷ���,��:����Ѹ���

            //����session_key����ʽ�����·���"session"��
            String session_key = getTestSession(nick, app_key);

            //System.out.println(session_key);
            TradesGetResponse rsp;
            req.setPageNo(pageNo);
            rsp = client.tradesSoldGet(req, session_key);
            logger.debug(rsp.getBody());
            //ת���Ա����ص�JSON����
            Map<String,Object>  resultMap = MyTaobaoJsonCovertUtil.json2ResultListMap(rsp.getBody(), "Trade", "taobao.trades.sold.get");
            if(resultMap==null) return null;

            total_results = (Integer)resultMap.get("total_results");
            //System.out.println("total_results:"+total_results);

            JSONArray jsonArray = (JSONArray)resultMap.get("JSONArray");
            if(jsonArray==null) return null;

            //�õ������Ա�������Ϣ�б�ÿ��������Ϣ���ܰ����������
            List<com.taobao.api.model.Trade> list = TaobaoTradeJSONConvert.convertJsonArrayToTradeList(jsonArray);
            List<Trade> tradeList = convertTaobaoTradeList(list);
            if(CollectionUtils.isNotEmpty(tradeList)){
                tradeListFromTaobao.addAll(tradeList);
            }

            retMap = new HashMap<String, Object>();
            retMap.put("tradeListFromTaobao", tradeListFromTaobao);
            retMap.put("total_results", total_results);

        } catch (TaobaoApiException e) {
            logger.error("[TaobaoUtils.getTradeListFromTaobaoByApply]�Ա��ӿڵ��÷����쳣",e);
        } catch (Exception e) {
            logger.error("[TaobaoUtils.getTradeListFromTaobaoByApply]�����쳣:"+"app_key="+app_key+"��nick="+nick,e);
        } finally{

        }
        return retMap;
	}

    /**
     * ���Ա����̶���ת��Ϊ���˶���
     * @param taobaoTradeList
     * @return
     */
    public static List<Trade> convertTaobaoTradeList(List<com.taobao.api.model.Trade> taobaoTradeList) {

        List<Trade> tradeList = new ArrayList<Trade>();// ��������

        if (CollectionUtils.isNotEmpty(taobaoTradeList)) {

            for (com.taobao.api.model.Trade taobaoTrade : taobaoTradeList) {

                Trade trade = new Trade();
                trade.setReceiver(taobaoTrade.getReceiverName());
                trade.setProvince(taobaoTrade.getReceiverState());
                trade.setCity(taobaoTrade.getReceiverCity());
                trade.setDistrict(taobaoTrade.getReceiverDistrict());
                trade.setAddress(taobaoTrade.getReceiverAddress());
                trade.setZipcode(taobaoTrade.getReceiverZip());
                /* begin add by shenzh Oct 29, 2010 ˵���� ʹ���Ա�����������*/
                try {
                    trade.setShippingAmount(Double.parseDouble(taobaoTrade.getPostFee()));
                } catch (NumberFormatException e) {
                    logger.error("�������ø�ʽת������", e);
                }
                /* end by shenzh Oct 29, 2010 */
                /* begin add by shenzh Nov 12, 2010 ˵����ȡ�Ա�����Ʒ�ܼۼ������ܼ� */
                try {
                    trade.setAmount(Double.parseDouble(taobaoTrade.getPayment()));//���ʵ���������������ã���
                    trade.setGoodsAmount(Double.parseDouble(taobaoTrade.getPayment())
                                         - Double.parseDouble(taobaoTrade.getPostFee()));//��Ʒ�ܽ��(���������ʾ�������ۿ۵��������ʷ�)
                } catch (NumberFormatException e) {
                    logger.error("����ʽת������", e);
                }
                /* end by shenzh Nov 12, 2010 */
                if (StringUtils.isNotBlank(taobaoTrade.getReceiverMobile())) {
                    trade.setMobile(taobaoTrade.getReceiverMobile());
                } else {
                    trade.setMobile(taobaoTrade.getReceiverPhone());
                }

                // ��������ת��
                trade.setCreateTime(taobaoTrade.getCreated());
                trade.setDealPayTime(taobaoTrade.getPayTime());
                trade.setDealCode(taobaoTrade.getTid());//�Ա����������
                //              trade.setTid(taobaoTrade.getId());
                //              trade.setBuyNick("�Ա�ƽ̨");
                // ���ö���֧����ʽ��delete�� �ں�������֧����ʽ��
                //trade.setExpressPayment(EnumExpressDistPayment.PAYMENT_FIRST.getKey());
                /* begin add by shenzh Nov 12, 2010 ˵�����Ա���ұ�ע */
                trade.setBuyerNote(taobaoTrade.getBuyerMessage());
                /* end by shenzh Nov 12, 2010 */
                List<com.taobao.api.model.Order> taobaoOrderList = taobaoTrade.getOrders();
                if (CollectionUtils.isNotEmpty(taobaoOrderList)) {
                    List<Order> orderList = new ArrayList<Order>();
                    for (com.taobao.api.model.Order taobaoOrder : taobaoOrderList) {
                        Order order = new Order();
                        order.setItemId(taobaoOrder.getOid());//�Ա��Ӷ������
                        order.setCode(taobaoOrder.getOuterSkuId() == null ? taobaoOrder
                            .getOuterIid() : taobaoOrder.getOuterSkuId());//���˲�Ʒ�������Ʒ���� ����Ψһȷ�����۵Ĳ�Ʒ
                        order.setGoodsNumber(taobaoOrder.getNum());
                        /* begin add by shenzh Oct 29, 2010 ˵���� */
                        /* modify by shenzh 2010-11-12 ��Ϊʵ����������Ʒ����*/
                        try {
                            double payment = Double.parseDouble(taobaoOrder.getPayment());
                            if (CollectionUtils.size(taobaoOrderList) == 1) {
                                payment -= trade.getShippingAmount();//��ֻ����һ��orderʱҪ��ȥ������
                            }
                            order.setGoodsPrice(payment / order.getGoodsNumber());
                        } catch (NumberFormatException e) {
                            logger.error("��Ʒ�۸��ʽת������", e);
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
     * ͬ�������Ա�������������Ϣ�����Ķ���״̬
     * @param app_key
     * @param app_sercet
     * @param nick
     * @param interfaceUserTrade    ����Ҫ���µ�ͬ��������Ϣ
     * @param depositoryFirst       �����ֿ��ַ��Ϣ
     * @param sellerMsg             ��������Ϣ
     *                              remark                ��ע
     *                              codeOfExpress         ������˾����
     *                              nameOfExpress         ������˾����
     *                              logisticsCode         ��������
     *                              sellerName            ������ʾ����������
     *                              sellerZip             ������ʾ�������ʱ�
     *                              sellerPhone           ������ʾ��������ϵ�绰
     * @return
     */
    public static String updateTaobaoLogisticsMsg(String app_key, String app_sercet, String nick,
                                                  InterfaceUserTrade interfaceUserTrade,
                                                  DepositoryFirst depositoryFirst,
                                                  Map<String, String> sellerMsg) {

        Map<String, String> errorCodeToChinese = new HashMap<String, String>();
        errorCodeToChinese.put("10", "���񲻿���");
        errorCodeToChinese.put("11", "����������Ȩ�޲���");
        errorCodeToChinese.put("12", "�û�Ȩ�޲���");
        errorCodeToChinese.put("15", "ִ��Զ�̷���ʱ����");

        String message = null;
        try {
            TaobaoRestClient client = new TaobaoJsonRestClient(IS_SANDBOX ? app_key = "test"
                : app_key, IS_SANDBOX ? "test" : app_sercet, IS_SANDBOX);

            String session_key = getTestSession(nick, app_key);

            TradeGetRequest req3 = new TradeGetRequest();
            req3.setFields("status");//��ѯ��ǰ����״̬��һЩ��Ҫ��������Ϣ�����ӿڵ���
            req3.setTid(interfaceUserTrade.getPaipaiTradeId());//�õ��Ա�ƽ̨�Ľ���ID

            TradeGetResponse rsp3 = client.tradeGet(req3, session_key);
            com.taobao.api.model.Trade taobaoTrade = MyTaobaoJsonCovertUtil.json2Modle(
                rsp3.getBody(), com.taobao.api.model.Trade.class, "taobao.trade.get");
            //System.out.println(taobaoTrade.getStatus());

            if (taobaoTrade == null
                || !"WAIT_SELLER_SEND_GOODS".equalsIgnoreCase(taobaoTrade.getStatus())) {
                message = "Զ�̶���[�Ա������ţ�"
                          + interfaceUserTrade.getPaipaiTradeId()
                          + ",���ض����ţ�"
                          + interfaceUserTrade.getTradeId()
                          + "]ͬ������"
                          + (taobaoTrade != null ? "�Ա��ϵĶ���״̬[" + taobaoTrade.getStatus()
                                                   + "]���ǵȴ����ҷ���" : "�޷��õ�Զ�̶���");
                logger.error(message + " "
                             + (rsp3.getSubMsg() == null ? rsp3.getMsg() : rsp3.getSubMsg()));
                return message;//���״̬��Ϊ����Ѹ���򲻽���֮��Ĳ���
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
            //���û���ã���Ĭ��Ϊ"YWSTO"���з��������ĸ���
            if (StringUtil.isBlank(expressCode)) {
                expressCode = "YWSTO";
            }
            
            req2.setTid(interfaceUserTrade.getPaipaiTradeId()); //����ID
            req2.setCompanyCode(IS_SANDBOX ? sellerMsg.get("nameOfExpress") : sellerMsg
                .get("codeOfExpress")); //����ͨ��taobao.logisticcompanies.get��ѯ������˾����
            req2.setMemo(sellerMsg.get("remark")); //���ұ�ע
            req2.setOutSid(sellerMsg.get("logisticsCode")); //�˵��� ����һ��������˾���˵�����
            req2.setOrderType("delivery_needed");
            /*������д������Ϣ*/
            String address = IS_SANDBOX ? depositoryFirst.getAddress() + " "
                : "" + depositoryFirst.getParticularAddress();
            req2.setSellerAddress(address); //��ʽ���������ҵ�ַ(��ϸ��ַ)ʡ���С�������Ҫ�ṩ
            req2.setSellerAreaId(depositoryFirst.getRegionCode()); //�������ڵع��ҹ�����׼������ͨ��taobao.areas.get��ȡ
            //req2.setSellerMobile(taobaoTrade.getSellerMobile());
            req2.setSellerName(sellerMsg.get("sellerName"));
            req2.setSellerPhone(sellerMsg.get("sellerPhone"));
            req2.setSellerZip(sellerMsg.get("sellerZip"));

            DeliverySendResponse response = client.deliverySend(req2, session_key);
            //            System.out.println(response.getBody());
            //            System.out.println("�����Ƿ�ɹ� : "+response.isDeliverSuccess()); //ע��:ͨ��SDK���淢��API�Ƿ�ɹ��ķ���Ϊ isDeliverSucce
            if (response.getErrorCode() == null) {
                logger.info("�Ա�������" + interfaceUserTrade.getPaipaiTradeId() + " ������Ϣ����Ƿ���ȷ: "
                            + response.isDeliverSuccess());
                return message;
            } else {
                message = "Զ�̶���[�Ա������ţ�"
                          + interfaceUserTrade.getPaipaiTradeId()
                          + ",���ض����ţ�"
                          + interfaceUserTrade.getTradeId()
                          + "]ͬ������"
                          + (response.getSubMsg() == null ? (errorCodeToChinese.get(response
                              .getErrorCode()) == null ? response.getMsg() : errorCodeToChinese
                              .get(response.getErrorCode()) + "!��Ҫ��¼�Ա���ɱ��ζ���״̬ͬ��������") : response
                              .getSubMsg());
                logger.error(message);
                return message;
            }
        } catch (TaobaoApiException e) {
            logger.error(
                "[TaobaoUtils.updateTaobaoLogisticsMsg]" + "Զ�̶���[�Ա������ţ�"
                        + interfaceUserTrade.getPaipaiTradeId() + ",���ض����ţ�"
                        + interfaceUserTrade.getTradeId() + "]ͬ������" + "�Ա��ӿڵ��÷����쳣", e);
            message = "�Ա��ӿڵ��÷����쳣������״̬δͬ�����Ա�";
        } finally {

        }
        return message;
    }

    /**
     *
     * ���ܣ�������ұ�ע��Ϣ<br>
     *
     * @param trade ������Ϣ��ȱ����ұ�ע��Ϣ
     * @param userTrade ���Ա�����ID
     * @param app_key
     * @param app_sercet
     * @param nick
     * @return trade ������Ϣ��������ұ�ע��Ϣ
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
            req3.setFields("buyer_message");//��ѯ��ǰ����״̬��һЩ��Ҫ��������Ϣ�����ӿڵ���
            req3.setTid(userTrade.getPaipaiTradeId());//�õ��Ա�ƽ̨�Ľ���ID

            TradeGetResponse rsp3 = client.tradeGet(req3, session_key);
            com.taobao.api.model.Trade taobaoTrade = MyTaobaoJsonCovertUtil.json2Modle(
                rsp3.getBody(), com.taobao.api.model.Trade.class, "taobao.trade.get");
            if (rsp3.getErrorCode() == null) {
                trade.setBuyerNote(taobaoTrade.getBuyerMessage());
            } else {
                String message = "Զ�̶���[�Ա������ţ�" + userTrade.getPaipaiTradeId() + "]��ȡ���������Ϣ����"
                                 + (rsp3.getSubMsg() == null ? rsp3.getMsg() : rsp3.getSubMsg());
                logger.error(message);
            }
        } catch (TaobaoApiException e) {
            logger.error("[TaobaoUtils.addBuyerNoteToTrade]�Ա��ӿڵ��÷����쳣��δȡ���������", e);
        } finally {

        }
        return trade;
    }

    /**
     * ���ܣ����ӷ�Ʊ̧ͷ��ͬ����ȡ
     *
     * @param trade ������Ϣ
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
            req4.setTid(userTrade.getPaipaiTradeId());//�õ��Ա�ƽ̨�Ľ���ID
            TradeGetResponse rsp4 = client.tradeFullInfoGet(req4, session_key);
            com.taobao.api.model.Trade taobaoTrade = MyTaobaoJsonCovertUtil.json2Modle(
                rsp4.getBody(), com.taobao.api.model.Trade.class, "taobao.trade.fullinfo.get");
            if (rsp4.getErrorCode() == null) {
                trade.setInvoiceName(taobaoTrade.getInvoiceName());
            } else {
                String message = "Զ�̶���[�Ա������ţ�" + userTrade.getPaipaiTradeId() + "]��ȡ��Ʊ̧ͷ����"
                                 + (rsp4.getSubMsg() == null ? rsp4.getMsg() : rsp4.getSubMsg());
                logger.error(message);
            }
        } catch (TaobaoApiException e) {
            logger.error("[TaobaoUtils.addInvoiceName]�Ա���ڵ��÷����쳣��δȡ�÷�Ʊ̧ͷ", e);
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
            logger.error("xml�����쳣..." + e.getMessage());
        }
        return doc.getRootElement().getName();
    }

    public static Map<String, String> getErrorCode(String xml) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            logger.error("xml�����쳣..." + e.getMessage());
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
