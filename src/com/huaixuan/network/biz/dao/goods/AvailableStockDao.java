package com.huaixuan.network.biz.dao.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.AvailableStock;

public interface AvailableStockDao {
    /**新增
     * @param availableStock
     * @return
     */
    long addAvailableStock(AvailableStock availableStock) ;
    
    /**新增
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
    

    /**更新
     * @param availableStock
     */
    void updateAvailableStock(AvailableStock availableStock);

    /**更新可用库存的数量
     * @param goodsInstanceId 产品ID
     * @param depFirstName //一级仓库ID
     * 产品ID 和一级仓库ID 可以确定唯一 一条记录
     */
    void updateAvailableStockGoodsNumber(Long goodsInstanceId,Long depFirstName,Long goodsNumber);


    /**通过ID得到唯一的可用库存记录
     * @param id
     * @return
     */
    AvailableStock getAvailableStockById(Long id);


    /**通过goodsInstanceId depFirstName 得到唯一的可用库存记录
     * @param goodsInstanceId
     * @param depFirstName
     * @return
     */
    AvailableStock getAvailableStock(Long goodsInstanceId,Long depFirstName);


    /**通过goodsInstanceId  得到不同仓库的记录
     * @param goodsInstanceId
     * @param depFirstName
     * @return
     */
    List<AvailableStock> getAvailableStockListByInstanceId(Long goodsInstanceId);


    /**通过商品ID查询 商品所在仓库的数量
     * @param goodsId
     * @return
     */
    List<AvailableStock> getAvailableStockGroupByGoodsId(Long goodsId);

    /**
     * 通过商品ID得到商品的可用库存
     * @param goodsId
     * @return
     */
    long getSumGoodsNumberByGoodsId(Long goodsId);
}
