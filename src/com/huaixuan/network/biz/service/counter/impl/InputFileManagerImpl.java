package com.huaixuan.network.biz.service.counter.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.account.BankPayOnlineDao;
import com.huaixuan.network.biz.dao.counter.InputFileDao;
import com.huaixuan.network.biz.domain.base.payment.BaseBankPayOnline;
import com.huaixuan.network.biz.domain.counter.InputFile;
import com.huaixuan.network.biz.domain.counter.InputFileSearchBean;
import com.huaixuan.network.biz.domain.trade.TradeResult;
import com.huaixuan.network.biz.enums.EnumInputFileMistakeType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.counter.InputFileManager;
import com.huaixuan.network.biz.service.trade.TradeManager;
import com.hundsun.itrans.biz.domain.Enum.EnumInstitution;
import com.hundsun.itrans.biz.domain.Enum.EnumSubTransCode;
import com.hundsun.itrans.biz.manager.AccountTransManager;
import com.hundsun.itrans.biz.model.AccountTransReq;
import com.hundsun.itrans.biz.model.AccountTransResult;
import com.hundsun.itrans.common.util.Money;
import com.hundsun.network.melody.common.util.StringUtil;

/**  
 * @version 3.2.0  
 */
@Service("inputFileManager")
public class InputFileManagerImpl implements InputFileManager {

    protected final Log log = LogFactory.getLog(getClass());

    @Autowired
    InputFileDao        inputFileDao;

    @Autowired
    AccountTransManager accountTransManager;      

    @Autowired
    TradeManager        tradeManager;                   

    @Autowired
    BankPayOnlineDao    bankPayOnlineDao;                

    /**
     * @param inputFileu
     */
    public void addInputFile(InputFile inputFileu) {

        this.inputFileDao.addInputFile(inputFileu);

    }

    /**
     * @param inputFileu
     */
    public void editInputFile(InputFile inputFileu) {

        this.inputFileDao.editInputFile(inputFileu);

    }

    /**
     * @param batchId
     */
    public void removeInputFile(Long batchId) {

        this.inputFileDao.removeInputFile(batchId);

    }

    /**
     * @param batchId
     * @return
     */
    public InputFile getInputFile(Long batchId) {

        return this.inputFileDao.getInputFile(batchId);

    }

    /**
     * @return
     */
    public List<InputFile> getInputFiles() {

        return this.inputFileDao.getInputFiles();

    }

    /**
     * @return Long
     */
    public Long getInputFileSeq() {

        return inputFileDao.getInputFileSeq();

    }

    //    /**
    //     * @param parameterMap
    //     * @param page
    //     * @return
    //     */
    //    public PageUtil<InputFile> getInputFileByParameterMap(Map<String, Object> parameterMap,
    //                                                          Page page) {
    //
    //        return this.inputFileDao.getInputFileByParameterMap(parameterMap, page);
    //
    //    }

    /**
     * @param inputFileSearchBean
     * @return
     */
    public InputFileSearchBean doCompareFile(InputFileSearchBean inputFileSearchBean) {
        if (inputFileSearchBean == null || StringUtil.isEmpty(inputFileSearchBean.getBatchId())) {
            log.error("no file to do data compare, please check the file import process");
            return null;
        }
        //add over
        long begin = System.currentTimeMillis();
        log.info("-----------Start to do file compare--------------");

        if (inputFileSearchBean.getOperateType().equals("C")) 
        {
            inputFileDao.updateBankCompareEqualSucc(inputFileSearchBean);
            inputFileDao.updateBankCompareNotEqualSucc(inputFileSearchBean);
            inputFileSearchBean.setCompareSuccessCount(inputFileDao
                .getCountBankCompareEqualSucc(inputFileSearchBean));
            //            inputFileSearchBean.setCompareNotSuccessCount(inputFileDao
            //                .getCountBankCompareNotEqualSucc(inputFileSearchBean));
            inputFileSearchBean.setWaiteDealCount(inputFileDao
                .getCountBankCompareWaitDealCount(inputFileSearchBean));
            inputFileSearchBean.setSumNotEqualCount(inputFileDao
                .getCountBankCompareNotEqualCount(inputFileSearchBean));
        } else 
        {
            inputFileDao.updateTermianlCompareNotEqualSucc(inputFileSearchBean);
            inputFileSearchBean.setCompareSuccessCount(inputFileDao
                .getCountTerminalCompareEqualSucc(inputFileSearchBean));
            inputFileSearchBean.setCompareNotSuccessCount(inputFileDao
                .getCountTerminalCompareNotEqualSucc(inputFileSearchBean));
        }
        InputFile inputFileu = new InputFile();
        inputFileu.setBatchId(Long.parseLong(inputFileSearchBean.getBatchId()));
        inputFileu.setStatus(Long.valueOf(3));
        inputFileDao.editInputFile(inputFileu); 

        long end = System.currentTimeMillis();
        long runTime = end - begin;
        log.info("---------finish file compare, the time is " + runTime + " ms");
        return inputFileSearchBean;

    }

    /**
     * @param inputFileSearchBean
     * @return
     */
    public InputFileSearchBean getCountBankCompareWaitDeal(InputFileSearchBean inputFileSearchBean) {

        inputFileSearchBean.setCompareNotSuccessCount(inputFileDao
            .getCountBankCompareWaitDeal(inputFileSearchBean)); 
        return inputFileSearchBean;
    }

