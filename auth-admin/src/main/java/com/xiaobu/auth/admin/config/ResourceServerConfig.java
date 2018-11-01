package com.xiaobu.auth.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * Api(资源服务器)配置
 *
 * @author qichao
 * @create 2018-10-23
 **/
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	public static final String RESOURCE_ID = "account_api";

	@Autowired
	CustomAccessDeniedHandler customAccessDeniedHandler;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
				.accessDeniedHandler(customAccessDeniedHandler);
		resources.resourceId(RESOURCE_ID);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/api/**").authenticated()
				.and()
				.authorizeRequests()
				.anyRequest()
				.authenticated();
	}
}
