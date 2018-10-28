package com.xiaohe.plugs.permissionSecurity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 权限注解，打上该注解的方法都需要验证权限 */
@Target(ElementType.METHOD) // 表示该注解用于方法上
@Retention(RetentionPolicy.RUNTIME) // 表示该注解在运行时生效
public @interface HavePermission {

	/** 权限值 */
	String value();
}
