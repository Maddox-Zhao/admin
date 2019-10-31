package com.huaixuan.network.biz.service.goods;

 import java.util.List;

import com.huaixuan.network.biz.domain.goods.PromationModel;


 public interface PromationModelManager {
	 	/* @interface model: PromationModel */ 
	 	public void addPromationModel(PromationModel promationModel); 
	  
	 	/* @interface model: PromationModel */ 
	 	public void editPromationModel(PromationModel promationModel); 
	  
	 	/* @interface model: PromationModel */ 
	 	public void removePromationModel(Long promationModelId); 
	  
	 	/* @interface model: PromationModel,PromationModel */ 
	 	public PromationModel getPromationModel(Long promationModelId); 
	  
	 	/* @interface model: PromationModel,PromationModel */ 
	 	public List<PromationModel> getPromationModels(); 
 }
