package com.huaixuan.network.web.action.goods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.dao.goods.RuleDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.goods.PromContext;
import com.huaixuan.network.biz.domain.goods.Promation;
import com.huaixuan.network.biz.domain.goods.PromationStr;
import com.huaixuan.network.biz.domain.goods.Rule;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.domain.shop.Series;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumGoodsStatus;
import com.huaixuan.network.biz.enums.EnumPromationModeCode;
import com.huaixuan.network.biz.query.PromationQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.goods.PromationManager;
import com.huaixuan.network.biz.service.goods.RuleManager;
import com.huaixuan.network.biz.service.shop.BrandService;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
@RequestMapping(value = "/goods")
public class PromationAction extends BaseAction {

	@Autowired
	private PromationManager promationManager;
	
	@Autowired
	private RuleManager ruleManager;
	
	@Autowired
	private CategoryManager categoryManager;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private GoodsInstanceManager goodsInstanceManager;
	
	@Autowired
	private GoodsManager goodsManager;
	
	@Autowired
	private RuleDao ruleDao;
	
	protected Log  log = LogFactory.getLog(this.getClass());
	

    @AdminAccess({EnumAdminPermission.A_PROMATION_VIEW_USER})
    @RequestMapping("/searchPromation")
    public String promationlist(@ModelAttribute("promation") PromationQuery promation, Model model,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage) {
      Date date = new Date();
      SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String newDateString = format1.format(date);
      model.addAttribute("newDate", date);
      model.addAttribute("newDateString", newDateString);
      QueryPage page = promationManager.getPromationListByConditionWithPage(promation, currPage, this.pageSize);
      if(page != null){
    	model.addAttribute("query", page);
      }
      return "/goods/searchPromations";
    }
    

    /**�޸�
     * @return
     * @throws Exception
     */
    @AdminAccess({EnumAdminPermission.A_PROMATION_MODIFY_USER})
    @RequestMapping(value = "/updatePromation", method = RequestMethod.GET)
    public String updatePromation(@ModelAttribute("promation") Promation promation , @RequestParam("id") String id, Model model) throws Exception {
    	
        try{
            log.info("id=="+id);
            Map  map = promationManager.getPromContextByPromation(Long.parseLong(id));
            promation = (Promation) map.get("promation");

            if(promation==null){
            	model.addAttribute("errormessage", "nopromation");
                return "redirect:/goods/searchPromation.html";
            }
            else{
                SimpleDateFormat df_new=new SimpleDateFormat("yyyy-MM-dd");
                promation.setStartDate_hour(String.valueOf(promation.getStartDate().getHours()));
                promation.setEndDate_hour(String.valueOf(promation.getEndDate().getHours()));


                promation.setStartDate_str(df_new.format(promation.getStartDate()));
                promation.setEndDate_str(df_new.format(promation.getEndDate()));

                String[] hanz = new String[]{"һ","��","��","��","��","��","��","��","��","ʮ"};
                List<PromationStr> updateList = new ArrayList<PromationStr>();
                PromContext proContext = (PromContext) map.get("promContext");
                log.info("proContext==>"+proContext);

                if(promation.getModeCode().equals("full_give")){
                    List lowerPrice=(List) proContext.get("lowerPrice");
                    List topPrice=(List) proContext.get("topPrice");
                    if(lowerPrice == null){
                    	lowerPrice=(List) proContext.get("lowerNumber");
                    }
                    if(topPrice == null){
                    	topPrice=(List) proContext.get("topNumber");
                    }
                    List send_goods_ids=(List) proContext.get("send_goods_ids");
                    int amount=lowerPrice.size();
                    model.addAttribute("amount", amount);
                    
                    Rule rule = ruleManager.getGiveRuleByPromationId(Long.parseLong(id));
                    model.addAttribute("rule", rule);
                    if(rule != null && StringUtil.isNotBlank(rule.getExrtaInfo())){
                    	String[] scopeStr = rule.getExrtaInfo().split("=");
                    	if(scopeStr.length == 2){
                    		model.addAttribute("scope", scopeStr[0].trim());// ��Χ����
                    		if("cat".equals(scopeStr[0].trim())){
                    			model.addAttribute("catName", categoryManager.getCatFullNameByCatcode(scopeStr[1].trim()));
                    		}
                    		model.addAttribute("scopeValue", scopeStr[1].trim());
                    	}
	                    //ȡ��Ʒ���б�
	                      List<Brand> brandList = brandService.getBrands();
	                      model.addAttribute("brandList", brandList);
	                      for(int i=0;i<send_goods_ids.size();i++){
	                          PromationStr  proStr=null;
	                          proStr = new PromationStr();
	                          proStr.setAmount(i);
	                          if("priceFull".equals(rule.getResultValue())){
	                        	  proStr.setLowerPrice(Double.parseDouble((String)lowerPrice.get(i)));
		                          proStr.setTopPrice(Double.parseDouble((String)topPrice.get(i)));
	                          }else if("numberFull".equals(rule.getResultValue())){
	                        	  proStr.setLowerNumber(Integer.parseInt((String)lowerPrice.get(i)));
		                          proStr.setTopNumber(Integer.parseInt((String)topPrice.get(i)));
	                          }	                          
	                          proStr.setName("��"+hanz[i]);
	                          proStr.setName0("name0"+i);
	                          proStr.setName1("name1"+i);
	                          proStr.setName2("name2"+i);
	                          GoodsInstance goodsInstance = goodsInstanceManager.getInstance(Long.parseLong((String)send_goods_ids.get(i)));
	                          if(goodsInstance!=null){
	                          proStr.setGoodsId(goodsInstance.getId());
	                          proStr.setGoodsName(goodsInstance.getInstanceName());
	                          proStr.setAttrName("attrName"+i);
	                          proStr.setAttrId("attrId"+i);
	                          proStr.setAttrDiv("attrDiv"+i);
	                          }
	                          updateList.add(proStr);
	
	                      }
                    }
                    model.addAttribute("priceList", updateList);
                    model.addAttribute("promation", promation);
                    return "/goods/update_full_give";
                }
                if(promation.getModeCode().equals("full_reduce")){
                    List lowerPrice=(List) proContext.get("lowerPrice");
                    List topPrice=(List) proContext.get("topPrice");
                    List price=(List) proContext.get("price");
                    int amount = lowerPrice.size();
                    model.addAttribute("amount", amount);
                    for(int i=0;i<price.size();i++){
                        PromationStr  proStr=null;
                        proStr = new PromationStr();
                        proStr.setAmount(i);
                        proStr.setLowerPrice(Double.parseDouble((String)lowerPrice.get(i)));
                        proStr.setTopPrice(Double.parseDouble((String)topPrice.get(i)));
                        proStr.setName("��"+hanz[i]+":");
                        proStr.setPrice(Integer.parseInt((String)price.get(i)));
                        proStr.setName0("name0"+i);
                        proStr.setName1("name1"+i);
                        proStr.setName2("name2"+i);
                        updateList.add(proStr);

                    }
                    model.addAttribute("priceList", updateList);
                    model.addAttribute("promation", promation);
                    return "/goods/update_full_reduce";
                }
                model.addAttribute("priceList", updateList);
                model.addAttribute("idstr", promation.getId());
                if(promation.getModeCode().equals(EnumPromationModeCode.COMBINED_SALE.getKey())){
                	return "redirect:/goods/updatepsp.html";
                }else if(promation.getModeCode().equals(EnumPromationModeCode.SALE_GIVE.getKey())){
                	return "redirect:/goods/updategb.html";
                }else if(promation.getModeCode().equals(EnumPromationModeCode.SALE_EXEMPT_POSTAGE.getKey())){
                	return "redirect:/goods/updateEpPage.html";
                }else if(promation.getModeCode().equals(EnumPromationModeCode.DISCOUNT.getKey())){
                	return "redirect:/goods/updateDiscountPage.html";
                }else{
                	model.addAttribute("errormessage", "systemerror");
                    return "redirect:/goods/searchPromation.html";
                }
            }
        }catch (Exception e) {
        	model.addAttribute("errormessage", "systemerror");
            return "redirect:/goods/searchPromation.html";
        }
    }
    
