/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-31
 */
package com.huaixuan.network.web.action.counter;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.counter.CounterCompareReq;
import com.huaixuan.network.biz.domain.counter.CounterOutPintMessage;
import com.huaixuan.network.biz.domain.counter.InputFileSearchBean;
import com.huaixuan.network.biz.domain.counter.TransApp;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumCounterAllowUploadType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.counter.AccountTransAdminManager;
import com.huaixuan.network.biz.service.counter.CounterManager;
import com.huaixuan.network.biz.service.counter.InputFileManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.itrans.biz.domain.Enum.EnumInstitution;
import com.hundsun.itrans.biz.domain.Enum.EnumSubTransCode;
import com.hundsun.itrans.biz.model.AccountTransReq;
import com.hundsun.itrans.biz.model.AccountTransResult;
import com.hundsun.itrans.common.util.Money;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author shengyong
 * @version $Id: CounterAction.java,v 0.1 2011-3-31 下午03:55:40 shengyong Exp $
 */
@Controller
public class CounterAction extends BaseAction {

    protected final Log log = LogFactory.getLog(this.getClass());

    @AdminAccess({EnumAdminPermission.A_COUNTER_FILE_IMPORT})
    @RequestMapping(value = "/counter/addImportCounterFilePage", method = RequestMethod.GET)
    public String importCounterFile(Model model) {
        return "/counter/importCounterFile";
    }

    @Autowired
    CounterManager counterManager;

