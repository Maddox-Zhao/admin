package com.huaixuan.network.web.action.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.express.Castweight;
import com.huaixuan.network.biz.domain.express.Express;
import com.huaixuan.network.biz.domain.express.ExpressDist;
import com.huaixuan.network.biz.domain.express.JsonArea;
import com.huaixuan.network.biz.domain.express.Region;
import com.huaixuan.network.biz.domain.goods.AvailableStock;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.goods.PromContext;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.domain.trade.TradeCode;
import com.huaixuan.network.biz.domain.trade.TradeList;
import com.huaixuan.network.biz.domain.user.User;
import com.huaixuan.network.biz.domain.user.UserAddress;
import com.huaixuan.network.biz.domain.user.UserInfo;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumExpressDistPayment;
import com.huaixuan.network.biz.enums.EnumPromationModeCode;
import com.huaixuan.network.biz.service.express.CastweightManager;
import com.huaixuan.network.biz.service.express.ExpressDistManager;
import com.huaixuan.network.biz.service.express.ExpressManager;
import com.huaixuan.network.biz.service.express.RegionManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.goods.PromationManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.trade.TradeManager;
import com.huaixuan.network.biz.service.user.UserAddressManager;
import com.huaixuan.network.biz.service.user.UserInfoManager;
import com.huaixuan.network.biz.service.user.UserManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.DoubleUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
@RequestMapping(value ="/user")
public class UsersManageAction extends BaseAction {

	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private RegionManager regionManager;
	
	@Autowired
	private DepositoryFirstManager depositoryFirstManager;
	
	@Autowired
	private UserInfoManager userInfoManager;
	
	@Autowired
	private UserAddressManager userAddressManager;
	
	@Autowired
	private ExpressDistManager expressDistManager;
	
	@Autowired
	private TradeManager tradeManager;
	
	@Autowired
	private GoodsInstanceManager goodsInstanceManager;
	
	@Autowired
	private GoodsManager goodsManager;
	
	@Autowired
	private PromationManager promationManager;
	
	@Autowired
	private ExpressManager expressManager;
	
	@Autowired
	private CastweightManager castweightManager;
	

    //��̨�¶���
	@AdminAccess({EnumAdminPermission.A_BACK_ORDER_USER})
	@RequestMapping("/toBackOrderPage")
    public String toBackOrderPage(@RequestParam("userId") String userId,Model model) {
		Map<UserAddress, String> userAddressMap = new HashMap<UserAddress, String>();
        List<Region> provinceList = regionManager.getRegionByType(2);
        List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstList();
        User user = userManager.getUserById(Long.parseLong(userId));
        UserInfo loginUserInfo = userInfoManager.getUserInfoByUserId(user.getId());
        List<UserAddress> userAddressList = userAddressManager.getTradeAddressByUserId(Long.parseLong(userId));
        // ȡ���ջ�����Ϣ�еĵ�ַ����
        for (UserAddress userAddress : userAddressList) {
            String address = this.regionManager.getAddressInfo(userAddress.getProvince(),
                userAddress.getCity(), userAddress.getDistrict());
            address += userAddress.getAddress();
            userAddressMap.put(userAddress, address);
        }
        model.addAttribute("provinceList", provinceList);
        model.addAttribute("depositoryFirstList", depositoryFirstList);
        model.addAttribute("user", user);
        model.addAttribute("loginUserInfo", loginUserInfo);
        model.addAttribute("userAddressList", userAddressList);
        model.addAttribute("userAddressMap", userAddressMap);
        return "/user/backOrderSelect";
    }
	
    /**
     * �û�����Ĭ���ջ���ַ
     */
	@RequestMapping("/tobeDefaultbackAddress")
    public @ResponseBody UserInfo tobeDefaultbackAddress(@RequestParam("param1") String param1,@RequestParam("param2") String param2) {
        Long addressId = Long.parseLong(param1);
        UserInfo loginUserInfo = userInfoManager.getUserInfoByUserId(Long.parseLong(param2));
        if (null != loginUserInfo) {
            loginUserInfo.setTradeAddressId(addressId);
            userInfoManager.editUserInfo(loginUserInfo);
            return loginUserInfo;
        } else {
            return loginUserInfo;
        }

    }
	
