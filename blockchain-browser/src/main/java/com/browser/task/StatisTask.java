package com.browser.task;

import com.browser.service.StatisService;
import com.browser.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * Created by mayakui on 2017/9/6 0006.
 */
@Component
public class StatisTask {

    private static Logger logger = LoggerFactory.getLogger(StatisTask.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private StatisService statisService;

    @Scheduled(cron="0/30 * * * * ? ")
    public void hourStatic(){
        try {
            transactionService.updateSelect();
        }catch(Exception e){
            logger.error("统计任务出错",e);
        }

    }

    @Scheduled(cron="0/10 * * * * ? ")
    public void newBlockStatic(){
        try {
            statisService.newBlockStatic();
        }catch(Exception e){
            logger.error("统计任务出错",e);
        }
    }

    @Scheduled(cron="0/10 * * * * ? ")
    public void newTransactionStatic(){
        try {
            statisService.newTransactionStatic();
        }catch(Exception e){
            logger.error("统计任务出错",e);
        }
    }


}
