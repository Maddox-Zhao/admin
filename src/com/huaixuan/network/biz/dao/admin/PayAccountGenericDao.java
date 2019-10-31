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
	// * ��ҳ��ѯ
	// * @param statementList ��ѯSQL��
	// * @param statementCount ��ѯ����SQL����,�������Ϊnull
	// * @param parameter ����,����ʹ��Map,����Ҫʹ��Object����
	// * @param page ��ҳ��Ϣ
	// */
	// public List<T> findQueryPage(String statementList, String statementCount, Object parameter,
	// Page page);

}