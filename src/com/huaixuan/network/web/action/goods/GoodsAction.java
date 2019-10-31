package com.huaixuan.network.web.action.goods;

import java.beans.PropertyEditor;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;

import javax.annotation.PostConstruct;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huaixuan.network.biz.dao.goods.CatAttrRelDao;
import com.huaixuan.network.biz.dao.goods.CategoryDao;
import com.huaixuan.network.biz.dao.storage.StockoutDao;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.admin.Resources;
import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.domain.express.Express;
import com.huaixuan.network.biz.domain.goods.Attribute;
import com.huaixuan.network.biz.domain.goods.AttributeDTO;
import com.huaixuan.network.biz.domain.goods.AvailableStock;
import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsGallery;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.goods.Unit;
import com.huaixuan.network.biz.domain.goods.WebSiteEmail;
import com.huaixuan.network.biz.domain.hy.Product;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.domain.shop.Series;
import com.huaixuan.network.biz.domain.storage.Stockout;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumGoodsStatus;
import com.huaixuan.network.biz.enums.EnumPaipaiApi;
import com.huaixuan.network.biz.enums.hy.EnumBrandType;
import com.huaixuan.network.biz.enums.hy.EnumSeriesType;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.ResourcesManager;
import com.huaixuan.network.biz.service.express.ExpressManager;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.goods.GoodsGalleryManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.goods.UploadUtil;
import com.huaixuan.network.biz.service.goods.WebSiteEmailService;
import com.huaixuan.network.biz.service.goods.impl.CategoryManagerImpl;
import com.huaixuan.network.biz.service.goods.impl.GoodsManagerImpl;
import com.huaixuan.network.biz.service.hy.ProductService;
import com.huaixuan.network.biz.service.platformstock.impl.WeiMobPlatFormStockUpdateImpl;
import com.huaixuan.network.biz.service.remote.PaipaiInterfaceManager;
import com.huaixuan.network.biz.service.shop.BrandService;
import com.huaixuan.network.biz.service.stock.AvailableStockService;
import com.huaixuan.network.biz.service.storage.StockoutManager;
import com.huaixuan.network.common.util.EmallStringUtil;
import com.huaixuan.network.common.util.StringTrimUtil;
import com.huaixuan.network.common.util.emisZipUtil;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.huaixuan.network.common.util.remote.TaobaoFenXiaoUtils;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;
import com.taobao.api.TaobaoApiException;
@Controller
@RequestMapping(value = "/goods")
public class GoodsAction extends BaseAction {

	
    @Autowired
    private CategoryManager categoryManager;
    

    @Autowired
	private CategoryDao categoryDao;
    
    @Autowired
    private BrandService  brandService;
    
    @Autowired
    private CatAttrRelDao catAttrRelDao;
    
    @Autowired
    private GoodsManager goodsManager;
    
    @Autowired
    private Validator goodsAddValidator;
    
    @Autowired
    private ExpressManager expressManager;
    
    @Autowired
    private StockoutDao stockoutDao;
    
    @Autowired
    private WebSiteEmailService webSiteEmailService;
    
    @Autowired
    private GoodsGalleryManager goodsGalleryManager;
    
    @Autowired
    private Validator goodsEditValidator;
    
    @Autowired
    private GoodsBatchManager goodsBatch;
    
	@Autowired
	private UploadUtil uploadUtil;
	
	@Autowired
	private ResourcesManager resourcesManager;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private AttributeManager attributeManager;
	
	@Autowired
    private GoodsInstanceManager goodsInstanceManager;
	
	@Autowired
    private AvailableStockService availableStockService;
	

	
	
	
	private @Value("${file.upload.dir}")
	String upload;
    
    protected Log  log = LogFactory.getLog(this.getClass());
    
    @Autowired
    private PaipaiInterfaceManager paipaiInterfaceManager;
    
    @Autowired
    private StockoutManager stockoutManager;
    
	private @Value("${liangpin99.url}")
	String liangpin99url;
    
    private static  int maxSize = 200;  //200KB
    
    
    
    @AdminAccess({EnumAdminPermission.A_GOODS_ADD_USER})
	@RequestMapping("/scat")
	public String selectCat(Model model, HttpServletRequest request) {
		List<Category> categoryList = categoryManager.getCategorys(true);
		model.addAttribute("categoryList", categoryList);
		String muse = request.getParameter("muse");
		model.addAttribute("muse", muse);
		return "/goods/selectCat";
	}
	
	@RequestMapping(value = "sel_category")
	public @ResponseBody List<Category> queryCategory(
			@RequestParam(value = "parentCode", defaultValue = "-1") String parentCode,
			@RequestParam(value = "depth", defaultValue = "1") String depth) {
		Category category = new Category();
		if (StringUtils.isNotBlank(parentCode)) {
			category.setParentCode(parentCode.toString());
		}
		if (StringUtils.isNotBlank(depth)) {
			category.setDepth(Integer.valueOf(depth));
		}
		
		category.setIsShow(1);
		List<Category> catList = categoryManager.getCategoryListByCategory(category);
		if (catList != null) {
			return catList;
		}
		return null;
	}

	@RequestMapping(value = "init_publish")
	public String initPublish(@RequestParam(value = "catCode",defaultValue="") String catCode,@ModelAttribute("goods") Goods goods,
			 Model model) throws Exception{

        String categoryName = "";
        try {
            categoryName = categoryManager.getCatFullNameByCatcodeSimple(catCode, "->");
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "无效类目");
            return "redirect:/goods/scat.html";
        }
        //根据类目选择关联品牌，若没有则选择上一级，若全没有则选择全部 
        List<Category> categoryList = new ArrayList<Category>();
        String catCodetmp = catCode;
        while(!(catCodetmp.equals("-1"))){
        	Category categoryNow = categoryManager.getCategoryByCatCode(catCodetmp);
        	categoryList.add(categoryNow);
        	catCodetmp = categoryNow.getParentCode();
        }
        
        List<Brand>  brandList = new ArrayList<Brand>();
        for(Category tmp:categoryList){
        	brandList = categoryManager.getBrandsByAllCatCode(tmp.getCatCode());
        	if((brandList!=null&&brandList.size()>0)){
        		break;
        	}
        }
        if(brandList == null ||brandList.size() == 0){
        	brandList = brandService.getBrands();
        }
        
        List<AttributeDTO> attributeList = catAttrRelDao.getAttributeDTOByCatCode(catCode);

        if (goods == null){
        	goods = new Goods();
        	goods.setCatCode(catCode);
        }
        
        model.addAttribute("goods", goods);
        model.addAttribute("brandList", brandList);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("attributeList", attributeList);
        
        Category category = categoryManager.getCateInfoByCatCode(catCode);
        double discount =  category.getCommonAgentAgio();
        model.addAttribute("discount", discount);
        