    @RequestMapping(value = "/updategb", method = RequestMethod.GET)
    public String doUpdateGiftsToBuy(@RequestParam("idstr") String idstr, Model model, HttpServletRequest request) {
    	List<GoodsInstance> giftsGoodsList = new ArrayList<GoodsInstance>();
        if (StringUtil.isBlank(idstr)) {
        	model.addAttribute("errormessage", "nopromation");
            return "redirect:/goods/searchPromation.html";
        }
        long id = Long.parseLong(idstr);
        Map map = promationManager.getPromContextByPromation(id);
        Promation promation = (Promation) map.get("promation");
        PromContext promContext = (PromContext) map.get("promContext");

        String buyGoodsId = (String) promContext.get("goods.id");
        Goods buyGoods = goodsManager.getGoods(Long.parseLong(buyGoodsId));
        model.addAttribute("buyGoods", buyGoods);

        List<Rule> ruleList = ruleDao.getSaleGiveRuleList(promation.getId());
		String giftGoodsIds = request.getParameter("giftGoodsIds");
		String goodsInfoStr = request.getParameter("goodsInfoStr");
        if(ruleList!=null && ruleList.size() > 0){
        	for(Rule rule : ruleList){
        		GoodsInstance gi = goodsInstanceManager.getInstance(Long.parseLong(rule.getResultValue()));
        		if(gi!=null){
        			Goods good = this.goodsManager.getGoods(gi.getGoodsId());
        			if(good!=null){
        				gi.setGoodsPrice(good.getGoodsPrice());
        				gi.setImgSmall(good.getImgSmall());
        			}
        		}
                if (StringUtil.isBlank(giftGoodsIds)) {
                	giftGoodsIds = gi.getId().toString();
                } else {
                	giftGoodsIds = giftGoodsIds + ";" + gi.getId().toString();
                }
                if (StringUtil.isBlank(goodsInfoStr)) {
                    goodsInfoStr = "" + gi.getId() + "~!~" + gi.getGoodsPrice() + "~!~"
                                   + gi.getInstanceName() + "~!~" + gi.getImgSmall() + "~!~" + ((gi.getAttrDesc()!=null)?gi.getAttrDesc(): "");
                } else {
                    goodsInfoStr = goodsInfoStr + "~@~" + gi.getId() + "~!~"
                                   + gi.getGoodsPrice() + "~!~" + gi.getInstanceName() + "~!~"
                                   + gi.getImgSmall() + "~!~" + ((gi.getAttrDesc()!=null)?gi.getAttrDesc(): "");
                }
                giftsGoodsList.add(gi);
        	}
        }
        promation.setStartDate_hour(DateUtil.getDateTime("H", promation.getStartDate()));
        promation.setEndDate_hour(DateUtil.getDateTime("H", promation.getEndDate()));
        promation.setStartDate_str(DateUtil.getDateTime("yyyy-MM-dd", promation.getStartDate()));
        promation.setEndDate_str(DateUtil.getDateTime("yyyy-MM-dd", promation.getEndDate()));
        
        model.addAttribute("buyGoods", buyGoods);
        model.addAttribute("promation", promation);
        model.addAttribute("giftGoodsIds", giftGoodsIds);
        model.addAttribute("goodsInfoStr", goodsInfoStr);
        model.addAttribute("giftsGoodsList", giftsGoodsList);
        model.addAttribute("buyGoodsId", buyGoodsId);
        return "/goods/updateGiftsToBuy";
    }
    
    
    @RequestMapping(value = "/updatepsp", method = RequestMethod.GET)
    private String doUpdatePortSale(@RequestParam("idstr") String idstr, Model model, HttpServletRequest request) {
        if (StringUtil.isBlank(idstr)) {
        	model.addAttribute("errormessage", "nopromation");
            return "redirect:/goods/searchPromation.html";
        }
        long id = Long.parseLong(idstr);
        Map map = promationManager.getPromContextByPromation(id);
        Promation promation = (Promation) map.get("promation");
        PromContext promContext = (PromContext) map.get("promContext");

        List<Goods> goodsList = new ArrayList<Goods>();
        List goodsIdList = (List) promContext.get("goodsIds");
		String goodsIds = request.getParameter("goodsIds");
		String goodsInfoStr = request.getParameter("goodsInfoStr");
        for (int i = 0; i < goodsIdList.size(); i++) {
            Goods goodsnew = goodsManager.getGoods(Long.parseLong((String) goodsIdList.get(i)));
            if (StringUtil.isBlank(goodsIds)) {
                goodsIds = (String) goodsIdList.get(i);
            } else {
                goodsIds = goodsIds + ";" + (String) goodsIdList.get(i);
            }
            if (StringUtil.isBlank(goodsInfoStr)) {
                goodsInfoStr = "" + goodsnew.getId() + "~!~" + goodsnew.getGoodsPrice() + "~!~"
                               + goodsnew.getTitle() + "~!~" + goodsnew.getImgSmall();
            } else {
                goodsInfoStr = goodsInfoStr + "~@~" + goodsnew.getId() + "~!~"
                               + goodsnew.getGoodsPrice() + "~!~" + goodsnew.getTitle() + "~!~"
                               + goodsnew.getImgSmall();
            }
            goodsList.add(goodsnew);
        }
        String priceStr = (String)promContext.get("price");
        if(StringUtil.isNotBlank(priceStr)){
        	promation.setPrice(Double.parseDouble((String) promContext.get("price")));
        }        
        promation.setStartDate_hour(DateUtil.getDateTime("H", promation.getStartDate()));
        promation.setEndDate_hour(DateUtil.getDateTime("H", promation.getEndDate()));
        promation.setStartDate_str(DateUtil.getDateTime("yyyy-MM-dd", promation.getStartDate()));
        promation.setEndDate_str(DateUtil.getDateTime("yyyy-MM-dd", promation.getEndDate()));

        model.addAttribute("promation", promation);
        model.addAttribute("goodsList", goodsList);
        model.addAttribute("goodsInfoStr", goodsInfoStr);
        model.addAttribute("goodsIds", goodsIds);
        return "/goods/updatePortSale";
	}
    
