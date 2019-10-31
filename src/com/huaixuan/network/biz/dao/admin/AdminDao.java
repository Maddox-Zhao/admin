package com.huaixuan.network.biz.dao.admin;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminTeam;

public interface AdminDao {
	/**
	 * ���������ȡ��̨����Ա
	 * @param admin
	 * @return
	 */
	Admin getAdminByCondition(Admin admin);

	/**
	 * ��ҳ��ȡ����Ա����
	 *
	 * @param parMap
	 * @return
	 */
	public int getAdminListByConditionWithPageCount(Map parMap);
	
	/**
	 * 自动生成用户名
	 * @param idSite
	 * @return
	 */
	public String getUserNameByIdSite(String idSite);

	/**
	 * ��ҳ��ȡ����Ա�б�
	 *
	 * @param parMap
	 * @return
	 */
	public List<Admin> getAdminListByConditionWithPage(Map parMap);

	/**
	 * ��Ĺ���Ա״̬
	 * @param admin
	 */
	public void updateAdminStatus(Admin admin);

	/**
	 * ���adminId��ȡAdmin����
	 * @param adminId
	 * @return
	 */
	public Admin getAdminById(Long adminId);

	/**
	 * ���������ȡ����
	 * @param parMap
	 * @return
	 */
	public List<AdminTeam> getAdminTeamListByMap(Map parMap);

	/**
	 * ��ݹ���Աidɾ�������Ϣ
	 * @param adminId
	 */
	public void deleteTeamByAdminId(Long adminId);

	/**
	 * �����������
	 * @param adminTeam
	 */
	public void insertAdminTeam(AdminTeam adminTeam);

	/**
	 * ���admin�����Ҹ���
	 * @param admin
	 * @return
	 */
	public Integer getAdminCount(Admin admin);

	/**
	 * ���¹���Ա
	 * @param admin
	 */
	public void updateAdmin(Admin admin);

	/**
	 * ���һ���ֿ�
	 * @param admin
	 */
	public void updateAdminDepfirst(Admin admin);

	/**
	 * ��������Ա
	 * @param admin
	 */
	public void insertAdmin(Admin admin);

	/**
	 * �޸ĵ�ǰ����
	 * @param admin
	 */
	public void updateCurPasswordAdmin(Admin admin);
	
    /**
     * ��ȡ���й���Ա
     * @return
     */
    public List<Admin> getAdminUserList();
    
    /**
     * ��ȡͬ����Ϣ
     * @param userName
     * @return
     */
    public List<AdminTeam> getListBySameTeam(String userName);
}
