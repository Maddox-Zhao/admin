package com.huaixuan.network.biz.service.statistics;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.statistics.SaleAnalysis;
import com.huaixuan.network.biz.domain.statistics.SaleAnalysisTwo;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.common.util.Page;

/**
 * ͳ�Ʊ���
 * @author yuancong
 * @version $Id: AnalysisManager.java,v 0.1 2009-3-30 ����05:18:16 yuancong Exp $
 */
public interface AnalysisManager {
    /**
     * ��ѯ����ͳ�Ʊ���
     * @return
     */
    public String getSingleOrderAnalysis(Map parMap);

    /**
     * ��ѯһ��Ŀ¼��Ʒ����ͳ�Ʊ���
     * @param parMap
     * @return
     */
    public String getCatalogAnalysis(Map parMap);

    /**
     * ��ѯ����ͳ�Ʊ���
     * @return
     */
    public String getMultiOrderAnalysis(Map parMap);
    
    
    public List<String> getStoreAnalysis(Map parMap);
    
    public List<String> getStoreAnalysisday(Map parMap);

    public int getSaleAnalysisCount(Map parMap);

    public Map getSaleAnalysisTradeOutPriceSum(Map parMap);

    /**
    * @Title: getCatalogOrderAnalysis
    * @Description: ��Ŀ����ͳ��
    * @param @param parMap
    * @param @return
    * @return Map
    * @throws
     */
    public QueryPage getCatalogOrderAnalysis(Map parMap, int currPage, int pageSize);

    public Map getSaleAnalysisDetailSum(Map parMap);

    public List<SaleAnalysisTwo> getSaleAnalysis(Map parMap);

    public int getSaleAnalysisDetailCount(Map parMap);

    /**
     * ��ѯ������ϸ
     * @param parMap
     * @param page
     * @return
     */
    public List<SaleAnalysis> getSaleAnalysisDetail(Map parMap);

    /**
     * ������Ʒ�б�
     * @param parMap
     * @param page
     * @return
     */
    public QueryPage getHotSalesGoods(Map parMap, int currPage, int pageSize);

    public int getHotSalesGoodsCount(Map parMap);

    public Map getHotSalesGoodsSum(Map parMap);

    /**
     * ���������Ʒ�б�
     * @param parMap
     * @param page
     * @return
     */
    public QueryPage getSlowSalesGoods(Map parMap, int currPage, int pageSize);

    public int getSlowSalesGoodsCount(Map parMap);

    public Map getSlowSalesGoodsSum(Map parMap);

    /**
     * ��Ŀ���ۻ����б�
     * @param parMap
     * @param page
     * @return
     */
    //    public List<GoodsAnalysis> getCatSalesGoods(Map parMap, Page page);

    public int getCatSalesGoodsCount(Map parMap);

    public Map getCatSalesGoodsSum(Map parMap);

    /**
     * �˻���Ʒ��ѯ
     * @param parMap
     * @param page
     * @return
     */
	public QueryPage getAnalysisRefundGoods(Map parMap, Page page);

    public int getAnalysisRefundGoodsCount(Map parMap);

    /**
     * �ڿ��Ʒͳ������
     * @param parMap
     * @return
     */
    public int getGoodsInStorageCount(Map parMap);

    /**
     * �ڿ��Ʒͳ���б�
     * @param parMap
     * @param page
     * @return
     */
    //    public List<SaleAnalysis> getGoodsInStorage(Map parMap, Page page);

    /**
     * ������ϸ��ȡ�˻����
     * @param parMap
     * @return
     */
    public Map getRefundAnalysisDetailSum(Map parMap);

    /**
     * ������ϸ��ȡ�˻�����
     * @param parMap
     * @return
     */
    public int getRefundAnalysisDetailCount(Map parMap);

    /**
     * ������ϸ��ȡ�˻���¼
     * @param parMap
     * @param page
     * @return
     */
    public List<SaleAnalysis> getRefundAnalysisDetail(Map parMap);

    /**
     * ����ͳ�ƻ�ȡ�˻�����
     * @param parMap
     * @return
     */
    public int getRefundAnalysisCount(Map parMap);

    /**
     * ����ͳ�ƻ�ȡ�˻����
     * @param parMap
     * @return
     */
    public Map getRefundAnalysisTradeOutPriceSum(Map parMap);

    /**
     * ����ͳ�ƻ�ȡ�˻���¼
     * @param parMap
     * @return
     */
    public List<SaleAnalysisTwo> getRefundAnalysis(Map parMap);

    public QueryPage getCatSalesGoods(Map parMap, int currPage, int pageSize);

    public QueryPage getGoodsInStorage(Map parMap, int currPage, int pageSize);

    public QueryPage getInOutStatReportListByMap(Map parMap, int currPage, int pageSize);
    
    /**
     * ��ѯ����ͳ�ƣ���Ʒ�ֿ⣩���۵�����
     * @param parMap
     * @return int
     * @author chenyan 2011/03/03
     */
    int getSaleAnalysisDepositoryCount(Map parMap);
    
    /**
     * ��ѯ����ͳ�ƣ���Ʒ�ֿ⣩�˻�������
     * @param parMap
     * @return int
     * @author chenyan 2011/03/03
     */
    int getRefundAnalysisDepositoryCount(Map parMap);
    
    /**
     * ��ѯ����ͳ�ƣ���Ʒ�ֿ⣩���ۼ۸��б�
     * @param parMap
     * @return int
     * @author chenyan 2011/03/03
     */
    Map getSaleAnalysisDepositoryTradeOutPriceSum(Map parMap);
    
    /**
     * ��ѯ����ͳ�ƣ���Ʒ�ֿ⣩���۵������б�
     * @param parMap
     * @return
     */
    List<SaleAnalysisTwo> getSaleAnalysisDepository(Map parMap);
    
    /**
     * ��ѯ����ͳ�ƣ���Ʒ�ֿ⣩�˻��۸��б�
     * @param parMap
     * @return int
     * @author chenyan 2011/03/03
     */
    Map getRefundAnalysisDepositoryTradeOutPriceSum(Map parMap);
    
    /**
     * ��ѯ����ͳ�ƣ���Ʒ�ֿ⣩�˻��������б�
     * @param parMap
     * @return int
     * @author chenyan 2011/03/03
     */
    List<SaleAnalysisTwo> getRefundAnalysisDepository(Map parMap);
}