        List<Unit> units = goodsManager.findAllUnits();
        model.addAttribute("units", units);
		return "/goods/publish";
	}
	
    /**
     * 加载拍拍类目 zhangwy
     * @return
     * @throws IOException 
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping(value = "/loadPaipaiClass")
    public @ResponseBody JSONObject loadPaipaiClass(@RequestParam("param") String navigationId) 
         throws UnsupportedEncodingException, IOException{
        Map<String,String> noSignPram = new HashMap<String,String>();
        noSignPram.put("navigationId", navigationId);
        JSONObject paipaiClassList = paipaiInterfaceManager.paipaiClientByJson(null,EnumPaipaiApi.getNavigationChildList,false,noSignPram,null,"json");
        return paipaiClassList;	

    }
    
    /**
     * 加载拍拍属性 zhangwy
     * @return
     * @throws IOException 
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping(value = "/loadPaipaiAttr")
    public @ResponseBody JSONObject loadPaipaiAttr(@RequestParam("param1") String classId, 
    		@RequestParam(value = "param2",defaultValue="undefined") String attrId ,
    		@RequestParam(value = "param3",defaultValue="undefined") String optionId)
        throws UnsupportedEncodingException, IOException{
        if(attrId.equals("undefined")){
     	   attrId ="";
        }
        if(optionId.equals("undefined")){
     	   optionId ="";
        }
        Map<String,String> noSignPram = new HashMap<String,String>();
        noSignPram.put("classId", classId);
        noSignPram.put("option", "1");
        if(StringUtil.isNotBlank(attrId)&& StringUtil.isNotEmpty(attrId)&& StringUtil.isNotBlank(optionId)&& StringUtil.isNotEmpty(optionId)){
            noSignPram.put("attrId", attrId);
            noSignPram.put("optionId", optionId);   
        }
        JSONObject paipaiAttrList = paipaiInterfaceManager.paipaiClientByJson(null,EnumPaipaiApi.getAttributeList,false,noSignPram,null,"json");
    	return paipaiAttrList;
    }
    
    
	/**
	 * 加载淘宝类目 liben
	 * 
	 * @return
	 * @throws TaobaoApiException
	 */
    @RequestMapping(value = "/loadTaobaoClass")
	public @ResponseBody JSONObject loadTaobaoClass(@RequestParam("param") String parentCid) throws TaobaoApiException {
    	JSONObject taobaoClassList = TaobaoFenXiaoUtils.getTaobaoClass(parentCid);
		return taobaoClassList;
	}
    
	/**
	 * 加载淘宝类目属性 liben
	 * 
	 * @return
	 * @throws TaobaoApiException
	 * @throws ParseException 
	 */
    @RequestMapping(value = "/loadTaobaoProps")
	public @ResponseBody JSONObject loadTaobaoProps(@RequestParam("param") String leafCid) throws TaobaoApiException, ParseException {
    	JSONObject propsJson = TaobaoFenXiaoUtils.loadTaobaoProps(leafCid);
		return propsJson;
	}
    
	/**
	 * 加载淘宝类目属性值 liben
	 * 
	 * @return
	 * @throws TaobaoApiException
	 */
    @RequestMapping(value = "/loadTaobaoPropValues")
	public @ResponseBody JSONObject loadTaobaoPropValues(@RequestParam("param") String leafCid) throws TaobaoApiException {
		JSONObject propValuesJson = TaobaoFenXiaoUtils.loadTaobaoPropValues(leafCid);
		return propValuesJson;
	}
    
    /**
     * 发布保存
     *
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/dp", method = RequestMethod.POST)
    public String doPublish(@ModelAttribute("goods") Goods goods, BindingResult result, Model model,
    		MultipartHttpServletRequest request) throws Exception {
        //去除前后空格
        StringTrimUtil.trim(goods);
    	String goodsDesc = request.getParameter("goodsDesc");
    	goods.setGoodsDesc(goodsDesc);
    	goodsAddValidator.validate(goods, result);
    	if(result.hasErrors()){
    		initPublish(goods.getCatCode(), goods, model);
    		return "/goods/publish";
    	}
    	
    	
    	List<AttributeDTO> attributeList = catAttrRelDao.getAttributeDTOByCatCode(goods.getCatCode());
    	
        //属性值集合 属性集描述
        StringBuffer attrValue = new StringBuffer();
        StringBuffer attrDesc = new StringBuffer();
        StringBuffer attrChoose = new StringBuffer();
        for (AttributeDTO attributeDTO : attributeList) {
            if ("checkbox".equalsIgnoreCase(attributeDTO.getInputType().toLowerCase())) {
                String[] paramValue = request.getParameterValues(attributeDTO.getAttrCode());
                if(attributeDTO.getIsBuyerChoose()==0){
                    if (paramValue != null) {
                        attrValue.append(attributeDTO.getAttrCode() + ":");
                        attrDesc.append(attributeDTO.getAttrName() + ":");
                        for (int i = 0; i < paramValue.length; i++) {
                            if (i != 0) {
                                attrValue.append("-");
                                attrDesc.append("-");
                            }
                            attrValue.append(convertStr(paramValue[i]));
                            attrDesc.append(convertStr(paramValue[i]));
                        }
                        attrValue.append(";");
                        attrDesc.append(";");
                    }else{
                        if(1==attributeDTO.getIsNeed()){
                            result.rejectValue("attrValue", "", attributeDTO.getAttrName()+"为必填项");
                    		initPublish(goods.getCatCode(), goods, model);
                    		return "/goods/publish";
                        }
    
                    }
               }else{
                   if (paramValue != null) {
                       attrChoose.append(attributeDTO.getAttrCode());
                       attrChoose.append("*");
                       attrChoose.append(attributeDTO.getAttrName());
                       for (int i = 0; i < paramValue.length; i++) {
                           attrChoose.append("*");
                           attrChoose.append(convertStr(paramValue[i]));
                       }
                       attrChoose.append("+");
                   }
                   else{
                	   if(1==attributeDTO.getIsNeed()){
                           result.rejectValue("attrValue", "", attributeDTO.getAttrName()+"为必填项");
                   		   initPublish(goods.getCatCode(), goods, model);
                   		   return "/goods/publish";
                       }
                   }
               }

            } else {
                String paramValue = request.getParameter(attributeDTO.getAttrCode());
                if (StringUtil.isNotBlank(paramValue)) {
                    attrValue.append(attributeDTO.getAttrCode() + ":" + convertStr(paramValue));
                    attrDesc.append(attributeDTO.getAttrName() + ":" + convertStr(paramValue));
                    attrValue.append(";");
                    attrDesc.append(";");
                }else{
                    if(1==attributeDTO.getIsNeed()){
                       result.rejectValue("attrValue", "", attributeDTO.getAttrName()+"为必填项");
               		   initPublish(goods.getCatCode(), goods, model);
               		   return "/goods/publish";
                    }
                }
            }

        }
        goods.setAttrValue(attrValue.toString());
        goods.setAttrDesc(attrDesc.toString());
        goods.setChoose(attrChoose.toString());
        
    	
        /** 取出图片信息*/
    	
        List<MultipartFile> files = request.getFiles("images");
        
        if(files!=null && files.size() > 0){
            for(MultipartFile file : files){
            	if(file.getSize() > 0){
            		if(extIsAllowed(getExtension(file.getOriginalFilename()))){
            			if(file.getSize() > (maxSize * 1024)){
                           result.rejectValue("imgLarge", "", "图片应小于200k");
                   		   initPublish(goods.getCatCode(), goods, model);
                   		   return "/goods/publish";
            			}
//            			else{
//                    		InputStream stream = file.getInputStream();
//                    		Image src = ImageIO.read(stream);
//                    		int width = src.getWidth(null);
//                    		int height = src.getHeight(null);
//            			}
            		}else{
                       result.rejectValue("imgLarge", "", "图片格式不正确");
               		   initPublish(goods.getCatCode(), goods, model);
               		   return "/goods/publish";
            		}
            	}
            }
        }

        goods.setGoodsStatus(EnumGoodsStatus.ON_DEPOT.getKey());
        goods.setGoodsSn(StringEscapeUtils.escapeHtml(goods.getGoodsSn().trim()));
        goods.setTitle(goods.getTitle().trim());
        //加入商品货号 zhangwy
        if(goods.getGoodsItem()!=null){
        	goods.setGoodsItem(goods.getGoodsItem().trim());
        }
        goods.setMarketPrice((goods.getMarketPrice()!=null? goods.getMarketPrice():0.00));
        goods.setSalesProPrice((goods.getSalesProPrice()!=null? goods.getSalesProPrice():0.00));
        goods.setAgentPrice((goods.getAgentPrice()!=null? goods.getAgentPrice():0.00));
        goods.setIsWholesale("n");
        goods.setGoodsWeight(goods.getGoodsWeight()!=null?goods.getGoodsWeight():0.000);
       
     //是否关联淘宝
       if(goods.getIsTaobao().equals("y")){
    	   if(goods.getTaobaoClassId()!= null){
    		   goods.setTaobaoClassId(goods.getTaobaoClassId()); 
    		   //淘宝商品属性列表串
    		   if(StringUtil.isNotEmpty(goods.getTaobaoAttr()) && StringUtil.isNotBlank(goods.getTaobaoAttr())){
    			   goods.setTaobaoAttr(goods.getTaobaoAttr());
    		   }
    		  //更新的SKU的属性串 
    		   if(StringUtil.isNotEmpty(goods.getTaobaoSkuProp()) && StringUtil.isNotBlank(goods.getTaobaoSkuProp())){
    			   goods.setTaobaoSkuProp(goods.getTaobaoSkuProp());
    		   }
    		   
    		   if(StringUtil.isNotEmpty(goods.getTaobaoSkuPropName()) && StringUtil.isNotBlank(goods.getTaobaoSkuPropName())){
    			   goods.setTaobaoSkuPropName(goods.getTaobaoSkuPropName());
    		   }
    		   
    		   //用户自行输入的子属性名和属性值
    		   if(StringUtil.isNotEmpty(goods.getTaobaoInputStr()) && StringUtil.isNotBlank(goods.getTaobaoInputStr())){
    			   goods.setTaobaoInputStr(goods.getTaobaoInputStr());
    		   }
    		   //用户自行输入的类目属性ID串
    		   if(StringUtil.isNotEmpty(goods.getTaobaoInputPids()) && StringUtil.isNotBlank(goods.getTaobaoInputPids())){
    			   goods.setTaobaoInputPids(goods.getTaobaoInputPids());
    		   }
    	   }else{
       		   model.addAttribute("taobaoMessage", "classnull");
    		   initPublish(goods.getCatCode(), goods, model);
    		   return "/goods/publish";
    	   }
       }
       
       //是否关联拍拍
       if(goods.getIsPaipai().equals("y")){
    	   if(goods.getPaipaiClassId()!=null){
    		   goods.setPaipaiClassId(goods.getPaipaiClassId());
    		   if(StringUtil.isNotEmpty(goods.getPaipaiAttr()) && StringUtil.isNotBlank(goods.getPaipaiAttr())){
    			   goods.setPaipaiAttr(goods.getPaipaiAttr());
    		   }
    	   }else{
    		   model.addAttribute("message", "classnull");
    		   initPublish(goods.getCatCode(), goods, model);
    		   return "/goods/publish";
    	   }
       }

        try {   
            boolean flag = goodsManager.newPublishGoods(goods, files);
            if (flag) {
                model.addAttribute("message", "publishsuccess");
            } else {
                model.addAttribute("errorMessage", "publishfail");
            }
        } catch (ManagerException ex) {
            model.addAttribute("errorMessage", "publishfail");
        }
        return "/goods/publishsuccess";

    }

    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/clientPic")
    public String doClientPic(@ModelAttribute("goods") Goods goods, BindingResult result, Model model,
    		HttpServletRequest request) throws Exception {
    	Map parMap = new HashMap();
//    	parMap.put("startRow", 0);
//    	parMap.put("endRow", 200);
    	
    	Map<String,String> enumBrandTypeMap = EnumBrandType.toMap();
    	List<Brand> list = brandService.getBrands();
    	for(Brand b : list)
    	{
    		enumBrandTypeMap.put(b.getId()+"", b.getBrandName());
    	}
    	
    	List<Product> productList = productService.getProductClientToBrowser(parMap);
    	
    	for(Product product:productList){
    		//图片，先从网络上取图片资源到本地，再上传，压缩，
    		//如果本地资源路径可以找到图片则不用这么麻烦
//    		String fileUrl = "http://www.shangshangsp.com:88/upload/GUCCI_211137_FCIER_9643.jpg";   
    		String fileUrl = "http://www.shangshangsp.com:88"+StringUtils.replace(
    				product.getPicture(),"view.php?filename=","");
			  /*photoUrl.substring(photoUrl.lastIndexOf("/")的方法将返回最后一个符号为  
			  * ‘/’后photoUrl变量中的所有字符，包裹此自身符号*/  
    		String fileName = fileUrl.substring(fileUrl.lastIndexOf("/"));   
    		String filePath = upload+"/goodsOri"; 
    		String savePath = filePath+fileName;
    		
    		File dirPath = new File(filePath);

    		// 如果没有，建立目录
    		if (!dirPath.exists()) {
    			dirPath.mkdirs();
    		}
    		
    		//如果文件已经存在就不上传了
    		File exsitFile = new File(savePath);
    		if (!exsitFile.exists()) {
	    		try {
		  		  	URL url = new URL(fileUrl);/*将网络资源地址传给,即赋值给url*/  
		  		  /*此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流*/  
		  		  	HttpURLConnection connection = (HttpURLConnection)url.openConnection();   
		  		  	DataInputStream in = new DataInputStream(connection.getInputStream());   
		  		  /*此处也可用BufferedInputStream与BufferedOutputStream*/  
		  		  	DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath));   
		  		  /*将参数savePath，即将截取的图片的存储在本地地址赋值给out输出流所指定的地址*/  
		  		  	byte[] buffer = new byte[4096];   
		  		  	int count = 0;   
		  		  	while ((count = in.read(buffer)) > 0)/*将输入流以字节的形式读取并写入buffer中*/  
		  		  	{   
		  			  out.write(buffer, 0, count);   
		  		  	}   
		  		  	out.close();/*后面三行为关闭输入输出流以及网络资源的固定格式*/  
		  		  	in.close();   
		  		  	connection.disconnect();   /*网络资源截取并存储本地成功返回true*/  
			  	}   
			  	catch (Exception e)   
			  	{
			  		System.out.println(e + fileUrl + savePath);   
			  	}
    		}
    	}
    	return "redirect:/goods/search.html";
    }
    
    
    @SuppressWarnings("unchecked")
//	@RequestMapping(value = "/timetask/clientTb")
    public void doClientPublish(@ModelAttribute("goods") Goods goods, BindingResult result, Model model,
    		HttpServletRequest request) throws Exception {

    	
    	
    	Map parMap = new HashMap();
//    	parMap.put("startRow", 0);
//    	parMap.put("endRow", 200);
//    	System.out.println("111111111111111111");
    	Map<String,String> enumBrandTypeMap = EnumBrandType.toMap();
    	List<Brand> list = brandService.getBrands();
    	for(Brand b : list)
    	{
    		enumBrandTypeMap.put(b.getId()+"", b.getBrandName());
    	}
//    	parMap.put("type", "4595405");
//    	parMap.put("material", "53084");
//    	parMap.put("color", "999");
    	
//    	parMap.put("idBrand", "4");
//    	parMap.put("idLocation", "102");
//    	parMap.put("idSeries", "3");
    	
    	List<Product> productList = productService.getProductClientToBrowser(parMap);
    	
    	for(Product product:productList){
    		
//    		if(product.getIdStatus().longValue()!=1 &&
//    				product.getIdStatus().longValue()!=4){
//    			continue;
//    		}
    		if(product.getIdStatus().longValue()==6){
    			continue;
    		}
    		
    		Map pramas = new HashMap();
    		pramas.put("type", product.getType());
    		pramas.put("material", product.getMaterial());
    		pramas.put("color", product.getColor());
    		
    		Goods exsitGoods = goodsManager.getClientGoodsExist(pramas);
    		
    		//如果已经存在就查找instance
    		if(exsitGoods != null){
    			List<GoodsGallery>  listGoodsGallery = goodsGalleryManager.getGoodsGallerysByGoodsId(exsitGoods.getId());
    			boolean updateLastTime = false;//是否排序到前面
    			if(listGoodsGallery != null && listGoodsGallery.size() >=2) updateLastTime=true; //有多张照片的排到前面
    			
    			//存在，商品库存先增加1
    			if(product.getIdStatus().longValue()==1){
    				goodsManager.updateGoodsGoodsNumberById(exsitGoods.getId(), 1L,updateLastTime);
    			}
    			//存在，如果是香港库存，香港库存增加1
    			//20150115增加帝国中心
    			if(product.getIdStatus().longValue()==1 && 
    					(product.getIdLocation().longValue()==102 || product.getIdLocation().longValue()==103 || product.getIdLocation().longValue()==104)){
    				goodsManager.updateGoodsHKGoodsNumberById(exsitGoods.getId(), 1L,updateLastTime);
    			}
    			//存在状态为已售，商品已售库存增加1
    			if(product.getIdStatus().longValue()==4){
    				//2018-7-26注释（已售库存，在早上恢复库存时统计）
//    				goodsManager.updateSaleNumberById(exsitGoods.getId(), 1L);
    			}
    			checkInstanceExsit(model,product,pramas,exsitGoods);
    			//更新最新欧洲价和尚上价和shangshang价和大陆价
    			if(product.getHkhxPrice().doubleValue()>0 && product.getHkhxPrice().doubleValue()>exsitGoods.getHkhxPrice()){
    				exsitGoods.setHkhxPrice(product.getHkhxPrice());
    			}
    			if(product.getHxPrice().doubleValue()>0 && product.getHxPrice().doubleValue()>exsitGoods.getGoodsPrice()){
    				exsitGoods.setGoodsPrice(product.getHxPrice());
    			}
    			if(product.getEuPrice().doubleValue()>0){
    				exsitGoods.setEuPrice(product.getEuPrice());
    			}
    			if(product.getCnPrice().doubleValue()>0){
    				exsitGoods.setMarketPrice(product.getCnPrice());
    			}
    			if(product.getHkPrice().doubleValue()>0){
    				exsitGoods.setHkPrice(product.getHkPrice());
    			}
    			goodsManager.editGoods(exsitGoods);
    			continue;
    		}
    		
    		Goods newgoods = new Goods();
    		
    		newgoods.setBrandId(product.getIdBrand());
    		
    		//倒过来数据批发平台专用类目
    		if(StringUtil.isBlank(product.getSize())){
    			newgoods.setCatCode("287");
    		}else{
    			newgoods.setCatCode("286");
    		}
    		if(product.getIdStatus().longValue()==1){
    			newgoods.setGoodsNumber(1);
    		}
    		if(product.getIdStatus().longValue()==1 && product.getIdLocation().longValue()==102){
    			newgoods.setHkGoodsNumber(1);
			}
    		if(product.getIdStatus().longValue()==4){
    			newgoods.setSaleNumber(1);
    		}
    		newgoods.setProductId(product.getIdProduct());
    		newgoods.setColor(product.getColor());
    		newgoods.setSiteId(product.getIdLocation());
    		newgoods.setType(product.getType());
    		newgoods.setTitle(enumBrandTypeMap.get(product.getIdBrand().toString())
    				+" 型号："+product.getType()
    				+" "+product.getMaterial()
    				+" "+product.getColor());
    		newgoods.setMaterial(product.getMaterial());
    		newgoods.setTargetCustomers(product.getTargetCustomers());
    		newgoods.setIsAgent("n");
    		//goodsSn 商品type加上2位随机数
    		Random rd1 = new Random();
    		int ronnum = 10 + rd1.nextInt(90);
    		newgoods.setGoodsSn(product.getType()+ronnum);
    		newgoods.setGoodsUnit("件");
    		newgoods.setIsWholesale("n");
    		newgoods.setGoodsWeight(newgoods.getGoodsWeight()!=null?newgoods.getGoodsWeight():0.000);
            //加入商品货号 zhangwy
            if(newgoods.getGoodsItem()!=null){
            	newgoods.setGoodsItem(newgoods.getGoodsItem().trim());
            }
            
            //设置一些产品需要的属性
            newgoods.setSize(product.getSize());
            newgoods.setCost(product.getCost());
            newgoods.setIdCostCurrency(product.getIdCostCurrency());
            newgoods.setHkhxPrice(product.getHkhxPrice());
    		newgoods.setMarketPrice(product.getCnPrice());
    		newgoods.setGoodsPrice(product.getHxPrice());
    		newgoods.setHkPrice(product.getHkPrice());
    		newgoods.setEuPrice(product.getEuPrice());
    		newgoods.setIdSeries(product.getIdSeries());
            
            
    		
    		//图片，先从网络上取图片资源到本地，再上传，压缩，
    		//如果本地资源路径可以找到图片则不用这么麻烦
//    		String fileUrl = "http://www.shangshangsp.com:88/upload/GUCCI_211137_FCIER_9643.jpg";   
    		String fileUrl = "http://116.231.158.188:9090"+StringUtils.replace(
    				product.getPicture(),"view.php?filename=","");
			  /*photoUrl.substring(photoUrl.lastIndexOf("/")的方法将返回最后一个符号为  
			  * ‘/’后photoUrl变量中的所有字符，包裹此自身符号*/  
    		String fileName = fileUrl.substring(fileUrl.lastIndexOf("/"));   
    		String filePath = upload+"/goodsOri"; 
    		String savePath = filePath+fileName;
    		
    		File dirPath = new File(filePath);

    		// 如果没有，建立目录
    		if (!dirPath.exists()) {
    			dirPath.mkdirs();
    		}
    		
    		//如果文件已经存在就不上传了
    		File exsitFile = new File(savePath);
    		if (!exsitFile.exists()) {
	    		try {
		  		  	URL url = new URL(fileUrl);/*将网络资源地址传给,即赋值给url*/  
		  		  /*此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流*/  
		  		  	HttpURLConnection connection = (HttpURLConnection)url.openConnection();   
		  		  	DataInputStream in = new DataInputStream(connection.getInputStream());   
		  		  /*此处也可用BufferedInputStream与BufferedOutputStream*/  
		  		  	DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath));   
		  		  /*将参数savePath，即将截取的图片的存储在本地地址赋值给out输出流所指定的地址*/  
		  		  	byte[] buffer = new byte[4096];   
		  		  	int count = 0;   
		  		  	while ((count = in.read(buffer)) > 0)/*将输入流以字节的形式读取并写入buffer中*/  
		  		  	{   
		  			  out.write(buffer, 0, count);   
		  		  	}   
		  		  	out.close();/*后面三行为关闭输入输出流以及网络资源的固定格式*/  
		  		  	in.close();   
		  		  	connection.disconnect();   /*网络资源截取并存储本地成功返回true*/  
			  	}   
			  	catch (Exception e)   
			  	{
			  		//System.out.println(e + fileUrl + savePath);   
			  	}
    		}
    		
		  	File file = new File(savePath);
        	
		  	this.checkAttr( product, model, newgoods);
    		//TODO
    		newgoods.setGoodsDesc(newgoods.getTitle());
    		//TODO
    		newgoods.setGoodsStatus(EnumGoodsStatus.ON_DEPOT.getKey());
    		
    		
    		newgoods.setSalesProPrice((goods.getSalesProPrice()!=null? goods.getSalesProPrice():0.00));
    		newgoods.setAgentPrice((goods.getAgentPrice()!=null? goods.getAgentPrice():0.00));
    		
           
            try {
                boolean flag = goodsManager.newClientToBrowser(newgoods, file,savePath);
                if (flag) {
                    model.addAttribute("message", "publishsuccess");
                } else {
                    model.addAttribute("errorMessage", "publishfail");
                    continue;
                }
            } catch (ManagerException ex) {
                model.addAttribute("errorMessage", "publishfail");
                continue;
            }
            
    	}
