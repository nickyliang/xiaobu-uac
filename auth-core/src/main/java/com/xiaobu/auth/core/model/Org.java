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
 * 可以是正式的公司（有注册，有税号taxID），也可以是非正式的一般性组织（比如：车队，公司内部的部门等）
 * @author qichao
 * @create 2018-10-20
 **/
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("fs_org")
public class Org extends Model<Org> {

	@TableId(value="id", type= IdType.AUTO)
	private Long id;

	private String code;

	@TableField("full_name")
	private String fullName;

	/**
	 * 公司的纳税人识别号
	 */
	@TableField("tax_id")
	private String taxId;

	@TableField("gmt_create")
	private Date gmtCreate;

	@TableField("gmt_modified")
	private Date gmtModified;

}
