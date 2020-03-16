package site.nullpointer.security.components;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SysAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 构造函数
	 * 
	 * @param userDetailsService
	 * @param passwordEncoder
	 */
	public SysAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		if(userDetailsService==null||passwordEncoder==null){
			throw new IllegalArgumentException("构造参数不可为空！");
		}
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * 用户认证
	 */
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");
			throw new BadCredentialsException("需要认证的密码为空！");
		}

		String presentedPassword = authentication.getCredentials().toString();

		if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
			logger.debug("Authentication failed: password does not match stored value");

			throw new BadCredentialsException("密码错误！");
		}
	}

	/**
	 * 根据用户名取得用户
	 */
	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		UserDetails loadedUser;
		try {
			loadedUser = this.userDetailsService.loadUserByUsername(username);
		} catch (UsernameNotFoundException notFound) {
			throw notFound;
		} catch (Exception repositoryProblem) {
			throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
		}
		if (loadedUser == null) {
			throw new InternalAuthenticationServiceException("userDetailsService 返回 null,可能内部出现错误！");
		}
		return loadedUser;

	}
}
