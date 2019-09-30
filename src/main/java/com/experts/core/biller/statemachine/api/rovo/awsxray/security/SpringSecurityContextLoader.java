package com.experts.core.biller.statemachine.api.rovo.awsxray.security;

import javax.annotation.Resource;

import com.experts.core.biller.statemachine.api.activemq.standers.config.WebSecurityConfigLdap;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.apache.camel.component.aws.xray.XRayTrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;


@Component
@XRayTrace
@Configuration
@Import({WebSecurityConfigLdap.class , UserKeyAuthenticationProvider.class})
public class SpringSecurityContextLoader extends AbstractSpringSecurityContextLoader {

  @Autowired
  private AuthenticationProvider authProvider;

  @Handler
  public void process(@Header("Authorization") String authHeader, Exchange exchange) throws Exception {
    super.handleRequest(authHeader, exchange, authProvider);
  }

  @Override
  protected UsernamePasswordAuthenticationToken handleAuthentication(String[] usernameAndPassword,
                                                                     Exchange exchange,
                                                                     AuthenticationProvider authProvider) {
    UsernamePasswordAuthenticationToken authToken =
        super.handleAuthentication(usernameAndPassword, exchange, authProvider);
    exchange.getIn().setHeader("userId", authToken.getPrincipal());
    return authToken;
  }
}
