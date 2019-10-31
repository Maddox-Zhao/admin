package com.huaixuan.network.web.action.hx;

import java.util.Map;

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
import com.huaixuan.network.biz.enums.hy.EnumBrandType;
import com.huaixuan.network.biz.enums.hy.EnumSeriesType;
import com.huaixuan.network.biz.service.hx.ConsignmentProService;
import com.huaixuan.network.biz.service.shop.BrandService;
import com.huaixuan.network.common.util.ImageSizer;

/**
 * 2012-02-16 14:36
 * 
 * @author Mr_Yang
 * 
 */
@Controller
@RequestMapping("/consignmentPro")
public class ConsignmentProAcion
{
	@Autowired
	private ConsignmentProService consignmentProService;
	
	@Autowired
	private BrandService brandService;

	private @Value("${file.upload.dir}")
	String path;

	// 添加数据
	@RequestMapping("/add")
	public String saveConsignmentPro(
			@ModelAttribute("consignmentPro") ConsignmentPro consignmentPro,
			HttpServletRequest request, Model model)
	{
		MultipartHttpServletRequest multiPartRequest = (MultipartHttpServletRequest)request;
		MultipartFile file = multiPartRequest.getFile("img");
		
		ImageSizer upLoadImg = new ImageSizer();
		String img = upLoadImg.upLoadImage(file, path);
	
		if (img.equals("error"))
		{
			model.addAttribute("consignmentPro", consignmentPro);
			model.addAttribute("message","imgError");
			model.addAttribute("brandList", brandService.getBrands());
			model.addAttribute("seriesList", EnumSeriesType.toMap());
			return "/hx/saveInputPro";
		}
		 if(!StringUtils.isBlank(img) && !img.equals("error"))
		{
			 consignmentPro.setPic(img);
		}
		
		consignmentProService.addConsignmentPro(consignmentPro);
		model.addAttribute("message", "addsuccess");
		model.addAttribute("id", consignmentPro.getConId());
		return "redirect:/consignment/detail.html";

	}

	@RequestMapping("/delete")
	public String deleteConsignmentPro(String proId, String conId, Model model)
	{
		consignmentProService.deleteConsignmentPro(proId,conId);
		model.addAttribute("message", "deletesuccess");
		model.addAttribute("id", conId);
		return "redirect:/consignment/detail.html";
	}

	// 更新数据
	@RequestMapping("/update")
	public String updateConsignmentPro(
			@ModelAttribute("consignmentPro") ConsignmentPro consignmentPro,HttpServletRequest request,
			Model model)
	{
		MultipartHttpServletRequest multiPartRequest = (MultipartHttpServletRequest)request;
		MultipartFile file = multiPartRequest.getFile("img");
		ImageSizer img = new ImageSizer();
		String pic = img.upLoadImage(file,path); //上传寄图片
		if(pic.equals("error"))
		{
			model.addAttribute("message", "imgError");
			Map<String,String> brand = EnumBrandType.toMap();
			Map<String,String> series = EnumSeriesType.toMap();
			model.addAttribute("brandList", brand);
			model.addAttribute("seriesList", series);
			model.addAttribute("consignmentPro", consignmentPro);
			return "hx/updateInputPro";
		}
		else if(!pic.equals("error") && !pic.equals(""))
			consignmentPro.setPic(pic);
		consignmentProService.updateConsignmentPro(consignmentPro);
		model.addAttribute("message", "updatesuccess");
		model.addAttribute("id", consignmentPro.getConId());
		return "redirect:/consignment/detail.html";
	}

	// 转跳到添加页面
	@RequestMapping("/addInput")
	public String addInput(String conId, String conCode, Model model)
	{
		ConsignmentPro consignmentPro = new ConsignmentPro();
		Map<String,String> series = EnumSeriesType.toMap();
		consignmentPro.setConId(conId);
		consignmentPro.setConCode(conCode);

		model.addAttribute("brandList", brandService.getBrands());
		
		model.addAttribute("seriesList", series);
		model.addAttribute("consignmentPro", consignmentPro);
		return "/hx/saveInputPro";
	}

	// 转跳到更新页面
	@RequestMapping("/updateInput")
	public String updateInput(String id, Model model)
	{
		ConsignmentPro consignmentPro = consignmentProService.getOneConsignmentPro(id);
		Map<String,String> brand = EnumBrandType.toMap();

		Map<String,String> series = EnumSeriesType.toMap();
		model.addAttribute("brandList", brand);
		model.addAttribute("seriesList", series);
		model.addAttribute("consignmentPro", consignmentPro);
		if (consignmentPro != null)
		{
			return "hx/updateInputPro";
		}
		return "hx/error";
	}
	
	// 客户取款
	@RequestMapping("/draw")
	public String drawConsignment(String id, int money, String conId,@RequestParam("type")String type,
			Model model,AdminAgent adminAgent)
	{
		adminAgent.setPassword(type); //保存选择的是现金还是银行转账
	    consignmentProService.updatePaidAmount(id, money, conId,adminAgent);
		model.addAttribute("id", conId);
		model.addAttribute("message", "qukuansuccess");
		return "redirect:/consignment/detail.html";
	}
	
	
	// 客户取款
	@RequestMapping("/drawInput")
	public String drawConsignmentInput(@RequestParam("id")String id, @RequestParam("conId")String conId,@RequestParam("kequ")Integer kequ,
			Model model)
	{
		model.addAttribute("id", id);
		model.addAttribute("conId", conId);
		model.addAttribute("kequ", kequ);
		return "hx/consignmentQuKuan";
	}
	
	//改变产品状态为已售
	@RequestMapping("/updateProStatus")
	public String updateProStatus(@RequestParam("proId")Long proId,@RequestParam("id")Long id,@RequestParam("type")String type,Model model)
	{
		consignmentProService.updateProStatusById(proId,type);
		model.addAttribute("id", id);
		return "redirect:/consignment/detail.html";
	}

	
}
