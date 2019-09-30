package com.experts.core.biller.statemachine.api.rovo.awsxray.settings;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@ConfigurationProperties(prefix = "httpClient")
public class HttpClientSettings {


    @NotNull
    private int conTimeout;

    @NotNull
    private int soTimeout;
}
