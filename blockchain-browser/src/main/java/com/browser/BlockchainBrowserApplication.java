package com.browser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@MapperScan("com.browser.dao.mapper")
public class BlockchainBrowserApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockchainBrowserApplication.class, args);
	}
}
