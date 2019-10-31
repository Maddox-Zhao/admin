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
import com.huaixuan.network.biz.domain.hx.DaiGou;
import com.huaixuan.network.biz.domain.hx.DaiGouPro;
import com.huaixuan.network.biz.enums.hy.EnumBrandType;
import com.huaixuan.network.biz.enums.hy.EnumSeriesType;
import com.huaixuan.network.biz.service.hx.DaiGouProService;
import com.huaixuan.network.biz.service.hx.DaiGouService;
import com.huaixuan.network.biz.service.shop.BrandService;
import com.huaixuan.network.common.util.ImageSizer;

/**
 * 2012-03-09 14:12
 * 
 * @author Mr_Yang
 * 
 */
@Controller
@RequestMapping("daigouPro")
public class DaiGouProAction
{
	@Autowired
	private DaiGouProService daigouProService;

	@Autowired
	private DaiGouService daigouService;

	@Autowired
	private BrandService brandService;

	private @Value("${file.upload.dir}")
	String path;

	@RequestMapping("/add")
	public String saveDaiGouPro(
			@ModelAttribute("daigouPro") DaiGouPro daigouPro,
			HttpServletRequest request, Model model, AdminAgent adminAgent)
	{

		MultipartHttpServletRequest multiHttp = (MultipartHttpServletRequest) request;
		MultipartFile pic = multiHttp.getFile("img");

		ImageSizer img = new ImageSizer();
		String picURL = img.upLoadImage(pic, path);

		if (picURL.equals("error"))
		{
			model.addAttribute("seriesList", EnumSeriesType.toMap());
			model.addAttribute("brandList", brandService.getBrands());
			model.addAttribute("message", "imgerror");
			return "hx/daigouSaveInputPro";
		}
		else if (!picURL.equals("error") && !picURL.equals(""))
		{
			daigouPro.setPic(picURL);
		}
		daigouProService.addDaiGouPro(daigouPro, adminAgent);
		model.addAttribute("message", "addsuccess");
		model.addAttribute("id", daigouPro.getDaigouId());
		return "redirect:/daigou/detail.html";
	}

	@RequestMapping("/addInput")
	public String savaInput(String daigouCode,
			@ModelAttribute("daigouPro") DaiGouPro daigouPro, Model model)
	{
		model.addAttribute("seriesList", EnumSeriesType.toMap());
		model.addAttribute("brandList", brandService.getBrands());
		DaiGou daigou = daigouService.selectDaigouByDaigouCode(daigouCode);
		daigouPro.setDaigouId(daigou.getId());
		daigouPro.setDaigouCode(daigouPro.getDaigouCode());
		return "hx/daigouSaveInputPro";
	}

	@RequestMapping("/delete")
	public String delete(Long id, Long daigouId, Model model)
	{
		daigouProService.deleteDaiGouProById(id);
		model.addAttribute("id", daigouId);
		model.addAttribute("message", "deletesuccess");
		return "redirect:/daigou/detail.html";
	}

	@RequestMapping("/update")
	public String update(@ModelAttribute("daigouPro") DaiGouPro daigouPro,
			HttpServletRequest request, Model model)
	{
		MultipartHttpServletRequest multiHttp = (MultipartHttpServletRequest) request;
		MultipartFile pic = multiHttp.getFile("img");
		ImageSizer img = new ImageSizer();
		String picURL = img.upLoadImage(pic, path);
		if (picURL.equals("error"))
		{
			model.addAttribute("message", "imgerror");
			model.addAttribute("daigouPro", daigouPro);
			model.addAttribute("seriesList", EnumBrandType.toMap());
			model.addAttribute("brandList", EnumSeriesType.toMap());
			return "hx/daigouUpdateInputPro";
		}
		else if (!picURL.equals("error") && !picURL.equals(""))
		{
			daigouPro.setPic(picURL);
		}
		daigouProService.updateDaiGouProByNotNull(daigouPro);
		model.addAttribute("message", "updatesuccess");
		model.addAttribute("id", daigouPro.getDaigouId());
		return "redirect:/daigou/detail.html";
	}

	@RequestMapping("/updateInput")
	public String updateInput(Long id, Model model)
	{
		DaiGouPro daigouPro = daigouProService.getDaiGouProById(id);
		model.addAttribute("seriesList", EnumBrandType.toMap());
		model.addAttribute("brandList", EnumSeriesType.toMap());
		model.addAttribute("daigouPro", daigouPro);
		return "hx/daigouUpdateInputPro";
	}

	// 转到取款
	@RequestMapping("/drawInput")
	public String draw(@RequestParam("id") Long id, Model model)
	{
		model.addAttribute("id", id);
		DaiGouPro daigouPro = daigouProService.getDaiGouProById(id);
		model.addAttribute("daigouPro", daigouPro);
		return "hx/daigouQuKuan";
	}

	// 取款
	@RequestMapping("/draw")
	public String drawInput(@RequestParam("id") Long id,
			@RequestParam("money") Double money,
			@RequestParam("type") String type, Model model,
			AdminAgent adminAgent)
	{
		DaiGouPro daigouPro = daigouProService.getDaiGouProById(id);
		daigouProService.draw(daigouPro, adminAgent, type, money);
		model.addAttribute("message", "qukuansuccess");
		model.addAttribute("id", daigouPro.getDaigouId());
		return "redirect:/daigou/detail.html";
	}

}
