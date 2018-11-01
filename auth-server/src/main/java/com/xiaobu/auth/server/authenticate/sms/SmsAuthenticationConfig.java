package com.xiaobu.auth.server.authenticate.sms;

import com.xiaobu.auth.server.authenticate.DefaultAuthenticationFailureHandler;
import com.xiaobu.auth.server.authenticate.DefaultAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 短信登录安全配置
 *
 * @author qichao
 * @create 2018-10-25
 **/
@Component
public class SmsAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,
		HttpSecurity> {

	@Autowired
	DefaultAuthenticationSuccessHandler defaultAuthenticationSuccessHandler;

	@Autowired
	DefaultAuthenticationFailureHandler defaultAuthenticationFailureHandler;

	@Qualifier("DefaultUserDetailsService")
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void configure(HttpSecurity http) {

		SmsAuthenticationFilter smsCodeAuthenticationFilter = new SmsAuthenticationFilter();
		smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject
				(AuthenticationManager.class));
		smsCodeAuthenticationFilter.setAuthenticationSuccessHandler
				(defaultAuthenticationSuccessHandler);
		smsCodeAuthenticationFilter.setAuthenticationFailureHandler
				(defaultAuthenticationFailureHandler);
		SmsAuthenticationProvider smsCodeAuthenticationProvider = new
				SmsAuthenticationProvider();
		smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);

		http.authenticationProvider(smsCodeAuthenticationProvider)
				.addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter
						.class);

	}

}
