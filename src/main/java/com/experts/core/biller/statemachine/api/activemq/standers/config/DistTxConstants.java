package com.experts.core.biller.statemachine.api.activemq.standers.config;

public class DistTxConstants {

    public static  String VIEW_PREFIX = "/WEB-INF/views/";
    public static  String VIEW_SUFFIX = ".html";

    public static  String DEV_PROPERTIES_RESOURCE = "/META-INF/dev.properties";
    public static  String TEST_PROPERTIES_RESOURCE = "/META-INF/test.properties";

    public static  String LOCALE_TURKISH = "tr";

    public static  String DEFAULT_BROKER_URL_SERVER0 = "tcp://localhost:61616";
    public static  String DEFAULT_BROKER_URL_SERVER1 = "tcp://localhost:61617";

    public static  String GROUP_ADDRESS = "231.7.7.7";
    public static  int GROUP_PORT = 9876;

    public static  String USERNAME = "admin";
    public static  String PASSWD = "admin";
    public static  String ACCOUNTS_QUEUE = "accounts-queue";

    public static  String UNIQUE_RESOURCE_NAME_DATASOURCE = "xads";
    public static  String UNIQUE_RESOURCE_NAME_MESSAGE_QUEUE = "xamq";

}