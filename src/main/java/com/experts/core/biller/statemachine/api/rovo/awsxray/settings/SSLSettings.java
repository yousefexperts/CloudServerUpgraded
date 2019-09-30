package com.experts.core.biller.statemachine.api.rovo.awsxray.settings;

import com.experts.core.biller.statemachine.api.rovo.awsxray.config.settings.KeyStoreSettings;
import com.experts.core.biller.statemachine.api.rovo.awsxray.config.settings.TrustStoreSettings;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@ConfigurationProperties(prefix = "ssl")
public class SSLSettings {

    @NestedConfigurationProperty
    private KeyStoreSettings keyStore;
    @NestedConfigurationProperty
    private TrustStoreSettings trustStore;

}