//    	}
//        return "/goods/publishsuccess";
//    	return "redirect:/goods/search.html";
    }
    
    private void checkAttr(Product product,Model model,Goods newgoods) throws Exception{
    	//有size属性的商品
	  	if(StringUtil.isNotBlank(product.getSize())){
	  		List<AttributeDTO> attributeList = catAttrRelDao.getAttributeDTOByCatCode(newgoods.getCatCode());
	  		
            //属性值集合 属性集描述
            StringBuffer attrValue = new StringBuffer();
            StringBuffer attrDesc = new StringBuffer();
            StringBuffer attrChoose = new StringBuffer();
            for (AttributeDTO attributeDTO : attributeList) {
            	if("20111108000001".equalsIgnoreCase(attributeDTO.getAttrCode())){
	                if ("checkbox".equalsIgnoreCase(attributeDTO.getInputType().toLowerCase())) {
	                    String paramValue = product.getSize();
	                    String[] attrAll = attributeDTO.getValues();
	                    //判断导入的尺码是否已经存在
	                    boolean exsitTag = false;
	                    for(int i = 0; i < attrAll.length; i++){
	                    	if(attrAll[i].equalsIgnoreCase(paramValue)){
	                    		exsitTag = true;
	                    		break;
	                    	}
	                    }
	                    //如果不存在则要更新属性，增加新的尺码
	                    if(!exsitTag){
	                    	Attribute attribute = attributeManager.getAttribute(attributeDTO.getId());
	                    	if(StringUtil.isBlank(attribute.getAttrValues())){
	                    		attribute.setAttrValues(paramValue);
	                    	}else{
	                    		attribute.setAttrValues(StringUtil.trim
	                    				(attribute.getAttrValues())+"\r\n"+paramValue);
	                    	}
	                        if (attributeManager.editAttribute(attribute)) {
	                            model.addAttribute("message", "editsuccess");
	                        }else{
	                            model.addAttribute("message", "editfail");
	                        }
	                    }
	                    attrChoose.append(attributeDTO.getAttrCode());
	                    attrChoose.append("*");
	                    attrChoose.append(attributeDTO.getAttrName());
	                    attrChoose.append("*");
	                    attrChoose.append(paramValue);
	                    attrChoose.append("+");
	                } 
            	}
            }
            newgoods.setAttrValue(attrValue.toString());
            newgoods.setAttrDesc(attrDesc.toString());
            newgoods.setChoose(attrChoose.toString());
	  	}
    }
    
    
    
    @SuppressWarnings("unchecked")
	private void checkInstanceExsit(Model model,Product product,Map pramas,Goods exsitGoods) throws Exception{
    	//TODO
		pramas.put("size", product.getSize());
		GoodsInstance goodsInstance = goodsInstanceManager.getClientInstance(pramas);
		//相应属性值的产品已经存在，在原来的基础上增加库存
		if(goodsInstance!=null){
			//状态为可售增加库存
			if(product.getIdStatus().longValue()==1){
				goodsInstanceManager.updateGoodsInstanceExistNumById(
						goodsInstance.getId(),1L);
			}
			
			//存在，如果是香港库存，香港库存增加1
			if(product.getIdStatus().longValue()==1 
					&& (product.getIdLocation().longValue()==102 || product.getIdLocation().longValue()==103)){
				goodsInstanceManager.updateGoodsInstanceHKExistNumById(
						goodsInstance.getId(),1L);
			}
			
			//判断仓库位置在哪里
			Map<String,Object> aspra = new HashMap<String,Object>();
			aspra.put("siteId", product.getIdLocation());
			aspra.put("goodsInstanceId", goodsInstance.getId());
			AvailableStock availableStock = availableStockService.getAvailableStockByPramas(aspra);
			//如果仓库已经存在，在这个仓库上增加库存
			if(availableStock!=null){
				if(product.getIdStatus().longValue()==1){
					aspra.put("goodsNumber", 1);
					availableStockService.updateAvaiStoEsNumByPramas(aspra);
				}
			}else{
				//如果仓库不存在，在这个仓库上增加产品仓库
				AvailableStock availableStockNew = new AvailableStock();
				availableStockNew.setGoodsId(goodsInstance.getGoodsId());
				availableStockNew.setGoodsInstanceId(goodsInstance.getId());
				if(product.getIdStatus().longValue()==1){
					availableStockNew.setGoodsNumber(1L);
				}else{
					availableStockNew.setGoodsNumber(0L);
				}
				availableStockNew.setSiteId(product.getIdLocation());
				availableStockService.addhxAvaiStoByPramas(availableStockNew);
			}
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("idProduct", product.getIdProduct());
			map.put("goodsId", exsitGoods.getId());
			map.put("instanceId", goodsInstance.getId());
			productService.updateProductToBrowser(map);
			//TODO  状态已售增加销售量
		}else{
			
			exsitGoods.setSiteId(product.getIdLocation());
			exsitGoods.setSize(product.getSize());
			exsitGoods.setCost(product.getCost());
			exsitGoods.setIdCostCurrency(product.getIdCostCurrency());
			exsitGoods.setHkhxPrice(product.getHkhxPrice());
			exsitGoods.setMarketPrice(product.getCnPrice());
			exsitGoods.setGoodsPrice(product.getHxPrice());
			exsitGoods.setHkPrice(product.getHkPrice());
			exsitGoods.setEuPrice(product.getEuPrice());
			exsitGoods.setIdSeries(product.getIdSeries());
			exsitGoods.setProductId(product.getIdProduct());
			
			//设置库存
			//状态为可售增加库存
			if(product.getIdStatus().longValue()==1){
				exsitGoods.setGoodsNumber(1);
			}else{
				exsitGoods.setGoodsNumber(0);
			}
			
			//TODO  状态已售增加销售量
			
			//相应属性值的产品不存在，则要增加新的产品
			//先增加新的属性值,更新商品、增加产品
			this.checkAttr( product, model, exsitGoods);
			goodsManager.editGoodsAddGoodsInstance(exsitGoods);
		}
    }
    
    /***
     * 
     * @param 自动确认库存
     * @param result
     * @param model
     * @param request
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/timetask/checkGoodsNum")
	private String checkGoodsNum(@ModelAttribute("goods") Goods goods, BindingResult result, Model model,
    		HttpServletRequest request) throws Exception{
    	
		Map<String,Object> parMap = new HashMap<String,Object>();
		parMap.put("statusTag", "yes");
		int number = goodsManager.checkGoodsNum(parMap);
		model.addAttribute("number", number);
		return "/goods/timetask/checkGoodsNum"; 
    }
    //自动确认库存(spring中配置低的自动任务)
    @SuppressWarnings("unused")
	private String checkGoodsNums(){    	
		Map<String,Object> parMap = new HashMap<String,Object>();
		parMap.put("statusTag", "yes");
		 goodsManager.checkGoodsNum(parMap);
		auListing();
		return null; 
    }
    public void auListing(){
		goodsManager.autoListing();
	}
	
    /***
     * 
     * @param 自动上架
     * @param result
     * @param model
     * @param request
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/timetask/autoListing")
	private void autoListing(@ModelAttribute("goods") Goods goods, BindingResult result, Model model,
    		HttpServletRequest request) throws Exception{
    	
    	goodsManager.autoListing();
    }
    
    /***
     * 
     * @param 自动确认新图片
     * @param result
     * @param model
     * @param request
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/timetask/autoCheckNewPicture")
	private String autoCheckNewPicture(@ModelAttribute("goods") Goods goods, BindingResult result, Model model,
    		HttpServletRequest request) throws Exception{
    	
		Map<String,Object> parMap = new HashMap<String,Object>();
		parMap.put("gmtPictureTag", "yes");
		List<Product> productList = productService.getProducttoCheckNum(parMap);
    	
    	for(Product product:productList){
    		String fileUrl = "http://www.shangshangsp.com:88"+StringUtils.replace(
    				product.getPicture(),"view.php?filename=","");
			  /*photoUrl.substring(photoUrl.lastIndexOf("/")的方法将返回最后一个符号为  
			  * ‘/’后photoUrl变量中的所有字符，包裹此自身符号*/  
    		for(int num = 1;num<6;num++){
	    		String fileName = fileUrl.substring(fileUrl.lastIndexOf("/"));
	    		if(num != 1){
	    			fileName = StringUtils.replace(fileName,".","_"+num+".");
	    		}
	    		String filePath = upload+"/goodsOri"; 
	    		String savePath = filePath+fileName;
	    		
	    		File dirPath = new File(filePath);
	
	    		// 如果没有，建立目录
	    		if (!dirPath.exists()) {
	    			dirPath.mkdirs();
	    		}
    		
//    		File exsitFile = new File(savePath);
//    		if (!exsitFile.exists()) {
//    			uploadUtil.deleteFile(filePath, fileName);
//    		}
    		
    		//如果文件已经存在就替换
//    		try {
//	  		  	URL url = new URL(fileUrl);/*将网络资源地址传给,即赋值给url*/  
//	  		  /*此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流*/  
//	  		  	HttpURLConnection connection = (HttpURLConnection)url.openConnection();   
//	  		  	DataInputStream in = new DataInputStream(connection.getInputStream());   
//	  		  /*此处也可用BufferedInputStream与BufferedOutputStream*/  
//	  		  	DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath));   
//	  		  /*将参数savePath，即将截取的图片的存储在本地地址赋值给out输出流所指定的地址*/  
//	  		  	byte[] buffer = new byte[4096];   
//	  		  	int count = 0;   
//	  		  	while ((count = in.read(buffer)) > 0)/*将输入流以字节的形式读取并写入buffer中*/  
//	  		  	{   
//	  			  out.write(buffer, 0, count);   
//	  		  	}   
//	  		  	out.close();/*后面三行为关闭输入输出流以及网络资源的固定格式*/  
//	  		  	in.close();   
//	  		  	connection.disconnect();   /*网络资源截取并存储本地成功返回true*/  
//		  	}   
//		  	catch (Exception e)   
//		  	{
//		  		System.out.println(e + fileUrl + savePath);   
//		  	}
    		
			  	File file = new File(savePath);
			  	if(file.exists()){
				  	goods = goodsManager.getGoods(product.getGoodsId());
				  	goodsManager.updateGoodsPicture(goods, file,savePath,num);
			  	}
    		}
		  	
    	}
		
		return "/goods/timetask/checkGoodsNum"; 
    }
    
    
    private String convertStr(String paramValue){
        if(paramValue==null)
            return null;
        String temp=paramValue.trim();
        temp=EmallStringUtil.escapeGoodsAttrFilter(temp);
        return temp;

    }
    
    
    /**
     * 商品查询
     * @return
     * @throws Exception 
     */
    @AdminAccess({EnumAdminPermission.A_GOODS_VIEW_USER})
    @RequestMapping("/search")
    public String search(@ModelAttribute("goods") Goods goods, Model model,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
    		@RequestParam(value= "pageFlag",defaultValue = "on_sale") String pageFlag,
    		ServletRequest request) throws Exception{
    	 String sellDateStart = request.getParameter("sellDateStart");
    	 String sellDateEnd = request.getParameter("sellDateEnd");
    	 Category category = new Category();
    	 String oneList = request.getParameter("oneList");  //3一级类目cateCode的长度是3
    	 goods.setOneList(oneList);
    	 String twoList = request.getParameter("twoList");  //7二级类目cateCode的长度是7
    	 goods.setTwoList(twoList);    	 
    	 String threeList = request.getParameter("threeList"); //11 三级类目cateCode的长度是7
         goods.setThreeList(threeList);
         
         //回填类目，页面刷新类目不变
        if(StringUtils.isNotBlank(oneList) || StringUtils.isNotBlank(twoList) || StringUtils.isNotBlank(threeList)){
        	getCodeName(model,oneList,twoList,threeList);
        }
         
        
      
         List<String> codeList = new ArrayList<String>();
         //如果是三级类目有值(男士斜跨包(cate_code是290.001.002)，只需把这个条件放进集合，查询
         if(StringUtils.isNotBlank(goods.getThreeList())){
        	 codeList.add(goods.getThreeList());
         }else if(StringUtils.isNotBlank(goods.getTwoList())){
        	 //如果二级类目有值(男士包290.001)，只需把这个条件放进集合，还要把男士包是父类的所有子类（如290.001.002）放进集合(男士斜跨包，男士工具包...)，即parentCode='男士包（290.001）'的所有子类
        	 codeList.add(goods.getTwoList());
        	 
        	 category.setParentCode(goods.getTwoList());
        	 List<Category> twoCategory = categoryDao.getCategoryListByParentCode(category);
        	 for(int i=0;i<twoCategory.size();i++){
        		 codeList.add(twoCategory.get(i).getCatCode());
        	 }
         }else if(StringUtils.isNotBlank(goods.getOneList())){
        	 //如果是一级类目有值(包包290)，要把catecode字段，一包包开头的所有类目放进去
        	 String one = goods.getOneList();
        	 codeList.add(one);
        	 List<Category> onelist = categoryDao.getCategorys();
        	 for(int i=0;i<onelist.size();i++){
        		 String ctCode = onelist.get(i).getCatCode();
        		 if(ctCode.startsWith(one)){
        			 codeList.add(ctCode);
        		 }
        	 }
         }
         //codeList集合去重
         codeList = removeDuplicate(codeList);
         
         
         
    	 List<Long> list = new ArrayList<Long>();    	 
        //取得品牌列表，用于检索条件
        List<Brand> brandList = brandService.getBrands();
        model.addAttribute("brandList", brandList);
        List<Series> seriesList = brandService.getAllSeries();
        model.addAttribute("seriesList", seriesList);
        if("y".equals(goods.getGoodsKind())){
        	goods.setIsAgent("y");
        	goods.setIsWholesale("n");
        }else if("w".equals(goods.getGoodsKind())){
        	goods.setIsAgent("n");
        	goods.setIsWholesale("y");
        }else if("z".equals(goods.getGoodsKind())){
        	goods.setIsAgent("y");
        	goods.setIsWholesale("y");
        }
        
        if (EnumGoodsStatus.ON_SALE.getKey().equals(pageFlag)) {
            goods.setGoodsStatus(EnumGoodsStatus.ON_SALE.getKey());
        } else if (EnumGoodsStatus.ON_DEPOT.getKey().equals(pageFlag)) {
            goods.setGoodsStatus(EnumGoodsStatus.ON_DEPOT.getKey());
        } else if (EnumGoodsStatus.CUT_PRICE.getKey().equals(pageFlag)) {
            goods.setIsCutprice(EnumGoodsStatus.CUT_PRICE.getValue());
        } else if (EnumGoodsStatus.ACTIVITY_GOODS.getKey().equals(pageFlag)) {
            goods.setIsCutprice(EnumGoodsStatus.ACTIVITY_GOODS.getValue());
        } else if (EnumGoodsStatus.IS_AGENT.getKey().equals(pageFlag)) {
            goods.setIsAgent(EnumGoodsStatus.IS_AGENT.getValue());
        }

        if(goods.getIsPaipai()!=null && goods.getIsPaipai().equals("1")){
        	goods.setIsPaipai("y");
        }
        if(goods.getIsTaobao()!=null && goods.getIsTaobao().equals("1")){
        	goods.setIsTaobao("y");
        }
        
//        goods.setOrder("cat_code");
        
//        getParameter(request,goods);
        QueryPage page = null;
        if(StringUtils.isNotBlank(sellDateStart) || StringUtils.isNotBlank(sellDateEnd)){
   		 Map<String,String> map = new HashMap<String, String>();
   		 map.put("sellDateStart", sellDateStart);
   		 map.put("sellDateEnd", sellDateEnd);
   		 try {
   			 //查询product,lifecycle,customerorder三表，得出某个时间段,已售和预定的goods_id
   			 List<Product> prot = productService.selectProAndLifcle(map);
//   			 System.out.println(prot);
//   			 System.out.println(goods.getId()); 
//   			System.out.println(list.size());
   				 for(Product p:prot){
   				 Long googId = p.getGoodsId();
   				 if(googId!= null && !list.contains(googId)){  					 
   					 list.add(googId);
   				 }   				 
   				 }  				 
   				   				
   			 if(goods.getId()==null){	
   				page = goodsManager.getGoodsListByConditionWithPageByList(goods, currPage, this.pageSize,list,codeList);
   			 }else{
   				 if(list.contains(goods.getId())){
   					 list.clear();
   					 list.add(goods.getId());
   					 goods.setId(null);
   				 }else{
   					list.clear();
   					goods.setId(null);
   					list.add(-1L); //防止sql语句报错
   				 }
   				
   				page = goodsManager.getGoodsListByConditionWithPageByList(goods, currPage, this.pageSize,list,codeList);

   			 }
			} catch (Exception e) {
				e.printStackTrace();
			}
   		 
   	 }else{
   		page = goodsManager.getGoodsListByConditionWithPageBycodeList(goods, currPage, this.pageSize,codeList);
   	 }
        
    	 
    	if(page != null){
    		model.addAttribute("query", page);
    	}
        List<Goods> goodsList = (List<Goods>)page.getItems();
        if(goodsList != null && goodsList.size() > 0){
            for (Goods temp : goodsList) {
                temp.setCatName(categoryManager.getCatFullNameByCatcodeSimple(temp.getCatCode(), ">")); //包包>潮流女包>手提包

            }
        }
        
        List<Category> catList = categoryManager.getCatInfoByDepth(1);
        
        //重定向后request是新的对象，标准的字符集是 "ISO-8859-1"
        if(StringUtil.isNotBlank(request.getParameter("errormessage"))){
//	        String ce = new String(request.getParameter("errormessage").getBytes("ISO-8859-1"),
//	        "gbk");
	        model.addAttribute("errormessage", request.getParameter("errormessage"));
        }
        //获取根类目（包包、钱夹、服饰.......）oneCatory
        catoryInit(model);
        model.addAttribute("catList", catList);
        model.addAttribute("pageFlag", pageFlag);
        model.addAttribute("goods", goods);
        model.addAttribute("liangpin99url", liangpin99url);
        return "/goods/goodslist";
    }
    /**
     * @throws Exception 
     * @date 2018-12-28
     */
    @RequestMapping("/updata_catcod")
	public String updata_code(@RequestParam("goodsIds") String goodsId,@ModelAttribute("goods") Goods goods, Model model,ServletRequest request) throws Exception{
    	long goodsID = Long.parseLong(goodsId);
    	goods.setId(goodsID);
    	String oneList = request.getParameter("oneList");  //3
   	 	goods.setOneList(oneList);
   	 	String twoList = request.getParameter("twoList");  //7
   	 	goods.setTwoList(twoList);
   	 	String threeList = request.getParameter("threeList"); //11
   	 	goods.setThreeList(threeList);
//   	 	model.addAttribute("goods", goods);
   	 	catoryInit(model);
   	 	
   	 	//修改类目
   	 	Integer updateCatcod = 2 ;
   	 	if(oneList != null || twoList != null || threeList != null){
   	 		updateCatcod = goodsManager.updateCatcod(goods);
   	 	}
   	 	//回填类目，页面刷新类目不变
        if(StringUtils.isNotBlank(oneList) || StringUtils.isNotBlank(twoList) || StringUtils.isNotBlank(threeList)){
        	getCodeName(model,oneList,twoList,threeList);
        }
   	 	
   	 	model.addAttribute("goodsIds",goodsId);
   	 	
   	 	if(updateCatcod.equals(1)){
//   	 		model.addAttribute("message", "success");
   	 		return "redirect:/instance/goods_instance.html?gid="+goodsId;
   	 	}else if(updateCatcod.equals(0)){
   	 		model.addAttribute("message", "fail");
   	 	}else{
   	 	model.addAttribute("message", "");
   	 	}
   	 	return "goods/updata_catcod";
	}
    private void getParameter(ServletRequest request , Goods goods) throws Exception {
    	if(StringUtil.isNotBlank(request.getParameter("zeroStock"))){
    		goods.setZeroStock(request.getParameter("zeroStock"));
    	}
		
	}
