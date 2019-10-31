package com.huaixuan.network.biz.service.counter;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.counter.InputFile;
import com.huaixuan.network.biz.domain.counter.InputFileSearchBean;
import com.huaixuan.network.biz.query.QueryPage;

/**  
 * @version 3.2.0  
 */
public interface InputFileManager {

    /**
     * �õ������ļ����α�seq��ֵ
     * @return Long
     */
    public Long getInputFileSeq();

    /**
     * �ļ����α��ѯ��ҳ
     * @param parameterMap
     * @param page
     * @return
     */
    //    public PageUtil<InputFile> getInputFileByParameterMap(Map<String, Object> parameterMap,
    //                                                          Page page);

    QueryPage getInputFileByParameterMapQuery(Map parameterMap, int currPage, int pageSize);

    /**
     * �ļ����˴���
     * @param inputFileSearchBean
     * @return
     */
    public InputFileSearchBean doCompareFile(InputFileSearchBean inputFileSearchBean);

    /**
     * ͳ�����ߴ���������(����)
     * @param inputFileSearchBean
     * @return
     */
    public InputFileSearchBean getCountBankCompareWaitDeal(InputFileSearchBean inputFileSearchBean);

    /**
     * �����������ݻָ�
     * @param inputFileSearchBean
     * @return
     */
    public InputFileSearchBean doRecoverBankFile(InputFileSearchBean inputFileSearchBean);

    /**
     * ��Ӷ����ļ���¼
     * @param inputFileu
     */
    public void addInputFile(InputFile inputFileu);

    /**
     * �༭�����ļ���¼
     * @param inputFileu
     */
    public void editInputFile(InputFile inputFileu);

    /**
     * ����batchIdɾ����Ӧ�ļ���¼
     * @param batchId
     */
    public void removeInputFile(Long batchId);

    /**
     * ����batchId�õ��ļ���¼
     * @param batchId
     * @return
     */
    public InputFile getInputFile(Long batchId);

    /**
     * �õ���Ӧ��List��¼
     * @return
     */
    public List<InputFile> getInputFiles();
}
