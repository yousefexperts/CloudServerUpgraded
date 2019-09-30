package com.experts.core.biller.statemachine.api.rovo.awsxray.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MaskingConverter<E extends ILoggingEvent> extends CompositeConverter<E> {

  public static final String CONFIDENTIAL = "CONFIDENTIAL";
  public static final Marker CONFIDENTIAL_MARKER = MarkerFactory.getMarker(CONFIDENTIAL);

  private Pattern keyValPattern;
  private Pattern basicAuthPattern;
  private Pattern urlAuthorizationPattern;

  @Override
  public void start() {
    keyValPattern = Pattern.compile("(pw|pwd|password)=.*?(&|$)");
    basicAuthPattern = Pattern.compile("(B|b)asic ([a-zA-Z0-9+/=]{3})[a-zA-Z0-9+/=]*([a-zA-Z0-9+/=]{3})");
    urlAuthorizationPattern = Pattern.compile("//(.*?):.*?@");
    super.start();
  }

  @Override
  protected String transform(E event, String in) {
    if (!started) {
      return in;
    }
    Marker marker = event.getMarker();
    if (null != marker && CONFIDENTIAL.equals(marker.getName())) {

      Matcher keyValMatcher = keyValPattern.matcher(in);

      Matcher basicAuthMatcher = basicAuthPattern.matcher(in);

      Matcher urlAuthMatcher = urlAuthorizationPattern.matcher(in);

      if (keyValMatcher.find()) {
        String replacement = "$1=XXX$2";
        return keyValMatcher.replaceAll(replacement);
      } else if (basicAuthMatcher.find()) {
        return basicAuthMatcher.replaceAll("$1asic $2XXX$3");
      } else if (urlAuthMatcher.find()) {
        return urlAuthMatcher.replaceAll("//$1:XXX@");
      }
    }
    return in;
  }
}
