package com.experts.core.biller.statemachine.api;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

public interface IDummyBean {

    @Cacheable("city")
    String getCity();

    @CachePut(value = "city", key = "#city + 1")
    String setCity(String city);
}