	/**
	 * ��̨���ʽ����
	 *
	 * @return
	 */
	@RequestMapping("/disPaymentSelBack")
	public @ResponseBody TradeCode disPaymentSelBack(@RequestParam("regionCodeEndTemp") String regionCodeEndTemp,@RequestParam("userId") String userId,
			@RequestParam("goodsPriceAmount") String goodsPriceAmount) {
		String paymentUrlBuffFinish = "";
		TradeCode tradecode = new TradeCode();
		if (expressDistManager.getCountByRegionCodeEnd(regionCodeEndTemp,
				EnumExpressDistPayment.PAYMENT_FIRST.getKey()) > 0) {
			tradecode.setPayBybank("true");
			tradecode.setRegionCodeEndTemp(regionCodeEndTemp);
		}
		User user = this.userManager.getUser(Long.parseLong(userId));
		Date now = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String nowstr = df.format(now);
		Date nowdate = DateUtil.strToDate(nowstr, "yyyy-MM-dd");
		Date periodStart = user.getGmtPeriodPayStart();
		Date periodEnd = user.getGmtPeriodPayEnd();
		if (user.getIsPeriodPay().equals("y") && periodStart != null
				&& periodEnd != null) {
			if ((nowdate.after(periodStart) || nowdate.equals(periodStart))
					&& (nowdate.before(periodEnd) || nowdate.equals(periodEnd))) {
				double maxPeriodMoney = 0;// ��ǰ��������󶩵����
				double nowPeriodMoney = 0;// ��ǰ������δ���㶩�����
				double todayPeriodMoney = 0;// ������δ���㶩�����
				double tradePeriodMoney = 0;// �����������
				maxPeriodMoney = user.getPeriodAmountMax();
				nowPeriodMoney = user.getPeriodAmountNow();
				Map parMap = new HashMap();
				parMap.put("userId", user.getId());
				parMap.put("gmtCreateStart", nowstr);
				parMap.put("gmtCreateEnd", nowstr);
				todayPeriodMoney = tradeManager.getTodayPeriodMoney(parMap);
				if(StringUtil.isNotBlank(goodsPriceAmount) && StringUtil.isNotEmpty(goodsPriceAmount)){
					tradePeriodMoney = Double.parseDouble(goodsPriceAmount);
				}

				if (DoubleUtil.round(nowPeriodMoney + todayPeriodMoney
						+ tradePeriodMoney, 2) < maxPeriodMoney) {
					tradecode.setPayByPeriod("true");
					tradecode.setRegionCodeEndTemp(regionCodeEndTemp);
				}
			}
		}
		return tradecode;

	}
	
	@RequestMapping("/validatenum")
    public @ResponseBody String validatenum(@RequestParam("param1") String num,@RequestParam("param2") String goodsInstanceId,
    		@RequestParam("param3") String depFirstId) {
		
		String message = null;
        AvailableStock availableStock = goodsInstanceManager.getAvailableStock(Long.parseLong(goodsInstanceId), Long.parseLong(depFirstId));
        //added by fangqing 20101104 ������СԤ��
        GoodsInstance goodsInstance = goodsInstanceManager.getInstance(Long.parseLong(goodsInstanceId));
        long minNum = 0; //��СԤ��
        long buyNum = 0; // ������
        long availableNum  = 0; //���Կ��
        if( goodsInstance!= null && null!= goodsInstance.getMinNum()){
        	minNum  = goodsInstance.getMinNum();
        }
        if(num!=null){
        	buyNum = Long.parseLong(num);
        }
        if(availableStock!=null){
        	availableNum = availableStock.getGoodsNumber();
        }
        if (null == availableStock || availableStock.getGoodsNumber() == null) {
            message = "nonum";
        } else if (availableStock.getGoodsNumber() < Long.parseLong(num)) {
            message = "overnum";
        } else if( minNum>0 &&(availableNum-buyNum)<minNum){
        	message = "warming";
        }
        else {
            message = "success";
        }
        return message;
    }
	
    /**
     * �޸ļ۸��¼ zhangwy
     * @return
     */
	@RequestMapping("/priceRecord")
    public @ResponseBody String priceRecord(@RequestParam("param1") String goodsInstaceId,@RequestParam("param2") String price, AdminAgent adminAgent){

		String message = null;
    	GoodsInstance gi = this.goodsInstanceManager.getInstance(Long.parseLong(goodsInstaceId));
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = df.format(now);
    	message = adminAgent.getUsername() + "��" + dateStr + "�޸���Ʒ" + gi.getInstanceName() + "����Ϊ��" + price + "Ԫ��";
    	return message;
    }
	
