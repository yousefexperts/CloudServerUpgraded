package com.experts.core.biller.statemachine.api.rovo.awsxray.config.settings;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@ConfigurationProperties(prefix = "http")
public class HttpClientSettings {

    /**
     * The time span the application will wait for a connection to get established. If the connection
     * is not established within the given amount of time a ConnectionTimeoutException will be raised.
     * <p>
     * The value has to be applied in seconds!
     **/
    @NotNull
    @Value("10000")
    private int conTimeout;

    /**
     * Monitors the time passed between two consecutive incoming messages over the connection and
     * raises a SocketTimeoutException if no message was received within the given timeout interval.
     * <p>
     * The value has to be applied in seconds!
     **/
    @NotNull
    @Value("10000")
    private int soTimeout;
}
