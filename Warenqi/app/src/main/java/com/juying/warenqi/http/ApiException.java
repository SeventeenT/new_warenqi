package com.digg.warenqiseller.http;

/**
 * <pre>
 * Author: @Administrator
 * Description:
 * Date: 2017/6/7 10:04
 * </pre>
 */
public class ApiException extends Exception {
    private int code;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
