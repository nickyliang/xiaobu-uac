package com.xiaobu.auth.server.authenticate.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;

/**
 * @author qichao
 * @create 2018-10-31
 **/
@Component("smsCodeValidateSecurityConfig")
public class SmsCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
	private Filter smsCodeValidateFilter;

	@Override
	public void configure(HttpSecurity http)  {
		http.addFilterBefore(smsCodeValidateFilter, AbstractPreAuthenticatedProcessingFilter.class);
	}
}
