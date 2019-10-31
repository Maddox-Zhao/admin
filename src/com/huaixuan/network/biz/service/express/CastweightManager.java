package com.huaixuan.network.biz.service.express;

 import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.express.Castweight;
import com.huaixuan.network.biz.domain.express.Express;
import com.huaixuan.network.biz.query.QueryPage;

 /**
  * �����Զ����(bibleUtil auto code generation)
  * @version 3.2.0
  */
 public interface CastweightManager {
	 	/* @interface model: ���һ��Castweight��¼ */
	 	public void addCastweight(Castweight castweight);

	 	/* @interface model: ����һ��Castweight��¼ */
	 	public void editCastweight(Castweight castweight);

	 	/* @interface model: ɾ��һ��Castweight��¼ */
	 	public void removeCastweight(Long castweightId);

	 	/* @interface model: ��ѯһ��Castweight���,����Castweight���� */
	 	public Castweight getCastweight(Long castweightId);

	 	/* @interface model: 根据商品ID和物流公司ID查询记录*/
	 	public Castweight getCastweightByGoodsIdAndExpessId(Long goodsId,Long expressId);

	 	/* @interface model: ��ѯ����Castweight���,����Castweight����ļ��� */
	 	public List<Castweight> getCastweights();

 	/**
 	 *����������������
 	 *@param parMap Map
 	 *@return int
 	 *@author chenhang 2011/02/16
 	 */
 	int getCastWeightCount(Map<String,String> parMap);

 	/**
 	 *������������
 	 *@param parMap Map
 	 *@param page Page
 	 *@return List
 	 *@author chenhang 2011/02/16
 	 */
 	QueryPage getCastWeightList(Map<String,String> parMap, int currentPage, int pageSize, boolean isPage);

 	/**
 	 * ����������Ʒ�ļ�������
 	 * @author chenhang 2011/02/16
 	 */
 	public void addCastweights(Map<String,String> parMap,String[] checkStatus,List<Express> expressInfoList);

 	/**
 	 * ����������Ʒ�ļ�������
 	 * @author chenhang 2011/02/16
 	 */
 	public void modifyCastweights(Map<String,String> parMap,String[] checkStatus,List<Express> expressInfoList);
 }