	@RequestMapping("/verifyallnum")
    public @ResponseBody String verifyallnum(@RequestParam("goodsInstanceIdArray") String goodsInstanceIdArray,@RequestParam("numsArray") String numsArray,@RequestParam("zpgoodsInstanceIdArray") String zpgoodsInstanceIdArray,
    		@RequestParam("zpnumsArray") String zpnumsArray,@RequestParam("depFirstId") String depFirstId) {

		String[] goodInstanceIds = null;
		if(StringUtil.isNotBlank(goodsInstanceIdArray) && StringUtil.isNotEmpty(goodsInstanceIdArray)){
			goodInstanceIds = goodsInstanceIdArray.split(",");
		}
		String[] nums = null;
		if(StringUtil.isNotBlank(numsArray) && StringUtil.isNotEmpty(numsArray)){
			nums = numsArray.split(",");
		}
		String[] zpgoodInstanceIds = null;
		if(StringUtil.isNotBlank(zpgoodsInstanceIdArray) && StringUtil.isNotEmpty(zpgoodsInstanceIdArray)){
			zpgoodInstanceIds = zpgoodsInstanceIdArray.split(",");
		}
		String[] zpnums = null;
		if(StringUtil.isNotBlank(zpnumsArray) && StringUtil.isNotEmpty(zpnumsArray)){
			zpnums = zpnumsArray.split(",");
		}
		
		String message = null;
        //�ظ��ж�
        boolean goodsSame = false;
        boolean zpgoodsSame = false;
        if(goodInstanceIds!=null){
        	 for (int i = 0; i < goodInstanceIds.length; i++) {
        		 for(int j = i+1; j< goodInstanceIds.length;j++){
        			 if(Long.parseLong(goodInstanceIds[i]) == Long.parseLong(goodInstanceIds[j])){
        				 goodsSame = true;
        				 break;
        			 }
        		 }
        		 if(goodsSame){
        			 break;
        		 }
        	 }
        }

        if(goodsSame){
        	message = "goodsSame";
        	return message;
        }

        if(zpgoodInstanceIds!=null){
       	 for (int i = 0; i < zpgoodInstanceIds.length; i++) {
       		 for(int j = i+1; j< zpgoodInstanceIds.length;j++){
       			 if(Long.parseLong(zpgoodInstanceIds[i]) == Long.parseLong(zpgoodInstanceIds[j])){
       				zpgoodsSame = true;
       				 break;
       			 }
       		 }
       		 if(zpgoodsSame){
       			 break;
       		 }
       	 }
       }

       if(zpgoodsSame){
       	   message = "zpgoodsSame";
    	   return message;
       }

        if (goodInstanceIds != null) {
            for (int i = 0; i < goodInstanceIds.length; i++) {
                AvailableStock availableStock = goodsInstanceManager.getAvailableStock(Long.parseLong(goodInstanceIds[i]), Long.parseLong(depFirstId));
                if (null == availableStock || availableStock.getGoodsNumber() == null) {
                    message = "nonum";
                    return message;
                } else if (availableStock.getGoodsNumber() < Long.parseLong(nums[i])) {
                    message = "overnum";
                    return message;
                }
            }
        }
        if (zpgoodInstanceIds != null) {
            for (int i = 0; i < zpgoodInstanceIds.length; i++) {
                AvailableStock availableStock = goodsInstanceManager.getAvailableStock(Long.parseLong(zpgoodInstanceIds[i]), Long.parseLong(depFirstId));
                if (null == availableStock || availableStock.getGoodsNumber() == null) {
                    message = "zpnonum";
                    return message;
                } else if (availableStock.getGoodsNumber() < Long.parseLong(zpnums[i])) {
                    message = "zpovernum";
                    return message;
                }
            }
        }
        if (goodInstanceIds != null && zpgoodInstanceIds != null) {
            if (goodInstanceIds.length > 0 && zpgoodInstanceIds.length > 0) {
                Map<String, String> temp1 = new HashMap<String, String>();
                Map<String, String> temp2 = new HashMap<String, String>();
                Map<String, Long> alltemp = new HashMap<String, Long>();//������ظ��Ľ��е���value
                for (int i = 0; i < goodInstanceIds.length; i++) {
                    temp1.put(goodInstanceIds[i].toString(), nums[i].toString());
                }
                for (int j = 0; j < zpgoodInstanceIds.length; j++) {
                    temp2.put(zpgoodInstanceIds[j].toString(), zpnums[j].toString());
                }
                for (Map.Entry<String, String> tem1 : temp1.entrySet()) {
                    for (Map.Entry<String, String> tem2 : temp2.entrySet()) {
                        if (tem1.getKey().equals(tem2.getKey())) {
                            alltemp.put(tem1.getKey(), Long.valueOf(tem1.getValue())
                                                       + Long.valueOf(tem2.getValue()));
                        }
                    }
                }
                if (alltemp != null) {
                    for (Map.Entry<String, Long> tem3 : alltemp.entrySet()) {
                        AvailableStock availableStock = goodsInstanceManager.getAvailableStock(Long
                            .parseLong(tem3.getKey()), Long.parseLong(depFirstId));
                        // cyan 20101115 ���Ԥ��
                        GoodsInstance goodsInstance = goodsInstanceManager.getInstance(Long.parseLong(tem3.getKey()));
                        long minNum = 0; //��СԤ��
                        long buyNum = 0; // ������
                        long availableNum  = 0; //���Կ��
                        if( goodsInstance!= null && null!= goodsInstance.getMinNum()){
                        	minNum  = goodsInstance.getMinNum();
                        }
                        if(tem3.getValue()!=null){
                        	buyNum = tem3.getValue();
                        }
                        if(availableStock!=null){
                        	availableNum = availableStock.getGoodsNumber();
                        }
                        if (availableStock.getGoodsNumber() < tem3.getValue()) {
                            message = "addovernum";
                        }else if(minNum>0 && (availableNum-buyNum)<minNum){
                        	message = "warming";
                        }
                        return message;
                    }
                }
            }
        }
        return message;
    }
	
	
	@RequestMapping("/getWeightAndMoney")
    public @ResponseBody GoodsInstance getWeightAndMoney(@RequestParam("goodsIdArray") String goodsIdArray,@RequestParam("numsArray") String numsArray,
    		@RequestParam("goodsPriceArray") String goodsPriceArray,@RequestParam("zpgoodsIdArray") String zpgoodsIdArray,
    		@RequestParam("zpnumsArray") String zpnumsArray,@RequestParam("depFirstId") String depFirstId) {
		
		String[] goodIds = null;
		if(StringUtil.isNotBlank(goodsIdArray) && StringUtil.isNotEmpty(goodsIdArray)){
			goodIds = goodsIdArray.split(",");
		}
		String[] nums = null;
		if(StringUtil.isNotBlank(numsArray) && StringUtil.isNotEmpty(numsArray)){
			nums = numsArray.split(",");
		}
		String[] goodsPrices = null;
		if(StringUtil.isNotBlank(goodsPriceArray) && StringUtil.isNotEmpty(goodsPriceArray)){
			goodsPrices = goodsPriceArray.split(",");
		}
		String[] zpgoodIds = null;
		if(StringUtil.isNotBlank(zpgoodsIdArray) && StringUtil.isNotEmpty(zpgoodsIdArray)){
			zpgoodIds = zpgoodsIdArray.split(",");
		}
		String[] zpnums = null;
		if(StringUtil.isNotBlank(zpnumsArray) && StringUtil.isNotEmpty(zpnumsArray)){
			zpnums = zpnumsArray.split(",");
		}
		
		
        double AllWeight = 0; //������
        double epWeight = 0;// ������Ʒ����
        double AllPrice = 0;//�ܼ۸�
        Long AllNum = 0L;//������
        DepositoryFirst depfirst = depositoryFirstManager.getDepositoryById(Long.parseLong(depFirstId));
        if (goodIds != null) {
            for (int i = 0; i < goodIds.length; i++) {
                Long num = Long.parseLong(nums[i]);
                Double goodsPrice = Double.parseDouble(goodsPrices[i]);
                Goods goods = goodsManager.getGoods(Long.parseLong(goodIds[i]));
                AllWeight += goods.getGoodsWeight() * num;
                // �Ƿ������Ͱ��ʻ��Ʒ
                if (this.isExemptPostageGoods(Long.parseLong(goodIds[i]))) {
                	epWeight += goods.getGoodsWeight() * num;;
                }
                AllPrice += goodsPrice * num;
                AllNum += Long.parseLong(nums[i]);
            }
        }
        if (zpgoodIds != null) {
            for (int j = 0; j < zpgoodIds.length; j++) {
                Long zpnum = Long.parseLong(zpnums[j]);
                Goods goodszp = goodsManager.getGoods(Long.parseLong(zpgoodIds[j]));
                AllWeight += goodszp.getGoodsWeight() * zpnum;
                // ��Ʒ�Ƿ�����Ͱ��ʻ��Ʒ
                if (this.isExemptPostageGoods(Long.parseLong(zpgoodIds[j]))) {
                	epWeight += goodszp.getGoodsWeight() * zpnum;;
                }
                AllNum += Long.parseLong(zpnums[j]);
            }
        }
        GoodsInstance search = new GoodsInstance();
        search.setTotalPrice(DoubleUtil.round(AllPrice, 2));
        search.setTotalWeight(DoubleUtil.round(AllWeight, 2));
        search.setTotalEpWeight(DoubleUtil.round(epWeight, 2));
        search.setDepfirstName(depfirst.getDepFirstName());
        search.setBuyNum(AllNum);
        return search;
    }
	
