package com.xiaobu.auth.server.authorize.oauth;

import com.xiaobu.auth.server.authenticate.DefaultUserDetailsService;
import com.xiaobu.auth.server.authorize.oauth.client.CustomJdbcClientDetailsService;
import com.xiaobu.auth.server.exceptions.CustomWebResponseExceptionTranslator;
import com.xiaobu.auth.server.properties.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers
		.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration
		.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration
		.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers
		.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers
		.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.*;

/**
 * OAuth2.0认证服务配置
 * 默认端点/oauth/authorize,/oauth/token,/oauth/check_token,/oauth/confirm_access,/oauth/error
 *
 * @author qichao
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	/**
	 * 使用WebSecurityConfig中定义的AuthenticationManager
	 * 以支持password授权模式
	 */
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	DefaultUserDetailsService userDetailsService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Resource
	private DataSource dataSource; // 当前环境db配置

	@Autowired
	CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator;

	@Bean
	public ClientDetailsService clientDetails() {
		return new CustomJdbcClientDetailsService(dataSource);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//spring authorize 5.x之后要对client secret加密
//		String finalSecret = passwordEncoder.encode("devops");
		clients.
				withClientDetails(clientDetails());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) {
		security
				.tokenKeyAccess("permitAll()") //url:/oauth/token_key,exposes public key for token
				// verification if using JWT tokens
				.checkTokenAccess("isAuthenticated()") //url:/oauth/check_token allow check token
				.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(
				Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));
		endpoints
				.authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService)
				.tokenEnhancer(tokenEnhancerChain)
				.tokenStore(jwtTokenStore())
				.accessTokenConverter(jwtAccessTokenConverter());
		endpoints.exceptionTranslator(customWebResponseExceptionTranslator); // 自定义登录失败异常信息
		endpoints.pathMapping("/oauth/token", SecurityConstant.DEFAULT_OAUTH_CODE_URL);
	}

	@Bean
	public TokenStore jwtTokenStore() {
		JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter());
		return jwtTokenStore;
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("uac");
		return converter;
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}

	public class CustomTokenEnhancer implements TokenEnhancer {
		@Override
		public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication
				authentication) {
			Map<String, Object> additionalInfo = new HashMap<>();
			String username = authentication.getName();
//			DefaultUserDetails userDetails = (DefaultUserDetails) authentication
//					.getPrincipal();
			List<GrantedAuthority> roles = (List<GrantedAuthority>) authentication
					.getAuthorities();
			additionalInfo.put("gmtCreate", String.valueOf(new Date().getTime()));
			additionalInfo.put("roles", roles);
			additionalInfo.put("username", String.valueOf(username));
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
			return accessToken;
		}
	}
}
