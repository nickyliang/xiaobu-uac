package com.xiaobu.auth.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 * @author qichao
 * @create 2018-10-23
 **/
@SpringBootApplication
@ComponentScan({"com.xiaobu.auth.admin","com.xiaobu.auth.core"})
public class AuthAdminApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AuthAdminApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthAdminApplication.class, args);
	}
}
