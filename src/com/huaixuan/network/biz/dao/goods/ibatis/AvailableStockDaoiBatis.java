package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.AvailableStockDao;
import com.huaixuan.network.biz.domain.goods.AvailableStock;

@Repository("availableStockDao")
public class AvailableStockDaoiBatis implements AvailableStockDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

    public long addAvailableStock(AvailableStock availableStock) {
        Object obj= this.sqlMapClient.insert("addAvailableStock",availableStock);
        if(null!=obj){
            return (Long) obj;
        }
        return 0;
      }
    
     public long addClientAvailableStock(AvailableStock availableStock) {
        Object obj= this.sqlMapClient.insert("addClientAvailableStock",availableStock);
        if(null!=obj){
            return (Long) obj;
        }
        return 0;
      }

     public AvailableStock getAvailableStockByPramas(Map<String, Object> pramas) {
         return (AvailableStock) this.sqlMapClient.queryForObject("gethxAvailableStockByPramas",pramas);
     }
     
     
     public void updateAvaiStoEsNumByPramas(Map<String, Object> pramas) {
         this.sqlMapClient.update("updateAvaiStoEsNumByPramas", pramas);
     }
     
     public void updateAvaiStoEsNumZero(Map<String, Object> pramas) {
         this.sqlMapClient.update("updateAvaiStoEsNumZero", pramas);
     }
     
      public AvailableStock getAvailableStock(Long goodsInstanceId, Long depFirstId) {
          Map<String,Object> paramMap=new HashMap<String,Object>();
          paramMap.put("goodsInstanceId", goodsInstanceId);
          paramMap.put("depFirstId", depFirstId);
          return (AvailableStock) this.sqlMapClient.queryForObject("getAvailableStockByInstance",paramMap);
      }

      public AvailableStock getAvailableStockById(Long id) {
          return (AvailableStock) this.sqlMapClient.queryForObject("getAvailableStockById",id);
      }

      public void updateAvailableStock(AvailableStock availableStock) {
          this.sqlMapClient.update("editAvailableStock", availableStock);

      }

      public void updateAvailableStockGoodsNumber(Long goodsInstanceId, Long depFirstId,Long goodsNumber) {
          Map<String,Object> paramMap=new HashMap<String,Object>();
          paramMap.put("goodsInstanceId", goodsInstanceId);
          paramMap.put("depFirstId", depFirstId);
          paramMap.put("goodsNumber", goodsNumber);
          this.sqlMapClient.update("editAvailableStockGoodsNumber", paramMap);

          // TODO Auto-generated method stub

      }

      @SuppressWarnings("unchecked")
      public List<AvailableStock> getAvailableStockListByInstanceId(Long goodsInstanceId) {
          Map<String,Object> paramMap=new HashMap<String,Object>();
          paramMap.put("goodsInstanceId", goodsInstanceId);
          return  this.sqlMapClient.queryForList("gethxAvailableStockByInstance",paramMap);
      }

      @SuppressWarnings("unchecked")
      public List<AvailableStock> getAvailableStockGroupByGoodsId(Long goodsId) {
          Map<String,Object> paramMap=new HashMap<String,Object>();
          paramMap.put("goodsId", goodsId);
          return  this.sqlMapClient.queryForList("getAvailableStockGroupByGoodsId",paramMap);
      }

	@Override
	public long getSumGoodsNumberByGoodsId(Long goodsId) {
		return (Long) this.sqlMapClient.queryForObject("getSumGoodsNumberByGoodsId",goodsId);
	}

}
