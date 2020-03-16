package site.nullpointer.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.nullpointer.auth.exception.NicknameExistException;
import site.nullpointer.auth.exception.UsernameExistException;
import site.nullpointer.auth.repository.SysUserRepository;
import site.nullpointer.auth.service.UserInfoCheckService;

/**
 * @author wangjc
 * @Title: UserInfoCheckServiceImpl
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/2515:22
 */
@Service
public class UserInfoCheckServiceImpl implements UserInfoCheckService {
    @Autowired
    private SysUserRepository sysUserRepository;
    @Override
    public void checkUsername(String username) {
        if(sysUserRepository.findSysUserByUsername(username) != null){
            throw new UsernameExistException();
        }
    }

    @Override
    public void checkNickname(String nickname) {
        if(sysUserRepository.findSysUserByNickname(nickname) != null){
            throw new NicknameExistException();
        }
    }
}
