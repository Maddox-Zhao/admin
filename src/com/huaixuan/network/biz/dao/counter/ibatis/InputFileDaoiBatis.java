package com.huaixuan.network.biz.dao.counter.ibatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.counter.InputFileDao;
import com.huaixuan.network.biz.domain.base.payment.BaseBankPayOnline;
import com.huaixuan.network.biz.domain.counter.BankCompareItem;
import com.huaixuan.network.biz.domain.counter.InputFile;
import com.huaixuan.network.biz.domain.counter.InputFileSearchBean;

/** 
 * @version 3.2.0 
 */
@Service("inputFileDao")
public class InputFileDaoiBatis implements InputFileDao {

    @Autowired
    private SqlMapClientTemplate sqlMapClientTemplate;

    /**
     * 文件批次表查询分顄
     * @param parameterMap
     * @param page
     * @return
     */
    //    public PageUtil<InputFile> getInputFileByParameterMap(Map<String, Object> parameterMap,
    //                                                          Page page) {
    //        List<InputFile> list = this.findQueryPage("getInputFileByParameterMap",
    //            "getInputFileByParameterMapCount", parameterMap, page);
    //        if (null == list) {
    //            list = new ArrayList<InputFile>();
    //        }
    //        return new PageUtil<InputFile>(list, page);
    //    }
    public int getInputFileByParameterMapCount(Map parameterMap) {
        return (Integer) this.sqlMapClientTemplate.queryForObject(
            "getInputFileByParameterMapCount", parameterMap);
    }

    public List<InputFile> getInputFileByParameterMap(Map parameterMap) {
        return this.sqlMapClientTemplate.queryForList("getInputFileByParameterMap", parameterMap);
    }

    /**
     * 得到导入文件批次表seq的
     * @return
     * @throws Exception
     */
    public Long getInputFileSeq() {
        return (Long) this.sqlMapClientTemplate.queryForObject("getInputFileSeq");

    }

    /**
     * 修改，银行成功，系统成功，但是金额不等的处理sql
     * @param inputFileSearchBean
     */
    public void updateBankCompareEqualSucc(InputFileSearchBean inputFileSearchBean) {
        this.sqlMapClientTemplate.update("BANK_COMPARE_UPDATE_EQUAL_SUCC", inputFileSearchBean);
    }

    /**
     * 修改，银行成功，系统未成功的处理sql 
     * @param inputFileSearchBean
     */
    public void updateBankCompareNotEqualSucc(InputFileSearchBean inputFileSearchBean) {
        this.sqlMapClientTemplate.update("BANK_COMPARE_UPDATE_NOT_EQUAL_SUCC", inputFileSearchBean);
    }

    /**
     * 修改对账文件
     * @param epInputFile
     */
    public void editInputFile(InputFile epInputFile) {
        this.sqlMapClientTemplate.update("editInputFile", epInputFile);
    }

    /**
     * 统计两边成功的条敄
     * @param inputFileSearchBean
     * @return
     */
    public int getCountBankCompareEqualSucc(InputFileSearchBean inputFileSearchBean) {
        return (Integer) this.sqlMapClientTemplate.queryForObject("BANK_COMPARE_SUM_EQUAL_SUCC",
            inputFileSearchBean);
    }

    /**
     * 统计两边待处理和金额不等条数
     * @param inputFileSearchBean
     * @return
     */
    public int getCountBankCompareNotEqualSucc(InputFileSearchBean inputFileSearchBean) {
        return (Integer) this.sqlMapClientTemplate.queryForObject(
            "BANK_COMPARE_SUM_NOT_EQUAL_SUCC", inputFileSearchBean);
    }

    /**
     * 统计两边成功的条敄(网点)
     * @param inputFileSearchBean
     * @return
     */
    public int getCountTerminalCompareEqualSucc(InputFileSearchBean inputFileSearchBean) {
        return (Integer) this.sqlMapClientTemplate.queryForObject(
            "TERMINAL_COMPARE_SUM_EQUAL_SUCC", inputFileSearchBean);
    }

