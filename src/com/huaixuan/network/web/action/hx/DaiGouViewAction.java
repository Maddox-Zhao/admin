package com.huaixuan.network.web.action.hx;

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
import com.huaixuan.network.biz.domain.hx.Customer;
import com.huaixuan.network.biz.domain.hx.DaiGou;
import com.huaixuan.network.biz.domain.hx.DaiGouPro;
import com.huaixuan.network.biz.enums.hy.EnumBrandType;
import com.huaixuan.network.biz.enums.hy.EnumPayment;
import com.huaixuan.network.biz.enums.hy.EnumSeriesType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.hx.DaiGouProService;
import com.huaixuan.network.biz.service.hx.DaiGouService;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.ImageSizer;
import com.huaixuan.network.web.action.BaseAction;

/**
 * 2012-03-08 17:17
 * 
 * @author Mr_Yang
 * 
 */
@Controller
@RequestMapping("/daigou")
public class DaiGouViewAction extends BaseAction
{

	@Autowired
	private DaiGouService daiGouService;

	@Autowired
	private DaiGouProService daigoupProsvice;

	private @Value("${file.upload.dir}")
	String path;

	/**
	 * 分页查询代购凭证
	 * 
	 */
	@RequestMapping("/list")
	public String daigouViewList(
			@ModelAttribute("daigou") DaiGou daigou,
			Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currpage,
			@RequestParam(value = "isFirst", required = false, defaultValue = "false") String isFirst)
	{
		if ("true".equals(isFirst))
		{
			Date now = new Date();
			if (daigou.getDateStart() == null
					|| daigou.getDateStart().equals(""))
			{
				daigou.setDateStart(DateUtil.getDate(now, -30));
			}
			if (daigou.getDateEnd() == null || daigou.getDateEnd().equals(""))
			{
				daigou.setDateEnd(DateUtil.getDate(now, 1));
			}
			
		}
		QueryPage queryPage = daiGouService.getDaiGouByConditionWithPage(
				daigou, currpage, this.pageSize);

		if (queryPage != null)
		{
			model.addAttribute("query", queryPage);
			model.addAttribute("paymentMap", EnumPayment.toMap());
		}
		return "hx/daigouViewList";
	}

	// 添加代购凭证
	@RequestMapping("/save")
	public String addDaiGou(@ModelAttribute("daigou") DaiGou daigou,
			@ModelAttribute("customer") Customer customer,
			HttpServletRequest request, Model model, AdminAgent adminAgent)
	{
		if (daiGouService.daiGouCodeIsExist(daigou.getDaigouCode()))
		{
			model.addAttribute("message", "该编号已经存在");
		}
		

		MultipartHttpServletRequest multiHttp = (MultipartHttpServletRequest) request;
		MultipartFile daigouImage = multiHttp.getFile("img");
		MultipartFile idcardsImage = multiHttp.getFile("img2");
		ImageSizer upLoadImg = new ImageSizer();
		String daigouImageUrl = upLoadImg.upLoadImage(daigouImage, path);
		String idcardsImageUrl = upLoadImg.upLoadImage(idcardsImage, path);
		if (daigouImageUrl.equals("error") || idcardsImageUrl.equals("error"))
		{
			model.addAttribute("message",
					"图片格式不正确,必须为jpg,jpeg,gif,bmp类型的,并且小于200K");
			model.addAttribute("daigou", daigou);
			return saveInput(daigou, customer, model);
		}
		if (!StringUtils.isBlank(daigouImageUrl)
				&& !daigouImageUrl.equals("error"))
		{
			daigou.setDaigouImage(daigouImageUrl);
		}
		if (!StringUtils.isBlank(idcardsImageUrl)
				&& !idcardsImageUrl.equals("error"))
		{
			daigou.setIdcardsImage(idcardsImageUrl);
		}

		daiGouService.addDaiGou(daigou, customer, adminAgent);
		model.addAttribute("isFirst", "true");
		model.addAttribute("message", "addsuccess");
		return "redirect:/daigou/list.html";
	}

	


	// 得到订单的详情信息
	@RequestMapping("/detail")
	public String detail(Long id, Model model)
	{
		DaiGou daigou = daiGouService.getDaiGouById(id);
		List<DaiGouPro> list = daigoupProsvice.getDaiGouProByDaiGouCode(daigou.getDaigouCode());
		model.addAttribute("daigou", daigou);
		model.addAttribute("daigouProList", list);
		model.addAttribute("seriesMap", EnumSeriesType.toMap());
		model.addAttribute("brandMap", EnumBrandType.toMap());
		return "hx/daigouViewDetail";
	}

	// 更新
	@RequestMapping("/update")
	public String updateByNotNull(@ModelAttribute("daigou") DaiGou daigou,
			Model model, HttpServletRequest request)
	{
		MultipartHttpServletRequest multiHttp = (MultipartHttpServletRequest) request;
		MultipartFile daigouImage = multiHttp.getFile("img");
		MultipartFile idcardsImage = multiHttp.getFile("img2");

		ImageSizer img = new ImageSizer();

		String daigouImageUrl = img.upLoadImage(daigouImage, path);
		String idcardsImageUrl = img.upLoadImage(idcardsImage, path);
		if (daigouImageUrl.equals("error"))
		{
			model.addAttribute("message", "imgerror");
			model.addAttribute("daigou", daigou);
			model.addAttribute("id", daigou.getId());
			return "redirect:/daigou/detail.html";
		}
		else if (!StringUtils.isBlank(daigouImageUrl)
				&& !daigouImageUrl.equals("error"))
		{
			daigou.setDaigouImage(daigouImageUrl);
		}
		if (idcardsImageUrl.equals("error"))
		{
			model.addAttribute("message", "imgerror");
			model.addAttribute("daigou", daigou);
			model.addAttribute("id", daigou.getId());
			return "redirect:/daigou/detail.html";

		}
		else if (!StringUtils.isBlank(idcardsImageUrl) && !idcardsImageUrl.equals("error"))
		{
			daigou.setIdcardsImage(idcardsImageUrl);
		}

		daigou.setGmtModify(new Date());
		daiGouService.updateDaiGouByNotNull(daigou);
		model.addAttribute("message", "updatesuccess");
		return detail(daigou.getId(), model);
	}

	// 删除代购凭证
	@RequestMapping("/delete")
	public String deleteDaiGou(Long id, Model model)
	{
		daiGouService.deleteDaiGouById(id);
		model.addAttribute("message", "deletesuccess");
		model.addAttribute("isFirest", "true");
		return "redirect:/daigou/list.html";
	}

	// 转到添加收购凭证页面
	@RequestMapping("/saveInput")
	public String saveInput(@ModelAttribute("daigou") DaiGou daigou,
			@ModelAttribute("customer") Customer customer, Model model)
	{
		model.addAttribute("paymentMap", EnumPayment.toMap());
		return "hx/daigouSaveInput";
	}

	@RequestMapping("/showimg")
	public String showImg(String url, Model model)
	{
		model.addAttribute("url", url);
		return "hx/showimg";
	}

}
