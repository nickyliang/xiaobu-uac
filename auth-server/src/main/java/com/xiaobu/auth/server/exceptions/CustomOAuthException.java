package com.xiaobu.auth.server.exceptions;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author qichao
 * @create 2018-11-01
 **/
public class CustomOAuthException extends OAuth2Exception{
	public CustomOAuthException(String msg) {
		super(msg);
	}
}
