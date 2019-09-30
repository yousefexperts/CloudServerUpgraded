package com.experts.core.biller.statemachine.api.activemq.standers.config;


import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.HttpClient;
import org.apache.velocity.app.VelocityEngine;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.parse.StaticBasicParserPool;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml.*;
import org.springframework.security.saml.context.SAMLContextProviderImpl;
import org.springframework.security.saml.key.JKSKeyManager;
import org.springframework.security.saml.key.KeyManager;
import org.springframework.security.saml.log.SAMLDefaultLogger;
import org.springframework.security.saml.metadata.*;
import org.springframework.security.saml.parser.ParserPoolHolder;
import org.springframework.security.saml.processor.*;
import org.springframework.security.saml.trust.httpclient.TLSProtocolConfigurer;
import org.springframework.security.saml.util.VelocityFactory;
import org.springframework.security.saml.websso.*;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Import({SamlUserDetailsServiceImpl.class})
@Order(1000)
public class WebSecurityConfigSaml extends WebSecurityConfigurerAdapter implements InitializingBean, DisposableBean {
 
	private Timer backgroundTaskTimer;
	private MultiThreadedHttpConnectionManager multiThreadedHttpConnectionManager;

	public void init() {
		this.backgroundTaskTimer = new Timer(true);
		this.multiThreadedHttpConnectionManager = new MultiThreadedHttpConnectionManager();
	}

	public void shutdown() {
		this.backgroundTaskTimer.purge();
		this.backgroundTaskTimer.cancel();
		this.multiThreadedHttpConnectionManager.shutdown();
	}
	
    @Autowired
    private SamlUserDetailsServiceImpl samlUserDetailsServiceImpl;

    @Bean
    public VelocityEngine velocityEngine() {
        return VelocityFactory.getEngine();
    }

    @Bean(initMethod = "initialize" , name = "parserPoolBackUp")
    public StaticBasicParserPool parserPool() {
        return new StaticBasicParserPool();
    }
 
    @Bean(name = "parserPoolHolder")
    public ParserPoolHolder parserPoolHolder() {
        return new ParserPoolHolder();
    }

    @Bean
    public HttpClient httpClient() throws IOException {
        return new HttpClient(this.multiThreadedHttpConnectionManager);
    }

    @Bean(name  = "samlAuthenticationInit")
    public SAMLAuthenticationProvider samlAuthenticationProvider() {
        SAMLAuthenticationProvider samlAuthenticationProvider = new SAMLAuthenticationProvider();
        samlAuthenticationProvider.setUserDetails(samlUserDetailsServiceImpl);
        samlAuthenticationProvider.setForcePrincipalAsString(false);
        return samlAuthenticationProvider;
    }

    @Bean(name  = "contextProviderBackUp")
    public SAMLContextProviderImpl contextProvider() {
        return new SAMLContextProviderImpl();
    }

    @Bean(name  = "sAMLBootstrapBackUp")
    public static SAMLBootstrap sAMLBootstrap() {
        return new SAMLBootstrap();
    }

    @Bean(name  = "samlLoggerBackUp")
    public SAMLDefaultLogger samlLogger() {
        return new SAMLDefaultLogger();
    }

    @Bean(name  = "webSSOprofileConsumerBackUp")
    public WebSSOProfileConsumer webSSOprofileConsumer() {
        return new WebSSOProfileConsumerImpl();
    }

    @Bean(name  = "hokWebSSOprofileConsumerBackUp")
    public WebSSOProfileConsumerHoKImpl hokWebSSOprofileConsumer() {
        return new WebSSOProfileConsumerHoKImpl();
    }

    @Bean/*(name  = "webSSOprofile")*/
    public WebSSOProfile webSSOprofile() {
        return new WebSSOProfileImpl();
    }

    @Bean(name  = "hokWebSSOProfileBackUp")
    public WebSSOProfileConsumerHoKImpl hokWebSSOProfile() {
        return new WebSSOProfileConsumerHoKImpl();
    }

    @Bean/*(name  = "ecpprofileBackUp")*/
    public WebSSOProfileECPImpl ecpprofile() {
        return new WebSSOProfileECPImpl();
    }
 
    @Bean(name  = "logoutprofileBackUp")
    public SingleLogoutProfile logoutprofile() {
        return new SingleLogoutProfileImpl();
    }
 

