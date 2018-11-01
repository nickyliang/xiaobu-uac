package com.xiaobu.auth.server.authenticate;

import com.xiaobu.auth.server.dto.AccountDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * spring authorize userdetails的实现
 * @author qichao
 * @create 2018-05-20
 **/
public class DefaultUserDetails implements UserDetails {

	private String username;

	private String password;

	private Long userId; // 对应的user id

	private Long orgId; // 对应的user所属的org id

	private Boolean expired; // 账号是否过期，过期后无法登录

	private Boolean locked; // 账号是否锁定，锁定后无法登录

	private Boolean enabled; // 账号是否启用，未启用的账号无法登录

	private Boolean credenceExpired; // 登录密码是否过期，密码过期后将无法登录

	private Set<GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}


	public DefaultUserDetails(AccountDTO accountDTO) {
		this.username = accountDTO.getUsername();
		this.password = accountDTO.getPassword();
		this.enabled = accountDTO.getEnabled();
		this.expired = accountDTO.getExpired();
		this.locked = accountDTO.getLocked();
		this.credenceExpired = accountDTO.getCredenceExpired();
		this.userId = accountDTO.getUserId();
		this.orgId = accountDTO.getOrgId();
	}


	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !this.expired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !this.locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !this.credenceExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Boolean getExpired() {
		return expired;
	}

	public void setExpired(Boolean expired) {
		this.expired = expired;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}

