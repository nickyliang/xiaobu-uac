package com.xiaobu.auth.core.util.sms;

import com.alibaba.fastjson.JSON;
import com.xiaobu.auth.core.util.common.HttpClientUtil;
import com.xiaobu.auth.core.util.sms.settings.SmsSettings;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsBatchSend;
import com.yunpian.sdk.model.SmsSingleSend;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by qichao on 2018/06/09.
 */
@Component
public class SmsSendUtil {

	private static final Logger logger = LoggerFactory.getLogger(SmsSendUtil.class);

    //模板单条发送接口的http地址
    private static String URI_TPL_SEND_SMS = "https://sms.yunpian.com/v2/sms/tpl_single_send.json";

    //模板群发接口的http地址
    private static String URI_TPL_Batch_SEND_SMS = "https://sms.yunpian.com/v2/sms/tpl_batch_send.json";

    //连接超时时间
    private static int socketTimeout = 10000;

    private static SmsSettings smsSettings;

	@Autowired
	public void setSmsSettings(SmsSettings smsSettings) {
		SmsSendUtil.smsSettings = smsSettings;
	}


     /**
     * 智能匹配模板单条短信
     * @param phone
     * @param message
     * @return
     */
    public static Result<SmsSingleSend> singleSend(String phone, String message,boolean isRegister){

    	logger.info("the key is {}:",smsSettings.getApiKey());

    	//初始化clnt,使用单例方式
        YunpianClient yunpianClient = new YunpianClient(smsSettings.getApiKey()).init();

        //发送短信API
        Map<String, String> param = yunpianClient.newParam(2);
        param.put(YunpianClient.MOBILE, phone);
        param.put(YunpianClient.TEXT, message);
        //在注册页调用 single_send.json 接口时带上参数“register”（布尔类型），值为“true”，
        param.put(YunpianClient.REGISTER,String.valueOf(isRegister));
        Result<SmsSingleSend> result = yunpianClient.sms().single_send(param);
        //获取返回结果，返回码:r.getCode(),返回码描述:r.getMsg(),API结果:r.getData(),其他说明:r.getDetail(),调用异常:r.getThrowable()

        //释放clnt
        yunpianClient.close();
        return  result;
    }

    /**
     * 智能匹配模板批量发送短信
     * @param phones
     * @param message
     * @return
     */
    public static Result<SmsBatchSend> batchSend(List<String> phones, String message){
        //初始化clnt,使用单例方式
        YunpianClient yunpianClient = new YunpianClient(smsSettings.getApiKey()).init();
        String strPhones = StringUtils.join(phones,",");
        //发送短信API
        Map<String, String> param = yunpianClient.newParam(2);
        param.put(YunpianClient.MOBILE, strPhones);
        param.put(YunpianClient.TEXT, message);
        Result<SmsBatchSend> result = yunpianClient.sms().batch_send(param);
        //释放clnt
        yunpianClient.close();
        return  result;
    }

    /**
     * 批量个性化发送短信
     * @param phones
     * @param messages
     * @return
     */
    public static Result<SmsBatchSend> multiSend(List<String> phones, List<String> messages){
        //初始化clnt,使用单例方式
        YunpianClient yunpianClient = new YunpianClient(smsSettings.getApiKey()).init();
        String strPhones = StringUtils.join(phones,",");
        String strMessages = StringUtils.join(messages,",");

        //发送短信API
        Map<String, String> param = yunpianClient.newParam(2);
        param.put(YunpianClient.MOBILE, strPhones);
        param.put(YunpianClient.TEXT, strMessages);
        Result<SmsBatchSend> result = yunpianClient.sms().multi_send(param);
        //释放clnt
        yunpianClient.close();
        return  result;
    }

    /**
     * 使用模板单条发送短信
     * @param phone
     * @param tpl_id
     * @param tpl_value
     * @return
     */
    public static Result<SmsSingleSend> tplSingleSend(String phone,String tpl_id,String tpl_value) throws Exception {
        Result<SmsSingleSend> result = new Result<>();
        //发送短信API
        Map<String, String> param = new HashMap<>();
        param.put(YunpianClient.APIKEY, smsSettings.getApiKey());
        param.put(YunpianClient.MOBILE, phone);
        param.put(YunpianClient.TPL_ID, tpl_id);
        param.put(YunpianClient.TPL_VALUE, tpl_value);
        String strReturn = HttpClientUtil.doPost(URI_TPL_Batch_SEND_SMS, param, socketTimeout);
        //两种转化方法都可以
        //SmsSingleSend smsSingleSend = JSON.parseObject(strReturn, new TypeReference<SmsSingleSend>() {});
        SmsSingleSend smsSingleSend = JSON.parseObject(strReturn,SmsSingleSend.class);
        result.setCode(smsSingleSend.getCode());
        result.setMsg(smsSingleSend.getMsg());
        result.setDetail(smsSingleSend.getMsg());
        result.setData(smsSingleSend);
        return  result;
    }

    /**
     * 使用模板群发短信
     * @param phoneList
     * @param tpl_id
     * @param tpl_value
     * @return
     */
    public static Result<SmsBatchSend> tplBatchSend(List<String> phoneList,String tpl_id,String tpl_value){
        Result<SmsBatchSend> result = new Result<>();

        //发送短信API
        Map<String, String> param = new HashMap<>();
        param.put(YunpianClient.APIKEY, smsSettings.getApiKey());
        param.put(YunpianClient.MOBILE, StringUtils.join(phoneList,","));
        param.put(YunpianClient.TPL_ID, tpl_id);
        param.put(YunpianClient.TPL_VALUE, tpl_value);
        String strReturn = HttpClientUtil.doPost(URI_TPL_Batch_SEND_SMS, param,socketTimeout);

        //两种转化方法都可以
        //SmsSingleSend smsSingleSend = JSON.parseObject(strReturn, new TypeReference<SmsSingleSend>() {});
        SmsBatchSend smsBatchSend = JSON.parseObject(strReturn,SmsBatchSend.class);
        List<SmsSingleSend> smsSingleSendList = smsBatchSend.getData().stream().filter(smsSingleSend -> smsSingleSend.getCode() !=0).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(smsSingleSendList)){
            result.setCode(0);
            result.setMsg("发送成功!");
        }
        else{
            result.setCode(-1);
            result.setMsg("短信发送失败");
        }
        result.setData(smsBatchSend);
        return  result;
    }

}
