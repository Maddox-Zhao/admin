package com.huaixuan.network.biz.service.goods.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.goods.GoodsInstanceDao;
import com.huaixuan.network.biz.dao.goods.PromationDao;
import com.huaixuan.network.biz.dao.goods.PromationModelDao;
import com.huaixuan.network.biz.dao.goods.RuleDao;
import com.huaixuan.network.biz.dao.goods.RuleDefDao;
import com.huaixuan.network.biz.domain.ShoppingCart;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.goods.ExpressionEvaluator;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.goods.PromContext;
import com.huaixuan.network.biz.domain.goods.Promation;
import com.huaixuan.network.biz.domain.goods.PromationFullGive;
import com.huaixuan.network.biz.domain.goods.PromationGive;
import com.huaixuan.network.biz.domain.goods.PromationModel;
import com.huaixuan.network.biz.domain.goods.PromationVO;
import com.huaixuan.network.biz.domain.goods.Rule;
import com.huaixuan.network.biz.domain.goods.RuleDef;
import com.huaixuan.network.biz.domain.goods.RuleParse;
import com.huaixuan.network.biz.domain.goods.RuleParseFactory;
import com.huaixuan.network.biz.enums.EnumPromationModeCode;
import com.huaixuan.network.biz.enums.EnumRuleType;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.query.PromationQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.goods.PromationManager;
import com.huaixuan.network.common.util.DateUtil;

@Service("promationManager")
public class PromationManagerImpl implements PromationManager {
	
	protected Log  log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private PromationDao promationDao;
	
	@Autowired
	private PromationModelDao promationModelDao;
	
	@Autowired
	private RuleDao ruleDao;
	
	@Autowired
	private RuleDefDao ruleDefDao;
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Autowired
	private GoodsInstanceDao goodsInstanceDao;
	
	@Autowired
	private GoodsInstanceManager goodsInstanceManager;

    

