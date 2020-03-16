package site.nullpointer.security.jwt;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ApiFilter extends OncePerRequestFilter {
    public final static String IS_API="_is_api_";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().startsWith("/api")) {
            request.getSession();
            logger.info("非API请求，使用session");
        }else{
            request.setAttribute(IS_API,Boolean.TRUE);
        }
        filterChain.doFilter(request,response);
    }

    /**
     * 是否使用API
     * @param request
     * @return
     */
    public static boolean isUseApi(HttpServletRequest request){
        return request.getAttribute(IS_API)!=null;
    }
}
