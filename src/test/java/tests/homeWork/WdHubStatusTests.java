package tests.homeWork;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;

public class WdHubStatusTests extends TestBase {


    @Test
    void validationJsonSchemaTest() {
        given()
                .auth().basic("user1", "1234")
                .log().uri()
                .log().headers()
                .when()
                .get("/wd/hub/status")
                .then()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/status.wd.hub.response.schema.json"));
    }

    @Test
    void fieldMessageContentTest() {
        given()
                .auth().basic("user1", "1234")
                .log().uri()
                .log().headers()
                .when()
                .get("/wd/hub/status")
                .then()
                .log().body()
                .statusCode(200)
                .body("value.message", is("Selenoid 1.11.3 built at 2024-05-25_12:34:40PM"));
    }

    @Test
    void fieldReadyContentTest() {
        given()
                .auth().basic("user1", "1234")
                .log().uri()
                .log().headers()
                .when()
                .get("/wd/hub/status")
                .then()
                .log().body()
                .statusCode(200)
                .body("value.ready", is(true));
    }

    // Дефект: при некорректном endpoint возвращает status code 200 -> ожидаем 401
    @Test
    void wrongEndpointNegativeTest() {
        given()
                .auth().basic("user1", "1234")
                .log().uri()
                .log().headers()
                .when()
                .get("/wd/hub/stat")
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    void missingLoginValueNegativeTest() {
        given()
                .auth().basic("", "1234")
                .log().uri()
                .log().headers()
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    void missingPasswordValueNegativeTest() {
        given()
                .auth().basic("user1", "")
                .log().uri()
                .log().headers()
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    void loginValueOtherNegativeTest() {
        given()
                .auth().basic("Other", "1234")
                .log().uri()
                .log().headers()
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    void passwordValueOtherNegativeTest() {
        given()
                .auth().basic("Other", "5555")
                .log().uri()
                .log().headers()
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }

}
