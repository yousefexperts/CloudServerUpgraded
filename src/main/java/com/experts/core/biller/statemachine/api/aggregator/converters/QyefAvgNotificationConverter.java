package com.experts.core.biller.statemachine.api.aggregator.converters;

import com.experts.core.biller.statemachine.api.akka.model.QyefAverageNotification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kafka.serializer.Decoder;
import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;


public class QyefAvgNotificationConverter implements Encoder<QyefAverageNotification>, Decoder<QyefAverageNotification> {

    private Gson gson;

    private static final Logger LOGGER = LoggerFactory.getLogger(QyefAvgNotificationConverter.class);

    public QyefAvgNotificationConverter(VerifiableProperties verifiableProperties) {
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    @Override
    public byte[] toBytes(QyefAverageNotification weatherAvgNotification) {
        return toJsonString(weatherAvgNotification).getBytes();
    }

    public String toJsonString(QyefAverageNotification weatherAvgNotification) {
        return gson.toJson(weatherAvgNotification);
    }

    @Override
    public QyefAverageNotification fromBytes(byte[] bytes) {
        try {
            return gson.fromJson(new String(bytes, "UTF-8"), QyefAverageNotification.class);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Can't convert json to object ", e.getMessage());
        }
        return null;
    }

    public QyefAverageNotification fromString(String string) {
        return gson.fromJson(string, QyefAverageNotification.class);
    }
}
