package com.huaixuan.network.biz.domain.admin;

import java.io.Serializable;

public class RoleAuthority implements Serializable {

    private Long roleId;
    private Long authorityId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }
}
