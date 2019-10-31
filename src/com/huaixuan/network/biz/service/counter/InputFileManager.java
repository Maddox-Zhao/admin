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
     * 得到导入文件批次表seq的值
     * @return Long
     */
    public Long getInputFileSeq();

    /**
     * 文件批次表查询分页
     * @param parameterMap
     * @param page
     * @return
     */
    //    public PageUtil<InputFile> getInputFileByParameterMap(Map<String, Object> parameterMap,
    //                                                          Page page);

    QueryPage getInputFileByParameterMapQuery(Map parameterMap, int currPage, int pageSize);

    /**
     * 文件对账处理
     * @param inputFileSearchBean
     * @return
     */
    public InputFileSearchBean doCompareFile(InputFileSearchBean inputFileSearchBean);

    /**
     * 统计两边待处理条数(银行)
     * @param inputFileSearchBean
     * @return
     */
    public InputFileSearchBean getCountBankCompareWaitDeal(InputFileSearchBean inputFileSearchBean);

    /**
     * 银行意外数据恢复
     * @param inputFileSearchBean
     * @return
     */
    public InputFileSearchBean doRecoverBankFile(InputFileSearchBean inputFileSearchBean);

    /**
     * 添加对账文件记录
     * @param inputFileu
     */
    public void addInputFile(InputFile inputFileu);

    /**
     * 编辑对账文件记录
     * @param inputFileu
     */
    public void editInputFile(InputFile inputFileu);

    /**
     * 根据batchId删除对应文件记录
     * @param batchId
     */
    public void removeInputFile(Long batchId);

    /**
     * 根据batchId得到文件记录
     * @param batchId
     * @return
     */
    public InputFile getInputFile(Long batchId);

    /**
     * 得到对应的List记录
     * @return
     */
    public List<InputFile> getInputFiles();
}