    @RequestMapping(value = "/updateEpPage", method = RequestMethod.GET)
    private String updatePortAndExemptSale(@RequestParam("idstr") String idstr, Model model, HttpServletRequest request) {
        if (StringUtil.isBlank(idstr)) {
        	model.addAttribute("errormessage", "nopromation");
            return "redirect:/goods/searchPromation.html";
        }
        long id = Long.parseLong(idstr);
        Map map = promationManager.getPromContextByPromation(id);
        Promation promation = (Promation) map.get("promation");
        PromContext promContext = (PromContext) map.get("promContext");

        List<Goods> goodsList = new ArrayList<Goods>();
        List goodsIdList = (List) promContext.get("goodsIds");
		String goodsIds = request.getParameter("goodsIds");
		String goodsInfoStr = request.getParameter("goodsInfoStr");
        for (int i = 0; i < goodsIdList.size(); i++) {
            Goods goodsnew = goodsManager.getGoods(Long.parseLong((String) goodsIdList.get(i)));
            if (StringUtil.isBlank(goodsIds)) {
                goodsIds = (String) goodsIdList.get(i);
            } else {
                goodsIds = goodsIds + ";" + (String) goodsIdList.get(i);
            }
            if (StringUtil.isBlank(goodsInfoStr)) {
                goodsInfoStr = "" + goodsnew.getId() + "~!~" + goodsnew.getGoodsPrice() + "~!~"
                               + goodsnew.getTitle() + "~!~" + goodsnew.getImgSmall();
            } else {
                goodsInfoStr = goodsInfoStr + "~@~" + goodsnew.getId() + "~!~"
                               + goodsnew.getGoodsPrice() + "~!~" + goodsnew.getTitle() + "~!~"
                               + goodsnew.getImgSmall();
            }
            goodsList.add(goodsnew);
        }
        String priceStr = (String)promContext.get("price");
        if(StringUtil.isNotBlank(priceStr)){
        	promation.setPrice(Double.parseDouble((String) promContext.get("price")));
        }        
        promation.setStartDate_hour(DateUtil.getDateTime("H", promation.getStartDate()));
        promation.setEndDate_hour(DateUtil.getDateTime("H", promation.getEndDate()));
        promation.setStartDate_str(DateUtil.getDateTime("yyyy-MM-dd", promation.getStartDate()));
        promation.setEndDate_str(DateUtil.getDateTime("yyyy-MM-dd", promation.getEndDate()));

        model.addAttribute("promation", promation);
        model.addAttribute("goodsList", goodsList);
        model.addAttribute("goodsInfoStr", goodsInfoStr);
        model.addAttribute("goodsIds", goodsIds);
        return "/goods/updateExemptPostage";
	}
    
    @RequestMapping(value = "/updateDiscountPage", method = RequestMethod.GET)
    private String doUpdateDiscountToBuyPage(@RequestParam("idstr") String idstr, Model model, HttpServletRequest request) {
        if (StringUtil.isBlank(idstr)) {
        	model.addAttribute("errormessage", "nopromation");
            return "redirect:/goods/searchPromation.html";
        }
        long id = Long.parseLong(idstr);
        Map map = promationManager.getPromContextByPromation(id);
        Promation promation = (Promation) map.get("promation");
        PromContext promContext = (PromContext) map.get("promContext");

        List<Goods> goodsList = new ArrayList<Goods>();
        List goodsIdList = (List) promContext.get("goodsIds");
		String goodsIds = request.getParameter("goodsIds");
		String goodsInfoStr = request.getParameter("goodsInfoStr");
        for (int i = 0; i < goodsIdList.size(); i++) {
            Goods goodsnew = goodsManager.getGoods(Long.parseLong((String) goodsIdList.get(i)));
            if (StringUtil.isBlank(goodsIds)) {
                goodsIds = (String) goodsIdList.get(i);
            } else {
                goodsIds = goodsIds + ";" + (String) goodsIdList.get(i);
            }
            if (StringUtil.isBlank(goodsInfoStr)) {
                goodsInfoStr = "" + goodsnew.getId() + "~!~" + goodsnew.getGoodsPrice() + "~!~"
                               + goodsnew.getTitle() + "~!~" + goodsnew.getImgSmall();
            } else {
                goodsInfoStr = goodsInfoStr + "~@~" + goodsnew.getId() + "~!~"
                               + goodsnew.getGoodsPrice() + "~!~" + goodsnew.getTitle() + "~!~"
                               + goodsnew.getImgSmall();
            }
            goodsList.add(goodsnew);
        }
        String priceStr = (String)promContext.get("price");
        if(StringUtil.isNotBlank(priceStr)){
        	promation.setPrice(Double.parseDouble((String) promContext.get("price")));
        }        
        promation.setStartDate_hour(DateUtil.getDateTime("H", promation.getStartDate()));
        promation.setEndDate_hour(DateUtil.getDateTime("H", promation.getEndDate()));
        promation.setStartDate_str(DateUtil.getDateTime("yyyy-MM-dd", promation.getStartDate()));
        promation.setEndDate_str(DateUtil.getDateTime("yyyy-MM-dd", promation.getEndDate()));

        
        model.addAttribute("promation", promation);
        model.addAttribute("goodsList", goodsList);
        model.addAttribute("goodsInfoStr", goodsInfoStr);
        model.addAttribute("goodsIds", goodsIds);
        return "/goods/updateDiscount";
	}
    
