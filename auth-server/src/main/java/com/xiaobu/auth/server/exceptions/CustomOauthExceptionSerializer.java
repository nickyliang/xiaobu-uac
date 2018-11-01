package com.xiaobu.auth.server.exceptions;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 自定义OAuth异常的序列化
 *
 * @author qichao
 * @create 2018-11-01
 **/
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOAuthException> {


	protected CustomOauthExceptionSerializer() {
		super(CustomOAuthException.class);
	}

	@Override
	public void serialize(CustomOAuthException e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("code", String.valueOf(e.getHttpErrorCode()));
		jsonGenerator.writeStringField("message", e.getMessage());
		jsonGenerator.writeStringField("path", request.getServletPath());
		jsonGenerator.writeStringField("timestamp", String.valueOf(new Date().getTime()));
		if (e.getAdditionalInformation()!=null) {
			for (Map.Entry<String, String> entry : e.getAdditionalInformation().entrySet()) {
				String key = entry.getKey();
				String add = entry.getValue();
				jsonGenerator.writeStringField(key, add);
			}
		}
		jsonGenerator.writeEndObject();
	}
}
