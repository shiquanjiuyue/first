package com.xiaohe.control;

import com.xiaohe.common.utils.JsonUtil;
import com.xiaohe.entity.Permission;
import com.xiaohe.plugs.permissionSecurity.HavePermission;
import com.xiaohe.service.PermissionService;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 权限操作controller
 *
 * @author xiezhaohe
 * @since 2019/2/28 20:41
 */
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
	public Map<String, Object> queryList(String aoData) {
		Map<String, Object> resultMap = new HashMap<>(4);
		try {
			Map<String, Object> aoResultMap = JsonUtil.parseAoDataToMap(aoData);
			Page<Permission> dataSource = permissionService.findAllByLikeName(aoResultMap.get("search_permission_name").toString(), (Integer.valueOf(aoResultMap.get("iDisplayStart").toString()) + Integer.valueOf(aoResultMap.get("iDisplayLength").toString())) / Integer.valueOf(aoResultMap.get("iDisplayLength").toString()), Integer.valueOf(aoResultMap.get("iDisplayLength").toString()));
			resultMap.put("iTotalRecords", dataSource.getTotalElements());
			// 分页用
			resultMap.put("sEcho", aoResultMap.get("sEcho"));
			resultMap.put("iTotalDisplayRecords", dataSource.getTotalElements());
			resultMap.put("aaData", dataSource.getContent());
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
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
		Map<String, Object> resultMap = new HashMap<>(2);
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
		Map<String, Object> resultMap = new HashMap<>(2);
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
