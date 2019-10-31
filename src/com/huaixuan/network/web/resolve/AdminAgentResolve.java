package com.huaixuan.network.web.resolve;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.hundsun.network.melody.common.web.cookyjar.Cookyjar;

public class AdminAgentResolve implements WebArgumentResolver{

	@Override
	public Object resolveArgument(MethodParameter methodParameter,
			NativeWebRequest webRequest) throws Exception {
		if (methodParameter.getParameterType().equals(AdminAgent.class)) {
			Cookyjar cookyjar = (Cookyjar) webRequest
					.getAttribute(Cookyjar.CookyjarInRequest,
							RequestAttributes.SCOPE_REQUEST);
			if (cookyjar != null) {
				return cookyjar.getObject(AdminAgent.class);
			}
		}
		return UNRESOLVED;
	}
}
