package com.huaixuan.network.web.action.goods;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.dao.goods.AvailableStockDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.goods.AttributeDTO;
import com.huaixuan.network.biz.domain.goods.AvailableStock;
import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.goods.GoodsInstanceSupplier;
import com.huaixuan.network.biz.domain.goods.GoodsWholsale;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumDepositoryType;
import com.huaixuan.network.biz.enums.hy.EnumBrandType;
import com.huaixuan.network.biz.enums.hy.EnumSeriesType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceSupplierManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.goods.GoodsWholsaleManager;
import com.huaixuan.network.biz.service.shop.BrandService;
import com.huaixuan.network.biz.service.storage.DepLocationManager;
import com.huaixuan.network.common.util.EmallStringUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
@RequestMapping(value = "/instance")
public class GoodsInstanceAction extends BaseAction {

    @Autowired
    private CategoryManager categoryManager;

    @Autowired
    private GoodsManager goodsManager;

    @Autowired
    private GoodsInstanceManager goodsInstanceManager;

    @Autowired
    private BrandService  brandService;

    @Autowired
    private AttributeManager attributeManager;

    @Autowired
    private Validator instanceAddValidator;

    private static final String AttrStart = "isattr_";

    @Autowired
    private GoodsWholsaleManager goodsWholsaleManager;

    @Autowired
    private GoodsBatchManager goodsBatch;

    protected Log  log = LogFactory.getLog(this.getClass());

    @Autowired
    private Validator instanceEditValidator;

    @Autowired
    private DepLocationManager depLocationManager;

    @Autowired
    private AvailableStockDao availableStockDao;

    @Autowired
    private GoodsInstanceSupplierManager goodsInstanceSupplierManager;

    @Autowired
    private Validator instanceSupplierAddValidator;
    
	private @Value("${liangpin99.url}")
	String liangpin99url;


    @RequestMapping("/goods_instance")
    public String goods_instance(@RequestParam("gid") String goodsId, Model model) throws Exception{
        if (StringUtil.isBlank(goodsId) || StringUtil.isEmpty(goodsId)) {
        	model.addAttribute("message", "商品不存在");
            return "/goods/goods_error";
        }

        Goods goods = goodsManager.getGoods(Long.parseLong(goodsId));
        if(goods != null){
        	goods.setCatName(categoryManager.getCatFullNameByCatcodeSimple(goods.getCatCode(), ">"));
        	Brand brand = brandService.getBrand(goods.getBrandId());
        	if(brand != null){
        		goods.setBrandName(brand.getBrandName());
        	}
        }

        List<GoodsInstance> goodsInstances = goodsInstanceManager.findGoodsInstances(Long.parseLong(goodsId));
        model.addAttribute("goodsInstances", goodsInstances);
        model.addAttribute("goods", goods);
        model.addAttribute("attributeManager", attributeManager);
        return "goods/instancelist";
    }

    @RequestMapping("/initnew_instance")
    public String initnew_instance(@RequestParam("goodsId") String goodsId,@ModelAttribute("instance") GoodsInstance instance,Model model,
    		HttpServletRequest request) throws Exception{
        if (StringUtil.isBlank(goodsId) || StringUtil.isEmpty(goodsId)) {
        	model.addAttribute("message", "商品不存在");
            return "/goods/goods_error";
        }

        Goods goods = goodsManager.getGoods(Long.parseLong(goodsId));
        if(goods != null){
        	goods.setCatName(categoryManager.getCatFullNameByCatcodeSimple(goods.getCatCode(), ">"));
        	Brand brand = brandService.getBrand(goods.getBrandId());
        	if(brand != null){
        		goods.setBrandName(brand.getBrandName());
        	}
        }

        instance.setCatCode(goods.getCatCode());
        instance.setGoodsId(goods.getId());

        List<AttributeDTO> attrs = attributeManager.getAttributeDTOByCatCode(goods.getCatCode());
        for(AttributeDTO attr : attrs){
        	if(attr.getIsInstance() == 1){
        		Map attrValueMap = new LinkedHashMap();
        		attrValueMap.clear();
        		String[] attrValues = attr.getValues();
        		String[] attrCode = request.getParameterValues(AttrStart + attr.getAttrCode());
        		for(String temp : attrValues){
        			if(attrCode != null && attrCode.length > 0){
            			for(String tmp : attrCode){
            				attrValueMap.put(temp, "0");
            				if(temp.trim().equals(tmp)){
            					attrValueMap.put(temp, "1");
            					break;
            				}
            			}
        			}else{
        				attrValueMap.put(temp, "0");
        			}
        		}
        		attr.setAttrValueMap(attrValueMap);
        	}
        }

        model.addAttribute("instance", instance);
        model.addAttribute("goods", goods);
        model.addAttribute("attrs", attrs);
    	return "/goods/newinstance";
    }

