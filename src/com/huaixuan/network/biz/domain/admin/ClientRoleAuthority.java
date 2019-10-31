package com.huaixuan.network.biz.domain.admin;

import java.io.Serializable;

public class ClientRoleAuthority implements Serializable {

    private Long roleId;
    private String authorityId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

	public String getAuthorityId()
	{
		return authorityId;
	}

	public void setAuthorityId(String authorityId)
	{
		this.authorityId = authorityId;
	}

}
