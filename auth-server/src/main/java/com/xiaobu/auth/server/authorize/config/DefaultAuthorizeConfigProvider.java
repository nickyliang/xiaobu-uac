package com.xiaobu.auth.server.authorize.config;


import com.xiaobu.auth.server.properties.SecurityConstant;
import com.xiaobu.auth.server.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 核心模块的授权配置提供器，安全模块涉及的url的授权配置在这里。
 * @author qichao
 */
@Component
@Order(Integer.MIN_VALUE)
public class DefaultAuthorizeConfigProvider implements AuthorizeConfigProvider {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		config.antMatchers(SecurityConstant.DEFAULT_SIGN_IN_PAGE_URL,
				SecurityConstant.DEFAULT_SIGN_IN_PROCESSING_URL_FORM,
				SecurityConstant.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
				SecurityConstant.DEFAULT_SIGN_IN_PROCESSING_URL_OPENID,
				securityProperties.getBrowser().getSignInPage(), 
				securityProperties.getBrowser().getSignUpUrl(),
				"/swagger-resources/**",
				"/swagger-ui.html",
				"/webjars/**",
				"/v2/api-docs").permitAll();

//		if (StringUtils.isNotBlank(securityProperties.getBrowser().getSignOutUrl())) {
//			config.antMatchers(securityProperties.getBrowser().getSignOutUrl()).permitAll();
//		}
		return false;
	}

}