    @Bean(name  = "keyManagerBackup")
    public KeyManager keyManager() {
        DefaultResourceLoader loader = new DefaultResourceLoader();
        Resource storeFile = loader
                .getResource("saml/saml-keystore.jks");
        String storePass = "nalle123";
        Map<String, String> passwords = new HashMap<String, String>();
        passwords.put("apollo", "nalle123");
        String defaultKey = "apollo";
        return new JKSKeyManager(storeFile, storePass, passwords, defaultKey);
    }
 

    @Bean(name  = "tlsProtocolConfigurerBackup")
    public TLSProtocolConfigurer tlsProtocolConfigurer() {
    	return new TLSProtocolConfigurer();
    }
    
    @Bean(name  = "defaultWebSSOProfileOptionsBackUp")
    public WebSSOProfileOptions defaultWebSSOProfileOptions() {
        WebSSOProfileOptions webSSOProfileOptions = new WebSSOProfileOptions();
        webSSOProfileOptions.setIncludeScoping(false);
        return webSSOProfileOptions;
    }

    @Bean(name  = "samlEntryPointBackUp")
    public SAMLEntryPoint samlEntryPoint() {
        SAMLEntryPoint samlEntryPoint = new SAMLEntryPoint();
        samlEntryPoint.setDefaultProfileOptions(defaultWebSSOProfileOptions());
        return samlEntryPoint;
    }



    @Bean(name  = "samlIdpDiscoveryBackUp")
    public SAMLDiscovery samlIdpDiscovery() {
        SAMLDiscovery idpDiscovery = new SAMLDiscovery();
        idpDiscovery.setIdpSelectionPath("/saml/discovery");
        return idpDiscovery;
    }


    @Bean(name  = "metadataDisplayFilterBackUp")
    public MetadataDisplayFilter metadataDisplayFilter() {
        return new MetadataDisplayFilter();
    }
     

    @Bean(name  = "successRedirectHandlerBackUp")
    public SavedRequestAwareAuthenticationSuccessHandler successRedirectHandler() {
        SavedRequestAwareAuthenticationSuccessHandler successRedirectHandler =
                new SavedRequestAwareAuthenticationSuccessHandler();
        successRedirectHandler.setDefaultTargetUrl("/landing");
        return successRedirectHandler;
    }
    

