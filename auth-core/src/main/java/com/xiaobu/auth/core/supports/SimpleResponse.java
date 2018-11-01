package com.xiaobu.auth.core.supports;

/**
 * 简单response模型，仅包含数据（或消息摘要）
 * 用以包装简单response类型
 * @author qichao
 * @create 2018-02-07
 **/
public class SimpleResponse {

    public SimpleResponse(Object content){
        this.content = content;
    }

    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