    /**生成产品属性信息
     * @throws Exception
     */
    private void buildAttrs(GoodsInstance instance, HttpServletRequest request) throws Exception {
        String catCode = instance.getCatCode();
        List<AttributeDTO> attributeList = attributeManager.getAttributeDTOByCatCode(catCode);
        //属性值集合 属性集描述
        StringBuffer attrValue = new StringBuffer();
        StringBuffer attrDesc = new StringBuffer();
        for (AttributeDTO attributeDTO : attributeList) {
            String paramValue = request.getParameter(AttrStart + attributeDTO.getAttrCode());
            if (StringUtil.isNotBlank(paramValue)) {
                attrValue.append(attributeDTO.getAttrCode() + "=" + convertStr(paramValue));
                attrDesc.append(attributeDTO.getAttrName() + "=" + convertStr(paramValue));
                attrValue.append(";");
                attrDesc.append(";");
            }
        }
        if (null != attrDesc && attrDesc.length() > 0)
            instance.setAttrDesc(attrDesc.toString());

        if (null != attrValue && attrValue.length() > 0)
            instance.setAttrs(attrValue.toString());
    }

    private String convertStr(String paramValue) {
        if (paramValue == null)
            return null;
        String temp = paramValue.trim();
        temp = EmallStringUtil.escapeGoodsAttrFilter(temp);
        return temp;

    }

    /**
     * 新增产品
     * @param instance
     * @param result
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create_instance", method = RequestMethod.POST)
    public String createInstance(@ModelAttribute("instance") GoodsInstance instance, BindingResult result,
    		Model model, HttpServletRequest request) throws Exception {

    	instanceAddValidator.validate(instance, result);
    	if(result.hasErrors()){
    		initnew_instance(instance.getGoodsId().toString(),instance, model, request);
    		return "/goods/newinstance";
    	}

        buildAttrs(instance,request);

        List<GoodsInstance> goodsInstanceList = goodsInstanceManager.findGoodsInstances(instance.getGoodsId());
        boolean falg = false;
        for (GoodsInstance list : goodsInstanceList) {
            falg = compareInstanceDesc(instance.getAttrs(), list.getAttrs());
            if (falg) {
                result.rejectValue("attrs", "", "相同属性产品已存在");
        		initnew_instance(instance.getGoodsId().toString(),instance, model, request);
        		return "/goods/newinstance";
            }
        }

        goodsInstanceManager.createGoodsInstance(instance);
        model.addAttribute("gid", instance.getGoodsId());
        model.addAttribute("message", "addsuccess");
        return "redirect:/instance/goods_instance.html";
    }

    /**
     * 比较所选择的属性是否相同
     */
    public boolean compareInstanceDesc(String attrs, String instanceAttrs) {
        if (null == attrs && null == instanceAttrs) {
            return true;
        }
        if (null != attrs && null == instanceAttrs) {
            return false;
        }
        if (null == attrs && null != instanceAttrs) {
            return false;
        }
        String[] attr = attrs.trim().split(";");
        String[] instanceAttr = instanceAttrs.trim().split(";");
        boolean attrFlag = true;
        for (String attrVale : attr) {
            if (StringUtil.isNotEmpty(attrVale)) {
                boolean flag = false;
                for (String instanceVale : instanceAttr) {
                    if (StringUtil.isNotEmpty(instanceVale)) {
                        if (instanceVale.equalsIgnoreCase(attrVale.trim())) {
                            flag = true;
                            break;
                        }
                    }
                }
                if (!flag) {
                    attrFlag = false;
                    return false;
                }
            }
        }
        if (attrFlag) {
            return true;
        } else {
            return false;
        }
    }

