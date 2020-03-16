package site.nullpointer.security.components;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import site.nullpointer.common.dto.ResponseEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static site.nullpointer.security.jwt.ApiFilter.isUseApi;

public class SysLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	public SysLoginFailureHandler(){
		super("/login?error=true");
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		if(!isUseApi(request)){
			super.onAuthenticationFailure(request,response,exception);
		}else{
			response.setContentType("application/json; charset=utf-8");
			String cause = "";
			if (exception instanceof BadCredentialsException) {
				cause = "无效的认证信息!";
			}
			response.getWriter().print(new ResponseEntity().success(false).addKeyValuePair("cause", cause).toJSONString());
		}
	}

}
