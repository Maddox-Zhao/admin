package com.huaixuan.network.biz.dao.goods.ibatis;

 import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.PromationModelDao;
import com.huaixuan.network.biz.domain.goods.PromationModel;


@Repository("promationModelDao")
 public class PromationModelDaoiBatis implements PromationModelDao{

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	 public void addPromationModel(PromationModel promationModel)throws Exception{
		 this.sqlMapClient.insert("addPromationModel", promationModel);
	 }

	 public void editPromationModel(PromationModel promationModel)throws Exception{
		 this.sqlMapClient.update("editPromationModel", promationModel);
	 }

	 public void removePromationModel(Long promationModelId)throws Exception{
		 this.sqlMapClient.delete("removePromationModel",promationModelId);
	 }

	 public PromationModel getPromationModel(Long promationModelId)throws Exception{
		 return (PromationModel)this.sqlMapClient.queryForObject("getPromationModel", promationModelId);
	 }

	 public List<PromationModel> getPromationModels()throws Exception{
		 return this.sqlMapClient.queryForList("getPromationModels", null);
	 }

   public PromationModel getPromationModelByCode(String Code) throws Exception {
       return (PromationModel)this.sqlMapClient.queryForObject("getPromationModelByCode", Code);
   }
 }