private void catoryInit(Model model) throws Exception {
		Category category = new Category();
		category.setParentCode("-1");
		List<Category> oneCatory = categoryDao.getCategoryListByParentCode(category);
		List<Category> oneCatorys = new ArrayList<Category>();;
		for(int i=0;i<oneCatory.size();i++){
			int catCode = Integer.parseInt(oneCatory.get(i).getCatCode());
			//查询出所有的根类目，摒弃掉以前的老数据，大于等于290是新建的数据
			if(catCode>287){
				oneCatorys.add(oneCatory.get(i));
			}
//			System.out.println(oneCatory.get(i).getCatCode()+"——————"+oneCatory.size());
//		    if(oneCatory.get(i).getCatCode().equals("-1") || oneCatory.get(i).getCatCode().equals("284") || oneCatory.get(i).getCatCode().equals("285") || oneCatory.get(i).getCatCode().equals("286") || oneCatory.get(i).getCatCode().equals("287")){
//			oneCatory.remove(i);
//			System.out.println(oneCatory.size());
//		    }
		}
     	model.addAttribute("oneCatory", oneCatorys);
  }
  private void getCodeName(Model model,String oneList,String twoList,String threeList) {
	  Category category = new Category();
	  
	  //如果一级类目有具体东西（包包），二级类目应根据包包，得到所有的二级类目(男包)返回给页面
	  if(StringUtils.isNotBlank(oneList)){
		  category.setParentCode(oneList);
		  List<Category> twoListCategorys = categoryDao.getCategoryListByParentCode(category);
		  model.addAttribute("onelist", "ww");
		  model.addAttribute("twoListCategorys", twoListCategorys);
	  }
	  
	  
	//如果二级类目有具体东西（男包），三级类目应根据男包包，得到所有的男包的三级类目(男士斜跨包)返回给页面
	  if(StringUtils.isNotBlank(oneList) && StringUtils.isNotBlank(twoList)){
		  category.setParentCode(twoList);
		  List<Category> threeListCategorys = categoryDao.getCategoryListByParentCode(category);
		  model.addAttribute("tolist", "ss");
		    model.addAttribute("threeListCategorys", threeListCategorys);
	  }
}
  
  //list集合去重
  public static List removeDuplicate(List list) {   
	    HashSet h = new HashSet(list);   
	    list.clear();   
	    list.addAll(h);   
	    return list;   
	}
  
	@RequestMapping(value = "/getTwoCatory")
	@ResponseBody
	public Category[] getTwoCatory(HttpServletRequest request) throws Exception {
		Category[] serArray = null;
		String catCode = request.getParameter("catCode");
		if(StringUtils.isBlank(catCode)){
			return serArray;
		}
		Category category = new Category();
		category.setParentCode(catCode);
		List<Category> twoCategory = categoryDao.getCategoryListByParentCode(category);
		
		int length = twoCategory.size();
		serArray = new Category[length];
		for (int i = 0; i < length; i++) {
			serArray[i] = (Category) twoCategory.get(i);
		}
	
		return serArray;
	}
	
	@RequestMapping(value = "/getThreeCatory")
	@ResponseBody
	public Category[] getThreeCatory(HttpServletRequest request) {
		Category[] serArray = null;
		String catCode = request.getParameter("catCode");
		if(StringUtils.isBlank(catCode)){
			return serArray;
		}
		Category category = new Category();
		category.setParentCode(catCode);
		List<Category> twoCategory = categoryDao.getCategoryListByParentCode(category);		
		int length = twoCategory.size();
		serArray = new Category[length];
		for (int i = 0; i < length; i++) {
			serArray[i] = (Category) twoCategory.get(i);
		}
	
		return serArray;
	}
	
//	//获取子级的
//	private Map<String, Category> map_cata = null;
//
//	private Map<Long, String> map_code = null;
//	public List<Category> getChildInfoOfTheParent(String parentCode) throws Exception {
//		getCategory();
//		List<Category> list = new ArrayList<Category>();
//		for (Category entity : map_cata.get(parentCode).getChidrens()) {
//			list.add(entity);
//		}
//		return list;
//	}
//	
//	private void getCategory() {
//		if (map_cata == null || map_code == null) {
//			loadCategory();
//		}
//	}
//	private void loadCategory() {
//		try {
//			Map<String, Category> temp = new HashMap<String, Category>();
//			Map<Long, String> temp_code = new HashMap<Long, String>();
//
//			List<Category> list = categoryDao.getCategorys();
//
//			String str = new String();
//
//			for (Category entity : list) {
//				temp.put(String.valueOf(entity.getCatCode()), entity);
//				temp_code.put(entity.getId(), entity.getCatCode());
//			}
//
//			for (Category entity : list) {
//				str = setParent(entity, temp, str);
//			}
//
//			if (StringUtil.isNotBlank(str)) {
//				log.error("����Ŀ���ڵ�ʧ��(IDS=[" + str + "])");
//			}
//
//			this.map_cata = temp;
//			this.map_code = temp_code;
//
//			temp = null;
//			temp_code = null;
//
//		} catch (Exception e) {
//			log.error(e.getMessage());
//		}
//	}
//	private String setParent(Category entity, Map<String, Category> map, String str) {
//		if (entity.getDepth() != 0) {
//			if (map.get(entity.getParentCode()) != null) {
//				map.get(entity.getParentCode()).addChild(entity);
//			} else {
//				str = str + entity.getParentCode() + ",";
//				log.error("����Ŀ���ڵ�ʧ��(IDS=[" + str + "])");
//			}
//		}
//		return str;
//	}
	
