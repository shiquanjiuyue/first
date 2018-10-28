package com.xiaohe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.xiaohe.entity.IUser;
import com.xiaohe.entity.Role;

public interface UserService {

	/** 更具用户名称查找用户 */
	public IUser findByUserName(String userName);
	
	/** 根据用户登入名查找用户 */
	public IUser findByLoginName(String loginName);

	/** 分页查找查找所有用户 */
	public Page<IUser> findAll(Specification<IUser> specification,Pageable pageable);

	/** 根据用户名登入名与密码查找用户 */
	public IUser findByLoginNameAndPassword(String loginName, String password);
	
	/** 添加或跟新用户 */
	public void addIUser(IUser user);

	/** 根据角色查找用户 */
	public IUser findByRole(Role role);

	/** 根据用户名和登入名获取分页数据 */
	public Page<IUser> findAllByLikeUserNameAndLoginName(String userName, String loginName, int page, Integer count);

	/** 根据主键删除用户 */
	public void deleteById(int userSn);

	/** 根据用户主键查询用户 */
	public IUser findById(Integer userSn);

}
