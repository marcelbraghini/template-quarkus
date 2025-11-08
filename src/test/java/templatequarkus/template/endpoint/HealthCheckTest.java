package templatequarkus.template.endpoint;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class HealthCheckTest {

    @Test
    public void testGet() {
        given()
                .when().get("/health/ready")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

}