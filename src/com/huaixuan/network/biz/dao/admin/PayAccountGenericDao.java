package com.huaixuan.network.biz.dao.admin;

import java.io.Serializable;

/**
 * Generic DAO (Data Access Object) with common methods to CRUD POJOs.
 *
 * <p>Extend this interface if you want typesafe (no casting necessary) DAO's for your
 * domain objects.
 *
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public interface PayAccountGenericDao<T, PK extends Serializable> {
	//
	// /**
	// */
	// @SuppressWarnings("unchecked")
	// public List<T> getAll();
	//
	// /**
	// */
	// @SuppressWarnings("unchecked")
	// public T get(PK id);
	//
	// /**
	// */
	// @SuppressWarnings("unchecked")
	// public boolean exists(PK id);
	//
	// /*
	// */
	// public T save(T object);
	//
	// /**
	// */
	// public void remove(PK id);
	//
	// /**
	// */
	// @SuppressWarnings("unchecked")
	// public PageUtil<T> findQueryPage(Object parameterObject, int currentPage, final int pageSize);
	//
	// /**
	// * 分页查询
	// * @param statementList 查询SQL名
	// * @param statementCount 查询数量SQL名称,这个可以为null
	// * @param parameter 条件,尽量使用Map,而不要使用Object类型
	// * @param page 分页信息
	// */
	// public List<T> findQueryPage(String statementList, String statementCount, Object parameter,
	// Page page);

}