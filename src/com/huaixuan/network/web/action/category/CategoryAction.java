package com.huaixuan.network.web.action.category;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.domain.goods.CatAttrRel;
import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.CatAttrRelManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.UploadUtil;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
@RequestMapping(value = "/category")
public class CategoryAction extends BaseAction{

	
    @Autowired
    private CategoryManager categoryManager;
    
    @Autowired
    private CatAttrRelManager catAttrRelManager;
    
	@Autowired
	private UploadUtil uploadUtil;
	
	@Autowired
	private Validator categoryEditValidator;
	
	@Autowired
	private Validator categoryAddValidator;
	
	@Autowired
	private AttributeManager attributeManager;
    
    @AdminAccess({EnumAdminPermission.A_CM_VIEW_USER})
    @RequestMapping(value = "cm")
    public String initCategoryTree(){
    	return "/category/categoryinit";
    }
    

    //��ȡ��Ŀ��
    @RequestMapping(value = "tree")
    public String getCategotyTree(Model model) throws Exception {
        model.addAttribute("cateGoryTree", categoryManager.getChildInfoOfTheParent("-1"));
        return "/category/tree";
    }
    
    @RequestMapping(value = "getci")
    public String getCategoryInfoAndRelatedAttr(@RequestParam(value = "catCode",defaultValue="") 
    		String catCode,
    		@RequestParam(value = "catNameForCallBackModify",required = false,defaultValue="") 
    		String catNameForCallBackModify,
    		@RequestParam(value = "catNameForCallBackModifyNew",required = false,defaultValue="") 
    		String catNameForCallBackModifyNew,
    		@ModelAttribute("category")Category category ,Model model) throws Exception {
        List<CatAttrRel> catAtrrRel = new ArrayList<CatAttrRel>();

        if (StringUtil.isNotEmpty(catCode) && StringUtil.isNotBlank(catCode)) {
        	category = categoryManager.getCateInfoByCatCode(catCode);
            catAtrrRel = catAttrRelManager.getAttributeOfTheCategoty(catCode);
            model.addAttribute("category", category);
            model.addAttribute("catAtrrRel", catAtrrRel);
            model.addAttribute("catNameForCallBackModify",catNameForCallBackModify);
            model.addAttribute("catNameForCallBackModifyNew",catNameForCallBackModifyNew);
        }
        return "/category/category";
    }
    
