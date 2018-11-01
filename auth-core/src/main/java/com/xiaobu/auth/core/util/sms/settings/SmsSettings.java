package com.xiaobu.auth.core.util.sms.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by qichao on 2018/06/09.
 */
@Configuration
@ConfigurationProperties(prefix = "sms")
public class SmsSettings {

    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