    @AdminAccess({EnumAdminPermission.A_GOODS_INSTANCE_VIEW_USER})
    @RequestMapping("/instance_search_init")
    public String initSearch(@ModelAttribute("instance") GoodsInstance instance, Model model,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage) throws Exception{
    	QueryPage page = goodsInstanceManager.getInstanceListByConditionWithPage(instance, currPage, this.pageSize);
    	if(page != null){
    		model.addAttribute("query", page);
    	}

    	List<Category> catList = categoryManager.getCatInfoByDepth(1);
    	model.addAttribute("catList", catList);
    	model.addAttribute("categoryManager", categoryManager);
    	model.addAttribute("attributeManager", attributeManager);
    	
    	//取得品牌列表，用于检索条件
        List<Brand> brandList = brandService.getBrands();
        model.addAttribute("brandList", brandList);
        
    	return "/goods/instancesearch";
    }

    @RequestMapping("/instance_search")
    public String instanceSearch(@ModelAttribute("instance") GoodsInstance instance, Model model,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage) throws Exception{
    	QueryPage page = goodsInstanceManager.getInstanceListByConditionWithPage(instance, currPage, this.pageSize);
    	if(page != null){
    		model.addAttribute("query", page);
    	}

    	List<Category> catList = categoryManager.getCatInfoByDepth(1);
    	model.addAttribute("catList", catList);
    	model.addAttribute("categoryManager", categoryManager);
    	model.addAttribute("attributeManager", attributeManager);
    	
    	//取得品牌列表，用于检索条件
        List<Brand> brandList = brandService.getBrands();
        model.addAttribute("brandList", brandList);
        
    	return "/goods/instancesearch";
    }

    /**
     * 产品excel导出
     *
     * @throws Exception
     */
    @RequestMapping(value = "do_export_instance")
    public String doExportGoodsInstance(Model model, HttpServletRequest request, HttpServletResponse res,
    		@ModelAttribute("instance") GoodsInstance instance) throws Exception {
        OutputStream outwt = null;
        try {
            Date da = new Date();
            outwt = res.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            res.setHeader("Content-disposition", "attachment; filename=goodsInstance" + date
                                                 + ".xls");
            res.setContentType("application/octet-stream;charset=utf-8");
            List<GoodsInstance> goodsInstances = null;
            if (null != instance) {
                goodsInstances = goodsInstanceManager.getInstanceListByConditionWithPage(instance);
            }
            List<String[]> goodsInstanceList = new ArrayList<String[]>();
            String[] title = { "商品编号","品牌","品名", "型号","材质","颜色","总库存", "香港库存", "已售数量","尚上价（RMB）","LUSSO&MODA价（HKD）","欧洲价（EU）","尺寸","图片" };
            goodsInstanceList.add(title);
//            String catName = null, goodsName = null;
            Map<String,String> brandEnumMap = EnumBrandType.toMap();
            Map<String,String> seriesEnumMap = EnumSeriesType.toMap();
            for (GoodsInstance goodsInstance : goodsInstances) {
//                catName = categoryManager.getCatFullNameByCatcodeSimple(goodsInstance.getCatCode(),
//                    ">");
//                Goods goods = goodsManager.getGoods(goodsInstance.getGoodsId());
//                List<GoodsWholsale> list = goodsWholsaleManager.getGoodsWholsalelistByGoodsId(goodsInstance.getGoodsId());
//                String wholesalePrice = "";
//                if(list != null){
//                	for(GoodsWholsale obj:list){
//                		wholesalePrice += obj.getStartNum()+"件起 "+obj.getWholesalePrice()+"元/件 ";
//                	}
//                }
//                if (null != goods)
//                    goodsName = goods.getTitle();
            	String[] gInstance = {
                		goodsInstance.getGoodsId()+"",
                		brandEnumMap.get(goodsInstance.getBrandId().toString())+ "", 
                		seriesEnumMap.get(goodsInstance.getIdSeries().toString())+ "",
                		goodsInstance.getType()+ "", goodsInstance.getMaterial()+ "", goodsInstance.getColor()+ "",
                        goodsInstance.getExistNum()+"",
                        goodsInstance.getHkExistNum()+"",
                        goodsInstance.getSellNum()+"",
                        goodsInstance.getSellPrice()+"",
                        goodsInstance.getHkhxPrice()+ "",
                        goodsInstance.getEuPrice()+ "",
                        goodsInstance.getAttrDesc(),
                        goodsInstance.getImgMiddle()};
//                catName = null;
//                goodsName = null;
                goodsInstanceList.add(gInstance);
            }
            goodsBatch.exportExcelWidthPic(outwt, goodsInstanceList);
            outwt.flush();
        } catch (Exception e) {
            model.addAttribute("message", "产品导出失败");
            log.error(e);
        } finally {
            outwt.close();
        }

        return "/goods/instancesearch";

    }

