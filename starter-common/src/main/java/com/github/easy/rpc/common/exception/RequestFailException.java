package com.github.easy.rpc.common.exception;

/**
 * Created on 2018/10/16.
 * 请求失败异常
 * @author wangxiaodong
 */
public class RequestFailException extends Exception{
    public RequestFailException(String message) {
        super(message);
    }
}
