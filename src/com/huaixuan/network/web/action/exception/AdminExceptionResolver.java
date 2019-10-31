package com.huaixuan.network.web.action.exception;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.web.action.interceptor.AdminDeniedException;
import com.huaixuan.network.web.action.interceptor.AdminLoginException;
import com.hundsun.network.melody.common.web.cookyjar.Cookyjar;


public class AdminExceptionResolver implements HandlerExceptionResolver{
	
    private String           webEncoding                 = "UTF-8";

    private String           loginPath                = "/login.html";

    private String           loginReturnParameterName = "returnto";

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		Log log = LogFactory.getLog(this.getClass());
		log.error("", ex);

		ModelAndView mv = new ModelAndView();
		if (AdminDeniedException.class.isInstance(ex)) {
			mv.setViewName("/authorityerror");
			mv.addObject("msg", ex.getMessage());
		}
		
		if(AdminLoginException.class.isInstance(ex)){
			Cookyjar cookyjar = (Cookyjar)request.getAttribute(Cookyjar.CookyjarInRequest);
			AdminAgent adminAgent = (AdminAgent)cookyjar.getObject(AdminAgent.class);
			String returnUrl = getReturnUrl(request);
			if (adminAgent != null) {
				
				if("dx".equals(adminAgent.getType())) //代销账号
				{
					return new ModelAndView("redirect:/slogin.html" , loginReturnParameterName,returnUrl);
				}
				else
				{
					return new ModelAndView("redirect:" + loginPath, loginReturnParameterName,returnUrl); //我们本地账号
				}
				
			}
			String domain = getDomain(request);
			//如果是代销的
			if("http://supplier.hkshangshang.com/".equals(domain))
			{
				return new ModelAndView("redirect:/slogin.html" , loginReturnParameterName,returnUrl);
			}
			
			mv.setViewName("/loginout");
			//��ת��login��¼ҳ��
//			mv.setViewName("/login");
//			return new ModelAndView("redirect:/login.html", null);
		}
		
		return mv;
	}

	public String getWebEncoding() {
		return webEncoding;
	}

	public void setWebEncoding(String webEncoding) {
		this.webEncoding = webEncoding;
	}

	public String getLoginPath() {
		return loginPath;
	}

	public void setLoginPath(String loginPath) {
		this.loginPath = loginPath;
	}

	public String getLoginReturnParameterName() {
		return loginReturnParameterName;
	}

	public void setLoginReturnParameterName(String loginReturnParameterName) {
		this.loginReturnParameterName = loginReturnParameterName;
	}
	
    private String getReturnUrl(HttpServletRequest request) {
        StringBuffer sb = request.getRequestURL();
        appendRequestParameters(sb, request);
        try {
            return URLEncoder.encode(sb.toString(), this.webEncoding);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
    
    private void appendRequestParameters(StringBuffer sb, HttpServletRequest request) {
        Enumeration en = request.getParameterNames();
        if (!en.hasMoreElements()) {
            return;
        }
        sb.append('?');
        while (en.hasMoreElements()) {
            String name = (String) en.nextElement();
            String[] values = request.getParameterValues(name);
            if (values == null || values.length == 0) {
                continue;
            }
            for (String v : values) {
                try {
                    v = URLEncoder.encode(v, this.webEncoding);
                } catch (UnsupportedEncodingException ignore) {
                }
                sb.append(name).append('=').append(v).append('&');
            }
        }
        sb.deleteCharAt(sb.length() - 1);
    }
    
    
    private String getDomain(HttpServletRequest request)
    {
    	StringBuffer url = request.getRequestURL();
    	if(null == url) return "";
    	String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
    	return tempContextUrl;
    }

}