    /**
     * 修改产品
     * @throws Exception
     */
    @AdminAccess({EnumAdminPermission.A_GOODS_INSTANCE_MODIFY_USER})
    @RequestMapping(value = "load_instance")
    public String loadinstance(@RequestParam(value = "id") String instanceId, @RequestParam(value = "gid") String goodsId,Model model,
    		HttpServletRequest request) throws Exception{
		GoodsInstance instance = goodsInstanceManager.getInstance(Long.parseLong(instanceId));
		// start - 关联淘宝产品
		//通过该产品的商品id找到该商品下的所有产品的销售属性组
		List<String> skuPropertyList = this.goodsInstanceManager.getSkuPropertyListByGoodsId(instance.getGoodsId());

		//通过该产品找到相关商品
		Goods goods = goodsManager.getGoods(instance.getGoodsId());
        if(goods != null){
        	goods.setCatName(categoryManager.getCatFullNameByCatcodeSimple(goods.getCatCode(), ">"));
        	Brand brand = brandService.getBrand(goods.getBrandId());
        	if(brand != null){
        		goods.setBrandName(brand.getBrandName());
        	}
        }

        instance.setCatCode(goods.getCatCode());
        instance.setGoodsId(goods.getId());

        List<AttributeDTO> attrs = attributeManager.getAttributeDTOByCatCode(goods.getCatCode());
        for(AttributeDTO attr : attrs){
        	if(attr.getIsInstance() == 1){
        		Map attrValueMap = new LinkedHashMap();
        		attrValueMap.clear();
        		String[] attrValues = attr.getValues();
        		for(String temp : attrValues){
        			if(instance.haveAttr(attr.getAttrCode(), temp)){
        				attrValueMap.put(temp, "1");
        			}else{
        				attrValueMap.put(temp, "0");
        			}
        		}
        		attr.setAttrValueMap(attrValueMap);
        	}
        }

		//构造属性组id数组
		String taobaoSkuProp = goods.getTaobaoSkuProp();
		String[] taobaoSkuPropArr = new String[0];
		if (taobaoSkuProp != null)
			taobaoSkuPropArr = taobaoSkuProp.split(",");

		//构造属性组名字数组
		String taobaoSkuPropName = goods.getTaobaoSkuPropName();
		String[] taobaoSkuPropNameArr = new String[0];
		if (taobaoSkuPropName != null)
			taobaoSkuPropNameArr = taobaoSkuPropName.split(",");

		//销售属性组串的id与名字的数目必须相等，且至少有一个属性组，否则不显示关联下拉框
		if (taobaoSkuPropArr.length == taobaoSkuPropNameArr.length && taobaoSkuPropArr.length > 0) {
			Map<String, String> skuMap = new HashMap<String, String>();
			for (int i = 0; i < taobaoSkuPropArr.length; i++) {
					//只有没有加到产品中的销售属性组才加到关联下拉框，或者这个销售属性等于本产品
					if(!skuPropertyList.contains(taobaoSkuPropArr[i]) || taobaoSkuPropArr[i].equals(instance.getTaobaoSkuProperty())){
						skuMap.put(taobaoSkuPropArr[i], taobaoSkuPropNameArr[i]);
					}
			}
			model.addAttribute("skuMap", skuMap);
		}

		model.addAttribute("instance", instance);
		model.addAttribute("goods", goods);
        model.addAttribute("attrs", attrs);
		// end - 关联淘宝产品
		return "/goods/loadinstance";
    }

