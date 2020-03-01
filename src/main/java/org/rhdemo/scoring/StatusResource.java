package org.rhdemo.scoring;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/status")
public class StatusResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> status() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "OK");
        return status;
    }
}
