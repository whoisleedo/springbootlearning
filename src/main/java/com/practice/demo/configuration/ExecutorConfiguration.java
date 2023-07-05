package com.practice.demo.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ExecutorConfiguration {

    @Value("${demo.thread_pool.core_pool_size:2}")
    private int corePoolSize;
    @Value("${demo.thread_pool.max_pool_size:8}")
    private int maxPoolSize;
    @Value("${demo.thread_pool.queue_capacity:10}")
    private int queueCapacity;
    @Value("${demo.thread_pool.keep_alive_seconds:60}")
    private int keepAliveSeconds;
    @Value("${demo.thread_pool.thread_name_prefix:taskExecutor-}")
    private String threadNamePrefix;

    @Bean(name = "myTaskExecutor")
    public Executor myTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }


}