    public void initInstance(GoodsInstance instance, Model model) throws Exception{
		// start - 关联淘宝产品
		//通过该产品的商品id找到该商品下的所有产品的销售属性组
		List<String> skuPropertyList = this.goodsInstanceManager.getSkuPropertyListByGoodsId(instance.getGoodsId());

		//通过该产品找到相关商品
		Goods goods = goodsManager.getGoods(instance.getGoodsId());
        if(goods != null){
        	goods.setCatName(categoryManager.getCatFullNameByCatcodeSimple(goods.getCatCode(), ">"));
        	Brand brand = brandService.getBrand(goods.getBrandId());
        	if(brand != null){
        		goods.setBrandName(brand.getBrandName());
        	}
        }

        instance.setCatCode(goods.getCatCode());
        instance.setGoodsId(goods.getId());

        List<AttributeDTO> attrs = attributeManager.getAttributeDTOByCatCode(goods.getCatCode());
        for(AttributeDTO attr : attrs){
        	if(attr.getIsInstance() == 1){
        		Map attrValueMap = new LinkedHashMap();
        		attrValueMap.clear();
        		String[] attrValues = attr.getValues();
        		for(String temp : attrValues){
        			if(instance.haveAttr(attr.getAttrCode(), temp)){
        				attrValueMap.put(temp, "1");
        			}else{
        				attrValueMap.put(temp, "0");
        			}
        		}
        		attr.setAttrValueMap(attrValueMap);
        	}
        }

		//构造属性组id数组
		String taobaoSkuProp = goods.getTaobaoSkuProp();
		String[] taobaoSkuPropArr = new String[0];
		if (taobaoSkuProp != null)
			taobaoSkuPropArr = taobaoSkuProp.split(",");

		//构造属性组名字数组
		String taobaoSkuPropName = goods.getTaobaoSkuPropName();
		String[] taobaoSkuPropNameArr = new String[0];
		if (taobaoSkuPropName != null)
			taobaoSkuPropNameArr = taobaoSkuPropName.split(",");

		//销售属性组串的id与名字的数目必须相等，且至少有一个属性组，否则不显示关联下拉框
		if (taobaoSkuPropArr.length == taobaoSkuPropNameArr.length && taobaoSkuPropArr.length > 0) {
			Map<String, String> skuMap = new HashMap<String, String>();
			for (int i = 0; i < taobaoSkuPropArr.length; i++) {
					//只有没有加到产品中的销售属性组才加到关联下拉框，或者这个销售属性等于本产品
					if(!skuPropertyList.contains(taobaoSkuPropArr[i]) || taobaoSkuPropArr[i].equals(instance.getTaobaoSkuProperty())){
						skuMap.put(taobaoSkuPropArr[i], taobaoSkuPropNameArr[i]);
					}
			}
			model.addAttribute("skuMap", skuMap);
		}

		model.addAttribute("instance", instance);
		model.addAttribute("goods", goods);
        model.addAttribute("attrs", attrs);
    }

    @RequestMapping(value = "/update_instance", method = RequestMethod.POST)
    public String updateInstance(@ModelAttribute("instance") GoodsInstance instance, BindingResult result, Model model,
    		HttpServletRequest request) throws Exception{
    	instanceEditValidator.validate(instance, result);
    	if(result.hasErrors()){
    		initInstance(instance, model);
    		return "/goods/loadinstance";
    	}

        buildAttrs(instance,request);

        List<GoodsInstance> goodsInstanceList = goodsInstanceManager.findGoodsInstances(instance.getGoodsId());
        boolean falg = false;
        for (GoodsInstance list : goodsInstanceList) {
        	if(!list.getId().equals(instance.getId())){
        		falg = compareInstanceDesc(instance.getAttrs(), list.getAttrs());
                if (falg) {
                    result.rejectValue("attrs", "", "相同属性产品已存在");
                    initInstance(instance, model);
                    return "/goods/loadinstance";
                }
        	}
        }

        goodsInstanceManager.updateGoodsInstance(instance);
        model.addAttribute("gid", instance.getGoodsId());
        model.addAttribute("message", "editsuccess");
        return "redirect:/instance/goods_instance.html";
    }