    @Bean(name  = "authenticationFailureHandlerBackUp")
    public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
	    	SimpleUrlAuthenticationFailureHandler failureHandler =
	    			new SimpleUrlAuthenticationFailureHandler();
	    	failureHandler.setUseForward(true);
	    	failureHandler.setDefaultFailureUrl("/error");
	    	return failureHandler;
    }
     
    @Bean(name  = "samlWebSSOHoKProcessingFilterBackUp")
    public SAMLWebSSOHoKProcessingFilter samlWebSSOHoKProcessingFilter() throws Exception {
        SAMLWebSSOHoKProcessingFilter samlWebSSOHoKProcessingFilter = new SAMLWebSSOHoKProcessingFilter();
        samlWebSSOHoKProcessingFilter.setAuthenticationSuccessHandler(successRedirectHandler());
        samlWebSSOHoKProcessingFilter.setAuthenticationManager(authenticationManager());
        samlWebSSOHoKProcessingFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return samlWebSSOHoKProcessingFilter;
    }
    

    @Bean(name  = "samlWebSSOProcessingFilter")
    public SAMLProcessingFilter samlWebSSOProcessingFilter() throws Exception {
        SAMLProcessingFilter samlWebSSOProcessingFilter = new SAMLProcessingFilter();
        samlWebSSOProcessingFilter.setAuthenticationManager(authenticationManager());
        samlWebSSOProcessingFilter.setAuthenticationSuccessHandler(successRedirectHandler());
        samlWebSSOProcessingFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return samlWebSSOProcessingFilter;
    }


    @Bean(name  = "successLogoutHandlerBackUp")
    public SimpleUrlLogoutSuccessHandler successLogoutHandler() {
        SimpleUrlLogoutSuccessHandler successLogoutHandler = new SimpleUrlLogoutSuccessHandler();
        successLogoutHandler.setDefaultTargetUrl("/");
        return successLogoutHandler;
    }

    @Bean(name  = "logoutHandlerBackUp")
    public SecurityContextLogoutHandler logoutHandler() {
        SecurityContextLogoutHandler logoutHandler = 
        		new SecurityContextLogoutHandler();
        logoutHandler.setInvalidateHttpSession(true);
        logoutHandler.setClearAuthentication(true);
        return logoutHandler;
    }

    @Bean(name  = "samlLogoutProcessingFilter")
    public SAMLLogoutProcessingFilter samlLogoutProcessingFilter() {
        return new SAMLLogoutProcessingFilter(successLogoutHandler(),
                logoutHandler());
    }
     

    @Bean(name  = "samlLogoutFilterBackUp")
    public SAMLLogoutFilter samlLogoutFilter() {
        return new SAMLLogoutFilter(successLogoutHandler(),
                new LogoutHandler[] { logoutHandler() },
                new LogoutHandler[] { logoutHandler() });
    }
	

    private ArtifactResolutionProfile artifactResolutionProfile() throws IOException{
        final ArtifactResolutionProfileImpl artifactResolutionProfile = 
        		new ArtifactResolutionProfileImpl(httpClient());
        artifactResolutionProfile.setProcessor(new SAMLProcessorImpl(soapBinding()));
        return artifactResolutionProfile;
    }
    
    @Bean(name  = "artifactBindingBackUp")
    public HTTPArtifactBinding artifactBinding(ParserPool parserPool, VelocityEngine velocityEngine) throws IOException {
        return new HTTPArtifactBinding(parserPool, velocityEngine, artifactResolutionProfile());
    }
 
    @Bean(name  = "soapBindingBackUp")
    public HTTPSOAP11Binding soapBinding() {
        return new HTTPSOAP11Binding(parserPool());
    }
    
    @Bean(name  = "httpPostBindingBackUp")
    public HTTPPostBinding httpPostBinding() {
    		return new HTTPPostBinding(parserPool(), velocityEngine());
    }
    
    @Bean(name  = "httpRedirectDeflateBindingBackUp")
    public HTTPRedirectDeflateBinding httpRedirectDeflateBinding() {
    		return new HTTPRedirectDeflateBinding(parserPool());
    }
    
    @Bean(name  = "httpSOAP11BindingBackUp")
    public HTTPSOAP11Binding httpSOAP11Binding() {
    	return new HTTPSOAP11Binding(parserPool());
    }
    
    @Bean(name  = "httpPAOS11BindingBackUp")
    public HTTPPAOS11Binding httpPAOS11Binding() {
    		return new HTTPPAOS11Binding(parserPool());
    }
    

	@Bean(name  = "processorBackUp")
	public SAMLProcessorImpl processor() throws IOException{
		Collection<SAMLBinding> bindings = new ArrayList<SAMLBinding>();
		bindings.add(httpRedirectDeflateBinding());
		bindings.add(httpPostBinding());
		bindings.add(artifactBinding(parserPool(), velocityEngine()));
		bindings.add(httpSOAP11Binding());
		bindings.add(httpPAOS11Binding());
		return new SAMLProcessorImpl(bindings);
	}
    

    @Bean(name  = "samlFilterBackUp")
    public FilterChainProxy samlFilter() throws Exception {
            List<SecurityFilterChain> chains = new ArrayList<SecurityFilterChain>();
            chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/login/**"),
                    samlEntryPoint()));
            chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/logout/**"),
                    samlLogoutFilter()));
            chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/metadata/**"),
                    metadataDisplayFilter()));
            chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/sso/**"),
                    samlWebSSOProcessingFilter()));
            chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/SSOHoK/**"),
                    samlWebSSOHoKProcessingFilter()));
            chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/SingleLogout/**"),
                    samlLogoutProcessingFilter()));
            chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/discovery/**"),
                    samlIdpDiscovery()));
            return new FilterChainProxy(chains);
    }

    @Bean(name  = "authenticationManagerIntegration")
    @Order(4000)
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic()
                .authenticationEntryPoint(samlEntryPoint());      
        http

        		.addFilterAfter(samlFilter(), BasicAuthenticationFilter.class)
        		.addFilterBefore(samlFilter(), CsrfFilter.class);
        http        
            .authorizeRequests()
           		.antMatchers("/").permitAll()
                .antMatchers("/selectUserName").permitAll()
                .antMatchers("/signin").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/core").authenticated()
           		.antMatchers("/saml/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
           		.antMatchers("/static/**").permitAll()
           		.antMatchers("/img/**").permitAll()
           		.antMatchers("/js/**").permitAll()
           		.anyRequest().authenticated();
        http.formLogin().loginPage("/login").successForwardUrl("/index").passwordParameter("j_password").usernameParameter("j_username").failureForwardUrl("/login").and()
        		.logout()
        			.disable();	// The logout procedure is already handled by SAML filters.
    }
 

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManager) throws Exception {
        authenticationManager
            .authenticationProvider(samlAuthenticationProvider());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    @Override
    public void destroy() throws Exception {
        shutdown();
    }

}
