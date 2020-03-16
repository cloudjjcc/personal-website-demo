package site.nullpointer.auth.service;

import site.nullpointer.auth.dto.UserRegisterInfo;
import site.nullpointer.auth.exception.UserInfoException;

public interface UserRegisterService {
	String DEFAULT_AVATAR="http://image.nullpointer.site/avatar/default/logo3.png";

	/**
	 * 用户注册
	 * @param info
	 * @throws UserInfoException
	 */
	void register(UserRegisterInfo info) throws UserInfoException;
}