    @RequestMapping(value = "instance_location")
    public String instancelocation(@RequestParam(value = "id") String instanceId, @RequestParam(value = "gid") String goodsId,Model model) throws Exception{
        GoodsInstance instance = goodsInstanceManager.getInstance(Long.parseLong(instanceId));
		Goods goods = goodsManager.getGoods(Long.parseLong(goodsId));
        if(goods != null){
        	goods.setCatName(categoryManager.getCatFullNameByCatcodeSimple(goods.getCatCode(), ">"));
        	Brand brand = brandService.getBrand(goods.getBrandId());
        	if(brand != null){
        		goods.setBrandName(brand.getBrandName());
        	}
        }
        Map<String, String> parMap = new HashMap<String, String>();
        parMap.put("status", "v");
        parMap.put("type", EnumDepositoryType.COMMON_DEP.getKey());
        model.addAttribute("locations", depLocationManager.getAllDepLocationByMap(parMap));
        model.addAttribute("existLoc", goodsInstanceManager.getGoodsInstanceLocations(Long.parseLong(instanceId)));
        model.addAttribute("instance", instance);
        model.addAttribute("goods", goods);
        return "/goods/instancelocation";
    }

    @RequestMapping(value = "update_instance_location", method = RequestMethod.POST)
    public String updateInstanceLocation(HttpServletRequest request, Model model) {
        List<Long> los = getLocationIds(request);
        String instanceId = request.getParameter("id");
        String goodsId = request.getParameter("gid");
        goodsInstanceManager.updateGoodsInstanceLocations(Long.parseLong(instanceId), los);
        model.addAttribute("message", "editdepsuccess");
        model.addAttribute("id", instanceId);
        model.addAttribute("gid", goodsId);
        return "redirect:/instance/instance_location.html";
    }

    private List<Long> getLocationIds(HttpServletRequest request) {
        Object obj = request.getParameterValues("loid");
        List<Long> back = new ArrayList<Long>();
        if (obj == null) {
            return back;
        }
        if (obj instanceof String[]) {
            for (String s : (String[]) obj) {
                back.add(Long.parseLong(s));
            }
        } else {

            back.add(Long.parseLong(obj.toString()));
        }
        return back;
    }


    @RequestMapping(value = "instance_supplier")
    public String instanceSuppliers(@RequestParam(value = "id") String instanceId, @RequestParam(value = "gid") String goodsId,Model model) throws Exception {
        GoodsInstance instance = goodsInstanceManager.getInstance(Long.parseLong(instanceId));
		Goods goods = goodsManager.getGoods(Long.parseLong(goodsId));
        if(goods != null){
        	goods.setCatName(categoryManager.getCatFullNameByCatcodeSimple(goods.getCatCode(), ">"));
        	Brand brand = brandService.getBrand(goods.getBrandId());
        	if(brand != null){
        		goods.setBrandName(brand.getBrandName());
        	}
        }

        model.addAttribute("instance", instance);
        model.addAttribute("goods", goods);
        model.addAttribute("suppliers", goodsInstanceSupplierManager.findGoodsInstanceSuppliers(instance.getId()));
        return "/goods/instancesupplier";
    }

