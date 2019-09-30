package co.insight.starter.firebase.config.metrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import co.insight.starter.firebase.config.DatabaseConfigurationStarter;

import javax.sql.DataSource;

@Configuration
@Import({ DatabaseConfigurationStarter.class} )
public class JHipsterHealthIndicatorConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public HealthIndicator dbHealthIndicator() {
        return new DatabaseHealthIndicator(dataSource);
    }

}
