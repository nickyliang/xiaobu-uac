
package com.xiaobu.auth.server.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@MapperScan("com.xiaobu.auth.server.dao")
public class MybatisPlusConfig {
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

	/**
	 * SQL执行效率插件
	 */
	@Bean
	@Profile({"dev","test"})// 设置 dev authorize 环境开启
	public PerformanceInterceptor performanceInterceptor() {
		return new PerformanceInterceptor();
	}
}

