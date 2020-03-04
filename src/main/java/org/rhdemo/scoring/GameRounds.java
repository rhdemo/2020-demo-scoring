package org.rhdemo.scoring;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.StartupEvent;
import org.rhdemo.scoring.models.Round;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.io.InputStream;
import java.util.List;

@ApplicationScoped
public class GameRounds {
    private List<Round> rounds;

    public void init(@Observes StartupEvent ev) throws Exception {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("rounds.json");
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        rounds = objectMapper.readValue(is, new TypeReference<List<Round>>() {});
    }

    public List<Round> getRounds() {
        return rounds;
    }
}