    @RequestMapping(value = "update_instance_supplier", method = RequestMethod.POST)
    public String updateInstanceSupplier(@RequestParam(value = "supplier.id") String giSupId,@RequestParam(value = "supplier.supplierCode") String supplierCode,@RequestParam(value = "supplier.consultPrice") String consultPrice,
    		@RequestParam(value = "id") String instanceId, @RequestParam(value = "gid") String goodsId, HttpServletRequest request, Model model) {
    	GoodsInstanceSupplier supplier = goodsInstanceSupplierManager.getGoodsInstanceSupplier(Long.parseLong(giSupId));
    	supplier.setSupplierCode(supplierCode);
    	supplier.setConsultPrice(BigDecimal.valueOf(Double.parseDouble(consultPrice)));
        goodsInstanceSupplierManager.editGoodsInstanceSupplier(supplier);
        model.addAttribute("id", instanceId);
        model.addAttribute("gid", goodsId);
        model.addAttribute("message", "editsuccess");
        return "redirect:/instance/instance_supplier.html";
    }

    @RequestMapping(value = "remove_instance_supplier", method = RequestMethod.POST)
    public String removeInstanceSupplier(@RequestParam(value = "supplier.id") String giSupId,@RequestParam(value = "id") String instanceId,
    		@RequestParam(value = "gid") String goodsId, HttpServletRequest request, Model model) {
        goodsInstanceSupplierManager.removeGoodsInstanceSupplier(Long.parseLong(giSupId));
        model.addAttribute("id", instanceId);
        model.addAttribute("gid", goodsId);
        model.addAttribute("message", "removesuccess");
        return "redirect:/instance/instance_supplier.html";
    }


    @RequestMapping(value = "add_instance_supplier")
    public String addInstanceSupplier(@RequestParam("id") String instanceId,@RequestParam("gid") String goodsId,
    		@ModelAttribute("goodsInstanceSupplier") GoodsInstanceSupplier goodsInstanceSupplier,Model model) throws Exception{
        GoodsInstance instance = goodsInstanceManager.getInstance(Long.parseLong(instanceId));
		Goods goods = goodsManager.getGoods(Long.parseLong(goodsId));
        if(goods != null){
        	goods.setCatName(categoryManager.getCatFullNameByCatcodeSimple(goods.getCatCode(), ">"));
        	Brand brand = brandService.getBrand(goods.getBrandId());
        	if(brand != null){
        		goods.setBrandName(brand.getBrandName());
        	}
        }

        model.addAttribute("instance", instance);
        model.addAttribute("goods", goods);
    	return "/goods/add_instance_supplier";
    }

    @RequestMapping(value = "create_instance_supplier", method = RequestMethod.POST)
    public String createInstanceSupplier(@ModelAttribute("goodsInstanceSupplier") GoodsInstanceSupplier goodsInstanceSupplier, BindingResult result,
    		Model model)throws Exception{
    	instanceSupplierAddValidator.validate(goodsInstanceSupplier, result);
    	if(result.hasErrors()){
    		addInstanceSupplier(goodsInstanceSupplier.getGoodsInstanceId().toString(),goodsInstanceSupplier.getGoodsId().toString(), goodsInstanceSupplier, model);
    		return "/goods/add_instance_supplier";
    	}

    	goodsInstanceSupplierManager.createInstanceSupplier(goodsInstanceSupplier);

    	model.addAttribute("message", "addsuccess");
    	model.addAttribute("id", goodsInstanceSupplier.getGoodsInstanceId().toString());
    	model.addAttribute("gid", goodsInstanceSupplier.getGoodsId().toString());
        return "redirect:/instance/instance_supplier.html";
    }

    @RequestMapping(value = "instance_available_stock")
    public String searchInstanceAvailableStock(@RequestParam(value = "id") String instanceId, @RequestParam(value = "gid") String goodsId,Model model){
        if (StringUtil.isNotBlank(instanceId) && StringUtil.isNumeric(instanceId)) {
            GoodsInstance gs = goodsInstanceManager.getInstance(new Long(instanceId));
            List<AvailableStock> availableStocks = availableStockDao.getAvailableStockListByInstanceId(new Long(instanceId));
            model.addAttribute("availableStocks", availableStocks);
            model.addAttribute("gs", gs);
        }
        return "/goods/instance_available_stock";
    }

