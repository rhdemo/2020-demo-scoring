package org.rhdemo.scoring.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.jboss.logging.Logger;
import org.rhdemo.scoring.ScoringJsonCustomizer;
import org.rhdemo.scoring.models.Environment;
import org.rhdemo.scoring.models.GameStatus;
import org.rhdemo.scoring.models.leaderboard.GameMessage;
import org.rhdemo.scoring.models.leaderboard.Player;
import org.rhdemo.scoring.models.leaderboard.Transaction;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Properties;

@ApplicationScoped
public class KafkaLeaderboard {
    private static final Logger log = Logger.getLogger("scoring.kafka.hq");

    public Producer<String, String> createProducer() {
        objectWriter = objectMapper.writerFor(GameMessage.class);
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, env.KAFKA_BROKER_LIST_HOST() + ":" + env.KAFKA_BROKER_LIST_PORT());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "scoring-service-" + env.CLUSTER_NAME());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }

    private Producer<String, String> producer;
    private ObjectWriter objectWriter;

    @Inject
    Environment env;

    @Inject
    ObjectMapper objectMapper;

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

    public void send(GameStatus state) {
        if (!initialized) return;

        GameMessage message = new GameMessage();
        message.setGame(state.getGame());
        Transaction tx = new Transaction();
        message.setTransaction(tx);
        if (state.getScore().getAward() > 0) {
            tx.setPoints(state.getScore().getAward());
            tx.setCorrect(true);
        }

        Player player = new Player();
        message.setPlayer(player);
        player.setId(state.getPlayer().getId());
        player.setUsername(state.getPlayer().getUsername());
        player.setAvatar(state.getPlayer().getAvatar());
        player.setCreationServer(state.getPlayer().getCreationServer());
        player.setGameServer(state.getPlayer().getGameServer());
        player.setScoringServer(state.getScore().getScoringServer());
        player.setScore(state.getScore().getScore());
        player.setRight(state.getScore().getRight());
        player.setWrong(state.getScore().getWrong());
        player.setGameId(state.getGame().getId());

        try {
            String json = objectWriter.writeValueAsString(message);
            producer.send(new ProducerRecord<>(env.KAFKA_TRANSACTION_TOPIC(), player.getId(), json));
            producer.flush();
        } catch (Exception e) {
            log.error("Failed to send transaction: ", e);
        }
    }

}
