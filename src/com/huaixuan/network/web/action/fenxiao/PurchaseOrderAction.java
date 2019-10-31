package com.huaixuan.network.web.action.fenxiao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.express.ExpressDist;
import com.huaixuan.network.biz.domain.purchase.Purchaselifecycle;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.service.express.ExpressDistManager;
import com.huaixuan.network.biz.service.express.RegionManager;
import com.huaixuan.network.biz.service.purchase.PurchaseOrderService;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.trade.OrderManager;
import com.huaixuan.network.biz.service.trade.TradeManager;
import com.huaixuan.network.common.util.DoubleUtil;
import com.huaixuan.network.web.action.BaseAction;

/**
 * �Ա�����ɹ�����ѯAction.
 * 
 * @author ����
 */
@Controller
public class PurchaseOrderAction extends BaseAction {

	private static Log logger = LogFactory.getLog(PurchaseOrderAction.class);

	@Autowired
	private TradeManager tradeManager;

	// private GoodsInstanceManager goodsInstanceManager;
	//
	// private StorageManager storageManager;
	@Autowired
	private DepositoryFirstManager depositoryFirstManager;

	@Autowired
	private RegionManager regionManager;
	@Autowired
	private OrderManager orderManager;

	@Autowired
	private ExpressDistManager expressDistManager;
	
	@Autowired
	private PurchaseOrderService purchaseOrderService;

	// private String FENXIAO_NO_STOCK_ORDER = "fenxiao_no_stock_order";
	// private String FENXIAO_NO_PRODUCT_ORDER = "fenxiao_no_product_order";
	//
	// private Map<String, String> enumExpressDistPaymentMap = EnumExpressDistPayment.toMap();
	// private Map<String, String> enumTradeStatusMap = EnumTradeStatus.toMap();
	// private Map<Long, String> depositoryNameMap = new HashMap<Long, String>();

	// public String showPurchaseOrder(){
	// return SUCCESS;
	// }
	//
	// public String showNoStockProductOrder() {
	//
	// // �����Ա��ӿڲ�ѯ�ɹ�����Ϣ
	// FenxiaoOrders fenxiaoOrders = TaobaoFenXiaoUtils.getFenxiaoOrders(); //getFenxiaoOrders(apply.getParamTwo(),
	// apply.getParamThree());
	// List<Trade> tradeList = tradeManager.setTradeAmount(TaobaoFenXiaoUtils.getTradeList(fenxiaoOrders));
	//
	// // ���˿��Ϊ��Ķ���
	// if(CollectionUtils.isNotEmpty(tradeList)){
	// // ���洦��������
	// List<Trade> tradeResaultList = new ArrayList<Trade>();
	// for(Trade trade : tradeList){
	// List<Order> orderList = trade.getOrderList();
	// if(CollectionUtils.isNotEmpty(orderList)){
	// boolean noStock = false;
	// boolean noProduct = false;
	// // ��ǰ������Ʒ�в�Ϊ�̳ǲ�Ʒ�Ĳ����й���
	// for(Order order:orderList){
	// GoodsInstance goodsIns = goodsInstanceManager.getInstance(NumberUtils.toLong(order.getCode()));
	// //.getInstanceByCode(order.getCode());
	// if(goodsIns!=null){
	// Long existNum = storageManager.sumStorageByGoodsInstanceId(goodsIns.getId(), 62l, "exist");
	// if(existNum < order.getGoodsNumber()){
	// noStock = true;
	// }
	// }else{
	// noProduct = true;
	// }
	// }
	// if(StringUtils.equals(pageFlag, FENXIAO_NO_STOCK_ORDER) && noStock){
	// tradeResaultList.add(trade);
	// }
	// if(StringUtils.equals(pageFlag, FENXIAO_NO_PRODUCT_ORDER) && noProduct){
	// tradeResaultList.add(trade);
	// }
	// }
	// }
	//
	// this.tradeList = tradeResaultList;
	// }
	//
	// // ��ѯһ���ֿ�
	// List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstList();
	// for (DepositoryFirst depositoryFirst : depositoryFirstList) {
	// depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
	// }
	//
	// return SUCCESS;
	// }

