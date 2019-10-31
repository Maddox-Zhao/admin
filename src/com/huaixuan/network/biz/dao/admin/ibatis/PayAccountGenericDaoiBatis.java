package com.huaixuan.network.biz.dao.admin.ibatis;

import java.io.Serializable;

import com.huaixuan.network.biz.dao.admin.PayAccountGenericDao;


/**
 * This class serves as the Base class for all other DAOs - namely to hold common CRUD methods that they might all use.
 * You should only need to extend this class when your require custom CRUD logic.
 * 
 * <p>
 * To register this class in your Spring context file, use the following XML.
 * 
 * <pre>
 *      &lt;bean id=&quot;fooDao&quot; class=&quot;com.hundsun.bible.dao.ibatis.GenericDaoiBatis&quot;&gt;
 *          &lt;constructor-arg value=&quot;com.hundsun.bible.model.Foo&quot;/&gt;
 *          &lt;property name=&quot;sessionFactory&quot; ref=&quot;sessionFactory&quot;/&gt;
 *      &lt;/bean&gt;
 * </pre>
 * 
 * @author Bobby Diaz, Bryan Noll
 * @param <T>
 *            a type variable
 * @param <PK>
 *            the primary key for that type
 */
public class PayAccountGenericDaoiBatis<T, PK extends Serializable> implements PayAccountGenericDao<T, PK> {
	// /**
	// * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
	// */
	// protected final Log log = LogFactory.getLog(getClass());
	// private Class<T> persistentClass;
	// private String className;
	//
	// /**
	// * Constructor that takes in a class to see which type of entity to persist
	// *
	// * @param persistentClass
	// * the class type you'd like to persist
	// */
	// public PayAccountGenericDaoiBatis(final Class<T> persistentClass) {
	// this.persistentClass = persistentClass;
	// this.className = ClassUtils.getShortName(this.persistentClass);
	// }
	//
	// /**
	// */
	// @SuppressWarnings("unchecked")
	// public List<T> getAll() {
	// return getSqlMapClientTemplate().queryForList(iBatisDaoUtils.getSelectQuery(className), null);
	// }
	//
	// /**
	// * {@inheritDoc}
	// */
	// @SuppressWarnings("unchecked")
	// public T get(PK id) {
	// T object = (T) getSqlMapClientTemplate().queryForObject(className, id);
	// if (object == null) {
	// log.warn("Uh oh, '" + className + "' object with id '" + id + "' not found...");
	// throw new ObjectRetrievalFailureException(className, id);
	// }
	// return object;
	// }
	//
	// /**
	// * {@inheritDoc}
	// */
	// @SuppressWarnings("unchecked")
	// public boolean exists(PK id) {
	// T object = get(id);
	// return object != null;
	// }
	//
	// @SuppressWarnings("unchecked")
	// public T save(final T object) {
	// String className = ClassUtils.getShortName(object.getClass());
	// Object primaryKey = iBatisDaoUtils.getPrimaryKeyValue(object);
	// Class primaryKeyClass = iBatisDaoUtils.getPrimaryKeyFieldType(object);
	// String keyId = null;
	//
	// // check for null id
	// if (primaryKey != null) {
	// keyId = primaryKey.toString();
	// }
	//
	// // check for new record
	// if (StringUtils.isBlank(keyId)) {
	// iBatisDaoUtils.prepareObjectForSaveOrUpdate(object);
	// primaryKey = getSqlMapClientTemplate().insert(iBatisDaoUtils.getInsertQuery(className), object);
	// iBatisDaoUtils.setPrimaryKey(object, primaryKeyClass, primaryKey);
	// } else {
	// iBatisDaoUtils.prepareObjectForSaveOrUpdate(object);
	// getSqlMapClientTemplate().update(iBatisDaoUtils.getUpdateQuery(className), object);
	// }
	//
	// // check for null id
	// if (iBatisDaoUtils.getPrimaryKeyValue(object) == null) {
	// throw new ObjectRetrievalFailureException(className, object);
	// } else {
	// return object;
	// }
	// }
	//
	// /**
	// */
	// public void remove(PK id) {
	// getSqlMapClientTemplate().update(iBatisDaoUtils.getDeleteQuery(className), id);
	// }
	//
	// /**
	// *
	// * @param pageNo
	// */
	// @Deprecated
	// @SuppressWarnings("unchecked")
	// public PageUtil<T> findQueryPage(Object parameterObject, int currentPage, final int pageSize) {
	// // Integer totalCount = (Integer) this.getSqlMapClientTemplate().queryForObject(
	// // iBatisDaoUtils.getCountQuery(className), parameterObject);
	//
	// Page page = new Page();
	// page.setPageSize(pageSize);
	// // page.setTotalRowsAmount(totalCount);
	// page.setCurrentPage(currentPage);
	//
	// // update by yuancong 2009-7-22 15:57:36
	// List<T> list = this.findQueryPage(iBatisDaoUtils.getSelectQuery(className),
	// iBatisDaoUtils.getCountQuery(className), parameterObject, page);
	// // List<T> list = getSqlMapClientTemplate().queryForList(
	// // iBatisDaoUtils.getSelectQuery(className), parameterObject, page.getPageStartRow() - 1,
	// // page.getPageSize());
	//
	// return new PageUtil<T>(list, page);
	// }
	//
	// /**
	// * 分页查询
	// *
	// * @param statementList
	// * 查询SQL名
	// * @param statementCount
	// * 查询数量SQL名称,这个可以为null
	// * @param parameter
	// * 条件,尽量使用Map,而不要使用Object类型
	// * @param page
	// * 分页信息
	// */
	// public List findQueryPage(String statementList, String statementCount, Object parameter, Page page) {
	// // 查询记录数量,如果需要的话
	// if (StringUtils.isNotBlank(statementCount)) {
	// Integer count = (Integer) this.getSqlMapClientTemplate().queryForObject(statementCount, parameter);
	// if (null == page) {
	// page = new Page();
	// }
	// int currentPage = page.getCurrentPage();
	// page.setTotalRowsAmount(count);
	// page.setCurrentPage(currentPage);
	// }
	// // 将参数统一转化成Map
	// Map m = new HashMap();
	// // 判断parameter是否是Map类型,如果不是,将它转化成Map
	// if (!(parameter instanceof Map)) {
	// try {
	// m = BeanUtils.describe(parameter);
	// } catch (IllegalAccessException e) {
	// log.error("", e);
	// } catch (InvocationTargetException e) {
	// log.error("", e);
	// } catch (NoSuchMethodException e) {
	// log.error("", e);
	// }
	// } else {
	// m = (Map) parameter;
	// }
	// // 加入分页信息
	// if (null != page) {
	// // 将分页信息放在参数Map中传到SqlMap里,进行物理分页.
	// m.put("startRow", page.getPageStartRow());
	// m.put("endRow", page.getPageEndRow());
	// }
	//
	// // 分页查询
	// List list = this.getSqlMapClientTemplate().queryForList(statementList, m);
	//
	// return list;
	// }
	//
	// /**
	// * 实际执行批量操作的方法,代理执行插入、更新、删除等操作
	// *
	// * @param statementName
	// * @param parameterList
	// * @return 批量执行数
	// */
	// private int executeBatchOperation(final String statementName, final List parameterList, final String flag) {
	// Long exectuteSucValue = null;
	// exectuteSucValue = (Long) getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
	// public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
	// Long states = Long.valueOf(0);
	// try {
	// executor.startBatch();
	// for (int i = 0; i < parameterList.size(); i++) {
	// if (flag.equals("update")) {
	// executor.update(statementName, parameterList.get(i));
	// } else if (flag.equals("insert")) {
	// executor.insert(statementName, parameterList.get(i));
	// } else if (flag.equals("delete")) {
	// executor.delete(statementName, parameterList.get(i));
	// }
	// }
	//
	// executor.executeBatch();
	// } catch (Exception e) {
	// states = Long.valueOf(-1);
	// log.error(e);
	// }
	// return states;
	//
	// }
	// });
	// if (exectuteSucValue.intValue() == -1) {
	// throw new RuntimeException();
	// }
	// return parameterList.size();
	// }
	//
	// /**
	// * 批量更新
	// *
	// * @param statementName
	// * @param parameterList
	// * @return 批量执行数
	// */
	// protected int executeBatchUpdate(final String statementName, List parameterList) {
	// return this.executeBatchOperation(statementName, parameterList, "update");
	// }
	//
	// /**
	// * 批量添加
	// *
	// * @param statementName
	// * @param parameterList
	// * @return 批量执行数
	// */
	// protected int exectuteBatchInsert(final String statementName, List parameterList) {
	// return this.executeBatchOperation(statementName, parameterList, "insert");
	// }
	//
	// /**
	// * 批量删除
	// *
	// * @param statementName
	// * @param parameterList
	// * @return 批量执行数
	// */
	// protected int executeBatchDelete(final String statementName, List parameterList) {
	// return this.executeBatchOperation(statementName, parameterList, "delete");
	// }
}
