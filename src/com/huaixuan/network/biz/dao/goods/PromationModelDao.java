package com.huaixuan.network.biz.dao.goods;

 import java.util.List;

import com.huaixuan.network.biz.domain.goods.PromationModel;


 public interface PromationModelDao {

	 	/* @interface model: PromationModel */
	 	void addPromationModel(PromationModel promationModel) throws Exception;

	 	/* @interface model: PromationModel */
	 	void editPromationModel(PromationModel promationModel) throws Exception;

	 	/* @interface model: PromationModel */
	 	void removePromationModel(Long promationModelId) throws Exception;

	 	/* @interface model: PromationModel,PromationModel */
	 	PromationModel getPromationModel(Long promationModelId) throws Exception;

	 	/* @interface model: PromationModel,PromationModel */
	 	List <PromationModel> getPromationModels() throws Exception;

	 	/**
	 	 * ����CODE��ȡ�Ż�ģ������
	 	 * @param Code
	 	 * @return
	 	 * @throws Exception
	 	 */
	 	PromationModel getPromationModelByCode(String Code) throws Exception;

 }
