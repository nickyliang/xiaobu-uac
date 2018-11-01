package com.xiaobu.auth.core.util.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xiaobu.auth.core.util.common.HttpClientUtil;
import com.xiaobu.auth.core.util.sms.settings.SmsSettings;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.ShortUrl;
import com.yunpian.sdk.model.SmsRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

//短信统计的Sevice
@Component
public class SmsStatUtil {

	//日期格式
	private static String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

	//获取账单
	private static String GETTOTALFEE = "https://sms.yunpian.com/v2/sms/get_total_fee.json";

	private static int socketTimeout = 10000;

	private static final Logger logger = LoggerFactory.getLogger(SmsStatUtil.class);


	private static SmsSettings smsSettings;

	@Autowired
	public void setSmsSettings(SmsSettings smsSettings) {
		SmsStatUtil.smsSettings = smsSettings;
	}


	/**
	 * 查短信发送记录
	 *
	 * @param phone 需要查询的手机号
	 * @param startDate 短信发送开始时间
	 * @param endDate 短信发送结束时间
	 * @param pageNum 页码，默认值为1
	 * @param pageSize 每页个数，最大100个
	 *
	 * @return
	 */
	public static Result<List<SmsRecord>> getRecord(String phone, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {

		//初始化clnt,使用单例方式
		YunpianClient yunpianClient = new YunpianClient(smsSettings.getApiKey()).init();
		//发送短信API
		Map<String, String> param = new HashMap<>();
		if (StringUtils.isNotEmpty(phone)) {
			param.put(YunpianClient.MOBILE, phone);
		}
		if (!Objects.isNull(pageNum)) {
			param.put(YunpianClient.PAGE_NUM, String.valueOf(pageNum));
		}
		if (!Objects.isNull(pageSize)) {
			param.put(YunpianClient.PAGE_SIZE, String.valueOf(pageSize));
		}
		param.put(YunpianClient.START_TIME, DateFormatUtils.format(startDate, DATEFORMAT));
		param.put(YunpianClient.END_TIME, DateFormatUtils.format(endDate, DATEFORMAT));
		Result<List<SmsRecord>> result = yunpianClient.sms().get_record(param);
		//释放clnt
		yunpianClient.close();
		return result;
	}

	/**
	 * 注册成功回调接口 此功能需联系客服开通。
	 * @param phone 需要查询的手机号
	 * @param date 日期
	 * @return
	 */
	public static Result<Void> regComplete(String phone, Date date) {
		//初始化clnt,使用单例方式
		YunpianClient yunpianClient = new YunpianClient(smsSettings.getApiKey()).init();
		//发送短信API
		Map<String, String> param = yunpianClient.newParam(2);
		param.put(YunpianClient.MOBILE, phone);
		param.put(YunpianClient.TIME, DateFormatUtils.format(date, DATEFORMAT));
		Result<Void> result = yunpianClient.sms().reg_complete(param);
		//释放clnt
		yunpianClient.close();
		return result;
	}


	/**
	 * 生成短链接
	 * @param longUrl 待转换的长网址，必须http://或https://开头
	 * @param statDuration 点击量统计持续时间（天），过期后不再统计，区间[0,30]，默认0表示不统计
	 * @param provider 生成的链接的域名，传入1是yp2.cn，2是t.cn，默认1
	 * @param name 取名方便区分，默认为“02-27 11:11生产的短链接”
	 *
	 * @return
	 */
	public static Result<ShortUrl> shorten(String longUrl, int statDuration, int provider, String name) {
		//初始化clnt,使用单例方式
		YunpianClient yunpianClient = new YunpianClient(smsSettings.getApiKey()).init();
		Map<String, String> param = yunpianClient.newParam(4);
		param.put(YunpianClient.LONG_URL, longUrl);
		param.put(YunpianClient.STAT_DURATION, String.valueOf(statDuration));
		param.put(YunpianClient.PROVIDER, String.valueOf(provider));
		param.put(YunpianClient.NAME, name);
		Result<ShortUrl> result = yunpianClient.shortUrl().shorten(param);
		//释放clnt
		yunpianClient.close();
		return result;
	}


	/***
	 * 短信点击统计
	 * @param sid 短链接唯一标识，用于获取统计数据
	 * @param startDate 开始时间，默认一个小时前
	 * @param endDate 结束时间，默认当前时间
	 * @return
	 */
	public static Result<Map<String, Long>> stat(String sid, Date startDate, Date endDate) {
		//初始化clnt,使用单例方式
		YunpianClient yunpianClient = new YunpianClient(smsSettings.getApiKey()).init();
		Map<String, String> param = yunpianClient.newParam(4);
		param.put(YunpianClient.SID, sid);
		param.put(YunpianClient.START_TIME, DateFormatUtils.format(startDate, DATEFORMAT));
		param.put(YunpianClient.END_TIME, DateFormatUtils.format(endDate, DATEFORMAT));
		Result<Map<String, Long>> result = yunpianClient.shortUrl().stat(param);
		return result;
	}


	/**
	 * 日账单导出
	 *
	 * @param date
	 *
	 * @return
	 */
	public static Result<Map<String, String>> getTotalFee(Date date) {
		Result<Map<String, String>> result = new Result<>();
		try {
			Map<String, String> param = new HashMap<>();
			param.put(YunpianClient.APIKEY, smsSettings.getApiKey());
			param.put(YunpianClient.DATA, DateFormatUtils.format(date, DATEFORMAT));
			String strReturn = HttpClientUtil.doPost(GETTOTALFEE, param, socketTimeout);
			Map<String, String> map = JSON.parseObject(strReturn, new TypeReference<Map<String, String>>() {
			});
			result.setCode(0);
			result.setMsg("日订单查询成功");
			result.setData(map);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			result.setCode(-1);
			result.setMsg("导出失败");
			result.setDetail(ex.getMessage());
		}
		return result;
	}

}
