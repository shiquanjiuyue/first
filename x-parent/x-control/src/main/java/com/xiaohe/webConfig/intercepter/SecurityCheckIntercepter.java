package com.xiaohe.webConfig.intercepter;

import java.lang.reflect.Method;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xiaohe.entity.IUser;
import com.xiaohe.plugs.permissionSecurity.HavePermission;
import com.xiaohe.plugs.permissionSecurity.PermissionUtil;

/** 权限拦截器 */
public class SecurityCheckIntercepter implements HandlerInterceptor {
	
	@Value("${USER_TOKEN}")
	private String USER_TOKEN;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 先判断是否是管理员访问，是就直接放行
		IUser user = (IUser) request.getSession().getAttribute(USER_TOKEN);
		if(user != null && user.getRole().getRoleName().equals("admin")){
			return true;
		}
		
		// 获取该用户的权限表达式集合
		Set<String> permissions = (Set<String>) request.getSession().getAttribute("permissions");
		// 把handler对象转换为HandlerMethod类型（详细信息请看：http://chenzhou123520.iteye.com/blog/1702563）
		HandlerMethod handlerMethod = (HandlerMethod) handler;  
		// 获取拦截的方法对象
		Method method = handlerMethod.getMethod();
		if(method.isAnnotationPresent(HavePermission.class)){ // 如果是打上了权限校验标签的方法则将其拦截
			// 获取该方法的权限表达式
			String expression = PermissionUtil.createExpression(method);
			if(permissions.contains(expression)){ // 若果该用户的权限表达式集合中有该权限则放行
				return true;
			}
		}
		// 跳转权限不足页面
		response.sendRedirect(request.getServletContext().getContextPath() + "/public/noPermissionPage");
		return false;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
	}

}
