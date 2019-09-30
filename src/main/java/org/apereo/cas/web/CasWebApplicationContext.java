package org.apereo.cas.web;

import org.apereo.cas.CasEmbeddedValueResolver;

import lombok.ToString;
import lombok.val;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.TaskManagementConfigUtils;


@ToString
public class CasWebApplicationContext extends AnnotationConfigServletWebServerApplicationContext {


    @Override
    protected void onRefresh() {
        val sch =
                (ScheduledAnnotationBeanPostProcessor) getBeanFactory()
                        .getBean(TaskManagementConfigUtils.SCHEDULED_ANNOTATION_PROCESSOR_BEAN_NAME, BeanPostProcessor.class);
        sch.setEmbeddedValueResolver(new CasEmbeddedValueResolver(this));
        super.onRefresh();
    }
}
