package com.huaixuan.network.web.action.storage;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.storage.Stockout;
import com.huaixuan.network.biz.domain.storage.query.StockoutQuery;
import com.huaixuan.network.biz.domain.storage.query.StorageQuery;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumStockOutStatus;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.storage.StockoutManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;

@Controller
@RequestMapping(value = "/storage" )
public class StockOutAction extends BaseAction {
    /**
     *
     */
    private static final long   serialVersionUID = -2326444322088978679L;
    @Autowired
    StockoutManager     stockoutManager;
    @Autowired
    GoodsBatchManager   goodsBatch;
    @Autowired
    CategoryManager     categoryManager;
    @Autowired
    AttributeManager    attributeManager;

    /**
     * 导出缺货登记
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/export_storage_out_excel" )
    public void exportStockOutExcel(
    		@ModelAttribute("stockoutQuery") StockoutQuery stockoutQuery,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
    		HttpServletResponse res) throws Exception{
        OutputStream outwt = null;
        QueryPage queryPage = stockoutManager.getStockoutList(stockoutQuery, currPage, pageSize, false);
        List<Stockout> stockouts = (List<Stockout>)queryPage.getItems();
        Date da = new Date();
        outwt = res.getOutputStream();
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
        String date = df.format(da);
        res.setHeader("Content-disposition", "attachment; filename=Out_Goods" + date + ".xls");
        res.setContentType("application/octet-stream;charset=utf-8");
        List<String[]> stockoutsList = new ArrayList<String[]>();
        String[] title = { "产品名称", "产品编码", "属性", "类目", "用户昵称", "用户Email", "创建时间", "上架通知"};
        stockoutsList.add(title);
        try {
        	if (stockouts != null && stockouts.size() > 0) {
               for (Stockout tmp : stockouts) {
            	SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	if(tmp.getNotifyStatus().equals("init")){
            		tmp.setNotifyStatus("未通知");
            	}else if(tmp.getNotifyStatus().equals("notified")){
            		tmp.setNotifyStatus("已通知");
            	}else{
            		tmp.setNotifyStatus("不需要通知");
            	}
            	String[] data ={tmp.getGoodsInstanceName() +"",tmp.getGoodsSn()+"",
            			attributeManager.getFullAttributeStringByAttrs(tmp.getGoodsDesc()) + "",
            			categoryManager.getCatFullNameByCatcode(tmp.getCatCode())+"",
            			tmp.getAccount()+"",tmp.getUserEmail()+"",df2.format(tmp.getGmtCreate())+"",
            			tmp.getNotifyStatus()+""
            	};
            	stockoutsList.add(data);
               }
        	}
	        goodsBatch.exportExcel(outwt, stockoutsList);
	        outwt.flush();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        outwt.close();
	    }
    }

    /**
     * 缺货登记列表
     */
    @AdminAccess({EnumAdminPermission.A_STOCKOUT_SELECT})
    @RequestMapping(value = "/list_storage_out_goods")
    public String showStockOutGoods(
    		@ModelAttribute("stockoutQuery") StockoutQuery stockoutQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			@RequestParam(value = "init", required = false, defaultValue = "") String init,
			Model model) {
        if("true".equals(init)){
        	stockoutQuery.setOptTimeStart(DateUtil.getDiffDate(new Date(), -30));
        	stockoutQuery.setOptTimeEnd(DateUtil.getDateToString(new Date()));
        }
        QueryPage queryPage = stockoutManager.getStockoutList(stockoutQuery, currPage, pageSize, true);
        model.addAttribute("stockOutStatus", EnumStockOutStatus.toMap());
        try {
			model.addAttribute("categorys", categoryManager.getCategoryForGuide());
		} catch (Exception e) {
			e.printStackTrace();
		}
        model.addAttribute("categoryManager", categoryManager);
        model.addAttribute("attributeManager", attributeManager);
        model.addAttribute("query", queryPage);
        return "/storage/list_storage_out_goods";
    }
}
