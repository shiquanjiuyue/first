package com.xiaohe.webConfig.startListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.xiaohe.entity.IUser;
import com.xiaohe.entity.Permission;
import com.xiaohe.entity.Role;
import com.xiaohe.plugs.commonUtil.PackageUtil;
import com.xiaohe.plugs.permissionSecurity.HavePermission;
import com.xiaohe.plugs.permissionSecurity.PermissionUtil;
import com.xiaohe.service.PermissionService;
import com.xiaohe.service.RoleService;
import com.xiaohe.service.UserService;

/** spring容器加载完成后执行这里的方法 */
@Component
public class ApplicationStartOver implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// 防止执行两次
		if(event.getApplicationContext().getParent() == null) {
			/** 系统启动后判断如果没有管理员则创建一个 */
			Role adminRole = roleService.findByRoleName("admin");
			IUser admin = userService.findByRole(adminRole);
			if(admin == null) {
				if(adminRole == null) { // 如果没有管理员这个角色就先创建
					adminRole = new Role();
					adminRole.setRoleName("admin");
					roleService.addRole(adminRole);
				}
				admin = new IUser();
				admin.setLoginName("admin");
				admin.setPassword("123");
				admin.setRole(adminRole);
				userService.addIUser(admin);
				System.out.println("*****************************创建系统管理员*****************************");
			}
			
			/** 加载所有controller中的权限，保存到数据库中 */
			permissionInit();
		}
		
	}

	/** 加载所有controller中的权限，保存到数据库中 */
	private void permissionInit() {
		try {
			// 获取数据库中已有的权限信息
			List<Permission> alreadyHavePermissionList = permissionService.findAll();
			// 存储权限表达式
			Set<String> expressionSet = new HashSet<String>();
			for (Permission permission : alreadyHavePermissionList) {
				expressionSet.add(permission.getExpression());
			}
			
			// 获取所有Controller的集合
			Set<String> controllerNames = PackageUtil.getClassName("com.xiaohe.control", false);
			List<Permission> permissionList = new ArrayList<Permission>(); // 用户存放权限对象的集合
			for (String name : controllerNames) {
				 // 根据包名+类名获取controller类对象
				Class<?> controllerClass = Class.forName(name);
				// 获取类中的所有方法
				Method[] methods = controllerClass.getDeclaredMethods();
				for (Method method : methods) {
					// 判断该方法是否包含权限
					if(method.isAnnotationPresent(HavePermission.class)){
						// 创建权限表达式
						String expression = PermissionUtil.createExpression(method);
						// 获取权限名称
						String permissionName = method.getAnnotation(HavePermission.class).value();
						// 加入集合
						if(!expressionSet.contains(expression)){ // 数据库中不存在该权限表达式则加入进去
							permissionList.add(new Permission(permissionName, expression));
						}
					}
				}
			}
			// 批量将权限插入数据库
			permissionService.addPermissionList(permissionList);
			System.out.println("*****************************系统权限加载完毕*****************************");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
