package com.experts.core.biller.statemachine.api;

import org.apache.commons.io.IOUtils;
/*import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;*/
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@ComponentScan
@Configuration
public class ServiceConfiguration {

    public static final String CRM_NAME = "crm";

    public static final File CRM_STORAGE_DIRECTORY = new File(
            System.getProperty("user.home"), CRM_NAME);

    public static final File CRM_STORAGE_UPLOADS_DIRECTORY = new File(CRM_STORAGE_DIRECTORY, "uploads");

    public static final File CRM_STORAGE_PROFILES_DIRECTORY = new File(CRM_STORAGE_DIRECTORY, "profiles");

    @PostConstruct
    protected void setupStorage() throws Throwable {
        File[] files = {CRM_STORAGE_DIRECTORY, CRM_STORAGE_UPLOADS_DIRECTORY, CRM_STORAGE_PROFILES_DIRECTORY};
        for (File f : files) {
            if (!f.exists() && !f.mkdirs()) {
                String msg = String.format("you must create the profile photos directory ('%s') " +
                        "and make it accessible to this process. Unable to do so from this process.", f.getAbsolutePath());
                throw new RuntimeException(msg);
            }
        }
    }

}
