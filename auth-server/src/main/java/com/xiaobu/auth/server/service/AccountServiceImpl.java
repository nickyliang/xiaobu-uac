package com.xiaobu.auth.server.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobu.auth.server.dao.AccountDTOMapper;
import com.xiaobu.auth.server.dto.AccountDTO;
import org.springframework.stereotype.Service;

/**
 * @author qichao
 * @create 2018-10-24
 **/
@Service
public class AccountServiceImpl extends ServiceImpl<AccountDTOMapper,AccountDTO> implements AccountService{

	@Override
	public AccountDTO getAccountDTOByIdentification(String identification) {
		return baseMapper.getAccountDTOByIdentification(identification);
	}
}
