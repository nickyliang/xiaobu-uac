package com.xiaobu.auth.admin.controller;

import com.xiaobu.auth.core.supports.BaseException;
import com.xiaobu.auth.core.supports.RestfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统账号open服务
 *
 * @author qichao
 * @create 2018-10-23
 **/
@RestController
@RequestMapping("/api/account")
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	/**
	 * 根据输入信息，获取系统账号
	 * @return RestfulResponse
	 */
	@GetMapping("/get")
	public RestfulResponse getAccount() {
		RestfulResponse<String> restfulResponse = new RestfulResponse<>(0, "initialized",
				null);
		try {
			String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			restfulResponse.setCode(0);
			restfulResponse.setMessage("接口调用成功！");
			restfulResponse.setData(username);
		} catch (BaseException ex) {
			restfulResponse.setCode(ex.getCode());
			restfulResponse.setMessage(ex.getMessage());
		}
		return restfulResponse;
	}
}
