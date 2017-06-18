package com.digg.warenqiseller.mvp.model.entity;

import com.digg.warenqiseller.mvp.model.api.Api;

import java.io.Serializable;

/**
 * 如果你服务器返回的数据固定为这种方式(字段名可根据服务器更改)
 * 替换范型即可重用
 */

public class BaseBean<T> implements Serializable{
    private T results;
    private int code;
    private String msg;

    public T getResults() {
        return results;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 请求是否成功
     * @return
     */
    public boolean isSuccess() {
        return code == Api.SUCCESS_CODE;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "results=" + results +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
