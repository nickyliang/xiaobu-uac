package com.xiaobu.auth.core.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统账号：用以登录系统的凭据
 * @author qichao
 * @create 2018-10-19
 **/
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("fs_account")
public class Account extends Model<Account> {

	@TableId(value="id", type= IdType.AUTO)
	private Long id;

	private String password;

	private String status;

	@TableField("gmt_create")
	private Date gmtCreate;

	@TableField("gmt_modified")
	private Date gmtModified;

}
