package com.browser.task;

import com.browser.service.StatisService;
import com.browser.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class InitBlockData implements InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(InitBlockData.class);
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private StatisService statisService;

	@Override
	public void afterPropertiesSet(){
		logger.info("开始初始化");
		try {
			//更新最新统计数据
			transactionService.updateSelect();

			statisService.newBlockStatic();

			statisService.newTransactionStatic();
		} catch (Exception e) {
			logger.error("初始化异常",e);
		}
	}

}