//	

    
    
    
    
    
    /**
     * 下架操作
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/delisting")
    public String deListingGoods(@RequestParam("id") String goodsId, Model model) throws Exception {
        if (StringUtil.isBlank(goodsId) || StringUtil.isEmpty(goodsId)) {
        	model.addAttribute("message", "商品不存在");
            return "/goods/goods_error";
        }
    	
        if (!goodsManager.deListingGoods(Long.parseLong(goodsId))){
        	model.addAttribute("message", "faultdelist");
        }else{
        	model.addAttribute("message", "sucdelist");
        }
        
        model.addAttribute("pageFlag", EnumGoodsStatus.ON_SALE.getKey());
        return "redirect:/goods/search.html";
    }
    
    /**
     * 设为特价操作
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/cutPrice")
    public String deCutPriceGoods(@RequestParam("id") String goodsId, @RequestParam("pageFlag") String pageFlag, Model model) throws Exception {
        if (StringUtil.isBlank(goodsId) || StringUtil.isEmpty(goodsId)) {
        	model.addAttribute("message", "商品不存在");
            return "/goods/goods_error";
        }
    	
        Goods goods = goodsManager.getGoods(Long.parseLong(goodsId));
        if (goods != null) {
             if ("3".equals(goods.getIsCutprice()) && !"y".equals(goods.getIsAgent())) {
                model.addAttribute("message", "wrongcut");//此商品为活动商品，请先取消再设置为特价商品
            } else {
                if (!goodsManager.updateCutPrice(Long.parseLong(goodsId))){
                	model.addAttribute("message", "faultcut");
                }else{
                	 model.addAttribute("message", "succut");
                }
            }
        }
        
        model.addAttribute("pageFlag", pageFlag);
        return "redirect:/goods/search.html";
    }
    
    /**
     * 设为或取消活动商品操作
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/doActivityGoods")
    public String doActivityGoods(@RequestParam("id") String goodsId, @RequestParam("pageFlag") String pageFlag, @RequestParam("actionType") String actionType,
    		Model model) throws Exception {        
        if (StringUtil.isBlank(goodsId) || StringUtil.isEmpty(goodsId)) {
        	model.addAttribute("message", "商品不存在");
            return "/goods/goods_error";
        }
        
        String type = "";
        if ("cancel".equals(actionType)) {
            type = EnumGoodsStatus.CANEL_CUT_PRICE.getValue();
            if (!goodsManager.updateGoodsType(Long.parseLong(goodsId), type)) {
            	model.addAttribute("message", "canelactfault");
            } else {
                model.addAttribute("message", "canelactsuc");
            }
        } else {
            type = EnumGoodsStatus.ACTIVITY_GOODS.getValue();
            if (!goodsManager.updateGoodsType(Long.parseLong(goodsId), type)) {
                model.addAttribute("message", "setupactfault");
            } else {
                model.addAttribute("message", "setupactsuc");
            }
        }
        
        model.addAttribute("pageFlag", pageFlag);
        return "redirect:/goods/search.html";
    }

    /**
     * 商品与物流公司相关联
     * @author zhangwy
     * @return
     */
    @RequestMapping("/goodsExpressRelation")
    public String goodsExpressRelation(@RequestParam("goodsId") String goodsId, Model model){
    	Goods goods = goodsManager.getExpressGoods(Long.parseLong(goodsId));
    	
    	List<Express> allExpressList = expressManager.getExpresss();
    	List<Express> expressListOne = new ArrayList<Express>();
    	List<Express> expressListTwo = new ArrayList<Express>();
    	if(goods.getExpressIds()!=null){
        	Map parMap = new HashMap();
        	parMap.put("expressIds", goods.getExpressIds().split(","));
        	String[] expressIds = goods.getExpressIds().split(",");
        	expressListOne = expressManager.getNotGoodsExpressRelation(parMap);
        	expressListTwo =  new ArrayList<Express>();
        	for(String expressId:expressIds){
        		Express express = expressManager.getExpress(Long.parseLong(expressId));
        		if(express!=null){
            		expressListTwo.add(express);	
        		}
        	}
    	}else{
    		expressListOne = allExpressList;
    		expressListTwo = null;
    	}
    	model.addAttribute("goods", goods);
    	model.addAttribute("expressListOne", expressListOne);
    	model.addAttribute("expressListTwo", expressListTwo);
    	return "/goods/goodsexpress";
    }

    /**
     * 保存商品与物流公司的关联关系
     * @author zhangwy
     * @return
     */
    @RequestMapping(value = "/saveGoodsExpress", method = RequestMethod.POST)
    public String saveGoodsExpress(@RequestParam("goodsId") String goodsId,HttpServletRequest request,
    		Model model){
    	
    	String[] expressIds = request.getParameterValues("selectIds");
    	Goods goods = goodsManager.getExpressGoods(Long.parseLong(goodsId));
    	StringBuffer expressbuffer = new StringBuffer();
    	if(expressIds!=null){
    		for(int i=0;i<expressIds.length;i++){
    			expressbuffer.append(expressIds[i]).append(",");
    		}
    		String expressStr = expressbuffer.substring(0, expressbuffer.length()-1);
    		goods.setExpressIds(expressStr);
    		goodsManager.editGoods(goods);
    	}else{
    		goods.setExpressIds(null);
    		goodsManager.editGoods(goods);
    	}
        
        model.addAttribute("goodsId", goodsId);
        model.addAttribute("message", "success");
        return "redirect:/goods/goodsExpressRelation.html";
    }

    /**
     * 上架操作
     *
     * @return
     * @throws Exception
     */
    @AdminAccess({EnumAdminPermission.A_GOODS_DOLIST_USER})
    @RequestMapping("/listing")
    public String listingGoods(@RequestParam("id") String goodsId, Model model) throws Exception {

    	model.addAttribute("pageFlag", EnumGoodsStatus.ON_DEPOT.getKey());
    	
        if (!goodsManager.listingGoods(Long.parseLong(goodsId))) {
            model.addAttribute("message", "listingfault");
            return "redirect:/goods/search.html";
        } else {
            model.addAttribute("message", "listingsuc");
        }
        Map<String, Object> parMap = new HashMap<String, Object>();
        parMap.put("goodsId", goodsId);
        parMap.put("notifyStatus", "init");
        List<Stockout> stockout = stockoutDao.getStockoutList(parMap);
        Map<Long, Object> userEmailMap = new HashMap<Long, Object>();
        for (Stockout list : stockout) {
            if (null != list.getUserId() && list.getGoodsId().longValue() == Long.parseLong(goodsId)) {
                String instanceName = list.getGoodsInstanceName();
                if (userEmailMap.containsKey(list.getUserId())) {
                    instanceName = (String) userEmailMap.get(list.getUserId());
                    instanceName = instanceName + ",  " + list.getGoodsInstanceName();
                }
                userEmailMap.put(list.getUserId(), instanceName);
                Map<String, Object> notifyMap = new HashMap<String, Object>();
                notifyMap.put("notifyStatus", "notified");
                notifyMap.put("userId", list.getId());
                notifyMap.put("goodsId", list.getGoodsId());
                stockoutDao.updateNotifyStatus(notifyMap);
            }
        }
        return "redirect:/goods/search.html";
    }
    
    
    /**
     * 删除商品
     *
     * @return
     * @throws Exception
     */
    @AdminAccess({EnumAdminPermission.A_GOODS_DELETE_USER})
    @RequestMapping("/deleteg")
    public String deleteGoods(@RequestParam("id") String goodsId, Model model) throws Exception {
    	model.addAttribute("pageFlag", EnumGoodsStatus.ON_DEPOT.getKey());
        Goods goods = goodsManager.getGoods(Long.parseLong(goodsId));
        if (goods == null) {
            model.addAttribute("message", "notexist");
            return "redirect:/goods/search.html";
        } else {
            if (!EnumGoodsStatus.ON_DEPOT.getKey().equals(goods.getGoodsStatus())) {
                model.addAttribute("message", "statufault");
                return "redirect:/goods/search.html";
            } else {
                goods.setGoodsStatus(EnumGoodsStatus.DELETE.getKey());
                goodsManager.editGoods(goods);
                // 删除成功
                //删除商品时，同时删除产品
                //				delete
                //				goodsInstanceManager.updateGoodsInstance(gi)
                model.addAttribute("message", "deletegoodssuc");
            }
        }
        return "redirect:/goods/search.html";
    }

    /**
     * 取消特价操作
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/deCutPrice")
    public String deCanelCutPriceGoods(@RequestParam("id") String goodsId, @RequestParam("pageFlag") String pageFlag, Model model) throws Exception {
        if (!goodsManager.updateCanelCutPrice(Long.parseLong(goodsId))){
        	model.addAttribute("message", "faultcanel");
        }else{
        	model.addAttribute("message", "succanel");
        }
        model.addAttribute("pageFlag", pageFlag);
        return "redirect:/goods/search.html";
    }

    /**
     * 取消代理
     * @throws
     */
    @RequestMapping("/dodisagent")
    public String dodisagent(@RequestParam("id") String goodsId, @RequestParam("pageFlag") String pageFlag, Model model, AdminAgent adminAgent) {
        goodsManager.dodisagent(Long.parseLong(goodsId));
        saveWebSite(goodsId, "disagent",adminAgent);
        
        // deleted by chenyan 2010/06/24 start 取消了群发邮件操作
        //this.goodsManager.dodisAgentGoods(id, RequestUtil.getUrlContext(getRequest()));
        // deleted by chenyan 2010/06/24 end
        model.addAttribute("pageFlag", pageFlag);
        return "redirect:/goods/search.html";
    }
    
    /**
     * 当天发布的商品上架(单独上架，批量上架)发站内信
     * @return String
     * @author fangqing 2009/12/14
     */
    private boolean saveWebSite(String id, String strDo, AdminAgent adminAgent) {
        if (StringUtil.isBlank(id))
            return false;
        if ("listing".equals(strDo)) {
            Goods goods = goodsManager.getGoods(Long.parseLong(id));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date gmtCreate = goods.getGmtCreate();
            Date dt = new Date();
            String dateNow = df.format(dt);
            String dateCreate = df.format(gmtCreate);
            if (!dateNow.equals(dateCreate))
                return false;
            WebSiteEmail webSiteEmailRalation = new WebSiteEmail();
            StringBuffer sb = new StringBuffer();
            String goodsTitle = goods.getTitle();
            webSiteEmailRalation.setTitle("商品上架通知");
            sb.append("您好！感谢您使用1858网。").append(goodsTitle).append(" 已经上架");
            webSiteEmailRalation.setContent(sb.toString());
            webSiteEmailRalation.setFromUser(adminAgent.getUsername());
            webSiteEmailRalation.setStatus("new");
            webSiteEmailRalation.setType("system");
            if (this.webSiteEmailService.insertWebSite2(webSiteEmailRalation)) {
                return true;
            } else {
                return false;
            }

        } else {
            Goods goods = goodsManager.getGoods(Long.parseLong(id));
            WebSiteEmail webSiteEmailRalation = new WebSiteEmail();
            StringBuffer sb = new StringBuffer();
            String goodsTitle = goods.getTitle();
            webSiteEmailRalation.setTitle("代销商品取消通知");
            sb.append("您好！感谢您使用1858网。您所订购的商品，").append(goodsTitle).append(" 已取消代销权限，请注意及时更新!");
            webSiteEmailRalation.setContent(sb.toString());
            webSiteEmailRalation.setFromUser(adminAgent.getUsername());
            webSiteEmailRalation.setStatus("new");
            webSiteEmailRalation.setType("system");
            if (this.webSiteEmailService.insertWebSite2(webSiteEmailRalation)) {
                return true;
            } else {
                return false;
            }
        }

    }
    
    @AdminAccess({EnumAdminPermission.A_GOODS_MODIFY_USER})
    @RequestMapping(value = "editg", method = RequestMethod.GET)
    public String initEditGoods(Model model,@RequestParam(value = "goodsId") String goodsId) throws Exception{
        List<Brand> brandList = brandService.getBrands();
        model.addAttribute("brandList", brandList);

        Goods goods = goodsManager.getGoods(new Long(goodsId));

        goods = goodsManager.addGoodsAttr(goods);
        
        List<GoodsGallery> goodsGalleryList = goodsGalleryManager.getGoodsGallerysByGoodsId(goods.getId());
        model.addAttribute("goodsGalleryList", goodsGalleryList);
        
        String categoryName = categoryManager.getCatFullNameByCatcodeSimple(goods.getCatCode(),
            "->");
        model.addAttribute("categoryName", categoryName);
        
        List<AttributeDTO> attributeList = catAttrRelDao.getAttributeDTOByCatCode(goods.getCatCode());
        model.addAttribute("attributeList", attributeList);
        
        if (this.goodsManager.isCutPriceGoods(new Long(goodsId))) {
            model.addAttribute("isCutPrice", "isCutPrice");
        }
        String catCode = goods.getCatCode();
        Category category = categoryManager.getCateInfoByCatCode(catCode);
        double discount = category.getCommonAgentAgio();
       
        model.addAttribute("goods", goods);
        model.addAttribute("discount", discount);
        List<Unit> units = goodsManager.findAllUnits();
        model.addAttribute("units", units);
        return "goods/editGoods";
    }
    

    @RequestMapping(value = "/removeGoodsGallery")
    public @ResponseBody String[] removeGoodsGallery(@RequestParam("param") String id){
        boolean flag = goodsManager.removeGoodsGallery(id);
        String[] result = new String[2];
        result[0] = flag ? "成功" : "失败";
        result[1] = id;
        return result;
    }
    
    @RequestMapping(value = "/changeGoodsGallerySort")
    public @ResponseBody String[] changeGoodsGallerySort(@RequestParam("param") String id,@RequestParam("step") String step) throws Exception{
    	String[] result = new String[2];
        if("1".equals(step)){
        	goodsGalleryManager.goodsGalleryDown(Long.parseLong(id));
            result[0] = "下降成功";
        }else if("-1".equals(step)){
        	goodsGalleryManager.goodsGalleryUp(Long.parseLong(id));
            result[0] = "上升成功";
        }else{
        	result[0] = "操作失败";
        }
        return result;
    }
    
    public void initGoods(Goods goods, Model model) throws Exception{
        List<Brand> brandList = brandService.getBrands();
        model.addAttribute("brandList", brandList);

        goods = goodsManager.addGoodsAttr(goods);
        
        List<GoodsGallery> goodsGalleryList = goodsGalleryManager.getGoodsGallerysByGoodsId(goods.getId());
        model.addAttribute("goodsGalleryList", goodsGalleryList);
        
        String categoryName = categoryManager.getCatFullNameByCatcodeSimple(goods.getCatCode(),
            "->");
        model.addAttribute("categoryName", categoryName);
        
        List<AttributeDTO> attributeList = catAttrRelDao.getAttributeDTOByCatCode(goods.getCatCode());
        model.addAttribute("attributeList", attributeList);
        
        if (this.goodsManager.isCutPriceGoods(goods.getId())) {
            model.addAttribute("isCutPrice", "isCutPrice");
        }
        String catCode = goods.getCatCode();
        Category category = categoryManager.getCateInfoByCatCode(catCode);
        double discount = category.getCommonAgentAgio();
       
        model.addAttribute("goods", goods);
        model.addAttribute("discount", discount);
        List<Unit> units = goodsManager.findAllUnits();
        model.addAttribute("units", units);
    }
    
    /**
     * 商品编辑保存
     * @param goods
     * @param result
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/doeg", method = RequestMethod.POST)
    public String doEditGoods(@ModelAttribute("goods") Goods goods, BindingResult result, Model model,
    		MultipartHttpServletRequest request, AdminAgent adminAgent) throws Exception {
        //去除前后空格
        StringTrimUtil.trim(goods);
    	String goodsDesc = request.getParameter("goodsDesc");
    	goods.setGoodsDesc(goodsDesc);
    	goodsEditValidator.validate(goods, result);
    	if(result.hasErrors()){
    		initGoods(goods, model);
    		return "/goods/editGoods";
    	}
    	
    	List<AttributeDTO> attributeList = catAttrRelDao.getAttributeDTOByCatCode(goods.getCatCode());
    	
        //属性值集合 属性集描述
        StringBuffer attrValue = new StringBuffer();
        StringBuffer attrDesc = new StringBuffer();
        StringBuffer attrChoose = new StringBuffer();
        for (AttributeDTO attributeDTO : attributeList) {
            if ("checkbox".equalsIgnoreCase(attributeDTO.getInputType().toLowerCase())) {
                String[] paramValue = request.getParameterValues(attributeDTO.getAttrCode());
                if(attributeDTO.getIsBuyerChoose()==0){
                    if (paramValue != null) {
                        attrValue.append(attributeDTO.getAttrCode() + ":");
                        attrDesc.append(attributeDTO.getAttrName() + ":");
                        for (int i = 0; i < paramValue.length; i++) {
                            if (i != 0) {
                                attrValue.append("-");
                                attrDesc.append("-");
                            }
                            attrValue.append(convertStr(paramValue[i]));
                            attrDesc.append(convertStr(paramValue[i]));
                        }
                        attrValue.append(";");
                        attrDesc.append(";");
                    }else{
                        if(1==attributeDTO.getIsNeed()){
                            result.rejectValue("attrValue", "", attributeDTO.getAttrName()+"为必填项");
                    		initGoods(goods, model);
                    		return "/goods/editGoods";
                        }
    
                    }
               }else{
                   if (paramValue != null) {
                       attrChoose.append(attributeDTO.getAttrCode());
                       attrChoose.append("*");
                       attrChoose.append(attributeDTO.getAttrName());
                       for (int i = 0; i < paramValue.length; i++) {
                           attrChoose.append("*");
                           attrChoose.append(convertStr(paramValue[i]));
                       }
                       attrChoose.append("+");
                   }
                   else{
                	   if(1==attributeDTO.getIsNeed()){
                           result.rejectValue("attrValue", "", attributeDTO.getAttrName()+"为必填项");
                   		   initGoods(goods, model);
                		   return "/goods/editGoods";
                       }
                   }
               }

            } else {
                String paramValue = request.getParameter(attributeDTO.getAttrCode());
                if (StringUtil.isNotBlank(paramValue)) {
                    attrValue.append(attributeDTO.getAttrCode() + ":" + convertStr(paramValue));
                    attrDesc.append(attributeDTO.getAttrName() + ":" + convertStr(paramValue));
                    attrValue.append(";");
                    attrDesc.append(";");
                }else{
                    if(1==attributeDTO.getIsNeed()){
                       result.rejectValue("attrValue", "", attributeDTO.getAttrName()+"为必填项");
               		   initGoods(goods, model);
            		   return "/goods/editGoods";
                    }
                }
            }

        }
        goods.setAttrValue(attrValue.toString());
        goods.setAttrDesc(attrDesc.toString());
        goods.setChoose(attrChoose.toString());
    	
        /** 取出图片信息*/
    	
        List<MultipartFile> files = request.getFiles("images");
        
        if(files!=null && files.size() > 0){
            for(MultipartFile file : files){
            	if(file.getSize() > 0){
            		if(extIsAllowed(getExtension(file.getOriginalFilename()))){
            			if(file.getSize() > (maxSize * 1024)){
                           result.rejectValue("imgLarge", "", "图片应小于200k");
                     	   initGoods(goods, model);
                  		   return "/goods/editGoods";
            			}
//            			else{
//                    		InputStream stream = file.getInputStream();
//                    		Image src = ImageIO.read(stream);
//                    		int width = src.getWidth(null);
//                    		int height = src.getHeight(null);
//            			}
            		}else{
                       result.rejectValue("imgLarge", "", "图片格式不正确");
                	   initGoods(goods, model);
             		   return "/goods/editGoods";
            		}
            	}
            }
        }
    	
        goods.setGoodsSn(goods.getGoodsSn().trim());
        goods.setTitle(goods.getTitle().trim());
        //增加商品货号 zhangwy
        if(goods.getGoodsItem()!=null){
        	goods.setGoodsItem(goods.getGoodsItem().trim());	
        }
        goods.setMarketPrice((goods.getMarketPrice()!=null? goods.getMarketPrice():0.00));
        goods.setSalesProPrice((goods.getSalesProPrice()!=null? goods.getSalesProPrice():0.00));
        goods.setAgentPrice((goods.getAgentPrice()!=null? goods.getAgentPrice():0.00));
        goods.setIsWholesale("n");
        goods.setGoodsWeight(goods.getGoodsWeight()!=null?goods.getGoodsWeight():0.000);
       
       if(goods.getIsPaipai().equals("y")&& goods.getCheckflag().equals("1")){
    	   if(goods.getPaipaiClassId()!= null){
    		   goods.setPaipaiClassId(goods.getPaipaiClassId());
    		   goods.setPaipaiAttr(goods.getPaipaiAttr());
    	   }else{
       		model.addAttribute("message", "classnull");
       		initGoods(goods,model);
    		return "/goods/editGoods";
    	   }
       }
       
       //是否关联淘宝
       if(goods.getIsTaobao().equals("y") && goods.getCheckflagTaobao().equals("1")){
    	   if(goods.getTaobaoClassId()!= null){
    		   goods.setTaobaoClassId(goods.getTaobaoClassId()); 
    		   //淘宝商品属性列表串
    		   if(StringUtil.isNotEmpty(goods.getTaobaoAttr()) && StringUtil.isNotBlank(goods.getTaobaoAttr())){
    			   goods.setTaobaoAttr(goods.getTaobaoAttr());
    		   }
    		  //更新的SKU的属性串 
    		   if(StringUtil.isNotEmpty(goods.getTaobaoSkuProp()) && StringUtil.isNotBlank(goods.getTaobaoSkuProp())){
    			   goods.setTaobaoSkuProp(goods.getTaobaoSkuProp());
    		   }
    		   
    		   if(StringUtil.isNotEmpty(goods.getTaobaoSkuPropName()) && StringUtil.isNotBlank(goods.getTaobaoSkuPropName())){
    			   goods.setTaobaoSkuPropName(goods.getTaobaoSkuPropName());
    		   }
    		   
    		   //用户自行输入的子属性名和属性值
    		   if(StringUtil.isNotEmpty(goods.getTaobaoInputStr()) && StringUtil.isNotBlank(goods.getTaobaoInputStr())){
    			   goods.setTaobaoInputStr(goods.getTaobaoInputStr());
    		   }
    		   //用户自行输入的类目属性ID串
    		   if(StringUtil.isNotEmpty(goods.getTaobaoInputPids()) && StringUtil.isNotBlank(goods.getTaobaoInputPids())){
    			   goods.setTaobaoInputPids(goods.getTaobaoInputPids());
    		   }
    	   }else{
       		    model.addAttribute("taobaoMessage", "classnull");
           		initGoods(goods,model);
    		    return "/goods/editGoods";
    	   }
       }

        try {
        	Map<String, String> map = goodsManager.updateGoods(goods, files);
            if (null != map.get("isSuccess") && "success".equals(map.get("isSuccess"))) {
                if (null != map.get("isSendWebSite") && "success".equals(map.get("isSendWebSite"))) {
                    saveWebSite(String.valueOf(goods.getId()), "doEidtGoods", adminAgent);
                }
                model.addAttribute("goods", goods);
                model.addAttribute("liangpin99url", liangpin99url);
                request.setAttribute("message", "editsuccess");
            } else {
                request.setAttribute("errorMessage", "editfail");
            }
        } catch (ManagerException ex) {
            request.setAttribute("errorMessage", "editfail");
            request.setAttribute("exception", ex);
        }
        return "/goods/publishsuccess";
    }
    
    /**   
     * 判断扩展名是否允许的方法   
     */   
    private boolean extIsAllowed(String ext) {    
        ext = ext.toLowerCase();    
        ArrayList allowList = new ArrayList();
        allowList.add("jpg");
        allowList.add("gif");
        allowList.add("png");
      
       
        if (allowList.contains(ext)) {    
            return true;    
        } else {    
            return false;    
        }    
       
    } 
    
    /**   
     * 获取扩展名的方法   
     */   
    private String getExtension(String fileName) {    
        return fileName.substring(fileName.lastIndexOf(".") + 1);    
    }  

    /**
     * 批量下架
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "delistinggb")
    public String deListingGoodsBat(@ModelAttribute("goods")Goods goods, Model model) throws Exception {
		  /*获取页面商品id列表*/
		String idsstr = goods.getIds();
		List<String> notSuccess = new ArrayList<String>();
		if(idsstr != null){
			String[] goodsIds = idsstr.split(",");
	        for (String goodsId : goodsIds) {
	            Long id = Long.parseLong(goodsId);
	            if (!goodsManager.deListingGoods(id)) {
	                Goods goodsTemp = goodsManager.getGoods(id);
	                //modified by chenyan 2010/05/04 修改了商品名称为商品编码
	                notSuccess.add(goods.getGoodsSn());
	            }
	        }
		}
    	
        if (notSuccess.size() > 0){
        	model.addAttribute("message", "faultbatdelisting");
        }else{
        	model.addAttribute("message", "sucbatdelisting");
        }
        model.addAttribute("pageFlag", EnumGoodsStatus.ON_SALE.getKey());
        return "redirect:/goods/search.html";
    }
    
 
    /**
     * 批量上架
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	@AdminAccess({EnumAdminPermission.A_GOODS_DOLIST_GB_USER})
    @RequestMapping(value = "listinggb")
    public String listingGoodsBat(@ModelAttribute("goods")Goods goods,ServletRequest request,
    		HttpServletResponse response,
    		Model model) throws Exception {
		  /*获取页面商品id列表*/
		String idsstr = goods.getIds();
		List<String> notSuccess = new ArrayList<String>();
		String[] goodsIds = idsstr.split(",");
        Map<Long, Object> userEmailMap = new HashMap<Long, Object>();
        List<Long> goodsList = new ArrayList<Long>();
        List<String> successGoodsIds = new ArrayList<String>();
        for (String goodsId : goodsIds) {
            Long id = Long.parseLong(goodsId);

            if (!goodsManager.listingGoods(id)) {
                Goods goodsTemp = goodsManager.getGoods(id);
                //modified by   修改了商品名称为商品编码
                notSuccess.add(goodsTemp.getId().toString());
            } else {
                successGoodsIds.add(goodsId);
                goodsList.add(id);
            }
        }

        for (Long goodsId : goodsList) {
            Map<String, Object> parMap = new HashMap<String, Object>();
            parMap.put("goodsId", goodsId);
            parMap.put("notifyStatus", "init");
            List<Stockout> stockout = stockoutManager.getStockoutList(parMap);
            for (Stockout list : stockout) {
                //                if (null != list.getUserId()) {
                //                    Map<Long, Object> stoTmp = new HashMap<Long, Object>();
                //                    if (userEmailMap.containsKey(list.getUserId())) {//判断用户ID是否存在
                //                        Map<Long, Object> temp = (Map<Long, Object>) userEmailMap.get(list
                //                            .getUserId());
                //                        if (!temp.containsKey(goodsId)) {
                //                            temp.put(goodsId, list);
                //                            userEmailMap.put(list.getUserId(), temp);
                //                        }
                //                    } else {
                //                        stoTmp.put(list.getGoodsId(), list);
                //                        userEmailMap.put(list.getUserId(), stoTmp);
                //                    }
                //                }
                if (null != list.getUserId()
                    && list.getGoodsId().longValue() == goodsId.longValue()) {
                    String instanceName = list.getGoodsInstanceName();
                    if (userEmailMap.containsKey(list.getUserId())) {
                        instanceName = (String) userEmailMap.get(list.getUserId());
                        instanceName = instanceName + ",  " + list.getGoodsInstanceName();
                    }
                    userEmailMap.put(list.getUserId(), instanceName);
                    Map<String, Object> notifyMap = new HashMap<String, Object>();
                    notifyMap.put("notifyStatus", "notified");
                    notifyMap.put("userId", list.getId());
                    notifyMap.put("goodsId", list.getGoodsId());
                    stockoutManager.updateNotifyStatus(notifyMap);

                }

            }
        }

        //批量发生站内信 fangqing 20091214 去掉上架发邮件 zhangwy 2010/10/29
