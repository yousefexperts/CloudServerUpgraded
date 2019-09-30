package com.experts.core.biller.statemachine.api.aggregator.db;

import org.springframework.data.mongodb.core.query.Query;

import com.experts.core.biller.statemachine.api.akka.model.QyefAverageNotification;
import com.experts.core.biller.statemachine.api.akka.model.QyefNotification;

import java.util.List;

/**
 * Author: Yuliia Vovk
 * Date: 22.02.16
 * Time: 16:32
 */
public interface IQyefRepository {

    void insert(QyefNotification notification);
    List<QyefNotification> findAll(Query query);
    QyefAverageNotification getAverageInfoByCityAndStreet(String city, String street);

}
