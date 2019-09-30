package co.insight.starter.firebase.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableJpaRepositories("co.insight.starter.firebase.repository")
@EnableTransactionManagement
@EnableJpaAuditing
public class DatabaseConfigurationStarter {
    private final Logger log = LoggerFactory.getLogger(DatabaseConfigurationStarter.class);

    @Autowired
    private Environment env;

    @Bean(name = {"org.springframework.boot.autoconfigure.AutoConfigurationUtils.basePackages"})
    public List<String> getBasePackages() {
        List<String> basePackages = new ArrayList<>();
        basePackages.add("co.qyef.starter.firebase.*");
        basePackages.add("com.gismat.test.*");
        return basePackages;
    }

    @Bean
    public DataSource dataSource() {
        log.debug("Configuring Datasource");

        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(null);
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/liquidbase_qyef?serverTimezone=UTC");
        config.setUsername("root");
        config.setPassword("opc@2018");

        return new HikariDataSource(config);
    }


    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        log.debug("Configuring Liquibase");
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog("classpath:config/liquibase/master.xml");
        liquibase.setContexts("development, production");
        return liquibase;
    }
}

