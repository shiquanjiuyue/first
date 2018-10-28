package com.xiaohe.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.xiaohe.entity.Role;

public interface RoleService {

	/** 添加或更新角色 */
	public void addRole(Role role);
	
	/** 查询所有角色 */
	public List<Role> findAll();
	
	/** 根据角色名称查询角色 */
	public Role findByRoleName(String roleName);

	/** 批量插入角色 */
	public void addRoleList(List<Role> roleList);

	/** 分页获取角色 */
	public Page<Role> findAllByLikeRoleMark(String roleMark, int page, int count);

	/** 根据主键删除角色 */
	public void deleteById(int roleSn);

	/** 根据主键查询角色 */
	public Role findById(int roleSn);
}
