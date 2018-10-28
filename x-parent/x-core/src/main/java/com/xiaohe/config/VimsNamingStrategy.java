package com.xiaohe.config;

import org.hibernate.cfg.ImprovedNamingStrategy;

public class VimsNamingStrategy extends ImprovedNamingStrategy {

	public String propertyToColumnName(String propertyName) {
		return propertyName;
	}

	public String tableName(String tableName) {
		return tableName;
	}

	public String columnName(String columnName) {
		return columnName;
	}
//
//	public String propertyToTableName(String className, String propertyName) {
//		return classToTableName(className) + '_' + propertyToColumnName(propertyName);
//	}
}