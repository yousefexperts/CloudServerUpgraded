package com.experts.core.biller.statemachine.api.rovo.awsxray.utils;

import com.experts.core.biller.statemachine.api.rovo.awsxray.security.AESEncryptor;
import org.mongodb.morphia.annotations.Embedded;

import java.io.Serializable;

@Embedded
public class EncryptedString implements Serializable {
    private String salt;
    private String encryptedAttribute;

    public EncryptedString() {
        super();
        setSalt(AESEncryptor.getSalt());
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEncryptedAttribute() {
        return encryptedAttribute;
    }

    public void setEncryptedAttribute(String encryptedAttribute) {
        this.encryptedAttribute = encryptedAttribute;
    }
}
