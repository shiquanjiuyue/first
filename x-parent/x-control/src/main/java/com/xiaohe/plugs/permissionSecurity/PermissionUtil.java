package com.xiaohe.plugs.permissionSecurity;

import java.lang.reflect.Method;

/** 权限工具类 */
public class PermissionUtil {

	/**
	 * 创建权限表达式 （类路径名 + :方法名）
	 * @param method 需要被加上权限的方法
	 * @return 权限表达式
	 */
	public static final String createExpression(Method method) {
		StringBuffer sb = new StringBuffer(100);
		String canonicalName = method.getDeclaringClass().getCanonicalName(); // 获取方法所在类路径全名
		String methodName = method.getName(); // 获取方法名
		return sb.append(canonicalName).append(":").append(methodName).toString();
	}
	
/*	public static void main(String[] args) {
		try {
			Method method = UserController.class.getMethod("ToIndexPage");
			String expression = createExpression(method);
			System.out.println(expression);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
