package com.xiaobu.auth.admin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * token校验失败的自定义异常
 *
 * @author qichao
 * @create 2018-11-01
 **/
public class CustomAuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

	private WebResponseExceptionTranslator exceptionTranslator = new DefaultWebResponseExceptionTranslator();

	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException,
			ServletException {
		//解析异常
		try {
			ResponseEntity<?> result = exceptionTranslator.translate(authException);
			logger.info(result.getStatusCode().toString());
			Map map = new HashMap();
			map.put("code", "401");
			map.put("message", authException.getMessage());
			map.put("path", request.getServletPath());
			map.put("timestamp", String.valueOf(new Date().getTime()));
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getOutputStream(), map);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
