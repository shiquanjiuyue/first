package com.xiaohe.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.xiaohe.entity.Permission;
import com.xiaohe.entity.Role;

public interface PermissionService {

	/** 添加或更新权限 */
	public void addPermission(Permission permission);
	
	/** 批量添加权限 */
	public void addPermissionList(List<Permission> permissionList);
	
	/** 获取所有权限 */
	public List<Permission> findAll();

	/** 分页查找 */
	public Page<Permission> findAllByLikeName(String name, int page, int count);

	/** 根据主键删除权限 */
	public void deleteById(int permissionSn);

	/** 根据主键获取权限 */
	public Permission findById(Integer sn);

}
