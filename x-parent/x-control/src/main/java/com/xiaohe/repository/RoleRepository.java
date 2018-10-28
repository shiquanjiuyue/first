package com.xiaohe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.xiaohe.entity.IUser;
import com.xiaohe.entity.Role;

/**
 * @author Administrator
 * 角色管理
 */
public interface RoleRepository extends BaseRepository<Role, Integer> {
	
	public Role findByRoleName(String roleName);

	public Page<Role> findAll(Specification<Role> specification, Pageable pageable);
	
}