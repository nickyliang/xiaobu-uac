package com.xiaobu.auth.server.dto;

import lombok.Data;

/**
 * 账号业务模型
 * @author qichao
 * @create 2018-10-24
 **/
@Data
public class AccountDTO {

	private Long id;

	private String username;

	private String password;

	private Long userId; // 账号对应的user id

	private Long orgId; // 对应的user所属的org id

	private Boolean expired; // 账号是否过期，过期后无法登录

	private Boolean locked; // 账号是否锁定，锁定后无法登录

	private Boolean enabled; // 账号是否启用，未启用的账号无法登录

	private Boolean credenceExpired; // 登录密码是否过期，密码过期后将无法登录

	private Boolean userRoot; // 是否为组织root用户

	private Boolean userAdmin; //是否为组织管理员
}
