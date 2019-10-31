package com.huaixuan.network.biz.dao.admin;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.admin.Resources;


public interface ResourcesDao {

 	void addResources(Resources resources) throws Exception;

 	void editResources(Resources resources) throws Exception;

 	void removeResources(Long resourcesId) throws Exception;

 	Resources getResources(Long resourcesId) throws Exception;

 	List <Resources> getResourcess() throws Exception;
 	
 	List<Resources> getResourcesByMap(Map map);

 	/**
 	 * @param type�������Ͳ�ѯ���
 	 * @return
 	 * @throws Exception
 	 */
 	List<Resources> getResourcesByType(String type)throws Exception;

 	/**�������ͣ����Ʋ�ѯ���
 	 * @param type
 	 * @param name
 	 * @return
 	 * @throws Exception
 	 */
 	Resources getResourcesByTypeAndName(String type,String name)throws Exception;
 	
}
