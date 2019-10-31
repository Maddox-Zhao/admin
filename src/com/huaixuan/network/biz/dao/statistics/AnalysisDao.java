package com.huaixuan.network.biz.dao.statistics;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.StoreDay;
import com.huaixuan.network.biz.domain.report.FCSet;
import com.huaixuan.network.biz.domain.statistics.CatalogOrderAnalysis;
import com.huaixuan.network.biz.domain.statistics.GoodsAnalysis;
import com.huaixuan.network.biz.domain.statistics.SaleAnalysis;
import com.huaixuan.network.biz.domain.statistics.SaleAnalysisTwo;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.common.util.Page;

public interface AnalysisDao {

	public List<FCSet> getSingleOrderAnalysis(Map parMap);

	/**
	 * һ��Ŀ¼���۽��ͳ��
	 * 
	 * @param parMap
	 * @return @
	 */
	public Map getCatalogAnalysis(Map parMap);

	/**
	 * ��Ŀ����ͳ��
	 * 
	 * @Title: getCatalogOrderAnalysis
	 * @Description: ��Ŀ����ͳ��
	 * @param @param parMap
	 * @param @return
	 * @return Map
	 * @throws
	 */
	public List<CatalogOrderAnalysis> getCatalogOrderAnalysis(Map parMap);

	/**
	 * ��Ŀ����ͳ��
	 * 
	 * @Title: getCatalogOrderAnalysis
	 * @Description: ��Ŀ����ͳ��
	 * @param @param parMap
	 * @param @return
	 * @return Map
	 * @throws
	 */
	public int getCatalogOrderAnalysisCount(Map parMap);

	int getSaleAnalysisCount(Map parMap);

	List<SaleAnalysisTwo> getSaleAnalysis(Map parMap);

	// SaleAnalysis getSaleAnalysis(Map parMap);

	/**
	 * ������Ʒ
	 * 
	 * @param parMap
	 * @param page
	 * @return
	 */
	// List<GoodsAnalysis> getHotSalsGoods(Map parMap, Page page) ;

	int getHotSalsGoodsCount(Map parMap);

	Map getHotSalesGoodsSum(Map parMap);

	Map getSaleAnalysisTradeOutPriceSum(Map parMap);

	Map getSaleAnalysisDetailSum(Map parMap);

	/**
	 * ������Ʒ
	 * 
	 * @param parMap
	 * @param page
	 * @return
	 */
	// List<GoodsAnalysis> getSlowSalsGoods(Map parMap, Page page) ;

	int getSlowSalsGoodsCount(Map parMap);

	Map getSlowSalesGoodsSum(Map parMap);

	/**
	 * ��Ŀ���ۻ���
	 * 
	 * @param parMap
	 * @param page
	 * @return
	 */
	// List<GoodsAnalysis> getCatSalesGoods(Map parMap, Page page) ;

	int getCatSalesGoodsCount(Map parMap);

	Map getCatSalesGoodsSum(Map parMap);

	/**
	 * �˻���Ʒ��ѯ
	 * 
	 * @param parMap
	 * @param page
	 * @return
	 */
	QueryPage getAnalysisRefundGoods(Map<String, Object> parMap, Page page);

	int getAnalysisRefundGoodsCount(Map parMap);

	int getSaleAnalysisDetailCount(Map parMap);

	/**
	 * ������ϸ��ѯ
	 * 
	 * @param parMap
	 * @param page
	 * @return
	 */
	List<SaleAnalysis> getSaleAnalysisDetail(Map parMap);

	/**
	 * �ڿ��Ʒͳ������
	 * 
	 * @param parMap
	 * @return
	 */
	int getGoodsInStorageCount(Map parMap);

	/**
	 * �ڿ��Ʒͳ���б�
	 * 
	 * @param parMap
	 * @param page
	 * @return
	 */
	List<SaleAnalysis> getGoodsInStorage(Map parMap);

	/**
	 * ������ϸ��ȡ�˻����
	 * 
	 * @param parMap
	 * @return
	 */
	Map getRefundAnalysisDetailSum(Map parMap);

	/**
	 * ������ϸ��ȡ�˻�����
	 * 
	 * @param parMap
	 * @return
	 */
	int getRefundAnalysisDetailCount(Map parMap);

	/**
	 * ������ϸ��ȡ�˻���¼
	 * 
	 * @param parMap
	 * @param page
	 * @return
	 */
	List<SaleAnalysis> getRefundAnalysisDetail(Map parMap);

	/**
	 * ����ͳ�ƻ�ȡ�˻�����
	 * 
	 * @param parMap
	 * @return
	 */
	int getRefundAnalysisCount(Map parMap);

	/**
	 * ����ͳ�ƻ�ȡ�˻���¼
	 * 
	 * @param parMap
	 * @return
	 */
	List<SaleAnalysisTwo> getRefundAnalysis(Map parMap);

	/**
	 * ����ͳ�ƻ�ȡ�˻����
	 * 
	 * @param parMap
	 * @return
	 */
	Map getRefundAnalysisTradeOutPriceSum(Map parMap);

	public List<GoodsAnalysis> getHotSalsGoods(Map parMap);

	public List<GoodsAnalysis> getSlowSalsGoods(Map parMap);

	public List<GoodsAnalysis> getCatSalesGoods(Map parMap);
	
    /**
     * ��ѯ����ͳ�ƣ���Ʒ�ֿ⣩���۵�����
     * @param parMap
     * @return int
     * @author chenyan 2011/03/03
     */
    int getSaleAnalysisDepositoryCount(Map parMap) throws Exception;
    
    /**
     * ��ѯ����ͳ�ƣ���Ʒ�ֿ⣩�˻�������
     * @param parMap
     * @return int
     * @author chenyan 2011/03/03
     */
    int getRefundAnalysisDepositoryCount(Map parMap);
    
    /**
     * ��ѯ����ͳ�ƣ���Ʒ�ֿ⣩���۵ļ۸�����
     * @param parMap
     * @return int
     * @author chenyan 2011/03/03
     */
    Map getSaleAnalysisDepositoryTradeOutPriceSum(Map parMap);
    
    /**
     * ��ѯ����ͳ�ƣ���Ʒ�ֿ⣩���۵������б�
     * @param parMap Map
     * @return List
     * @author chenyan 2011/03/03
     */
    List<SaleAnalysisTwo> getSaleAnalysisDepository(Map parMap) throws Exception;
    
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
    
    public StoreDay getStoreDay(Map parMap) throws Exception;
}