//        saveWebSite(successGoodsIds);
//        for (Map.Entry<Long, Object> map : userEmailMap.entrySet()) {
//            //            StringBuilder sb = new StringBuilder();
//            //            Map<Long, Object> goodsTmp = (Map<Long, Object>) map.getValue();
//            User user = userManager.getUser(map.getKey());
//            if (user != null) {
//                //              发邮件的方法
//                sendListingGoodsEmailToUser(user, (String) map.getValue());
//            }
//
//        }

        /* the list of not success */        
        if (notSuccess.size() > 0){
        	model.addAttribute("errormessage", notSuccess.toString()+" 上架失败！");
        }else{
        	model.addAttribute("message", "batlistsuc");
        }
        model.addAttribute("pageFlag", EnumGoodsStatus.ON_DEPOT.getKey());
        
        Class clazz = goods.getClass();
        
        setParameter(clazz,model,goods);
        //避免乱码
//        request.setCharacterEncoding("gbk");
        return "redirect:/goods/search.html";
        
    }
    
    
    @SuppressWarnings("unchecked")
	private void setParameter(Class clazz,Model model , Object obj) throws Exception {
		if (clazz.getSimpleName().equals("Object")) {
			return;
		}
		java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				String name = fields[i].getName();
				if ("serialVersionUID".equals(name)) {
					continue;
				}
				Object v = fields[i].get(obj);

				if (v instanceof Number || v instanceof String || v instanceof Character
						|| v instanceof java.sql.Date || v instanceof java.util.Date || v instanceof Timestamp) {
					model.addAttribute(name, v);
				}

			}
		}
		Class superClzz = clazz.getSuperclass();
		setParameter(superClzz, model, obj);
		
	}
    

    /**
     * 批量设为特价
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "cutPriceGoods")
    public String cutPriceGoods(@ModelAttribute("goods")Goods goods, Model model,@RequestParam("pageFlag") String pageFlag) throws Exception {
		  /*获取页面商品id列表*/
		String idsstr = goods.getIds();
        List<String> notSuccess = new ArrayList<String>();
        if(idsstr != null){
        	String[] goodsIds = idsstr.split(",");
            for (String goodsId : goodsIds) {
                Long id = Long.parseLong(goodsId);
                if (!goodsManager.updateCutPrice(id)) {
                    Goods goodsTemp = goodsManager.getGoods(id);
                    notSuccess.add(goods.getTitle());
                }
            }
        }

        if (notSuccess.size() > 0){
        	model.addAttribute("message", "listfacut");
        }else{
        	model.addAttribute("message", "sucessgoodscut");
        }
        model.addAttribute("pageFlag", pageFlag);
        return "redirect:/goods/search.html";
    }
    

    /**
     * 批量取消设为特价
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "canelCutPriceGoods")
    public String canelCutPriceGoods(@ModelAttribute("goods")Goods goods, Model model,@RequestParam("pageFlag") String pageFlag) throws Exception {
		  /*获取页面商品id列表*/
		String idsstr = goods.getIds();
        List<String> notSuccess = new ArrayList<String>();
        
        if(idsstr != null){
        	String[] goodsIds = idsstr.split(",");
            for (String goodsId : goodsIds) {
                Long id = Long.parseLong(goodsId);
                if (!goodsManager.updateCanelCutPrice(id)) {
                    Goods goodsTemp = goodsManager.getGoods(id);
                    notSuccess.add(goods.getTitle());
                }
            }
        }
        
        if (notSuccess.size() > 0){
        	model.addAttribute("message", "listfacanel");
        }else{
        	model.addAttribute("message", "listsucanel");
        }
        model.addAttribute("pageFlag", pageFlag);
        return "redirect:/goods/search.html";
    }

    /**
     * 批量取消设置代销
     */
    @RequestMapping(value = "doCanelAgentGoods")
    public String doCanelAgentGoods(@ModelAttribute("goods")Goods goods, Model model,AdminAgent adminAgent) {
		  /*获取页面商品id列表*/
		String idsstr = goods.getIds();
		
		if(StringUtils.isNotBlank(idsstr)){
			  List<String> goodsIds = new java.util.ArrayList<String>();
			  String[] ids = idsstr.split(",");
			  for(String id : ids){
				  goodsIds.add(id);
			  }
			  
			  boolean flag = false;
		      Map map = new HashMap();
		      map.put("goodsIds", goodsIds);
		      saveWebSiteForCancle(goodsIds,adminAgent);
		      flag = this.goodsManager.doCanelAgentGoods(map);
		      if(flag){
		    	  model.addAttribute("message", "goodsAgentListSuccess");
		      }else{
		    	  model.addAttribute("message", "goodsAgentlistFail");
		      }
		}else{
			model.addAttribute("message", "goodsAgentlistFail");
		}
    	
		model.addAttribute("pageFlag", EnumGoodsStatus.IS_AGENT.getKey());
        return "redirect:/goods/search.html";
    }
    
    
    /**
     * 批量取消代销商品发站内信
     */
    private boolean saveWebSiteForCancle(List<String> list, AdminAgent adminAgent) {
        StringBuilder sb = new StringBuilder();
        for (String goodsId : list) {
            Goods goods = goodsManager.getGoods(Long.parseLong(goodsId));
            sb.append(goods.getTitle()).append(" ");
        }
        WebSiteEmail webSiteEmailRalation = new WebSiteEmail();
        webSiteEmailRalation.setTitle("代销商品取消通知");
        webSiteEmailRalation.setContent("您好！感谢您使用1858网。您所订购的商品，" + sb.toString()
                                        + "已取消代销权限，请注意及时更新!");
        webSiteEmailRalation.setFromUser(adminAgent.getUsername());
        webSiteEmailRalation.setStatus("new");
        webSiteEmailRalation.setType("system");
        if (webSiteEmailService.insertWebSite2(webSiteEmailRalation)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 导出淘宝格式的商品数据
     */
    @RequestMapping(value = "exportTaobaoExcel")
    public String exportTaobaoExcel(Model model, HttpServletRequest request, HttpServletResponse res, @ModelAttribute("goods") Goods goods,
    		@RequestParam(value= "pageFlag",defaultValue = "on_sale") String pageFlag) {
        OutputStream outwt = null;
        try {
            Date da = new Date();
            outwt = res.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            res.setHeader("Content-disposition", "attachment; filename=goods" + date + ".xls");
            res.setContentType("application/octet-stream;charset=utf-8");
            
            if("y".equals(goods.getGoodsKind())){
            	goods.setIsAgent("y");
            	goods.setIsWholesale("n");
            }else if("w".equals(goods.getGoodsKind())){
            	goods.setIsAgent("n");
            	goods.setIsWholesale("y");
            }else if("z".equals(goods.getGoodsKind())){
            	goods.setIsAgent("y");
            	goods.setIsWholesale("y");
            }
            
            if (EnumGoodsStatus.ON_SALE.getKey().equals(pageFlag)) {
                goods.setGoodsStatus(EnumGoodsStatus.ON_SALE.getKey());
            } else if (EnumGoodsStatus.ON_DEPOT.getKey().equals(pageFlag)) {
                goods.setGoodsStatus(EnumGoodsStatus.ON_DEPOT.getKey());
            } else if (EnumGoodsStatus.CUT_PRICE.getKey().equals(pageFlag)) {
                goods.setIsCutprice(EnumGoodsStatus.CUT_PRICE.getValue());
            } else if (EnumGoodsStatus.ACTIVITY_GOODS.getKey().equals(pageFlag)) {
                goods.setIsCutprice(EnumGoodsStatus.ACTIVITY_GOODS.getValue());
            } else if (EnumGoodsStatus.IS_AGENT.getKey().equals(pageFlag)) {
                goods.setIsAgent(EnumGoodsStatus.IS_AGENT.getValue());
            }
            
            if(goods.getIsPaipai()!=null && goods.getIsPaipai().equals("1")){
            	goods.setIsPaipai("y");
            }
            if(goods.getIsTaobao()!=null && goods.getIsTaobao().equals("1")){
            	goods.setIsTaobao("y");
            }
            
            goods.setOrder("cat_code");
            
            
            
            //使用类目条件进行导出
            Category category = new Category();
        	String oneList = request.getParameter("oneList");  //3一级类目cateCode的长度是3
       	    goods.setOneList(oneList);
       	    String twoList = request.getParameter("twoList");  //7二级类目cateCode的长度是7
       	    goods.setTwoList(twoList);    	 
       	    String threeList = request.getParameter("threeList"); //11 三级类目cateCode的长度是7
            goods.setThreeList(threeList);
            
            //回填类目，页面刷新类目不变
           if(StringUtils.isNotBlank(oneList) || StringUtils.isNotBlank(twoList) || StringUtils.isNotBlank(threeList)){
           	getCodeName(model,oneList,twoList,threeList);
           }
            List<String> codeList = new ArrayList<String>();
            //如果是三级类目有值(男士斜跨包(cate_code是290.001.002)，只需把这个条件放进集合，查询
            if(StringUtils.isNotBlank(goods.getThreeList())){
           	 codeList.add(goods.getThreeList());
            }else if(StringUtils.isNotBlank(goods.getTwoList())){
           	 //如果二级类目有值(男士包290.001)，只需把这个条件放进集合，还要把男士包是父类的所有子类（如290.001.002）放进集合(男士斜跨包，男士工具包...)，即parentCode='男士包（290.001）'的所有子类
           	 codeList.add(goods.getTwoList());
           	 
           	 category.setParentCode(goods.getTwoList());
           	 List<Category> twoCategory = categoryDao.getCategoryListByParentCode(category);
           	 for(int i=0;i<twoCategory.size();i++){
           		 codeList.add(twoCategory.get(i).getCatCode());
           	 }
            }else if(StringUtils.isNotBlank(goods.getOneList())){
           	 //如果是一级类目有值(包包290)，要把catecode字段，一包包开头的所有类目放进去
           	 String one = goods.getOneList();
           	 codeList.add(one);
           	 List<Category> onelist = categoryDao.getCategorys();
           	 for(int i=0;i<onelist.size();i++){
           		 String ctCode = onelist.get(i).getCatCode();
           		 if(ctCode.startsWith(one)){
           			 codeList.add(ctCode);
           		 }
           	 }
            }
            //codeList集合去重
            codeList = removeDuplicate(codeList);
            
            //查询某段时间内的销售量
            String sellDateStart = request.getParameter("sellDateStart");
       	    String sellDateEnd = request.getParameter("sellDateEnd");
            
            List<Goods> goodsList = new ArrayList<Goods>();
            List<Long> list = new ArrayList<Long>();
            if(StringUtils.isNotBlank(sellDateStart) || StringUtils.isNotBlank(sellDateEnd)){
          		 Map<String,String> map = new HashMap<String, String>();
          		 map.put("sellDateStart", sellDateStart);
          		 map.put("sellDateEnd", sellDateEnd);
          		 try {
          			 //查询product,lifecycle,customerorder三表，得出某个时间段,已售和预定的goods_id
          			 List<Product> prot = productService.selectProAndLifcle(map);
//          			 System.out.println(prot);
//          			 System.out.println(goods.getId()); 
//          			System.out.println(list.size());
          				 for(Product p:prot){
          				 Long googId = p.getGoodsId();
          				 if(googId!= null && !list.contains(googId)){  					 
          					 list.add(googId);
          				 }   				 
          				 }  				 
          				   				
          			 if(goods.getId()==null){	
//          				page = goodsManager.getGoodsListByConditionWithPageByList(goods, currPage, this.pageSize,list,codeList);
          				goodsList = goodsManager.getGoodsListByConditionWithPageByListExport(goods,list,codeList);
          			 }else{
          				 if(list.contains(goods.getId())){
          					 list.clear();
          					 list.add(goods.getId());
          					 goods.setId(null);
          				 }else{
          					list.clear();
          					goods.setId(null);
          					list.add(-1L); //防止sql语句报错
          				 }
          				
          				goodsList = goodsManager.getGoodsListByConditionWithPageByListExport(goods,list,codeList);

          			 }
       			} catch (Exception e) {
       				e.printStackTrace();
       			}
          		 
          	 }else{
          		goodsList = goodsManager.getGoodsListByConditionWithPageByCodeListExport(goods,codeList);
          	 }
                        
            Map<String,String> brandEnumMap = EnumBrandType.toMap();
            Map<String,String> seriesEnumMap = EnumSeriesType.toMap();
            
            List<String[]> goodsExportList = new ArrayList<String[]>();
            if(EnumGoodsStatus.IS_AGENT.getKey().equals(pageFlag)){
            	String[] title = { "商品编号","品牌","品名", "型号","材质","颜色","材质描述","颜色描述","产地","安全技术类别","执行标准","重量","总库存","香港库存","商品类目","月销售数量", "三月内销售数量","尚上价（RMB）","LUSSO&MODA价（HKD）","欧洲价（EU）","图片" };
                goodsExportList.add(title);
            }else{
            	String[] title = { "商品编号","品牌","品名", "型号","材质","颜色","材质描述","颜色描述","产地","安全技术类别","执行标准","重量","总库存","香港库存","商品类目","月销售数量", "三月内销售数量","尚上价（RMB）","LUSSO&MODA价（HKD）","欧洲价（EU）","图片" };
                goodsExportList.add(title);
            }
            SimpleDateFormat dateTwo = new SimpleDateFormat("yyyy-MM-dd");
            if(goodsList!=null){
            	for(Goods tmp:goodsList){
            		tmp.setCatName(categoryManager.getCatFullNameByCatcodeSimple(tmp.getCatCode(), ">"));
            		if(tmp.getGoodsStatus().equals("on_sale")){
            			tmp.setGoodsStatus("销售中");
            		}else if(tmp.getGoodsStatus().equals("on_depot")){
            			tmp.setGoodsStatus("仓库中");
            		}else{
            			tmp.setGoodsStatus("删除");
            		}
            		if(tmp.getGmtListing()!=null||tmp.getGmtDelisting()!=null){
            			tmp.setGmtDesc(dateTwo.format(tmp.getGmtListing())+"/"+dateTwo.format(tmp.getGmtDelisting()));
            		}
            		if(EnumGoodsStatus.IS_AGENT.getKey().equals(pageFlag)){
                		String[] data = {
            				tmp.getId().toString()+ "", brandEnumMap.get(tmp.getBrandId().toString())+ "", seriesEnumMap.get(tmp.getIdSeries().toString())+ "",
       				         tmp.getType()+ "", tmp.getMaterial()+ "", tmp.getColor()+ "",
       				         tmp.getMaterialdes()+"",tmp.getColordes()+"",tmp.getOrigin()+"",tmp.getSecurityTC()+"",tmp.getImplementationS()+"",tmp.getGoodsWeight()+"",
       				         tmp.getGoodsNumber()+ "", tmp.getHkGoodsNumber()+ "", 
       				         tmp.getCatName()+ "",tmp.getSaleNumber()+ "",tmp.getHkthNumber()+ "", tmp.getGoodsPrice()+ "",tmp.getHkhxPrice()+ "",
			        		 tmp.getEuPrice()+ "",
			        		 tmp.getImgMiddle()+ ""};
                		goodsExportList.add(data);
            		}else{
                		String[] data = {
                				tmp.getId().toString()+ "", brandEnumMap.get(tmp.getBrandId().toString())+ "", seriesEnumMap.get(tmp.getIdSeries().toString())+ "",
          				         tmp.getType()+ "", tmp.getMaterial()+ "", tmp.getColor()+ "",
          				       tmp.getMaterialdes()+"",tmp.getColordes()+"",tmp.getOrigin()+"",tmp.getSecurityTC()+"",tmp.getImplementationS()+"",tmp.getGoodsWeight()+"",
          				         tmp.getGoodsNumber()+ "", tmp.getHkGoodsNumber()+ "", 
          				         tmp.getCatName()+ "",tmp.getSaleNumber()+ "",tmp.getHkthNumber()+ "", tmp.getGoodsPrice()+ "",tmp.getHkhxPrice()+ "",
	   			        		 tmp.getEuPrice()+ "",
	   			        		tmp.getImgMiddle()+ ""};
                		goodsExportList.add(data);
            		}
            	}
            }
            goodsBatch.exportExcelWidthPic(outwt, goodsExportList);
            outwt.flush();
        } catch (Exception e) {
            model.addAttribute("message", "商品导出失败");
            log.error(e);
        } finally {
            close(outwt);
        }
        
        model.addAttribute("pageFlag", pageFlag);
        return "/goods/goodslist";
    }
    
    public static void close(OutputStream out) {

        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException ioe) {
            //ignore
        }
    }

    /**
     * 根据图片路径下载图片
     * @return
     */
    @RequestMapping(value = "downLoadImg")
    public String downLoadImg(Model model, HttpServletResponse res, @ModelAttribute("goods") Goods goods,
    		@RequestParam(value= "pageFlag",defaultValue = "on_sale") String pageFlag) {
        emisZipUtil zipUitl = null;
        OutputStream outwt = null;
        try {
            Date da = new Date();
            outwt = res.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            res.setHeader("Content-disposition", "attachment; filename=picture" + date + ".zip");
            res.setContentType("application/octet-stream;charset=utf-8");
            
            if("y".equals(goods.getGoodsKind())){
            	goods.setIsAgent("y");
            	goods.setIsWholesale("n");
            }else if("w".equals(goods.getGoodsKind())){
            	goods.setIsAgent("n");
            	goods.setIsWholesale("y");
            }else if("z".equals(goods.getGoodsKind())){
            	goods.setIsAgent("y");
            	goods.setIsWholesale("y");
            }
            
            if (EnumGoodsStatus.ON_SALE.getKey().equals(pageFlag)) {
                goods.setGoodsStatus(EnumGoodsStatus.ON_SALE.getKey());
            } else if (EnumGoodsStatus.ON_DEPOT.getKey().equals(pageFlag)) {
                goods.setGoodsStatus(EnumGoodsStatus.ON_DEPOT.getKey());
            } else if (EnumGoodsStatus.CUT_PRICE.getKey().equals(pageFlag)) {
                goods.setIsCutprice(EnumGoodsStatus.CUT_PRICE.getValue());
            } else if (EnumGoodsStatus.ACTIVITY_GOODS.getKey().equals(pageFlag)) {
                goods.setIsCutprice(EnumGoodsStatus.ACTIVITY_GOODS.getValue());
            } else if (EnumGoodsStatus.IS_AGENT.getKey().equals(pageFlag)) {
                goods.setIsAgent(EnumGoodsStatus.IS_AGENT.getValue());
            }
            
            
            if(goods.getIsPaipai()!=null && goods.getIsPaipai().equals("1")){
            	goods.setIsPaipai("y");
            }
            if(goods.getIsTaobao()!=null && goods.getIsTaobao().equals("1")){
            	goods.setIsTaobao("y");
            }
            
            goods.setOrder("cat_code");
            
            List<Goods> goodsList = goodsManager.getGoodsListByConditionWithPage(goods);

            zipUitl = new emisZipUtil(outwt);
            String realPathPre = upload;
            for (Goods goodsTemp : goodsList) {
                if (StringUtil.isNotBlank(goodsTemp.getImgLarge())) {
                    zipUitl.put(realPathPre + Constants.FILE_SEP + goodsTemp.getImgLarge());
                }
            }
            if(zipUitl.getFileCount()==0){
            	zipUitl.putNextEntry(new ZipEntry(""));
            }
            outwt.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (null != zipUitl)
                ;
            try {
                zipUitl.close();
                close(outwt);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        
        model.addAttribute("pageFlag", pageFlag);
        return "/goods/goodslist";
    }
    
	@RequestMapping(value = "goodsBillRel", method = RequestMethod.GET)
	public String goodsBillRelation(@RequestParam("ids") String ids, Model model) {
		// 进行关联的商品
		List<Long> idList = new ArrayList<Long>();
		for(String id : ids.split(",")) {
			idList.add(Long.valueOf(id));
		}
		List<Goods> goodsList = goodsManager.getGoodsByGoodsIds(idList);
		model.addAttribute("goodsList", goodsList);

		// 所有商品开票公司
		Map<String, Object> resParam = new HashMap<String, Object>();
		resParam.put("type", "goodsbill");
		List<Resources> goodsBillList = resourcesManager.getResourcesByMap(resParam);
		model.addAttribute("goodsBillList", goodsBillList);
		
		Map<Long, Resources> goodsBillMap = new HashMap<Long, Resources>();
		for(Resources b : goodsBillList) {
			goodsBillMap.put(b.getId(), b);
		}
		model.addAttribute("goodsBillMap", goodsBillMap);

		return "/goods/goodsBillRel";
	}
	
	@RequestMapping(value = "goodsBillRel", method = RequestMethod.POST)
	public String goodsBillRelation(@RequestParam("gids") Long[] gids, @RequestParam("bid") Long bid, Model model) {
		List<Long> gidList = Arrays.asList(gids);
		goodsManager.updateGoodsBill(gidList, bid);

		model.addAttribute("success", 1);

		StringBuffer ids = new StringBuffer();
		for (Long gid : gids) {
			ids.append(gid).append(",");
		}

		return goodsBillRelation(ids.toString(), model);
	}
	
	@RequestMapping("/updatesalesproprice")
	 public @ResponseBody Object updatesalesproprice(@ModelAttribute("goods") Goods goods,
	    		ServletRequest request,HttpServletRequest req){
//		Map<String, String>  map = MiniUiUtil.getParameterMap(req);
//		System.out.println(map);
		Map<String,Object> maps = new  HashMap<String, Object>();
//		String curpagee = request.getParameter("curpagee");
		String p = request.getParameter("price");
		if(!p.equals("null")){
		double price=Double.parseDouble(p);
		long id = Long.valueOf(request.getParameter("id"));
		goods.setId(id);
		goods.setSalesProPrice(price);
		goodsManager.updatesalesproprice(goods);
		
		maps.put("success", "ok");
		maps.put("price", price);
		maps.put("id", request.getParameter("id"));
		return maps;
//		return "redirect:/goods/search.html?page="+curpagee+"&goods="+goods;
		}else{
			maps.put("success", "no");
			return maps;
//			return "redirect:/goods/search.html";
		}
	
	}
	
	 @RequestMapping(value = "/importExcelBytmc")
	    public @ResponseBody Object importExcelBytmc(Model model,HttpServletRequest requ,HttpServletResponse response) {
	    	try
	    	{
	    		 // request转换
		    	MultipartHttpServletRequest request = (MultipartHttpServletRequest)requ;
		    	//取得request中的所有文件名  
				MultipartFile locationFile = request.getFile("dr");
				InputStream  in = locationFile.getInputStream();
				//取得当前上传文件的文件名称
				String locationFileName = locationFile.getOriginalFilename(); 
				//截取上传文件的名称.后面的名称
				String fileType = locationFileName.substring(locationFileName.lastIndexOf(".")+1);
				
				//判断是xls文件，还是xlsx文件
				Workbook wb = null;  
		        if (fileType.equals("xls")) {  
		            wb = new HSSFWorkbook(in);  
		        }  
		        else if (fileType.equals("xlsx")) {  
		            wb = new XSSFWorkbook(in);  
		        }  
		        
		        //得到Excel表格中的第一个sheet1
		        Sheet sheet1 = wb.getSheetAt(0);
		        
		        
		        
		        //处理本地对应关系excel
		        Map<String,List<String>> locationMap = new HashMap<String,List<String>>();
		        for(int  rowIndex = 1;rowIndex < sheet1.getPhysicalNumberOfRows();rowIndex++)
		        { 
		        	Row row  =  sheet1.getRow(rowIndex);
		        	String   tmc = "";
		        	List<String> list = new ArrayList<String>(); 
		        	for(int colIndex = 0; colIndex < 10;colIndex++)//只需要前7列
		        	{
		        		Cell cell = row.getCell(colIndex);
		        		String value = getCellValue(cell);
		        	  if(colIndex == 3){
		        		  value = getCellValueDou(cell);
		        	  }
		            	if(colIndex == 0)    //型号
		            	{  
		            		if(StringUtil.isBlank(value)){
		            			list.add("");
		            		}else{
		            			tmc=value;
//		            			System.out.println(tmc);
		            			list.add(value);
		            		}
		            	}
		            	else if(colIndex == 1)  //材质
		            	{
		            		
		            		if(StringUtil.isBlank(value)){
		            			list.add("");
		            		}else{
		            			tmc=tmc+"_"+value;
//		            			System.out.println(tmc);
		            			list.add(value);
		            		}
		            	}else if(colIndex == 2) //颜色
		            	{
		            		
		            		if(StringUtil.isBlank(value)){
		            			list.add("");
		            		}else{
		            			tmc=tmc+"_"+value;
//		            			System.out.println(tmc);
		            			list.add(value);
		            		}
		            	}else if(colIndex == 3) //重量
		            	{
		            		if(StringUtil.isBlank(value)){
		            			list.add("");
		            		}else{
		            			list.add(value);
		            		}
		            	}else if(colIndex == 4)  //商品名称
		            	{
		            		if(StringUtil.isBlank(value)){
		            			list.add("");
		            		}else{
		            			value=value.replaceAll("\n", " ");  //将传入的内容中有换行的，用空格代替，传向后面，导入数据库
		            			list.add(value);
		            		}
		            	}else if(colIndex == 5) //材质描述
		            	{
		            		if(StringUtil.isBlank(value)){
		            			list.add("");
		            		}else{
		            			list.add(value);
		            		}
		            	}else if(colIndex == 6) //颜色描述
		            	{
		            		if(StringUtil.isBlank(value)){
		            			list.add("");
		            		}else{
		            			list.add(value);
		            		}
		            	}else if(colIndex == 7) //颜色描述
		            	{
		            		if(StringUtil.isBlank(value)){
		            			list.add("");
		            		}else{
		            			list.add(value);
		            		}
		            	}else if(colIndex == 8) //安全技术类别
		            	{
		            		if(StringUtil.isBlank(value)){
		            			list.add("");
		            		}else{
		            			list.add(value);
		            		}
		            	}else if(colIndex ==9) //执行标准
		            	{
		            		if(StringUtil.isBlank(value)){
		            			list.add("");
		            		}else{
		            			list.add(value);
		            		}
		            	}
		        	}
		        	
		        	locationMap.put(tmc, list);
		        }
		        goodsManager.updateEmallGoods(locationMap);
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    		log.error(e.getMessage());
	    		return "error";
	    	}
		 return "success";
	 }
	
	 private String getCellValue(Cell cell) {
	    	if(null == cell) return"";
	        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
	            return String.valueOf(cell.getBooleanCellValue());
	        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
	        	DecimalFormat df = new DecimalFormat("0");  
	            return df.format(cell.getNumericCellValue());
	        } else {
	            return String.valueOf(cell.getStringCellValue());
	        }
	    }
	 
	 private String getCellValueDou(Cell cell) {
	    	if(null == cell) return"";
	        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
	            return String.valueOf(cell.getBooleanCellValue());
	        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
	        	
	            return String.valueOf(cell.getNumericCellValue());
	        } else {
	            return String.valueOf(cell.getStringCellValue());
	        }
	    }
}
