package site.nullpointer.auth.service.impl;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import site.nullpointer.auth.dto.UserRegisterInfo;
import site.nullpointer.auth.entity.SysUser;
import site.nullpointer.auth.exception.CaptchaErrorException;
import site.nullpointer.auth.exception.UserInfoException;
import site.nullpointer.auth.exception.UsernameExistException;
import site.nullpointer.auth.repository.SysUserRepository;
import site.nullpointer.auth.service.UserRegisterService;
import site.nullpointer.utils.RedisUtils;

@Service
@Transactional
public class UserRegisterServiceImpl implements UserRegisterService {
	@Autowired
	private SysUserRepository sysUserRepository;
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void register(UserRegisterInfo info) throws UserInfoException {
		if(StringUtils.isNotBlank(info.getUsername())
				&&(sysUserRepository.findSysUserByUsername(info.getUsername())==null)) {
			String captchaText="";
			try {
				captchaText = redisUtils.getString(info.getCaptchaKey());
			}catch(Exception e) {
				e.printStackTrace();
			}
			if(StringUtils.isNoneBlank(captchaText)&&
					StringUtils.equalsIgnoreCase(captchaText, info.getCaptchaCode())){
				SysUser user=new SysUser();
				user.setUsername(info.getUsername());
				user.setPassword(passwordEncoder.encode(info.getPassword()));
				user.setEnabled(true);
				user.setAvatar(DEFAULT_AVATAR);
				user.setNickname("用户_"+(sysUserRepository.count()+1));
				sysUserRepository.save(user);
			}else {
				throw new CaptchaErrorException();
			}
		}else {
			throw new UsernameExistException();
		}
	}

}
