package com.xiaobu.auth.server.service;

import com.xiaobu.auth.server.dto.AccountDTO;

/**
 * @author qichao
 * @create 2018-10-24
 **/
public interface AccountService {

	AccountDTO getAccountDTOByIdentification(String identification);
}
