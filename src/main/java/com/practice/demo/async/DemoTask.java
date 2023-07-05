package com.practice.demo.async;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DemoTask {
    private static final Logger log = LoggerFactory.getLogger(DemoTask.class);
    @Autowired
    @Qualifier("myTaskExecutor")
    private Executor taskExecutor;


    public void demoTask(List<Integer> numList) {
        CompletableFuture<?>[] futures = numList.stream()
                .map(i -> CompletableFuture.runAsync(() -> calculateAndLog(i),taskExecutor))
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures)
                .thenRun(() -> log.info("All tasks complete"))
                .exceptionally(ex -> {
                    log.error("Some tasks failed: " + ex.getMessage());
                    return null;
                });
    }

    private void calculateAndLog(int i){
        try{
            Thread.sleep(randomSleep());
            int result = i * i;
            log.info("The square of " + i + " is " + result);
        }catch (InterruptedException e){
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    private long randomSleep(){
        long min = 800;
        long max = 2000;
        return ThreadLocalRandom.current().nextLong(min, max + 1);

    }
}
