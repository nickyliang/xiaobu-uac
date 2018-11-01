package com.xiaobu.auth.server.authenticate;

import com.xiaobu.auth.server.dto.AccountDTO;
import com.xiaobu.auth.server.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * @author qichao
 * @create 2018-06-09
 **/
@Service("DefaultUserDetailsService")
public class DefaultUserDetailsService implements UserDetailsService {

	private static final String ADMIN_ROLE_STR = "ROLE_ADMIN";

	private static final String ROOT_ROLE_STR = "ROLE_ROOT";

	private static final String NORMAL_ROLE_STR = "ROLE_NORMAL";


	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AccountService accountService;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		AccountDTO accountDTO = accountService.getAccountDTOByIdentification(s);
		if (Objects.isNull(accountDTO)) throw new UsernameNotFoundException("账号不存在！");
		DefaultUserDetails userDetails = new DefaultUserDetails(accountDTO);
		this.setAuthorize(userDetails,accountDTO);
		return userDetails;
	}

	/**
	 * 设置角色
	 */
	public void setAuthorize(DefaultUserDetails userDetails, AccountDTO accountDTO) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		SimpleGrantedAuthority adminRole = new SimpleGrantedAuthority(ADMIN_ROLE_STR);
		SimpleGrantedAuthority rootRole = new SimpleGrantedAuthority(ROOT_ROLE_STR);
		SimpleGrantedAuthority normalRole = new SimpleGrantedAuthority(NORMAL_ROLE_STR);

		if (Objects.nonNull(accountDTO.getUserRoot()) && accountDTO.getUserRoot()) {
			authorities.add(rootRole);
		}
		if(Objects.nonNull(accountDTO.getUserAdmin()) && accountDTO.getUserAdmin()) {
			authorities.add(adminRole);
		} else {
			authorities.add(normalRole);
		}
		userDetails.setAuthorities(authorities);
	}
}
