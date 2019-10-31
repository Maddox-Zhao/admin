package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.query.QueryPage;

public interface DepLocationDao {

	/* @interface model: ������λ��Ϣ */
	Long addDepLocation(DepLocation depLocation);

	/* @interface model: �༭��λ��Ϣ */
	void editDepLocation(DepLocation depLocation);

	public List<DepLocation> getAll();

	public List<DepLocation> getAllDepLocationByMap(Map<String, String> parMap);

	/*
	 * ����������ѯ��λ��¼����
	 */
	public QueryPage getDepLocationsByParMap(Map parameterMap, int currentPage, int pageSize);

	/*
	 * ����������ѯ��λ��¼����
	 */
	public int getCountByParMap(Map<String, Object> parMap);

	/*
	 * ���ݲֿ�ID�õ�����ֿ�����п��1717
	 */
	List<DepLocation> getLocationsByDepositoryId(Long depositoryId);

	DepLocation getLocationsById(Long id);

	DepLocation getLocationsByName(String name);

	/**
	 * ������Ʒʵ��IDȡ�ÿ�λ���Ő_17
	 * 
	 * @param goodsInstanceId
	 *            Long
	 * @param isCheck
	 *            String
	 * @param depFirstId
	 *            Long
	 * @return List
	 * @author chenyan 2009/07/27 modified by chenyan 2010/03/18
	 */
	List<DepLocation> getCheckLocationInfo(Long goodsInstanceId, String isCheck, Long depFirstId);

	/**
	 * ȡ���Ƿ����̵��е����17
	 * 
	 * @param id
	 *            Long
	 * @param isCheck
	 *            String
	 * @return int
	 * @author chenyan 2009/07/27
	 */
	int getIsCheckCountById(Long id, String isCheck);

	/**
	 *  ischeck
	 * 
	 * @param depLocation
	 */
	void lockDepLocation(DepLocation depLocation);

	/**
	 * ȡ�ÿ��õĿ�λ��Ϣ�б�
	 * 
	 * @param depFirstId
	 *            Long
	 * @return List
	 * @author chenyan 2009/08/13 modified by chenyan 2010/03/18
	 */
	List<DepLocation> listUseabledLocInfo(Long depFirstId);

	/**
	 * �ж�ĳ��λ���Ƿ��л���
	 * 
	 * @param depLocId
	 * @return
	 */
	int countStorageByDepLocId(Long depLocId);

	/**
	 * ���ݲֿ�ID�õ�����ֿ������û�б�ɾ���Ŀ�λ
	 * 
	 * @param depositoryId
	 * @return
	 */
	List<DepLocation> getRightLocationsByDepositoryId(Long depositoryId);

	/**
	 * ��ȡ���еķ�ɾ���Ŀ�λ����(�����̵�)
	 * 
	 * @return
	 */
	List<DepLocation> getAllRightLocations();
	
	/**
	 * ��ȡ�����ʱ�����õĿ�λ
	 * @param depFirstId
	 * @return
	 */
    List<DepLocation> getRightDeplocationByDepfirstId(Long depFirstId);
}
