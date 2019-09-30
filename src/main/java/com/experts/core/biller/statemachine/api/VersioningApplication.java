package com.experts.core.biller.statemachine.api;

import co.insight.starter.firebase.security.CustomPersistentRememberMeServices;
import co.insight.starter.firebase.security.UserDetailsService;
import co.insight.starter.firebase.service.MailService;
import co.insight.starter.firebase.service.UserQyef;
import co.insight.starter.firebase.web.filter.CachingHttpHeadersFilter;
import co.insight.starter.firebase.web.filter.StaticResourcesProductionFilter;
import co.insight.starter.firebase.web.rest.AccountResourceFirebase;
import co.insight.starter.firebase.web.rest.AuditResourceFirebaseEngine;
import co.insight.starter.firebase.web.rest.UserResourceFirebase;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.experts.core.biller.statemachine.api.activemq.standers.config.*;
import com.experts.core.biller.statemachine.api.annotation.StatesOnStates;
import com.experts.core.biller.statemachine.api.annotation.StatesOnTransition;
import com.experts.core.biller.statemachine.api.auth.WebSecurityConfigApp;
import com.experts.core.biller.statemachine.api.model.domain.jpa.Roles;
import com.experts.core.biller.statemachine.api.model.domain.jpa.TaskVariables;
import com.experts.core.biller.statemachine.api.model.domain.jpa.Tasks;
import com.experts.core.biller.statemachine.api.model.domain.jpa.UsersCore;
import com.experts.core.biller.statemachine.api.model.domain.jpa.hr.Person;
import com.experts.core.biller.statemachine.api.security.encrypt.*;
import com.experts.core.biller.statemachine.api.service.impl.HeaderModifierAdvice;
import com.experts.core.biller.statemachine.api.service.impl.IInquiryServiceController;
import com.experts.core.biller.statemachine.api.service.impl.PaymentNotificationController;
import com.experts.core.biller.statemachine.api.service.impl.PaymentServiceController;
import com.experts.core.biller.statemachine.api.transitions.*;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapAttributeConfig;
import com.hazelcast.config.MapIndexConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
/*import io.micrometer.spring.autoconfigure.web.client.RestTemplateMetricsAutoConfiguration;
import io.micrometer.spring.web.client.DefaultRestTemplateExchangeTagsProvider;
import io.micrometer.spring.web.client.MetricsRestTemplateCustomizer;
import io.micrometer.spring.web.client.RestTemplateExchangeTagsProvider;*/
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.camel.spring.javaconfig.Main;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
/*import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.ConnectorConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.ignite.spi.failover.always.AlwaysFailoverSpi;*/
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.ConnectorConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.ignite.spi.failover.always.AlwaysFailoverSpi;
import org.apereo.cas.CasEmbeddedContainerUtils;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.CasWebApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.zookeeper.ZookeeperAutoConfiguration;
import org.springframework.cloud.zookeeper.discovery.dependency.DependencyRestTemplateAutoConfiguration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperAutoServiceRegistrationAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.core.annotation.Order;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.config.EnableIntegrationManagement;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.hazelcast.HazelcastSessionRepository;
import org.springframework.session.hazelcast.PrincipalNameExtractor;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;
import org.springframework.statemachine.config.common.annotation.EnableAnnotationConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import javax.annotation.PostConstruct;
import javax.naming.NamingException;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, WebFluxAutoConfiguration.class, LiquibaseAutoConfiguration.class, EurekaClientAutoConfiguration.class, DependencyRestTemplateAutoConfiguration.class, ZookeeperAutoConfiguration.class, ZookeeperAutoServiceRegistrationAutoConfiguration.class})
@Configuration
@ComponentScan(basePackages = {"com.gismat.test.*", "co.qyef.starter.firebase.*"})
@ComponentScan(basePackages = {"com.experts.core.biller.statemachine.api",
    "com.experts.core.biller.statemachine.api.*",
    "com.experts.core.biller.statemachine.api.interceptor",
    "com.experts.core.biller.statemachine.api.persist",
    "com.experts.core.biller.statemachine.api.transitions",
    "com.experts.core.biller.statemachine.api.activemq.standers.config", "com.gismat.test.*", "co.qyef.starter.firebase.*"
}, basePackageClasses = {StatesOnStates.class, StatesOnTransition.class, GenerateAndVerifySignature.class,
    AtomikFactoryBean.class, AtomikFactoryBean.class, CustomerConfig.class,
    CompletedTransitionBean.class,
    FSMStartingBean.class,
    WebSecurityConfigApp.class,
    SamlConfigDefaults.class,
    InitialTransitionBean.class,
    TransitionProcessBean.class,
    TransitionCancelBean.class,
    AccountResourceFirebase.class,
    AuditResourceFirebaseEngine.class,
    UserResourceFirebase.class,
    CachingHttpHeadersFilter.class,
    CustomPersistentRememberMeServices.class,
    UserDetailsService.class,
    MailService.class,
    UserQyef.class,
    StaticResourcesProductionFilter.class,
    SpringConfigMvc.class,
    TransitionExecutionBean.class, WebSecurityConfigSaml.class, CustomerConfig.class, XAPublisherTemplate.class, JdbcInMemory.class, GenerateKeysService.class, AsymmetricCryptoService.class, EncryptByPkService.class, WebApplicationInitializer.class, SOAPSecurityHandler.class, SpringConfigMvc.class, HeaderModifierAdvice.class, WebSecurityConfigApp.class, IInquiryServiceController.class, PaymentNotificationController.class, PaymentServiceController.class,
    TransitionPayBean.class})
