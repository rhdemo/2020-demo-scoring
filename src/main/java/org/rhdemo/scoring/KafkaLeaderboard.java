package org.rhdemo.scoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.jboss.logging.Logger;
import org.rhdemo.scoring.models.Environment;
import org.rhdemo.scoring.models.PlayerLeaderboardTransaction;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Properties;

@ApplicationScoped
public class KafkaLeaderboard {
    private static final Logger log = Logger.getLogger("scoring.kafka.hq");

    public Producer<Integer, String> createProducer() {
        objectMapper = new ObjectMapper();
        objectWriter = objectMapper.writerFor(PlayerLeaderboardTransaction.class);
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, env.KAFKA_BROKER_LIST_HOST() + ":" + env.KAFKA_BROKER_LIST_PORT());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "scoring-service-" + env.CLUSTER_NAME());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<Integer, String>(props);
    }

    private int count;
    private Producer<Integer, String> producer;
    private ObjectMapper objectMapper;
    private ObjectWriter objectWriter;

    @Inject
    Environment env;

    private boolean initialized;

    @PostConstruct
    public void create() {
        if (env.KAFKA_BROKER_LIST_HOST().equals("unknown")) {
            log.warn("KAFKA_PROKER_LIST_HOST not set.  Kafka messages to leaderboard will not be set.");
            return;
        }
        if (env.KAFKA_BROKER_LIST_PORT() == -1) {
            log.warn("KAFKA_PROKER_LIST_PORT not set.  Kafka messages to leaderboard will not be set.");
            return;
        }
        if (env.KAFKA_TRANSACTION_TOPIC().equals("unknown")) {
            log.warn("KAFKA_TRANSACTION_TOPIC not set.  Kafka messages to leaderboard will not be set.");
            return;
        }
        producer = createProducer();
        initialized = true;
    }

    public void send(PlayerLeaderboardTransaction tx) {
        if (!initialized) return;
        try {
            String message = objectWriter.writeValueAsString(tx);
            producer.send(new ProducerRecord<>(env.KAFKA_TRANSACTION_TOPIC(), count++, message));
            producer.flush();
        } catch (Exception e) {
            log.error("Failed to send transaction: ", e);
        }
    }

}
