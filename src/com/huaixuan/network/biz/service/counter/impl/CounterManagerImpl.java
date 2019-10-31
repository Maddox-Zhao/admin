/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-31
 */
package com.huaixuan.network.biz.service.counter.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.dao.account.AccountDao;
import com.huaixuan.network.biz.dao.account.BankPayOnlineDao;
import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.domain.counter.CounterCompareReq;
import com.huaixuan.network.biz.domain.counter.CounterOutPintMessage;
import com.huaixuan.network.biz.enums.EnumCounterManger;
import com.huaixuan.network.biz.enums.EnumPaymentType;
import com.huaixuan.network.biz.service.counter.CounterManager;
import com.huaixuan.network.biz.service.counter.ParseCompareFileFactory;
import com.huaixuan.network.biz.service.counter.ParseCompareFileTrans;
import com.huaixuan.network.biz.service.goods.UploadUtil;
import com.hundsun.itrans.biz.manager.AccountTransManager;
import com.hundsun.itrans.common.util.DateUtil;

/**
 * @author shengyong
 * @version $Id: CounterManagerImpl.java,v 0.1 2011-3-31 ����01:55:41 shengyong Exp $
 */
/**
 * @author guoyj
 * @version $Id: CounterManagerImpl.java,v 0.1 2010-6-7 上午10:39:14 guoyj Exp $
 */
@Service("counterManager")
public class CounterManagerImpl implements CounterManager {

    protected final Log     log = LogFactory.getLog(getClass());

    @Autowired
    UploadUtil              uploadUtil;                         //文件上传处理�

    @Autowired
    BankPayOnlineDao        bankPayOnlineDao;                   //银行订单号操�

    @Autowired
    AccountTransManager     accountTransManager;                //帐务核心操作

    @Autowired
    AccountDao              accountDao;                         //账户

    @Autowired
    ParseCompareFileFactory parseCompareFileFactory;            //产生解析接口的工�

    public void setUploadUtil(UploadUtil uploadUtil) {
        this.uploadUtil = uploadUtil;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setAccountTransManager(AccountTransManager accountTransManager) {
        this.accountTransManager = accountTransManager;
    }

    public void setBankPayOnlineDao(BankPayOnlineDao bankPayOnlineDao) {
        this.bankPayOnlineDao = bankPayOnlineDao;
    }

    public void setParseCompareFileFactory(ParseCompareFileFactory parseCompareFileFactory) {
        this.parseCompareFileFactory = parseCompareFileFactory;
    }

    /**
     * 上传对账文件
     * @param compareAccountFile
     * @param compareAccountFileFileName
     * @return  string[0]返回上传之后的重新命名的名字，string[1]返回上传的路�
     */
    public String[] uploadCompareFile(File compareAccountFile, String compareAccountFileFileName) {
        try {

            String compareFilePath = "backUpCompareFile" + Constants.FILE_SEP
                                     + DateUtil.getDateTime("yyyyMM", new Date());
            return uploadUtil.uploadComapreFile(compareAccountFile, compareAccountFileFileName,
                compareFilePath);
        } catch (Exception e) {
            log.error("upload file error:", e);
            return null;
        }
    }

    /**
     * 根据不同银行生成规则生成,目前仅为年月�+2+支付目的+8位sequence
     * @param payDest
     * @return
     */
    public String getStringSeq(String payDest) {
        Date date = new Date();
        String todayDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //格式转换
        todayDate = sdf.format(date);

        String seq = String.valueOf(accountDao.getPayMentOffLineSeq()); // 得到SEQ_PAYMENT_OFFLINE�

        String str_zero = "0"; // 左端补零
        if (seq.length() < 8) // 如果用户id是小亍的�
        {
            for (int i = 0; i < 8 - seq.length() - 1; i++)
                // 左端循环补零
                str_zero = "0" + str_zero; // 要补零的个数
            seq = str_zero + seq; // 构�的新的seq

        }

        return todayDate + String.valueOf(EnumPaymentType.PAYMENT_TYPE_TMO.getValue()) + payDest
               + seq;
    }

    /**
     * 判断某账户是否是公司账户,�般公司账户都未账户的，一个是收款账户，一个是付款账户 
     * @param accountNo
     * @return
     */
    public boolean isCompayAccount(String accountNo) {
        return accountDao.getCompayAccountCount(accountNo) > 0;
    }

    /**
     *���������ļ������ݶ�����������
     * @param counterCompareReq
     * @return
     */
    public CounterOutPintMessage parseCompareFileExcute(CounterCompareReq counterCompareReq) {

        ParseCompareFileTrans trans = null;

        //����transCode��ȡʵ����
        trans = parseCompareFileFactory.createParseCompareFileTrans(counterCompareReq.getCompareAccountType());
        if (trans == null) {
            CounterOutPintMessage counterOutPintMessageu = new CounterOutPintMessage();
            counterOutPintMessageu.setBatchId(""); //�����ļ����κ�
            counterOutPintMessageu.setAccountFileName(counterCompareReq
                .getCompareReNameAccountFile());//�����ļ���
            counterOutPintMessageu.setBlankName(counterCompareReq.getCompareAccountType()); //������������
            counterOutPintMessageu.setImportSuccessCount("0"); //���óɹ���������
            counterOutPintMessageu.setImportFailCount("0"); //����ʧ�ܵ�������
            counterOutPintMessageu.getFailDescriptionListu().add(
                EnumCounterManger.FAILDESCRIPTIONSTR.getValue());
            return counterOutPintMessageu;
        }
        //ִ�о������
        return trans.doParseFile(counterCompareReq);

    }

    @Override
    public String[] uploadCompareFileNew(MultipartFile compareAccountFile,
                                         String compareAccountFileFileName) {
        try {

            String compareFilePath = "backUpCompareFile" + Constants.FILE_SEP
                                     + DateUtil.getDateTime("yyyyMM", new Date());
            return uploadUtil.uploadComapreMultipartFile(compareAccountFile,
                compareAccountFileFileName, compareFilePath);
        } catch (Exception e) {
            log.error("upload file error:", e);
            return null;
        }
    }

}
