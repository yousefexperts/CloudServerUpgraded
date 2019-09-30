package com.experts.core.biller.statemachine.api.rovo.awsxray.config.settings;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@ConfigurationProperties(prefix = "trust")
public class TrustStoreSettings {

    @Value("classpath*:/keystore-biller.jks")
    private String resource;

    @Value("opc@2018")
    private String password;
}
