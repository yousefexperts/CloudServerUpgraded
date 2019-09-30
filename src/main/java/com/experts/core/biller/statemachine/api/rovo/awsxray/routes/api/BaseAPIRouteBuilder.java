package com.experts.core.biller.statemachine.api.rovo.awsxray.routes.api;

import org.apache.camel.CamelAuthorizationException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

import com.experts.core.biller.statemachine.api.rovo.awsxray.exceptions.APIException;
import com.experts.core.biller.statemachine.api.rovo.awsxray.routes.beans.PrepareErrorResponse;
import com.experts.core.biller.statemachine.api.rovo.awsxray.security.BasicAuthFailedHandler;

public abstract class BaseAPIRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(APIException.class)
                .handled(true)
                .bean(PrepareErrorResponse.class)
                .log("Handled error ${exception.message}");

        onException(CamelAuthorizationException.class, BadCredentialsException.class, AuthenticationException.class)
                .handled(true)
                .logExhausted(false)
                .bean(BasicAuthFailedHandler.class)
                .log("Handled basic auth failure");

        onException(Exception.class)
                .handled(true)
                .bean(PrepareErrorResponse.class)
                .log("Handled generic error ${exception.stacktrace}");

        restConfiguration()
                .component("jetty")
                .scheme("https")
                    .host("0.0.0.0")
                    .port("{{api.port}}")
                    .contextPath("/api")
                .endpointProperty("matchOnUriPrefix", "true")
                .endpointProperty("sendServerVersion", "false")
                .jsonDataFormat("hal+json")
                .enableCORS(true)
                .corsHeaderProperty("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .corsHeaderProperty("Access-Control-Allow-Headers",
                                    "Origin, Accept, Content-Type, Accept-Encoding, Accept-Language, "
                                    + "X-Requested-With, Transfer-Encoding, Authorization, X-APP-KEY, "
                                    + "Access-Control-Request-Method, Access-Control-Request-Headers")
                .endpointProperty("chunked", "true");

        this.defineRoute();
    }

    protected abstract void defineRoute() throws Exception;
}
