package com.xiaobu.auth.server.authenticate.sms;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 短信登录验证逻辑
 *
 * @author qichao
 * @create 2018-10-25
 **/
public class SmsAuthenticationProvider implements AuthenticationProvider {

	private UserDetailsService userDetailsService;

	/**
	 * 由于短信验证码的验证在过滤器里已完成，这里直接读取用户信息即可
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws
			AuthenticationException {
		SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
		UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken
				.getPrincipal());
		if (user == null) {
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		}
		SmsAuthenticationToken authenticationResult = new SmsAuthenticationToken(user,user.getAuthorities());
		authenticationResult.setDetails(authenticationToken.getDetails());
		return authenticationResult;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return SmsAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
}
