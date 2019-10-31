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
     * �õ������ļ����α�seq��
     * @return
     * @throws Exception
     */
    public Long getInputFileSeq();

    /**
     * �ļ����α��ѯ���u
     * @param parameterMap
     * @param page
     * @return
     */
    //    PageUtil<InputFile> getInputFileByParameterMap(Map<String, Object> parameterMap, Page page);

    public int getInputFileByParameterMapCount(Map parameterMap);

    public List<InputFile> getInputFileByParameterMap(Map parameterMap);

    /**
     * �޸ģ����гɹ���ϵͳ�ɹ������ǽ��ȵĴ���sql
     * @param inputFileSearchBean
     */
    void updateBankCompareEqualSucc(InputFileSearchBean inputFileSearchBean);

    /**
     * �޸ģ����гɹ���ϵͳδ�ɹ��Ĵ���sql 
     * @param inputFileSearchBean
     */
    void updateBankCompareNotEqualSucc(InputFileSearchBean inputFileSearchBean);

    /**
     * �޸Ķ����ļ�
     * @param epInputFile
     */
    void editInputFile(InputFile epInputFile);

    /**
     * ͳ�����߳ɹ�������(����)
     * @param inputFileSearchBean
     * @return
     */
    int getCountBankCompareEqualSucc(InputFileSearchBean inputFileSearchBean);

    /**
     * ͳ�����ߴ�����ͽ�������(����)
     * @param inputFileSearchBean
     * @return
     */
    int getCountBankCompareNotEqualSucc(InputFileSearchBean inputFileSearchBean);

    /**
     * ͳ�����ߴ���������(����)
     * @param inputFileSearchBean
     * @return
     */
    int getCountBankCompareWaitDealCount(InputFileSearchBean inputFileSearchBean);

    /**
     * ͳ�ƽ�������(����)
     * @param inputFileSearchBean
     * @return
     */
    int getCountBankCompareNotEqualCount(InputFileSearchBean inputFileSearchBean);

    /**
     * ͳ�����߳ɹ�������(����)
     * @param inputFileSearchBean
     * @return
     */
    int getCountTerminalCompareEqualSucc(InputFileSearchBean inputFileSearchBean);

    /**
     * ͳ�����ߴ���������(����)
     * @param inputFileSearchBean
     * @return
     */
    int getCountTerminalCompareNotEqualSucc(InputFileSearchBean inputFileSearchBean);

    /**
     * �޸ģ�����ɹ���ϵͳδ�ɹ��Ĵ���sql
     * @param inputFileSearchBean
     */
    void updateTermianlCompareNotEqualSucc(InputFileSearchBean inputFileSearchBean);

    /**
     * ͳ�����ߴ���������(����)
     * @param inputFileSearchBean
     * @return
     */
    int getCountBankCompareWaitDeal(InputFileSearchBean inputFileSearchBean);

    /**
     * ͳ�����ߴ���������(���䣬����)
     * @param inputFileSearchBean
     * @return
     */
    int getCountTerminalCompareWaitDeal(InputFileSearchBean inputFileSearchBean);

    /**
     * �õ����еĴ���������,IS_SUCCEED = 'Y' ,FLAG_COMPARE = 'F'
     * @param inputFileSearchBean
     * @return
     */
    List<BaseBankPayOnline> getBankPayOnLineList(InputFileSearchBean inputFileSearchBean);

    /**
     * ��Ӷ����ļ����μ�¼
     * @param inputFileu
     */
    void addInputFile(InputFile inputFileu);

    /**
     * ����idɾ�������ļ����μ�¼
     * @param batchId
     */
    void removeInputFile(Long batchId);

    /**
     * ����id�õ������ļ���¼
     * @param batchId
     * @return
     */
    InputFile getInputFile(Long batchId);

    /**
     * �õ�List
     * @return
     */
    List<InputFile> getInputFiles();

    /**
     * ��ѯ�������ظ������ж�����
     */
    List<BankCompareItem> getBankCompareItemBankBillNO(InputFile inputFile);

    /**
     * ɾ���������ظ������ж�����
     */
    void deleteBankCompareItemBankBillNO(InputFile inputFile);
}
