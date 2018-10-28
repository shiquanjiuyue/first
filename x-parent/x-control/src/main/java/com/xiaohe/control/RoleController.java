package com.xiaohe.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaohe.common.utils.JsonUtil;
import com.xiaohe.entity.Permission;
import com.xiaohe.entity.Role;
import com.xiaohe.plugs.permissionSecurity.HavePermission;
import com.xiaohe.service.PermissionService;
import com.xiaohe.service.RoleService;

/** 角色管理controller */
@Controller
@RequestMapping("/x/role")
public class RoleController {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	
	/** 跳转角色列表界面 */
	@RequestMapping("/toRolePage")
	@HavePermission("角色列表页面")
	public String toRolePage(){
		return "role/role_list";
	}
	
	/** 跳转角色添加界面 */
	@HavePermission("角色添加页面")
	@RequestMapping("/toRoleAddPage")
	public String toRoleAddPage(Model model, String sn){
		if(sn != null){ // sn不为空表示编辑
			Role role = roleService.findById(Integer.valueOf(sn));
			model.addAttribute("role", role);
		}
		List<Permission> list = permissionService.findAll();
		List<Map<String, Object>> resultList = new ArrayList<>();
		for (Permission permission : list) {
			boolean flag = true;
			for (Map<String, Object> map : resultList) {
				if(map.containsValue(permission.getExpression().split(":")[0])){
					((List<Permission>) map.get("permissionList")).add(permission);
					flag = false;
				}
			}
			if(flag) {
				Map<String, Object> permissionMap = new HashMap<String, Object>();
				List<Permission> permissionList = new ArrayList<Permission>();
				permissionList.add(permission);
				permissionMap.put("totalName", permission.getExpression().split(":")[0]);
				permissionMap.put("permissionList", permissionList);
				resultList.add(permissionMap);
			}
		}
		model.addAttribute("resultList", resultList);
		return "role/role_add";
	}
	
	/** 分页获取角色列表 */
	@HavePermission("角色列表")
	@RequestMapping(value = "/queryList")
	@ResponseBody
	public Map<String, Object> queryList(String aoData, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> aoresultMap = JsonUtil.parseAoDataToMap(aoData);
			Page<Role> PdataSource = roleService.findAllByLikeRoleMark(aoresultMap.get("search_role_mark").toString(), (Integer.valueOf(aoresultMap.get("iDisplayStart").toString()) + Integer.valueOf(aoresultMap.get("iDisplayLength").toString())) / Integer.valueOf(aoresultMap.get("iDisplayLength").toString()), Integer.valueOf(aoresultMap.get("iDisplayLength").toString()));
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
	
	@HavePermission("删除角色")
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(int roleSn){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			roleService.deleteById(roleSn);
			resultMap.put("message", "角色删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("message", "角色删除失败！");
		}
		return resultMap;
	}
	
	@HavePermission("批量删除角色")
	@RequestMapping("/batchDelete")
	@ResponseBody
	public Map<String, Object> batchDelete(String ids){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String[] idArr = ids.split(",");
			for (String roleSn : idArr) {
				roleService.deleteById(Integer.valueOf(roleSn));
			}
			resultMap.put("message", "角色删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("message", "角色删除失败！");
		}
		return resultMap;
	}
	
	@HavePermission("更新角色权限")
	@RequestMapping("/updateRolePermission")
	@ResponseBody
	public Map<String, Object> updateRolePermission(Role role, Integer[] permissionSnArr){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if(permissionSnArr != null) {
				for(Integer sn : permissionSnArr) {
					Permission permission = permissionService.findById(sn);
					if(permission != null) {
						role.getPermissionList().add(permission);
					}
				}
			}
			roleService.addRole(role);
			resultMap.put("message", "角色权限更新成功！");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("message", "角色权限更新失败！");
		}
		return resultMap;
	}

}
