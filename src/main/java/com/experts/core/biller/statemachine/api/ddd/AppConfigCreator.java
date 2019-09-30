package com.experts.core.biller.statemachine.api.ddd;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AppConfigCreator {

    @Bean
    public ThreadPoolTaskExecutor appExecutor() {
        ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
        //TODO put configs on the properties
        //TODO define an equation for pool size
        t.setCorePoolSize(350);
        t.setMaxPoolSize(350);
        t.setQueueCapacity(1000);
        t.setAllowCoreThreadTimeOut(true);
        t.setKeepAliveSeconds(120);
        t.initialize();
        return t;
    }
}
