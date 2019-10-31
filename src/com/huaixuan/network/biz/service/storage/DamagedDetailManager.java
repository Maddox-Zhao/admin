package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.domain.storage.DamagedDetail;
import com.huaixuan.network.biz.domain.storage.DamagedDetailView;
import com.huaixuan.network.biz.query.QueryPage;

public interface DamagedDetailManager {

	/**
	 * ���ӱ��е���ϸ��¼
	 *
	 * @param damagedDetail
	 * @return long
	 */
	public long addDamagedDetail(DamagedDetail damagedDetail);

	public void editDamagedDetail(DamagedDetail damagedDetail);

	/*
	 * ɾ��������ϸ��¼
	 *
	 * @param damagedDetailId
	 */

	public void removeDamagedDetail(Long damagedDetailId);

	/*
	 * ����id��ȡ��Ʒ��Ϣ
	 *
	 * @param damagedDetailId
	 *
	 * @return DamagedDetailView
	 */

	// public DamagedDetailView getDamagedDetail(Long damagedDetailId);

	/**
	 * ����id��ȡ���е���ϸ��¼
	 *
	 * @param damagedDetailId
	 *
	 * @return DamagedDetail
	 */

	public DamagedDetail getDamagedDetailById(Long damagedDetailId);

	/**
	 * ���ݲ�ѯ�����õ�������Ʒ��¼����
	 *
	 * @param parameterMap
	 * @param page
	 * @return QueryPage
	 */

	public QueryPage getDamagedDetailsByParameterMap(
			Map parameterMap, int currPage, int pageSize);

	/**
	 * ���ݲ�ѯ�������LIST���͵ı�����Ʒ��¼����
	 *
	 * @author chenhang 2011-3-28
	 * @description
	 * @param par
	 * @return
	 */
	public List<DamagedDetailView> getDamagedDetails(Map par);

	/**
	 * ���������õ������ļ�¼����
	 *
	 * @param parameterMap
	 * @return
	 */
	public List<DamagedDetail> getGroupCountListByMap(Map parameterMap);

	// /**
	// * ���ݲ�ѯ�����õ�������Ʒ��¼����
	// * @param parMap
	// * @return int
	// */
	// public int getDamagedDetailsCountByParameterMap(Map<String, String>
	// parMap);

	/**
	 * ͬһ�����е��Ƿ�����ͬ�ı��е���Ʒdistinct
	 *
	 * @param parMap
	 *
	 * @return int
	 */
	public int getCountByParameterMap(Map<String, String> parMap);

	/**
	 * ��ɱ��У����ɳ��ⵥ���޸Ŀ�桢�޸Ĳ�Ʒʵ�������Ʒ���еĿ��
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public Boolean finishedDamaged(Map parMap) throws Exception;
}
