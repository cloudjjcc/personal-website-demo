package site.nullpointer.auth.service;

/**
 * @author wangjc
 * @Title: UserInfoCheckService
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/2515:19
 */
public interface UserInfoCheckService {
    /**
     * 检查用户名
     * @param username
     */
    void checkUsername(String username);

    /**
     * 检查昵称
     * @param nickname
     */
    void checkNickname(String nickname);
}
