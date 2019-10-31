package com.huaixuan.network.web.action.category;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.goods.Attribute;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
@RequestMapping(value = "/attr")
public class AttributeAction extends BaseAction {

	
	@Autowired
	private AttributeManager attributeManager;

    @Autowired
    private Validator   attrAddValidator;
    
    @Autowired
    private Validator   attrEditValidator;
	

    @AdminAccess({EnumAdminPermission.A_AM_VIEW_USER})
    @RequestMapping("/am")
    public String adminlist(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int currPage) {
    	QueryPage page = attributeManager.getAttrListByConditionWithPage(currPage, this.pageSize);
    	if(page != null){
    		model.addAttribute("query", page);
    	}
    	return "/category/attributeList";
    }
    
    @AdminAccess({EnumAdminPermission.A_AM_ADD_USER})
    @RequestMapping(value = "/addattr", method = RequestMethod.GET)
    public String add_admin(@ModelAttribute("attribute") Attribute attribute, Model model) {
    	return "/category/addattr";
    }
    

    @RequestMapping(value = "/adda", method = RequestMethod.POST)
    public String AddAttribute(HttpServletRequest request, @ModelAttribute("attribute") Attribute attribute, BindingResult result, Model model) throws Exception {
    	attrAddValidator.validate(attribute, result);
    	if(result.hasErrors()){
    		return "/category/addattr";
    	}
    	attribute.setAttrValues(request.getParameter("attrValues"));
        attribute.setAttrDesc(request.getParameter("attrDesc"));
        
        attribute.setAttrName(StringUtil.trim(attribute.getAttrName()));
        attribute.setAttrDesc(StringUtil.trim(attribute.getAttrDesc()));
        if(attributeManager.addAttribute(attribute)){
            model.addAttribute("message", "addsuccess");
            return "redirect:/attr/am.html";
        }
        
        model.addAttribute("message", "addfail");
        return "redirect:/attr/addattr.html";
    }
    

    @AdminAccess({EnumAdminPermission.A_AM_MODIFY_USER})
    @RequestMapping(value = "/edita", method = RequestMethod.GET)
    public String gotoEditAttribute(@RequestParam("id") String id, Model model) throws Exception {
        if(StringUtil.isNotEmpty(id)){
        	Attribute attribute = attributeManager.getAttribute(Long.parseLong(id));
        	model.addAttribute("attribute", attribute);
        	return "/category/editAttribute";
        }else{
            model.addAttribute("message", "该属性不存在");
            return "/goods/goods_error";
        }
    }
    
    
    @RequestMapping(value = "/ea", method = RequestMethod.POST)
    public String editAttribute(HttpServletRequest request,@ModelAttribute("attribute") Attribute attribute, BindingResult result, Model model) throws Exception {
    	attrEditValidator.validate(attribute, result);
    	
    	attribute.setAttrValues(request.getParameter("attrValues"));
    	attribute.setAttrDesc(request.getParameter("attrDesc"));
    	
    	if(result.hasErrors()){
    		return "/category/editAttribute";
    	}
    	
        attribute.setAttrName(StringUtil.trim(attribute.getAttrName()));
        attribute.setAttrDesc(StringUtil.trim(attribute.getAttrDesc()));

        if (attributeManager.editAttribute(attribute)) {
            model.addAttribute("message", "editsuccess");
            return "redirect:/attr/am.html";
        }else{
            model.addAttribute("message", "editfail");
            model.addAttribute("id", attribute.getId());
            return "redirect:/attr/edita.html";
        }
    }
    
    @AdminAccess({EnumAdminPermission.A_AM_DELETE_USER})
    @RequestMapping("/ra")
    public String removeAttribute(@RequestParam("id") String id, Model model) throws Exception {
        if(StringUtil.isNotEmpty(id)){
        	String attrCode = attributeManager.getAttribute(Long.parseLong(id)).getAttrCode();
        	if(attributeManager.removeAttribute(Long.parseLong(id), attrCode)){
                model.addAttribute("message", "removesuccess");
                return "redirect:/attr/am.html";
        	}else{
                model.addAttribute("errormessagetwo", "属性名称:" + attributeManager.getAttribute(Long.parseLong(id)).getAttrName() + "有相关类目与之关联,无法删除");
                return "redirect:/attr/am.html";
        	}
        }else{
            model.addAttribute("errormessage", "removefail");
            return "redirect:/attr/am.html";
        }
    }
	
    @AdminAccess({EnumAdminPermission.A_AM_DELETE_USER})
    @RequestMapping("/batchdel")
    public String batchDel(@RequestParam("attributeChecked") String attributeChecked, Model model) throws Exception {
    	
        String[] ids = attributeChecked.split(",");
        /*   for (int i = 0; i < ids.length; i++) {
         this.attributeManager.removeAttribute(Long.parseLong(ids[i]));
         }*/
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (i < ids.length) {
            Attribute attribute = this.attributeManager.getAttribute(Long.parseLong(ids[i]));
            String attrCode = attribute.getAttrCode();
            if (this.attributeManager.removeAttribute(Long.parseLong(ids[i]), attrCode)) {
                i += 1;
                continue;
            } else {
                sb.append(attribute.getAttrName());
                sb.append(" ");
                i += 1;
                continue;
            }
        }
        if (sb.length() > 0) {
            model.addAttribute("errormessage", "batchremovefail");
            return "redirect:/attr/am.html";
        }
        
        model.addAttribute("message", "batchremovesuccess");
        return "redirect:/attr/am.html";
    }
    
}
