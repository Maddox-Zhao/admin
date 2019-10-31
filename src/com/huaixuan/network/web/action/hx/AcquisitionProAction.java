package com.huaixuan.network.web.action.hx;

import javax.servlet.http.HttpServletRequest;

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
import com.huaixuan.network.biz.domain.hx.Acquisition;
import com.huaixuan.network.biz.domain.hx.AcquisitionPro;
import com.huaixuan.network.biz.enums.hy.EnumBrandType;
import com.huaixuan.network.biz.enums.hy.EnumSeriesType;
import com.huaixuan.network.biz.service.hx.AcquisitionProService;
import com.huaixuan.network.biz.service.hx.AcquisitionService;
import com.huaixuan.network.biz.service.shop.BrandService;
import com.huaixuan.network.common.util.ImageSizer;
import com.huaixuan.network.web.action.BaseAction;

@Controller
@RequestMapping("/acquisitionPro")
public class AcquisitionProAction extends BaseAction
{

	@Autowired
	private AcquisitionProService acquisitionProService;

	@Autowired
	private AcquisitionService acquisitionService;
	
	@Autowired
	private BrandService brandService;

	private @Value("${file.upload.dir}")
	String path;

	@RequestMapping("/add")
	public String saveAcquisitionPro(
			@ModelAttribute("acquisitionPro") AcquisitionPro acquisitionPro,HttpServletRequest request,
			Model model,AdminAgent adminAgent)
	{
		MultipartHttpServletRequest multiHttp = (MultipartHttpServletRequest) request;
		MultipartFile pic = multiHttp.getFile("img");
		ImageSizer upLoadImg = new ImageSizer();
		String picURL = upLoadImg.upLoadImage(pic, path);
		if (picURL.equals("error"))
		{
			model.addAttribute("brandList", brandService.getBrands());
			model.addAttribute("seriesList", EnumSeriesType.toMap());
			model.addAttribute("message", "imgError");
			return "hx/acquisitionSaveInputPro";
		}
		else if (!picURL.equals("error") && !picURL.equals(""))
		{
			acquisitionPro.setPic(picURL);
		}
		acquisitionProService.addAcquisitionPro(acquisitionPro,adminAgent);
		
		
		model.addAttribute("message", "addsuccess");
		model.addAttribute("id", acquisitionPro.getAcqId());
		return "redirect:/acquisition/detail.html";
	}

	@RequestMapping("/update")
	public String updateAcquisitionPro(
			@ModelAttribute("acquisitionPro") AcquisitionPro acquisitionPro,
			HttpServletRequest request, Model model)
	{
		MultipartHttpServletRequest multiHttp = (MultipartHttpServletRequest) request;
		MultipartFile pic = multiHttp.getFile("img");
		ImageSizer upLoadImg = new ImageSizer();
		String picURL = upLoadImg.upLoadImage(pic, path);
		if (picURL.equals("error"))
		{
			model.addAttribute("message", "图片格式不正确或大于了200K！");
			model.addAttribute("brandList", brandService.getBrands());
			model.addAttribute("seriesList", EnumSeriesType.toMap());
			return  "hx/acquisitionUpdateInputPro";
		}
		else if (!picURL.equals("error") && !picURL.equals(""))
		{
			acquisitionPro.setPic(picURL);
		}
		acquisitionProService.updateAcquisitionProByNotNull(acquisitionPro);
		model.addAttribute("message", "updatesuccess");
		model.addAttribute("id", acquisitionPro.getAcqId());
		return "redirect:/acquisition/detail.html";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("id")Long id,@RequestParam("acqId")Long acqId,Model model )
	{
		acquisitionProService.deleteAcquisitionProById(id);
		model.addAttribute("messge", "deletesuccess");
		model.addAttribute("id", acqId);
		return "redirect:/acquisition/detail.html";
	}

	@RequestMapping("/addInput")
	public String savaInput(String acqCode,Model model)
	{
		Acquisition acquisition = acquisitionService.selectAcquisitionViewByAcpCode(acqCode);
		AcquisitionPro acquisitionPro = new AcquisitionPro();
		acquisitionPro.setAcqCode(acqCode);
		acquisitionPro.setAcqId(acquisition.getId());
		model.addAttribute("brandList", brandService.getBrands());
		model.addAttribute("seriesList", EnumSeriesType.toMap());
		model.addAttribute("acquisitionPro", acquisitionPro);
		return "hx/acquisitionSaveInputPro";
	}

	@RequestMapping("/updateInput")
	public String updateInput(Long id, Model model)
	{
		AcquisitionPro acquisitionPro = acquisitionProService.getOneAcquisitionById(id);
		model.addAttribute("seriesList", EnumBrandType.toMap());
		model.addAttribute("brandList", EnumSeriesType.toMap());
		model.addAttribute("acquisitionPro", acquisitionPro);
		return "hx/acquisitionUpdateInputPro";
	}
}
