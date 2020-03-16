package site.nullpointer.admin.user_manager.dto;

import java.io.Serializable;

/**
 * @author wangjc
 * @Title: UserUpdateInfo
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/2513:58
 */
public class UserUpdateInfo implements Serializable {
    private String nickname;//昵称
    private String avatar;//头像
    private String id;//id
    private Boolean enabled;//是否启用
    private Boolean accountLocked;//账号锁定
    private String oldPassword;//原密码
    private String newPassword;//新密码

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }
}
