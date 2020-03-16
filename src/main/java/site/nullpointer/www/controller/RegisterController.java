package site.nullpointer.www.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import site.nullpointer.auth.dto.UserRegisterInfo;
import site.nullpointer.auth.exception.CaptchaErrorException;
import site.nullpointer.auth.exception.UserInfoException;
import site.nullpointer.auth.exception.UsernameExistException;
import site.nullpointer.auth.service.UserRegisterService;
import site.nullpointer.common.dto.ResponseEntity;
import site.nullpointer.utils.ClientUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjc
 * @Title: RegisterController
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/1817:13
 */
@Controller
public class RegisterController {
    @Autowired
    private UserRegisterService userRegisterService;

    @GetMapping("/register")
    public ModelAndView register() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("register", model);
    }
}
