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
     * �ļ����α��ѯ���u
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
     * �õ������ļ����α�seq��
     * @return
     * @throws Exception
     */
    public Long getInputFileSeq() {
        return (Long) this.sqlMapClientTemplate.queryForObject("getInputFileSeq");

    }

    /**
     * �޸ģ����гɹ���ϵͳ�ɹ������ǽ��ȵĴ���sql
     * @param inputFileSearchBean
     */
    public void updateBankCompareEqualSucc(InputFileSearchBean inputFileSearchBean) {
        this.sqlMapClientTemplate.update("BANK_COMPARE_UPDATE_EQUAL_SUCC", inputFileSearchBean);
    }

    /**
     * �޸ģ����гɹ���ϵͳδ�ɹ��Ĵ���sql 
     * @param inputFileSearchBean
     */
    public void updateBankCompareNotEqualSucc(InputFileSearchBean inputFileSearchBean) {
        this.sqlMapClientTemplate.update("BANK_COMPARE_UPDATE_NOT_EQUAL_SUCC", inputFileSearchBean);
    }

    /**
     * �޸Ķ����ļ�
     * @param epInputFile
     */
    public void editInputFile(InputFile epInputFile) {
        this.sqlMapClientTemplate.update("editInputFile", epInputFile);
    }

    /**
     * ͳ�����߳ɹ�������
     * @param inputFileSearchBean
     * @return
     */
    public int getCountBankCompareEqualSucc(InputFileSearchBean inputFileSearchBean) {
        return (Integer) this.sqlMapClientTemplate.queryForObject("BANK_COMPARE_SUM_EQUAL_SUCC",
            inputFileSearchBean);
    }

    /**
     * ͳ�����ߴ�����ͽ�������
     * @param inputFileSearchBean
     * @return
     */
    public int getCountBankCompareNotEqualSucc(InputFileSearchBean inputFileSearchBean) {
        return (Integer) this.sqlMapClientTemplate.queryForObject(
            "BANK_COMPARE_SUM_NOT_EQUAL_SUCC", inputFileSearchBean);
    }

    /**
     * ͳ�����߳ɹ�������(����)
     * @param inputFileSearchBean
     * @return
     */
    public int getCountTerminalCompareEqualSucc(InputFileSearchBean inputFileSearchBean) {
        return (Integer) this.sqlMapClientTemplate.queryForObject(
            "TERMINAL_COMPARE_SUM_EQUAL_SUCC", inputFileSearchBean);
    }

    /**
     * ͳ�����ߴ���������(����)
     * @param inputFileSearchBean
     * @return
     */
    public int getCountTerminalCompareNotEqualSucc(InputFileSearchBean inputFileSearchBean) {
        return (Integer) this.sqlMapClientTemplate.queryForObject(
            "TERMINAL_COMPARE_SUM_NOT_EQUAL_SUCC", inputFileSearchBean);
    }

    /**
     * �޸ģ�����ɹ���ϵͳδ�ɹ��Ĵ���sql
     * @param inputFileSearchBean
     */
    public void updateTermianlCompareNotEqualSucc(InputFileSearchBean inputFileSearchBean) {
        this.sqlMapClientTemplate.update("TERMINAL_COMPARE_UPDATE_NOT_EQUAL_SUCC",
            inputFileSearchBean);
    }

    /**
     * ͳ�����ߴ���������(����)
     * @param inputFileSearchBean
     * @return
     */
    public int getCountBankCompareWaitDeal(InputFileSearchBean inputFileSearchBean) {
        return (Integer) this.sqlMapClientTemplate.queryForObject("BANK_COMPARE_SUM_WAIT_DEAL",
            inputFileSearchBean);
    }

    /**
     * ͳ�����ߴ���������(���䣬����)
     * @param inputFileSearchBean
     * @return
     */
    public int getCountTerminalCompareWaitDeal(InputFileSearchBean inputFileSearchBean) {
        return (Integer) this.sqlMapClientTemplate.queryForObject("TERMINAL_COMPARE_SUM_WAIT_DEAL",
            inputFileSearchBean);
    }

    /**
     * �õ����еĴ���������,IS_SUCCEED = 'Y' ,FLAG_COMPARE = 'F'
     * @param inputFileSearchBean
     * @return
     */
    public List<BaseBankPayOnline> getBankPayOnLineList(InputFileSearchBean inputFileSearchBean) {
        return this.sqlMapClientTemplate.queryForList("GET_BANK_PAYONLINE_WAIT_DEAL",
            inputFileSearchBean);
    }

    /**
     * ��Ӷ����ļ����μ�¼
     * @param inputFileu
     */
    public void addInputFile(InputFile inputFileu) {
        this.sqlMapClientTemplate.insert("addInputFile", inputFileu);
    }

    /**
     * ����idɾ�������ļ����μ�¼
     * @param batchId
     */
    public void removeInputFile(Long batchId) {
        this.sqlMapClientTemplate.delete("removeInputFile", batchId);
    }

    /**
     * ����id�õ������ļ���¼
     * @param batchId
     * @return
     */
    public InputFile getInputFile(Long batchId) {
        return (InputFile) this.sqlMapClientTemplate.queryForObject("getInputFile", batchId);
    }

    /**
     * �õ�List
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