@EnableTransactionManagement
@EntityScan(basePackageClasses = {Roles.class, Tasks.class, TaskVariables.class, UsersCore.class, IInquiryServiceController.class})
@EnableCaching
@EnableWs
@EnableScheduling
@AutoConfigureAfter(HazelcastAutoConfiguration.class)
@Slf4j
@ImportResource(locations = {"classpath*:/applicationContext.xml", "classpath*:/WEB-INF/spring-configuration/*.xml", "classpath*:/WEB-INF/deployerConfigContext.xml"})
@EnableHazelcastHttpSession
@EnableBatchProcessing
@EnableSpringDataWebSupport
@EnableHystrix
@EnableJms
@EnableKafka
@EnableIntegration
@EnableSpringHttpSession
@EnableIntegrationManagement
@EnableAnnotationConfiguration
@EnableSpringConfigured
@EnableMetrics
@EnableConfigurationProperties(CasConfigurationProperties.class)
@EnableAsync
@NoArgsConstructor
@EnableCircuitBreaker
public class VersioningApplication {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private Main app;

    public static void main(String[] args) throws Exception {

        val properties = CasEmbeddedContainerUtils.getRuntimeProperties(Boolean.TRUE);
        val banner = CasEmbeddedContainerUtils.getCasBannerInstance();
        SpringApplicationBuilder builder = new SpringApplicationBuilder(VersioningApplication.class)
            .banner(banner)
            .web(WebApplicationType.SERVLET)
            .properties(properties)
            .logStartupInfo(true)
            .contextClass(CasWebApplicationContext.class);
        builder.application().setAllowBeanDefinitionOverriding(true);
        builder.run(args);

        new SpringApplicationBuilder(VersioningApplication.class).run(args);
        try {
            java.security.Security.setProperty("networkaddress.cache.ttl", "180");
            LOG.info("Set Networkaddress cache of the Java VM to a TTL of 3m");

        } catch (Exception ex) {
            LOG.warn(
                "Could not set the TTL of the Java VM Networkaddress cache. Will stick to the default value");

        }

    }

    @Bean(name = "manager")
    @Primary
    public CacheManager manager() {
        return new ConcurrentMapCacheManager("entities");
    }

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TaskScheduler taskExecutor() {
        return new ConcurrentTaskScheduler(
            Executors.newScheduledThreadPool(3));
    }

