package com.xiaobu.auth.server.properties;

/**
 * 浏览器环境配置项
 * 
 * @author qichao
 *
 */
public class BrowserProperties {

	/**
	 * 登录页面，当引发登录行为的url以html结尾时，会跳到这里配置的url上
	 */
	private String signInPage = SecurityConstant.DEFAULT_SIGN_IN_PAGE_URL;
	/**
	 * 社交登录，如果需要用户注册，跳转的页面
	 */
	private String signUpUrl = "/signUp.html";
	

	public String getSignInPage() {
		return signInPage;
	}

	public void setSignInPage(String loginPage) {
		this.signInPage = loginPage;
	}

	public String getSignUpUrl() {
		return signUpUrl;
	}

	public void setSignUpUrl(String signUpUrl) {
		this.signUpUrl = signUpUrl;
	}
	
}
