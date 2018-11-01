package com.xiaobu.auth.core.supports;

/**
 * Created by qichao on 2018/2/13.
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 2332608236621015980L;

    private int code;
    private String message;

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    public BaseException(int code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code  = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
