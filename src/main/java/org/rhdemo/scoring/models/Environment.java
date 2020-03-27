package org.rhdemo.scoring.models;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Environment {
    @ConfigProperty(name="cluster.name", defaultValue = "SFO")
    String CLUSTER_NAME;

    @ConfigProperty(name="datagrid.host", defaultValue = "undefined")
    String DATAGRID_HOST;

    @ConfigProperty(name="datagrid.hotrod.port", defaultValue = "-1")
    int DATAGRID_HOTROD_PORT;

    @ConfigProperty(name="kafka.broker.list.host", defaultValue = "undefined")
    String KAFKA_BROKER_LIST_HOST;

    @ConfigProperty(name="kafka.broker.list.port", defaultValue = "-1")
    int KAFKA_BROKER_LIST_PORT;

    @ConfigProperty(name="kafka.transaction.topic", defaultValue = "undefined")
    String KAFKA_TRANSACTION_TOPIC;

    public String CLUSTER_NAME() {
        return CLUSTER_NAME;
    }

    public String DATAGRID_HOST() {
        return DATAGRID_HOST;
    }

    public int DATAGRID_HOTROD_PORT() {
        return DATAGRID_HOTROD_PORT;
    }

    public String KAFKA_BROKER_LIST_HOST() {
        return KAFKA_BROKER_LIST_HOST;
    }

    public int KAFKA_BROKER_LIST_PORT() {
        return KAFKA_BROKER_LIST_PORT;
    }

    public String KAFKA_TRANSACTION_TOPIC() {
        return KAFKA_TRANSACTION_TOPIC;
    }
}
