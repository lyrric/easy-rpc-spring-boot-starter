package com.github.easy.rpc.common.model;

import com.github.easy.rpc.common.exception.RequestFailException;

import java.io.Serializable;

/**
 * Created on 2018/10/16.
 * Rpc响应
 * @author wangxiaodong
 */
public class RpcResponse implements Serializable {
    /**
     * 请求的ID
     */
    private String requestId;
    /**
     * 是否成功
     */
    private Boolean success;
    /**
     * 出错信息
     */
    private String errMsg;
    /**
     * 返回数据
     */
    private Object data;
    /**
     * 返回时间
     */
    private Long responseTime;

    public RpcResponse() {
    }

    public RpcResponse(String requestId, Boolean success, String errMsg, Object data) {
        this.requestId = requestId;
        this.success = success;
        this.errMsg = errMsg;
        this.data = data;
        this.responseTime = System.currentTimeMillis();
    }


    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getData() throws RequestFailException {
        if(!this.success){
            throw new RequestFailException(this.errMsg);
        }
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }
}