    /*
     * �Ƿ���ʻ��Ʒ״̬
     * @param goodsId
     * @return
     * add by fanyj 2011-1-11
     */
	@SuppressWarnings("unchecked")
	private boolean isExemptPostageGoods(Long goodsId) {
		PromContext context = new PromContext();
		List promationTypes = new ArrayList();
		promationTypes.add(EnumPromationModeCode.SALE_EXEMPT_POSTAGE.getKey());
		context.put("goods.id", goodsId);
		context.put("promationTypes", promationTypes);
		return promationManager.isExemptPostageGoods(context);//
	}
	
	// ��̨��ȡʡ��
	@RequestMapping("/getBackRightCitys")
	public @ResponseBody JsonArea getBackRightCitys(@RequestParam("code") String code) {
		
		ArrayList<Region> temp = new ArrayList();
		Region region = new Region();
		region.setParentCode(code);
		region.setRegionType(3);
		temp = (ArrayList<Region>) regionManager.getRegion(region);

		JsonArea citys = new JsonArea(temp);
		return citys;
	}

	@RequestMapping("/getBackRightDistincs")
	public @ResponseBody JsonArea getBackRightDistincs(@RequestParam("code") String code) {
		Region region = new Region();
		ArrayList<Region> temp = new ArrayList<Region>();
		region.setParentCode(code);
		region.setRegionType(4);
		temp = (ArrayList<Region>) regionManager.getRegion(region);
		JsonArea distincs = new JsonArea(temp);
		return distincs;
	}
	