    //�ӽڵ���¼���
	@RequestMapping(value = "catpcode")
    public void getCategotyByParentCode(HttpServletRequest request, HttpServletResponse res) throws Exception {

        List<Category> list = new ArrayList<Category>();
        list = this.categoryManager.getChildInfoOfTheParent(request.getParameter("catCode"));
        JSONArray resultArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            Category d = (Category) list.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("index", d.getCatCode());
            jsonObject.put("label", d.getCatName());
            jsonObject.put("href", "/category/getci.html?catCode=" + d.getCatCode());
            jsonObject.put("target", "content");
            resultArray.put(jsonObject);
        }
        res.getWriter().write(resultArray.toString());

    }
	
	public void initCategory(Category category, Model model) throws Exception{
		List<CatAttrRel> catAtrrRel = catAttrRelManager.getAttributeOfTheCategoty(category.getCatCode());
		model.addAttribute("catAtrrRel", catAtrrRel);
		model.addAttribute("category", category);
	}
	
    @AdminAccess({EnumAdminPermission.A_CM_MODIFY_USER})
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String EditCategory(@ModelAttribute("category") Category category, BindingResult result, Model model,
    		@RequestParam("editFlag") String editFlag,MultipartHttpServletRequest request) throws Exception {
    	
    	categoryEditValidator.validate(category, result);
    	if(result.hasErrors()){
            initCategory(category,model);
            return "/category/category";
    	}
    	
    	Category oldCategory = categoryManager.getCategory(category.getId());
    	
    	
    	MultipartFile file = request.getFile("catImage");
    	
    	if(file != null && file.getSize() > 0){
    		Long length = file.getSize();
    		int size = (int)(length / 1024);
    		if(size > 200){
    			result.rejectValue("picPath", "", "�ļ����ܳ���200K");
                initCategory(category,model);
                return "/category/category";
    		}
    		String cateGoryPic = "categorys" + Constants.FILE_SEP + DateUtil.getDateTime("yyyyMM", new Date());
    		String cateImageUrl = uploadUtil.newUpload(file, cateGoryPic);
    		category.setPicPath(cateImageUrl);
    	}

    	 category.setCatName(category.getCatName().trim());
    	 category.setCatName(category.getCatName().replaceAll("\"", ""));
    	 String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~��@#��%����&*��������+|{}������������������������]";
         Pattern p = Pattern.compile(regEx);
         Matcher m = p.matcher(category.getCatName());
         category.setCatName(m.replaceAll("").trim());
    	 category.setCatDesc(category.getCatDesc());
    	 
         Map<Boolean, String> map = categoryManager.editCategory(category);
         for (Map.Entry<Boolean, String> tmp : map.entrySet()) {
                if (tmp.getKey()) {
                	request.setCharacterEncoding("UTF-8");
                    model.addAttribute("catNameForCallBackModify", oldCategory.getCatCode());
                    model.addAttribute("catNameForCallBackModifyNew", category.getCatName());
                    model.addAttribute("catCodeForModify", category.getCatCode());
                    model.addAttribute("catCode", category.getCatCode());
                    model.addAttribute("headmessage", "editsuccess");
                    return "redirect:/category/getci.html";
                } else {
        			result.rejectValue("catName", "", "��ͬ����Ŀ�Ѿ�����");
                    initCategory(category,model);
                    return "/category/category";
                }
         }

        initCategory(category,model);
        return "/category/category";
    }
    

    //ɾ����Ŀ
    @AdminAccess({EnumAdminPermission.A_CM_DELETE_USER})
    @RequestMapping(value = "/removec")
    public String removeCatagory(@RequestParam("catCode") String catCode, Model model) throws Exception {
    	if(StringUtil.isNotEmpty(catCode)){
    		Category category = categoryManager.getCategoryByCatCode(catCode);
    		model.addAttribute("catCode", category.getParentCode());
    		categoryManager.removeCategory(catCode,category);
            model.addAttribute("headmessage", "removesuccess");
    	}else{
            model.addAttribute("headerrormessage", "removefail");
    	}
    	
    	model.addAttribute("catNameForCallBackRemove", catCode);
    	return "redirect:/category/getci.html";
    }
    
    //��ת�����Թ���
    @RequestMapping(value = "/attriass")
    public String gotoAttriAssociate(@RequestParam("catCode") String catCode, Model model,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
    		@ModelAttribute("catAttrRel") CatAttrRel catAttrRel) throws Exception {
        if(StringUtil.isNotEmpty(catCode)){
        	catAttrRel.setCatCode(catCode);
        	model.addAttribute("catCode", catCode);
        }
        
        String attrNameUser = null;
        if(StringUtil.isNotEmpty(catAttrRel.getAttrName())){
        	attrNameUser = catAttrRel.getAttrName();
            String attrName = catAttrRel.getAttrName();
            attrName = "%" + StringUtil.trim(attrName).trim().replaceAll("%", " ") + "%";
            catAttrRel.setAttrName(attrName);
        }
        QueryPage page = attributeManager.getAttributesByNameByPage(catAttrRel, currPage, 20);
        if(page != null){
        	model.addAttribute("query", page);
        }
        catAttrRel.setAttrName(attrNameUser);
        return "/category/attributeAssociate";
    }

    //��������Թ���
    @RequestMapping(value = "/addca")
    public String addNewCatAttr(@RequestParam("catCode") String catCode,@RequestParam("sele") String sele, Model model) throws Exception {

        //ѡ�е�����id��
        String selectedAtt = sele;
        String[] ids = selectedAtt.split(",");
        CatAttrRel catAttrRel = new CatAttrRel();
        catAttrRel.setCatCode(catCode);
        for (int i = 0; i < ids.length; i++) {
            catAttrRel.setAttrCode(ids[i]);
            catAttrRelManager.addCatAttrRel(catAttrRel);
        }
        
        model.addAttribute("catCode", catCode);
        model.addAttribute("headmessage", "addcatrelsuccess");
        return "redirect:/category/getci.html";
    }
    
    //����˳��
    @RequestMapping(value = "/eo")
    public String exchangeSortOrder(@RequestParam("sortOrder") String sortOrder, @RequestParam("sourceId") String sourceId,
    		@RequestParam("catCode") String catCode, Model model) throws Exception {
        if (StringUtil.isNotEmpty(sortOrder)&& StringUtil.isNotEmpty(sourceId)&& StringUtil.isNotEmpty(catCode)) {
            //��ǰ��һ������˳��
            if (sortOrder.equalsIgnoreCase("-1")) {
                catAttrRelManager.exchangeSortOrderBefore(Long.parseLong(sourceId));
            }
            //�ͺ���һ������˳��
            if (sortOrder.equalsIgnoreCase("1")) {
                catAttrRelManager.exchangeSortOrderAfter(Long.parseLong(sourceId));
            }

        }
        model.addAttribute("catCode", catCode);
        model.addAttribute("message", "operatorsuccess");
        return "redirect:/category/getci.html";
    }
    

    //ɾ�����
    @RequestMapping(value = "/removeca")
    public String removeCatAttr(@RequestParam("catAttrId") String catAttrId, @RequestParam("catCode") String catCode, Model model) throws Exception {
        if (StringUtil.isNotEmpty(catAttrId) && StringUtil.isNotEmpty(catCode)) {
            if (null != catAttrRelManager.getCatAttrRel(Long.parseLong(catAttrId))) {
                this.catAttrRelManager.removeCatAttrRel(Long.parseLong(catAttrId));
                model.addAttribute("catCode", catCode);
                model.addAttribute("message", "removesuccess");
                return "redirect:/category/getci.html";
            }
            model.addAttribute("catCode", catCode);
            model.addAttribute("errormessage", "removefail");
            return "redirect:/category/getci.html";
        }
        model.addAttribute("catCode", catCode);
        model.addAttribute("errormessage", "removefail");
        return "redirect:/category/getci.html";
    }
    

    @AdminAccess({EnumAdminPermission.A_CM_ADD_USER})
    @RequestMapping(value = "/addcat")
    public String gotoAdd(@ModelAttribute("category")Category category, Model model,@RequestParam("parentCode") String parentCode) throws Exception{
    	getCategotyTree(model);
        model.addAttribute("parentCode", parentCode);
        return "/category/addcategory";
    }
    

    @RequestMapping(value = "/addc", method = RequestMethod.POST)
    public String addCatagory(@ModelAttribute("category") Category category, BindingResult result, Model model,
    		MultipartHttpServletRequest request,HttpServletResponse res,@RequestParam("parentCode") String parentCode) throws Exception {
    	categoryAddValidator.validate(category, result);
    	if(result.hasErrors()){
            model.addAttribute("parentCode", "-1");
            return "/category/addcategory";
    	}
    	
    	
    	MultipartFile file = request.getFile("catImage");
    	
    	if(file != null && file.getSize() > 0){
    		Long length = file.getSize();
    		int size = (int)(length / 1024);
    		if(size > 200){
    			result.rejectValue("picPath", "", "�ļ����ܳ���200K");
                initCategory(category,model);
                return "/category/category";
    		}
    		String cateGoryPic = "categorys" + Constants.FILE_SEP + DateUtil.getDateTime("yyyyMM", new Date());
    		String cateImageUrl = uploadUtil.newUpload(file, cateGoryPic);
    		category.setPicPath(cateImageUrl);
    	}
    	
   	    category.setCatName(category.getCatName().trim());
   	    if(StringUtil.isNotBlank(category.getCatDesc())){
   	    	category.setCatDesc(category.getCatDesc().trim());
   	    }
	    category.setIsSearch(1);
	    category.setParentCode(parentCode);
	    if(categoryManager.addCategory(category)){
	    	model.addAttribute("catNameForCallBackNew", category.getCatName());
	    	model.addAttribute("newCatCode", category.getCatCode());
	    	if(StringUtil.isEmpty(parentCode) || parentCode.equalsIgnoreCase("-1")){
	    		parentCode = "-1";
	    	}
	    	model.addAttribute("sourceName", parentCode);
	    	model.addAttribute("sourceCode", parentCode);
	        model.addAttribute("catCode", category.getCatCode());
	        model.addAttribute("headmessage", "addfirstsuccess");
	        
	        return "redirect:/category/getci.html";
	    }else{
	        model.addAttribute("catCode", category.getCatCode());
	        model.addAttribute("headerrormessage", "samecatgory");
	        return "redirect:/category/getci.html";
	    }
    }

}
