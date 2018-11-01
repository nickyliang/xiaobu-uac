package com.xiaobu.auth.server.authorize.config;

import com.xiaobu.auth.server.authenticate.DefaultUserDetailsService;
import com.xiaobu.auth.server.authenticate.form.FormAuthenticationConfig;
import com.xiaobu.auth.server.authenticate.sms.SmsAuthenticationConfig;
import com.xiaobu.auth.server.authenticate.sms.SmsCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders
		.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration
		.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @author qichao
 * @create 2018-06-02
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DefaultUserDetailsService userDetailsService;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private SmsAuthenticationConfig smsAuthenticationConfig;

	@Autowired
	private FormAuthenticationConfig formAuthenticationConfig;

	@Autowired
	private AuthorizeConfigManager authorizeConfigManager;

	@Autowired
	private SmsCodeSecurityConfig smsCodeSecurityConfig;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		formAuthenticationConfig.configure(http);

		http.apply(smsAuthenticationConfig)
				.and()
				.apply(smsCodeSecurityConfig)
				.and()
				.cors()
				.and().csrf().disable();

		authorizeConfigManager.config(http.authorizeRequests());
	}

}
