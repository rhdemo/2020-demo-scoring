package org.rhdemo.scoring;

import org.rhdemo.scoring.models.Round;
import org.rhdemo.scoring.services.ProductDB;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/products")
public class ProductResource {
    @Inject
    ProductDB products;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Round> environment() {
        return products.getRounds();
    }
}
