package com.xiaohe.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.xiaohe.entity.Permission;
import com.xiaohe.repository.PermissionRepository;
import com.xiaohe.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionRepository permissionDao;

	@Override
	public void addPermission(Permission permission) {
		permissionDao.saveAndFlush(permission);
	}

	@Override
	public List<Permission> findAll() {
		return permissionDao.findAll();
	}

	@Override
	public void addPermissionList(List<Permission> permissionList) {
		permissionDao.save(permissionList);
		
	}

	@Override
	public Page<Permission> findAllByLikeName(final String name, int page, int count) {
		Specification<Permission> specification = new Specification<Permission>() {
			@Override
			public Predicate toPredicate(Root<Permission> root,
					CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Path<String> _name = root.get("name");
				Predicate _key = criteriaBuilder.like(_name, "%" + name + "%");
				return criteriaBuilder.and(_key);
			}
		};
		Sort sort = new Sort(Direction.ASC, "permissionSn");
		Pageable pageable = new PageRequest(page - 1, count, sort);
		return permissionDao.findAll(specification, pageable);
	}

	@Override
	public void deleteById(int permissionSn) {
		permissionDao.delete(permissionSn);
	}

	@Override
	public Permission findById(Integer sn) {
		return permissionDao.findOne(sn);
	}

}
