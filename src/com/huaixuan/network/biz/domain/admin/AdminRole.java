package com.huaixuan.network.biz.domain.admin;

import java.io.Serializable;

public class AdminRole implements Serializable {

    /**
     * �û�Id
     */
    private Long adminId;
    /**
     * ��ɫID
     */
    private Long roleId;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
