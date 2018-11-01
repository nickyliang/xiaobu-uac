package com.xiaobu.auth.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaobu.auth.server.dto.AccountDTO;
import org.apache.ibatis.annotations.Param;

/**
 * 系统账号Dao
 *
 * @author qichao
 * @create 2018-10-22
 **/
public interface AccountDTOMapper extends BaseMapper<AccountDTO> {

	AccountDTO getAccountDTOByIdentification(@Param("identification") String identification);
}
