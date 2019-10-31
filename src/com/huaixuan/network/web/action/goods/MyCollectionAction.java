package com.huaixuan.network.web.action.goods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.goods.MyCollection;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.MyCollectionManager;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;

@Controller
@RequestMapping(value = "/goods")
public class MyCollectionAction extends BaseAction{

	@Autowired
	private MyCollectionManager myCollectionManager;
	
	@AdminAccess({EnumAdminPermission.A_COLLECTION_VIEW_USER})
    @RequestMapping("/searchCollection")
    public String adminlist(@ModelAttribute("myCollection") MyCollection myCollection, Model model,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage) {
    	QueryPage page = myCollectionManager.getMyCollectionListByConditionWithPage(myCollection, currPage, this.pageSize);
    	if(page != null){
    		model.addAttribute("query", page);
    	}
    	return "/goods/searchCollection";
    }
}
