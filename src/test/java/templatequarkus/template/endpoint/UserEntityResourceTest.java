package templatequarkus.template.endpoint;

import templatequarkus.template.application.services.UserService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class UserEntityResourceTest {

    @Inject
    UserService userService;

    @Test
    public void testGetAll() {
        given()
                .when().get("/v1/users")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testPostAndGet() {
        given()
                .body("{\"bio\": \"Desenvolvedor Full Stack\",\n" +
                        "        \"blog\": \"https://google.com.br\",\n" +
                        "        \"location\": \"Googleland-Neverland\",\n" +
                        "        \"login\": \"googlebr\",\n" +
                        "        \"name\": \"Google of googles\"}")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .post("/v1/users")
                .then()
                .statusCode(201);

        final String url = "/v1/users/"+userService.findByLogin("googlebr").get_id();
        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .get(url)
                .then()
                .statusCode(200);
    }

    @Test
    public void testPostAndPut() {
        given()
                .body("{\"bio\": \"Desenvolvedor\",\n" +
                        "        \"blog\": \"https://www.linkedin.com/in/marcel-braghini-8b5210b6/\",\n" +
                        "        \"location\": \"Chapecó-SC-Brasil\",\n" +
                        "        \"login\": \"marcelbb\",\n" +
                        "        \"name\": \"Marcel\"}")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .post("/v1/users")
                .then()
                .statusCode(201);

        final String url = "/v1/users/"+userService.findByLogin("marcelbb").get_id();
        given()
                .body("{\"bio\": \"Desenvolvedor\",\n" +
                        "        \"blog\": \"https://www.linkedin.com/in/marcel-braghini-8b5210b6/\",\n" +
                        "        \"location\": \"Chapecó-SC-Brasil\",\n" +
                        "        \"login\": \"marcelbb\",\n" +
                        "        \"name\": \"Marcel Braghini\"}")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .put(url)
                .then()
                .statusCode(200);
    }

    @Test
    public void testPostAndDelete() {
        given()
                .body("{\"bio\": \"DBA\",\n" +
                        "        \"blog\": \"https://google.com.br/\",\n" +
                        "        \"location\": \"Águas de Chapecó-SC-Brasil\",\n" +
                        "        \"login\": \"mariadba\",\n" +
                        "        \"name\": \"Maria DBO\"}")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .post("/v1/users")
                .then()
                .statusCode(201);

        final String url = "/v1/users/"+userService.findByLogin("mariadba").get_id();
        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .delete(url)
                .then()
                .statusCode(200);
    }

}