    /**
     * 导入对账文件动作
     * @return
     */
    @RequestMapping(value = "/counter/importCounterFile", method = RequestMethod.POST)
    public String importCounterFile(HttpServletRequest request,
                                    Model model,
                                    @RequestParam(value = "compareAccountFile", required = false) MultipartFile compareAccountFileFile,
                                    AdminAgent adminAgent) {

        /*****************************前台判断开始*************************************************/
        String compareAccountType = request.getParameter("compareAccountType"); //对账银行类型
        compareAccountType = (compareAccountType == null) ? "" : compareAccountType;

        if (compareAccountType.equals("")) //如果对账银行类型为空
        {
            model.addAttribute("counterTypeInfoError", "请选择对账类型!");
            return "/counter/importCounterFile";
        }
        String compareAccountFileFileName = compareAccountFileFile.getOriginalFilename();

        compareAccountFileFileName = (compareAccountFileFileName == null) ? ""
            : compareAccountFileFileName;
        if (compareAccountFileFileName.equals("")) //如果没有选择文件
        {
            model.addAttribute("counterAccountFileInfoError", "请选择对账文件！");
            return "/counter/importCounterFile";
        }
        /*****************************前台判断结束*************************************************/

        String extention = getExtention(compareAccountFileFileName); //得到上传文件的后缀名称

        if (compareAccountType.equals(EnumInstitution.INST_ALIPAY.getName())) //如果对账的是支付宝
        {
            if (!extention.equals(EnumCounterAllowUploadType.ALIPAY.getKey())) //如果不符合支付宝对账文件的格式
            {
                model.addAttribute("counterAccountAlipayFileExtentionError",
                    EnumCounterAllowUploadType.ALIPAYFILEEXTENTIONERROR.getValue());
                return "/counter/importCounterFile";
            }
        } else if (compareAccountType.equals(EnumInstitution.INST_TENPAY.getName())) {
            if (!extention.equalsIgnoreCase(EnumCounterAllowUploadType.TENPAY.getKey())) //如果不符合财付通对账文件的格式
            {
                model.addAttribute("counterAccountTenpayFileExtentionError",
                    EnumCounterAllowUploadType.TENPAYFILEEXTENTIONERROR.getValue());
                return "/counter/importCounterFile";
            }
        } else if (compareAccountType.equals(EnumInstitution.INST_CHINABANK.getName())) {
            if (!extention.equalsIgnoreCase(EnumCounterAllowUploadType.CHINABANK.getKey())) //如果不符合网银在线对账文件的格式
            {
                model.addAttribute("counterAccountChinabankFileExtentionError",
                    EnumCounterAllowUploadType.CHINABANKFILEEXTENTIONERROR.getValue());
                return "/counter/importCounterFile";
            }
        } else if (compareAccountType.equals(EnumInstitution.INST_POST.getName())) {
            if (!extention.equals(EnumCounterAllowUploadType.POST.getKey())) //如果不符合中国邮政对账文件的格式
            {
                model.addAttribute("counterAccountPostFileExtentionError",
                    EnumCounterAllowUploadType.POSTFILEEXTENTIONERROR.getValue());
                return "/counter/importCounterFile";
            }
        } else if (compareAccountType.equals(EnumInstitution.INST_JSPOINT.getName())) //如果对账的是江苏邮政积分卡
        {
            if (!extention.equals("")) //如果不符合代充代付对账文件的格式,积分卡的后缀格式是空的
            {
                model.addAttribute("counterAccountJsPointFileExtentionError",
                    EnumCounterAllowUploadType.JSPOSTFILEEXTENTIONERROR.getValue());
                return "/counter/importCounterFile";
            }
        } else if (compareAccountType.equals(EnumInstitution.INST_EBANK_ICBC.getName())) //如果对账的是工商银行
        {
            if (!extention.equals(EnumCounterAllowUploadType.ICBC.getKey())) //如果不符合工商银行对账文件的格式
            {
                model.addAttribute("counterAccountIcbcFileExtentionError",
                    EnumCounterAllowUploadType.ICBCFILEEXTENTIONERROR.getValue());
                return "/counter/importCounterFile";
            }
        } else //如果是其他的对账格式
        {
            model.addAttribute("counterAccountOtherFileExtentionError",
                EnumCounterAllowUploadType.OTHERFILEEXTENTIONERROR.getValue());
            return "/counter/importCounterFile";
        }

        String[] str = counterManager.uploadCompareFileNew(compareAccountFileFile,
            compareAccountFileFileName); //上传文件

        /*****************************对账文件上传结束*************************************************/

        /*****************************对账文件批量上传开始*************************************************/
        CounterCompareReq counterCompareReq = new CounterCompareReq();
        counterCompareReq.setCompareAccountFile(str[1]); //设置上传的路径名称
        counterCompareReq.setDealOperator(adminAgent.getUsername());
        counterCompareReq.setCompareReNameAccountFile(str[0]);//设置上传的文件名称
        counterCompareReq.setCompareAccountType(compareAccountType);
        //        counterOutPintMessage = counterManager.parseCompareFile(counterCompareReq);
        CounterOutPintMessage counterOutPintMessage = new CounterOutPintMessage();
        counterOutPintMessage = counterManager.parseCompareFileExcute(counterCompareReq);
        /*****************************对账文件批量上传结束*************************************************/

        //        addWorkLog("文件导入操作", str[0], EnumWorkLogType.Import.getKey(), "文件导入操作成功");
        model.addAttribute("counterOutPintMessage", counterOutPintMessage);
        return "/counter/showSuccessMessage";

    }

    private static String getExtention(String fileName) {
        int pos = fileName.lastIndexOf(".");
        if (pos != -1)
            return fileName.substring(pos + 1);
        else
            return "";
    }

	@AdminAccess({ EnumAdminPermission.A_COUNTER_FILE_SHOW })
	@RequestMapping(value = "/counter/showImportCounterFilePage", method = RequestMethod.GET)
	public String showImportCounterFilePageInit(
			@ModelAttribute("inputFileSearchBean") InputFileSearchBean inputFileSearchBean, Model model,
			HttpServletRequest req) {
		java.util.Calendar ca = java.util.Calendar.getInstance();
		ca.add(Calendar.DAY_OF_MONTH, -1);
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String result = sdf.format(ca.getTime()); // 得到前一天日期

		inputFileSearchBean.setDatestart(result);
		inputFileSearchBean.setDateend(result);

		return showImportCounterFilePage(inputFileSearchBean, 1, model, req);
	}

