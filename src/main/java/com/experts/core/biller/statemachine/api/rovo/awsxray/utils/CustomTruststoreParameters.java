package com.experts.core.biller.statemachine.api.rovo.awsxray.utils;

import org.apache.camel.util.jsse.TrustManagersParameters;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


public class CustomTruststoreParameters extends TrustManagersParameters
{

  @Override
  public TrustManager[] createTrustManagers() throws GeneralSecurityException, IOException {
    return  new TrustManager[]{
        new X509TrustManager() {
          public void checkClientTrusted(X509Certificate[] chain, String authType)
              throws CertificateException {
          }

          public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
          }

          public void checkServerTrusted(X509Certificate[] chain, String authType)
              throws CertificateException {
            // This will never throw an exception.
            // This doesn't check anything at all: it's insecure.
          }
        }
    };
  }
}
