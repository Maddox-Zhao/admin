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
import com.huaixuan.network.biz.domain.hx.Acquisition;
import com.huaixuan.network.biz.domain.hx.AcquisitionPro;
import com.huaixuan.network.biz.domain.hx.Customer;
import com.huaixuan.network.biz.enums.hy.EnumBrandType;
import com.huaixuan.network.biz.enums.hy.EnumSeriesType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.hx.AcquisitionProService;
import com.huaixuan.network.biz.service.hx.AcquisitionService;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.ImageSizer;
import com.huaixuan.network.web.action.BaseAction;

/**
 * 2012-03-02 12:04
 * 
 * @author Mr_Yang
 * 
 */
@Controller
@RequestMapping("/acquisition")
public class AcquisitionViewAction extends BaseAction
{

	@Autowired
	private AcquisitionService acquisitionService;

	@Autowired
	private AcquisitionProService acquisitionProService;

	private @Value("${file.upload.dir}")
	String path;

	/**
	 * ��ҳ��ѯ�չ�ƾ֤
	 * 
	 * @param acquisition
	 * @param model
	 * @param currpage
	 * @param isFirst
	 * @return
	 */
	@RequestMapping("/list")
	public String acquisitionViewList(
			@ModelAttribute("acquisitionView") Acquisition acquisition,
			Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currpage,
			@RequestParam(value = "isFirst", required = false, defaultValue = "false") String isFirst)
	{
		if ("true".equals(isFirst))
		{
			Date now = new Date();
			if (acquisition.getDateStart() == null
					|| acquisition.getDateStart().equals(""))
			{
				acquisition.setDateStart(DateUtil.getDate(now, -30));
			}
			if (acquisition.getDateEnd() == null
					|| acquisition.getDateEnd().equals(""))
			{
				acquisition.setDateEnd(DateUtil.getDate(now, 1));
			}
		}
		QueryPage queryPage = acquisitionService
				.getAcquisitionViewByConditionWithPage(acquisition, currpage,
						this.pageSize);
		if (queryPage != null)
		{
			model.addAttribute("query", queryPage);
		}
		return "hx/acquisitionViewList";
	}

	// ����չ�ƾ֤
	@RequestMapping("/save")
	public String addAcquisition(
			@ModelAttribute("acquisition") Acquisition acquisition,
			@ModelAttribute("customer") Customer customer,
			HttpServletRequest request, Model model, AdminAgent adminAgent)
	{
		if (acquisitionService.theAcpCodeIsExist(acquisition.getAcpCode()))
		{
			model.addAttribute("message", "�ñ���Ѿ�����");
			return saveInput(acquisition, customer, model);
		}

		MultipartHttpServletRequest multiHttp = (MultipartHttpServletRequest) request;
		MultipartFile acqImage = multiHttp.getFile("img");
		MultipartFile idcardsImage = multiHttp.getFile("img2");
		ImageSizer upLoadImg = new ImageSizer();
		String acqImageUrl = upLoadImg.upLoadImage(acqImage, path);
		String idcardsImageUrl = upLoadImg.upLoadImage(idcardsImage, path);
		if (acqImageUrl.equals("error") || idcardsImageUrl.equals("error"))
		{
			model.addAttribute("acquisition", acquisition);
			model.addAttribute("message",
					"ͼƬ��ʽ����ȷ,����Ϊjpg,jpeg,gif,bmp���͵�,����С��200K");
			return "hx/acquisitionSaveInput";
		}
		if (!StringUtils.isBlank(acqImageUrl) && !acqImageUrl.equals("error"))
		{
			acquisition.setAcqImage(acqImageUrl);
		}
		if (!StringUtils.isBlank(idcardsImageUrl)
				&& !idcardsImageUrl.equals("error"))
		{
			acquisition.setIdcardsImage(idcardsImageUrl);
		}

		acquisitionService
				.addAcquisitionView(acquisition, customer, adminAgent);
		model.addAttribute("isFirst", "true");
		model.addAttribute("message", "addsuccess");
		return "redirect:/acquisition/list.html";
	}

