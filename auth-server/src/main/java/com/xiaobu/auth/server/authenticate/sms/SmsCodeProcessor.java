package com.xiaobu.auth.server.authenticate.sms;

import com.xiaobu.auth.core.util.common.RandCodeUtil;
import com.xiaobu.auth.core.util.redis.RedisUtil;
import com.xiaobu.auth.core.util.sms.SmsSendUtil;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 短信验证码处理器
 * @author qichao
 * @create 2018-10-31
 **/
@Component
public class SmsCodeProcessor {

	Logger logger = LoggerFactory.getLogger(getClass());

	private static final String MSG_TPL = "【慧运车】您的验证码是";

	@Autowired
	RedisUtil redisUtil;

	/**
	 * 生成短信验证码，随机6位数
	 * @return
	 */
	private String generate(int digit) {
		return RandCodeUtil.getRandNumber(digit);
	}

	/**
	 * 向某个手机号发送短信验证码
	 * @param mobile
	 */
	private Result<SmsSingleSend> send(String mobile, String smsCode) {
		String message = MSG_TPL + smsCode;
		Result<SmsSingleSend> result = SmsSendUtil.singleSend(mobile, message, true);
		return result;
	}

	/**
	 * 保存某个已发送到某手机号的验证码至Redis
	 */
	private void save(String mobile, int code) {
		redisUtil.set(mobile,code);
	}

	/**
	 * 1.生成验证码
	 * 2.向某手机号发送
	 * 3.保存至Redis
	 */
	public void createSendAndSave(String mobile, int digit) throws Exception {
		String smsCode = generate(digit);
		logger.info("生成的短信验证码为："+smsCode);
		Result<SmsSingleSend> result = send(mobile,smsCode);
		if(result.getCode()!=0) {
			throw new Exception("发送短信验证码失败！");
		}else {
			save(mobile, Integer.valueOf(smsCode));
		}
	}

	/**
	 * 验证短信验证码是否正确(是否存在redis的键值对)
	 * @param mobile
	 * @param code
	 */
	public void validate(String mobile, String code) {
		if(StringUtils.isEmpty(mobile)) {
			throw new SmsCodeException("手机号为空！");
		}
		if(StringUtils.isEmpty(code)) {
			throw new SmsCodeException("验证码为空！");
		}
		boolean codeExisted = redisUtil.exists(mobile);
		if(!codeExisted) {
			logger.info("未给该手机号发送过验证码，请检查手机号是否正确！");
			throw new SmsCodeException("未给该手机号发送过验证码，请检查手机号是否正确！");
		}else if (!StringUtils.equals(redisUtil.get(mobile),code)) {
			logger.info("验证码错误！");
			throw new SmsCodeException("验证码错误！");
		}
		redisUtil.remove(mobile); //验证通过之后，删除redis中的短信验证码
	}
}