    @Value("#{'${service.endpoint}'}")
    private String serviceEndpoint;

    @Value("#{'${marshaller.packages.to.scan}'}")
    private String marshallerPackagesToScan;

    @Value("#{'${unmarshaller.packages.to.scan}'}")
    private String unmarshallerPackagesToScan;


    @Bean
    public SaajSoapMessageFactory messageFactory() {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
        messageFactory.afterPropertiesSet();
        return messageFactory;
    }


    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.experts.core.biller.statemachine.api.soapService");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate createWebServiceTemplate() throws NamingException {

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setDefaultUri("https://localhost:3070/v1/soap?wsdl");
        webServiceTemplate.setMarshaller(marshaller());
        webServiceTemplate.setUnmarshaller(marshaller());
        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
        webServiceTemplate.setMessageSender(messageSender);
        messageSender.setHttpClient(getHttpClient());
        return webServiceTemplate;
    }


    public HttpClient getHttpClient() {
        return HttpClientBuilder.create().build();
    }

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean
    public IgniteConfiguration igniteConfiguration() {
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setClientMode(false);
        ConnectorConfiguration connectorConfiguration = new ConnectorConfiguration();
        connectorConfiguration.setPort(11211);
        connectorConfiguration.setHost("localhost");
        igniteConfiguration.setPeerClassLoadingEnabled(true);
        igniteConfiguration.setIgniteInstanceName("biller");
        igniteConfiguration.setConnectorConfiguration(connectorConfiguration);
        TcpDiscoverySpi tcpDiscoverySpi = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder tcpDiscoveryVmIpFinder = new TcpDiscoveryVmIpFinder();
        tcpDiscoveryVmIpFinder.setAddresses(Arrays.asList("127.0.0.1:47500..47509"));
        tcpDiscoverySpi.setIpFinder(tcpDiscoveryVmIpFinder);
        AlwaysFailoverSpi spi = new AlwaysFailoverSpi();
        spi.setMaximumFailoverAttempts(5);
        igniteConfiguration.setFailoverSpi(spi);
        igniteConfiguration.setDiscoverySpi(new TcpDiscoverySpi());
        CacheConfiguration alerts = new CacheConfiguration();
        alerts.setCacheMode(CacheMode.REPLICATED);
        alerts.setBackups(1);
        alerts.setName("biller");
        alerts.setIndexedTypes(String.class, Person.class);
        igniteConfiguration.setCacheConfiguration(alerts);
        return igniteConfiguration;
    }

    @Bean
    public Ignite ignite(IgniteConfiguration igniteConfiguration) throws IgniteException {
        final Ignite start = Ignition.start(igniteConfiguration);
        start.active(true);
        return start;
    }

    @Bean
    public IDummyBean dummyBean() {
        return new DummyBean();
    }

    @Bean
    CacheManager cacheManager() {
        return new HazelcastCacheManager(hazelcastInstance());
    }

    @Bean
    KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    @Bean
    public HazelcastInstance hazelcastInstance() {
        MapAttributeConfig attributeConfig = new MapAttributeConfig()
            .setName(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
            .setExtractor(PrincipalNameExtractor.class.getName());

        Config config = new Config();

        config.getMapConfig(HazelcastSessionRepository.PRINCIPAL_NAME_INDEX_NAME)
            .addMapAttributeConfig(attributeConfig)
            .addMapIndexConfig(new MapIndexConfig(
                HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE, false));

        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    @Primary
    @Order(3000)
    public MetricRegistry getMetricsRegistry() {
        return new MetricRegistry();
    }

    @PostConstruct
    public void initMetrics() {
        JmxReporter reporter = JmxReporter.forRegistry(getMetricsRegistry())
            .convertRatesTo(TimeUnit.MILLISECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();
        reporter.start();
    }


}
