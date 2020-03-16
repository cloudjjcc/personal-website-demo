package site.nullpointer.admin.base.api;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.nullpointer.auth.entity.SysUser;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminBaseApi {

}
