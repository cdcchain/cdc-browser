package com.browser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/** 异步线程池
 * Created by mayakui on 2017/4/1.
 */
@Configuration
@EnableAsync
public class ExecutorConfig {
    /** 核心线程数大小 */
    private static int corePoolSize =50;
    /** 最大线程数 */
    private static int maxPoolSize = 500;
    /** 缓冲队列 */
    private static int queueCapacity = 200;

    @Bean
    public Executor mySimpleAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("MySimpleExecutor-");
        executor.initialize();
        return executor;
    }

    @Bean
    public Executor myAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("MyExecutor-");

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
