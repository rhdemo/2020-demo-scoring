package org.rhdemo.scoring;

import org.jboss.logging.Logger;
import org.rhdemo.scoring.models.Environment;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/env")
public class EnvironmentResource {
    private static final Logger log = Logger.getLogger("scoring");

    @Inject
    Environment env;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> environment() {
        log.info("** env called **");
        Map<String, Object> status = new HashMap<>();
        status.put("LOG_TRANSACTIONS", env.LOG_TRANSACTIONS());
        status.put("CLUSTER_NAME", env.CLUSTER_NAME());
        status.put("DATAGRID_HOST", env.DATAGRID_HOST());
        status.put("DATAGRID_HOTROD_PORT", env.DATAGRID_HOTROD_PORT());
        status.put("KAFKA_BROKER_LIST_HOST", env.KAFKA_BROKER_LIST_HOST());
        status.put("KAFKA_BROKER_LIST_PORT", env.KAFKA_BROKER_LIST_PORT());
        status.put("KAFKA_TRANSACTION_TOPIC", env.KAFKA_TRANSACTION_TOPIC());
        return status;
    }
}
