package site.nullpointer.admin.user_manager.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import site.nullpointer.admin.base.api.AdminBaseApi;
import site.nullpointer.admin.user_manager.dto.UserUpdateInfo;
import site.nullpointer.admin.user_manager.exception.NicknameIllegalException;
import site.nullpointer.admin.user_manager.exception.NotExistAnyModifyException;
import site.nullpointer.admin.user_manager.exception.PasswordNotMatchException;
import site.nullpointer.admin.user_manager.service.UserService;
import site.nullpointer.auth.exception.UserInfoException;
import site.nullpointer.common.dto.ResponseEntity;

/**
 * @author wangjc
 * @Title: UserApi
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/2421:01
 */
@RestController
public class UserApi extends AdminBaseApi {
    private final Logger logger= LoggerFactory.getLogger(UserApi.class);
    @Autowired
    private UserService userService;

    /**
     * 查看当前用户信息
     * @return
     */
    @GetMapping("/user/info")
    public ResponseEntity getUserInfo(){
        return new ResponseEntity().success(true).data(userService.getUserInfo());
    }

    /**
     * 查看指定id用户信息
     * @param userId
     * @return
     */
    @GetMapping("/user/{id}")
    public ResponseEntity getUserInfoById(@PathVariable(name="id")String userId){

        try {
            return new ResponseEntity().success(true).data(userService.getUserInfoById(userId));
        } catch (Exception e) {
            logger.error("查看指定id用户信息出错!",e);
            return new ResponseEntity().success(false).addKeyValuePair("cause","用户不存在");
        }
    }

    /**
     * 查询用户列表
     * @param
     * @return
     */
    @GetMapping("/users")
    public ResponseEntity getUserList(@RequestParam(name="username",defaultValue = "") String username,
                                      @RequestParam (name="nickname",defaultValue = "")String nickname,
                                      @RequestParam(name="accountLocked",defaultValue = "-1")int accountLocked,
                                      @RequestParam(name="enabled",defaultValue = "-1") int enabled,
                                      @RequestParam(name="accountExpired",defaultValue = "-1") int accountExpired, Pageable pageable){
        try {
            return new ResponseEntity().success(true).data(userService.getUserList(username,nickname,accountLocked,accountExpired,enabled,pageable));
        } catch (Exception e) {
            logger.error("获取用户列表出错!",e);
            return new ResponseEntity().success(false);
        }
    }

    /**
     * 修改用户信息
     * @param userInfo
     * @param userId
     * @return
     */
    @PutMapping("/user/{id}")
    public ResponseEntity updateUserInfo(@RequestBody UserUpdateInfo userInfo, @PathVariable(name="id") String userId){
        try {
            userInfo.setId(userId);
            return new ResponseEntity().success(true).data(userService.updateUserInfo(userInfo));
        } catch (UserInfoException e) {
            String cause="";
            if (e instanceof NicknameIllegalException){
                cause="非法用户名";
            }else if(e instanceof PasswordNotMatchException){
                cause="密码错误";
            }else if(e instanceof NotExistAnyModifyException){
                cause="没有任何修改动作";
            }
            return new ResponseEntity().success(false).addKeyValuePair("cause",cause);
        }
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return new ResponseEntity().success(true);
    }
}
