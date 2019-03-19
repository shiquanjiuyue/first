package com.xiaohe.config;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * 建表的命名规则
 *
 * @author xiezhaohe
 * @since 2019/3/7 21:27
 */
public class VimsNamingStrategy extends ImprovedNamingStrategy {

	@Override
	public String propertyToColumnName(String propertyName) {
		return propertyName;
	}

	@Override
	public String tableName(String tableName) {
		return tableName;
	}

	@Override
	public String columnName(String columnName) {
		return columnName;
	}
//
//	public String propertyToTableName(String className, String propertyName) {
//		return classToTableName(className) + '_' + propertyToColumnName(propertyName);
//	}
}