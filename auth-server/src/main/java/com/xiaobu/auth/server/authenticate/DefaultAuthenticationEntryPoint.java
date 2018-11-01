package com.xiaobu.auth.server.authenticate;

import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;

/**
 * token验证失败后，http 401，捕捉异常可自动刷新token
 * @author qichao
 * @create 2018-10-29
 **/
public class DefaultAuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint{
}
