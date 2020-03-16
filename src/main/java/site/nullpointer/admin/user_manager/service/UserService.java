package site.nullpointer.admin.user_manager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.nullpointer.admin.user_manager.dto.UserInfo;
import site.nullpointer.admin.user_manager.dto.UserUpdateInfo;

/**
 * @author wangjc
 * @Title: UserService
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/2421:08
 */
public interface UserService {
    /**
     * 获取当前用户信息
     * @return
     */
    UserInfo getUserInfo();

    /**
     * 获取指定用户信息
     * @param userId
     * @return
     */
    UserInfo getUserInfoById(String userId);

    /**
     * 查询用户列表
     * @param
     * @return
     */
    Page<UserInfo> getUserList(String username, String nickname, int accountLocked, int accountExpired, int enabled, Pageable pageable);

    /**
     * 更新用户信息
     * @param userInfo
     * @return
     */
    UserInfo updateUserInfo(UserUpdateInfo userInfo);

    /**
     * 删除用户信息
     * @param id
     */
    void deleteUser(String id);
}
