package com.xiaobu.auth.core.supports;

import java.io.Serializable;

/**
 * 调用Restful服务的结果封装
 * Created by qichao on 2018/2/6.
 */
public class RestfulResponse<T> implements Serializable {

    private static final long serialVersionUID = -8669623946040912284L;

    //调用Restful服务返回的状态码
    private int code;

    //调用Restful服务返回的消息摘要（如异常信息等）
    private String message;

    //调用Restful服务返回的数据
    private T data;

    public RestfulResponse() {
        super();
    }

    public RestfulResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response is{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                "}";
    }
}