    /* @model: */
    public void addPromation(Promation promationDao) {
        log.info("PromationManagerImpl.addPromation method");
        try {
            this.promationDao.addPromation(promationDao);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public void editPromation(Promation promation) {
        log.info("PromationManagerImpl.editPromation method");
        try {
            this.promationDao.editPromation(promation);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public void removePromation(Long promationId) {
        log.info("PromationManagerImpl.removePromation method");
        try {
            this.promationDao.removePromation(promationId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public Promation getPromation(Long promationId) {
        log.info("PromationManagerImpl.getPromation method");
        try {
            return this.promationDao.getPromation(promationId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /* @model: */
    public List<Promation> getPromations() {
        log.info("PromationManagerImpl.getPromations method");
        try {
            return this.promationDao.getPromations();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public List<PromationVO> getPromationsByContext(PromContext context) {

        HashMap<String, Object> params = new HashMap<String, Object>();
        try {
            params.put("curTime", DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", new Date()));

            List<PromationVO> returnList = new ArrayList<PromationVO>();


            //����ֱ��PUT�����ݵײ㣬����һЩ��������mode_code��
            params.putAll(context);
            //��ȡ������Ч�������Ż�����
            List<PromationVO> allPromations = promationDao.getPromationsByParams(params);
            for (PromationVO promationVO : allPromations) {



                //��ȡ�Ż�ģ��
                PromationModel promationModel = promationModelDao
                    .getPromationModelByCode(promationVO.getModeCode());

                String condition = promationModel.getConditionExpression();

                //��ȡ�Żݹ�������
                List<Rule> rules = ruleDao.getRulesByPromationId(promationVO.getId());
                for (Rule rule : rules) {
                    if (rule.getRuleType() == EnumRuleType.USERD.getKey().intValue()) {
                        if (condition.indexOf(":[" + rule.getRuleCode() + "]") >= 0) {
                            condition = condition.replaceAll(":\\[" + rule.getRuleCode() + "\\]",
                                rule.getConditionValue());
                        }
                    }
                }

                //����goods,id,������Ǹ�����ƷID������ȡ�������ܵ��Ż�
                Object goodsId=context.get("goods.id");
                //������������
                String isFlag = ExpressionEvaluator.resolvePlaceholder(condition, context);
                if (goodsId==null||isFlag.equalsIgnoreCase("true")) {
                    getPromationGoods(promationVO);
                    returnList.add(promationVO);
                }

            }
            return returnList;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ManagerException(e);
        }
    }

    public boolean isExemptPostageGoods(PromContext context) {
    	HashMap<String, Object> params = new HashMap<String, Object>();
    	boolean result = false;
        try {
            params.put("curTime", DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", new Date()));
            List<PromationVO> returnList = new ArrayList<PromationVO>();

            //����ֱ��PUT�����ݵײ㣬����һЩ��������mode_code��
            params.putAll(context);
            //��ȡ������Ч�������Ż�����
            List<PromationVO> allPromations = promationDao.getPromationsByParams(params);
            Long goodsId = new Long(0);
            for (PromationVO promationVO : allPromations) {
                //��ȡ�Żݹ�������
                List<Rule> rules = ruleDao.getRulesByPromationId(promationVO.getId()); 
                for (Rule rule : rules) {
                    if (rule.getRuleType() == EnumRuleType.USERD.getKey().intValue()) {
                    	goodsId = (Long)context.get("goods.id");
                        if (rule.getConditionValue().indexOf(goodsId.toString()) >= 0) {
                        	result = true;
                        }
                    }
                }
            }
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ManagerException(e);
        }
	}
    
	@SuppressWarnings("unchecked")
	public double getGoodsDiscount(Long goodsId) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		List promationTypes = new ArrayList();
		String result = "";boolean isHas = false;
		PromContext context = new PromContext();
		promationTypes.add(EnumPromationModeCode.DISCOUNT.getKey());
        context.put("promationTypes", promationTypes);
        try {
            params.put("curTime", DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", new Date()));

            //����ֱ��PUT�����ݵײ㣬����һЩ��������mode_code��
            params.putAll(context);
            //��ȡ������Ч�������Ż�����
            List<PromationVO> allPromations = promationDao.getPromationsByParams(params);
            for (PromationVO promationVO : allPromations) {
                //��ȡ�Żݹ�������
                List<Rule> rules = ruleDao.getRulesByPromationId(promationVO.getId()); 
                for (Rule rule : rules) {
                    if (rule.getRuleType() == EnumRuleType.USERD.getKey().intValue()) {
                        if (rule.getConditionValue().indexOf(goodsId.toString()) >= 0) {
                        	isHas = true;
                        }
                    }
                    if (rule.getRuleType() == EnumRuleType.PROMATION.getKey().intValue()) {
                        result = rule.getResultValue();
                    }
                }
            }
            if(isHas && StringUtils.isNotBlank(result)){
            	return Double.parseDouble(result);
            }else{
            	return 0;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ManagerException(e);
        }
	}

	private void getPromationGoods(PromationVO promationVO) throws Exception {

        List<Goods> goodsList = new ArrayList<Goods>();
        //������۴��� Ĭ���Ѿ���goodsIds�ı���������Ҫ����
        if (promationVO.getModeCode().equalsIgnoreCase(
            EnumPromationModeCode.COMBINED_SALE.getKey())){
            Map map=getPromContextByPromation(promationVO.getId());//��ȡ�Żݣ�����Ӧ�Ĺ������
            PromContext promContext=(PromContext)map.get("promContext");
            promationVO.setPromationPrice(Double.parseDouble(promContext.get("price").toString()));
            List goodsIds = (List) promContext.get("goodsIds");
            for (int i = 0; i < goodsIds.size(); i++) {
                Goods goods=goodsDao.getGoods(Long.parseLong(goodsIds.get(i).toString()));
                if(goods!=null){
                    goodsList.add(goods);
                    promationVO.setTotalPrice(promationVO.getTotalPrice()+goods.getGoodsPrice());
                }
            }
            promationVO.setGoodsList(goodsList);

        }
        //������Ĵ���Ĭ���Ѿ���send_goods_id�����ִ���
        else if(promationVO.getModeCode()
                .equalsIgnoreCase(EnumPromationModeCode.SALE_GIVE.getKey())) {
            Map map=getPromContextByPromation(promationVO.getId());//��ȡ�Żݣ�����Ӧ�Ĺ������
            PromContext promContext=(PromContext)map.get("promContext");
            String goodsId = (String) promContext.get("goods.id");
            Goods goods=goodsDao.getGoods(Long.parseLong(goodsId));
            promationVO.setPromationPrice(goods.getGoodsPrice());
            promationVO.setTotalPrice(goods.getGoodsPrice());
            goodsList.add(goods);//����Ʒ�ļ���
            goodsId = (String) promContext.get("send_goods_id");
            Goods goods2=goodsDao.getGoods(Long.parseLong(goodsId));
            promationVO.setTotalPrice(promationVO.getTotalPrice()+goods2.getGoodsPrice());
            goodsList.add(goods2);//������Ʒ�ļ���
            promationVO.setGoodsList(goodsList);
        }



        return;
        /*
        List<Rule> rules = ruleDao.getRulesByPromationId(promationVO.getId());
        double goodsTotolPrice=0;
        Map goodsParam = new HashMap();
        for (Rule rule : rules) {
            if (promationVO.getModeCode().equalsIgnoreCase(
                EnumPromationModeCode.COMBINED_SALE.getKey())) {
                if (rule.getConditionValue().equalsIgnoreCase("package.scope.id.include")) {
                    RuleDef ruleDef = ruleDefDao.getRuleDefByCode(rule.getRuleCode());
                    RuleParse ruleParse = RuleParseFactory.getRuleParse(ruleDef);
                    List<Rule> parsmRules = new ArrayList<Rule>();
                    Map params = ruleParse.ruleToParams(parsmRules, ruleDef);
                    List goodsIds = (List) params.get("goods_ids");
                    for (int i = 0; i < goodsIds.size(); i++) {
                        Goods goods=goodsDao.getGoods(Long.parseLong(goodsIds.get(i).toString()));
                        if(goods!=null){
                            goodsTotolPrice+=goods.getGoodsPrice();
                            goodsList.add(goods);
                        }
                    }
                }
            } else if (promationVO.getModeCode().equalsIgnoreCase(
                EnumPromationModeCode.SALE_GIVE.getKey())) {
                if (rule.getConditionValue().equalsIgnoreCase("give.goods")) {
                    RuleDef ruleDef = ruleDefDao.getRuleDefByCode(rule.getRuleCode());
                    RuleParse ruleParse = RuleParseFactory.getRuleParse(ruleDef);
                    List<Rule> parsmRules = new ArrayList<Rule>();
                    Map params = ruleParse.ruleToParams(parsmRules, ruleDef);
                    String goodsId = (String) params.get("send_goods_id");
                    Goods goods=goodsDao.getGoods(Long.parseLong(goodsId));
                    if(goods!=null){
                        goodsTotolPrice+=goods.getGoodsPrice();
                        goodsList.add(goods);
                    }
                }
                if (rule.getConditionValue().equalsIgnoreCase("package.scope.id")) {
                    RuleDef ruleDef = ruleDefDao.getRuleDefByCode(rule.getRuleCode());
                    RuleParse ruleParse = RuleParseFactory.getRuleParse(ruleDef);
                    List<Rule> parsmRules = new ArrayList<Rule>();
                    Map params = ruleParse.ruleToParams(parsmRules, ruleDef);
                    String goodsId = (String) params.get("goods_id");
                    Goods goods=goodsDao.getGoods(Long.parseLong(goodsId));
                    if(goods!=null){
                        goodsTotolPrice+=goods.getGoodsPrice();
                        goodsList.add(goods);
                    }
                }
            }
        }

       if(goodsList.size()>0){
           promationVO.setGoodsList(goodsList);
           promationVO.setTotalPrice(goodsTotolPrice);
       }*/
    }



    public boolean addPromation(Promation promation, PromContext promContext) {

        if (promation == null)
            return false;
        if (promation.getModeCode() == null)
            return false;
        try {
            //�������Ż�DO����
            promationDao.addPromation(promation);
            //�������Żݹ���
            addPromationRule(promation, promContext);
            return true;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ManagerException(e);
        }

    }

    private void addPromationRule(Promation promation, PromContext promContext) throws Exception {

        if (promation.getModeCode().equalsIgnoreCase(EnumPromationModeCode.COMBINED_SALE.getKey())) {
            //������Ʒ������۷�Χ���� ,��Ʒ��������
            addRule("package.scope.id.include", promation, promContext);
            //������Ʒ��ϵ��Żݼ۸�,�۸��滻����
            addRule("price", promation, promContext);
        } else if (promation.getModeCode().equalsIgnoreCase(
            EnumPromationModeCode.SALE_GIVE.getKey())) {
            //������Ʒ�������Χ����,��ƷID��ȹ���
            addRule("package.scope.id", promation, promContext);
            //����������Żݹ���,����ID����
            addRule("give.goods", promation, promContext);
        } else if (promation.getModeCode().equalsIgnoreCase(
            EnumPromationModeCode.FULL_GIVE.getKey())) {
            //������Ʒ�����ͷ�Χ����,��Ʒ����ȫ������
            addRule("package.scope.all", promation, promContext);
            //���������͵��Żݹ���,��Ʒ�������
            if("numberFull".equals((String)promContext.get("fullGiveType"))){
            	addRule("give.goods.number.between", promation, promContext);
            }else{
            	addRule("give.goods.zone", promation, promContext);
            }
        } else if (promation.getModeCode().equalsIgnoreCase(
            EnumPromationModeCode.FULL_REDUCE.getKey())) {
            //������Ʒ���ͼ���Χ����
            addRule("package.scope.all", promation, promContext);
            //�������ͼ����Żݹ��򣬼۸������Χ
            addRule("price.between", promation, promContext);
        } else if (promation.getModeCode().equalsIgnoreCase(
            EnumPromationModeCode.SALE_EXEMPT_POSTAGE.getKey())) {
            //������Ʒ��Ͱ��ʷ�Χ����
            addRule("package.scope.id.include", promation, promContext);
        } else if (promation.getModeCode().equalsIgnoreCase(
            EnumPromationModeCode.DISCOUNT.getKey())) {
            //������Ʒ���۴�����Χ����
            addRule("package.scope.id.include", promation, promContext);
            //���Ӵ��۴������Żݼ۸�,�ۿ��滻����
            addRule("price", promation, promContext);
        }

    }

    /**
     * �����Żݹ���
     * @param ruleCode
     * @param promation
     * @param promContext
     * @throws Exception
     */
    private void addRule(String ruleCode, Promation promation, PromContext promContext)
                                                                                     throws Exception {
        RuleDef ruleDef = ruleDefDao.getRuleDefByCode(ruleCode);

        //���ݹ�������Ż������ģ��������������б�
        List<Rule> rules = RuleParseFactory.getRuleParse(ruleDef).defToRule(promContext, ruleDef);
        if (rules != null && rules.size() > 0) {
            if(rules.get(0).getRuleCode().equals("give.goods")){
            	String giftIds = rules.get(0).getResultValue();
            	String[] giftId = giftIds.split(";");
            	for(int i = 0; i<giftId.length; i++){
            		Rule rule = new Rule();
            		rule.setPromationId(promation.getId());
            		rule.setRuleCode(rules.get(0).getRuleCode());
            		rule.setResultValue(giftId[i].trim());
            		rule.setRuleType(rules.get(0).getRuleType());
            		ruleDao.addRule(rule);
            	}
            }else{
                for (Rule rule : rules) {
                	// �������ײ����⴦��
                	if(promation.getModeCode().equalsIgnoreCase(
                            EnumPromationModeCode.FULL_GIVE.getKey())
                            && rules.get(0).getRuleCode().equals("package.scope.all")){
                		rule.setResultValue((String)promContext.get("fullGiveType"));// �������������
                		rule.setExrtaInfo((String)promContext.get("scope"));// ��������ͷ�Χ
                	}
                    rule.setPromationId(promation.getId());
                    ruleDao.addRule(rule);
                }	
            }
        }

    }

    public Map getPromContextByPromation(Long promationId) {
        if (promationId == null)
            return null;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            Promation promation = promationDao.getPromation(promationId);
            if (promation == null)
                return null;
            map.put("promation", promation);

            PromContext promContext = new PromContext();
            List<Rule> rules = ruleDao.getRulesByPromationId(promationId);


            //�ϲ�����ͬһ����������ݣ����ϲ�һ��
            Map ruleDefMap = new HashMap();
            for (Rule rule : rules) {
                //���������ruleCode��Ӧ��List,������
                if (ruleDefMap.get(rule.getRuleCode()) == null) {
                    ruleDefMap.put(rule.getRuleCode(), new ArrayList());
                }
                ((List) ruleDefMap.get(rule.getRuleCode())).add(rule);
            }

            Iterator it = ruleDefMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object ruleCode = entry.getKey();
                Object ruleList = entry.getValue();
                RuleDef ruleDef = ruleDefDao.getRuleDefByCode(ruleCode.toString());
                RuleParse ruleParse = RuleParseFactory.getRuleParse(ruleDef);
                //�����Żݹ������ݣ����Żݵ�Map�����У��Է���ԭ�ȵ�ʹ�á�
                Map params = ruleParse.ruleToParams((List<Rule>) ruleList, ruleDef);
                promContext.putAll(params);
            }
            map.put("promContext", promContext);
            return map;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ManagerException(e);
        }
    }

    public boolean editPromation(Promation promation, PromContext promContext) {
        if (promation == null)
            return false;
        try {
            //���޸��Ż�DO����
            promationDao.editPromationByDynamic(promation);
            //����ɾ����������
            ruleDao.removeRuleByPromationId(promation.getId());
            //����������������
            addPromationRule(promation, promContext);

            return true;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ManagerException(e);
        }
    }

    public GoodsDao getGoodsDao() {
        return goodsDao;
    }

    public void setGoodsDao(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    public PromationVO getPromationInfo(Long promationId, PromContext promContext) {

        if(promationId==null||promContext==null){
            return null;
        }
        try{
            Promation promation = promationDao.getPromation(promationId);
            if (promation == null)
                return null;
            PromationVO promationVO=new PromationVO();
            BeanUtils.copyProperties(promationVO, promation);

            //��ȡ�Żݹ�������
            List<Rule> rules = ruleDao.getRulesByPromationId(promationVO.getId());
            String promModeCode=promation.getModeCode();
            for (Rule rule : rules) {
                if (rule.getRuleType() == EnumRuleType.PROMATION.getKey().intValue()) {
                    if (promation.getModeCode().equalsIgnoreCase(EnumPromationModeCode.COMBINED_SALE.getKey())) {
                        //������۵��Żݣ������Żݼ۸�
                        promationVO.setPromationPrice(Double.parseDouble(rule.getResultValue()));
                        getPromationGoods(promationVO);
                    }else if (promation.getModeCode().equalsIgnoreCase(EnumPromationModeCode.DISCOUNT.getKey())) {
                        //���۴������Żݣ������Ż��ۿ�
                        promationVO.setPromationPrice(Double.parseDouble(rule.getResultValue()));
                        getPromationGoods(promationVO);
                    } else if (promation.getModeCode().equalsIgnoreCase(
                        EnumPromationModeCode.SALE_GIVE.getKey())) {

                    	//��͸������Żݣ�����������Ʒ
                    	 getPromationGoods(promationVO);
                    	/*Goods goods=goodsDao.getGoods(Long.parseLong(rule.getResultValue()));
                        if(goods!=null){
                            List<Goods> list=new ArrayList<Goods>();
                            list.add(goods);
                            promationVO.setGoodsList(list);
                        }
                        */
                    } else if (promation.getModeCode().equalsIgnoreCase(
                        EnumPromationModeCode.FULL_GIVE.getKey())) {
                        //�����͵��Żݣ�����������Ʒ
                        String isFlag = ExpressionEvaluator.resolvePlaceholder(rule.getConditionValue(), promContext);
                        if (isFlag.equalsIgnoreCase("true")) {
                            Goods goods=goodsDao.getGoods(Long.parseLong(rule.getResultValue()));
                            if(goods!=null){
                                List<Goods> list=new ArrayList<Goods>();
                                list.add(goods);
                                promationVO.setGoodsList(list);
                            }
                        }

                    } else if (promation.getModeCode().equalsIgnoreCase(
                        EnumPromationModeCode.FULL_REDUCE.getKey())) {
                        //���ͼ����Żݣ����Ǽ۸��Ż�
                        String isFlag = ExpressionEvaluator.resolvePlaceholder(rule.getConditionValue(), promContext);
                        if (isFlag.equalsIgnoreCase("true")) {
                            double price=Double.parseDouble(promContext.get("price").toString());
                            promationVO.setPromationPrice(price+Double.parseDouble(rule.getResultValue()));
                        }
                    }
                }
            }

            return promationVO;
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new ManagerException(e);
        }
    }

	public List<Map> getPromationMapListByIds(List promationIdList) {

		List<Map> promationMapList = new ArrayList();
		if(promationIdList!=null && promationIdList.size()>0){
			for(int i = 0 ;i<promationIdList.size();i++){
				Long promationId = (Long)promationIdList.get(i);
				Map map = this.getPromContextByPromation(promationId);
				if(map!=null){
					promationMapList.add(map);
				}
			}
		}

		return promationMapList;
	}


//    public List<Promation> getPromationsByPage(Map<String, Object> conditions, Page page) {
//
//        log.info("getPromationsByPage method");
//        try {
//            return this.promationDao.getPromationsByPage(conditions, page);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw new ManagerException(e);
//        }
//
//    }

	public List<PromationVO> getGivePromationList(String scope) {
		 HashMap<String, Object> params = new HashMap<String, Object>();
		 params.put("curTime", DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", new Date()));
		 List promationTypes=new ArrayList();
		 if(EnumPromationModeCode.FULL_GIVE.getKey().equals(scope)){
			 promationTypes.add("full_give");
		 }else if(EnumPromationModeCode.FULL_REDUCE.getKey().equals(scope)){
			 promationTypes.add("full_reduce");
		 }else{
			 promationTypes.add("full_give");
			 promationTypes.add("full_reduce");
		 }
         params.put("promationTypes", promationTypes);
         params.put("isFreeze", "no");
		 return this.promationDao.getGivePromationList(params);
	}

	public Promation getPromationByModeCode(String modeCode) {
    	log.info("getPromationByModeCode method");
        try {
            return this.promationDao.getPromationByModeCode(modeCode);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ManagerException(e);
        }
	}

	public List<Promation> getPromationByName(String name) {
        log.info("getPromationsByPage method");
        try {
            return this.promationDao.getPromationByName(name);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ManagerException(e);
        }
    }

    public List<Promation> getPromationsByIds(List ids){
         try {
             return this.promationDao.getPromationsByIds(ids);
         } catch (Exception e) {
             log.error(e.getMessage());
             throw new ManagerException(e);
         }
    }

    public void autoCanelFreeze() {
        try {
             this.promationDao.autoCanelFreeze();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ManagerException(e);
        }
    }

	public List<PromationGive> getNewPromationGiveList(Long goodsId) {
		List<PromationGive> promationVOGiveList = new ArrayList<PromationGive>();
		Date now = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowStr = df.format(now);
		List<PromationGive> list = promationDao.getNewPromationVOListGive(nowStr);
		if(list!=null && list.size() > 0){
			for(PromationGive temp : list){
				Map parMap = new HashMap();
				parMap.put("promationId", temp.getId());
				parMap.put("goodsId", goodsId);
				Rule rule = ruleDao.getGiveRule(parMap);
				if(rule!=null){
					List<PromationGive> promationGiveList = promationDao.getGivePromation(rule.getPromationId());
					if(promationGiveList!=null&&promationGiveList.size()> 0){
						for(PromationGive promationGive : promationGiveList){
						  if(promationGive.getGoodsInstanceId()!=null){
								try{
									GoodsInstance goodsInstance = goodsInstanceDao.getInstance(promationGive.getGoodsInstanceId());
									Goods goods = goodsDao.getGoods(goodsInstance.getGoodsId());
									if(goodsInstance!=null && goods!=null){
										promationGive.setTitle(goodsInstance.getInstanceName());
										promationGive.setImgMiddle((goods.getImgMiddle()!=null)?goods.getImgMiddle():"");
										promationGive.setGoodsInstanceTitle((goodsInstance.getAttrDesc()!=null)?goodsInstance.getInstanceName()+";"+goodsInstance.getAttrDesc():goodsInstance.getInstanceName());
										promationGive.setGoodsId(goods.getId());
										boolean isSame = false; //ȥ���ظ����ײ�
										for(PromationGive tmp:promationVOGiveList){
										  if(tmp.getGoodsInstanceId().equals(goodsInstance.getId())){
											  isSame = true;
										  }
										}
										if(!isSame){
											promationVOGiveList.add(promationGive);
										}
									}
								}catch(Exception e){
									log.error(e.getMessage());
								}	  
						  }
						}
					}
				}
			}
		}
		return promationVOGiveList;
	}

	public List<PromationGive> getFullGiveGoodsList(List<ShoppingCart> shoppingCartsList) {
		List<PromationGive> giveList = new ArrayList<PromationGive>();
		Map<String,List<PromationFullGive>> resultMap = this.getPromationFullGiveList();
		if(resultMap != null && resultMap.size() > 0){
			List<PromationFullGive>  allFullGiveList  = resultMap.get("all");
	        List<PromationFullGive>  catFullGiveList  = resultMap.get("cat");
	        List<PromationFullGive>  brandFullGiveList  = resultMap.get("brand");
	        
	        Map<String,PromationFullGive> catMap = new HashMap<String,PromationFullGive>();
	        Map<String,PromationFullGive> brandMap = new HashMap<String,PromationFullGive>();
	        // ����ȫ���������ײ�
	        double allSumMoney = 0;int allSumNumber = 0;
	        PromationFullGive promationFullGive;
	        try {
	        	Goods goods = null;
	        	if(allFullGiveList != null && allFullGiveList.size() > 0){// ȫ��������
					for(ShoppingCart shoppingCart : shoppingCartsList){
						goods = goodsDao.getGoods(shoppingCart.getGoodsId());
						if(goods != null){
							allSumMoney += shoppingCart.getGoodsNumber()*goods.getAgentPrice();
							allSumNumber += shoppingCart.getGoodsNumber();
						}
					}
					for(PromationFullGive fullGive:allFullGiveList){
						PromationGive obj = this.setFullGiveGoodsList(allSumMoney, allSumNumber, goods, fullGive);
						if(obj != null){
							giveList.add(obj);
						}
					}
				}
	        	
	        	if(catFullGiveList != null && catFullGiveList.size() > 0){// ��Ŀ��������
	        		for(PromationFullGive catFullGive:catFullGiveList){// ���㹺�ﳵ����Ʒ�����ײ͵��ܽ��������
						for(ShoppingCart shoppingCart : shoppingCartsList){
							goods = goodsDao.getGoods(shoppingCart.getGoodsId());
							if(goods != null && "cat".equals(catFullGive.getScope())
									&& goods.getCatCode().indexOf(catFullGive.getScopeValue()) > -1){
								promationFullGive = catMap.get(catFullGive.getScopeValue());
								if(promationFullGive != null){
									promationFullGive.setSumMoney(promationFullGive.getSumMoney()+shoppingCart.getGoodsNumber()*goods.getAgentPrice());
									promationFullGive.setSumNumber(promationFullGive.getSumNumber()+shoppingCart.getGoodsNumber());
								}else{
									promationFullGive = new PromationFullGive();
									promationFullGive.setSumMoney(shoppingCart.getGoodsNumber()*goods.getAgentPrice());
									promationFullGive.setSumNumber(shoppingCart.getGoodsNumber());
								}
								catMap.put(catFullGive.getScopeValue(), promationFullGive);
							}
						}
					}
	        		for(PromationFullGive catFullGive:catFullGiveList){// ��ӷ�����Ŀ��������Ʒ
	        			String catCode = catFullGive.getScopeValue();
	        			for(Entry<String, PromationFullGive> entry : catMap.entrySet()){
	        				if(catCode.indexOf(entry.getKey()) > -1){
	        					PromationGive obj = this.setFullGiveGoodsList(entry.getValue().getSumMoney(), entry.getValue().getSumNumber(), goods, catFullGive);
	    						if(obj != null){
	    							giveList.add(obj);
	    						}        					
	        				}
	        			}
	        		}
	        	}
	        	
	        	if(brandFullGiveList != null && brandFullGiveList.size() > 0){// Ʒ����������
	        		for(PromationFullGive brandFullGive:brandFullGiveList){// ���㹺�ﳵ����Ʒ�����ײ͵��ܽ��������
						for(ShoppingCart shoppingCart : shoppingCartsList){
							goods = goodsDao.getGoods(shoppingCart.getGoodsId());
							if(goods != null && "brand".equals(brandFullGive.getScope())
									&& brandFullGive.getScopeValue().equals(String.valueOf(goods.getBrandId()))){
								promationFullGive = brandMap.get(brandFullGive.getScopeValue());
								if(promationFullGive != null){
									promationFullGive.setSumMoney(promationFullGive.getSumMoney()+shoppingCart.getGoodsNumber()*goods.getAgentPrice());
									promationFullGive.setSumNumber(promationFullGive.getSumNumber()+shoppingCart.getGoodsNumber());
								}else{
									promationFullGive = new PromationFullGive();
									promationFullGive.setSumMoney(shoppingCart.getGoodsNumber()*goods.getAgentPrice());
									promationFullGive.setSumNumber(shoppingCart.getGoodsNumber());
								}
								brandMap.put(brandFullGive.getScopeValue(), promationFullGive);
							}
						}
					}
	        		for(PromationFullGive brandFullGive:brandFullGiveList){// ��ӷ���Ʒ����������Ʒ
	        			String brandId = brandFullGive.getScopeValue();
	        			for(Entry<String, PromationFullGive> entry : brandMap.entrySet()){
	        				if(entry.getKey().equals(brandId)){
	        					PromationGive obj = this.setFullGiveGoodsList(entry.getValue().getSumMoney(), entry.getValue().getSumNumber(), goods, brandFullGive);
	    						if(obj != null){
	    							giveList.add(obj);
	    						}        					
	        				}
	        			}
	        		}
	        	}
	        } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}		
		return giveList;
	}

	private PromationGive setFullGiveGoodsList(double sumMoney, 
			int sumNumber,Goods goods, PromationFullGive fullGive) throws Exception {
		PromationGive promationGive = new PromationGive();
		if("numberFull".equals(fullGive.getFullGiveType())){// ����������
			GoodsInstance goodsInstance = null;
			for(int i=0;i<fullGive.getLowerPrice().size();i++){
				int low = fullGive.getLowerPrice().get(i).indexOf(".");
				int top = fullGive.getTopPrice().get(i).indexOf(".");
				String lowPriceStr = fullGive.getLowerPrice().get(i)
										.substring(0, (low >= 0 ? low:fullGive.getLowerPrice().get(i).length()));
				String topPriceStr = fullGive.getTopPrice().get(i)
										.substring(0, (top >= 0 ? top:fullGive.getTopPrice().get(i).length()));
				if(Integer.parseInt(lowPriceStr) <= sumNumber
						&& sumNumber < Integer.parseInt(topPriceStr)){
					goodsInstance = fullGive.getResultMap().get(fullGive.getTopPrice().get(i));
					break;
				}
			}
			if(goodsInstance != null){
				Goods gds = goodsDao.getGoods(goodsInstance.getGoodsId());
				if(goods != null){
					promationGive.setTitle(goodsInstance.getInstanceName());
					promationGive.setImgMiddle((gds.getImgMiddle()!=null)?gds.getImgMiddle():"");
					promationGive.setGoodsInstanceTitle((goodsInstance.getAttrDesc()!=null)?goodsInstance.getInstanceName()+";"+goodsInstance.getAttrDesc():goodsInstance.getInstanceName());
					promationGive.setGoodsId(gds.getId());
					promationGive.setGoodsInstanceId(goodsInstance.getId());
					promationGive.setGoodsInstanceNum(1);// ��Ʒ���һ��
					promationGive.setGoodsWeight(gds.getGoodsWeight());
					return promationGive;
				}
			}
		}else{// ���������
			GoodsInstance goodsInstance = null;
			for(int i=0;i<fullGive.getLowerPrice().size();i++){
				if(Double.parseDouble(fullGive.getLowerPrice().get(i)) <= sumMoney
						&& sumMoney < Double.parseDouble(fullGive.getTopPrice().get(i))){
					goodsInstance = fullGive.getResultMap().get(fullGive.getTopPrice().get(i));
					break;
				}
			}
			if(goodsInstance != null){
				Goods gds = goodsDao.getGoods(goodsInstance.getGoodsId());
				if(goods != null){
					promationGive.setTitle(goodsInstance.getInstanceName());
					promationGive.setImgMiddle((gds.getImgMiddle()!=null)?gds.getImgMiddle():"");
					promationGive.setGoodsInstanceTitle((goodsInstance.getAttrDesc()!=null)?goodsInstance.getInstanceName()+";"+goodsInstance.getAttrDesc():goodsInstance.getInstanceName());
					promationGive.setGoodsId(gds.getId());
					promationGive.setGoodsInstanceId(goodsInstance.getId());
					promationGive.setGoodsInstanceNum(1);//��Ʒ���һ��
					promationGive.setGoodsWeight(gds.getGoodsWeight());
					return promationGive;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Map<String,List<PromationFullGive>> getPromationFullGiveList() {
		List<PromationVO> promationList = this.getGivePromationList(EnumPromationModeCode.FULL_GIVE.getKey());
        PromationFullGive fullGive = null;
        Map<String,List<PromationFullGive>> resultMap = new HashMap<String,List<PromationFullGive>>();
        List<PromationFullGive>  catFullGiveList  = new ArrayList<PromationFullGive>();
        List<PromationFullGive>  brandFullGiveList  = new ArrayList<PromationFullGive>();
        List<PromationFullGive>  allFullGiveList  = new ArrayList<PromationFullGive>();
        for(PromationVO obj:promationList){
     	   fullGive = new PromationFullGive();
     	   if(obj.getRuleExrtaInfo() != null){
     		  String[] scopeStr = obj.getRuleExrtaInfo().split("=");
        	  if(scopeStr.length > 1){
        		  Map  map = null;
           	   	  if(obj.getRuleExrtaInfo().indexOf("all") >= 0){
           		   	map = this.getPromContextByPromation(obj.getId());
           		   	if(map != null && map.size() > 0){
           		   		PromContext proContext = (PromContext) map.get("promContext");
           		   		allFullGiveList.add(setFullGiveObject(fullGive, obj, scopeStr, proContext, "all"));
           		   	}
                  }else if(obj.getRuleExrtaInfo().indexOf("cat") >= 0){
                  	map = this.getPromContextByPromation(obj.getId());
            		   	if(map != null && map.size() > 0){
            		   		PromContext proContext = (PromContext) map.get("promContext");
            		   		catFullGiveList.add(setFullGiveObject(fullGive, obj, scopeStr, proContext, "cat"));
            		   }
                  }else if(obj.getRuleExrtaInfo().indexOf("brand") >= 0){
                  	map = this.getPromContextByPromation(obj.getId());
            		   	if(map != null && map.size() > 0){
            		   		PromContext proContext = (PromContext) map.get("promContext");
            		   		brandFullGiveList.add(setFullGiveObject(fullGive, obj, scopeStr, proContext, "brand"));
            		   }
                  }
        	  }
     	   }
        }
        resultMap.put("all", allFullGiveList);
        resultMap.put("cat", catFullGiveList);
        resultMap.put("brand", brandFullGiveList);
        return resultMap;
	}

	@SuppressWarnings("unchecked")
	private PromationFullGive setFullGiveObject(PromationFullGive fullGive, PromationVO obj,
			String[] scopeStr, PromContext proContext, String scope) {
		List lowerPrice = (List) proContext.get("lowerPrice");
		List topPrice = (List) proContext.get("topPrice");
		List send_goods_ids = (List) proContext.get("send_goods_ids");
		if(lowerPrice == null){
			lowerPrice=(List) proContext.get("lowerNumber");
		}
		if(topPrice == null){
			topPrice=(List) proContext.get("topNumber");
		}
		fullGive.setFullGiveType(obj.getRuleResultValue());
		fullGive.setScopeValue(scopeStr[1]);
		fullGive.setScope(scope);
		fullGive.setTopPrice(topPrice);
		fullGive.setLowerPrice(lowerPrice);
		fullGive.setResultMap(goodsInstanceManager.getAllFullGiveGoodsInstance(send_goods_ids),topPrice);		
		return fullGive;
	}
	
    @SuppressWarnings("unchecked")
	@Override
	public QueryPage getPromationListByConditionWithPage(PromationQuery promation,
			int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(promation);
		Map pramas = queryPage.getParameters();

		int count = promationDao.getPromationListByConditionWithPageCount(pramas);

		if (count > 0) {

			/* ��ǰҳ */
			queryPage.setCurrentPage(currPage);
			/* ÿҳ���� */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			List<Promation> promationList = promationDao.getPromationListByConditionWithPage(pramas);
			if (promationList != null && promationList.size() > 0) {
				queryPage.setItems(promationList);
			}
		}
		return queryPage;
	}


}
