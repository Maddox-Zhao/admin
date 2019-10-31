package com.huaixuan.network.web.action.express;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.express.Castweight;
import com.huaixuan.network.biz.domain.express.Express;
import com.huaixuan.network.biz.domain.express.ExpressAnalysis;
import com.huaixuan.network.biz.domain.express.ExpressDist;
import com.huaixuan.network.biz.domain.express.MotorTransInfo;
import com.huaixuan.network.biz.domain.express.Region;
import com.huaixuan.network.biz.domain.express.query.ExpressDistQuery;
import com.huaixuan.network.biz.domain.express.query.ExpressQuery;
import com.huaixuan.network.biz.domain.express.query.FreightStatisticsQuery;
import com.huaixuan.network.biz.domain.express.query.MotorTransQuery;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumExpressDistPaymentPart;
import com.huaixuan.network.biz.enums.EnumExpressDistStatus;
import com.huaixuan.network.biz.enums.EnumExpressStatus;
import com.huaixuan.network.biz.enums.EnumTaobaoExpress;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.express.CastweightManager;
import com.huaixuan.network.biz.service.express.ExpressDistManager;
import com.huaixuan.network.biz.service.express.ExpressManager;
import com.huaixuan.network.biz.service.express.RegionManager;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.DoubleUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * ����������
 *
 * @author fanyj 2011/03/01
 *
 */
@Controller
@RequestMapping(value = "/express")
public class ExpressAction extends BaseAction {

	private static final long serialVersionUID = -7351373533102538386L;
	// serviceע��
	@Autowired
	private ExpressManager expressManager;
	@Autowired
	private ExpressDistManager expressDistManager;
	@Autowired
	private RegionManager regionManager;
	@Autowired
	private CastweightManager castweightManager;
	@Autowired
	private GoodsManager goodsManager;
	@Autowired
	GoodsBatchManager goodsBatch;
	@Autowired
	DepositoryFirstManager depositoryFirstManager;

	// ���������ڲ������ݣ�
	private Boolean isAddOpt = Boolean.FALSE;
	private List<Express> expressInfoList;
	private List<ExpressAnalysis> expressAnalysisList;
	private List<String> year;
	private Castweight castWeight;
	private List<Region> provinceList;

	/**
	 * ��������˾��������ͳ��
	 *
	 * @author chenhang 2011/01/15
	 */
	@RequestMapping(value = "/list_express_analysis_by_exp")
	@AdminAccess({ EnumAdminPermission.A_EXPRESS_ANALYSIS_BY_EXP })
	public String expressAnalysisByExp(
			@ModelAttribute("expressDistQuery") ExpressDistQuery expressDistQuery,
			Model model) throws Exception {
		// ȡ����Ч��������˾
		expressInfoList = expressManager
				.listExpressByStatus(EnumExpressStatus.ENABLED.getKey());
		model.addAttribute("expressInfoList", expressInfoList);
		Date start = new Date();
		Date end = new Date();
		DateFormat fm = new SimpleDateFormat("yyyy-MM");// ����ʱ����������¼������
		if (StringUtil.isBlank(expressDistQuery.getGmtOutDepStart())
				&& StringUtil.isBlank(expressDistQuery.getGmtOutDepEnd())) {
			// ��ʼ��ʱ����Ҫ����Ĭ��ʱ��
			end = new Date();
			start = DateUtil.getDate(end, -30);
			expressDistQuery.setGmtOutDepStart(fm.format(start));
			expressDistQuery.setGmtOutDepEnd(fm.format(end));
		} else {
			start = fm.parse(expressDistQuery.getGmtOutDepStart());
			end = fm.parse(expressDistQuery.getGmtOutDepEnd());
		}
		model.addAttribute("query",
				this.getExpressByExp(expressDistQuery, start, end));
		return "/express/list_express_analysis_by_exp";
	}

	@SuppressWarnings("unchecked")
	private QueryPage getExpressByExp(ExpressDistQuery expressDistQuery,
			Date start, Date end) throws Exception {
		DateFormat fm = new SimpleDateFormat("yyyy-MM");
		// �����·ݲ�ֵ
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(start);
		c2.setTime(end);
		int value = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12
				+ c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH) + 1;
		year = new ArrayList<String>();
		for (int i = value; i > 0; i--) {
			year.add(fm.format(c1.getTime()));
			c1.add(Calendar.MONTH, 1);
		}

		List<Long> exprId = new ArrayList<Long>();
		if (StringUtil.isNotBlank(expressDistQuery.getExpressId())) {
			exprId.add(Long.valueOf(expressDistQuery.getExpressId().trim()));
		} else {
			for (Express e : expressInfoList) {
				exprId.add(e.getId());
			}
		}

