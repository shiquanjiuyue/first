package com.xiaohe.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/** 权限实体类 */
@Entity
@Table(name="permission")
@NamedQuery(name="Permission.findAll", query="SELECT p FROM Permission p")
public class Permission {
	
	/** 权限主键 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int permissionSn;
	/** 权限名称 */
	private String name;
	/** 权限表达式 */
	private String expression;
	
	public Permission() {
		super();
	}
	public Permission(String name, String expression) {
		this.name = name;
		this.expression = expression;
	}
	public int getPermissionSn() {
		return permissionSn;
	}
	public void setPermissionSn(int permissionSn) {
		this.permissionSn = permissionSn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
}
