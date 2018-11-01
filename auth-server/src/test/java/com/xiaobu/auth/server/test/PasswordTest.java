package com.xiaobu.auth.server.test;

import com.xiaobu.auth.server.AuthServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author qichao
 * @create 2018-10-24
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthServerApplication.class)
public class PasswordTest {
	private static final Logger logger = LoggerFactory.getLogger(PasswordTest.class);

	@Test
	public void bcryptEncoderTest() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String raw = "devops";
		String encoded = bCryptPasswordEncoder.encode(raw);
		logger.info(encoded);
	}

}
