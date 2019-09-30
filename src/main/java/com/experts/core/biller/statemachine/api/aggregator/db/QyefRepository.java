package com.experts.core.biller.statemachine.api.aggregator.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.experts.core.biller.statemachine.api.akka.model.QyefAverageNotification;
import com.experts.core.biller.statemachine.api.akka.model.QyefNotification;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Author: Yuliia Vovk
 * Date: 22.02.16
 * Time: 16:50
 */
@Repository
public class QyefRepository implements IQyefRepository {

    private static final String COLLECTION_NAME = "notifications";

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void insert(QyefNotification notification) {
        mongoOperations.insert(notification, COLLECTION_NAME);
    }

    @Override
    public List<QyefNotification> findAll(Query query) {
        return mongoOperations.find(query, QyefNotification.class, COLLECTION_NAME);
    }

    @Override
    public QyefAverageNotification getAverageInfoByCityAndStreet(String city, String street) {
        List<QyefNotification> notifications = findAll(query(where("city").is(city).and("street").is(street)));

        float avgTemperature  = notifications
                .parallelStream()
                .map(QyefNotification::getTemp)
                .reduce(Float::sum)
                .get()/notifications.size();

        float avgLight  = notifications
                .parallelStream()
                .map(QyefNotification::getLight)
                .reduce(Float::sum)
                .get()/notifications.size();

        return new QyefAverageNotification(city, street, avgTemperature, avgLight);
    }
}
