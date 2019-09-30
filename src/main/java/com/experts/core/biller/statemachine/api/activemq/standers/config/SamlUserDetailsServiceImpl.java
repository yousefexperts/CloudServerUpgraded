package com.experts.core.biller.statemachine.api.activemq.standers.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SamlUserDetailsServiceImpl implements SAMLUserDetailsService {

  public Object loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {
    log.info("Login received for user {}", credential.getNameID().getValue());
    return new SamlUserDetails(credential);
  }
}
