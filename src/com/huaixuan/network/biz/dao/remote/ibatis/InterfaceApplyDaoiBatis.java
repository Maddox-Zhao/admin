package com.huaixuan.network.biz.dao.remote.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.remote.InterfaceApplyDao;
import com.huaixuan.network.biz.domain.remote.InterfaceApply;

/**
 *
 * @version 3.2.0
 */
@Repository("interfaceApplyDao")
public class InterfaceApplyDaoiBatis implements InterfaceApplyDao {

	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	/* @model: 锟斤拷锟揭伙拷锟�terfaceApply锟斤拷录 */
	public void addInterfaceApply(InterfaceApply interfaceApply)
			throws Exception {
		this.sqlMapClient.insert("addInterfaceApply", interfaceApply);
	}

	/* @model: 锟斤拷锟斤拷且�斤拷InterfaceApply锟斤拷录 */
	public void editInterfaceApply(InterfaceApply interfaceApply)
			throws Exception {
		this.sqlMapClient.update("editInterfaceApply", interfaceApply);
	}

	public void editGmtSync(InterfaceApply interfaceApply) throws Exception {
		this.sqlMapClient.update("editGmtSync", interfaceApply);
	}

	/* @model: 删锟斤拷且�斤拷InterfaceApply锟斤拷录 */
	public void removeInterfaceApply(Long interfaceApplyId) throws Exception {
		this.sqlMapClient.delete("removeInterfaceApply", interfaceApplyId);
	}

	/* @model: 锟斤拷询且�斤拷InterfaceApply锟斤拷锟��,锟斤拷锟斤拷InterfaceApply锟斤拷锟斤拷 */
	public InterfaceApply getInterfaceApply(Long interfaceApplyId)
			throws Exception {
		return (InterfaceApply) this.sqlMapClient.queryForObject(
				"getInterfaceApply", interfaceApplyId);
	}

	public InterfaceApply getInterfaceApplyByInterfaceExpressCode(
			String interfaceExpressCode) throws Exception {
		return (InterfaceApply) this.sqlMapClient
				.queryForObject("getInterfaceApplyByInterfaceExpressCode",
						interfaceExpressCode);
	}

	public InterfaceApply getInterfaceApplyByUserId(Long userId, String type)
			throws Exception {
		try {
			Map parMap = new HashMap();
			parMap.put("userId", userId);
			parMap.put("type", type);
			return (InterfaceApply) this.sqlMapClient.queryForObject(
					"getInterfaceApplyByUserId", parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/* @model: 锟斤拷询锟斤拷锟斤拷InterfaceApply锟斤拷锟��,锟斤拷锟斤拷InterfaceApply锟斤拷锟斤拷募锟斤拷锟��*/
	public List<InterfaceApply> getInterfaceApplys() throws Exception {
		return this.sqlMapClient.queryForList("getInterfaceApplys", null);
	}

	public int getCountByMap(Map parMap) throws Exception {
		return (Integer) this.sqlMapClient.queryForObject("getCountByMap",
				parMap);
	}

	public List<InterfaceApply> getListByMap(Map parMap) throws Exception {
		return this.sqlMapClient.queryForList("getListByMap", parMap);
	}

	public void editInterfaceApplyStatus(Long id, String status)
			throws Exception {
		InterfaceApply interfaceApply = new InterfaceApply();
		interfaceApply.setId(id);
		interfaceApply.setStatus(status);
		this.sqlMapClient.update("editInterfaceApplyStatus", interfaceApply);
	}

}
