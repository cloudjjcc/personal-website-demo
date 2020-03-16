package site.nullpointer.www.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>类路径 : site.nullpointer.www.api.LoginController
 * <p>类描述 : 登陆控制器
 * <p>类详情 : 无
 * 
 * @author  王金灿
 * @version 1.0.0
 *<p>
 *--------------------------------------------------------------<br>
 * 修改履历：<br>
 *        <li> 2018年7月15日，wangjc，创建文件；<br>
 *--------------------------------------------------------------<br>
 *</p>
 */
@Controller
public class LoginController {
	/**
	 * <p>功能描述 : 登陆
	 * <p>详细说明 : 无
	 *
	 * @return 
	 * @author  王金灿
	 *<p>
	 *--------------------------------------------------------------<br>
	 * 修改履历：<br>
	 *        <li> 2018年7月15日，wangjc，创建方法；<br>
	 *--------------------------------------------------------------<br>
	 *</p>
	 */
	@GetMapping("/login")
	public ModelAndView login() {
		Map<String, Object> model=new HashMap<>();
		return new ModelAndView("login",model);
	}
}
