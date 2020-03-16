package site.nullpointer.admin.user_manager.dto;

import site.nullpointer.auth.entity.SysUser;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangjc
 * @Title: UserInfo
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/2421:11
 */
public class UserInfo implements Serializable {
    private String username;//用户名
    private String nickname;//昵称
    private String avatar;//头像
    private String id;//id
    private Date createDate;//创建时间
    private boolean accountExpired;//账号过期
    private boolean enabled;//启用
    private boolean accountLocked;//账号锁定
    public UserInfo(SysUser sysUser){
        this.username=sysUser.getUsername();
        this.nickname=sysUser.getNickname();
        this.avatar=sysUser.getAvatar();
        this.id=sysUser.getId();
        this.createDate=sysUser.getCreateDate();
        this.accountExpired=sysUser.isAccountExpired();
        this.accountLocked=sysUser.isAccountLocked();
        this.enabled=sysUser.isEnabled();
    }
    public UserInfo(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }
}
