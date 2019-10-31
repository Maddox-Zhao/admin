package com.huaixuan.network.web.action.hx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.hx.DxrecordService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.huaixuan.network.web.action.BaseAction;

@Controller
@RequestMapping("/dxrecord")
public class DxrecordViewAction extends BaseAction{

	@Autowired
	private DxrecordService dxrecordService;
	
	@RequestMapping("/todxrecordlist")
	public String todxrecordlist(){
		return "/hx/dxrecordlist" ;
		
	}
	//查看登录代销用户
	@RequestMapping("/dxrecordlist")
	public @ResponseBody Object list(HttpServletRequest request){
		Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
		String type = request.getParameter("type");
		if(type.equals("1")){
			//代销用户登录信息查看
			QueryPage  queryPage= dxrecordService.queryDxrecordList(searchMap);
			MiniUiGrid gird = new MiniUiGrid();
			gird.setData(queryPage.getItems());
			gird.setTotal(queryPage.getTotalItem());
			return gird;
		}
		if(type.equals("2")){
			//统计各代销用户登录次数
			QueryPage  queryPage = dxrecordService.statisticsDxrecordList(searchMap);
			MiniUiGrid gird = new MiniUiGrid();
			gird.setData(queryPage.getItems());
			gird.setTotal(queryPage.getTotalItem());
			return gird;
		}
		return "/dxrecord/todxrecordlist";
		
	}
}
