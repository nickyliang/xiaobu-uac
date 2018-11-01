package com.xiaobu.auth.core.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * OAuth客户端模型
 *
 * @author qichao
 * @create 2018-10-30
 **/
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("fs_account")
public class OAuthClient extends Model<Account> {

	@TableId("client_id")
	private String clientId;

	@TableField("resource_ids")
	private String resourceIds;

	@TableField("client_secret")
	private String clientSecret;

	private String scope;

	@TableField("grant_types")
	private String grantTypes = "authorization_code,refresh_token";

	@TableField("redirect_uri")
	private String redirectUri;

	@TableField("access_token_validity")
	private Integer accessTokenValidity;

	@TableField("refresh_token_validity")
	private Integer refreshTokenValidity;

	private Boolean archived;

	@TableField("gmt_create")
	private Date gmtCreate;

	@TableField("gmt_modified")
	private Date gmtModified;

}
