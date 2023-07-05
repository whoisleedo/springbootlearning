package com.practice.demo.schedule;


import com.practice.demo.async.DemoTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Component
public class DemoScheduler {
    private static final Logger log = LoggerFactory.getLogger(DemoScheduler.class);

    private AtomicBoolean methodCheck;
    @Autowired
    private DemoTask demoTask;

    @PostConstruct
    public void initScheduler() {
        methodCheck = new AtomicBoolean();
    }

    @Scheduled(cron = "${demo.scheduler.cron}")
    public void demoSchedule() {
        if (methodCheck.compareAndSet(false, true)) {
            try {
                List<Integer> numList = getNumList();
                demoTask.demoTask(numList);
            } catch (Exception e) {
                log.error("task error" ,e);

            } finally {
                methodCheck.set(false);
            }

        }
    }

    private List<Integer> getNumList() {
        Random random = new Random();
        return IntStream.range(0, 50)
                .map(i -> 1 + random.nextInt(20))
                .boxed()
                .collect(Collectors.toList());

    }


}