	// �õ�������������Ϣ
	@RequestMapping("/detail")
	public String detail(Long id, Model model)
	{
		Acquisition acquisition = acquisitionService.getAcquisitionById(id);
		List<AcquisitionPro> list = acquisitionProService
				.getAcquisitionByAcqCode(acquisition.getAcpCode());
		model.addAttribute("acquisition", acquisition);
		model.addAttribute("acquisitionProList", list);
		model.addAttribute("brandMap", EnumBrandType.toMap());
		model.addAttribute("seriesMap", EnumSeriesType.toMap());
		return "hx/acquisitionViewDetail";
	}

	// ����
	@RequestMapping("/update")
	public String updateByNotNull(
			@ModelAttribute("acquisition") Acquisition acquisition,
			Model model, HttpServletRequest request)
	{
		MultipartHttpServletRequest multiPartRequest = (MultipartHttpServletRequest) request;
		MultipartFile cardId = multiPartRequest.getFile("cardId");
		MultipartFile con = multiPartRequest.getFile("con");
		ImageSizer img = new ImageSizer();
		String cardIdString = img.upLoadImage(cardId, path);
		String conImageString = img.upLoadImage(con, path);
		if (cardIdString.equals("error"))
		{
			model.addAttribute("message", "uploadFile");
			model.addAttribute("consignmentView", acquisition);
			model.addAttribute("id", acquisition.getId());
			return "redirect:/acquisition/detail.html";
		}
		else if (!cardIdString.equals("error") && !cardIdString.equals(""))
		{
			acquisition.setIdcardsImage(cardIdString);
		}
		if (conImageString.equals("error"))
		{
			model.addAttribute("message", "uploadFile");
			model.addAttribute("consignmentView", acquisition);
			model.addAttribute("id", acquisition.getId());
			return "redirect:/acquisition/detail.html";
		}
		else if (!conImageString.equals("error") && !conImageString.equals(""))
		{
			acquisition.setAcqImage(conImageString);
		}
		acquisition.setGmtModify(new Date());
		acquisitionService.updateAcquisitionViewByNotNull(acquisition);
		model.addAttribute("message", "updatesuccess");
		model.addAttribute("id", acquisition.getId());
		return "redirect:/acquisition/detail.html";
	}

	// ɾ������ƾ֤
	@RequestMapping("/delete")
	public String deleteAcquisition(Long id, Model model)
	{
		acquisitionService.deleteAcquisitionViewById(id);
		model.addAttribute("message", "deletesuccess");
		model.addAttribute("isFirest", "true");
		return "redirect:/acquisition/list.html";
	}

	// ת������չ�ƾ֤ҳ��
	@RequestMapping("/saveInput")
	public String saveInput(
			@ModelAttribute("acquisition") Acquisition acquisition,
			@ModelAttribute("customer") Customer customer, Model model)
	{
		return "hx/acquisitionSaveInput";
	}

	// ȡ��
	@RequestMapping("/drawInput")
	public String draw(@RequestParam("acqId") Long acqId,
			@RequestParam("id") Long id, @RequestParam("money") Double money,
			Model model, AdminAgent adminAgent)
	{
		model.addAttribute("acqId", acqId);
		model.addAttribute("id", id);
		model.addAttribute("kequ", money);
		return "hx/acquisitionQuKuan";
	}

	// ת��ȡ��
	@RequestMapping("/draw")
	public String drawInput(@RequestParam("acqId") Long acqId,
			@RequestParam("id") Long id, @RequestParam("money") Double money,
			@RequestParam("type") String type, Model model,
			AdminAgent adminAgent)
	{
		adminAgent.setPassword(type); //����ѡ������ֽ�������ת��
		acquisitionProService.draw(id, acqId, adminAgent, money);
		model.addAttribute("message", "qukuansuccess");
		model.addAttribute("id", acqId);
		return "redirect:/acquisition/detail.html";
	}

}
