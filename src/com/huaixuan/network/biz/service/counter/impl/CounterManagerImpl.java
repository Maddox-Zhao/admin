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
 * @version $Id: CounterManagerImpl.java,v 0.1 2011-3-31 下午01:55:41 shengyong Exp $
 */
/**
 * @author guoyj
 * @version $Id: CounterManagerImpl.java,v 0.1 2010-6-7 涓婂崍10:39:14 guoyj Exp $
 */
@Service("counterManager")
public class CounterManagerImpl implements CounterManager {

    protected final Log     log = LogFactory.getLog(getClass());

    @Autowired
    UploadUtil              uploadUtil;                         //鏂囦欢涓婁紶澶勭悊绫

    @Autowired
    BankPayOnlineDao        bankPayOnlineDao;                   //閾惰璁㈠崟鍙锋搷浣

    @Autowired
    AccountTransManager     accountTransManager;                //甯愬姟鏍稿績鎿嶄綔

    @Autowired
    AccountDao              accountDao;                         //璐︽埛

    @Autowired
    ParseCompareFileFactory parseCompareFileFactory;            //浜х敓瑙ｆ瀽鎺ュ彛鐨勫伐鍘

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
     * 涓婁紶瀵硅处鏂囦欢
     * @param compareAccountFile
     * @param compareAccountFileFileName
     * @return  string[0]杩斿洖涓婁紶涔嬪悗鐨勯噸鏂板懡鍚嶇殑鍚嶅瓧锛宻tring[1]杩斿洖涓婁紶鐨勮矾寰
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
     * 鏍规嵁涓嶅悓閾惰鐢熸垚瑙勫垯鐢熸垚,鐩墠浠呬负骞存湀鏃+2+鏀粯鐩殑+8浣峴equence
     * @param payDest
     * @return
     */
    public String getStringSeq(String payDest) {
        Date date = new Date();
        String todayDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //鏍煎紡杞崲
        todayDate = sdf.format(date);

        String seq = String.valueOf(accountDao.getPayMentOffLineSeq()); // 寰楀埌SEQ_PAYMENT_OFFLINE鍊

        String str_zero = "0"; // 宸︾琛ラ浂
        if (seq.length() < 8) // 濡傛灉鐢ㄦ埛id鏄皬浜嶇殑璇
        {
            for (int i = 0; i < 8 - seq.length() - 1; i++)
                // 宸︾寰幆琛ラ浂
                str_zero = "0" + str_zero; // 瑕佽ˉ闆剁殑涓暟
            seq = str_zero + seq; // 鏋勯犵殑鏂扮殑seq

        }

        return todayDate + String.valueOf(EnumPaymentType.PAYMENT_TYPE_TMO.getValue()) + payDest
               + seq;
    }

    /**
     * 鍒ゆ柇鏌愯处鎴锋槸鍚︽槸鍏徃璐︽埛,涓鑸叕鍙歌处鎴烽兘鏈处鎴风殑锛屼竴涓槸鏀舵璐︽埛锛屼竴涓槸浠樻璐︽埛 
     * @param accountNo
     * @return
     */
    public boolean isCompayAccount(String accountNo) {
        return accountDao.getCompayAccountCount(accountNo) > 0;
    }

    /**
     *解析对账文件，根据对账银行类型
     * @param counterCompareReq
     * @return
     */
    public CounterOutPintMessage parseCompareFileExcute(CounterCompareReq counterCompareReq) {

        ParseCompareFileTrans trans = null;

        //根据transCode获取实现类
        trans = parseCompareFileFactory.createParseCompareFileTrans(counterCompareReq.getCompareAccountType());
        if (trans == null) {
            CounterOutPintMessage counterOutPintMessageu = new CounterOutPintMessage();
            counterOutPintMessageu.setBatchId(""); //设置文件批次号
            counterOutPintMessageu.setAccountFileName(counterCompareReq
                .getCompareReNameAccountFile());//设置文件名
            counterOutPintMessageu.setBlankName(counterCompareReq.getCompareAccountType()); //设置银行类型
            counterOutPintMessageu.setImportSuccessCount("0"); //设置成功导入条数
            counterOutPintMessageu.setImportFailCount("0"); //设置失败导入条数
            counterOutPintMessageu.getFailDescriptionListu().add(
                EnumCounterManger.FAILDESCRIPTIONSTR.getValue());
            return counterOutPintMessageu;
        }
        //执行具体操作
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
