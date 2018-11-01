package com.xiaobu.auth.server.service;


import com.xiaobu.auth.server.dto.OrgDTO;

/**
 * 组织(Org)服务接口
 *
 * @author qichao
 * @create 2018-10-22
 **/
public interface OrgService {
	/**
	 * 创建组织
	 * @param orgDTO
	 */
	Long createOrg(OrgDTO orgDTO);
}