    /**
     * 统计两边待处理条敄(网点)
     * @param inputFileSearchBean
     * @return
     */
    public int getCountTerminalCompareNotEqualSucc(InputFileSearchBean inputFileSearchBean) {
        return (Integer) this.sqlMapClientTemplate.queryForObject(
            "TERMINAL_COMPARE_SUM_NOT_EQUAL_SUCC", inputFileSearchBean);
    }

    /**
     * 修改，网点成功，系统未成功的处理sql
     * @param inputFileSearchBean
     */
    public void updateTermianlCompareNotEqualSucc(InputFileSearchBean inputFileSearchBean) {
        this.sqlMapClientTemplate.update("TERMINAL_COMPARE_UPDATE_NOT_EQUAL_SUCC",
            inputFileSearchBean);
    }

    /**
     * 统计两边待处理条敄(银行)
     * @param inputFileSearchBean
     * @return
     */
    public int getCountBankCompareWaitDeal(InputFileSearchBean inputFileSearchBean) {
        return (Integer) this.sqlMapClientTemplate.queryForObject("BANK_COMPARE_SUM_WAIT_DEAL",
            inputFileSearchBean);
    }

    /**
     * 统计两边待处理条敄(代充，代仄)
     * @param inputFileSearchBean
     * @return
     */
    public int getCountTerminalCompareWaitDeal(InputFileSearchBean inputFileSearchBean) {
        return (Integer) this.sqlMapClientTemplate.queryForObject("TERMINAL_COMPARE_SUM_WAIT_DEAL",
            inputFileSearchBean);
    }

    /**
     * 得到银行的待处理数据,IS_SUCCEED = 'Y' ,FLAG_COMPARE = 'F'
     * @param inputFileSearchBean
     * @return
     */
    public List<BaseBankPayOnline> getBankPayOnLineList(InputFileSearchBean inputFileSearchBean) {
        return this.sqlMapClientTemplate.queryForList("GET_BANK_PAYONLINE_WAIT_DEAL",
            inputFileSearchBean);
    }

    /**
     * 添加对账文件批次记录
     * @param inputFileu
     */
    public void addInputFile(InputFile inputFileu) {
        this.sqlMapClientTemplate.insert("addInputFile", inputFileu);
    }

    /**
     * 根据id删除对账文件批次记录
     * @param batchId
     */
    public void removeInputFile(Long batchId) {
        this.sqlMapClientTemplate.delete("removeInputFile", batchId);
    }

    /**
     * 根据id得到批次文件记录
     * @param batchId
     * @return
     */
    public InputFile getInputFile(Long batchId) {
        return (InputFile) this.sqlMapClientTemplate.queryForObject("getInputFile", batchId);
    }

    /**
     * 得到List
     * @return
     */
    public List<InputFile> getInputFiles() {
        return this.sqlMapClientTemplate.queryForList("getInputFiles", null);
    }

    public void deleteBankCompareItemBankBillNO(InputFile inputFile) {
        this.sqlMapClientTemplate.delete("BANK_COMPARE_DELETE_EQUAL_BANKBILLNO", inputFile);
    }

    public List<BankCompareItem> getBankCompareItemBankBillNO(InputFile inputFile) {
        return this.sqlMapClientTemplate.queryForList("BANK_COMPARE_EQUAL_BANKBILLNO", inputFile);
    }

    public int getCountBankCompareNotEqualCount(InputFileSearchBean inputFileSearchBean) {
        return (Integer) this.sqlMapClientTemplate.queryForObject(
            "BANK_COMPARE_SUM_NOT_EQUAL_COUNT", inputFileSearchBean);
    }

    public int getCountBankCompareWaitDealCount(InputFileSearchBean inputFileSearchBean) {
        return (Integer) this.sqlMapClientTemplate.queryForObject("BANK_COMPARE_WAITE_DEAL_COUNT",
            inputFileSearchBean);
    }

}
