package com.huaixuan.network.biz.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminRole;
import com.huaixuan.network.biz.domain.admin.AdminTeam;
import com.huaixuan.network.biz.query.QueryPage;


public interface AdminService {

	/**
	 * ���������ȡ��̨����Ա
	 * @param admin
	 * @return
	 */
	public Admin getAdminByCondition(Admin admin);

	/**
	 * ��ҳ��ѯ��ȡadminList
	 *
	 * @param adminList
	 * @return
	 */
	public QueryPage getAdminListByConditionWithPage(Admin admin, int currPage, int pageSize);
	
	/**
	 * 通过站点ID自动生成用户名
	 * @param idSite
	 * @return
	 */
	public String getUserNameByIdSite(String idSite);

	/**
	 * �������Ա
	 * @param adminId
	 */
	public void updateFreezeAdmin(Long adminId);

	/**
	 * �ⶳ����Ա
	 * @param adminId
	 */
	public void updateReleaseAdmin(Long adminId);

	/**
	 * ���id��ȡAdmin
	 * @param adminId
	 * @return
	 */
	public Admin getAdminById(Long adminId);

	/**
	 * ���������ȡ����
	 * @return
	 */
	public List<AdminTeam> getAdminTeamListByMap(Map parMap);

    /**
     * �����û������ϵ
     * @param adminId
     * @param adminName
     * @param teamValues
     * @param type
     */
    public void updateAdminTeam(Long adminId,String adminName,String[] teamValues,String type);

    /**
     * �ж�email�Ƿ��Ѿ���ע��
     *
     * @param email
     * @return
     */
    public boolean checkUserEmail(String email);

    /**
     * �޸Ĺ���Ա
     * @param admin
     */
    public void updateAdmin(Admin admin);

    /**
     * �ж�userName�Ƿ��Ѿ���ע��
     *
     * @param email
     * @return
     */
    public boolean checkUserName(String userName);

    /**
     * �޸�һ���ֿ�
     * @param admin
     */
    public void updateAdminDepfirst(Admin admin);

    /**
     * ��������Ա
     * @param admin
     */
    public void insertAdmin(Admin admin);

    /**
     *  �޸ĵ�ǰ����Ա
     * @param admin
     */
    public void updateCurAdmin(Admin admin);

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
