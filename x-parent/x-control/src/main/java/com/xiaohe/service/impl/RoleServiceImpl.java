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
import com.xiaohe.entity.Role;
import com.xiaohe.repository.RoleRepository;
import com.xiaohe.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleDao;

	@Override
	public void addRole(Role role) {
		roleDao.saveAndFlush(role);
	}
	
	@Override
	public void addRoleList(List<Role> roleList) {
		roleDao.save(roleList);
	}

	@Override
	public Role findByRoleName(String roleName) {
		return roleDao.findByRoleName(roleName);
	}

	@Override
	public Page<Role> findAllByLikeRoleMark(final String roleMark, int page, int count) {
		Specification<Role> specification = new Specification<Role>() {
			@Override
			public Predicate toPredicate(Root<Role> root,
					CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Path<String> _name = root.get("roleMark");
				Predicate _key = criteriaBuilder.like(_name, "%" + roleMark + "%");
				return criteriaBuilder.and(_key);
			}
		};
		Sort sort = new Sort(Direction.ASC, "roleSn");
		Pageable pageable = new PageRequest(page - 1, count, sort);
		return roleDao.findAll(specification, pageable);
	}

	@Override
	public void deleteById(int roleSn) {
		roleDao.delete(roleSn);
	}

	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}

	@Override
	public Role findById(int roleSn) {
		return roleDao.findOne(roleSn);
	}

}