		QueryPage queryPage = expressManager.getExpressAnalysisByExp(
				expressDistQuery, 0, pageSize, false);
		List<ExpressAnalysis> ea = (List<ExpressAnalysis>) queryPage.getItems();
		for (ExpressAnalysis e : ea) {
			if (e.getActualInventorySum() != null) {
				e.setAverge(e.getActualInventorySum() / e.getOutDepSum());
			} else {
				e.setAverge(0D);
			}
		}
		expressAnalysisList = new ArrayList<ExpressAnalysis>();
		for (long i : exprId) {
			boolean b = false;
			for (String y : year) {
				if (b == true) {
					break;
				}
				boolean c = true;
				for (ExpressAnalysis as : ea) {
					c = true;
					if (null != as.getExpressId()) {
						if (i < as.getExpressId()
								&& fm.parse(y).getTime() > fm.parse(
										as.getGmtOutDep()).getTime()) {
							Calendar c3 = Calendar.getInstance();
							c3.setTime(fm.parse(y));
							for (; c3.getTime().getTime() <= end.getTime();) {
								ExpressAnalysis temp = new ExpressAnalysis();
								temp.setExpressId(i);
								temp.setGmtOutDep(fm.format(c3.getTime()));
								temp.setOutDepSum(0L);
								temp.setActualInventorySum(0.00);
								temp.setShippingAmountSum(0.00);
								temp.setAverge(0.00);
								expressAnalysisList.add(temp);
								c3.add(Calendar.MONTH, 1);
							}
							b = true;
							c = false;
							break;
						} else if (i == as.getExpressId()
								&& y.equals(as.getGmtOutDep())) {
							expressAnalysisList.add(as);
							c = false;
							break;
						} else if (i == as.getExpressId()
								&& y != as.getGmtOutDep()
								&& fm.parse(y).getTime() <= fm.parse(
										as.getGmtOutDep()).getTime()) {
							ExpressAnalysis temp = new ExpressAnalysis();
							temp.setExpressId(i);
							temp.setGmtOutDep(y);
							temp.setOutDepSum(0L);
							temp.setActualInventorySum(0.00);
							temp.setShippingAmountSum(0.00);
							temp.setAverge(0.00);
							expressAnalysisList.add(temp);
							c = false;
							break;
						}
					}
				}
				if (c == true) {
					Calendar c3 = Calendar.getInstance();
					c3.setTime(fm.parse(y));
					for (; c3.getTime().getTime() <= end.getTime();) {
						ExpressAnalysis temp = new ExpressAnalysis();
						temp.setExpressId(i);
						temp.setGmtOutDep(fm.format(c3.getTime()));
						temp.setOutDepSum(0L);
						temp.setActualInventorySum(0.00);
						temp.setShippingAmountSum(0.00);
						temp.setAverge(0.00);
						expressAnalysisList.add(temp);
						c3.add(Calendar.MONTH, 1);
					}
					b = true;
				}
			}
		}
		queryPage.setItems(expressAnalysisList);
		return queryPage;
	}

	@SuppressWarnings("unchecked")
	private QueryPage getExpressByPro(ExpressDistQuery expressDistQuery,
			Date start, Date end) throws Exception {
		DateFormat fm = new SimpleDateFormat("yyyy-MM");
		// �����·ݲ�ֵ
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(start);
		c2.setTime(end);
		int value = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12
				+ c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH) + 1;
		year = new ArrayList<String>();
		for (int i = value; i > 0; i--) {
			year.add(fm.format(c1.getTime()));
			c1.add(Calendar.MONTH, 1);
		}

		List<Long> regCode = new ArrayList<Long>();
		if (StringUtil.isNotBlank(expressDistQuery.getRegionCode())) {
			regCode.add(Long.valueOf(expressDistQuery.getRegionCode().trim()));
		} else {
			for (Region r : regionManager.getRegionByType(2)) {
				regCode.add(Long.valueOf(r.getCode()));
			}
		}

		QueryPage queryPage = expressManager.getExpressAnalysisByPro(
				expressDistQuery, 0, pageSize, false);
		List<ExpressAnalysis> ea = (List<ExpressAnalysis>) queryPage.getItems();
		expressAnalysisList = new ArrayList<ExpressAnalysis>();
		for (long i : regCode) {
			boolean b = false;
			for (String y : year) {
				if (b == true) {
					break;
				}
				boolean c = true;
				for (ExpressAnalysis as : ea) {
					c = true;
					if (null != as.getRegionCode()) {
						if (i < as.getRegionCode()
								&& fm.parse(y).getTime() > fm.parse(
										as.getGmtOutDep()).getTime()) {
							Calendar c3 = Calendar.getInstance();
							c3.setTime(fm.parse(y));
							for (; c3.getTime().getTime() <= end.getTime();) {
								ExpressAnalysis temp = new ExpressAnalysis();
								temp.setRegionCode(i);
								temp.setGmtOutDep(fm.format(c3.getTime()));
								temp.setOutDepSum(0L);
								temp.setActualInventorySum(0.00);
								temp.setShippingAmountSum(0.00);
								expressAnalysisList.add(temp);
								c3.add(Calendar.MONTH, 1);
							}
							b = true;
							c = false;
							break;
						} else if (i == as.getRegionCode()
								&& y.equals(as.getGmtOutDep())) {
							expressAnalysisList.add(as);
							c = false;
							break;
						} else if (i == as.getRegionCode()
								&& y != as.getGmtOutDep()
								&& fm.parse(y).getTime() <= fm.parse(
										as.getGmtOutDep()).getTime()) {
							ExpressAnalysis temp = new ExpressAnalysis();
							temp.setRegionCode(i);
							temp.setGmtOutDep(y);
							temp.setOutDepSum(0L);
							temp.setActualInventorySum(0.00);
							temp.setShippingAmountSum(0.00);
							expressAnalysisList.add(temp);
							c = false;
							break;
						}
					}
				}
				if (c == true) {
					Calendar c3 = Calendar.getInstance();
					c3.setTime(fm.parse(y));
					for (; c3.getTime().getTime() <= end.getTime();) {
						ExpressAnalysis temp = new ExpressAnalysis();
						temp.setRegionCode(i);
						temp.setGmtOutDep(fm.format(c3.getTime()));
						temp.setOutDepSum(0L);
						temp.setActualInventorySum(0.00);
						temp.setShippingAmountSum(0.00);
						expressAnalysisList.add(temp);
						c3.add(Calendar.MONTH, 1);
					}
					b = true;
				}
			}
		}
		queryPage.setItems(expressAnalysisList);
		return queryPage;
	}

	/**
	 * ��������˾��������ͳ�Ƶ�excel����
	 *
	 * @author chenhang 2011/01/15
	 */
	@RequestMapping(value = "/export_analysis_by_exp")
	@SuppressWarnings("unchecked")
	public String exportAnalysisByExp(
			@ModelAttribute("expressDistQuery") ExpressDistQuery expressDistQuery,
			@RequestParam(value = "init", required = false, defaultValue = "") String initFlag,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// ȡ����Ч��������˾
		expressInfoList = expressManager
				.listExpressByStatus(EnumExpressStatus.ENABLED.getKey());
		Date start = new Date();
		Date end = new Date();
		DateFormat fm = new SimpleDateFormat("yyyy-MM");// ����ʱ����������¼������
		if (StringUtil.isNotBlank(initFlag) && initFlag.equals("true")) {
			// ��ʼ��ʱ����Ҫ����Ĭ��ʱ��
			end = new Date();
			start = DateUtil.getDate(end, -30);
			expressDistQuery.setGmtOutDepStart(fm.format(start));
			expressDistQuery.setGmtOutDepEnd(fm.format(end));
		} else {
			start = fm.parse(expressDistQuery.getGmtOutDepStart());
			end = fm.parse(expressDistQuery.getGmtOutDepEnd());
		}
		QueryPage queryPage = this
				.getExpressByExp(expressDistQuery, start, end);
		expressAnalysisList = (List<ExpressAnalysis>) queryPage.getItems();
		OutputStream outwt = null;
		try {
			Date da = new Date();
			outwt = response.getOutputStream();
			// ȡ�õ���excel��ʱ�䣬�����ļ�����
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			response.setHeader("Content-disposition",
					"attachment; filename=expressAnalysisByExpList" + date
							+ ".xls");
			response.setContentType("application/octet-stream;charset=utf-8");
			List<String[]> expressAnalysisExportList = new ArrayList<String[]>();
			String[] title = { "������˾", "ͳ��ʱ��", "����", "ʵ���˷�", "ϵͳ�˷�", "ƽ���˷�" };
			expressAnalysisExportList.add(title);
			// SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			if (expressAnalysisList != null) {
				for (ExpressAnalysis tmp : expressAnalysisList) {
					for (Express e : expressInfoList) {
						if (null != tmp.getExpressId()
								&& tmp.getExpressId().intValue() == e.getId()
										.intValue()) {
							String[] data = {
									e.getExpressName(),
									tmp.getGmtOutDep(),
									String.valueOf(tmp.getOutDepSum()),
									String.valueOf(DoubleUtil.round(
											tmp.getActualInventorySum(), 2)),
									String.valueOf(DoubleUtil.round(
											tmp.getShippingAmountSum(), 2)),
									String.valueOf(DoubleUtil.round(
											tmp.getAverge(), 2)) };
							expressAnalysisExportList.add(data);
						}
					}
				}
			}
			goodsBatch.exportExcel(outwt, expressAnalysisExportList);
			outwt.flush();
		} catch (Exception e) {
		} finally {
			close(outwt);
		}
		return "/express/list_express_analysis_by_exp";
	}

	/**
	 * ��ʡ�ݽ�������ͳ��
	 *
	 * @author chenhang 2011/01/15
	 */
	@RequestMapping(value = "/list_express_analysis_by_pro")
	@AdminAccess({ EnumAdminPermission.A_EXPRESS_ANALYSIS_BY_PRO })
	public String expressAnalysisByPro(
			@ModelAttribute("expressDistQuery") ExpressDistQuery expressDistQuery,
			Model model) throws Exception {
		// ȡ��ʡ��
		model.addAttribute("provinceList", regionManager.getRegionByType(2));
		Date start = new Date();
		Date end = new Date();
		DateFormat fm = new SimpleDateFormat("yyyy-MM");// ����ʱ����������¼������
		if (StringUtil.isBlank(expressDistQuery.getGmtOutDepStart())
				&& StringUtil.isBlank(expressDistQuery.getGmtOutDepEnd())) {
			// ��ʼ��ʱ����Ҫ����Ĭ��ʱ��
			end = new Date();
			start = DateUtil.getDate(end, -30);
			expressDistQuery.setGmtOutDepStart(fm.format(start));
			expressDistQuery.setGmtOutDepEnd(fm.format(end));
		} else {
			start = fm.parse(expressDistQuery.getGmtOutDepStart());
			end = fm.parse(expressDistQuery.getGmtOutDepEnd());
		}
		model.addAttribute("query",
				this.getExpressByPro(expressDistQuery, start, end));
		return "/express/list_express_analysis_by_pro";
	}

	/**
	 * ��ʡ�ݽ�������ͳ�Ƶ�excel����
	 *
	 * @author chenhang 2011/01/15
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/export_analysis_by_pro")
	public String exportAnalysisByPro(
			@ModelAttribute("expressDistQuery") ExpressDistQuery expressDistQuery,
			@RequestParam(value = "init", required = false, defaultValue = "") String initFlag,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// ȡ��ʡ��
		provinceList = regionManager.getRegionByType(2);
		Date start = new Date();
		Date end = new Date();
		DateFormat fm = new SimpleDateFormat("yyyy-MM");// ����ʱ����������¼������
		if (StringUtil.isNotBlank(initFlag) && initFlag.equals("true")) {
			// ��ʼ��ʱ����Ҫ����Ĭ��ʱ��
			end = new Date();
			start = DateUtil.getDate(end, -30);
			expressDistQuery.setGmtOutDepStart(fm.format(start));
			expressDistQuery.setGmtOutDepEnd(fm.format(end));
		} else {
			start = fm.parse(expressDistQuery.getGmtOutDepStart());
			end = fm.parse(expressDistQuery.getGmtOutDepEnd());
		}
		QueryPage queryPage = getExpressByPro(expressDistQuery, start, end);
		expressAnalysisList = (List<ExpressAnalysis>) queryPage.getItems();
		OutputStream outwt = null;
		try {
			Date da = new Date();
			outwt = response.getOutputStream();
			// ȡ�õ���excel��ʱ�䣬�����ļ�����
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			response.setHeader("Content-disposition",
					"attachment; filename=expressAnalysisByProList" + date
							+ ".xls");
			response.setContentType("application/octet-stream;charset=utf-8");
			List<String[]> expressAnalysisExportList = new ArrayList<String[]>();
			String[] title = { "ʡ��", "ͳ��ʱ��", "����", "ʵ���˷�", "ϵͳ�˷�" };
			expressAnalysisExportList.add(title);
			if (expressAnalysisList != null) {
				for (ExpressAnalysis tmp : expressAnalysisList) {
					for (Region r : provinceList) {
						if (null != tmp.getRegionCode()) {
							if (tmp.getRegionCode().equals(
									Long.valueOf(r.getCode()))) {
								String[] data = {
										r.getRegionName(),
										tmp.getGmtOutDep(),
										String.valueOf(tmp.getOutDepSum()),
										String.valueOf(DoubleUtil.round(
												tmp.getActualInventorySum(), 2)),
										String.valueOf(DoubleUtil.round(
												tmp.getShippingAmountSum(), 2)) };
								expressAnalysisExportList.add(data);
							}
						}
					}
				}
			}
			goodsBatch.exportExcel(outwt, expressAnalysisExportList);
			outwt.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(outwt);
		}
		return "/express/list_express_analysis_by_pro";
	}

	/**
	 * ��������
	 *
	 * @return String
	 * @throws Exception
	 * @author chenhang 2011/02/16
	 */
	@RequestMapping(value = "/list_cast_weight")
	@AdminAccess({ EnumAdminPermission.A_CASTWEIGHT_USER })
	public String searchCastWeight(
			@RequestParam(value = "goodsName", required = false) String goodsName,
			@RequestParam(value = "expressId", required = false) String expressId,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			Model model) throws Exception {
		// ȡ����Ч��������˾
		expressInfoList = expressManager
				.listExpressByStatus(EnumExpressStatus.ENABLED.getKey());
		model.addAttribute("expressInfoList", expressInfoList);

		Map<String, String> parMap = new HashMap<String, String>();
		parMap.put("goodsName", goodsName);
		parMap.put("expressId", expressId);
		parMap.put("status", status);
		// ���з�ҳ������
		model.addAttribute("parMap", parMap);
		model.addAttribute("query", this.castweightManager.getCastWeightList(
				parMap, currPage, pageSize, true));
		return "/express/list_cast_weight";
	}

	/**
	 * �����޸ļ���������Ʒ
	 *
	 * @return String
	 * @throws Exception
	 * @author chenhang 2011/02/16
	 */
	@RequestMapping(value = "/add_cast_weight")
	@AdminAccess({ EnumAdminPermission.A_CASTWEIGHT_USER })
	public String addCastWeight(
			@RequestParam(value = "goodsSn", required = false) String goodsSn,
			@RequestParam(value = "status", required = false) String[] status,
			@RequestParam(value = "eId", required = false) String[] eIds,
			@RequestParam(value = "cWeight", required = false) String[] cWeight,
			@RequestParam(value = "flag", required = false) String initFlag,
			@RequestParam(value = "optType", required = false) String optType,
			Model model) {
		// ȡ����Ч��������˾
		expressInfoList = expressManager
				.listExpressByStatus(EnumExpressStatus.ENABLED.getKey());
		model.addAttribute("expressInfoList", expressInfoList);

		Map<String, String> parMap = new HashMap<String, String>();
		if (eIds != null) {
			int i = 0;
			for (String eId : eIds) {
				if (StringUtil.isNotBlank(cWeight[i])) {
					parMap.put(eId, cWeight[i]);
				}
				i++;
			}
		}
		parMap.put("goodsSn", goodsSn);
		QueryPage queryPage = null;
		if (StringUtil.isNotBlank(initFlag) && initFlag.equals("true")) {

		} else if (StringUtil.isNotBlank(initFlag) && initFlag.equals("modify")) {
			castweightManager
					.modifyCastweights(parMap, status, expressInfoList);
			queryPage = this.getCastWeightTemp(parMap, goodsSn);
			model.addAttribute("addFlag", "true");
			model.addAttribute("message", "�޸���Ʒ�ɹ���");
			optType = "edit";
		} else if (StringUtil.isNotBlank(initFlag) && initFlag.equals("add")) {
			if (parMap.size() == 1) {
				queryPage = this.getCastWeightTemp(parMap, goodsSn);
				model.addAttribute("addFlag", "true");
			} else {
				castweightManager.addCastweights(parMap, status,
						expressInfoList);
				queryPage = this.getCastWeightTemp(parMap, goodsSn);
				model.addAttribute("message", "������Ʒ�ɹ���");
			}

		}
		model.addAttribute("query", queryPage);
		model.addAttribute("parMap", parMap);
		model.addAttribute("castWeight", castWeight);
		if ("edit".equals(optType)) {
			return "/express/edit_cast_weight";
		}
		return "/express/add_cast_weight";
	}

	private QueryPage getCastWeightTemp(Map<String, String> parMap,
			String goodsSn) {
		castWeight = new Castweight();
		Goods temp = goodsManager.getGoodsByGoodsSn(goodsSn);
		castWeight.setGoodsName(temp.getTitle());
		castWeight.setGoodsWeight(temp.getGoodsWeight());
		return this.castweightManager.getCastWeightList(parMap, 0, pageSize,
				false);
	}

	/**
	 * ��������������Ʒ��ѯ
	 *
	 * @author chenhang 2011/02/16
	 */
	@RequestMapping(value = "/searchCastWeightByCon")
	@AdminAccess({ EnumAdminPermission.A_CASTWEIGHT_USER })
	public String searchCastWeightByCon(
			@RequestParam(value = "goodsSn", required = false) String goodsSn,
			@RequestParam(value = "flag", required = false) String initFlag,
			Model model) {
		Map<String, String> parMap = new HashMap<String, String>();

		// ȡ����Ч��������˾
		parMap.put("goodsSn", goodsSn);
		expressInfoList = expressManager
				.listExpressByStatus(EnumExpressStatus.ENABLED.getKey());
		QueryPage queryPage = this.castweightManager.getCastWeightList(parMap,
				0, pageSize, false);
		if (null != queryPage && queryPage.getItems().size() > 0) {
			castWeight = new Castweight();
			Goods temp = goodsManager.getGoodsByGoodsSn(goodsSn);
			castWeight.setGoodsName(temp.getTitle());
			castWeight.setGoodsWeight(temp.getGoodsWeight());
			if (StringUtil.isNotBlank(initFlag) && initFlag.equals("modify")) {
				model.addAttribute("addFlag", "true");
			}
			model.addAttribute("query", queryPage);
		} else {
			Goods temp = goodsManager.getGoodsByGoodsSn(goodsSn);
			if (null != temp) {
				castWeight = new Castweight();
				castWeight.setGoodsName(temp.getTitle());
				castWeight.setGoodsWeight(temp.getGoodsWeight());
				if (StringUtil.isNotBlank(initFlag)
						&& initFlag.equals("modify")) {

				} else {
					model.addAttribute("addFlag", "true");
				}
				model.addAttribute("message", "����Ʒ������������������");
			} else {
				model.addAttribute("message", "����Ʒ�����ڣ�");
			}
		}
		model.addAttribute("castWeight", castWeight);
		model.addAttribute("parMap", parMap);
		model.addAttribute("expressInfoList", expressInfoList);
		if (StringUtil.isNotBlank(initFlag) && initFlag.equals("modify")) {
			return "/express/edit_cast_weight";
		} else {
			return "/express/add_cast_weight";
		}
	}

	/**
	 * ����������Ϣ
	 */
	@RequestMapping(value = "/list_express")
	@AdminAccess({ EnumAdminPermission.A_EXPRESS_VIEW_USER })
	public String searchExpress(
			@ModelAttribute("expressQuery") ExpressQuery expressQuery,
			@RequestParam(value = "eid", required = false, defaultValue = "") String expressId,
			@RequestParam(value = "optStatus", required = false, defaultValue = "") String optStatus,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			Model model) {
		QueryPage queryPage = null;
		try {
			if (StringUtil.isNotBlank(expressId)
					&& StringUtil.isNumeric(expressId)
					&& StringUtil.isNotBlank(EnumExpressStatus.toMap().get(
							optStatus))) {
				// ��������״̬
				this.expressManager.updateExpressStatusById(optStatus,
						new Long(expressId));
			}
			// ȡ�÷�����������������
			queryPage = this.expressManager.listExpressByCond(expressQuery,
					currPage, pageSize);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (queryPage != null) {
			model.addAttribute("query", queryPage);
		}

		return "/express/list_express";
	}

	/**
	 * ����������Χ
	 *
	 * @return String
	 * @throws Exception
	 * @author chenyan 2009/08/10
	 */
	@RequestMapping(value = "/list_express_dist")
	@AdminAccess({ EnumAdminPermission.A_EXPRESS_DIST_VIEW_USER })
	public String searchExpressDist(
			@ModelAttribute("expressDistQuery") ExpressDistQuery expressDistQuery,
			@RequestParam(value = "eid", required = false, defaultValue = "") String expressId,
			@RequestParam(value = "optStatus", required = false, defaultValue = "") String optStatus,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			Model model) throws Exception {

		// ��ʼ��������Ϣ
		model.addAttribute("allExpressInfoList",
				this.expressManager.getExpresss());
		// ��ʼ��ʡ����Դ
		model.addAttribute("provinceList", regionManager.getRegionByType(2));
		// ��ʼ��������Դ
		model.addAttribute("cityList", regionManager.getRegionByType(3));
		// ��ʼ��������Դ
		model.addAttribute("distincList", regionManager.getRegionByType(4));
		// ��ʼ��֧����ʽ
		model.addAttribute("expressDistPaymentPartMap",
				EnumExpressDistPaymentPart.toMap());
		// ��ʼ��������Ϣ״̬
		model.addAttribute("expressDistStatusMap",
				EnumExpressDistStatus.toMap());

		if (StringUtil.isNotBlank(expressDistQuery.getProvinceStart())) {
			// ��ʼ����ǰ��ʼ��ʡ�ݵĳ�����Դ
			model.addAttribute("cityListInit", regionManager
					.getRegionChilds(expressDistQuery.getProvinceStart()));
		}
		if (StringUtil.isNotBlank(expressDistQuery.getCityStart())) {
			// ��ʼ����ǰ��ʼ�س��е�������Դ
			model.addAttribute("distincListInit", regionManager
					.getRegionChilds(expressDistQuery.getCityStart()));
		}
		if (StringUtil.isNotBlank(expressDistQuery.getProvinceEnd())) {
			// ��ʼ����ǰĿ�ĵ�ʡ�ݵĳ�����Դ
			model.addAttribute("cityEndListInit", regionManager
					.getRegionChilds(expressDistQuery.getProvinceEnd()));
		}
		if (StringUtil.isNotBlank(expressDistQuery.getCityEnd())) {
			// ��ʼ����ǰĿ�ĵس��е�������Դ
			model.addAttribute("distincEndListInit", regionManager
					.getRegionChilds(expressDistQuery.getCityEnd()));
		}

		// ��װ����
		expressDistQuery.setRegionCodeStartList(convertRegionCode(
				expressDistQuery.getProvinceStart(),
				expressDistQuery.getCityStart(),
				expressDistQuery.getDistrictStart()));
		expressDistQuery.setRegionCodeEndList(convertRegionCode(
				expressDistQuery.getProvinceEnd(),
				expressDistQuery.getCityEnd(),
				expressDistQuery.getDistrictEnd()));

		// ȡ�÷�����������������
		QueryPage queryPage = null;
		try {
			// ȡ�÷�����������������
			queryPage = this.expressDistManager.getExpressDistByCond(
					expressDistQuery, currPage, pageSize);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (queryPage != null) {
			model.addAttribute("query", queryPage);
		}

		return "/express/list_express_dist";
	}

	// @SuppressWarnings("unchecked")
	// private Map convertSearchCond() throws Exception{
	// Map mapTmp = new HashMap();
	// if (parMap == null) {
	// return null;
	// }
	//
	// String regionCodeProvinceStart = (String) parMap.get("provinceStart");
	// String regionCodeCityStart = (String) parMap.get("cityStart");
	// String regionCodeDistinctStart = (String) parMap.get("districtStart");
	// String regionCodeProvinceEnd = (String) parMap.get("provinceEnd");
	// String regionCodeCityEnd = (String) parMap.get("cityEnd");
	// String regionCodeDistinctEnd = (String) parMap.get("districtEnd");
	//
	// mapTmp.put("regionCodeStartList",
	// convertRegionCode(regionCodeProvinceStart, regionCodeCityStart,
	// regionCodeDistinctStart));
	// mapTmp.put("regionCodeEndList", convertRegionCode(regionCodeProvinceEnd,
	// regionCodeCityEnd,
	// regionCodeDistinctEnd));
	// mapTmp.put("expressId", parMap.get("expressId"));
	// mapTmp.put("payment", parMap.get("payment"));
	// mapTmp.put("status", parMap.get("status"));
	// mapTmp.put("optTimeStart", parMap.get("optTimeStart"));
	// mapTmp.put("optTimeEnd", parMap.get("optTimeEnd"));
	// return mapTmp;
	// }

	/**
	 * ��ӻ��޸�������˾
	 *
	 * @return String
	 * @throws Exception
	 * @author chenyan 2009/08/07
	 */
	@RequestMapping(value = "/add_express")
	@AdminAccess({ EnumAdminPermission.A_EXPRESS_DIST_SET_USER })
	public String addExpress(
			@ModelAttribute("express") Express express,
			@RequestParam(value = "optType", required = false, defaultValue = "") String optType,
			@RequestParam(value = "eid", required = false, defaultValue = "") String expressIdForDis,
			Model model) throws Exception {
		model.addAttribute("enumTaobaoExpressMap", EnumTaobaoExpress.toMap());
		if (StringUtil.isNotBlank(optType)) {
			// ��ӻ��߸��²��������
			String expressName = express.getExpressName();
			String expressCode = express.getExpressCode();
			String expressDesc = express.getExpressDesc();
			String expressUrl = express.getExpressUrl();
			String status = express.getStatus();
			Long expressId = express.getId();
			
			if(expressName.length() > 20){
				model.addAttribute("errorInfo", "�������Ʋ��ܳ���20����");
				return "/express/add_express";
			}
			// ������������ȡ�����ݿ�������
			List<Express> expressUniqueList = this.expressManager
					.getExpressByName(expressName);
			List<Express> expressUniqueList2 = null;
			if (StringUtil.isBlank(expressName)) {
				model.addAttribute("errorInfo", "��������������");
				return "/express/add_express";
			}
			if (StringUtil.isBlank(expressCode)) {
				model.addAttribute("errorInfo", "��������������");
				return "/express/add_express";
			}
			if (optType.equals("add")) {
				// �ж����������Ƿ��Ѵ���
				if (expressUniqueList != null && expressUniqueList.size() > 0) {
					model.addAttribute("errorInfo", "�����������Ѵ��ڡ�");
					return "/express/add_express";
				} else {
					expressUniqueList2 = this.expressManager
							.getExpressByCode(expressCode.trim());
					if (expressUniqueList2 != null
							&& expressUniqueList2.size() > 0) {
						model.addAttribute("errorInfo", "�����������Ѵ��ڡ�");
						return "/express/add_express";
					}
				}
			} else if (optType.equals("update")) {
				if (expressId > 0) {
					Express expressForDis = this.expressManager
							.getExpress(new Long(expressId));
					if (expressForDis == null
							|| !expressName.equals(expressForDis
									.getExpressName())) {
						// �ж����������Ƿ��Ѵ���
						if (expressUniqueList != null
								&& expressUniqueList.size() > 0) {
							model.addAttribute("errorInfo", "�����������Ѵ��ڡ�");
							return "/express/add_express";
						}
					}
					if (expressForDis == null
							|| !(expressCode.trim()).equals(expressForDis
									.getExpressCode())) {
						// �ж����������Ƿ��Ѵ���
						expressUniqueList2 = this.expressManager
								.getExpressByCode(expressCode.trim());
						if (expressUniqueList2 != null
								&& expressUniqueList2.size() > 0) {
							model.addAttribute("errorInfo", "�����������Ѵ��ڡ�");
							return "/express/add_express";
						}
					}
				}
			}
			Express expressForOpt = new Express();
			expressForOpt.setGmtCreate(new Date());
			expressForOpt.setGmtModify(new Date());
			expressForOpt.setExpressName(expressName.trim());
			expressForOpt.setExpressCode(expressCode.trim());
			expressForOpt.setExpressDesc(expressDesc);
			expressForOpt.setExpressUrl(expressUrl);
			expressForOpt.setStatus(status);
			expressForOpt.setInterfaceExpressCode(express
					.getInterfaceExpressCode());
			if (optType.equals("add")) {
				// ��Ӳ���
				this.expressManager.addExpress(expressForOpt);
				model.addAttribute("successInfo", "����(" + expressName + ")������");
				// ��ʼ��ҳ������
			} else if (optType.equals("update")) {
				if (expressId > 0) {
					expressForOpt.setId(new Long(expressId));
					// ���²���
					this.expressManager.editExpress(expressForOpt);
					model.addAttribute("successInfo", "����(" + expressName
							+ ")�������");
				} else {
					model.addAttribute("errorInfo", "���ݲ������������µ�¼");
				}
			}
		} else if (StringUtil.isNotBlank(expressIdForDis)) {
			if (StringUtil.isNumeric(expressIdForDis)) {
				model.addAttribute("express", this.expressManager
						.getExpress(new Long(expressIdForDis)));
			} else {
				// �������ݴ���.
				return "/express/add_express";
			}
		}
		return "/express/add_express";
	}

	/**
	 * ������˾ѡ��ҳ��
	 *
	 * @return String
	 * @author chenyan 2009/08/27
	 */
	@RequestMapping(value = "/select_express")
	@AdminAccess({ EnumAdminPermission.A_EXPRESS_DIST_SET_USER })
	public String selExpress(
			@RequestParam(value = "selExpressId", required = false, defaultValue = "") String selExpressId,
			Model model) {
		// ȡ����Ч��������˾
		List<Express> expressInfoList = expressManager
				.listExpressByStatus(EnumExpressStatus.ENABLED.getKey());

		model.addAttribute("expressInfoList", expressInfoList);

		// ����ID
		if (StringUtil.isNotBlank(selExpressId)) {
			return "redirect:/express/add_express_dist_page.html?eid="
					+ selExpressId;
		}
		return "/express/select_express";
	}

	/**
	 * ����޸�����������Ϣҳ����ת
	 *
	 * @return String
	 * @throws Exception
	 * @author chenyan 2009/08/07
	 */
	@RequestMapping(value = "/add_express_dist_page")
	@AdminAccess({ EnumAdminPermission.A_EXPRESS_DIST_SET_USER })
	public String addExpressDistPage(
			@ModelAttribute("expressDist") ExpressDist expressDist,
			@RequestParam(value = "optType", required = false, defaultValue = "") String optType,
			@RequestParam(value = "eid", required = false) Long expressId,
			@RequestParam(value = "edid", required = false) Long expressDistId,
			Model model) throws Exception {
		model.addAttribute("eid", expressId);
		model.addAttribute("edid", expressDistId);

		if (!"batch".equals(optType)) {
			// ------------- ��ÿ��������Ϣ -------------
			// ������������
			Express expressInfo = expressManager.getExpress(expressId);
			if (expressInfo == null) {
				// URL�������ݴ���
				model.addAttribute("errorInfo", "������˾��ϢΪ�գ�");
				return "/express/add_express_dist";
			}
			expressDist.setExpressName(expressInfo.getExpressName());
		} else {
			// ȡ����Ч��������˾
			List<Express> expressInfoList = expressManager
					.listExpressByStatus(EnumExpressStatus.ENABLED.getKey());
			model.addAttribute("expressInfoList", expressInfoList);
		}

		if (StringUtil.isBlank(optType)) {
			if (expressDistId != null) {
				optType = "update";
				expressDist.setId(expressDistId);
				// ��ʼ��������Χ��Ϣ
				expressDist.setExpressId(expressId);
				initExpressDist(expressDist);
			} else {
				optType = "add";
				// ��ʼ������Ϊ�㽭����
				expressDist.setRegionCodeDistinctStart("330782");
				expressDist.setRegionCodeCityStart("330100");
				expressDist.setRegionCodeProvinceStart("330000");
				expressDist.setWeightFirst(1D);
				expressDist.setWeightNext(0.5);
			}
		} else if ("batch".equals(optType)) {
			expressDist.setRegionCodeDistinctStart("330782");
			expressDist.setRegionCodeCityStart("330100");
			expressDist.setRegionCodeProvinceStart("330000");
			expressDist.setWeightFirst(1D);
			expressDist.setWeightNext(0.5);
		}
		model.addAttribute("optType", optType);

		model.addAttribute("expressDist", expressDist);

		if (StringUtil.isNotBlank(expressDist.getRegionCodeProvinceStart())) {
			// ��ʼ����ǰ��ʼ��ʡ�ݵĳ�����Դ
			model.addAttribute("cityListInit", regionManager
					.getRegionChilds(expressDist.getRegionCodeProvinceStart()));
		}
		if (StringUtil.isNotBlank(expressDist.getRegionCodeCityStart())) {
			// ��ʼ����ǰ��ʼ�س��е�������Դ
			model.addAttribute("distincListInit", regionManager
					.getRegionChilds(expressDist.getRegionCodeCityStart()));
		}
		if (StringUtil.isNotBlank(expressDist.getRegionCodeProvinceEnd())) {
			// ��ʼ����ǰĿ�ĵ�ʡ�ݵĳ�����Դ
			model.addAttribute("cityEndListInit", regionManager
					.getRegionChilds(expressDist.getRegionCodeProvinceEnd()));
		}
		if (StringUtil.isNotBlank(expressDist.getRegionCodeCityEnd())) {
			// ��ʼ����ǰĿ�ĵس��е�������Դ
			model.addAttribute("distincEndListInit", regionManager
					.getRegionChilds(expressDist.getRegionCodeCityEnd()));
		}

		// ��ʼ��ʡ����Դ
		model.addAttribute("provinceList", regionManager.getRegionByType(2));
		// ��ʼ��������Դ
		model.addAttribute("cityList", regionManager.getRegionByType(3));
		// ��ʼ��������Դ
		model.addAttribute("distincList", regionManager.getRegionByType(4));

		model.addAttribute("expressDistPaymentPartMap",
				EnumExpressDistPaymentPart.toMap());
		model.addAttribute("expressDistStatusMap",
				EnumExpressDistStatus.toMap());
		model.addAttribute("expressDistPaymentAll",
				EnumExpressDistPaymentPart.ALL);

		return "/express/add_express_dist";
	}

	/**
	 * ����޸�����������Ϣ
	 *
	 * @return String
	 * @throws Exception
	 * @author chenyan 2009/08/07
	 */
	@RequestMapping(value = "/add_express_dist")
	@AdminAccess({ EnumAdminPermission.A_EXPRESS_DIST_SET_USER })
	public String addExpressDist(
			@ModelAttribute("expressDist") ExpressDist expressDist,
			@RequestParam(value = "selExpressId", required = false, defaultValue = "") String selExpressId,
			@RequestParam(value = "optType", required = false, defaultValue = "") String optType,
			Model model, HttpServletRequest req) throws Exception {

		if (StringUtil.isNotBlank(optType)) {
			if ("batch".equals(optType)) {
				expressDist.setExpressId(Long.valueOf(selExpressId));
			}
			List<ExpressDist> expressDistForOptList = assembleDateFromPage(expressDist);

			if (optType.equals("add")) {

				// ���ж��Ƿ��������Ѿ������
				for (ExpressDist expressDistAddInfo : expressDistForOptList) {
					expressDistAddInfo.setExpressId(expressDist.getExpressId());
					// Ϊ��ֹ�ظ���ӣ����ж��Ƿ�������
					int exzistCount = this.expressDistManager
							.getExpressDistByRegion(expressDist.getExpressId(),
									expressDistAddInfo.getRegionCodeStart(),
									expressDistAddInfo.getRegionCodeEnd(),
									expressDistAddInfo.getPayment());
					if (exzistCount > 0) {
						model.addAttribute("errorInfo", "����ӵ������Ѿ����ڣ���������޸ļ�¼��");
						return addExpressDistPage(expressDist, optType,
								expressDist.getExpressId(),
								expressDist.getId(), model);
					}
				}
				for (ExpressDist expressDistAddInfo : expressDistForOptList) {
					expressDistAddInfo.setExpressId(expressDist.getExpressId());
					// ������Ӳ�����
					if (StringUtil.isNotBlank(expressDistAddInfo.getPayment())
							&& expressDistAddInfo.getPayment().equals(
									EnumExpressDistPaymentPart.ALL.getKey())) {
						// ֧����ʽΪall��ʱ������ɾ������Ӷ�����¼.
						this.expressDistManager.removeExpressDistByRegion(
								expressDist.getExpressId(),
								expressDistAddInfo.getRegionCodeStart(),
								expressDistAddInfo.getRegionCodeEnd());

						expressDistAddInfo
								.setPayment(EnumExpressDistPaymentPart.GOODS_FIRST
										.getKey());
						this.expressDistManager
								.addExpressDist(expressDistAddInfo);

						expressDistAddInfo
								.setPayment(EnumExpressDistPaymentPart.PAYMENT_FIRST
										.getKey());
						this.expressDistManager
								.addExpressDist(expressDistAddInfo);
					} else {
						this.expressDistManager
								.addExpressDist(expressDistAddInfo);
					}
				}
				model.addAttribute("successInfo", "������ӳɹ���");
				// ������ʾ������Ӱ�ť
				isAddOpt = Boolean.TRUE;
				model.addAttribute("isAddOpt", isAddOpt);
			} else if (optType.equals("update") || optType.equals("batch")) {
				for (ExpressDist expressDistUpdateInfo : expressDistForOptList) {
					int existUpdateCount = this.expressDistManager
							.getCountByFourForUpdate(
									expressDistUpdateInfo.getRegionCodeStart(),
									expressDistUpdateInfo.getRegionCodeEnd(),
									expressDist.getExpressId(),
									expressDist.getId(),
									expressDistUpdateInfo.getPayment());
					if (existUpdateCount > 0) {
						model.addAttribute("errorInfo",
								"���޸ĵ�������ʽ�����Ѿ����ڣ���������޸ļ�¼��");
						return addExpressDistPage(expressDist, optType,
								expressDist.getExpressId(),
								expressDist.getId(), model);
					}
				}

				// ���ݸ��²���
				int count = 0;
				for (ExpressDist expressDistUpdateInfo : expressDistForOptList) {
					expressDistUpdateInfo.setExpressId(expressDist
							.getExpressId());
					expressDistUpdateInfo.setId(expressDist.getId());

					if (optType.equals("batch")) {
						int result = 0;
						if ("all".equals(expressDistUpdateInfo.getPayment())) {
							expressDistUpdateInfo
									.setPayment(EnumExpressDistPaymentPart.GOODS_FIRST
											.getKey());
							result = this.expressDistManager
									.editExpressDistByFourCond(expressDistUpdateInfo);
							if (result > 0) {
								count++;
								result = 0;
							}
							expressDistUpdateInfo
									.setPayment(EnumExpressDistPaymentPart.PAYMENT_FIRST
											.getKey());
							result = this.expressDistManager
									.editExpressDistByFourCond(expressDistUpdateInfo);
							if (result > 0) {
								count++;
								result = 0;
							}
						} else {
							result = this.expressDistManager
									.editExpressDistByFourCond(expressDistUpdateInfo);
							if (result > 0) {
								count++;
								result = 0;
							}
						}
					} else {
						if (StringUtil.isNotBlank(expressDistUpdateInfo
								.getPayment())
								&& expressDistUpdateInfo.getPayment()
										.equals(EnumExpressDistPaymentPart.ALL
												.getKey())) {
							// ֧����ʽΪall��ʱ������ɾ������Ӷ�����¼.
							this.expressDistManager.removeExpressDistByRegion(
									expressDist.getExpressId(),
									expressDistUpdateInfo.getRegionCodeStart(),
									expressDistUpdateInfo.getRegionCodeEnd());

							expressDistUpdateInfo
									.setPayment(EnumExpressDistPaymentPart.GOODS_FIRST
											.getKey());
							this.expressDistManager
									.addExpressDist(expressDistUpdateInfo);

							expressDistUpdateInfo
									.setPayment(EnumExpressDistPaymentPart.PAYMENT_FIRST
											.getKey());
							this.expressDistManager
									.addExpressDist(expressDistUpdateInfo);
						} else {
							this.expressDistManager
									.editExpressDist(expressDistUpdateInfo);
						}
					}

				}
				if (optType.equals("batch")) {
					model.addAttribute("successInfo", "�����������³ɹ�,��������" + count
							+ "�����ݣ�");
				} else {
					model.addAttribute("successInfo", "���ݸ��³ɹ���");
				}

			} else {
				model.addAttribute("errorInfo", "�������Ͳ���ȷ����ˢ�´�ҳ�������²�����");
			}
		}
		return addExpressDistPage(expressDist, optType,
				expressDist.getExpressId(), expressDist.getId(), model);
	}

	/**
	 * ��ʼ��������Χҳ������(������ת��)
	 *
	 * @param expressDistId
	 *            Long
	 * @author chenyan 2009/08/07
	 */
	private void initExpressDist(ExpressDist expressDist) throws Exception {
		// ���ݲ�������������ʾ����Ϣ
		ExpressDist expressDistOri = this.expressDistManager
				.getExpressDistById(expressDist.getId());
		if (expressDistOri != null) {

			// ��ʼ������code
			String regionCodeStart = expressDistOri.getRegionCodeStart();
			Region regionCodeDistinctStart = null;
			Region regionCodeCityStart = null;
			Region regionCodeProvinceStart = null;
			regionCodeDistinctStart = regionManager
					.getRegionByCode(regionCodeStart);
			if (regionCodeDistinctStart != null) {
				regionCodeCityStart = regionManager
						.getRegionByCode(regionCodeDistinctStart
								.getParentCode());
				if (regionCodeCityStart != null) {
					regionCodeProvinceStart = regionManager
							.getRegionByCode(regionCodeCityStart
									.getParentCode());
				}
			}

			if (regionCodeProvinceStart == null) {
				if (regionCodeCityStart == null) {
					if (regionCodeDistinctStart != null) {
						// 1�������
						expressDist
								.setRegionCodeProvinceStart(regionCodeDistinctStart
										.getCode());
					}
				} else {
					// 2�������
					expressDist.setRegionCodeCityStart(regionCodeDistinctStart
							.getCode());
					expressDist.setRegionCodeProvinceStart(regionCodeCityStart
							.getCode());
				}
			} else {
				// 3�������
				expressDist.setRegionCodeDistinctStart(regionCodeDistinctStart
						.getCode());
				expressDist.setRegionCodeCityStart(regionCodeCityStart
						.getCode());
				expressDist.setRegionCodeProvinceStart(regionCodeProvinceStart
						.getCode());
			}

			// Ŀ�ĵ�����code
			String regionCodeEnd = expressDistOri.getRegionCodeEnd();

			Region regionCodeDistinctEnd = null;
			Region regionCodeCityEnd = null;
			Region regionCodeProvinceEnd = null;
			regionCodeDistinctEnd = regionManager
					.getRegionByCode(regionCodeEnd);
			if (regionCodeDistinctEnd != null) {
				regionCodeCityEnd = regionManager
						.getRegionByCode(regionCodeDistinctEnd.getParentCode());
				if (regionCodeCityEnd != null) {
					regionCodeProvinceEnd = regionManager
							.getRegionByCode(regionCodeCityEnd.getParentCode());
				}
			}
			if (regionCodeProvinceEnd == null) {
				if (regionCodeCityEnd == null) {
					if (regionCodeDistinctEnd != null) {
						// 1�������
						expressDist
								.setRegionCodeProvinceEnd(regionCodeDistinctEnd
										.getCode());
					}
				} else {
					// 2�������
					expressDist.setRegionCodeCityEnd(regionCodeDistinctEnd
							.getCode());
					expressDist.setRegionCodeProvinceEnd(regionCodeCityEnd
							.getCode());
				}
			} else {
				// 3�������
				expressDist.setRegionCodeDistinctEnd(regionCodeDistinctEnd
						.getCode());
				expressDist.setRegionCodeCityEnd(regionCodeCityEnd.getCode());
				expressDist.setRegionCodeProvinceEnd(regionCodeProvinceEnd
						.getCode());
			}

			// ����ʱ��
			String spendTime = expressDistOri.getSpendTime();
			if (StringUtil.isNotBlank(spendTime)) {
				String[] spendTimeArray = spendTime.split("-");
				if (spendTimeArray != null) {
					if (spendTimeArray.length == 2) {
						expressDist.setSpendTimeFrom(spendTimeArray[0]);
						expressDist.setSpendTimeTo(spendTimeArray[1]);
					} else {
						expressDist.setSpendTimeFrom(spendTimeArray[0]);
					}
				}
			}

			expressDist.setWeightFirst(expressDistOri.getWeightFirst());
			expressDist.setWeightFirstMoney(expressDistOri
					.getWeightFirstMoney());
			expressDist.setWeightNext(expressDistOri.getWeightNext());
			expressDist.setWeightNextMoney(expressDistOri.getWeightNextMoney());

			expressDist.setPayment(expressDistOri.getPayment());
			expressDist.setMemo(expressDistOri.getMemo());
			expressDist.setStatus(expressDistOri.getStatus());
		}
	}

	/**
	 * ��װҳ���е��������ڸ������ݿ�
	 *
	 * @return ExpressDist
	 */
	private List<ExpressDist> assembleDateFromPage(ExpressDist expressDist)
			throws Exception {
		List<ExpressDist> expressDistInfoList = new ArrayList<ExpressDist>();
		if (expressDist != null) {
			ExpressDist expressDistInfo = new ExpressDist();
			expressDistInfo.setGmtCreate(new Date());
			expressDistInfo.setGmtModify(new Date());
			// �����ͻ�ʱ��
			Long spendTimeFrom = StringUtil.isBlank(expressDist
					.getSpendTimeFrom()) ? null : Long.parseLong(expressDist
					.getSpendTimeFrom());
			Long spendTimeTo = StringUtil.isBlank(expressDist.getSpendTimeTo()) ? null
					: Long.parseLong(expressDist.getSpendTimeTo());
			String spendTime = "";
			if (spendTimeFrom != null && spendTimeTo != null
					&& spendTimeFrom > 0 && spendTimeTo > 0) {
				spendTime = spendTimeFrom + "-" + spendTimeTo;
			} else if (spendTimeFrom != null && spendTimeFrom > 0) {
				spendTime = spendTimeFrom + "";
			} else if (spendTimeTo != null && spendTimeTo > 0) {
				spendTime = spendTimeTo + "";
			}
			expressDistInfo.setSpendTime(spendTime);

			expressDistInfo.setWeightFirst(expressDist.getWeightFirst());
			expressDistInfo.setWeightFirstMoney(expressDist
					.getWeightFirstMoney() == null ? 0 : expressDist
					.getWeightFirstMoney());
			expressDistInfo.setWeightNext(expressDist.getWeightNext());
			expressDistInfo
					.setWeightNextMoney(expressDist.getWeightNextMoney() == null ? 0
							: expressDist.getWeightNextMoney());

			expressDistInfo.setPayment(expressDist.getPayment());
			expressDistInfo.setMemo(expressDist.getMemo());
			expressDistInfo.setStatus(expressDist.getStatus());

			String regionCodeProvinceStart = expressDist
					.getRegionCodeProvinceStart();
			String regionCodeCityStart = expressDist.getRegionCodeCityStart();
			String regionCodeDistinctStart = expressDist
					.getRegionCodeDistinctStart();
			// ��װ��ʼ��
			if (StringUtil.isNotBlank(regionCodeDistinctStart)) {
				expressDistInfo.setRegionCodeStart(regionCodeDistinctStart);
				convertRegionCodeEnd(expressDist, expressDistInfo,
						expressDistInfoList);
			} else if (StringUtil.isNotBlank(regionCodeCityStart)) {
				List<String> tmpStartList = regionManager
						.listRegionCodeByParentCode(regionCodeCityStart);
				if (tmpStartList != null && tmpStartList.size() > 0) {
					for (String codeStart : tmpStartList) {
						ExpressDist expressDistNew = expressDistInfo.clone();
						expressDistNew.setRegionCodeStart(codeStart);
						convertRegionCodeEnd(expressDist, expressDistNew,
								expressDistInfoList);
					}
				} else {
					expressDistInfo.setRegionCodeStart(regionCodeCityStart);
					convertRegionCodeEnd(expressDist, expressDistInfo,
							expressDistInfoList);
				}
			} else if (StringUtil.isNotBlank(regionCodeProvinceStart)) {
				List<String> tmpStartTwoList = regionManager
						.listTwoRegionCodeByParentCode(regionCodeProvinceStart);
				if (tmpStartTwoList != null && tmpStartTwoList.size() > 0) {
					for (String codeStartTwo : tmpStartTwoList) {
						ExpressDist expressDistNew = expressDistInfo.clone();
						expressDistNew.setRegionCodeStart(codeStartTwo);
						convertRegionCodeEnd(expressDist, expressDistNew,
								expressDistInfoList);
					}
				} else {
					expressDistInfo.setRegionCodeStart(regionCodeProvinceStart);
					convertRegionCodeEnd(expressDist, expressDistInfo,
							expressDistInfoList);
				}
			}
		}
		return expressDistInfoList;
	}

	/**
	 * ��װ���������ڸ�������
	 *
	 * @param province
	 *            String
	 * @param city
	 *            String
	 * @param district
	 *            String
	 * @return List
	 * @throws Exception
	 * @author chenyan 2009/08/12
	 */
	private List<String> convertRegionCode(String province, String city,
			String district) throws Exception {
		List<String> regionCodeList = new ArrayList<String>();

		if (StringUtil.isNotBlank(district)) {
			// ������ֵ�������ֱ��ʹ��
			regionCodeList.add(district);
		} else if (StringUtil.isNotBlank(city)) {
			// ����û��ֵ��������ֵ��������г�����
			List<String> tmpList = this.regionManager
					.listRegionCodeByParentCode(city);
			if (tmpList != null && tmpList.size() > 0) {
				for (String code : tmpList) {
					regionCodeList.add(code);
				}
			} else {
				regionCodeList.add(city);
			}
		} else if (StringUtil.isNotBlank(province)) {
			// ���������ж�û��ֵ��ʡ��ֵ�������ѭ�������к͵���
			List<String> tmpTwoList = this.regionManager
					.listTwoRegionCodeByParentCode(province);
			if (tmpTwoList != null && tmpTwoList.size() > 0) {
				for (String codeTwo : tmpTwoList) {
					regionCodeList.add(codeTwo);
				}
			} else {
				regionCodeList.add(province);
			}
		}
		return regionCodeList;
	}

	/**
	 * ��װĿ�ĵأ����ڸ������ݡ�(ѭ��ʱ��ʹ��clone���޸�����)
	 *
	 * @param expressDistInfo
	 *            ExpressDist
	 * @param expressDistInfoList
	 *            List
	 * @throws Exception
	 * @author chenyan 2009/08/10
	 */
	private void convertRegionCodeEnd(ExpressDist expressDist,
			ExpressDist expressDistInfo, List<ExpressDist> expressDistInfoList)
			throws Exception {
		String regionCodeProvinceEnd = expressDist.getRegionCodeProvinceEnd();
		String regionCodeCityEnd = expressDist.getRegionCodeCityEnd();
		String regionCodeDistinctEnd = expressDist.getRegionCodeDistinctEnd();

		if (StringUtil.isNotBlank(regionCodeDistinctEnd)) {
			// ������ֵ�������ֱ��ʹ��
			expressDistInfo.setRegionCodeEnd(regionCodeDistinctEnd);
			expressDistInfoList.add(expressDistInfo);
		} else if (StringUtil.isNotBlank(regionCodeCityEnd)) {
			// ����û��ֵ��������ֵ��������г�����
			List<String> tmpList = this.regionManager
					.listRegionCodeByParentCode(regionCodeCityEnd);
			if (tmpList != null && tmpList.size() > 0) {
				for (String code : tmpList) {
					ExpressDist expressDistNew = expressDistInfo.clone();
					expressDistNew.setRegionCodeEnd(code);
					expressDistInfoList.add(expressDistNew);
				}
			} else {
				expressDistInfo.setRegionCodeEnd(regionCodeCityEnd);
				expressDistInfoList.add(expressDistInfo);
			}
		} else if (StringUtil.isNotBlank(regionCodeProvinceEnd)) {
			// ���������ж�û��ֵ��ʡ��ֵ�������ѭ�������к͵���
			List<String> tmpTwoList = this.regionManager
					.listTwoRegionCodeByParentCode(regionCodeProvinceEnd);
			if (tmpTwoList != null && tmpTwoList.size() > 0) {
				for (String codeTwo : tmpTwoList) {
					ExpressDist expressDistNew = expressDistInfo.clone();
					expressDistNew.setRegionCodeEnd(codeTwo);
					expressDistInfoList.add(expressDistNew);
				}
			} else {
				expressDistInfo.setRegionCodeEnd(regionCodeProvinceEnd);
				expressDistInfoList.add(expressDistInfo);
			}
		}
	}

	/**
	 * �����޸�������Χ
	 *
	 * @return String
	 * @author chenyan 2009/11/09
	 * @throws Exception
	 */
	// @RequestMapping(value = "/batch_edit_express_dist")
	// public String batchModifyExpressDist(
	// @ModelAttribute("expressDist") ExpressDist expressDist,
	// @RequestParam(value = "optType", required = false, defaultValue = "")
	// String optType,
	// @RequestParam(value = "eid", required = false, defaultValue = "") String
	// expressIdTmp,
	// @RequestParam(value = "edid", required = false, defaultValue = "") String
	// expressDistIdTmp,
	// Model model) throws Exception{
	// if (StringUtil.isBlank(optType)) {
	// //��ʼ������Ϊ�㽭����
	// addDistMap.put("regionCodeDistinctStart", "330782");
	// addDistMap.put("regionCodeCityStart", "330100");
	// addDistMap.put("regionCodeProvinceStart", "330000");
	// addDistMap.put("weightFirst", "1");
	// addDistMap.put("weightNext", "0.5");
	// }
	//
	// if (StringUtil.isNotBlank(addDistMap.get("regionCodeProvinceStart"))) {
	// //��ʼ����ǰ��ʼ��ʡ�ݵĳ�����Դ
	// cityListInit =
	// regionManager.getRegionChilds(addDistMap.get("regionCodeProvinceStart"));
	// }
	// if (StringUtil.isNotBlank(addDistMap.get("regionCodeCityStart"))) {
	// //��ʼ����ǰ��ʼ�س��е�������Դ
	// distincListInit =
	// regionManager.getRegionChilds(addDistMap.get("regionCodeCityStart"));
	// }
	// if (StringUtil.isNotBlank(addDistMap.get("regionCodeProvinceEnd"))) {
	// //��ʼ����ǰĿ�ĵ�ʡ�ݵĳ�����Դ
	// cityEndListInit =
	// regionManager.getRegionChilds(addDistMap.get("regionCodeProvinceEnd"));
	// }
	// if (StringUtil.isNotBlank(addDistMap.get("regionCodeCityEnd"))) {
	// //��ʼ����ǰĿ�ĵس��е�������Դ
	// distincEndListInit =
	// regionManager.getRegionChilds(addDistMap.get("regionCodeCityEnd"));
	// }
	// //��ʼ��ʡ����Դ
	// provinceList = regionManager.getRegionByType(2);
	// //��ʼ��������Դ
	// cityList = regionManager.getRegionByType(3);
	// //��ʼ��������Դ
	// distincList = regionManager.getRegionByType(4);
	//
	// //ȡ����Ч��������˾
	// expressInfoList =
	// expressManager.listExpressByStatus(EnumExpressStatus.ENABLED.getKey());
	//
	// if (StringUtil.isNotBlank(optType)) {
	// List<ExpressDist> expressDistForOptList = assembleDateFromPage();
	//
	// if(optType.equals("update")) {
	// int iCount = 0;
	// //���ݸ��²���
	// for (ExpressDist expressDistUpdateInfo : expressDistForOptList) {
	// expressDistUpdateInfo.setExpressId(Long.parseLong(addDistMap.get("selExpressId")));
	//
	// if(StringUtil.isNotBlank(expressDistUpdateInfo.getPayment())
	// &&
	// expressDistUpdateInfo.getPayment().equals(EnumExpressDistPaymentPart.ALL.getKey())){
	// expressDistUpdateInfo.setPayment(EnumExpressDistPaymentPart.GOODS_FIRST.getKey());
	// int iGoodsFirst =
	// this.expressDistManager.editExpressDistByFourCond(expressDistUpdateInfo);
	//
	// expressDistUpdateInfo.setPayment(EnumExpressDistPaymentPart.PAYMENT_FIRST.getKey());
	// int iPaymentFirst =
	// this.expressDistManager.editExpressDistByFourCond(expressDistUpdateInfo);
	//
	// iCount = iCount + iGoodsFirst + iPaymentFirst;
	// } else {
	// int iRandom =
	// this.expressDistManager.editExpressDistByFourCond(expressDistUpdateInfo);
	// iCount = iCount + iRandom;
	// }
	// }
	// successInfo = "�����������³ɹ�,��������" + iCount + "�����ݡ�";
	// } else {
	// errorInfo = "�������Ͳ���ȷ����ˢ�´�ҳ�������²�����";
	// }
	// }
	// return "/express/batch_edit_express_dist";
	// }

	/**
	 * �˷�ͳ��
	 *
	 * @author zhangwy
	 * @return
	 */
	@RequestMapping(value = "/freightStatistics")
	public String freightStatistics(
			@ModelAttribute("freightStatisticsQuery") FreightStatisticsQuery freightStatisticsQuery,
			@RequestParam(value = "isFirst", required = false, defaultValue = "") String isFirst,
			AdminAgent adminAgent,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			Model model) throws Exception {
		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
				.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));

		if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
			return "/express/freightStatistics";
		}

		List<Express> expressInfoList = expressManager.getExpresss();
		long freightCount = 0;
		double freightAmount = 0;

		if ("true".equals(isFirst)) {
			Date now = new Date();
			Timestamp nowTs = new Timestamp(now.getTime());
			Date before30 = DateUtil.getDate(now, -30);
			Timestamp beforeTs = new Timestamp(before30.getTime());
			if (StringUtils.isBlank(freightStatisticsQuery
					.getDeliveryTimeStart())) {
				freightStatisticsQuery.setDeliveryTimeStart(DateUtil
						.getTimestampToString(beforeTs));
			}
			if (StringUtils
					.isBlank(freightStatisticsQuery.getDeliveryTimeEnd())) {
				freightStatisticsQuery.setDeliveryTimeEnd(DateUtil
						.getTimestampToString(nowTs));
			}
		}

		freightStatisticsQuery.setDepfirstIds(this
				.getDepfirstIdForQuery(adminAgent));
		QueryPage query = expressManager.getFreightListsByParameterMap(
				freightStatisticsQuery, currPage, pageSize);
		freightCount = query.getTotalItem();
		freightAmount = expressManager
				.getFreightAmountByParameterMap(freightStatisticsQuery);

		model.addAttribute("query", query);
		model.addAttribute("queryObject", freightStatisticsQuery);
		model.addAttribute("expressInfoList", expressInfoList);
		model.addAttribute("depositoryFirstList", depositoryFirstList);
		model.addAttribute("freightCount", freightCount);
		model.addAttribute("freightAmount", freightAmount);
		return "/express/freightStatistics";
	}

	/**
	 * �˷�ͳ�Ƶ���
	 *
	 * @author zhangwy
	 * @return
	 */
	@RequestMapping(value = "/exportFreightStatistics")
	public void exportFreightStatistics(
			@ModelAttribute("freightStatisticsQuery") FreightStatisticsQuery freightStatisticsQuery,
			AdminAgent adminAgent, HttpServletResponse res) {
		OutputStream outwt = null;
		try {
			Date da = new Date();
			outwt = res.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			res.setContentType("application/octet-stream;charset=utf-8");
			List<String[]> freightExportList = new ArrayList<String[]>();
			res.setHeader("Content-disposition",
					"attachment; filename=Freight_Statistics_" + date + ".xls");
			List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
					.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
			if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
				return;
			}

			freightStatisticsQuery.setDepfirstIds(this
					.getDepfirstIdForQuery(adminAgent));
			QueryPage queryPage = expressManager.getFreightListsByParameterMap(
					freightStatisticsQuery, 1, Integer.MAX_VALUE);
			List<Express> expressFreightList = (List<Express>) queryPage
					.getItems();
			String[] title = { "�û���", "������", "�˷ѽ��(��)", "�����ֿ�", "������˾", "����ʱ��",
					"ʵ���˷�" };
			freightExportList.add(title);
			SimpleDateFormat usedf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (expressFreightList != null) {
				for (Express tmp : expressFreightList) {
					String[] data = {
							tmp.getAccount(),
							tmp.getTid(),
							String.valueOf(DoubleUtil.round(
									tmp.getShippingAmount(), 2)),
							tmp.getDepFirstName(),
							tmp.getExpressName(),
							usedf.format(tmp.getGmtOutDep()),
							String.valueOf(tmp.getActualInventory() == null ? ""
									: tmp.getActualInventory()) };
					freightExportList.add(data);
				}
			}
			goodsBatch.exportExcel(outwt, freightExportList);
			outwt.flush();
		} catch (Exception e) {
			// request.setAttribute("errorMessage", "����ʧ�ܣ�");
			// log.error(e);
		} finally {
			close(outwt);
		}
	}

	/**
	 * ��������������Χ״̬
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bath_update_status")
	@AdminAccess({ EnumAdminPermission.A_EXPRESS_DIST_SET_USER })
	public String bathUpdateStatus(
			@RequestParam(value = "checkbox", required = false, defaultValue = "") String[] ids,
			@RequestParam(value = "optStatus", required = false, defaultValue = "") String status,
			@ModelAttribute("expressDistQuery") ExpressDistQuery expressDistQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			Model model) throws Exception {
		// �����µ�״̬
		if (ids.length == 0
				|| !("enabled".equalsIgnoreCase(status) || "disabled"
						.equalsIgnoreCase(status))) {
			model.addAttribute("message", "Ҫ���µļ�¼ID����Ϊ�գ�");
			return "/error";
		}
		List<Long> expressDistIds = new ArrayList<Long>();
		for (int i = 0; ids.length > i; i++) {
			Long id = Long.parseLong(ids[i]);
			expressDistIds.add(id);
		}

		// �����������²���
		expressDistManager.bathUpdateStatus(expressDistIds, status);
		// int currentPage = 1;// �����޸ĳɹ���ʼ����ת����һҳ
		return this.searchExpressDist(expressDistQuery, null, null, currPage,
				model);
	}

	public static void close(OutputStream out) {

		try {
			if (out != null) {
				out.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}

	/**
	 * ����motorTransInfo
	 *
	 * @return "success" if no exceptions thrown
	 * @throws Exception
	 */
	@RequestMapping(value = "/add_motor_trans")
	@AdminAccess({ EnumAdminPermission.A_EXPRESS_MOTOR_TRANS_INFO })
	public String addMotorTransInfo(
			@ModelAttribute("motorTransInfo") MotorTransInfo motorTransInfo,
			@RequestParam(value = "optType", required = false, defaultValue = "") String optType,
			@RequestParam(value = "gmtExpressDate", required = false, defaultValue = "") String gmtExpressDate,
			Model model) throws Exception {

		if ("add".equals(optType)) {
			if (!StringUtil.isBlank(gmtExpressDate)) {
				DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sf.parse(gmtExpressDate);
				motorTransInfo.setGmtExpressDate(date);
			}
			expressManager.addMotorTransInfo(motorTransInfo);
			return "redirect:/express/list_motor_trans.html";
		}
		return "/express/add_motor_trans";
	}

	/**
	 * ������Ϣ
	 *
	 * @return String
	 * @throws Exception
	 * @author chenhang 2010/12/16
	 */
	@RequestMapping(value = "/list_motor_trans")
	@AdminAccess({ EnumAdminPermission.A_EXPRESS_MOTOR_TRANS_INFO })
	public String searchMotorTransInfo(
			@ModelAttribute("motorTransQuery") MotorTransQuery motorTransQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			Model model) throws Exception {

		// ȡ�÷�����������������
		QueryPage queryPage = this.expressManager.listMotorByCond(
				motorTransQuery, currPage, pageSize);
		model.addAttribute("query", queryPage);
		return "/express/list_motor_trans";
	}

	/**
	 * �޸�motorTransInfo
	 *
	 * @return "success" if no exceptions thrown ,"error" if no id
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit_motor_trans")
	@AdminAccess({ EnumAdminPermission.A_EXPRESS_MOTOR_TRANS_INFO })
	public String updateMotorTransInfo(
			@ModelAttribute("motorTransInfo") MotorTransInfo motorTransInfo,
			@RequestParam(value = "id", required = false, defaultValue = "") String idPara,
			@RequestParam(value = "optType", required = false, defaultValue = "") String optType,
			@RequestParam(value = "gmtExpressDate", required = false, defaultValue = "") String gmtExpressDate,
			Model model) throws Exception {

		if ("edit".equals(optType)) {
			if (!StringUtil.isBlank(gmtExpressDate)) {
				DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sf.parse(gmtExpressDate);
				motorTransInfo.setGmtExpressDate(date);
			}
			// ���и��²���
			expressManager.editMotorTransInfo(motorTransInfo);
			return "redirect:/express/list_motor_trans.html";
		} else {
			// ��ȡ������Ϣid����
			Long id = null;
			if (StringUtils.isNotBlank(idPara)
					|| !StringUtils.isNumeric(idPara)) {
				id = Long.parseLong(idPara);
			} else {
				model.addAttribute("message", "������ϢIDΪ�ջ������֣�");
				return "/error";
			}

			// ����idȡ�ù�����Ϣ
			motorTransInfo = expressManager.getMotorTransInfoById(id);
			if (motorTransInfo.getGmtExpressDate() != null) {
				DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				model.addAttribute("gmtExpressDate",
						sf.format(motorTransInfo.getGmtExpressDate()));
			}
			model.addAttribute("motorTransInfo", motorTransInfo);
		}
		return "/express/edit_motor_trans";
	}

	/**
	 * ɾ��motorTransInfo
	 *
	 * @return "success" if no exceptions thrown ,"error" if no id
	 * @throws Exception
	 */
	@RequestMapping(value = "/del_motor_trans")
	@AdminAccess({ EnumAdminPermission.A_EXPRESS_MOTOR_TRANS_INFO })
	public String delMotorTransInfo(
			@RequestParam(value = "id", required = false, defaultValue = "") String idPara,
			Model model) throws Exception {

		if (idPara == null || idPara.length() == 0
				|| !StringUtil.isNumber(idPara)) {
			model.addAttribute("message", "IDΪ�գ��������֣�");
			return "/express/list_motor_trans";
		}
		Long id = Long.valueOf(idPara);

		// ���и��²���
		expressManager.delMotorTransInfo(id);
		model.addAttribute("message", "ɾ���ɹ���");
		return "redirect:/express/list_motor_trans.html";
	}
}