	@AdminAccess({ EnumAdminPermission.A_COUNTER_FILE_SHOW })
	@RequestMapping(value = "/counter/showImportCounterFilePage", method = RequestMethod.POST)
	public String showImportCounterFilePage(
			@ModelAttribute("inputFileSearchBean") InputFileSearchBean inputFileSearchBean,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model,
			HttpServletRequest request) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("batchId", inputFileSearchBean.getBatchId());
		parameterMap.put("datestart", inputFileSearchBean.getDatestart());
		parameterMap.put("dateend", inputFileSearchBean.getDateend());
		parameterMap.put("bankType", inputFileSearchBean.getBankType());

		QueryPage query = inputFileManager.getInputFileByParameterMapQuery(parameterMap, currPage, pageSize);
		model.addAttribute("query", query);

		return "/counter/showImportCounterFilePage";
	}

    @AdminAccess({EnumAdminPermission.A_OPEN_BANK_RECOVER})
    @RequestMapping(value = "/counter/openBankRecoverPage", method = RequestMethod.GET)
    public String openBankRecoverPageInit(@ModelAttribute("inputFileSearchBean") InputFileSearchBean inputFileSearchBean,
                                          Model model) {
        model.addAttribute("inputFileSearchBean", inputFileSearchBean);
        return "/counter/showBankRecoverPageMessage";
    }

    @RequestMapping(value = "/counter/showBankRecoverPage")
    public String openBankRecoverPage(@ModelAttribute("inputFileSearchBean") InputFileSearchBean inputFileSearchBean,
                                      Model model, HttpServletRequest request) {
        inputFileSearchBean.setDatestart(request.getParameter("datestart"));
        inputFileSearchBean.setBankType(request.getParameter("bankType"));
        inputFileSearchBean = inputFileManager.getCountBankCompareWaitDeal(inputFileSearchBean);
        model.addAttribute("inputFileSearchBean", inputFileSearchBean);
        return "/counter/showBankWaitCount";
    }

    @RequestMapping(value = "/counter/doBankRecover")
    public String doBankRecover(@ModelAttribute("inputFileSearchBean") InputFileSearchBean inputFileSearchBean,
                                Model model, HttpServletRequest request, AdminAgent adminAgent) {
        String createDate = request.getParameter("datestart");
        inputFileSearchBean.setDatestart(request.getParameter("datestart"));
        inputFileSearchBean.setBankType(request.getParameter("bankType"));
        inputFileSearchBean.setOperator(adminAgent.getUsername());
        inputFileSearchBean = inputFileManager.doRecoverBankFile(inputFileSearchBean);
        //        addWorkLog("数据恢复操作", createDate, EnumWorkLogType.Recover.getKey(), "数据恢复操作成功");
        model.addAttribute("inputFileSearchBean", inputFileSearchBean);
        return "/counter/showBankWaitCount";
    }

    @Autowired
    InputFileManager inputFileManager;

    /**
     * 文件对账动作
     * @return
     */
	@RequestMapping(value = "/counter/doCompareFile")
	public String doCompareFile(@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			HttpServletRequest request, AdminAgent adminAgent, Model model) {
		InputFileSearchBean inputFileSearchBeann = new InputFileSearchBean();
		inputFileSearchBeann.setOperator(adminAgent.getUsername()); // 设置操作人员
		inputFileSearchBeann.setBatchId(request.getParameter("batchId")); // 设置对账文件批次号
		inputFileSearchBeann.setBankType(request.getParameter("bankType")); // 设置银行类型
		inputFileSearchBeann.setOperateType(request.getParameter("operateType")); // 设置操作类型
		inputFileSearchBeann.setDatestart(request.getParameter("datestart")); // 设置开始时间
		inputFileSearchBeann.setDateend(request.getParameter("dateend")); // 设置结束时间

		inputFileSearchBeann = inputFileManager.doCompareFile(inputFileSearchBeann); // 文件对账

		// addWorkLog("文件对账操作", inputFileSearchBeann.getBatchId(), EnumWorkLogType.Compare.getKey(),
		// "文件对账操作成功");

		inputFileSearchBeann.setBatchId(request.getParameter("batchIdd")); // 设置对账文件批次号
		inputFileSearchBeann.setBankType(request.getParameter("bankTypee")); // 设置银行类型
		inputFileSearchBeann.setOperateType(request.getParameter("operateTypee")); // 设置操作类型

		model.addAttribute("duizhang", "duizhang");
		model.addAttribute("inputFileSearchBean", inputFileSearchBeann);

		return showImportCounterFilePage(inputFileSearchBeann, currPage, model, request);
	}

    @RequestMapping(value = "/counter/showAddAccTrans", method = RequestMethod.GET)
    public String showAddAccTransInit(@ModelAttribute("inputFileSearchBean") InputFileSearchBean inputFileSearchBean,
                                      Model model) {
        model.addAttribute("inputFileSearchBean", inputFileSearchBean);
        return "/counter/addAccountTrans";
    }

    private List<EnumSubTransCode> transSubCode;

    public List<EnumSubTransCode> getTransSubCode() {
        return transSubCode;
    }

    public void setTransSubCode(List<EnumSubTransCode> transSubCode) {
        this.transSubCode = transSubCode;
    }

    @RequestMapping(value = "/counter/listSubTransCodeByType", method = RequestMethod.POST)
    public String listSubTransCodeByTypeInit(@ModelAttribute("inputFileSearchBean") InputFileSearchBean inputFileSearchBean,
                                             @RequestParam(value = "transType", required = false, defaultValue = "") String transType,
                                             Model model) {
        transSubCode = EnumSubTransCode.listSubTransCodeByType(transType);
        model.addAttribute("transSubCode", transSubCode);
        model.addAttribute("transType", transType);
        return "/counter/addAccountTransCondition";
    }

    @Autowired
    AccountTransAdminManager accountTransAdminManager;

    /**
     * 进行账务补帐申请
     *
     * @return
     */
    @RequestMapping(value = "/counter/doTransApp")
    public String doTransApp(Model model, HttpServletRequest request) {

        TransApp transApp = new TransApp();
        transApp.setStatus("new");
        Map<String, String> accTransReq = new HashMap<String, String>();
        accTransReq.put("subTransCode", request.getParameter("accTransReq.subTransCode"));
        accTransReq.put("amount", request.getParameter("accTransReq.amount"));
        accTransReq.put("memo", request.getParameter("accTransReq.memo"));
        accTransReq.put("transDate", request.getParameter("accTransReq.transDate"));
        accTransReq.put("bankType", request.getParameter("accTransReq.bankType"));
        accTransReq.put("innerBizNo", request.getParameter("accTransReq.innerBizNo"));
        accTransReq.put("outBizNo", request.getParameter("accTransReq.outBizNo"));
        accTransReq.put("outDate", request.getParameter("accTransReq.outDate"));
        accTransReq.put("accountNo", request.getParameter("accTransReq.accountNo"));
        accTransReq.put("inAccountNo", request.getParameter("accTransReq.inAccountNo"));

        String subTransCode = accTransReq.get("subTransCode");
        transApp.setSubTransCode(subTransCode);
        transApp.setAmount(Double.valueOf(accTransReq.get("amount")));
        transApp.setMemo(accTransReq.get("memo"));
        transApp.setTransDate(accTransReq.get("transDate"));

        if (StringUtil.isNotBlank(accTransReq.get("bankType"))) {
            transApp.setBankType(accTransReq.get("bankType"));
        } else {
            transApp.setBankType("");
        }

        if (StringUtil.isNotBlank(accTransReq.get("innerBizNo"))) {
            transApp.setInnerBizNo(accTransReq.get("innerBizNo"));
        } else {
            transApp.setInnerBizNo("");
            ;
        }

        if (StringUtil.isNotBlank(accTransReq.get("outBizNo"))) {
            transApp.setOutBizNo(accTransReq.get("outBizNo"));
        } else {
            transApp.setOutBizNo("");
        }

        if (StringUtil.isNotBlank(accTransReq.get("outDate"))) {
            transApp.setOutDate(accTransReq.get("outDate"));
        } else {
            transApp.setOutDate("");
        }

        if (StringUtil.equalsIgnoreCase(subTransCode,
            EnumSubTransCode.TXCODE_DEPOSIT_ONLINE.getCode())) {
            transApp.setInAccountNo(accTransReq.get("accountNo").trim());
            transApp.setOutAccountNo("");
        } else if (StringUtil.equalsIgnoreCase(subTransCode,
            EnumSubTransCode.TXCODE_WITHDRAW_BATCH_SUCCESS.getCode())
                   || StringUtil.equalsIgnoreCase(subTransCode,
                       EnumSubTransCode.TXCODE_WITHDRAW_SUCCESS.getCode())) {
            transApp.setOutAccountNo(accTransReq.get("accountNo").trim());
            transApp.setInAccountNo("");
        } else if (StringUtil.equalsIgnoreCase(subTransCode,
            EnumSubTransCode.TXCODE_TRANSFER_RETURN_GOODS.getCode())) {
            transApp.setOutAccountNo(accTransReq.get("outAccountNo").trim());
            transApp.setInAccountNo(accTransReq.get("inAccountNo").trim());
        }
        String message = "";
        message = accountTransAdminManager.doTransApp(transApp);
        if ("counter.app.success".equals(message)) {
            message = "补帐申请成功！";
        } else {
            message = "补帐申请失败！";
        }
        model.addAttribute("message", message);
        model.addAttribute("accTransReq", accTransReq);

        return "/counter/addAccountTrans";
    }

