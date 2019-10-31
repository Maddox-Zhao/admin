package com.huaixuan.network.biz.dao.remote;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.remote.InterfaceApply;

/**
 * �����Զ����(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface InterfaceApplyDao {
	/* @interface model: ���һ��InterfaceApply��¼ */
	void addInterfaceApply(InterfaceApply interfaceApply) throws Exception;

	/* @interface model: ����һ��InterfaceApply��¼ */
	void editInterfaceApply(InterfaceApply interfaceApply) throws Exception;

	/* @interface model: ����һ��InterfaceApply��¼ */
	void editGmtSync(InterfaceApply interfaceApply) throws Exception;

	/* @interface model: ɾ��һ��InterfaceApply��¼ */
	void removeInterfaceApply(Long interfaceApplyId) throws Exception;

	/* @interface model: ��ѯһ��InterfaceApply���,����InterfaceApply���� */
	InterfaceApply getInterfaceApply(Long interfaceApplyId) throws Exception;

	/**
	 *
	 * @author chenhang 2011-5-19
	 * @description
	 * @param interfaceExpressCode
	 * @return
	 * @throws Exception
	 */
	InterfaceApply getInterfaceApplyByInterfaceExpressCode(
			String interfaceExpressCode) throws Exception;

	InterfaceApply getInterfaceApplyByUserId(Long userId, String type)
			throws Exception;

	/*
	 * @interface model:
	 * ��ѯ����InterfaceApply���,����InterfaceApply����ļ���
	 */
	List<InterfaceApply> getInterfaceApplys() throws Exception;

	/**
	 *
	 * @param parameterMap
	 * @param page
	 * @return
	 */
	List<InterfaceApply> getListByMap(Map parMap) throws Exception;

	/**
	 *
	 * @param parMap
	 * @return
	 */
	int getCountByMap(Map parMap) throws Exception;

	/**
	 * 修改接入申请状�
	 *
	 * @param id
	 * @param status
	 */
	void editInterfaceApplyStatus(Long id, String status) throws Exception;
}
