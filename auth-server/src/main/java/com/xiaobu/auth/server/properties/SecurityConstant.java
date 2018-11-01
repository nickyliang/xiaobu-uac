package com.xiaobu.auth.server.properties;

/**
 * @author qichao
 *
 */
public interface SecurityConstant {

	/**
	 * 默认的获取/更新token的url，可在配置文件中替换
	 */
	String DEFAULT_OAUTH_CODE_URL = "/oauth2/token";
	/**
	 * 默认的用户名密码登录请求处理url
	 */
	String DEFAULT_SIGN_IN_PROCESSING_URL_FORM = "/auth/form";
	/**
	 * 默认的短信验证码登录请求处理url
	 */
	String DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE = "/auth/sms";
	/**
	 * 默认的OPENID登录请求处理url
	 */
	String DEFAULT_SIGN_IN_PROCESSING_URL_OPENID = "/auth/openid";
	/**
	 * 默认登录页面
	 */
	String DEFAULT_SIGN_IN_PAGE_URL = "/signIn.html";
	/**
	 * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
	 */
	String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";
	/**
	 * openid参数名
	 */
	String DEFAULT_PARAMETER_NAME_OPENID = "openId";
	/**
	 * providerId参数名
	 */
	String DEFAULT_PARAMETER_NAME_PROVIDERID = "providerId";
	/**
	 * 获取第三方用户信息的url
	 */
	String DEFAULT_SOCIAL_USER_INFO_URL = "/social/user";
}