//    @RequestMapping(value = "/counter/managerTransApp", method = RequestMethod.GET)
//    public String managerTransAppInit(@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
//                                      Model model, HttpServletRequest request) {
//        Map parMap = new HashMap();
//        String transDateStart = "";
//        String transDateEnd = "";
//        Date date = new Date();
//        transDateStart = DateUtil.getDiffDate(date, -30);
//        transDateEnd = DateUtil.getDateToString(date);
//        parMap.put("transDateStart", transDateStart);
//        parMap.put("transDateEnd", transDateEnd);
//        model.addAttribute("parMap", parMap);
//        return "/counter/managerTransApp";
//    }

    @RequestMapping(value = "/counter/managerTransApp")
    public String managerTransApp(@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                  Model model, HttpServletRequest request) {
        Map parMap = new HashMap();
        parMap.put("transDateStart", request.getParameter("parMap.transDateStart"));
        parMap.put("transDateEnd", request.getParameter("parMap.transDateEnd"));
        parMap.put("innerBizNo", request.getParameter("parMap.innerBizNo"));
        parMap.put("subTransCode", request.getParameter("parMap.subTransCode"));
        parMap.put("status", request.getParameter("parMap.status"));

        String transDateStart = (String) parMap.get("transDateStart");
        String transDateEnd = (String) parMap.get("transDateEnd");
        if (StringUtil.isBlank(transDateStart) && StringUtil.isBlank(transDateEnd)) {
            Date date = new Date();
            transDateStart = DateUtil.getDiffDate(date, -30);
            transDateEnd = DateUtil.getDateToString(date);
            parMap.put("transDateStart", transDateStart);
            parMap.put("transDateEnd", transDateEnd);
        }

        QueryPage query = accountTransAdminManager.getManagerTransAppQuery(parMap, currPage,
            this.pageSize);
        model.addAttribute("parMap", parMap);
        model.addAttribute("query", query);
        return "/counter/managerTransApp";
    }

    @RequestMapping(value = "/counter/showTransAppInfo", method = RequestMethod.GET)
    public String showAddAccTransInit(@ModelAttribute("transApp") TransApp transApp, Model model,
                                      HttpServletRequest request) {
        Map parMap = new HashMap();
        parMap.put("transAppId", request.getParameter("parMap.transAppId"));
        transApp = accountTransAdminManager.getTransAppInfo(parMap);
        model.addAttribute("transApp", transApp);
        return "/counter/transAppInfo";
    }

    @RequestMapping(value = "/counter/doAddAccTrans", method = RequestMethod.POST)
    public String doAddAccTrans(@ModelAttribute("transApp") TransApp transApp, Model model,
                                HttpServletRequest request, AdminAgent adminAgent) {
        String message = "";
        Map<String,String> parMap = new HashMap<String,String>();
        parMap.put("transAppId", request.getParameter("parMap.transAppId"));
        parMap.put("status", request.getParameter("parMap.status"));
        AccountTransReq transReq = new AccountTransReq();

        Map<String, String> accTransReq = new HashMap<String, String>();
        accTransReq.put("subTransCode", request.getParameter("accTransReq.subTransCode"));
        accTransReq.put("amount", request.getParameter("accTransReq.amount"));
        accTransReq.put("memo", request.getParameter("accTransReq.memo"));
        accTransReq.put("transDate", request.getParameter("accTransReq.transDate"));
        accTransReq.put("bankType", request.getParameter("accTransReq.bankType"));
        accTransReq.put("innerBizNo", request.getParameter("accTransReq.innerBizNo"));
        accTransReq.put("outBizNo", request.getParameter("accTransReq.outBizNo"));
        accTransReq.put("outDate", request.getParameter("accTransReq.outDate"));
        accTransReq.put("accountNo", request.getParameter("accTransReq.accountNo"));
        accTransReq.put("inAccountNo", request.getParameter("accTransReq.inAccountNo"));

        accTransReq.put("operator", adminAgent.getUsername());
        // 将accTransReq里面的属性拷贝到transReq里面
        try {
            if (accTransReq.get("amount") != null) {
                transReq.setAmount(new Money(accTransReq.get("amount")));
            }
            accTransReq.remove("amount");
            if (accTransReq.get("transDate") != null) {
                Date date = new Date();
                transReq.setTransDate(date);
            }
            accTransReq.remove("transDate");
            if (accTransReq.get("bankType") != null) {
                transReq.setBankType(EnumInstitution.getByName(accTransReq.get("bankType")));
            }
            accTransReq.remove("bankType");
            if (accTransReq.get("subTransCode") != null) {
                transReq
                    .setSubTransCode(EnumSubTransCode.getByCode(accTransReq.get("subTransCode")));
            }
            accTransReq.remove("subTransCode");
            if (accTransReq.get("outDate") != null) {
                transReq.setOutDate(accTransReq.get("outDate").replace("-", ""));
            }
            accTransReq.remove("outDate");
            BeanUtils.copyProperties(transReq, accTransReq);
        } catch (Exception e) {
            log.error("doAddAccTrans occur error", e);
            message = AccountTransResult.TXN_RESULT_REQ_PARA_NOT_MATCH.getMessage();
            return "/counter/transAppInfo";
        }
        message = accountTransAdminManager.doAddAccTrans(transReq, parMap);



        transApp = accountTransAdminManager.getTransAppInfo(parMap);
        model.addAttribute("transApp", transApp);
        model.addAttribute("message", message);
        model.addAttribute("parMap", parMap);
        return "/counter/transAppInfo";
    }

    /**
     * 新增导入对账文件页面
     * @return
     */
    @RequestMapping("/counter/addImportCounterFilePage")
    public String addImportCounterFilePage() {
        return "/counter/importCounterFile";

    }

}
