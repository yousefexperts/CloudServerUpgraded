package com.experts.core.biller.statemachine.api.rovo.awsxray.routes;

import static com.experts.core.biller.statemachine.api.rovo.awsxray.utils.MaskingConverter.CONFIDENTIAL;

import java.lang.invoke.MethodHandles;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.apache.camel.model.rest.RestBindingMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.experts.core.biller.statemachine.api.rovo.awsxray.exceptions.ExternalMessagingException;
import com.experts.core.biller.statemachine.api.rovo.awsxray.routes.beans.CopyBodyToHeaders;
import org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer;

public class HttpInvokerRoute extends RouteBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private int redeliveryDelay = 30000;
    public HttpInvokerRoute() {
        redeliveryDelay = 30000;
    }
    public HttpInvokerRoute(int redeliveryDelay) {
        this.redeliveryDelay = redeliveryDelay;
    }

    @Override
    public void configure() throws Exception {

        restConfiguration()
            .component("servlet")

            //Enable swagger endpoint.
            .apiContextPath("/swagger") //swagger endpoint path
            .apiContextRouteId("swagger") //id of route providing the swagger endpoint

            //Swagger properties
            .contextPath("/camel-api/api") //base.path swagger property; use the mapping path set for CamelServlet
            .apiProperty("api.title", "Swagger UI with Apache Camel-servlet")
            .apiProperty("api.version", "1.0")
            .apiProperty("api.contact.name", "Yousef.Ataya")
            .apiProperty("api.contact.email", "yousef.experts@outlook.com")
            .apiProperty("api.contact.url", "yousef.experts@outlook.com")
            .apiProperty("host", "") //by default 0.0.0.0
            .apiProperty("port", "9090")
            .apiProperty("schemes", "");

        onException(ExternalMessagingException.class)
                .handled(true)
                .log(LoggingLevel.ERROR, "error reported: ${exception.details} - can not recover from this");
        onException(UnknownHostException.class)
                .handled(true)
                .logRetryAttempted(true)
                .redeliveryDelay(redeliveryDelay)
                .maximumRedeliveries(3)
                .useExponentialBackOff()
                .backOffMultiplier(2)
                .log(LoggingLevel.ERROR, "Could not reach host ${exception.message}");
        from("direct:http-invoke")
                .routeId("HttpInvoker")
                .log(LoggingLevel.INFO, LOG, CONFIDENTIAL, "Invoking external URL: ${header[EXTERNAL_URL]}")
                .recipientList(header("EXTERNAL_URL"))
                .log(LoggingLevel.DEBUG, "HTTP response code: ${header[" + Exchange.HTTP_RESPONSE_CODE + "]}")
                .bean(CopyBodyToHeaders.class)
                .choice()
                    .when(header(Exchange.HTTP_RESPONSE_CODE).isGreaterThanOrEqualTo(300)).process((Exchange exchange) ->
                         {
                             String url = exchange.getIn().getHeader("EXTERNAL_URL").toString();
                             int statusCode = exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
                             String statusText = exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_TEXT, String.class);
                             String responseBody = exchange.getIn().getBody(String.class);
                             LOG.debug("Received invocation response code {} {} - body was {}",statusCode, statusText, responseBody);
                             Map<String, String> responseHeaders = new HashMap<>();
                             HttpOperationFailedException error;
                             try {
                                 JSONObject response = new JSONObject(responseBody);
                                 error = new HttpOperationFailedException(url, statusCode, statusText, null, responseHeaders, response.toString());
                             } catch (JSONException jsonEx) {
                                 error = new HttpOperationFailedException(url, statusCode, statusText, null, responseHeaders, responseBody);
                             }
                             throw new ExternalMessagingException(error);
                         })
                    .otherwise()
                        .log("Remote service responded with ${in.header.CamelHttpResponseCode} ${in.header.CamelHttpResponseText}")
                        .process((Exchange exchange) -> exchange.getIn().setBody(null));
    }
}
