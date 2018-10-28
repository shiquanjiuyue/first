package com.xiaohe.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/** 角色实体类 */
@Entity
@Table(name="role")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role {

	/** 角色主键 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int roleSn;
	/** 角色名称 */
	private String roleName;
	/** 角色标识 */
	private String roleMark;
	/** 权限集合 */
	@ManyToMany
	//关系维护端，负责多对多关系的绑定和解除
    //@JoinTable注解的name属性指定关联表的名字，joinColumns指定外键的名字，关联到关系维护端(role)
    //inverseJoinColumns指定外键的名字，要关联的关系被维护端(permission)
    //其实可以不使用@JoinTable注解，默认生成的关联表名称为主表表名+下划线+从表表名，
    //即表名为role_permission
    //主表就是关系维护端对应的表，从表就是关系被维护端对应的表
	@JoinTable(name = "role_permission", joinColumns = {
            @JoinColumn(name = "roleSn", referencedColumnName = "roleSn")}, inverseJoinColumns = {
            @JoinColumn(name = "permissionSn", referencedColumnName = "permissionSn")})
	private List<Permission> permissionList = new ArrayList();
	public int getRoleSn() {
		return roleSn;
	}
	public void setRoleSn(int roleSn) {
		this.roleSn = roleSn;
	}
	public String getRoleMark() {
		return roleMark;
	}
	public void setRoleMark(String roleMark) {
		this.roleMark = roleMark;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public List getPermissionList() {
		return permissionList;
	}
	public void setPermissionList(List permissionList) {
		this.permissionList = permissionList;
	}
	
	
}