    /**
     * �����ײ�
     * @return
     * @throws Exception
     */
    @RequestMapping("/freezePro")
    public String freezePro(@RequestParam("id") String id, Model model) throws Exception {
        try{
        	Promation promation = promationManager.getPromation(Long.parseLong(id));

            if(promation==null){
            	model.addAttribute("errormessage", "nopromation");
                return "redirect:/goods/searchPromation.html";
            }
            else{
                promation.setIsFreeze("yes");
                promationManager.editPromation(promation);
            	model.addAttribute("message", "freezesuccesss");
                return "redirect:/goods/searchPromation.html";
            }
        }catch (Exception e) {
        	model.addAttribute("errormessage", "systemerror");
            return "redirect:/goods/searchPromation.html";
        }

    }
    /**
     * ȡ����ײ�
     * @return
     * @throws Exception
     */
    @RequestMapping("/isfreezePro")
    public String isfreezePro(@RequestParam("id") String id, Model model) throws Exception {
        try{
        	Promation promation = promationManager.getPromation(Long.parseLong(id));
            if(promation==null){
            	model.addAttribute("errormessage", "nopromation");
                return "redirect:/goods/searchPromation.html";
            }
            else{
                promation.setIsFreeze("no");
                promationManager.editPromation(promation);
            	model.addAttribute("message", "isfreezesuccesss");
                return "redirect:/goods/searchPromation.html";
            }
        }catch (Exception e) {
        	model.addAttribute("errormessage", "systemerror");
            return "redirect:/goods/searchPromation.html";
        }

    }
    

    /**�����ײ�
     * @return
     * @throws Exception
     */
    @AdminAccess({EnumAdminPermission.A_PROMATION_ADD_USER})
    @RequestMapping("/addPro")
    public String addPro(@ModelAttribute("promation") Promation promation,@RequestParam("modeCode") String modeCode, Model model, HttpServletRequest request) throws Exception {
    	// ����ͳ�ʼ�����
    	if(EnumPromationModeCode.FULL_GIVE.getKey().equals(modeCode)){ 
    		String promationType = request.getParameter("promationType");
    		if(("priceFull").equals(promationType)){
    			model.addAttribute("isPriceFull", "true");
    		}
    		model.addAttribute("promationType", promationType);
            //ȡ��Ʒ���б�
            List<Brand> brandList = brandService.getBrands();
            model.addAttribute("brandList", brandList);
    	}
        if(modeCode.equals(EnumPromationModeCode.FULL_GIVE.getKey())){
        	return "/goods/full_give";
        }else if(modeCode.equals(EnumPromationModeCode.FULL_REDUCE.getKey())){
        	return "/goods/full_reduce";
        }else if(modeCode.equals(EnumPromationModeCode.SALE_GIVE.getKey())){
            return "redirect:/goods/giftsBuy.html";
        }else if(modeCode.equals(EnumPromationModeCode.COMBINED_SALE.getKey())){
        	return "redirect:/goods/portsale.html";
        }else if(modeCode.equals(EnumPromationModeCode.SALE_EXEMPT_POSTAGE.getKey())){
        	return "redirect:/goods/exemptPostage.html";
        }else if(modeCode.equals(EnumPromationModeCode.DISCOUNT.getKey())){
        	return "redirect:/goods/discount.html";
        }else{
        	model.addAttribute("errormessage", "nopromation");
            return "redirect:/goods/searchPromation.html";
        }
    }
    
    @RequestMapping(value = "/giftsBuy", method = RequestMethod.GET)
    public String doGiftsToBuy(Model model) {
    	Promation promation = new Promation();
    	model.addAttribute("promation", promation);
        return "/goods/giftsToBuy";
    }
    
    @RequestMapping(value = "/portsale", method = RequestMethod.GET)
    public String doAddPortSale(Model model) {
    	Promation promation = new Promation();
    	model.addAttribute("promation", promation);
        return "/goods/addPortSale";
    }
    
    @RequestMapping(value = "/exemptPostage", method = RequestMethod.GET)
    public String doExemptPostageToBuy(Model model) {
    	Promation promation = new Promation();
    	model.addAttribute("promation", promation);
        return "/goods/exemptPostage";
    }
    
    @RequestMapping(value = "/discount", method = RequestMethod.GET)
    public String doDiscountToBuy(Model model) {
    	Promation promation = new Promation();
    	model.addAttribute("promation", promation);
        return "/goods/discount";
    }
    

