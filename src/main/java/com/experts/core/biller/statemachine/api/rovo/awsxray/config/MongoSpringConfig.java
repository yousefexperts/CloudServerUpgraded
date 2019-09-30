package com.experts.core.biller.statemachine.api.rovo.awsxray.config;

import com.experts.core.biller.statemachine.api.rovo.awsxray.config.settings.MongoSettings;
import com.experts.core.biller.statemachine.api.rovo.awsxray.domain.CompanyService;
import com.experts.core.biller.statemachine.api.rovo.awsxray.domain.FileService;
import com.experts.core.biller.statemachine.api.rovo.awsxray.domain.UserServiceQyef;
import com.experts.core.biller.statemachine.api.rovo.awsxray.utils.BigDecimalConverter;
import com.experts.core.biller.statemachine.api.rovo.awsxray.utils.MongoBuilder;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.ReadPreference;
import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.UnknownHostException;

@Configuration
@EnableConfigurationProperties(MongoSettings.class)
public class MongoSpringConfig {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static final String BCRYPT_ENCODER = "bcrypt_encoder";

    @Resource
    private MongoSettings settings;

    @Bean(name = BCRYPT_ENCODER)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(destroyMethod = "close")
    public MongoClient mongo() throws MongoException, IOException {
        return new MongoBuilder(settings)
                .withSocketTimeout(60000)
                .withConnectTimeout(15000)
                .withMaxConnectionIdleTime(600000)
                .withReadPreference(ReadPreference.primaryPreferred())
                .build();
    }

    @Bean
    public Morphia morphia() {
        Morphia morphia = new Morphia().mapPackage("com.experts.core.biller.statemachine.api.rovo.awsxray.domain.entities.mongo");

        morphia.getMapper().getConverters().addConverter(BigDecimalConverter.class);

        return morphia;
    }

    @Bean
    public Datastore datastore() throws MongoException, UnknownHostException {
        MongoClient mongo = null;
        try {
            mongo = mongo();

        } catch (IOException e) {
            LOG.error("Could not create the local database (embedded MongoDB)", e);
        }

        if (mongo == null) {
            throw new IllegalStateException("Unable to create the MongoClient");
        }

        Datastore datastore = morphia().createDatastore(mongo, settings.getDatabase());

        // Ensure that indexes and caps are applied, but only if it's not then read-only user
        if (StringUtils.isEmpty(settings.getUser()) || !settings.getUser().endsWith("-read")) {
            datastore.ensureIndexes();
            datastore.ensureCaps();
        }

        return datastore;
    }

    @Bean
    public UserServiceQyef userService() {
        return new UserServiceQyef();
    }

    @Bean
    public CompanyService companyService() {
        return new CompanyService();
    }

    @Bean
    public FileService fileService() {
        return new FileService();
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException{
        return new SimpleMongoDbFactory(new MongoClient("localhost", 27017), "admin");
    }

    @Bean
    public MongoOperations mongoOperations() throws UnknownHostException{
        return new MongoTemplate(mongoDbFactory());
    }
}
