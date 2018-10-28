package com.xiaohe.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分页表格请求返回数据域
 * 
 */
public class DataStore {
	
	//总记录数
	private int count;
	
	//表格数据列表
	private List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();

	
	public DataStore(int count, List<Map<String, Object>> rows) {
		super();
		this.count = count;
		this.rows = rows;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Map<String, Object>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}

	
	
}
