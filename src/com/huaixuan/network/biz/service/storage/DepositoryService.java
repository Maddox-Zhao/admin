/**
 * @Title: DepositoryService.java
 * @Package com.huaixuan.network.biz.service.storage
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 ����07:15:53
 * @version V1.0
 */
package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.query.DepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * @ClassName: DepositoryService
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 ����07:15:53
 *
 */
public interface DepositoryService {
 	/**
 	 * �����ֿ���Ϣ
 	 * @param depository
 	 * @return Long
 	 */
 	public Long addDepository(Depository depository);

 	/**
 	 * �༭�ֿ���Ϣ
 	 * @param depository
 	 */
 	public void editDepository(Depository depository);

 	/**
 	 * ɾ���ֿ���Ϣ
 	 * @param depositoryId
 	 */
 	public void removeDepository(Long depositoryId);

 	/**
 	 * ���ݲֿ�ID�Ҷ�Ӧ�Ĳֿ���Ϣ
 	 * @param depositoryId
 	 * @return Depository
 	 */
 	public Depository getDepository(Long depositoryId);

 	/**
 	 * ��ȡ������Ч�Ĳֿ���Ϣ
 	 * @return List<Depository>
 	 */
 	public List<Depository> getDepositorys();

 	/**
 	 * ����������ȡ�ֿ���Ϣ
 	 * @param parameterMap
 	 * @param page
 	 * @return List<Depository>
 	 */
// 	public List<Depository> getDepositorysByParMap(Map parameterMap, Page page);
 	public QueryPage getDepositorysByParMap(DepositoryQuery query, int currPage, int pageSize, boolean isPage);

 	/**
 	 * ����������ȡ�ֿ�����
 	 * @param parMap
 	 * @return
 	 */
 	public int getCountByParMap(Map parMap);

 	/**
 	 * ����һ���ֿ�Id��ȡȫ���Ĳֿ��б�
 	 * @param depfirstId
 	 * @return
 	 */
 	public List<Depository> getDeplistByFirstDepId(Long depfirstId);

 	List<Depository> getDepositorysByDepFirstId(Map<String,Object> paramMap);

 	public List<Depository> getRightDeplistByFirstDepId(Long depfirstId);
 }
