package com.experts.core.biller.statemachine.api;

import java.util.concurrent.TimeUnit;

public class DummyBean implements IDummyBean {

    @Override
    public String getCity() {
        System.out.println("DummyBean.getCity() called!");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Ankara";
    }

    @Override
    public String setCity(String city) {
        return city;
    }
}
