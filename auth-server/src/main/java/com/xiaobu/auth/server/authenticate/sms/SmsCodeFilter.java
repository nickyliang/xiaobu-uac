package com.xiaobu.auth.server.authenticate.sms;

import com.xiaobu.auth.server.properties.SecurityConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 短信验证码校验filter
 *
 * @author qichao
 * @create 2018-10-31
 **/
@Component("smsCodeValidateFilter")
public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

	private static final String SMS_CODE_TYPE = "sms";

	@Value("${server.servlet.context-path}")
	private String context_path;

	/**
	 * 验证码校验失败处理器
	 */
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

	/**
	 * 验证请求url与配置的url是否匹配的工具类
	 */
	private AntPathMatcher pathMatcher = new AntPathMatcher();

	/**
	 * 存放所有需要校验验证码的url
	 */
	private Map<String, String> urlMap = new HashMap<>();

	@Autowired
	SmsCodeProcessor smsCodeProcessor;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,
			IOException {
		String type = getValidateCodeType(request);
		logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
		if(StringUtils.isNotEmpty(type)) {
			String mobile = ServletRequestUtils.getStringParameter(request,"mobile");
			String smsCode = ServletRequestUtils.getStringParameter(request,"code");
			try{
				smsCodeProcessor.validate(mobile, smsCode);
			}catch (SmsCodeException exception) {
				authenticationFailureHandler.onAuthenticationFailure(request,response,exception);
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * 初始化要拦截的url配置
	 */
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		urlMap.put(context_path + SecurityConstant.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE, SMS_CODE_TYPE);
	}


	/**
	 * 获取校验码的类型，如果当前请求不需要校验，则返回null
	 *
	 * @param request
	 */
	private String getValidateCodeType(HttpServletRequest request) {
		String result = null;
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
			Set<String> urls = urlMap.keySet();
			for (String url : urls) {
				if (pathMatcher.match(url, request.getRequestURI())) {
					result = urlMap.get(url);
				}
			}
		}
		return result;
	}


}
