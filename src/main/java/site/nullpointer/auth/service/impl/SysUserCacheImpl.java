package site.nullpointer.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import site.nullpointer.auth.entity.SysUser;
import site.nullpointer.utils.RedisUtils;

/**
 * 用户信息缓存实现类
 * 
 * @author cloud-c
 *
 */
@Component
public class SysUserCacheImpl implements UserCache {
	@Autowired
	private RedisUtils redisUtils;
	@Value("${usercache.timeout}")
	private long userCacheTimeout;
	private final Logger logger = LoggerFactory.getLogger(SysUserCacheImpl.class);

	@Override
	public UserDetails getUserFromCache(String username) {
		logger.info("get " + username + "'s userDetails from cache");
		return redisUtils.getObject(username, SysUser.class);
	}

	@Override
	public void putUserInCache(UserDetails user) {
		logger.info("put " + user.getUsername() + "'s userDetails to cache");
		redisUtils.setObject(user.getUsername(), user, userCacheTimeout);
	}

	public RedisUtils getRedisUtils() {
		return redisUtils;
	}

	public void setRedisUtils(RedisUtils redisUtils) {
		this.redisUtils = redisUtils;
	}

	public long getUserCacheTimeout() {
		return userCacheTimeout;
	}

	public void setUserCacheTimeout(long userCacheTimeout) {
		this.userCacheTimeout = userCacheTimeout;
	}

	@Override
	public void removeUserFromCache(String username) {
		logger.info("remove " + username + "'s userDetails from cache");
		redisUtils.delete(username);
	}

}
