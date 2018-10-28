package com.xiaohe.service.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.xiaohe.entity.IUser;
import com.xiaohe.entity.Role;
import com.xiaohe.repository.UserRepository;
import com.xiaohe.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userDao;

	@Override
	public IUser findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

	@Override
	public IUser findByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}

	@Override
	public Page<IUser> findAll(Specification<IUser> specification, Pageable pageable) {
		return userDao.findAll(specification, pageable);
	}

	@Override
	public IUser findByLoginNameAndPassword(String loginName, String password) {
		return userDao.findByLoginNameAndPassword(loginName, password);
	}

	@Override
	public void addIUser(IUser user) {
		userDao.saveAndFlush(user);
	}

	@Override
	public IUser findByRole(Role role) {
		return userDao.findByRole(role);
	}

	@Override
	public Page<IUser> findAllByLikeUserNameAndLoginName(final String userName, final String loginName, int page, Integer count) {
		Specification<IUser> specification = new Specification<IUser>() {
			@Override
			public Predicate toPredicate(Root<IUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.conjunction();
				if(!StringUtils.isEmpty(userName)){
					Path<String> _userName = root.get("userName");
					 predicate.getExpressions().add(criteriaBuilder.like(_userName, "%" + userName + "%"));
				}
				if(!StringUtils.isEmpty(loginName)){
					Path<String> _loginName = root.get("loginName");
					predicate.getExpressions().add(criteriaBuilder.like(_loginName, "%" + loginName + "%"));
				}
				return predicate;
			}
		};
		Sort sort = new Sort(Direction.ASC, "userSn");
		Pageable pageable = new PageRequest(page - 1, count, sort);
		return userDao.findAll(specification, pageable);
	}

	@Override
	public void deleteById(int userSn) {
		userDao.delete(userSn);
	}

	@Override
	public IUser findById(Integer userSn) {
		return userDao.findOne(userSn);
	}

}