    /**
     * �������͹���
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addFullGive", method = RequestMethod.POST)
    public String addFullGive(@ModelAttribute("promation") Promation promation, BindingResult result, Model model,HttpServletRequest request) throws Exception {
        String promationType = request.getParameter("promationType");
        String scope = request.getParameter("scope");
        String amountStr = request.getParameter("amount");
        int amount = Integer.valueOf(amountStr);
        try{
            promation.setName(promation.getName().trim());
            List promationList = promationManager.getPromationByName(promation.getName());
            if(promationList!=null&&promationList.size()!=0){
            	result.rejectValue("name", "", "�Բ��𣬸��Ż��ײ�����Ѿ����ڣ�");
            	addPro(promation,EnumPromationModeCode.FULL_GIVE.getKey(), model, request);
                return "/goods/full_give";
            }
            PromContext promContext=new PromContext();
            promation.setModeCode("full_give");
            promation.setIsFreeze("no");
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = format.parse(promation.getStartDate_str() + " " + promation.getStartDate_hour()
                                     + ":00:00");
                date2 = format.parse(promation.getEndDate_str() + " " + promation.getEndDate_hour()
                                     + ":00:00");
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
            promation.setStartDate(date1);
            promation.setEndDate(date2);

            List lowerPrice=new java.util.ArrayList();
            List topPrice=new java.util.ArrayList();
            List send_goods_ids=new java.util.ArrayList();

            // ѡ���ײ�ʹ�÷�Χ
            if("cat".equals(scope)){
            	if(request.getParameter("selectCode") == null 
            			|| request.getParameter("selectCode") == ""){
                	model.addAttribute("errormessage", "areaerror");
                	model.addAttribute("modeCode", EnumPromationModeCode.FULL_GIVE.getKey());
                    return "redirect:/goods/addPro.html";
            	}else{
            		promContext.put("scope", "cat="+request.getParameter("selectCode"));
            	}
            }else if("brand".equals(scope)){
            	if(request.getParameter("selectBrand") == null 
            			|| request.getParameter("selectBrand") == ""){
                	model.addAttribute("errormessage", "branderror");
                	model.addAttribute("modeCode", EnumPromationModeCode.FULL_GIVE.getKey());
                    return "redirect:/goods/addPro.html";
            	}else{
            		promContext.put("scope", "brand="+request.getParameter("selectBrand"));
            	}
            }else{
            	promContext.put("scope", "all=all");
            }
            if(amount!=0){
                for(int i=0;i<amount;i++){
                    lowerPrice.add(request.getParameter("money0"+i));
                    topPrice.add(request.getParameter("money1"+i));
                    send_goods_ids.add(request.getParameter("attrId"+i));
                }
            }
            
            promContext.put("fullGiveType", promationType);
            // ����ͷֽ������ͺ����������
            if("numberFull".equals(promationType)){
            	promContext.put("lowerNumber", lowerPrice);
                promContext.put("topNumber", topPrice);
            }else{
            	promContext.put("lowerPrice", lowerPrice);
                promContext.put("topPrice", topPrice);
            }            
            promContext.put("send_goods_ids", send_goods_ids);
            promContext.put("zoneNum", amount);

            boolean flag=promationManager.addPromation(promation, promContext);
            if(flag){
            	model.addAttribute("message", "addsuccess");
                return "redirect:/goods/searchPromation.html";
            }
            else{
            	model.addAttribute("errormessage", "addfail");
                return "redirect:/goods/searchPromation.html";
            }
        }catch (Exception e) {
        	model.addAttribute("errormessage", "systemerror");
            return "redirect:/goods/searchPromation.html";
        }

    }
    
    /**
     * ���ʱ�䴦��
     * @return Promation
     * @throws
     */
    public Promation promationAddTime(Promation promation, HttpServletRequest request){
    	String startDateStr = request.getParameter("startDateStr");
    	String endDateStr = request.getParameter("endDateStr");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        promation.setStartDate_str(startDateStr);
        promation.setEndDate_str(endDateStr);
        log.info("promation.getStartDate_str()==>"+promation.getStartDate_str());
        log.info("promation.getEndDate_str()==>"+promation.getEndDate_str());
        Date date1 = null;
        Date date2 = null;
         try {
             if(promation.getStartDate_hour()==null||promation.getStartDate_hour().length()==0){
                 promation.setStartDate_hour("00");
             }
             if(promation.getEndDate_hour()==null||promation.getEndDate_hour().length()==0){
                 promation.setEndDate_hour("00");
             }
             date1 = format.parse(promation.getStartDate_str()+" "+promation.getStartDate_hour()+":00:00");
             date2 = format.parse(promation.getEndDate_str()+" "+promation.getEndDate_hour()+":00:00");
         } catch (ParseException e) {
             log.error(e.getMessage());
         }
         promation.setStartDate(date1);
         promation.setEndDate(date2);


         return promation;

    }
    
    /**
     * �����ͼ�����
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/promation", method = RequestMethod.POST)
    public String addPromation(@ModelAttribute("promation") Promation promation, BindingResult result, Model model,HttpServletRequest request) throws Exception {
        try{
            String amountStr = request.getParameter("amount");
            int amount = Integer.valueOf(amountStr);
            promation.setName(promation.getName().trim());

            List promationList = promationManager.getPromationByName(promation.getName());
            if(promationList!=null&&promationList.size()!=0){
            	result.rejectValue("name", "", "�Բ��𣬸��Ż��ײ�����Ѿ����ڣ�");
            	addPro(promation,EnumPromationModeCode.FULL_REDUCE.getKey(), model, request);
                return "/goods/full_reduce";
            }
            PromContext promContext=new PromContext();
            promation = promationAddTime(promation,request);
            promation.setModeCode("full_reduce");
            promation.setIsFreeze("no");
            List lowerPrice=new java.util.ArrayList();
            List topPrice=new java.util.ArrayList();
            List price=new java.util.ArrayList();

            if(amount!=0){
                for(int i=0;i<amount;i++){
                    lowerPrice.add(request.getParameter("money0"+i));
                    topPrice.add(request.getParameter("money1"+i));
                    price.add(request.getParameter("money2"+i));
                }

            }

            promContext.put("lowerPrice", lowerPrice);
            promContext.put("topPrice", topPrice);
            promContext.put("price", price);
            promContext.put("zoneNum", amount);

            boolean flag=promationManager.addPromation(promation, promContext);

            if(flag){
            	model.addAttribute("message", "addsuccess");
                return "redirect:/goods/searchPromation.html";
            }
            else{
            	model.addAttribute("errormessage", "addfail");
                return "redirect:/goods/searchPromation.html";
            }
        }catch (Exception e) {
        	model.addAttribute("errormessage", "systemerror");
            return "redirect:/goods/searchPromation.html";
        }
    }
    

    @RequestMapping(value = "/addsalegoods")
    public String doAddPortSaleGoods(@ModelAttribute("goods") Goods goods, Model model,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, HttpServletRequest request,
    		@RequestParam("chtype") String chtype) throws Exception {
    	
    	System.out.println("goods查询条件有：   "+goods.toString());    	
    	String pageFlag  = EnumGoodsStatus.ON_SALE.getKey();

        if (EnumGoodsStatus.ON_SALE.getKey().equals(pageFlag)) {
            goods.setGoodsStatus(EnumGoodsStatus.ON_SALE.getKey());
        } else {
            goods.setGoodsStatus(EnumGoodsStatus.ON_DEPOT.getKey());
        }
        goods.setType("");
        if(chtype.equals("setActivityValue")){
        	goods.setIshavegoodsnumber("yes");
        }
        String selectIds = request.getParameter("selectIds");
    	if(StringUtils.isBlank(selectIds)){
    		goods.setSelectIds("");
    	}else{
    		goods.setSelectIds(selectIds);
    	}
    	/*		  上架时间区间条件检索
		String gmtListingStart = goods.getGmtListingStart();
		String gmtListingEnd = goods.getGmtListingEnd();
			if (StringUtil.isBlank(gmtListingStart) && StringUtil.isBlank(gmtListingEnd)) {
				Calendar calendar  =   new  GregorianCalendar();
				calendar.set( Calendar.DATE,  1 );
				gmtListingStart = DateUtil.convertDateToString(calendar.getTime());
				gmtListingEnd = DateUtil.convertDateToString(new Date());
				goods.setGmtListingStart(gmtListingStart);
				goods.setGmtListingEnd(gmtListingEnd);
			}
        */
        // 品牌检索条件
        List<Brand> brandList = brandService.getBrands();
        model.addAttribute("brandList", brandList);
        
