package com.experts.core.biller.statemachine.api.rovo.awsxray.config.settings;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "pdpserver")
public class MySQLSettings {
    @Value("root")
    private String user;

    @Value("opc@2018")
    private String password;

    @NotNull
    @Value("pdpserver")
    private String database;

    @NotNull
    @Value("localhost")
    private String host;

    @NotNull
    @Value("3306")
    private Integer port;
}
