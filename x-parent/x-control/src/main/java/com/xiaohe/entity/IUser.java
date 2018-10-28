package com.xiaohe.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/** 用户实体类 */
@Entity
@Table(name="i_user")
@NamedQuery(name="IUser.findAll", query="SELECT i FROM IUser i")
public class IUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userSn;

	/** 设置字段默认值（仅在自动建表时有效） */
	@Column(name="active",columnDefinition="int default 0")
	private int active;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	private String email;

	private String isOnline;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;

	private String loginName;

	private String password;

	private String phone;

	@ManyToOne
	@JoinColumn(name = "role")
	private Role role;

	private String userName;
	
	private int sex;
	
	/** 返回用户权限集合 */
	public Set<String> getPermissions(){
		Set<String> permissions = new HashSet<String>();
		List<Permission> permissionList = this.role.getPermissionList();
		for (Permission permission : permissionList) {
			permissions.add(permission.getExpression());
		}
		return permissions;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public IUser() {
	}

	public int getUserSn() {
		return this.userSn;
	}

	public void setUserSn(int userSn) {
		this.userSn = userSn;
	}

	public int getActive() {
		return this.active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getIsOnline() {
		return this.isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

	public Date getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}