        List<Series> seriesList = brandService.getAllSeries();
        model.addAttribute("seriesList", seriesList);
        model.addAttribute("goods", goods);
        
    	QueryPage page = goodsManager.getGoodsListByConditionWithPage(goods, currPage, this.pageSize);
    	if(page != null){
    		List<Goods> goodsList = (List<Goods>)page.getItems();
    		if(goodsList != null && goodsList.size() > 0){
    	        for(Goods temp : goodsList){
    	            try {
    	            	temp.setCatName(categoryManager.getCatFullNameByCatcodeSimple(goods.getCatCode(),
    	                    ">"));
    	            } catch (Exception e) {
    	                log.error(e.getMessage());
    	            }
    	        }
    		}
    		model.addAttribute("query", page);
    	}
    	
        
//       model.addAttribute("catList", categoryManager.getCatInfoByDepth(1));
        model.addAttribute("chtype", chtype);

        return "/goods/addSaleGoods";

    }
    
    @RequestMapping(value = "/addGift", method = RequestMethod.POST)
    public String addGiftToBuy(@ModelAttribute("promation") Promation promation, BindingResult result, Model model,HttpServletRequest request) {
    	
    	String buyGoodsId = request.getParameter("buyGoodsId");
    	String giftGoodsIds = request.getParameter("giftGoodsIds");
    		
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //	   SimpleDateFormat df = new SimpleDateFormat("h");
        Date date1 = null;
        Date date2 = null;
        //	   String ss = "";
        try {
            date1 = format.parse(promation.getStartDate_str() + " " + promation.getStartDate_hour()
                                 + ":00:00");
            date2 = format.parse(promation.getEndDate_str() + " " + promation.getEndDate_hour()
                                 + ":00:00");
            //			ss = df.format(date1);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        promation.setStartDate(date1);
        promation.setEndDate(date2);
        
        Promation promationnew = new Promation();
        PromContext promContext = new PromContext();

        promationnew.setModeCode("sale_give");
        promationnew.setName(promation.getName());
        promationnew.setIsFreeze("no");
        promationnew.setStartDate(promation.getStartDate());
        promationnew.setEndDate(promation.getEndDate());
        promContext.put("goods.id", buyGoodsId);
        promContext.put("send_goods_id", giftGoodsIds);
        

        boolean flag = promationManager.addPromation(promationnew, promContext);
        if (flag) {
        	model.addAttribute("message", "addsuccess");
            return "redirect:/goods/searchPromation.html";
        } else {
        	model.addAttribute("errormessage", "addfail");
            return "redirect:/goods/searchPromation.html";
        }
    }
    
    @RequestMapping(value = "/addsale", method = RequestMethod.POST)
    public String addPortSale(@ModelAttribute("promation") Promation promation, BindingResult result, Model model,HttpServletRequest request) {
    	
    	String goodsIds = request.getParameter("goodsIds");
    	
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(promation.getStartDate_str() + " " + promation.getStartDate_hour()
                                 + ":00:00");
            date2 = format.parse(promation.getEndDate_str() + " " + promation.getEndDate_hour()
                                 + ":00:00");
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        promation.setStartDate(date1);
        promation.setEndDate(date2);

        String[] ids = goodsIds.split(";");

        Promation promationnew = new Promation();
        PromContext promContext = new PromContext();
        promationnew.setModeCode(EnumPromationModeCode.COMBINED_SALE.getKey());
        promationnew.setName(promation.getName());
        promationnew.setIsFreeze("no");
        promationnew.setStartDate(promation.getStartDate());
        promationnew.setEndDate(promation.getEndDate());
        promContext.put("price", promation.getPrice());
        List list = new java.util.ArrayList();
        for (int i = 0; i < ids.length; i++) {
            list.add(ids[i]);
        }
        promContext.put("goodsIds", list);

        boolean flag = promationManager.addPromation(promationnew, promContext);
        if (flag) {
        	model.addAttribute("message", "addsuccess");
            return "redirect:/goods/searchPromation.html";
        } else {
        	model.addAttribute("errormessage", "addfail");
            return "redirect:/goods/searchPromation.html";
        }

    }
    
    /**
     * ������Ͱ����ײ�
     * @return 
     */
    @RequestMapping(value = "/addExemptPostage", method = RequestMethod.POST)
    public String addExemptPostageToBuy(@ModelAttribute("promation") Promation promation, BindingResult result, Model model,HttpServletRequest request) {
    	
    	String goodsIds = request.getParameter("goodsIds");
    	
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(promation.getStartDate_str() + " " + promation.getStartDate_hour()
                                 + ":00:00");
            date2 = format.parse(promation.getEndDate_str() + " " + promation.getEndDate_hour()
                                 + ":00:00");
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        promation.setStartDate(date1);
        promation.setEndDate(date2);

        String[] ids = goodsIds.split(";");

        Promation promationnew = new Promation();
        PromContext promContext = new PromContext();
        promationnew.setModeCode(EnumPromationModeCode.SALE_EXEMPT_POSTAGE.getKey());
        promationnew.setName(promation.getName());
        promationnew.setIsFreeze("no");
        promationnew.setStartDate(promation.getStartDate());
        promationnew.setEndDate(promation.getEndDate());
        List list = new java.util.ArrayList();
        for (int i = 0; i < ids.length; i++) {
            list.add(ids[i]);
        }
        promContext.put("goodsIds", list);

        boolean flag = promationManager.addPromation(promationnew, promContext);
        if (flag) {
        	model.addAttribute("message", "addsuccess");
            return "redirect:/goods/searchPromation.html";
        } else {
        	model.addAttribute("errormessage", "addfail");
            return "redirect:/goods/searchPromation.html";
        }
    }
    

    /**
     * �������۴����ײ�
     * @return TODO
     */
    @RequestMapping(value = "/addDiscount", method = RequestMethod.POST)
    public String addDiscountToBuy(@ModelAttribute("promation") Promation promation, BindingResult result, Model model,HttpServletRequest request) {
    	
    	String goodsIds = request.getParameter("goodsIds");
    	
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(promation.getStartDate_str() + " " + promation.getStartDate_hour()
                                 + ":00:00");
            date2 = format.parse(promation.getEndDate_str() + " " + promation.getEndDate_hour()
                                 + ":00:00");
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        promation.setStartDate(date1);
        promation.setEndDate(date2);

        String[] ids = goodsIds.split(";");

        Promation promationnew = new Promation();
        PromContext promContext = new PromContext();
        promationnew.setModeCode(EnumPromationModeCode.DISCOUNT.getKey());
        promationnew.setName(promation.getName());
        promationnew.setIsFreeze("no");
        promationnew.setStartDate(promation.getStartDate());
        promationnew.setEndDate(promation.getEndDate());
        List list = new java.util.ArrayList();
        for (int i = 0; i < ids.length; i++) {
            list.add(ids[i]);
        }
        promContext.put("goodsIds", list);
        promContext.put("price", promation.getPrice());//�ۿ�����

        boolean flag = promationManager.addPromation(promationnew, promContext);
        if (flag) {
        	model.addAttribute("message", "addsuccess");
            return "redirect:/goods/searchPromation.html";
        } else {
        	model.addAttribute("errormessage", "addfail");
            return "redirect:/goods/searchPromation.html";
        }

    }
    

    /**
     * �޸�����͹���
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateFullGive", method = RequestMethod.POST)
    public String updateFullGive(@ModelAttribute("promation") Promation promation, BindingResult result, Model model,HttpServletRequest request) throws Exception {

        String promationType = request.getParameter("promationType");
        String scope = request.getParameter("scope");
        String amountStr = request.getParameter("amount");
        int amount = Integer.valueOf(amountStr);
    	
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(promation.getStartDate_str() + " " + promation.getStartDate_hour()
                                 + ":00:00");
            date2 = format.parse(promation.getEndDate_str() + " " + promation.getEndDate_hour()
                                 + ":00:00");
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        promation.setStartDate(date1);
        promation.setEndDate(date2);
    	

        try{
            PromContext promContext=new PromContext();
            promation.setName(promation.getName().trim());
            List<Promation> newPromation = promationManager.getPromationByName(promation.getName());
            if(newPromation!=null&&newPromation.size()>1){
            	model.addAttribute("errormessage", "same");
            	updatePromation(promation, String.valueOf(promation.getId()),model);
                return "redirect:/goods/updatePromation.html";
            }
            if(newPromation!=null&&newPromation.size()==1&&newPromation.get(0).getId()!=promation.getId()){

            }
            promation.setModeCode("full_give");
            List lowerPrice=new java.util.ArrayList();
            List topPrice=new java.util.ArrayList();
            List send_goods_ids=new java.util.ArrayList();
            
            // ѡ���ײ�ʹ�÷�Χ
            if("cat".equals(scope)){
            	if(request.getParameter("selectCode") == null 
            			|| request.getParameter("selectCode") == ""){
                	model.addAttribute("errormessage", "areaerror");
                	updatePromation(promation, String.valueOf(promation.getId()),model);
                    return "redirect:/goods/updatePromation.html";
            	}else{
            		promContext.put("scope", "cat="+request.getParameter("selectCode"));
            	}
            }else if("brand".equals(scope)){
            	if(request.getParameter("selectBrand") == null 
            			|| request.getParameter("selectBrand") == ""){
                	model.addAttribute("errormessage", "branderror");
                	updatePromation(promation, String.valueOf(promation.getId()),model);
                    return "redirect:/goods/updatePromation.html";
            	}else{
            		promContext.put("scope", "brand="+request.getParameter("selectBrand"));
            	}
            }else{
            	promContext.put("scope", "all=all");
            }

            if(amount!=0){
                for(int i=0;i<amount;i++){
                    lowerPrice.add(request.getParameter("money0"+i));
                    topPrice.add(request.getParameter("money1"+i));
                    send_goods_ids.add(request.getParameter("attrId"+i));
                }

            }

            // ����ͷֽ������ͺ����������
            promContext.put("fullGiveType", promationType);
            if("numberFull".equals(promationType)){
            	promContext.put("lowerNumber", lowerPrice);
                promContext.put("topNumber", topPrice);
            }else{
            	promContext.put("lowerPrice", lowerPrice);
                promContext.put("topPrice", topPrice);
            }            
            promContext.put("send_goods_ids", send_goods_ids);
            promContext.put("zoneNum", amount);

            boolean flag=promationManager.editPromation(promation, promContext);
            if(flag){
            	model.addAttribute("message", "updatesuccess");
                return "redirect:/goods/searchPromation.html";
            }
            else{
            	model.addAttribute("errormessage", "updatefail");
                return "redirect:/goods/searchPromation.html";
            }
        }catch (Exception e) {
        	model.addAttribute("errormessage", "systemerror");
            return "redirect:/goods/searchPromation.html";
        }


    }
    
    /**
     * �޸���ͼ�����
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateFullReduce", method = RequestMethod.POST)
    public String updateFullReduce(@ModelAttribute("promation") Promation promation, BindingResult result, Model model,HttpServletRequest request) throws Exception {

        String amountStr = request.getParameter("amount");
        int amount = Integer.valueOf(amountStr);
        try{
            promation.setName(promation.getName().trim());
            List<Promation> newPromation = promationManager.getPromationByName(promation.getName());
            if(newPromation!=null&&newPromation.size()>1){
             	model.addAttribute("errormessage", "same");
            	updatePromation(promation, String.valueOf(promation.getId()),model);
                return "redirect:/goods/updatePromation.html";
            }
            if(newPromation!=null&&newPromation.size()==1&&newPromation.get(0).getId()!=promation.getId()){
            	model.addAttribute("errormessage", "same");
            	updatePromation(promation, String.valueOf(promation.getId()),model);
                return "redirect:/goods/updatePromation.html";
            }
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = format.parse(promation.getStartDate_str() + " " + promation.getStartDate_hour()
                                     + ":00:00");
                date2 = format.parse(promation.getEndDate_str() + " " + promation.getEndDate_hour()
                                     + ":00:00");
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
            promation.setStartDate(date1);
            promation.setEndDate(date2);
            PromContext promContext=new PromContext();
            promation.setModeCode("full_reduce");
            List lowerPrice=new java.util.ArrayList();
            List topPrice=new java.util.ArrayList();
            List price=new java.util.ArrayList();
            if(amount!=0){
                for(int i=0;i<amount;i++){
                    lowerPrice.add(request.getParameter("money0"+i));
                    topPrice.add(request.getParameter("money1"+i));
                    price.add(request.getParameter("money2"+i));
                }
            }
            promContext.put("lowerPrice", lowerPrice);
            promContext.put("topPrice", topPrice);
            promContext.put("price", price);
            promContext.put("zoneNum", amount);

            boolean flag=promationManager.editPromation(promation, promContext);
            

            if(flag){
            	model.addAttribute("message", "updatesuccess");
                return "redirect:/goods/searchPromation.html";
            }
            else{
            	model.addAttribute("errormessage", "updatefail");
                return "redirect:/goods/searchPromation.html";
            }
        }catch (Exception e) {
        	model.addAttribute("errormessage", "systemerror");
            return "redirect:/goods/searchPromation.html";
        }
    }
    
    @RequestMapping(value = "/updatesale", method = RequestMethod.POST)
	private String doUpdatePortAndExemptSale(@ModelAttribute("promation") Promation promation, BindingResult result, Model model,HttpServletRequest request) {
    	
        String goodsIds = request.getParameter("goodsIds");
        String promationId = request.getParameter("promation.id");
        promation.setId(Long.parseLong(promationId));
    	
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(promation.getStartDate_str() + " " + promation.getStartDate_hour()
                                 + ":00:00");
            date2 = format.parse(promation.getEndDate_str() + " " + promation.getEndDate_hour()
                                 + ":00:00");
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        promation.setStartDate(date1);
        promation.setEndDate(date2);

        String[] ids = goodsIds.split(";");

        Promation promationnew = new Promation();
        PromContext promContext = new PromContext();
        promationnew.setId(promation.getId());
        promationnew.setModeCode(EnumPromationModeCode.COMBINED_SALE.getKey());
        promationnew.setName(promation.getName());
        promationnew.setIsFreeze("no");
        promationnew.setStartDate(promation.getStartDate());
        promationnew.setEndDate(promation.getEndDate());
        promContext.put("price", promation.getPrice());
        List list = new java.util.ArrayList();
        for (int i = 0; i < ids.length; i++) {
            list.add(ids[i]);
        }
        promContext.put("goodsIds", list);

        Boolean flag = promationManager.editPromation(promationnew, promContext);
        if (flag) {
        	model.addAttribute("message", "updatesuccess");
            return "redirect:/goods/searchPromation.html";
        } else {
        	model.addAttribute("errormessage", "updatefail");
            return "redirect:/goods/searchPromation.html";
        }
	}
    
    @RequestMapping(value = "/updategift", method = RequestMethod.POST)
    public String updateGiftsToBuy(@ModelAttribute("promation") Promation promation, BindingResult result, Model model,HttpServletRequest request) {

    	
    	String buyGoodsId = request.getParameter("buyGoodsId");
    	String giftGoodsIds = request.getParameter("giftGoodsIds");
    	
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(promation.getStartDate_str() + " " + promation.getStartDate_hour()
                                 + ":00:00");
            date2 = format.parse(promation.getEndDate_str() + " " + promation.getEndDate_hour()
                                 + ":00:00");
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        promation.setStartDate(date1);
        promation.setEndDate(date2);

        Promation promationnew = new Promation();
        PromContext promContext = new PromContext();
        promationnew.setId(promation.getId());
        promationnew.setModeCode("sale_give");
        promationnew.setName(promation.getName());
        promationnew.setIsFreeze("no");
        promationnew.setStartDate(promation.getStartDate());
        promationnew.setEndDate(promation.getEndDate());
        promContext.put("goods.id", buyGoodsId);
        promContext.put("send_goods_id", giftGoodsIds);

        Boolean flag = promationManager.editPromation(promationnew, promContext);
        if (flag) {
        	model.addAttribute("message", "updatesuccess");
            return "redirect:/goods/searchPromation.html";
        } else {
        	model.addAttribute("errormessage", "updatefail");
            return "redirect:/goods/searchPromation.html";
        }
    }
    
    @RequestMapping(value = "/updateEp", method = RequestMethod.POST)
	private String doExemptSale(@ModelAttribute("promation") Promation promation, BindingResult result, Model model,HttpServletRequest request) {
    	
    	String goodsIds = request.getParameter("goodsIds");
    	
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(promation.getStartDate_str() + " " + promation.getStartDate_hour()
                                 + ":00:00");
            date2 = format.parse(promation.getEndDate_str() + " " + promation.getEndDate_hour()
                                 + ":00:00");
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        promation.setStartDate(date1);
        promation.setEndDate(date2);

        String[] ids = goodsIds.split(";");

        Promation promationnew = new Promation();
        PromContext promContext = new PromContext();
        promationnew.setId(promation.getId());
        promationnew.setModeCode(EnumPromationModeCode.SALE_EXEMPT_POSTAGE.getKey());
        promationnew.setName(promation.getName());
        promationnew.setIsFreeze("no");
        promationnew.setStartDate(promation.getStartDate());
        promationnew.setEndDate(promation.getEndDate());
        promContext.put("price", promation.getPrice());
        List list = new java.util.ArrayList();
        for (int i = 0; i < ids.length; i++) {
            list.add(ids[i]);
        }
        promContext.put("goodsIds", list);

        Boolean flag = promationManager.editPromation(promationnew, promContext);
        if (flag) {
        	model.addAttribute("message", "updatesuccess");
            return "redirect:/goods/searchPromation.html";
        } else {
        	model.addAttribute("errormessage", "updatefail");
            return "redirect:/goods/searchPromation.html";
        }
        
	}
    
    @RequestMapping(value = "/updateDiscount", method = RequestMethod.POST)
	private String doDiscount(@ModelAttribute("promation") Promation promation, BindingResult result, Model model,HttpServletRequest request) {
    	
    	String goodsIds = request.getParameter("goodsIds");
    	
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(promation.getStartDate_str() + " " + promation.getStartDate_hour()
                                 + ":00:00");
            date2 = format.parse(promation.getEndDate_str() + " " + promation.getEndDate_hour()
                                 + ":00:00");
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        promation.setStartDate(date1);
        promation.setEndDate(date2);

        String[] ids = goodsIds.split(";");

        Promation promationnew = new Promation();
        PromContext promContext = new PromContext();
        promationnew.setId(promation.getId());
        promationnew.setModeCode(EnumPromationModeCode.DISCOUNT.getKey());
        promationnew.setName(promation.getName());
        promationnew.setIsFreeze("no");
        promationnew.setStartDate(promation.getStartDate());
        promationnew.setEndDate(promation.getEndDate());
        promContext.put("price", promation.getPrice());
        List list = new java.util.ArrayList();
        for (int i = 0; i < ids.length; i++) {
            list.add(ids[i]);
        }
        promContext.put("goodsIds", list);

        Boolean flag = promationManager.editPromation(promationnew, promContext);
        if (flag) {
        	model.addAttribute("message", "updatesuccess");
            return "redirect:/goods/searchPromation.html";
        } else {
        	model.addAttribute("errormessage", "updatefail");
            return "redirect:/goods/searchPromation.html";
        }
        
	}


}
