package com.xiaobu.auth.server.authenticate;

import com.alibaba.fastjson.JSON;
import com.xiaobu.auth.core.supports.RestfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qichao
 * @create 2018-10-25
 **/
@Component
public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
	                                    AuthenticationException exception) throws IOException {

		logger.info("登录失败！");
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setContentType("application/json;charset=UTF-8");
		RestfulResponse<String> result = new RestfulResponse<>();
		result.setCode(HttpServletResponse.SC_UNAUTHORIZED);
		result.setMessage(exception.getMessage());
		result.setData("");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(JSON.toJSONString(result));
	}
}
