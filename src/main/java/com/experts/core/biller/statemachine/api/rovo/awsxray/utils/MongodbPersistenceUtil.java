package com.experts.core.biller.statemachine.api.rovo.awsxray.utils;

public class MongodbPersistenceUtil
{


  public static String sanitize(final String input) {
    if ((input == null) || input.isEmpty()) {
      return input;
    }


    return input.replaceAll("[^A-Za-z0-9@ÄÖÜäöüß.:&/ +_-]", "");
  }



  public static String prepareForRegex(final String input) {
    if ((input == null) || input.isEmpty()) {
      return input;
    }

    return prepareForRegexStartsWith(input) + "$";
  }


  public static String prepareForRegexStartsWith(final String input) {
    if ((input == null) || input.isEmpty()) {
      return input;
    }

    // Remove any \E which is used to terminate the full regular expression.
    return "^\\Q" + sanitize(input.replace("\\E", "")) + "\\E";
  }

}
