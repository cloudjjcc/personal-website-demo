package site.nullpointer.admin.user_manager.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import scala.collection.parallel.ParIterableLike;
import site.nullpointer.admin.user_manager.dto.UserInfo;
import site.nullpointer.admin.user_manager.dto.UserUpdateInfo;
import site.nullpointer.admin.user_manager.exception.NotExistAnyModifyException;
import site.nullpointer.admin.user_manager.exception.PasswordNotMatchException;
import site.nullpointer.admin.user_manager.service.UserService;
import site.nullpointer.auth.entity.SysUser;
import site.nullpointer.auth.repository.SysUserRepository;
import site.nullpointer.auth.service.UserBaseInfoService;
import site.nullpointer.www.repository.impl.BaseRepositoryImpl;

import java.util.*;

/**
 * @author wangjc
 * @Title: UserServiceImpl
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/24 21:08
 */
@Service
public class UserServiceImpl implements UserService,UserBaseInfoService {
    @Autowired
    private SysUserRepository userRepository;
    @Autowired
    private BaseRepositoryImpl baseRepository;
    @Autowired
    private PasswordEncoder sysPasswordEncoder;
    @Autowired
    private UserCache userCache;
    @Autowired
    private SessionRegistry sysSessionRegistry;
    @Override
    public UserInfo getUserInfo() {
        return new UserInfo(getCurrentUser());
    }

    @Override
    public UserInfo getUserInfoById(String userId) {
        return new UserInfo(userRepository.findSysUserById(userId));
    }

    @Override
    public Page<UserInfo> getUserList(String username,String nickname,int accountLocked,int accountExpired,int enabled,Pageable pageable) {
        StringBuilder sql=new StringBuilder("SELECT id,avatar,username,nickname,account_locked,account_expired,enabled,create_date FROM sys_user WHERE 1=1 ");
        Map<String,Object> params=new HashMap<>();
        /**
         * 用户名模糊搜索
         */
        if(StringUtils.isNoneBlank(username)){
            sql.append(" AND username LIKE :username ");
            params.put("username","%"+username+"%");
        }
        /**
         * 昵称模糊搜索
         */
        if(StringUtils.isNoneBlank(nickname)){
            sql.append(" AND nickname LIKE :nickname ");
            params.put("nickname","%"+nickname+"%");
        }
        /**
         * 账号过期条件
         */
        if(accountExpired!=-1){
            sql.append(" AND account_expired=:account_expired ");
            params.put("account_expired",accountExpired);
        }
        /**
         * 账号锁定条件
         */
        if(accountLocked!=-1){
            sql.append(" AND account_locked=:account_locked ");
            params.put("account_locked",accountLocked);
        }
        /**
         * 账号启用条件
         */
        if(enabled!=-1){
            sql.append(" AND enabled=:enabled ");
            params.put("enabled",enabled);
        }
        /**
         * 默认按创建时间倒序
         */
        sql.append(" ORDER BY create_date DESC ");
        return baseRepository.beanPage(sql.toString(),params,pageable,new UserInfo());
    }

    @Override
    public UserInfo updateUserInfo(UserUpdateInfo userInfo) {
        SysUser sysUser= userRepository.findById(userInfo.getId()).get();
        String nickname=userInfo.getNickname();
        String avatar=userInfo.getAvatar();
        String oldPassword=userInfo.getOldPassword();
        String newPassword=userInfo.getNewPassword();
        Boolean enabled=userInfo.getEnabled();
        Boolean accountLocked=userInfo.getAccountLocked();
        int modifyCount=0;//修改计数
        /**
         * 修改昵称
         */
        if(StringUtils.isNoneBlank(nickname)&&!StringUtils.equals(nickname,sysUser.getNickname())){
            sysUser.setNickname(nickname);
            modifyCount++;
        }
        /**
         * 修改头像
         */
        if(StringUtils.isNoneBlank(avatar)&&!StringUtils.equals(avatar,sysUser.getAvatar())){
            sysUser.setAvatar(avatar);
            modifyCount++;
        }
        /**
         * 修改密码
         */
        if(StringUtils.isNoneBlank(oldPassword)&&StringUtils.isNoneBlank(newPassword)&&!StringUtils.equals(oldPassword,newPassword)){
          if(!sysPasswordEncoder.matches(oldPassword,sysUser.getPassword())){
           throw new PasswordNotMatchException();
          }
          sysUser.setPassword(sysPasswordEncoder.encode(newPassword));
            modifyCount++;
        }
        /**
         * 修改启用状态
         */
        if (enabled!=null&&enabled!=sysUser.isEnabled()){
            sysUser.setEnabled(enabled);
            modifyCount++;
        }
        /**
         * 修改锁定状态
         */
        if (accountLocked!=null&&accountLocked!=sysUser.isAccountLocked()){
            sysUser.setAccountLocked(accountLocked);
            modifyCount++;
        }
        if(modifyCount>0){
            SysUser user = userRepository.save(sysUser);
            //修改用户信息后清空用户缓存
            userCache.removeUserFromCache(sysUser.getUsername());
            //失效session
            List<SessionInformation> sessionInformations = sysSessionRegistry.getAllSessions(user, false);
            for (SessionInformation sessionInformation : sessionInformations) {
                sessionInformation.expireNow();
            }
            return new UserInfo(user);
        }else {
            throw new NotExistAnyModifyException();
        }
    }

    @Override
    public void deleteUser(String id) {
        try {
            SysUser user = userRepository.findById(id).get();
            userRepository.delete(user);
            //修改用户信息后清空用户缓存
            userCache.removeUserFromCache(user.getUsername());
            //失效session
            List<SessionInformation> sessionInformations = sysSessionRegistry.getAllSessions(user, false);
            for (SessionInformation sessionInformation : sessionInformations) {
                sessionInformation.expireNow();
            }
        }catch (NoSuchElementException e){

        }
    }
}
