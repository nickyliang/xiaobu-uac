package com.xiaobu.auth.server.test;

import com.xiaobu.auth.server.AuthServerApplication;
import com.xiaobu.auth.server.authenticate.sms.SmsCodeProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author qichao
 * @create 2018-11-01
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthServerApplication.class)
public class SmsTest {
	private static final Logger logger = LoggerFactory.getLogger(SmsTest.class);

	@Autowired
	SmsCodeProcessor smsCodeProcessor;

	@Test
	public void smsCodeTest() {
		try {
			smsCodeProcessor.createSendAndSave("18612563578",6);
		} catch (Exception ex) {
			logger.info(ex.getMessage());
		}
	}
}