    /**
     * @param inputFileSearchBean
     * @return
     */
    public InputFileSearchBean doRecoverBankFile(InputFileSearchBean inputFileSearchBean) {
        //add by daodao at 2009-09-23
        if (inputFileSearchBean == null) {
            return null;
        }
        //add over

        StringBuffer sBuf = new StringBuffer();
        List<BaseBankPayOnline> listu = inputFileDao.getBankPayOnLineList(inputFileSearchBean); 
        if (listu.size() > 0) {
            for (BaseBankPayOnline bankPayOnlineu : listu) { 
                try {
                    /******************************************/
                    AccountTransReq req = new AccountTransReq();
                    Money amount = new Money();
                    amount.setCent(bankPayOnlineu.getRealAmount());
                    req.setAmount(amount); 
                    req.setBankType(EnumInstitution.getByName(bankPayOnlineu.getBankType())); 
                    req.setAccountNo(bankPayOnlineu.getAccountNo()); 
                    req.setInnerBizNo(bankPayOnlineu.getInnerBillNo()); 
                    req.setOperator(inputFileSearchBean.getOperator()); 
                    req.setOutBizNo(bankPayOnlineu.getBankBillNo()); 
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
                    req.setOutDate(sdf.format(bankPayOnlineu.getPayDate())); 
                    req.setSubTransCode(EnumSubTransCode.TXCODE_DEPOSIT_ONLINE);

                    AccountTransResult result = accountTransManager.execute(req);
                    /*******************************************/

                    if (result.getCode().equals("TXN_RESULT_SUCCESS")) 
                    {
                        if (bankPayOnlineu.getPayAmount() == bankPayOnlineu.getRealAmount())
                        {
                            if (bankPayOnlineu.getPayDest() == 1) 
                            {
                                TradeResult traderesult = tradeManager.updateTradeStatusByPayment(
                                    bankPayOnlineu.getInnerBillNo(), bankPayOnlineu.getBankType());

                                if (traderesult.isSucess()) 
                                {
                                    bankPayOnlineu.setFlagCompare("S");
                                    bankPayOnlineu.setIsCheck("Y");
                                    bankPayOnlineDao.editBankPayOnline(bankPayOnlineu); 
                                } else 
                                {
                                    bankPayOnlineu.setPayAmount(bankPayOnlineu.getRealAmount());
                                    bankPayOnlineu.setPayDest(2);
                                    bankPayOnlineu.setFlagCompare("S");
                                    bankPayOnlineu.setIsCheck("Y");
                                    bankPayOnlineDao.editBankPayOnline(bankPayOnlineu); 
                                }
                            } else if (bankPayOnlineu.getPayDest() == 2
                                       || bankPayOnlineu.getPayDest() == 3) 
                            {
                                bankPayOnlineu.setFlagCompare("S");
                                bankPayOnlineu.setIsCheck("Y");
                                bankPayOnlineDao.editBankPayOnline(bankPayOnlineu); 
                            }
                            //                            else 
                            //                            {
                            //                                FreezeBalanceReq reqFreeze = new FreezeBalanceReq();
                            //                                BeanUtils.copyProperties(reqFreeze, req);
                            //                                reqFreeze
                            //                                    .setSubTransCode(EnumSubTransCode.TXCODE_ACCOUNT_SECURITY_FREEZE);
                            //                                AccountTransResult resulFreeze = accountTransManager
                            //                                    .execute(reqFreeze);
                            //                                if (!resulFreeze.getCode().equals("TXN_RESULT_SUCCESS")) 
                            //                                {
                            //                                    sBuf.append(EnumInputFileMistakeType.BankBILLNO.getValue()
                            //                                                + bankPayOnlineu.getBankBillNo()
                            //                                                + EnumInputFileMistakeType.FREEZEBALANCEFAIL
                            //                                                    .getValue()); 
                            //                                    inputFileSearchBean.getFailMessageList().add(sBuf.toString());
                            //                                    continue;
                            //                                }
                            //
                            //                                bankPayOnlineu.setFlagCompare("S");
                            //                                bankPayOnlineu.setIsCheck("Y");
                            //                                bankPayOnlineDao.editBankPayOnline(bankPayOnlineu); 
                            //                            }

                        } else 
                        {
                            bankPayOnlineu.setPayAmount(bankPayOnlineu.getRealAmount());
                            bankPayOnlineu.setPayDest(2);
                            bankPayOnlineu.setFlagCompare("S");
                            bankPayOnlineu.setIsCheck("Y");
                            bankPayOnlineDao.editBankPayOnline(bankPayOnlineu); 
                        }
                    } else 
                    {
                        sBuf.append(EnumInputFileMistakeType.BankBILLNO.getValue()
                                    + bankPayOnlineu.getBankBillNo()
                                    + EnumInputFileMistakeType.ACCOUNTFAIL.getValue()); 
                        inputFileSearchBean.getFailMessageList().add(sBuf.toString());
                        continue;
                    }
                } catch (Exception e) {
                    log.error("recover bank data error:", e);
                    continue;
                }

            }
        }
        return inputFileSearchBean;

    }

    @Override
    public QueryPage getInputFileByParameterMapQuery(Map parameterMap, int currPage, int pageSize) {
        QueryPage queryPage = new QueryPage(parameterMap);
        int count = inputFileDao.getInputFileByParameterMapCount(parameterMap);
        if (count > 0) {

            /* 当前页 */
            queryPage.setCurrentPage(currPage);
            /* 每页总数 */
            queryPage.setPageSize(pageSize);
            queryPage.setTotalItem(count);

            parameterMap.put("startRow", queryPage.getPageFristItem());
            parameterMap.put("endRow", queryPage.getPageLastItem());

            List<InputFile> inputFileList = inputFileDao.getInputFileByParameterMap(parameterMap);
            if (inputFileList != null && inputFileList.size() > 0) {
                queryPage.setItems(inputFileList);
            }
        }
        return queryPage;
    }

}
