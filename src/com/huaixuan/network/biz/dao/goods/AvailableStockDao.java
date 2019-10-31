package com.huaixuan.network.biz.dao.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.AvailableStock;

public interface AvailableStockDao {
    /**����
     * @param availableStock
     * @return
     */
    long addAvailableStock(AvailableStock availableStock) ;
    
    /**����
     * @param availableStock
     * @return
     */
    long addClientAvailableStock(AvailableStock availableStock) ;
    
    
    
    /**
     * @param availableStock
     * @return
     */
    public AvailableStock getAvailableStockByPramas(Map<String, Object> pramas) ;
    
    public void updateAvaiStoEsNumByPramas(Map<String,Object> pramas);
    
    public void updateAvaiStoEsNumZero(Map<String,Object> pramas);
    

    /**����
     * @param availableStock
     */
    void updateAvailableStock(AvailableStock availableStock);

    /**���¿��ÿ�������
     * @param goodsInstanceId ��ƷID
     * @param depFirstName //һ���ֿ�ID
     * ��ƷID ��һ���ֿ�ID ����ȷ��Ψһ һ����¼
     */
    void updateAvailableStockGoodsNumber(Long goodsInstanceId,Long depFirstName,Long goodsNumber);


    /**ͨ��ID�õ�Ψһ�Ŀ��ÿ���¼
     * @param id
     * @return
     */
    AvailableStock getAvailableStockById(Long id);


    /**ͨ��goodsInstanceId depFirstName �õ�Ψһ�Ŀ��ÿ���¼
     * @param goodsInstanceId
     * @param depFirstName
     * @return
     */
    AvailableStock getAvailableStock(Long goodsInstanceId,Long depFirstName);


    /**ͨ��goodsInstanceId  �õ���ͬ�ֿ�ļ�¼
     * @param goodsInstanceId
     * @param depFirstName
     * @return
     */
    List<AvailableStock> getAvailableStockListByInstanceId(Long goodsInstanceId);


    /**ͨ����ƷID��ѯ ��Ʒ���ڲֿ������
     * @param goodsId
     * @return
     */
    List<AvailableStock> getAvailableStockGroupByGoodsId(Long goodsId);

    /**
     * ͨ����ƷID�õ���Ʒ�Ŀ��ÿ��
     * @param goodsId
     * @return
     */
    long getSumGoodsNumberByGoodsId(Long goodsId);
}
