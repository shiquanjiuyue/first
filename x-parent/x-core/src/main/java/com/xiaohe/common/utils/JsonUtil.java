package com.xiaohe.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

/**
 * Date: 13-6-3
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * </p>
 * 
 * @author SRH
 * @version 1.0
 */
public class JsonUtil {
	
	 /** 
     * 将json格式的字符串数组转map,datatable插件使用 
     * @param jsonStr 
     * @return 
     */  
    public static Map<String, Object> parseAoDataToMap(String jsonStr){  
    	 JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		 List<Map<String,Object>> jsonList = (List)jsonArray;
		 Map<String, Object> reMap = new HashMap<String, Object>();
		 for (Map<String, Object> map : jsonList) {
			 reMap.put((String) map.get("name"),map.get("value"));
		}
        return reMap;  
    }  
    
    /** 
     * 将json格式的字符串转List
     * @param jsonStr 
     * @return 
     */  
    public static  List<Map<String,String>> parseToList(String jsonStr){  
    	 JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		 List<Map<String,String>> jsonList = (List)jsonArray;
         return jsonList;  
    } 
    
    /** 
     * 将json格式的字符串转List
     * @param jsonStr 
     * @return 
     */  
    public static  List<Map<String,Object>> parseToLists(String jsonStr){  
    	 JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		 List<Map<String,Object>> jsonList = (List)jsonArray;
         return jsonList;  
    } 
    
    
    

}
