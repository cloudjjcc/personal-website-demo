package site.nullpointer.auth.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;

import site.nullpointer.auth.entity.SysUser;

/**
 * <p>类路径 : site.nullpointer.auth.service.UserBaseInfoService
 * <p>类描述 : 用户信息服务
 * <p>类详情 : 无
 * 
 * @author  王金灿
 * @version 1.0.0
 *<p>
 *--------------------------------------------------------------<br>
 * 修改履历：<br>
 *        <li> 2018年7月13日，wangjc，创建文件；<br>
 *--------------------------------------------------------------<br>
 *</p>
 */
public interface UserBaseInfoService {
	/**
	 * <p>
	 * 功能描述 : 获取当前登陆用户信息
	 * <p>
	 * 详细说明 : 无
	 *
	 * @return
	 * @author 王金灿
	 *         <p>
	 *         --------------------------------------------------------------<br>
	 *         修改履历：<br>
	 *         <li>2018年5月24日，wangjc，创建方法；<br>
	 *         --------------------------------------------------------------<br>
	 *         </p>
	 */
    @PreAuthorize("isAuthenticated()")
	default SysUser getCurrentUser() {
		return (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