	/**
	 * ���ڶ�̬DWR���֧����ʽ�͵�ַ��ʾ������Ϣ
	 */
	@RequestMapping("/igc/disExpressSel")
	@ResponseBody
	public Object disExpressSel(@RequestParam(value = "regionCodeEndSel", required = false) String regionCodeEndSel,
			@RequestParam(value = "paymentTempSel", required = false) String paymentTempSel,
			@RequestParam(value = "goodsWeightTotal", required = false) String goodsWeightTotal,
			@RequestParam(value = "depositoryFirstId", required = false) String depositoryFirstId,
			@RequestParam(value = "tradeId", required = false) String tradeId, AdminAgent agent) {
		// ĿǰĬ������������ʼ��Ϊ�㽭����330782) added by chenyan 2009/09/07
		String regionCodeStart = "330782";
		if (depositoryFirstId != null) {// ȡ������ʼ��
			Long firstId = Long.parseLong(depositoryFirstId);
			DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(firstId);
			if (null != depositoryFirst) {
				regionCodeStart = depositoryFirst.getRegionCode();
			}
		}

		Trade trade = tradeManager.getTrade(NumberUtils.toLong(tradeId));
		List<Order> orderList = orderManager.getOrdersByTid(trade.getTid());

		double weight = 0; // ��Ʒ������
		for (Order order : orderList) {
			weight += order.getGoodWeight() * order.getGoodsNumber();
		}

		List<ExpressDist> expressDistList = expressDistManager.listExpressDistByRegionCodeEnd(regionCodeStart,
				regionCodeEndSel, paymentTempSel, null);

		if (CollectionUtils.isNotEmpty(expressDistList)) {
			for (ExpressDist tmp : expressDistList) {
				if (tmp.getWeightFirst() < weight) {
					double newWeight = (weight - tmp.getWeightFirst()) / tmp.getWeightNext();// �м�������
					// ����С�����أ���+1
					int newWeightInt = (int) newWeight;
					if (newWeight > newWeightInt) {
						newWeightInt = newWeightInt + 1;
					}
					double newWeightFirst = tmp.getWeightFirstMoney() + newWeightInt * tmp.getWeightNextMoney();
					tmp.setWeightMoney(DoubleUtil.round(newWeightFirst, 1));// ��ȷ����
				} else {
					double WeightMoney = tmp.getWeightFirstMoney();
					tmp.setWeightMoney(DoubleUtil.round(WeightMoney, 1));
				}
			}

			return expressDistList;
		} else {
			Map<String, String> errorMsg = new HashMap<String, String>();
			errorMsg.put(
					"msg",
					"ϵͳ�в�����" + regionManager.getCodeName(trade.getProvince())
							+ regionManager.getCodeName(trade.getCity())
							+ regionManager.getCodeName(trade.getDistrict()) + trade.getAddress() + "���Ӧ������!");
			errorMsg.put("error", "error");

			return errorMsg;
		}
	}

	/**
	 * ���涩����������Ϣ
	 */
	@RequestMapping("/igc/saveExpress")
	@ResponseBody
	public Object saveExpress(@RequestParam("tradeId") String tradeId,
			@RequestParam(value = "expressId", required = false) String expressId) {
		Map<String, String> message = new HashMap<String, String>();

		try {
			Trade trade = tradeManager.getTrade(NumberUtils.toLong(tradeId));
			trade.setExpressId(NumberUtils.toLong(expressId));
			tradeManager.editTrade(trade);
		} catch (Exception e) {
			message.put("msg", "����������Ϣ����" + e.getMessage());
			message.put("error", "error");
		}

		message.put("msg", "����������Ϣ�ɹ�");
		message.put("success", "success");
		return message;
	}

	/**
	 * ��ѯ������������Ϣ
	 */
	@RequestMapping("/trade/getExpress")
	@ResponseBody
	public Object getExpress(@RequestParam(value = "tradeId") String tradeId) {
		Map<String, String> message = new HashMap<String, String>();

		if (StringUtils.isNotBlank(tradeId)) {
			try {
				Trade trade = tradeManager.getTrade(NumberUtils.toLong(tradeId));
				if (trade != null && trade.getExpressId() == null) {
					message.put("msg", "����ѡ���������ͺ�,�ٽ����������!");
					message.put("error", "error");
				} else {
					message.put("success", "success");
				}
			} catch (Exception e) {
				logger.error("", e);
				message.put("msg", "��������!");
				message.put("error", "error");
			}
		}
		return message;
	}
	
	
	
	
	@RequestMapping("/purchase/getPurchaseLifeCyleByStatus")
	@ResponseBody
	public Object getPurchaseLifeCyle(HttpServletRequest request)
	{
		String status = request.getParameter("status");
		if(null == status || "".equals(status)) status = "14";//默认查询已发货 待确认
		
		Purchaselifecycle   purchaselifecycle = new Purchaselifecycle();
		purchaselifecycle.setIdStatus(Long.valueOf(status));
		List<Purchaselifecycle> purchaselifecycleList = purchaseOrderService.queryPurchaselifecycle(purchaselifecycle);
		return purchaselifecycleList;
	}

}
