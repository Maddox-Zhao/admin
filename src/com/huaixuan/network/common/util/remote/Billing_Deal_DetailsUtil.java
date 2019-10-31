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
    // �����Ѹ���״̬
    public static final String DS_WAIT_SELLER_DELIVERY           = "DS_WAIT_SELLER_DELIVERY";

    public static final String DATE_FOMAT_STR                    = "yyyy-MM-dd HH:mm:ss";

    // ���Ҳ��Ҷ�������ӿڵ�ַ
    public static final String DEAL_DETAIL_URL                   = "/deal/getDealDetail.xhtml";
    // ���Ҳ��Ҷ����б� �ӿڵ�ַ
    public static final String DEAL_LIST_URL                     = "/deal/sellerSearchDealList.xhtml";
    // ���ұ�Ƕ��������
    public static final String DEAL_PREPARING_URL                = "/deal/sellerSignDealPreparing.xhtml";
    // ���ұ�Ƕ�������
    public static final String DEAL_CONSIGN_URL                  = "/deal/sellerConsignDealItem.xhtml";
    // �����ӳ������ջ�ʱ��
    public static final String DELAY_CONSIGNMENT_TIME_URL        = "/deal/sellerDelayConsignmentTime.xhtml";
    // ������ӻ����޸Ķ�����ע
    public static final String MODIFY_SELLER_NOTE_URL            = "/deal/modifySellerNote.xhtml";
    // ����ȡ������
    public static final String SELLER_CANCEL_DEAL_URL            = "/deal/sellerCancelDeal.xhtml";
    // ���ұ�Ƕ���ȱ��
    public static final String SELLER_SIGN_DEAL_OUT_OF_STOCK_URL = "/deal/sellerSignDealOutOfStock.xhtml";

    /**
     * ���Ҳ��Ҷ�������ӿڵ�ַ
     * 
     * @param sellerUin  ����QQ��
     * @param dealCode   ��������
     * @param listItem   �Ƿ��г���Ʒ:  0��Ĭ�ϣ������г����������Ʒ
     * @param sign       ���з��ļ��ֶ�name��value��У��ֵ����ֹ��Ҫ���ݱ���;�۸�
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
     * ���Ҳ��Ҷ����б�ӿڵ�ַ
     * 
     * @param pureData
     *            number 0,1 Ĭ��ֵΪ0��
     * @param timeType
     *            string ���ַ�����Ĭ�ϣ� ʱ�����͡� ���ַ�����Ĭ�ϣ�:��ʾ��ʹ��ʱ��ɸѡ��
     *            CREATE:��ʾtimeBegin��timeEnd���µ�ʱ��
     *            UPDATE:��ʾtimeBegin��timeEnd�Ƕ���������ʱ��
     *            ���timeType������ΪCREATE��UPDATEʱ��timeBegin��timeEnd�ֶα��븳ֵ
     * @param timeBegin
     *            time ��ʽ: yyyy-MM-dd HH:mm:ss ��ʼʱ��
     *            ����ֶ�timeType������ΪCREATE��UPDATEʱ�����ֶα��븳ֵ 
     * @param timeEnd 
     *            time ��ʽ:yyyy-MM-dd HH:mm:ss ����ʱ��
     *            ����ֶ�timeType������ΪCREATE��UPDATEʱ�����ֶα��븳ֵ
     * @param dealState
     *            string Ĭ��Ϊ���ַ��� ����״̬:dealState
     * @param itemCode
     *            string ��Ʒ���� ������Ҫ��Ҫ��Ҫ��д�����е���Ʒ���ձ��룬��Ҫ��д��Ʒ����
     *            4A42403200000001004A39AB068030E3 �����е���Ʒ���ձ���
     *            4A42403200000000004A39AB068030E3 ��Ʒ����
     *            ��Ʒ���ձ��룺������µ�������̼��޸�����Ʒ��Ϣ����ô�����в鿴����Ʒ���룬
     *            �ǿ��ձ��룬������Ʒ������м��ĳһλ�ۼ�һ,�м��00000001�м���8λ�ǿ��ձ������кţ������ۼӵ�
     * @param itemNameKey
     *            string ��Ʒ���ƹؼ��� Ŀ�궩������Ʒ���⣬��ʾ���Һ͸���Ʒ������صĶ�����
     *            �����������Ϊ��ɭ�֡�����ô��Ʒ�����д��С�ɭ�֡��ʵĶ����б�ͻᱻ���ҳ���
     * @param listItem
     *            number 0��Ĭ�ϣ� �Ƿ��г���Ʒ 0��Ĭ�ϣ������г����������Ʒ ��0���г����������Ʒ���ٶȱȽ�����
     * @param dealCode
     *            string �������� �ɲ�ѯ������������Ϣ
     * @param orderField
     *            string �����׼�� UPDATE_TIME��Ĭ�ϣ�����������ʱ������ CREATE_TIME�����µ�ʱ������
     *            ��ʱ��֧�֣�ָ��timeTypeʱͬʱ�ö�Ӧʱ���ֶ���Ϊ�����ֶΣ���ָ��timeTypeʱû������
     * @param orderDesc
     *            number ��0��Ĭ�ϣ� �Ƿ��������� ��0��Ĭ�ϣ����������� 0��˳������
     * @param pageIndex
     *            number 1��Ĭ�ϣ� ҳ����������1��ʼ
     * @param pageSize
     *            number Ĭ��Ϊ10 ҳ��С ÿҳ���صĶ�����¼������Ҫ����20��Ĭ��Ϊ10
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
     * ���ұ�Ƕ��������
     * 
     * @param sellerUin  ����QQ��
     * @param dealCode string  ��������
     * @return
     */
    public static String CreateDealPreparingUrl(TreeMap<String, String> signParams, String dealCode) {
        Contants.setPublicParams(signParams);
        signParams.put("dealCode", dealCode);
        return Contants.createNewUrl(DEAL_PREPARING_URL, signParams);
    }

    /**
     * ���ұ�Ƕ�������
     * 
     * @param dealCode
     *            ����� string ��������
     * @param logisticsName
     *            ����� string 256���ֽ� ������˾���� �Զ���ķ�����˾����
     * @param logisticsDesc
     *            string 0-100�ֽ� ������д�ķ���˵��
     * @param logisticsCode
     *            ����� string 20�ֽ� ��������
     * @param arriveDays
     *            ����� number [3,5,7,10] Ԥ�Ƽ���󵽻�
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
     * �����ӳ������ջ�ʱ��
     * 
     * @param sellerUin  ����QQ��
     * @param dealCode string  ��������
     * @param days number 3��5��7��10 �ӳ��ջ������� 
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
     * ������ӻ����޸Ķ�����ע
     * 
     * @param sellerUin  ����QQ��
     * @param dealCode string  ��������
     * @param dealNote string  ������ע���� ������254���ַ�  
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
     * ������ӻ����޸Ķ�����ע
     * 
     * @param sellerUin
     *            ����QQ��
     * @param dealCode
     *            string ��������
     * @param closeReason
     *            number ��д��ȡ��˵�� 0���޷���ϵ����� 1��������Ļ������� 2������޳�����ɽ��� 3����ͨ���������»��
     *            4����ͨ��ͬ�Ǽ��潻�� 5����ͨ����������� 6����ͨ����������ֱ�ӻ�� 7���Ѿ�ȱ���޷�����
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
    *  ���ұ�Ƕ���ȱ��
    * 
    * @param sellerUin
    *            ����QQ��
    * @param dealCode
    *            string ��������
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
     * ��ѯ ȷ�Ϸ��� ���ؽ�������󷵻ش������飬����ȷ���ض�������
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
            result.setMessage("��������");
            return result;
        }
    }

    /**
     * ���������б��XML�ĵ�
     * @param ItemUrl
     * @return Map errorCode:������룻errorMessage:������Ϣ��tradeList:�����б�userTradeList:ͬ��������Ϣ�б�
     */

    @SuppressWarnings("unchecked")
    public static Map parseInterfaceTradeListXml(String ItemUrl) {
        Map resultMap = new HashMap();
        List<Trade> tradeList = new ArrayList<Trade>();// ��������
        try {
            SAXReader reader = new SAXReader();
            String[] str = { "yyyy-MM-dd HH:mm:ss" };
            Document doc = reader.read(new URL(ItemUrl).openStream());
            Element root = doc.getRootElement();
            String errorCode = root.elementText("errorCode");
            resultMap.put("errorCode", errorCode);// �������
            if (!errorCode.equalsIgnoreCase("0")) {
                resultMap.put("errorMessage", EnumBilling_Paipai_Detail.No_Permission.getValue(root
                    .elementText("errorMessage")));// ������Ϣ
                return resultMap;
            } else {
                Iterator dealList = root.elementIterator("dealList");// �����б�
                while (dealList.hasNext()) {
                    Element dealInfo = (Element) dealList.next();
                    Iterator deal = dealInfo.elementIterator("dealInfo");// ������Ϣ
                    while (deal.hasNext()) {
                        Element ele = (Element) deal.next();
                        Trade trade = new Trade();

                        // ������Ϣ
                        trade.setBuyerNote(ele.elementText("buyerRemark"));
                        trade.setAddress(ele.elementText("receiverAddress"));
                        trade.setMobile(ele.elementText("receiverMobile"));
                        trade.setReceiver(ele.elementText("receiverName"));
                        trade.setZipcode(ele.elementText("receiverPostcode"));
                        // �ӿڶ�������
                        trade.setDealCode(ele.elementText("dealCode"));
                        if (StringUtils.isNotBlank(ele.elementText("createTime"))) {
                            trade.setCreateTime(DateUtils.parseDate(ele.elementText("createTime"),
                                str));
                        }
                        if (StringUtils.isNotBlank(ele.elementText("payTime"))) {
                            trade.setDealPayTime(DateUtils.parseDate(ele.elementText("payTime"),
                                str));
                        }

                        Element element = ele.element("itemList");// ��Ʒ�б�
                        List<Element> itemLocalCodeList = element.elements("itemLocalCode");// �̼ұ��뼯��
                        List<Element> stockLocalCodeList = element.elements("stockLocalCode");// ��Ʒ���뼯��
                        List<Element> itemDealCountList = element.elements("itemDealCount");// ������������
                        List<Element> itemDealPriceList = element.elements("itemDealPrice");// ����µ�ʱ����Ʒ�۸�
                        List<Element> itemAdjustPriceList = element.elements("itemAdjustPrice");// �����ĵ����۸�:����Ϊ�����Ӽ�,����Ϊ��������
                        List<Element> itemDiscountFeeList = element.elements("itemDiscountFee");// ������Ʒ�ĺ��ֵ���ۿ��Żݼ�
                        List<Order> orderList = new ArrayList<Order>();
                        for (int i = 0; i < stockLocalCodeList.size(); i++) {
                            Element element1 = (Element) stockLocalCodeList.get(i);
                            Element element2 = (Element) itemDealCountList.get(i);
                            Element element3 = (Element) itemLocalCodeList.get(i);
                            Element element4 = (Element) itemDealPriceList.get(i);
                            Element element5 = (Element) itemAdjustPriceList.get(i);
                            Element element6 = (Element) itemDiscountFeeList.get(i);
                            Order order = new Order();
                            
                            // ��������Ʒ��Ϣ
                            order.setCode(StringUtils.isBlank(element1.getText()) ? element3
                                .getText() : element1.getText());
                            if (StringUtils.isNotBlank(element2.getText())) {
                                order.setGoodsNumber(Long.parseLong(element2.getText()));
                                //ץȡ���Ķ�������Ʒ�۸�
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
            resultMap.put("errorMessage", "���ز�������");
            return resultMap;
        }

    }

    /**
     * ���������޸Ķ���״̬������У��ѷ�����XML
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
            resultMap.put("errorCode", errorCode);// �������
            if (errorCode.equalsIgnoreCase("1")) {
                resultMap.put("errorMessage", EnumBilling_Paipai_Detail.No_Permission.getValue(root
                    .elementText("errorMessage")));// ������Ϣ
            } else {
                resultMap.put("dealCode",
                    EnumBilling_Paipai_Detail.No_Permission.getValue(root.elementText("dealCode")));// �������
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            resultMap.put("errorCode", "1");
            resultMap.put("errorMessage", "���ز�������");
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
        //      String ItemUrl = CreateDealConsignUrl(DEAL_CONSIGN_URL,"855008924-20100804-359568958","��ͨ���","�ѷ���","19291828131",2);
        //      String ItemUrl = CreateDelayConsignmentTimeUrl(DELAY_CONSIGNMENT_TIME_URL,"855008924-20100804-359568958",5);
        //      String ItemUrl = CreateModifySellerNoteUrl(MODIFY_SELLER_NOTE_URL,"855008924-20100804-359568958","�޸Ķ�����ע��Ϣ�ӿڲ���");
        //      String ItemUrl = CreateSellerCancelDealUrl(SELLER_CANCEL_DEAL_URL,"855008924-20100804-359568958",0);
        String ItemUrl = CreateSellerSignDealOutOfStockUrl(SELLER_SIGN_DEAL_OUT_OF_STOCK_URL,
            "855008924-20100804-359568958");
        System.out.println(ItemUrl);
        Result<String> result = parseDeliveryConfirmationURL(ItemUrl);
        System.out.println(result.getMessage());
    }
}
