package com.github.easy.rpc.client.task;

import com.github.easy.rpc.client.SyncFutureMgr;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created on 2018/10/20.
 * 定时任务
 * @author wangxiaodong
 */
@Component
public class ScheduledTask {

    @Resource
    private SyncFutureMgr syncFutureMgr;
    /**
     * 清除超时数据
     */
    @Scheduled(cron = "10/30 * * * * ? ")
    public void clearTimeoutData(){
        syncFutureMgr.scanData();
    }
}
