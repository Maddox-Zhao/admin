package com.huaixuan.network.web.action.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.huaixuan.network.biz.enums.EnumAdminPermission;

@Target( { ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminAccess {
	EnumAdminPermission[] value() default {};

}
