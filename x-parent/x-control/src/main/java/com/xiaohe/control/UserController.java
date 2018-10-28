package com.xiaohe.control;

import java.util.Date;
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

import com.xiaohe.common.utils.BeanUtil;
import com.xiaohe.common.utils.JsonUtil;
import com.xiaohe.entity.IUser;
import com.xiaohe.entity.Role;
import com.xiaohe.plugs.permissionSecurity.HavePermission;
import com.xiaohe.service.RoleService;
import com.xiaohe.service.UserService;

@Controller
@RequestMapping("/x/user")
public class UserController {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	/** 首页跳转 */
	@HavePermission("访问首页")
	@RequestMapping("/indexPage")
	public String ToIndexPage(){
		return "index";
	}
	
	/** 用户界面 */
	@HavePermission("用户界面")
	@RequestMapping("/toUserPage")
	public String toUserPage(){
		return "user/user_list";
	}
	
	/** 用户添加界面*/
	@HavePermission("用户添加界面")
	@RequestMapping("/toUserAddPage")
	public String toUserAddPage(Model model, String sn){
		if(sn != null){
			IUser user = userService.findById(Integer.valueOf(sn));
			model.addAttribute("user", user);
		}
		List<Role> roleList = roleService.findAll();
		model.addAttribute("roleList", roleList);
		return "user/user_add";
	}
	
	/** 用户更新操作 */
	@HavePermission("用户更新")
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> Register(IUser user){
		Map<String, Object> map = new HashMap<>();
		try {
			if(user.getUserSn() == 0){
				user.setCreateTime(new Date());
			}else{
				BeanUtil.copyNonNullProperties(userService.findById(user.getUserSn()), user); // 拷贝空值
			}
			userService.addIUser(user);
			map.put("message", "用户添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorCode", -1);
			map.put("message", "用户添加失败！");
		}
		return map;
	}
	
	/** 分页获取用户列表 */
	@HavePermission("用户列表")
	@RequestMapping(value = "/queryList")
	@ResponseBody
	public Map<String, Object> queryList(String aoData, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> aoresultMap = JsonUtil.parseAoDataToMap(aoData);
			Page<IUser> PdataSource = userService.findAllByLikeUserNameAndLoginName(aoresultMap.get("search_user_name").toString(), aoresultMap.get("search_login_name").toString(), (Integer.valueOf(aoresultMap.get("iDisplayStart").toString()) + Integer.valueOf(aoresultMap.get("iDisplayLength").toString())) / Integer.valueOf(aoresultMap.get("iDisplayLength").toString()), Integer.valueOf(aoresultMap.get("iDisplayLength").toString()));
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
	
	@HavePermission("删除用户")
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(int userSn){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			userService.deleteById(userSn);
			resultMap.put("message", "用户删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("message", "用户删除失败！");
		}
		return resultMap;
	}
	
	@HavePermission("批量删除用户")
	@RequestMapping("/batchDelete")
	@ResponseBody
	public Map<String, Object> batchDelete(String ids){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String[] idArr = ids.split(",");
			for (String userSn : idArr) {
				userService.deleteById(Integer.valueOf(userSn));
			}
			resultMap.put("message", "用户删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("message", "用户删除失败！");
		}
		return resultMap;
	}
	
}
