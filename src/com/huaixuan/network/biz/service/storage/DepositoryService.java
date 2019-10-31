/**
 * @Title: DepositoryService.java
 * @Package com.huaixuan.network.biz.service.storage
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 下午07:15:53
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
 * @date 2011-3-4 下午07:15:53
 *
 */
public interface DepositoryService {
 	/**
 	 * 新增仓库信息
 	 * @param depository
 	 * @return Long
 	 */
 	public Long addDepository(Depository depository);

 	/**
 	 * 编辑仓库信息
 	 * @param depository
 	 */
 	public void editDepository(Depository depository);

 	/**
 	 * 删除仓库信息
 	 * @param depositoryId
 	 */
 	public void removeDepository(Long depositoryId);

 	/**
 	 * 根据仓库ID找对应的仓库信息
 	 * @param depositoryId
 	 * @return Depository
 	 */
 	public Depository getDepository(Long depositoryId);

 	/**
 	 * 获取所有有效的仓库信息
 	 * @return List<Depository>
 	 */
 	public List<Depository> getDepositorys();

 	/**
 	 * 根据条件获取仓库信息
 	 * @param parameterMap
 	 * @param page
 	 * @return List<Depository>
 	 */
// 	public List<Depository> getDepositorysByParMap(Map parameterMap, Page page);
 	public QueryPage getDepositorysByParMap(DepositoryQuery query, int currPage, int pageSize, boolean isPage);

 	/**
 	 * 根据条件获取仓库数量
 	 * @param parMap
 	 * @return
 	 */
 	public int getCountByParMap(Map parMap);

 	/**
 	 * 根据一级仓库Id获取全部的仓库列表
 	 * @param depfirstId
 	 * @return
 	 */
 	public List<Depository> getDeplistByFirstDepId(Long depfirstId);

 	List<Depository> getDepositorysByDepFirstId(Map<String,Object> paramMap);

 	public List<Depository> getRightDeplistByFirstDepId(Long depfirstId);
 }
