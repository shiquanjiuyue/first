package com.xiaohe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.xiaohe.entity.Permission;

/**
 * @author Administrator
 * 权限管理
 */
public interface PermissionRepository extends BaseRepository<Permission, Integer> {

	Page<Permission> findAll(Specification<Permission> specification, Pageable pageable);
	
}