package co.insight.starter.firebase.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
@Configuration
public  class GlobalSecurityMethodFireBase extends GlobalMethodSecurityConfiguration {
}
