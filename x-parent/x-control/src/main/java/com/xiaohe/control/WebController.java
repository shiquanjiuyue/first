package com.xiaohe.control;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiaohe.entity.IUser;
import com.xiaohe.service.UserService;

/** 公共页面，在这里写的方法不会被登入拦截器拦截 */
@Controller
@RequestMapping("/public")
public class WebController {
	
	@Autowired
	private UserService userService;
	
	@Value("${USER_TOKEN}")
	private String USER_TOKEN;

	/** 错误页面跳转 */
	@RequestMapping("/error")
	public String ToErrorPage(){
		return "error";
	}
	
	/** 登入界面跳转 */
	@RequestMapping("/loginPage")
	public String toLoginPage(){
		return "login";
	}
	
	/** 权限不足界面跳转 */
	@RequestMapping("/noPermissionPage")
	public String toNoPermissionPage(){
		return "noPermission";
	}
	
	/** 登入验证，在同一个controller中的不同路径之间跳转一定要加forward！ */
	@RequestMapping("/login")
	public String login(Model model, HttpSession session, String loginName, String password){
		IUser user = userService.findByLoginNameAndPassword(loginName, password);
		// 更新登入时间
		user.setLastLogin(new Date());
		userService.addIUser(user);
		if(user != null){
			session.setAttribute(USER_TOKEN, user);
			if(!user.getRole().getRoleName().equals("admin")){
				// 将用户的权限集合放入session中
				session.setAttribute("permissions", user.getPermissions());
			}
			return "redirect:/x/user/indexPage";
		}
		model.addAttribute("errorMessage", "用户名或密码错误！");
		return "forward:/public/loginPage";
	}
	
	/** 用户注册界面跳转 */
/*	@RequestMapping("/registerPage")
	public String ToRegisterPage(){
		return "register";
	}*/
	
	/** 退出登入 */
	@RequestMapping("/loginOut")
	public String loginOut(HttpSession session){
		IUser user = (IUser) session.getAttribute(USER_TOKEN);
		if(user != null){
			session.removeAttribute(USER_TOKEN);
		}
		return "redirect:/public/loginPage";
	}
}
