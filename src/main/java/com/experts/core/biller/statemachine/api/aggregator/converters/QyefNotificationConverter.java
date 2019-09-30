package com.experts.core.biller.statemachine.api.aggregator.converters;

import com.experts.core.biller.statemachine.api.akka.model.QyefNotification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kafka.serializer.Decoder;
import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;


public class QyefNotificationConverter implements Encoder<QyefNotification>, Decoder<QyefNotification> {

    private Gson gson;

    private static final Logger LOGGER = LoggerFactory.getLogger(QyefNotificationConverter.class);

    public QyefNotificationConverter(VerifiableProperties verifiableProperties) {
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    @Override
    public byte[] toBytes(QyefNotification qyefNotification) {
        return toJsonString(qyefNotification).getBytes();
    }

    public String toJsonString(QyefNotification qyefNotification) {
        return gson.toJson(qyefNotification);
    }

    @Override
    public QyefNotification fromBytes(byte[] bytes) {
        try {
            return gson.fromJson(new String(bytes, "UTF-8"), QyefNotification.class);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Can't convert json to object ", e.getMessage());
        }
        return null;
    }

    public QyefNotification fromString(String string) {
        return gson.fromJson(string, QyefNotification.class);
    }
}
