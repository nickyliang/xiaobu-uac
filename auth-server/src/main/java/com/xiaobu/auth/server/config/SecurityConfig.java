package com.xiaobu.auth.server.config;

import com.xiaobu.auth.server.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author qichao
 * @create 2018-10-29
 **/
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig {

}
