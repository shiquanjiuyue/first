package com.xiaohe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.xiaohe.entity.IUser;
import com.xiaohe.entity.Role;
import com.xiaohe.repository.BaseRepository;

/**
 * @author Administrator
 * 用户管理
 */
public interface UserRepository extends BaseRepository<IUser, Integer> {
 
	public IUser findByUserName(String userName);
	
	public IUser findByLoginName(String loginName);

	public Page<IUser> findAll(Specification<IUser> specification,Pageable pageable);

	public IUser findByLoginNameAndPassword(String loginName, String password);

	public IUser findByRole(Role role);

}