package com.huaixuan.network.biz.dao.goods;

 import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.Promation;
import com.huaixuan.network.biz.domain.goods.PromationGive;
import com.huaixuan.network.biz.domain.goods.PromationVO;
import com.huaixuan.network.biz.query.QueryPage;


 public interface PromationDao {
	 	/* @interface model: Promation */
	 	long addPromation(Promation promation) throws Exception;

	 	/* @interface model: Promation */
	 	void editPromation(Promation promation) throws Exception;

	 	/* @interface model: Promation */
	 	void removePromation(Long promationId) throws Exception;

	 	/* @interface model: Promation,Promation */
	 	Promation getPromation(Long promationId) throws Exception;

	 	/*
	     * 根据套餐类型查询有效的套餐记录
	     * @param context
	     * @return
	     */
	 	public Promation getPromationByModeCode(String modeCode) throws Exception;
	 	
	 	/* @interface model: Promation,Promation */
	 	List<Promation> getPromationByName(String name) throws Exception;

	    /* @interface model: Promation,Promation */
	 	List <Promation> getPromations() throws Exception;

	 	List<Promation> getPromationsByIds(List ids);

	 	/**
	 	 * 根据参数，获取优惠列表
	 	 * @param map
	 	 * @return
	 	 * @throws Exception
	 	 */

	 	List<PromationVO> getPromationsByParams(Map map)throws Exception;
	 	/**
	     * 根据分页和相关信息查询PromationList
	     * 使用范围在Promation查询
	     * @param context
	     * @return List<Promation>
	     */

//	 	List<Promation> getPromationsByPage(Map<String, Object> conditions,Page page) throws Exception;


	 	/**
	 	 * 动态修改参数
	 	 * @param promation
	 	 * @throws Exception
	 	 */
	 	void editPromationByDynamic(Promation promation) throws Exception;


	 	List<PromationVO> getGivePromationList(Map<String, Object> params);
	 	/**
	     * 定时器自动取消冻结
	     *
	     * @param promationId
	     * @param promContext
	     * @return
	     */
	    void autoCanelFreeze();

	    /**
	     * 获取全部有用的买就增套餐
	     * @return
	     */
	    List<PromationGive> getNewPromationVOListGive(String nowDate);
	    
	    /**
	     * 获取当前商品的买就增套餐
	     * @param promationId
	     * @return
	     */
	    List<PromationGive> getGivePromation(Long promationId);
	    
	    /**
	     * 获取满就送的套餐集合
	     * @param scope
	     * @return
	     */
	    List<Promation> getFullGivePromationList(Map parMap);
	    
	    /**
	     * 获取套餐列表数量
	     * @param parMap
	     * @return
	     */
	    Integer getPromationListByConditionWithPageCount(Map parMap);
	    
	    /**
	     * 获取套餐列表
	     * @param parMap
	     * @return
	     */
	    List<Promation> getPromationListByConditionWithPage(Map parMap);
 }
