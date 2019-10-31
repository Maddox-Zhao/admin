package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.DamagedDetail;
import com.huaixuan.network.biz.domain.storage.DamagedDetailView;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * (bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface DamagedDetailDao {
	/* @interface model: DamagedDetail */
	long addDamagedDetail(DamagedDetail damagedDetail) throws Exception;

	/* @interface model: DamagedDetail */
	void editDamagedDetail(DamagedDetail damagedDetail) throws Exception;

	/* @interface model: DamagedDetail */
	void removeDamagedDetail(Long damagedDetailId) throws Exception;

	/* @interface model: DamagedDetail,DamagedDetail */
	DamagedDetailView getDamagedDetail(Long damagedDetailId) throws Exception;

	/*
	 * ����ID�õ�����
	 */
	public DamagedDetail getDamagedDetailById(Long damagedDetailId)
			throws Exception;

	/*
	 * ���ݲ�ѯ�����õ�������Ʒ��¼����
	 */
	public List<DamagedDetailView> getDamagedDetailsByParameterMap(
			Map parameterMap) throws Exception;

	/*
	 * ���������õ������ļ�¼����
	 */
	List<DamagedDetail> getGroupCountListByMap(Map parameterMap)
			throws Exception;

	/*
	 * ���ݲ�ѯ�����õ�������Ʒ��¼����
	 */
	public int getDamagedDetailsCountByParameterMap(Map<String, String> parMap)
			throws Exception;

	/*
	 * ���ݲ�ѯ�����õ����в�Ʒ��¼��
	 */
	int getCountByParameterMap(Map<String, String> parMap) throws Exception;
}
