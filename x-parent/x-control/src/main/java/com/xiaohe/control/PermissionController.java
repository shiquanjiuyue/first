package com.xiaohe.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xiaohe.common.utils.JsonUtil;
import com.xiaohe.entity.Permission;
import com.xiaohe.plugs.permissionSecurity.HavePermission;
import com.xiaohe.service.PermissionService;

@Controller
@RequestMapping("/x/permission")
public class PermissionController {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	@Autowired
	private PermissionService permissionService;
	
	/** 跳转权限列表界面 */
	@HavePermission("权限界面")
	@RequestMapping("/toPermissionPage")
	public String toPermissionPage(){
		return "permission/permission_list";
	}
	
	/** 分页获取权限列表 */
	@HavePermission("权限列表")
	@RequestMapping(value = "/queryList")
	@ResponseBody
	public Map<String, Object> queryList(String aoData, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> aoresultMap = JsonUtil.parseAoDataToMap(aoData);
			Page<Permission> PdataSource = permissionService.findAllByLikeName(aoresultMap.get("search_permission_name").toString(), (Integer.valueOf(aoresultMap.get("iDisplayStart").toString()) + Integer.valueOf(aoresultMap.get("iDisplayLength").toString())) / Integer.valueOf(aoresultMap.get("iDisplayLength").toString()), Integer.valueOf(aoresultMap.get("iDisplayLength").toString()));
			resultMap.put("iTotalRecords", PdataSource.getTotalElements());
			resultMap.put("sEcho", aoresultMap.get("sEcho"));// 分页用
			resultMap.put("iTotalDisplayRecords", PdataSource.getTotalElements());
			resultMap.put("aaData", PdataSource.getContent());
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();// 获取当前方法名称
			logger.error(methodName + ":" + e.getMessage(), e);
			resultMap.put("message", "数据获取失败");
			resultMap.put("errorCode", "-1");
		}
		return resultMap;
	}
	
	@HavePermission("删除权限")
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(int permissionSn){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			permissionService.deleteById(permissionSn);
			resultMap.put("message", "权限删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("message", "权限删除失败！");
		}
		return resultMap;
	}
	
	@HavePermission("批量删除权限")
	@RequestMapping("/batchDelete")
	@ResponseBody
	public Map<String, Object> batchDelete(String ids){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String[] idArr = ids.split(",");
			for (String permissionSn : idArr) {
				permissionService.deleteById(Integer.valueOf(permissionSn));
			}
			resultMap.put("message", "权限删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("message", "权限删除失败！");
		}
		return resultMap;
	}

}
