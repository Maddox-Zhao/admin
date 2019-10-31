package com.huaixuan.network.biz.dao.counter;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.base.payment.BaseBankPayOnline;
import com.huaixuan.network.biz.domain.counter.BankCompareItem;
import com.huaixuan.network.biz.domain.counter.InputFile;
import com.huaixuan.network.biz.domain.counter.InputFileSearchBean;

/**  
 * @version 3.2.0  
 */
public interface InputFileDao {

    /**
     * 得到导入文件批次表seq的
     * @return
     * @throws Exception
     */
    public Long getInputFileSeq();

    /**
     * 文件批次表查询分顄
     * @param parameterMap
     * @param page
     * @return
     */
    //    PageUtil<InputFile> getInputFileByParameterMap(Map<String, Object> parameterMap, Page page);

    public int getInputFileByParameterMapCount(Map parameterMap);

    public List<InputFile> getInputFileByParameterMap(Map parameterMap);

    /**
     * 修改，银行成功，系统成功，但是金额不等的处理sql
     * @param inputFileSearchBean
     */
    void updateBankCompareEqualSucc(InputFileSearchBean inputFileSearchBean);

    /**
     * 修改，银行成功，系统未成功的处理sql 
     * @param inputFileSearchBean
     */
    void updateBankCompareNotEqualSucc(InputFileSearchBean inputFileSearchBean);

    /**
     * 修改对账文件
     * @param epInputFile
     */
    void editInputFile(InputFile epInputFile);

    /**
     * 统计两边成功的条敄(银行)
     * @param inputFileSearchBean
     * @return
     */
    int getCountBankCompareEqualSucc(InputFileSearchBean inputFileSearchBean);

    /**
     * 统计两边待处理和金额不等条数(银行)
     * @param inputFileSearchBean
     * @return
     */
    int getCountBankCompareNotEqualSucc(InputFileSearchBean inputFileSearchBean);

    /**
     * 统计两边待处理条敄(银行)
     * @param inputFileSearchBean
     * @return
     */
    int getCountBankCompareWaitDealCount(InputFileSearchBean inputFileSearchBean);

    /**
     * 统计金额不等条数(银行)
     * @param inputFileSearchBean
     * @return
     */
    int getCountBankCompareNotEqualCount(InputFileSearchBean inputFileSearchBean);

    /**
     * 统计两边成功的条敄(网点)
     * @param inputFileSearchBean
     * @return
     */
    int getCountTerminalCompareEqualSucc(InputFileSearchBean inputFileSearchBean);

    /**
     * 统计两边待处理条敄(网点)
     * @param inputFileSearchBean
     * @return
     */
    int getCountTerminalCompareNotEqualSucc(InputFileSearchBean inputFileSearchBean);

    /**
     * 修改，网点成功，系统未成功的处理sql
     * @param inputFileSearchBean
     */
    void updateTermianlCompareNotEqualSucc(InputFileSearchBean inputFileSearchBean);

    /**
     * 统计两边待处理条敄(银行)
     * @param inputFileSearchBean
     * @return
     */
    int getCountBankCompareWaitDeal(InputFileSearchBean inputFileSearchBean);

    /**
     * 统计两边待处理条敄(代充，代仄)
     * @param inputFileSearchBean
     * @return
     */
    int getCountTerminalCompareWaitDeal(InputFileSearchBean inputFileSearchBean);

    /**
     * 得到银行的待处理数据,IS_SUCCEED = 'Y' ,FLAG_COMPARE = 'F'
     * @param inputFileSearchBean
     * @return
     */
    List<BaseBankPayOnline> getBankPayOnLineList(InputFileSearchBean inputFileSearchBean);

    /**
     * 添加对账文件批次记录
     * @param inputFileu
     */
    void addInputFile(InputFile inputFileu);

    /**
     * 根据id删除对账文件批次记录
     * @param batchId
     */
    void removeInputFile(Long batchId);

    /**
     * 根据id得到批次文件记录
     * @param batchId
     * @return
     */
    InputFile getInputFile(Long batchId);

    /**
     * 得到List
     * @return
     */
    List<InputFile> getInputFiles();

    /**
     * 查询该批次重复的银行订单叄
     */
    List<BankCompareItem> getBankCompareItemBankBillNO(InputFile inputFile);

    /**
     * 删除该批次重复的银行订单叄
     */
    void deleteBankCompareItemBankBillNO(InputFile inputFile);
}
