package com.xiaobu.auth.server.authenticate.form;

import com.xiaobu.auth.server.authenticate.DefaultAuthenticationFailureHandler;
import com.xiaobu.auth.server.authenticate.DefaultAuthenticationSuccessHandler;
import com.xiaobu.auth.server.properties.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

/**
 * 表单(用户名+密码)登录安全配置
 * @author qichao
 * @create 2018-10-25
 **/
@Component
public class FormAuthenticationConfig {

	@Autowired
	DefaultAuthenticationSuccessHandler defaultAuthenticationSuccessHandler;

	@Autowired
	DefaultAuthenticationFailureHandler defaultAuthenticationFailureHandler;

	public void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				.loginPage(SecurityConstant.DEFAULT_SIGN_IN_PAGE_URL)
				.loginProcessingUrl(SecurityConstant.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
				.successHandler(defaultAuthenticationSuccessHandler)
				.failureHandler(defaultAuthenticationFailureHandler);
	}
}