    @RequestMapping(value = "select_instance")
    public String searchInstance(@ModelAttribute("instance") GoodsInstance instance, Model model,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,HttpServletRequest request) throws Exception {
    	model.addAttribute("catList", categoryManager.getCatInfoByDepth(1));
    	QueryPage page = goodsInstanceManager.getInstanceListByConditionWithPage(instance, currPage, this.pageSize);
    	if(page != null){
    		model.addAttribute("query", page);
    	}
    	model.addAttribute("attributeManager", attributeManager);
    	model.addAttribute("categoryManager", categoryManager);
    	String muse = request.getParameter("muse");
    	if(StringUtil.isNotEmpty(muse) && StringUtil.isNotBlank(muse)){
    		model.addAttribute("muse", muse);
    	}
        return "/goods/select_instance";
    }

    @RequestMapping(value = "select_suppliler_instance")
    public String searchSupplierInstance(@ModelAttribute("instance") GoodsInstance instance, Model model,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, HttpServletRequest request) throws Exception {
        String shoppingOriCount = request.getParameter("shoppingOriCount");
        String supplierId = request.getParameter("sid");
        if(StringUtil.isNotBlank(shoppingOriCount)){
        	instance.setShoppingOriCount(shoppingOriCount);
        	model.addAttribute("shoppingOriCount", shoppingOriCount);
        }else{
        	model.addAttribute("shoppingOriCount", instance.getShoppingOriCount());
        }
        if(StringUtil.isNotBlank(supplierId)){
        	instance.setSupplierId(Long.parseLong(supplierId));
        }
        model.addAttribute("catList", categoryManager.getCatInfoByDepth(1));
    	QueryPage page = goodsInstanceManager.getSupplierInstanceListByConditionWithPage(instance, currPage, this.pageSize);
    	if(page != null){
    		model.addAttribute("query", page);
    	}
    	model.addAttribute("attributeManager", attributeManager);
    	model.addAttribute("categoryManager", categoryManager);
        return "/goods/select_supplier_instance";
    }


    /**
     * 增加买就赠产品页面 zhangwy
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "addGiftInstanceGoods")
    public String doAddGiftInstanceGoods(@ModelAttribute("instance") GoodsInstance instance, Model model,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, HttpServletRequest request) throws Exception{

        String type = request.getParameter("type");
    	model.addAttribute("catList", categoryManager.getCatInfoByDepth(1));
    	model.addAttribute("type", type);
    	QueryPage page = goodsInstanceManager.getSaleGiftInstanceListByConditionWithPage(instance, currPage, this.pageSize);
    	if(page != null){
    		List<GoodsInstance> goodsInstanceList = (List<GoodsInstance>)page.getItems();
    		if(goodsInstanceList != null && goodsInstanceList.size() > 0){
    	        for(GoodsInstance gi : goodsInstanceList){
    	            try {
    	            	gi.setCatName(categoryManager.getCatFullNameByCatcodeSimple(gi.getCatCode(),
    	                    ">"));
    	            } catch (Exception e) {
    	                log.error(e.getMessage());
    	            }
    	        }
    		}
    		model.addAttribute("query", page);
    	}
    	model.addAttribute("liangpin99url", liangpin99url);
    	return "/goods/addGiftInstanceGoods";
    }


    @RequestMapping(value = "select_back_instance")
    public String selectBackInstance(@ModelAttribute("instance") GoodsInstance instance, Model model,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,HttpServletRequest request) throws Exception{

    	model.addAttribute("catList", categoryManager.getCatInfoByDepth(1));
    	QueryPage page = goodsInstanceManager.getBackInstanceListByConditionWithPage(instance, currPage, this.pageSize);
    	if(page != null){
    		List<GoodsInstance> goodsInstance = (List<GoodsInstance>)page.getItems();
    		if(goodsInstance != null && goodsInstance.size() > 0){
                for (GoodsInstance temp : goodsInstance) {
                    temp.setCatName(categoryManager.getCatFullNameByCatcodeSimple(temp.getCatCode(), ">"));

                }
    		}
    		model.addAttribute("query", page);
    	}
    	model.addAttribute("attributeManager", attributeManager);
    	String type = request.getParameter("type");
    	model.addAttribute("type", type);
    	return "/goods/selectbackInstance";

    }

}
