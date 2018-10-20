package com.github.easy.rpc.client.model;

import com.github.easy.rpc.common.model.RpcRequest;
import com.github.easy.rpc.common.model.RpcResponse;

import java.util.concurrent.CountDownLatch;

/**
 * Created on 2018/10/16.
 *
 * @author wangxiaodong
 */
public class SyncResFuture {
    /**
     * 阻塞等待获取响应
     */
    private CountDownLatch latch = new CountDownLatch(1);
    /**
     * 响应数据
     */
    private RpcResponse response;
    private String requestId;
    private Long requestTime;

    public SyncResFuture() {
    }

    public SyncResFuture(RpcRequest rpcRequest) {
        this.requestId = rpcRequest.getRequestId();
        this.requestTime = rpcRequest.getRequestTime();
    }

    /**
     * 获取响应数据，知道有数据才返回
     * @return
     * @throws InterruptedException
     */
    public RpcResponse getResponse() throws InterruptedException {
        latch.await();
        return response;
    }

    public void setResponse(RpcResponse response) {
        this.response = response;
        latch.countDown();
    }

    public Long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
