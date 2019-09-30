package com.experts.core.biller.statemachine.api.activemq.standers.config;


import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.xd.dirt.zookeeper.EmbeddedZooKeeper;
import org.springframework.xd.dirt.zookeeper.ZooKeeperConnection;

import javax.annotation.PreDestroy;
import java.io.IOException;

@Configuration
public class ZookeeperApp {


    private static final String LOCALHOST = "localhost:";


    @PreDestroy
    public void preDestroy() {
        EmbeddedZookeeperUtil.stop();
    }

    @Bean
    public ZooKeeperConnection zooKeeperConnection() {
        return new ZooKeeperConnection(LOCALHOST + embeddedZooKeeper().getClientPort());
    }

    @Bean
    public EmbeddedZooKeeper embeddedZooKeeper() {
        return new EmbeddedZooKeeper();
    }

    @Bean(name  = "customRestTemplateCustomizer")
    public CustomRestTemplateCustomizer customRestTemplateCustomizer() {
        return new CustomRestTemplateCustomizer();
    }

    @Bean
    @DependsOn(value = {"customRestTemplateCustomizer"})
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return  restTemplateBuilder.build();
    }

    @NoArgsConstructor
    private static class CustomRestTemplateCustomizer implements RestTemplateCustomizer {
        @Override
        public void customize(RestTemplate restTemplate) {
            restTemplate.getInterceptors().add(new CustomClientHttpRequestInterceptor());
        }
    }


    public static class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

        private static final Logger LOGGER = LoggerFactory.getLogger(CustomClientHttpRequestInterceptor.class);

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

            logRequestDetails(request);

            return execution.execute(request, body);
        }

        private void logRequestDetails(HttpRequest request) {
            LOGGER.info("Request Headers: {}", request.getHeaders());
            LOGGER.info("Request Method: {}", request.getMethod());
            LOGGER.info("Request URI: {}", request.getURI());
        }
    }
}
