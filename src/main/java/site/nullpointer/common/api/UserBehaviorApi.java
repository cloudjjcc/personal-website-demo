package site.nullpointer.common.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import site.nullpointer.auth.dto.CurrentUserInfo;
import site.nullpointer.auth.dto.UserRegisterInfo;
import site.nullpointer.auth.entity.SysUser;
import site.nullpointer.auth.exception.CaptchaErrorException;
import site.nullpointer.auth.exception.NicknameExistException;
import site.nullpointer.auth.exception.UserInfoException;
import site.nullpointer.auth.exception.UsernameExistException;
import site.nullpointer.auth.service.UserInfoCheckService;
import site.nullpointer.auth.service.UserRegisterService;
import site.nullpointer.security.jwt.JwtTokenUtil;
import site.nullpointer.common.dto.ResponseEntity;
import site.nullpointer.utils.ClientUtils;

@RestController
public class UserBehaviorApi extends CommonApi {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private UserInfoCheckService userInfoCheckService;
    private final static Logger logger = LoggerFactory.getLogger(UserBehaviorApi.class);

    /**
     * 用户刷新请求
     *
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/refresh")
    public ResponseEntity refresh(HttpServletRequest request, HttpServletResponse response) {
        final String requestHeader = request.getHeader(jwtTokenUtil.getAuthHeader());
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                SysUser user = (SysUser) authentication.getPrincipal();
                if (jwtTokenUtil.canTokenBeRefreshed(authToken)) {
                    String refreshedToken = jwtTokenUtil.refreshToken(authToken);
                    logger.info("用户：" + user.getUsername() + "成功刷新Token");
                    CurrentUserInfo currentUser = new CurrentUserInfo();
                    currentUser.setAdmin(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
                    currentUser.setToken(jwtTokenUtil.generateToken(user));
                    currentUser.setUserId(user.getId());
                    currentUser.setUsername(user.getUsername());
                    return new ResponseEntity().success(true).data(currentUser);
                } else {//Token过期
                    logger.info("用户：" + user.getUsername() + "Token过期");
                    return new ResponseEntity().success(false);
                }
            } else {//无效Token
                logger.info("无效的Token");
                return new ResponseEntity().success(false);
            }
        } else {//没有Token
            logger.info("没有Token");
            return new ResponseEntity().success(false);
        }
    }

    /**
     * 用户注册请求
     *
     * @param request
     * @param info
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity register(HttpServletRequest request, @RequestBody UserRegisterInfo info) {
        try {
            info.setCaptchaKey(ClientUtils.getIpOnlyMd5Key(request, info.getCaptchaKey()));
            userRegisterService.register(info);
            return new ResponseEntity().success(true);
        } catch (UserInfoException e) {
            String cause = "";
            if (e instanceof CaptchaErrorException) {
                cause = "验证码错误";
            } else if (e instanceof UsernameExistException) {
                cause = "不合法的用户名";
            }
            return new ResponseEntity().success(false).addKeyValuePair("cause", cause);
        }
    }

    /**
     * 检查用户名
     *
     * @param username
     * @return
     */
    @GetMapping("/check/username")
    public ResponseEntity checkUsername(@RequestParam String username) {
        try {
            userInfoCheckService.checkUsername(username);
            return new ResponseEntity().success(true);
        } catch (UserInfoException e) {
            String cause = "";
            if (e instanceof UsernameExistException) {
                cause = "用户名已存在";
            }
            return new ResponseEntity().success(false).addKeyValuePair("cause", cause);
        }
    }

    /**
     * 检查昵称
     *
     * @param nickname
     * @return
     */
    @GetMapping("/check/nickname")
    public ResponseEntity checkNickname(@RequestParam String nickname) {
        try {
            userInfoCheckService.checkNickname(nickname);
            return new ResponseEntity().success(true);
        } catch (UserInfoException e) {
            String cause = "";
            if (e instanceof NicknameExistException) {
                cause = "昵称已被占用";
            }
            return new ResponseEntity().success(false).addKeyValuePair("cause", cause);
        }
    }
}