    /**
     * ��̨��������
     * @return
     */
	@RequestMapping("/disExpressSelBack")
    public @ResponseBody TradeList disExpressSelBack(@RequestParam("regionCodeEndSel") String regionCodeEndSel,@RequestParam("paymentTempSel") String paymentTempSel,
    		@RequestParam("epGoodsWeightTotal") String epGoodsWeightTotal,@RequestParam("depositoryFirstId") String depositoryFirstId,@RequestParam("goodsIdArray") String goodsIds,
    		@RequestParam("goodsNumArray") String goodsNum,@RequestParam("zpgoodsIdArray") String zpgoodsIds,@RequestParam("zpgoodsNumArray") String zpgoodsNum) {
        String regionCodeStart = "330782";
        if (depositoryFirstId != null) {
            Long firstId = Long.parseLong(depositoryFirstId);
            DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(firstId);
            if (null != depositoryFirst) {
                regionCodeStart = depositoryFirst.getRegionCode();
            }
        }
        List<ExpressDist> expressDistListTmp = expressDistManager.listExpressDistByRegionCodeEnd(
            regionCodeStart, regionCodeEndSel, paymentTempSel, null);

		// ��Ʒ��������˾������ϵ zhangwy
        /**
         * var goodsIdArray = new Array();
			var goodsNumArray = new Array();
			var zpgoodsIdArray = new Array();
			var zpgoodsNumArray = new Array();
         */
		List<Express> expressList = expressManager.getExpresss();
		List<String> allExpressListIds = new ArrayList<String>(); //ȫ����expressid����
		List<String> cfExpressListIds = new ArrayList<String>(); //�ظ���expressid����
		StringBuffer tempBuffer = new StringBuffer();
		StringBuffer numBuffer = new StringBuffer();

		//��ƷID�ַ�
		if(StringUtil.isBlank(goodsIds) || StringUtil.isEmpty(goodsIds)){
			tempBuffer.append(zpgoodsIds);
		}else if(StringUtil.isBlank(zpgoodsIds) || StringUtil.isEmpty(zpgoodsIds)){
			tempBuffer.append(goodsIds);
		}else{
			tempBuffer.append(goodsIds).append(",").append(zpgoodsIds);
		}

		//�����ַ�
		if(StringUtil.isBlank(goodsNum) || StringUtil.isEmpty(goodsNum)){
			numBuffer.append(zpgoodsNum);
		}else if(StringUtil.isBlank(zpgoodsNum) || StringUtil.isEmpty(zpgoodsNum)){
			numBuffer.append(goodsNum);
		}else{
			numBuffer.append(goodsNum).append(",").append(zpgoodsNum);
		}

		String[] allGoodsIdArray = tempBuffer.toString().split(",");
		String[] allGoodsNumArray = numBuffer.toString().split(",");
		if(allGoodsIdArray!=null){
			for(int i=0;i<allGoodsIdArray.length;i++){
				Goods goods = goodsManager.getExpressGoods(Long.parseLong(allGoodsIdArray[i]));
				if(StringUtil.isNotBlank(goods.getExpressIds()) && StringUtil.isNotEmpty(goods.getExpressIds())){
					String[] tempIds = goods.getExpressIds().split(",");
					if(tempIds!=null){
						for(int j=0;j<tempIds.length;j++){
							allExpressListIds.add(tempIds[j]);
						}
					}
				}else{
					for(Express express:expressList){
						allExpressListIds.add(String.valueOf(express.getId()));
					}
				}
			}
		}

		// �ҳ��ظ���expressids
		if(allExpressListIds!=null && allExpressListIds.size() > 0){
			if(allGoodsIdArray.length == 1){
				for(int i = 0; i < allExpressListIds.size(); i++){
					cfExpressListIds.add(allExpressListIds.get(i));
				}
			}else{
				for(int i = 0; i < allExpressListIds.size(); i++){
					int count = 0;
					for(int j = 0; j < allExpressListIds.size(); j++){
						if(allExpressListIds.get(j).equals(allExpressListIds.get(i))){
							count++;
						}
					}
					if(count > allGoodsIdArray.length-1){
						//���ظ�����ݷ����������list������˵��ȡ����(�������ظ�)
						cfExpressListIds.add(allExpressListIds.get(i));
					}
				}
			}
		}

		//�ٺ�ԭ���Ľ���ȡ�ظ�����
		List<ExpressDist> leftExpressDist = new ArrayList<ExpressDist>();
		if(cfExpressListIds!=null && cfExpressListIds.size() > 0){
			//ȥ���ظ�
			Set someSet = new HashSet(cfExpressListIds);
			Iterator iterator = someSet.iterator();
			List<String> tempList = new ArrayList<String>();
			int i = 0;
		    while(iterator.hasNext()){
		       tempList.add(iterator.next().toString());
		       i++;
		    }

			for(ExpressDist tmp : expressDistListTmp){
				for(String expressId : tempList){
					if(tmp.getExpressId() == Long.parseLong(expressId)){
						leftExpressDist.add(tmp);
					}
				}
			}
		}

		expressDistListTmp = leftExpressDist;

        for (ExpressDist tmp : expressDistListTmp) {
        	// ���¼��������������������ʼ��׹��ܣ������ʷ����¼��㣩add by fanyj 2011-02-16
        	double goodsWeightSum = 0;
        	double epGoodsWeightSum = 0;
        	// ���ﳵ�е���Ʒ
        	if(allGoodsIdArray != null && allGoodsIdArray.length > 0){
        		for(int i=0;i<allGoodsIdArray.length;i++){
    				Goods goods = goodsManager.getExpressGoods(Long.parseLong(allGoodsIdArray[i]));
    				if(goods != null){
    					boolean isEpGoods = this.isExemptPostageGoods(goods.getId());
    					Castweight castweight = castweightManager.getCastweightByGoodsIdAndExpessId(goods.getId(), tmp.getExpressId());
    					if(castweight != null && castweight.getCastWeight() > 0){
    						goodsWeightSum += castweight.getCastWeight()*Long.parseLong(allGoodsNumArray[i]);
    						tmp.setYouJiPao("(�м�����Ʒ)");
    						if(isEpGoods){
    							epGoodsWeightSum += castweight.getCastWeight()*Long.parseLong(allGoodsNumArray[i]);
    						}
    					}else{
    						goodsWeightSum += goods.getGoodsWeight()*Long.parseLong(allGoodsNumArray[i]);
    						if(isEpGoods){
    							epGoodsWeightSum += goods.getGoodsWeight()*Long.parseLong(allGoodsNumArray[i]);
    						}
    					}
    				}
         		}
        	}
        	String goodsWeightTotal = String.valueOf(goodsWeightSum);
        	epGoodsWeightTotal = String.valueOf(epGoodsWeightSum);
        	// ���������Ʒ�˷�
	       	 double epWeightMoney = 0;
	       	 if(StringUtil.isNotBlank(epGoodsWeightTotal) && Double.parseDouble(epGoodsWeightTotal) > 0){
	       		if (tmp.getWeightFirst() < Double.parseDouble(epGoodsWeightTotal)) {
	                double newWeight = (Double.parseDouble(epGoodsWeightTotal) - tmp.getWeightFirst()) / tmp.getWeightNext();//�м�������
	                //����С�����أ���+1
	                int newWeightInt = (int) newWeight;
	                if (newWeight > newWeightInt) {
	                    newWeightInt = newWeightInt + 1;
	                }
	                epWeightMoney = tmp.getWeightFirstMoney() + newWeightInt * tmp.getWeightNextMoney();
	            } else {
	           	 	epWeightMoney = tmp.getWeightFirstMoney();
	            }
	       	 }
	       	// �������˷�
	       	double weightMoney = 0;
	           if (tmp.getWeightFirst() < Double.parseDouble(goodsWeightTotal)) {
	               double newWeight = (Double.parseDouble(goodsWeightTotal) - tmp.getWeightFirst()) / tmp.getWeightNext();//�м�������
	               //����С�����أ���+1
	               int newWeightInt = (int) newWeight;
	               if (newWeight > newWeightInt) {
	                   newWeightInt = newWeightInt + 1;
	               }
	               weightMoney = tmp.getWeightFirstMoney() + newWeightInt * tmp.getWeightNextMoney();
	           } else {
	           	weightMoney = tmp.getWeightFirstMoney();
	           }
	           double resultMoney = weightMoney-epWeightMoney;
	        tmp.setWeightMoney(DoubleUtil.round(resultMoney>0?resultMoney:0, 1));
	    }
        TradeList tradelists = new TradeList(expressDistListTmp);// ���ض���jQuery�Ļص�����json
        tradelists.setPaymentTempSel(paymentTempSel);
        return tradelists;
    }
	

