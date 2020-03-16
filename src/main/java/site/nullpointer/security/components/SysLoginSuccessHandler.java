package site.nullpointer.security.components;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import site.nullpointer.auth.constant.UserAction;
import site.nullpointer.auth.dto.CurrentUserInfo;
import site.nullpointer.auth.entity.SysAction;
import site.nullpointer.auth.entity.SysUser;
import site.nullpointer.auth.repository.SysUserActionRepository;
import site.nullpointer.security.jwt.JwtTokenUtil;
import site.nullpointer.common.dto.ResponseEntity;
import site.nullpointer.utils.ClientUtils;

import static site.nullpointer.security.jwt.ApiFilter.isUseApi;

public class SysLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Autowired
	private SysUserActionRepository suarRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		SysUser user = (SysUser) authentication.getPrincipal();
		// 记录用户登录信息
		SysAction entity = new SysAction();
		entity.setAction(UserAction.LOGIN);
		entity.setAgent(ClientUtils.getAgentString(request));
		entity.setDate(new Date());
		entity.setIp(ClientUtils.getIpAddress(request));
		entity.setUserId(user.getId());
		suarRepository.save(entity);
		if(!isUseApi(request)){
            super.onAuthenticationSuccess(request,response,authentication);
		}else{
			CurrentUserInfo currentUser = new CurrentUserInfo();
			currentUser.setAdmin(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
			currentUser.setToken(jwtTokenUtil.generateToken(user));
			currentUser.setUserId(user.getId());
			currentUser.setUsername(user.getUsername());
			// 设置响应信息
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().print(new ResponseEntity().success(true).data(currentUser).toJSONString());
		}
	}
}
