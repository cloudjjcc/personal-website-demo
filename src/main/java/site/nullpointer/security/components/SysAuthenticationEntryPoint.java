package site.nullpointer.security.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import site.nullpointer.common.dto.ResponseEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static site.nullpointer.security.jwt.ApiFilter.isUseApi;

/**
 * 认证入口类
 */
public class SysAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
    private final Logger logger=LoggerFactory.getLogger(SysAuthenticationEntryPoint.class);

    /**
     * @param loginFormUrl URL where the login page can be found. Should either be
     *                     relative to the web-app context path (include a leading {@code /}) or an absolute
     *                     URL.
     */
    public SysAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }
    public SysAuthenticationEntryPoint() {
        super("/login");
    }
    @Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		if(!isUseApi(request)){
            super.commence(request,response,authException);
		}else{
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(new ResponseEntity().success(false).toJSONString());
		}
    }
}
