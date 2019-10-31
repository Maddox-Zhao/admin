package com.huaixuan.network.biz.service.express;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.express.Region;

/**
 * �����Զ����(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface RegionManager {

	/**
	 * ����CODE��ȡ��������
	 * 
	 * @param code
	 * @return
	 */
	public String getCodeName(String code);

	/**
	 * ����Code ��ȡ��������
	 * 
	 * @param code
	 * @return
	 */
	public List<Region> getRegionChilds(String code);

	/**
	 * �������ͻ�ȡ���򼯺�
	 * 
	 * @param type
	 * @return
	 */
	public List<Region> getRegionByType(int type);

	/**
	 * 
	 * @param type
	 * @return
	 */
	public List<Region> getRegion(Region region);

	/**
	 * ����ʡ���룬��ȡ��ַ����
	 * 
	 * @param provinceCode
	 * @return
	 */
	public String getAddressInfo(String provinceCode);

	/**
	 * ����ʡ���д��룬��ȡ��ַ����
	 * 
	 * @param provinceCode
	 * @param cityCode
	 * @return
	 */
	public String getAddressInfo(String provinceCode, String cityCode);

	/**
	 * ����ʡ���С��س����룬��ȡ��ַ����
	 * 
	 * @param provinceCode
	 * @param cityCode
	 * @param districtCode
	 * @return
	 */
	public String getAddressInfo(String provinceCode, String cityCode, String districtCode);

	/**
	 * ����ʡ���С��س����룬��ȡ��ַ���� ����contactStr������
	 * 
	 * @param provinceCode
	 * @param cityCode
	 * @param districtCode
	 * @param contactStr
	 * @return
	 */
	public String getAddressInfo(String provinceCode, String cityCode, String districtCode, String contactStr);

	/**
	 * ����codeȡ����λ��Ϣ
	 * 
	 * @param code
	 *            String
	 * @return Region @
	 * @author chenyan 2009/08/07
	 */
	Region getRegionByCode(String code);

	/**
	 * ������һ������������ҵ�����Ϣ
	 * 
	 * @param parentCode
	 *            String
	 * @return List @
	 * @author chenyan 2009/08/07
	 */
	List<String> listRegionCodeByParentCode(String parentCode);

	/**
	 * ����cityȡ���ӵ�����Ϣ
	 * 
	 * @param parentCode
	 *            String
	 * @return List @
	 * @author chenyan 2009/08/07
	 */
	List<String> listTwoRegionCodeByParentCode(String parentCode);

	/**
	 * ����idȡ�õ�����Ϣ
	 * 
	 * @param regionId
	 * @return @
	 */
	Region getRegion(Long regionId);

	/**
	 * ����codeȡ���ӵ�����Ϣ�򸸵�����Ϣ
	 * 
	 * @param parentCode
	 *            Map
	 * @return List @
	 * @author wangkun 2010/08/07
	 */
	List<Region> getRegionByName(Map region);

	/**
	 * ����һ���ֿ�ID�õ�������Ϣ
	 * 
	 * @param depositoryId
	 * @return Region Object @
	 */
	List<Region> getRegionByFirstDepositoryId(String depositoryId);
	
	/**
	 * ��ȡ����ʡ�еĶ�������
	 * @param specialCode
	 * @return
	 */
	List<Region> getSpecialRegion(Map parMap);
}
