package org.apereo.cas.web;

import com.experts.core.biller.statemachine.api.VersioningApplication;
import org.apereo.cas.CasEmbeddedContainerUtils;

import lombok.val;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


public class CasWebApplicationServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
        val properties = CasEmbeddedContainerUtils.getRuntimeProperties(Boolean.FALSE);
        builder.application().setAllowBeanDefinitionOverriding(true);
        return builder
                .sources(VersioningApplication.class)
                .properties(properties)
                .banner(CasEmbeddedContainerUtils.getCasBannerInstance());
    }
}
