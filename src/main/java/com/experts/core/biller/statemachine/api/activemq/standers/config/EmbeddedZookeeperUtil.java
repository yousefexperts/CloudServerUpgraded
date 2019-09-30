package com.experts.core.biller.statemachine.api.activemq.standers.config;

import org.springframework.xd.dirt.zookeeper.EmbeddedZooKeeper;

public class EmbeddedZookeeperUtil {

    private static final EmbeddedZooKeeper embeddedZooKeeper = new EmbeddedZooKeeper(2181);
    public static void start(){
        embeddedZooKeeper.start();
    }
    public static void stop(){
        embeddedZooKeeper.stop();
    }

}