	/**
	 * ��̨�¶���
	 *
	 * @return
	 */
	@RequestMapping(value = "/backOrder", method = RequestMethod.POST)
	public String backOrder(Model model, HttpServletRequest request) {
		String[] goodsInstanceIds = request.getParameterValues("goodsInstanceId");
		String[] count = request.getParameterValues("count");
		String[] zpgoodsInstanceIds = request.getParameterValues("zpgoodsInstanceId");
		String[] zpcount = request.getParameterValues("zpcount");
		String depFirstId = request.getParameter("firstDepId");
//		String userId = getRequest().getParameter("userId");
//		String weight = getRequest().getParameter("goodsWeightTotal");
//		String epWeight = getRequest().getParameter("epGoodsWeightTotal");
		String goodsAmount = request.getParameter("goodsPriceTotal");
		String[] goodsPrices = request.getParameterValues("goodsPrice");
		String memo = request.getParameter("modifyRecord").trim();

		String userId = request.getParameter("userId").trim();

		Trade trade = new Trade();
		String zipcode = request.getParameter("trade.zipcode").trim();
		if (StringUtil.isNotBlank(zipcode) && StringUtil.isNotEmpty(zipcode)) {
			trade.setZipcode(zipcode);
		}
		String mobile = request.getParameter("trade.mobile").trim();
		if (StringUtil.isNotBlank(mobile) && StringUtil.isNotEmpty(mobile)) {
			trade.setMobile(mobile);
		}
		String address = request.getParameter("trade.address").trim();
		if (StringUtil.isNotBlank(address) && StringUtil.isNotEmpty(address)) {
			trade.setAddress(address);
		}
		String receiver = request.getParameter("trade.receiver").trim();
		if (StringUtil.isNotBlank(receiver) && StringUtil.isNotEmpty(receiver)) {
			trade.setReceiver(receiver);
		}
		String expressId = request.getParameter("expressId");
		if (StringUtil.isNotBlank(expressId) && StringUtil.isNotEmpty(expressId)) {
			String[] expressDistInfo = expressId.split("-");
			if (expressDistInfo != null && expressDistInfo.length == 3) {
				trade.setExpressId(new Long(expressDistInfo[0]));
				trade.setExpressPayment(expressDistInfo[1]);
			}
		}
		String buynote = request.getParameter("trade.buyerNote").trim();
		if (StringUtil.isNotBlank(buynote) && StringUtil.isNotEmpty(buynote)) {
			trade.setBuyerNote(buynote);
		}

		UserAddress userAddress = new UserAddress();
		String userAddressId = request.getParameter("userAddress.id").trim();
		if (StringUtil.isNotBlank(userAddressId) && StringUtil.isNotEmpty(userAddressId)) {
			userAddress.setId(Long.parseLong(userAddressId));
		}
		String province = request.getParameter("userAddress.province").trim();
		if (StringUtil.isNotBlank(province) && StringUtil.isNotEmpty(province)) {
			userAddress.setProvince(province);
		}
		String city = request.getParameter("userAddress.city").trim();
		if (StringUtil.isNotBlank(city) && StringUtil.isNotEmpty(city)) {
			userAddress.setCity(city);
		}
		String district = request.getParameter("userAddress.district").trim();
		if (StringUtil.isNotBlank(district) && StringUtil.isNotEmpty(district)) {
			userAddress.setDistrict(district);
		}
		if (StringUtil.isNotBlank(memo) && StringUtil.isNotEmpty(memo)) {
			trade.setMemo(memo);
		}

		DepositoryFirst depFirst = depositoryFirstManager.getDepositoryById(Long.parseLong(depFirstId));
		User user = userManager.getUser(Long.parseLong(userId));

		// ��֤�ж�
		boolean goodsSame = false;
		boolean zpgoodsSame = false;
		if (goodsInstanceIds != null) {
			for (int i = 0; i < goodsInstanceIds.length; i++) {
				for (int j = i + 1; j < goodsInstanceIds.length; j++) {
					if (Long.parseLong(goodsInstanceIds[i]) == Long
							.parseLong(goodsInstanceIds[j])) {
						goodsSame = true;
						break;
					}
				}
				if (goodsSame) {
					break;
				}
			}
		}

		if (goodsSame) {
	        model.addAttribute("userId", userId);
	        model.addAttribute("errormessage", "goodsSame");
	        return "redirect:/user/toBackOrderPage.html";
		}

		if (zpgoodsInstanceIds != null) {
			for (int i = 0; i < zpgoodsInstanceIds.length; i++) {
				for (int j = i + 1; j < zpgoodsInstanceIds.length; j++) {
					if (Long.parseLong(zpgoodsInstanceIds[i]) == Long
							.parseLong(zpgoodsInstanceIds[j])) {
						zpgoodsSame = true;
						break;
					}
				}
				if (zpgoodsSame) {
					break;
				}
			}
		}

		if (zpgoodsSame) {
	        model.addAttribute("userId", userId);
	        model.addAttribute("errormessage", "zpgoodsSame");
	        return "redirect:/user/toBackOrderPage.html";
		}

		if (goodsInstanceIds != null) {
			for (int i = 0; i < goodsInstanceIds.length; i++) {
				AvailableStock availableStock = goodsInstanceManager
						.getAvailableStock(Long.parseLong(goodsInstanceIds[i]),
								Long.parseLong(depFirstId));
				if (null == availableStock
						|| availableStock.getGoodsNumber() == null) {
			        model.addAttribute("userId", userId);
			        model.addAttribute("errormessage", "nonum");
			        return "redirect:/user/toBackOrderPage.html";
				} else if (availableStock.getGoodsNumber() < Long
						.parseLong(count[i])) {
			        model.addAttribute("userId", userId);
			        model.addAttribute("errormessage", "overnum");
			        return "redirect:/user/toBackOrderPage.html";
				}
			}
		}
		if (zpgoodsInstanceIds != null) {
			for (int i = 0; i < zpgoodsInstanceIds.length; i++) {
				AvailableStock availableStock = goodsInstanceManager
						.getAvailableStock(Long
								.parseLong(zpgoodsInstanceIds[i]), Long
								.parseLong(depFirstId));
				if (null == availableStock
						|| availableStock.getGoodsNumber() == null) {
			        model.addAttribute("userId", userId);
			        model.addAttribute("errormessage", "zpnonum");
			        return "redirect:/user/toBackOrderPage.html";
				} else if (availableStock.getGoodsNumber() < Long
						.parseLong(zpcount[i])) {
			        model.addAttribute("userId", userId);
			        model.addAttribute("errormessage", "zpovernum");
			        return "redirect:/user/toBackOrderPage.html";
				}
			}
		}
		if (goodsInstanceIds != null && zpgoodsInstanceIds != null) {
			if (goodsInstanceIds.length > 0 && zpgoodsInstanceIds.length > 0) {
				Map<String, String> temp1 = new HashMap<String, String>();
				Map<String, String> temp2 = new HashMap<String, String>();
				Map<String, Long> alltemp = new HashMap<String, Long>();// ������ظ��Ľ��е���value
				for (int i = 0; i < goodsInstanceIds.length; i++) {
					temp1.put(goodsInstanceIds[i], count[i]);
				}
				for (int j = 0; j < zpgoodsInstanceIds.length; j++) {
					temp2.put(zpgoodsInstanceIds[j], zpcount[j]);
				}
				for (Map.Entry<String, String> tem1 : temp1.entrySet()) {
					for (Map.Entry<String, String> tem2 : temp2.entrySet()) {
						if (tem1.getKey().equals(tem2.getKey())) {
							alltemp.put(tem1.getKey(), Long.valueOf(tem1
									.getValue())
									+ Long.valueOf(tem2.getValue()));
						}
					}
				}
				if (alltemp != null) {
					for (Map.Entry<String, Long> tem3 : alltemp.entrySet()) {
						AvailableStock availableStock = goodsInstanceManager
								.getAvailableStock(Long
										.parseLong(tem3.getKey()), Long
										.parseLong(depFirstId));
						if (availableStock.getGoodsNumber() < tem3.getValue()) {
					        model.addAttribute("userId", userId);
					        model.addAttribute("errormessage", "addovernum");
					        return "redirect:/user/toBackOrderPage.html";
						}
					}
				}
			}
		}
		if (goodsPrices != null && goodsPrices.length > 0) {
			for (int i = 0; i < goodsPrices.length; i++) {
				if (goodsPrices[i] == null
						|| Double.parseDouble(goodsPrices[i]) < 0) {
			        model.addAttribute("userId", userId);
			        model.addAttribute("errormessage", "wrongprice");
			        return "redirect:/user/toBackOrderPage.html";
				}
			}
		}

		List<GoodsInstance> goodsInstanceList = new ArrayList<GoodsInstance>();
		double weight = 0;
		double epWeight = 0;
		if (goodsInstanceIds != null) {
			for (int i = 0; i < goodsInstanceIds.length; i++) {
				GoodsInstance goodsInstance = goodsInstanceManager
						.getInstance(Long.parseLong(goodsInstanceIds[i]));
				goodsInstance.setBuyNum(Long.parseLong(count[i]));
				goodsInstance.setAgentPrice(Double.parseDouble(goodsPrices[i]));
				goodsInstanceList.add(goodsInstance);
				// ���¼����������м�����Ʒ����  add by fanyj 2011-02-18��
				Goods goods = goodsManager.getGoods(goodsInstance.getGoodsId());
				boolean isEpGoods = this.isExemptPostageGoods(goodsInstance.getGoodsId());// �Ƿ���ʲ�Ʒ
				Castweight castweight = castweightManager.getCastweightByGoodsIdAndExpessId(goodsInstance.getGoodsId(), trade.getExpressId());
				if(castweight != null && castweight.getCastWeight() > 0){
					weight += castweight.getCastWeight()*goodsInstance.getBuyNum();
					if(isEpGoods){
						epWeight += castweight.getCastWeight()*goodsInstance.getBuyNum();
					}
				}else{
					weight += goods.getGoodsWeight()*goodsInstance.getBuyNum();
					if(isEpGoods){
						epWeight += goods.getGoodsWeight()*goodsInstance.getBuyNum();
					}
				}
			}
		}
		if (zpgoodsInstanceIds != null) {
			for (int j = 0; j < zpgoodsInstanceIds.length; j++) {
				GoodsInstance goodsInstance = goodsInstanceManager
						.getInstance(Long.parseLong(zpgoodsInstanceIds[j]));
				goodsInstance.setBuyNum(Long.parseLong(zpcount[j]));
				goodsInstance.setType("zp");
				goodsInstanceList.add(goodsInstance);
				// ���¼����������м�����Ʒ����  add by fanyj 2011-02-18��
				Goods goods = goodsManager.getGoods(goodsInstance.getGoodsId());
				boolean isEpGoods = this.isExemptPostageGoods(goodsInstance.getGoodsId());// �Ƿ���ʲ�Ʒ
				Castweight castweight = castweightManager.getCastweightByGoodsIdAndExpessId(goodsInstance.getGoodsId(), trade.getExpressId());
				if(castweight != null && castweight.getCastWeight() > 0){
					weight += castweight.getCastWeight()*goodsInstance.getBuyNum();
					if(isEpGoods){
						epWeight += castweight.getCastWeight()*goodsInstance.getBuyNum();
					}
				}else{
					weight += goods.getGoodsWeight()*goodsInstance.getBuyNum();
					if(isEpGoods){
						epWeight += goods.getGoodsWeight()*goodsInstance.getBuyNum();
					}
				}
			}
		}
		tradeManager.backSaveTrade(goodsInstanceList, depFirst, user, trade,
				userAddress, weight, epWeight,Double.parseDouble(goodsAmount));
		return "redirect:/trade/showl.html";
	}
	
}
