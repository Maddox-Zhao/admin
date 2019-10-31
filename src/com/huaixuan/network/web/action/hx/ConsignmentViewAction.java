package com.huaixuan.network.web.action.hx;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.hx.ConsignmentPro;
import com.huaixuan.network.biz.domain.hx.ConsignmentView;
import com.huaixuan.network.biz.domain.hx.Customer;
import com.huaixuan.network.biz.enums.hy.EnumBrandType;
import com.huaixuan.network.biz.enums.hy.EnumSeriesType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.hx.ConsignmentProService;
import com.huaixuan.network.biz.service.hx.ConsignmentViewService;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.ImageSizer;
import com.huaixuan.network.web.action.BaseAction;

/**
 * 2012-02-10 17:10
 * 
 * @author Mr_Yang
 * 
 */

@Controller
@RequestMapping("/consignment")
public class ConsignmentViewAction extends BaseAction
{
	@Autowired
	private ConsignmentViewService consignmentViewService;

	@Autowired
	private ConsignmentProService consignmentProService;

	private @Value("${file.upload.dir}")
	String path;

	/**
	 * 
	 * 查询寄卖凭证
	 * 
	 */
	@RequestMapping("/consignmentViewList")
	public String consignmentViewList(
			@ModelAttribute("consignmentView") ConsignmentView consignmentView,
			Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			@RequestParam(value = "isFirst", required = false, defaultValue = "false") String isFirst)
	{
		if ("true".equals(isFirst))
		{
			Date now = new Date();
			Timestamp nowTs = new Timestamp(DateUtil.getDate(now,1).getTime());
			Date before30 = DateUtil.getDate(now, -30);

			Timestamp beforeTs = new Timestamp(before30.getTime());
			if (StringUtils.isBlank(consignmentView.getDateStart()))
			{
				consignmentView.setDateStart(DateUtil
						.getTimestampToString(beforeTs));

			}
			if (StringUtils.isBlank(consignmentView.getDateEnd()))
			{
				consignmentView.setDateEnd(DateUtil.getTimestampToString(nowTs));
			}
		}

		QueryPage queryPage = consignmentViewService.getConsignmentViewByConditionWithPage(consignmentView,
						currPage, this.pageSize);
		if (queryPage != null)
		{
			model.addAttribute("query", queryPage);
		}

		return "/hx/consignmentViewList";
	}

	/**
	 * 更新寄卖信息
	 * 
	 * @param consignmentView
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")
	public String updateConsignment(
			@ModelAttribute("consignmentView") ConsignmentView consignmentView,HttpServletRequest request,
			Model model)
	{
		MultipartHttpServletRequest multipartHttp = (MultipartHttpServletRequest)request;
		MultipartFile idcardsImage = multipartHttp.getFile("cardid");
		MultipartFile conImage = multipartHttp.getFile("con");
		ImageSizer img = new ImageSizer();
		String idCardImageString = img.upLoadImage(idcardsImage,path); 
		String conImageString = img.upLoadImage(conImage,path);
		if(idCardImageString.equals("error"))
		{
			model.addAttribute("message", "uploadFile");
			model.addAttribute("consignmentView", consignmentView);
			model.addAttribute("id", consignmentView.getId());
			return "redirect:/consignment/detail.html";
		}
		else if(!idCardImageString.equals("error") && !idCardImageString.equals(""))
		{
			consignmentView.setIdcardsImage(idCardImageString);
		}
		if(conImageString.equals("error"))
		{
			model.addAttribute("message", "uploadFile");
			model.addAttribute("consignmentView", consignmentView);
			model.addAttribute("id", consignmentView.getId());
			return "redirect:/consignment/detail.html";
		}
		else if(!conImageString.equals("error") && !conImageString.equals(""))
		{
			consignmentView.setConImage(conImageString);
		}
		consignmentViewService.updateConsignmentView(consignmentView);
		model.addAttribute("message", "editsuccess");
		model.addAttribute("id", consignmentView.getId());
		return "redirect:/consignment/detail.html";

	}

	/**
	 * 保存寄卖凭证
	 * 
	 * @param consignmentView
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	public String addConsignment(
			@ModelAttribute("consignmentView") ConsignmentView consignmentView,
			@ModelAttribute("customer") Customer customer,HttpServletRequest request, Model model,AdminAgent adminAgent)
	{
		if (consignmentViewService.conCodeIsEixsts(consignmentView.getConCode()))
		{	
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile img1 = multipartRequest.getFile("img1");
			MultipartFile img2 = multipartRequest.getFile("img2");
			ImageSizer img = new ImageSizer();
			String consignmentImg = img.upLoadImage(img1,path); //上传寄卖凭证图片
			String cardid = img.upLoadImage(img2,path);//上传客户身份证图片
			if(consignmentImg.equals("error"))
			{
				model.addAttribute("message", "图片格式不正确或大于了200K！");
				model.addAttribute("consignmentView", consignmentView);
				return "/hx/saveInput";
			}
			else if(!consignmentImg.equals("error") && !consignmentImg.equals(""))
			{
				consignmentView.setConImage(consignmentImg);
			}
			if(cardid.equals("error"))
			{
				model.addAttribute("message", "图片格式不正确或大于了200K！");
				model.addAttribute("consignmentView", consignmentView);
			}
			else if(!cardid.equals("error") && !cardid.equals(""))
			{
				consignmentView.setIdcardsImage(cardid);
			}
			consignmentViewService.addConsignmentView(consignmentView, customer,adminAgent);
			model.addAttribute("message", "addsuccess");
			return "redirect:/consignment/consignmentViewList.html?isFirst=true";
		}
		else
		{
			model.addAttribute("message", "该编号已经存在");
			model.addAttribute("id", consignmentView.getId());
			return "/hx/saveInput";
		}
		
	}

	/**
	 * 删除寄卖凭证
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	public String deleteConsignment(String id, Model model)
	{
		consignmentViewService.deleteOneConsignmentView(id);
		model.addAttribute("message", "deletesuccess");
		model.addAttribute("isFirst", "true");
		return "redirect:/consignment/consignmentViewList.html?isFirst=true";
	}

	


	// 转跳到填写数据页面
	@RequestMapping("/saveInput")
	public String save_Input(Model model)
	{
		ConsignmentView consignmentView = new ConsignmentView();
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		model.addAttribute("consignmentView", consignmentView);

		return "/hx/saveInput";
	}

	// 转到详情信息页面
	@RequestMapping("/detail")
	public String update_Input(Model model, String id)
	{
		ConsignmentView consignmentView = consignmentViewService.getOneConsignmentView(id);
		List<ConsignmentPro> proList = consignmentProService.getConsignmentPro(id);
		model.addAttribute("consignmentView", consignmentView);
		model.addAttribute("proList", proList);
		model.addAttribute("brandMap", EnumBrandType.toMap());
		model.addAttribute("seriesMap", EnumSeriesType.toMap());
		return "/hx/consignmentViewDetail";

	}
	


}
