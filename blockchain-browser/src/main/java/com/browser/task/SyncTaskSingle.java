package com.browser.task;

import com.browser.service.BlockService;
import com.browser.service.SocketService;
import com.browser.service.impl.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by mayakui on 2018/1/21 0021.
 * 每次同步一个块的定时任务
 */
@Component
public class SyncTaskSingle {

    private static Logger logger = LoggerFactory.getLogger(SyncTaskSingle.class);

    @Autowired
    private BlockService blockService;

    @Autowired
    private SocketService socketService;

    @Autowired
    private SyncService syncService;

    @Scheduled(cron="0/10 * * * * ? ")
    public void syncData(){
        logger.info("同步数据开始");
        //查询本地数据库最大块号
        Long blockNum = blockService.queryBlockNum();
        if(null == blockNum){
            blockNum = 0L;
        }
        Long total = socketService.getBlockCount();

        if(total>blockNum){
            for(Long i=blockNum; i<total; i++){
                logger.info("同步"+(i+1)+"块");
                syncService.blockSync(i+1);
            }
        }

       logger.info("同步数据结束");
    }
}
