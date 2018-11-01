package com.xiaobu.auth.server.authenticate.sms;


import org.springframework.security.core.AuthenticationException;

/**
 * @author qichao
 * @create 2018-10-31
 **/
public class SmsCodeException extends AuthenticationException {

	private static final long serialVersionUID = -7285211528095468156L;

	public SmsCodeException(String msg) {
		super(msg);
	}
}
