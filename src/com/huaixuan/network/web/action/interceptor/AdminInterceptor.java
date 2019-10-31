package com.huaixuan.network.web.action.interceptor;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.huaixuan.network.biz.dao.admin.AdminLogDao;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.admin.AdminLog;
import com.huaixuan.network.biz.enums.EnumAdminLog;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.hundsun.network.melody.common.util.StringUtil;
import com.hundsun.network.melody.common.web.adapter.AnnotationMethodHandlerInterceptorAdapter;
import com.hundsun.network.melody.common.web.cookyjar.Cookyjar;

public class AdminInterceptor extends AnnotationMethodHandlerInterceptorAdapter {
	
	@Autowired
	private AdminLogDao adminLogDao;
	
    protected Log  log = LogFactory.getLog(this.getClass());

	private static final Integer placeholder = Integer.valueOf(0);

	@Override
	public void preInvoke(Method handlerMethod, Object handler, ServletWebRequest webRequest) {
        Cookyjar cookyjar = (Cookyjar) webRequest.getAttribute(Cookyjar.CookyjarInRequest,
                RequestAttributes.SCOPE_REQUEST);
        if (cookyjar == null) {
            throw new IllegalStateException("cookyjar not find in request");
        }
        
        AdminAgent adminAgent = (AdminAgent)cookyjar.getObject(AdminAgent.class);
        
        String uriAll = webRequest.getRequest().getRequestURI();
        
        String uri = null; 
        
        boolean isTimeTask = false;
        
        if(StringUtil.isNotBlank(uriAll) && StringUtil.isNotEmpty(uriAll)){
        	int len = uriAll.lastIndexOf("/");
        	int lenTwo = uriAll.lastIndexOf(".");
        	if(lenTwo>len){
        		uri = uriAll.substring(len+1, lenTwo);
        	}
        	
            //定时任务timetask判断
        	String[] urlArray = uriAll.split("/");
        	if(urlArray != null && urlArray.length > 0){
        		for(String urlTemp : urlArray){
        			if(urlTemp.equals("timetask") || urlTemp.equals("api")){
        				isTimeTask = true;
        				break;
        			}
        		}
        	}
        }
        
        if(!isTimeTask){ //当链接地址不包含timetask或者 访问ip不为指定的ip的时候
            if(adminAgent == null){
            	if(StringUtil.isNotBlank(uri) && StringUtil.isNotEmpty(uri) && !(uri.equals("login"))  && !(uri.equals("slogin"))){
            		throw new AdminLoginException();
            	}
            }
        }
        
        if (!pass(adminAgent, handlerMethod, handler)) {
            throw new AdminDeniedException();
        }else{
        	//记录日志
        	EnumAdminLog enumAdminLog = EnumAdminLog.findLog(uri);
        	if(enumAdminLog != null){
                Map map = webRequest.getRequest().getParameterMap();
                StringBuffer content = new StringBuffer();
                Iterator it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String[] value = (String[]) entry.getValue();
                    if (value != null && value.length > 0)
                        content.append(value[0] + ",");
                }

                String memo = StringUtil.left(content.toString(), 200);
                String ip = webRequest.getRequest().getRemoteAddr();
                AdminLog adminLog = new AdminLog();
                adminLog.setAccount(adminAgent.getUsername());
                adminLog.setIp(ip);
                adminLog.setOperationType(uri);
                adminLog.setContent(memo);
                try {
    				adminLogDao.addAdminLog(adminLog);
    			} catch (Exception e) {
    				log.error(e.getMessage());
    			}	
        	}
        }
        
	}
	
    private Map<Method, EnumAdminPermission[]> caches          = new ConcurrentHashMap<Method, EnumAdminPermission[]>();


    private Map<Method, Integer>             noControlCaches = new ConcurrentHashMap<Method, Integer>();

    private boolean pass(AdminAgent adminAgent, Method handlerMethod, Object handler) {
    	EnumAdminPermission[] funs = null;
        funs = this.caches.get(handlerMethod);
        if (funs == null) {
            if (noControlCaches.containsKey(handlerMethod)) {
                return true;
            }
            AdminAccess access = AnnotationUtils.getAnnotation(handlerMethod, AdminAccess.class);
            if (access == null) {
                access = AnnotationUtils.findAnnotation(handler.getClass(), AdminAccess.class);
                if (access == null) {
                    noControlCaches.put(handlerMethod, placeholder);
                    return true;
                }

            }
            funs = access.value();
            this.caches.put(handlerMethod, funs);
        }
        if (funs.length == 0) {
            return adminAgent != null;
        }


        if (adminAgent != null) {
            for (EnumAdminPermission permission : funs) {
                if (adminAgent.havePermission(permission)) {
                    return true;
                }
            }
        }
        return false;
    }
}
