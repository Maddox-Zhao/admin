package com.huaixuan.network.biz.service.admin.impl;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.base.SiteDao;
import com.huaixuan.network.biz.dao.hx.DxrecordDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.admin.AdminRole;
import com.huaixuan.network.biz.domain.admin.RoleAuthority;
import com.huaixuan.network.biz.domain.base.MiniUiBase;
import com.huaixuan.network.biz.domain.hx.Dxrecord;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumAdminUserStatus;
import com.huaixuan.network.biz.enums.EnumLoginResult;
import com.huaixuan.network.biz.service.admin.AdminRoleService;
import com.huaixuan.network.biz.service.admin.AdminService;
import com.huaixuan.network.biz.service.admin.LoginService;
import com.huaixuan.network.biz.service.admin.RoleAuthorityService;
import com.huaixuan.network.biz.service.base.MiniuiBaseService;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.hundsun.network.melody.common.util.StringUtil;
import com.hundsun.network.melody.common.web.cookyjar.Cookyjar;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AdminRoleService adminRoleService;
	
	@Autowired
	private RoleAuthorityService roleAuthorityService;
	
	@Autowired
	private MiniuiBaseService miniuiBaseService;
	
	@Autowired
	private DxrecordDao dxrecordao;
	
	protected Log        log = LogFactory.getLog(this.getClass());
	
	@Override
	public Admin login(Admin admin,Cookyjar cookyjar,boolean remember) {
		
		Admin result = null;
		try {
			result = adminService.getAdminByCondition(admin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result != null){
			if(result.getPassword().equals(admin.getPassword())){
				if(EnumAdminUserStatus.USING.getValue() == result.getStatus()){
					//����Ȩ�޵�cooky
					AdminAgent loginUser = new AdminAgent();
					loginUser.setId(result.getId());
					loginUser.setUsername(result.getUserName());
					loginUser.setName(result.getName());
					loginUser.setPassword(result.getPassword());
					loginUser.setStatus(result.getStatus());
					loginUser.setLoginSystem(admin.getLoginSystem());
					loginUser.setSiteId(result.getSite());
					
					
					List<MiniUiBase> siteList = miniuiBaseService.getSiteByType("1,4");//代销和直营的站点
					for(MiniUiBase m : siteList)
					{
						if(m.getId() == result.getSite())
						{
							loginUser.setSiteName(m.getName());
							//用户关联了站点 通过站点名字确认是否代销用户登录
							if(m.getName().contains("代销")) //代销站点  是代销客户登录
							{
								/*  代销不从slogin登录  直接从login登录
								if(!"dx".equals(admin.getType())) //前端传过来的不是代销客户类型 不能登录
								{
									result.setLoginTag(EnumLoginResult.NO_DAIXIAO.getCode());
									return result;
								}
								*/
								loginUser.setSearchIdSites(MiniUiUtil.shSiteStr + "," + MiniUiUtil.hkSiteStr);
								loginUser.setType("dx");//代销客户
								admin.setType("dx");
							}
						}
						
					}
					//����һ���ֿ�Ȩ��
					if(StringUtil.isNotBlank(result.getDepFirstId())&& StringUtil.isNotEmpty(result.getDepFirstId())){
						String[] depfirstIds = result.getDepFirstId().split(",");
				        for (int i = 0; i < depfirstIds.length; i++) {
				            if (StringUtil.isNotBlank(depfirstIds[i])) {
				            	
				            	loginUser.setDepfirstIds(Integer.parseInt(depfirstIds[i]));
				            	
				            }
				        }
					}
					
					
			        if (loginUser.getDepfirstIds() == null) {
			            loginUser.setDepfirstIds(new BigInteger("0"));
			        }
					//����Ȩ��
			        List<AdminRole> roles = adminRoleService.getRoleByAdminId(result.getId());
			        String dutyIds = "";
			        for (AdminRole adminRole : roles) {
			        	Long roleId = adminRole.getRoleId();
			        	dutyIds += roleId;//设置职务 用于前端判断
			            List<RoleAuthority> permissions = roleAuthorityService.getPermissionByRoleId(adminRole.getRoleId());
			            for (RoleAuthority adminPermission : permissions) {
			            	
			           EnumAdminPermission permission = EnumAdminPermission
			        		   .findPermission( adminPermission.getAuthorityId().toString());
			                if (permission != null && (permission.getSystem().equals(admin.getLoginSystem())))
			      
			                    loginUser.setPermissions(permission.ordinal());
			                
			            }
			        }
			        loginUser.setDutyIds(dutyIds);
			        
			        if (loginUser.getPermissions() == null) {
			            loginUser.setPermissions(new BigInteger("0"));
			        }
			        // �����û���Ϣ��cookie
			        if (remember) {
			            cookyjar.set(loginUser, 7 * 3600 * 24); //����7��
			        } else {
			            cookyjar.set(loginUser);
			        }
			        
					result.setLoginTag(EnumLoginResult.SUCCESS.getCode());
					return result;
				}else{
					result.setLoginTag(EnumLoginResult.USER_FREEZING.getCode());
					return result;
				}
			}else{
				result.setLoginTag(EnumLoginResult.PASSWORD_WRONG.getCode());
				return result;
			}
		}else{
			result = new Admin();
			result.setId(0L);
			result.setLoginTag(EnumLoginResult.NO_USER.getCode());
			return result;
		}
	}

	@Override
	public void addDxrecord(Dxrecord dx) {
		log.info("LoginServiceImpl.addDxrecord method");
		try {
			this.dxrecordao.addDxrecord(dx);
		} catch (Exception e) {
		}
		
	}

}
