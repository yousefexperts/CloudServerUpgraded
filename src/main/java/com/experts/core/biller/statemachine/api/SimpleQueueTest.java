package com.experts.core.biller.statemachine.api;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
public final class SimpleQueueTest {

    private static final int VALUE_SIZE = 1000;
    private static final int STATS_SECONDS = 10;

    private SimpleQueueTest() {
    }

    public static void main(String[] args) {
        int threadCount = 5;
        final HazelcastInstance hz1 = Hazelcast.newHazelcastInstance(null);
        final Stats stats = new Stats();
        ExecutorService es = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            es.submit(new Runnable() {
                public void run() {
                    Random random = new Random();
                    while (true) {
                        int ran = random.nextInt(100);
                        Queue<byte[]> queue = hz1.getQueue("default" + ran);
                        for (int j = 0; j < 1000; j++) {
                            queue.offer(new byte[VALUE_SIZE]);
                            stats.offers.incrementAndGet();
                        }
                        for (int j = 0; j < 1000; j++) {
                            queue.poll();
                            stats.polls.incrementAndGet();
                        }
                    }
                }
            });
        }

        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @SuppressWarnings("BusyWait")
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(STATS_SECONDS * 1000);
                        System.out.println("cluster size: " + hz1.getCluster().getMembers().size());
                        Stats currentStats = stats.getAndReset();
                        System.out.println(currentStats);
                        System.out.println("Operations per Second: " + currentStats.total()
                                / STATS_SECONDS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private static class Stats {

        private AtomicLong offers = new AtomicLong();
        private AtomicLong polls = new AtomicLong();

        Stats getAndReset() {
            long offersNow = offers.getAndSet(0);
            long pollsNow = polls.getAndSet(0);
            Stats newOne = new Stats();
            newOne.offers.set(offersNow);
            newOne.polls.set(pollsNow);
            return newOne;
        }

        long total() {
            return offers.get() + polls.get();
        }

        @Override
        public String toString() {
            return "total: " + total() + ", offers: " + offers.get() + ", polls: " + polls.get();
        }
    }
}
