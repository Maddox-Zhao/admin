package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.gargoylesoftware.htmlunit.Page;
import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.query.QueryPage;

public interface DepLocationManager {

	/* @interface model: ������λ��Ϣ */
 	public Long addDepLocation(DepLocation depLocation);

 	/* @interface model: �༭��λ��Ϣ */
 	public void editDepLocation(DepLocation depLocation);

	public List<DepLocation> getAllLocations();

	public List<DepLocation> getAllDepLocationByMap(Map<String, String> parMap);

	/*
	 * ����������ѯ��λ��¼����
	 */
	public QueryPage getDepLocationsByParMap(Map parameterMap, int currPage, int pageSize);

	/*
	 * ����������ѯ��λ��¼����
	 */
 	public int getCountByParMap(Map parMap);

	/*
	 * ���ݲֿ�ID�õ�����ֿ�����п�λ
	 */
	public List<DepLocation> getLocationsByDepositoryId(Long depositoryId);

	public Depository getDepositoryByLocationId(Long locationId);

	public DepLocation getDepLocationByLocationId(Long locationId);

    public DepLocation getDepLocationByLocationName(String locationName);

	/**
	 * ������Ʒʵ��IDȡ�ÿ�λ����Ϣ
	 * @param goodsInstanceId Long
	 * @param isCheck String
	 * @param depFirstId Long
	 * @return List
	 * @author chenyan 2009/07/27 modified by chenyan 2010/03/18
	 */
	List<DepLocation> getCheckLocationInfo(Long goodsInstanceId, String isCheck, Long depFirstId);

	/**
	 * ȡ���Ƿ����̵��е�����
	 * @param id Long
	 * @param isCheck String
	 * @return int
	 * @author chenyan 2009/07/27
	 */
	int getIsCheckCountById(Long id, String isCheck);

    /**
     * �̴�ʱ���������� ����ischeck�ֶ�
     * @param depLocation
     */
    void lockDepLocation(DepLocation depLocation);

    /**
     * ȡ�ÿ��õĿ�λ��Ϣ�б�
     * @param depFirstId Long
     * @return List
     * @author chenyan 2009/08/13 modified by chenyan 2010/03/18
     */
    List<DepLocation> listUseabledLocInfo(Long depFirstId);

    /**
     * �ж�ĳ��λ���Ƿ��л���
     * @param depLocId
     * @return
     */
    public int countStorageByDepLocId(Long depLocId);

    /**
     * ���ݲֿ�ID�õ�����ֿ������û�б�ɾ���Ŀ�λ
     * @param depositoryId
     * @return
     */
	public List<DepLocation> getRightLocationsByDepositoryId(Long depositoryId);

	/**
	 * ��ȡ���еķ�ɾ���Ŀ�λ����(�����̵�)
	 * @return
	 */
	public List<DepLocation> getAllRightLocations();
	
	/**
	 * ��ȡ�����ʱ�����õĿ�λ
	 * @param depFirstId
	 * @return
	 */
	public List<DepLocation> getRightDeplocationByDepfirstId(Long depFirstId);
}
