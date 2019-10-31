package com.huaixuan.network.biz.service.admin.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.huaixuan.network.biz.dao.admin.AdminDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminRole;
import com.huaixuan.network.biz.domain.admin.AdminTeam;
import com.huaixuan.network.biz.enums.EnumAdminUserStatus;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.AdminService;
import com.huaixuan.network.common.util.Md5Util;
import com.hundsun.network.melody.common.util.StringUtil;
import com.tenpay.util.MD5Util;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao adminDao;

    @Autowired
    private PasswordEncoder     passwordEncoder;

    /**
     * ����ģ��
     */
    @Autowired
    protected TransactionTemplate transactionTemplate;

	@Override
	public Admin getAdminByCondition(Admin admin) {
		return adminDao.getAdminByCondition(admin);
	}

	public String getUserNameByIdSite(String idSite)
	{
		return adminDao.getUserNameByIdSite(idSite);
	}
	@Override
	public QueryPage getAdminListByConditionWithPage(Admin admin,int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(admin);
		Map pramas = queryPage.getParameters();

		int count = adminDao.getAdminListByConditionWithPageCount(pramas);

		if (count > 0) {

			/* ��ǰҳ */
			queryPage.setCurrentPage(currPage);
			/* ÿҳ���� */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());
			
			List<Admin> adminList = adminDao.getAdminListByConditionWithPage(pramas);
			if (adminList != null && adminList.size() > 0) {
				queryPage.setItems(adminList);
			}
		}
		return queryPage;
	}

	@Override
	public void updateFreezeAdmin(Long adminId) {
        Admin admin = new Admin();
        admin.setId(adminId);
        admin.setStatus(EnumAdminUserStatus.FREEZING.getValue());
        adminDao.updateAdminStatus(admin);
	}

	@Override
	public void updateReleaseAdmin(Long adminId) {
        Admin admin = new Admin();
        admin.setId(adminId);
        admin.setStatus(EnumAdminUserStatus.USING.getValue());
        adminDao.updateAdminStatus(admin);
	}

	@Override
	public Admin getAdminById(Long adminId) {
		return adminDao.getAdminById(adminId);
	}

	@Override
	public List<AdminTeam> getAdminTeamListByMap(Map parMap) {
		return adminDao.getAdminTeamListByMap(parMap);
	}

	@Override
	public void updateAdminTeam(final Long adminId, final String adminName,
			final String[] teamValues, final String type) {
        Boolean isSuccess = transactionTemplate.execute(new TransactionCallback<Boolean>() {
        	public Boolean doInTransaction(TransactionStatus status) {
                if(adminId != null){
                    AdminTeam adminTeam = null;
                    adminDao.deleteTeamByAdminId(adminId);
                    if(teamValues != null && teamValues.length > 0){
                        for(String obj : teamValues){
                            adminTeam = new AdminTeam();
                            adminTeam.setAdminId(adminId);
                            adminTeam.setTeamName(obj);
                            adminTeam.setType(type);
                            adminTeam.setUserName(adminName);
                            adminDao.insertAdminTeam(adminTeam);
                        }
                    }
                }
        		return true;
        	};
        });
	}

	@Override
	public boolean checkUserEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }

        Admin admin = new Admin();
        admin.setEmail(email);
        Integer count = adminDao.getAdminCount(admin);
        if (count == null || count == 0) {
            return true;
        }

		return false;
	}

	@Override
	public void updateAdmin(Admin admin) {
		Admin current = adminDao.getAdminById(admin.getId());
		if(current == null){
			return;
		}

		if(admin.getStatus() == null){
			admin.setStatus(1);
		}

		if(StringUtil.isNotBlank(admin.getPassword()) || StringUtil.isNotEmpty(admin.getPassword())){
			admin.setPassword(Md5Util.makeMd5Sum(admin.getPassword().getBytes()));
		}else{
			admin.setPassword(current.getPassword());
		}
		
		adminDao.updateAdmin(admin);
	}

	@Override
	public boolean checkUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            return false;
        }

        Admin admin = new Admin();
        admin.setUserName(userName);
        Integer count = adminDao.getAdminCount(admin);
        if (count == null || count == 0) {
            return true;
        }

		return false;
	}

	@Override
	public void updateAdminDepfirst(Admin admin) {
		adminDao.updateAdminDepfirst(admin);
	}

	@Override
	public void insertAdmin(Admin admin) {
		if(admin == null){
			return;
		}

        if (admin.getStatus() == null) {
            admin.setStatus(1);
        }

        admin.setPassword(MD5Util.MD5Encode(admin.getPassword(), null));
        adminDao.insertAdmin(admin);
	}

	@Override
	public void updateCurAdmin(Admin admin) {
		Admin adminDB = adminDao.getAdminById(admin.getId());
		adminDB.setName(admin.getName());
		adminDB.setEmail(admin.getEmail());
		adminDB.setTel(admin.getTel());
		adminDB.setMobile(admin.getMobile());
		adminDao.updateAdmin(adminDB);
	}

	@Override
	public void updateCurPasswordAdmin(Admin admin) {
		admin.setPassword(MD5Util.MD5Encode(admin.getPassword(), null));
		adminDao.updateCurPasswordAdmin(admin);
	}

	@Override
	public List<Admin> getAdminUserList() {
		return adminDao.getAdminUserList();
	}

	@Override
	public List<AdminTeam> getListBySameTeam(String userName) {
		return adminDao.getListBySameTeam(userName);
	}

}
