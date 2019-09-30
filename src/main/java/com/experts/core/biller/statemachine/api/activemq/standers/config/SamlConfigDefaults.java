package com.experts.core.biller.statemachine.api.activemq.standers.config;

import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.saml.SAMLBootstrap;
import org.springframework.security.saml.context.SAMLContextProviderImpl;
import org.springframework.security.saml.log.SAMLDefaultLogger;
import org.springframework.security.saml.metadata.CachingMetadataManager;
import org.springframework.security.saml.parser.ParserPoolHolder;
import org.springframework.security.saml.websso.*;

import java.util.List;

@Configuration
public class SamlConfigDefaults {
    @Bean(name  = "sAMLBootstrapKey")
    @Primary
    @Order(-2)
    public static SAMLBootstrap sAMLBootstrap() {
        return new SAMLBootstrap();
    }

    @Bean(name  = "parserPoolHolderKey")
    @Primary
    @Order(-2)
    public ParserPoolHolder parserPoolHolder() {
        return new ParserPoolHolder();
    }

    @Bean(name  = "contextProviderKey")
    @Primary
    @Order(-2)
    public SAMLContextProviderImpl contextProvider() {
        return new SAMLContextProviderImpl();
    }

    @Bean(name  = "samlLogger")
    @Primary
    @Order(-2)
    public SAMLDefaultLogger samlLogger() {
        return new SAMLDefaultLogger();
    }

    @Bean(name  = "webSSOprofileConsumer")
    @Primary
    @Order(-2)
    public WebSSOProfileConsumer webSSOprofileConsumer() {
        return new WebSSOProfileConsumerImpl();
    }

    @Bean(name  = "hokWebSSOprofileConsumer")
    @Primary
    @Order(-2)
    public WebSSOProfileConsumerHoKImpl hokWebSSOprofileConsumer() {
        return new WebSSOProfileConsumerHoKImpl();
    }

    @Bean(name  = "webSSOprofileKey")
    @Primary
    @Order(-2)
    public WebSSOProfile webSSOprofile() {
        return new WebSSOProfileImpl();
    }

    @Bean(name  = "ecpProfileKey")
    @Primary
    @Order(-2)
    public WebSSOProfileECPImpl ecpProfile() {
        return new WebSSOProfileECPImpl();
    }

    @Bean(name  = "hokWebSSOProfileKey")
    @Primary
    @Order(-2)
    public WebSSOProfileHoKImpl hokWebSSOProfile() {
        return new WebSSOProfileHoKImpl();
    }

    @Bean(name  = "logoutProfileKey")
    @Primary
    @Order(-2)
    public SingleLogoutProfile logoutProfile() {
        return new SingleLogoutProfileImpl();
    }

    @Bean(name  = "metadataManager")
    @Primary
    @Order(-2)
    public CachingMetadataManager metadataManager(List<MetadataProvider> metadataProviders) throws MetadataProviderException {
        return new CachingMetadataManager(metadataProviders);
    }
}
