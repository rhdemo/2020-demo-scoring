package org.rhdemo.scoring;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ScoringResourceTest {
    static final String DOLLAR_BILL = "{\n" +
            "    \"id\": 0,\n" +
            "    \"name\": \"Dollar bill\",\n" +
            "    \"description\": \"One United States dollar and no cents\",\n" +
            "    \"price\": [1, \".\", 0, 0],\n" +
            "    \"choices\": [9, 1, 0, 5, 0, 1],\n" +
            "    \"answers\": [{\"format\": \"number\"}, {\"format\": \"decimal\"}, {\"format\": \"number\"}, {\"format\": \"number\"}],\n" +
            "    \"image\": \"/static/images/0.jpg\"\n" +
            "  }";

    static final String GUESS = "{\n" +
            "  \"game\": \"1\",\n" +
            "  \"player\": \"Bill\",\n" +
            "  \"pointsAvailable\": 100,\n" +
            "  \"item\": " + DOLLAR_BILL + "," +
            "  \"answers\": [\n";

    static final String CORRECT = GUESS +
            "    {\n" +
            "        \"format\": \"number\",\n" +
            "        \"result\": \"pending\",\n" +
            "        \"number\": 1\n" +
            "    },\n" +
            "    {\n" +
            "        \"format\": \"decimal\",\n" +
            "        \"result\": \"pending\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"format\": \"number\",\n" +
            "        \"result\": \"pending\",\n" +
            "        \"number\": 0\n" +
            "    },\n" +
            "    {\n" +
            "        \"format\": \"number\",\n" +
            "        \"result\": \"pending\",\n" +
            "        \"number\": 0\n" +
            "    }\n" +
            "  ]" +
            "}";


    @Test
    public void testHelloEndpoint() {
        given()
          .when().contentType("application/json").body(CORRECT).post("/scores")
          .then()
             .statusCode(200)
             .body("points", equalTo(100));
    }

}