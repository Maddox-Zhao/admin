package com.huaixuan.network.biz.dao.user.iBatis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;

import com.huaixuan.network.biz.dao.user.UserDao;
import com.huaixuan.network.biz.domain.trade.TradeSalesCount;
import com.huaixuan.network.biz.domain.user.User;

@Repository("userDao")
public class UserDaoiBatis implements UserDao {
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		return sqlMapClient.queryForList("getUsers", null);
	}

	/**
	 * Convenience method to delete roles
	 *
	 * @param userId
	 *            the id of the user to delete
	 */
	public void deleteUserRoles(final Long userId) {
		sqlMapClient.delete("deleteUserRoles", userId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long saveUser(final User user) {
		prepareObjectForSaveOrUpdate(user);
		if (user.getId() == null) {
			Long id = (Long) sqlMapClient.insert("addUser", user);
			user.setId(id);
		} else {
			sqlMapClient.update("updateUser", user);
		}
		return user.getId();
	}

	protected void prepareObjectForSaveOrUpdate(Object o) {
		try {
			Field[] fieldlist = o.getClass().getDeclaredFields();
			for (Field fld : fieldlist) {
				String fieldName = fld.getName();
				if (fieldName.equals("version")) {
					Method setMethod = o.getClass().getMethod("setVersion",
							Integer.class);
					Object value = o.getClass()
							.getMethod("getVersion", (Class[]) null)
							.invoke(o, (Object[]) null);
					if (value == null) {
						setMethod.invoke(o, 1);
					} else {
						setMethod.invoke(o, (Integer) value + 1);
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Could not prepare '"
					+ ClassUtils.getShortName(o.getClass())
					+ "' for insert/update");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateUserGrade(Map<String, String> parMap) throws Exception {
		sqlMapClient.update("updateUserGrade", parMap);
	}

	public void updateGrade(Map<String, Object> parMap) throws Exception {
		this.sqlMapClient.update("updateGrade", parMap);
	}

	/**
	 * {@inheritDoc}
	 */
	public void remove(Long userId) {
		deleteUserRoles(userId);
		sqlMapClient.update("deleteUser", userId);
	}

	public User loadUserByUsername(String username) {
		return (User) sqlMapClient
				.queryForObject("getUserByUsername", username);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getUserPassword(String username) {
		return (String) sqlMapClient
				.queryForObject("getUserPassword", username);
	}

	public boolean exists(Long id) {
		return false;
	}

	// public PageUtil<User> findQueryPage(Object parameterObject, int
	// currentPage, int pageSize) {
	// return null;
	// }

	public List<User> getAll() {
		return null;
	}

	public User save(User object) {
		return null;
	}

	public void editPassword(User user) {
		sqlMapClient.update("editPassword", user);
	}

	public void editPayPassword(User user) {
		sqlMapClient.update("editPayPassword", user);
	}

	public Boolean checkEmail(String email) {
		if (sqlMapClient.queryForObject("checkEmail", email) != null) {
			return true;
		}
		return false;
	}

	public Boolean checkAccount(String account) {
		if (sqlMapClient.queryForObject("checkAccount", account) != null) {
			return true;
		}
		return false;
	}

	public void updateLastLogin(User user) {
		sqlMapClient.update("updateLastLogin", user);
	}

	public Boolean checkPassword(User user) {
		if (sqlMapClient.queryForObject("checkPassword", user) != null) {
			return true;
		}
		return false;
	}

	public Boolean checkPayPassword(User user) {
		if (sqlMapClient.queryForObject("checkPayPassword", user) != null) {
			return true;
		}
		return false;
	}

	public User getUserByAccount(String account) {
		User user = (User) sqlMapClient.queryForObject("getUserByAccount",
				account);
		return user;
	}

	// public List<User> getUsersByCondition(User user, Page page) throws
	// DaoException {
	// List<User> users = null;
	// try {
	// // if ((StringUtil.isBlank(user.getApply_stauts()))
	// // && (StringUtil.isBlank(user.getCashStatus())) &&
	// user.getAgentManagerId() == null) {
	// // users = findQueryPage("getUsersByCondition",
	// "getUsersByConditionCount", user, page);
	// // } else {
	// users = findQueryPage("getUsersByCondition_addagent",
	// "getUsersByConditionCount_addagent", user, page);
	// // }
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return users;
	// }

	// @SuppressWarnings( { "unchecked", "unchecked" })
	// public List<User> getUsers(User user, Page page) throws DaoException {
	// Integer count = (Integer)
	// this.sqlMapClient.queryForObject("getUserCount");
	// int currentPage = page.getCurrentPage();
	// page.setTotalRowsAmount(count);
	// page.setCurrentPage(currentPage);
	// int startRow = page.getPageStartRow() - 1;
	//
	// return this.sqlMapClient
	// .queryForList("getUsers", startRow, page.getPageSize());
	//
	// }

	public User getUserById(Long id) {
		return (User) sqlMapClient.queryForObject("getUserById", id);
	}

	public void addUserRoleRegister(Long userId, Long roleId) {
		Map<String, Object> prama = new HashMap<String, Object>();
		prama.put("userId", userId);
		prama.put("roleId", roleId);
		sqlMapClient.insert("addUserRoleRegister", prama);

	}

	@SuppressWarnings("unchecked")
	public List<User> getUserByIsAdmin(int isAdmin) {
		return this.sqlMapClient.queryForList("getUserByIsAdmin", isAdmin);
	}

	public User getUserByEmail(String email) {
		return (User) sqlMapClient.queryForObject("getUserByEmail", email);
	}

	public List getAgentUsers(long id) {
		ArrayList list = (ArrayList) this.sqlMapClient.queryForList(
				"getAgentUsers", id);
		return list;
	}

	public User getUser(String userName, String password) {
		User user = new User();
		user.setAccount(userName);
		user.setPassword(password);
		return (User) sqlMapClient.queryForObject("getUserByUser", user);
	}

	public void updateUserType(long id, String type) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("type", type);
		this.sqlMapClient.update("updateUserType", map);

	}

	public void deleteUser(Long userId) {
		this.sqlMapClient.delete("deleteUser", userId);
	}

	@SuppressWarnings("unchecked")
	public void updateAgentCount(Long userId, Long agentCountNumber) {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("agentCountNumber", agentCountNumber);
		this.sqlMapClient.update("updateAgentCount", map);
	}

	public void updateAgentInvoice(Long userId, String invoice) {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("invoice", invoice);
		this.sqlMapClient.update("updateAgentInvoice", map);
	}

	public void updateUserByParMap(Map map) {
		this.sqlMapClient.update("updateUserByParMap", map);
	}

	public void updateUserSalesCount(TradeSalesCount tradeSales) {
		this.sqlMapClient.update("updateUserSalesCount", tradeSales);
	}

	public List<User> getUserWithTypes(Map parMap) {
		return this.sqlMapClient.queryForList("getUserWithTypes", parMap);
	}

	public void updateUserPeriod(User user) throws Exception {
		this.sqlMapClient.update("updateUserPeriod", user);
	}

	public void updateUserScrap(User user) throws Exception {
		this.sqlMapClient.update("updateUserScrap", user);
	}

	public void editGmtPeriodPay() {
		sqlMapClient.update("editGmtPeriodPay", null);
	}

	public void updateUserPeriodAmount(User user) {
		this.sqlMapClient.update("updateUserPeriodAmount", user);
	}

	@SuppressWarnings("unchecked")
	public void updateChannelMemoByUserId(Long userId, String channelMemo) {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("channelMemo", channelMemo);
		this.sqlMapClient.update("updateChannelMemoByUserId", map);
	}

	@SuppressWarnings("unchecked")
	public void updateMainMemoByUserId(Long userId, String mainMemo) {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("mainMemo", mainMemo);
		this.sqlMapClient.update("updateMainMemoByUserId", map);
	}

	@SuppressWarnings("unchecked")
	public void updateConnectMemoByUserId(Long userId, String connectMemo) {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("connectMemo", connectMemo);
		this.sqlMapClient.update("updateConnectMemoByUserId", map);
	}

	@SuppressWarnings("unchecked")
	public void updateIsFulltimeByUserId(Long userId, String isFulltime) {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("isFulltime", isFulltime);
		this.sqlMapClient.update("updateIsFulltimeByUserId", map);
	}

	@Override
	public Integer getUserListByConditionWithPageCount(Map parMap, User user) {
		return (Integer) sqlMapClient.queryForObject(
				"getUsersByConditionCount_addagent", parMap);
	}

	@Override
	public List<User> getUserListByConditionWithPage(Map parMap, User user) {
		return sqlMapClient
				.queryForList("getUsersByCondition_addagent", parMap);
	}

	@Override
	public void updateUserByNotNull(User user)
	{
		sqlMapClient.update("updateUsersByNotNull",user);
	}

	@Override
	public Long getCustomerIdByUserId(Long userId)
	{
		return (Long)sqlMapClient.queryForObject("selectCustomerIdById",userId);
	}

	@Override//count
	public Integer serchUserListByConditionWithPageCount(Map parMap, User user) {
		return (Integer) sqlMapClient.queryForObject(
				"serchUserListByConditionWithPageCount", parMap);
	}

	@Override//map
	public List<User> serchUserListByConditionWithPage(Map parMap, User user) {
		return sqlMapClient.queryForList("serchUserListByConditionWith", parMap);
	}

}
