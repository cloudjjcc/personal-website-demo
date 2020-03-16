package site.nullpointer.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import site.nullpointer.auth.repository.SysUserRepository;

public class SysUserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private SysUserRepository repository;
	@Autowired
	private UserCache userCache;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = userCache.getUserFromCache(username);
		if (user == null) {
			user = repository.findSysUserByUsername(username);
		}
		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在！");
		}
		userCache.putUserInCache(user);
		return user;
	}

	public SysUserRepository getRepository() {
		return repository;
	}

	public void setRepository(SysUserRepository repository) {
		this.repository = repository;
	}

	public UserCache getUserCache() {
		return userCache;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

}
