package com.huaixuan.network.web.action.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.tools.view.context.ViewContext;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.hundsun.network.melody.common.web.cookyjar.Cookyjar;


public class AdminAccessVMTool {

    private AdminAgent adminAgent;

    public void init(Object obj) {
        if (!(obj instanceof ViewContext)) {
            throw new IllegalArgumentException("Tool can only be initialized with a ViewContext");
        }
        ViewContext viewContext = (ViewContext) obj;
        HttpServletRequest request = viewContext.getRequest();
        Cookyjar cookyjar = (Cookyjar) request.getAttribute(Cookyjar.CookyjarInRequest);
        if (cookyjar == null) {
            throw new IllegalStateException("Cookyjar not find in HttpServletRequest");
        }
        adminAgent = (AdminAgent) cookyjar.getObject(AdminAgent.class);
    }

    public boolean has(String functionName) {
        if (adminAgent == null) {
            return false;
        }
        EnumAdminPermission en = EnumAdminPermission.valueOf(functionName);
        if (en == null) {
            throw new IllegalArgumentException("unknow function name:" + functionName);
        }
        return this.adminAgent.havePermission(en);
    }

}
