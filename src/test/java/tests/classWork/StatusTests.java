package tests.classWork;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class StatusTests {
  /*   1. Отправить запрос на : https://selenoid.autotests.cloud/status
       2. Получить ответ вида:  {"total":5,"used":0,"queued":0,"pending":0,"browsers":{"chrome":{"127.0":{},"128.0":{}},"firefox":{"124.0":{},"125.0":{}},"opera":{"108.0":{},"109.0":{}}}}
       3. Проверить, что total = 5
   */

    // Проверка содержимого поля total.
    @Test
    void fieldTotal5Test() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(5));
    }

    // Проверка содержимого поля total, с логом ответа.
    @Test
    void fieldTotal5WithResonsLogTest() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .body("total", is(5));
    }

    // Проверка содержимого поля total, с полным логом.
    @Test
    void fieldTotal5WithAllLogTest() {
        given()
                .log().all()
        .when()
                .get("https://selenoid.autotests.cloud/status")
        .then()
                .log().all()
                .body("total", is(5));
    }

    // Проверка содержимого поля total, с логированием URI, в запросе и логированием status и body в response.
    @Test
    void fieldTotal5WithASomeLogTest() {
        given()
                .log().uri()
        .when()
                .get("https://selenoid.autotests.cloud/status")
        .then()
                .log().status()
                .log().body()
                .body("total", is(5));
    }

    // Проверка status code.
    @Test
    void status200Test() {
        given()
                .log().uri()
        .when()
                .get("https://selenoid.autotests.cloud/status")
        .then()
                .log().all()
                .statusCode(200);
    }

    // Проверка присутствия полей: "used", "queued", "pending", "browsers".
    @Test
    void consistKeysTest() {
        given()
                .log().uri()
        .when()
                .get("https://selenoid.autotests.cloud/status")
        .then()
                .log().body()
                .statusCode(200)
                .body("",hasKey("used"))
                .body("",hasKey("queued"))
                .body("",hasKey("pending"))
                .body("",hasKey("pending"));
    }

    // Проверка версии chrome "127.0" и "128.0".
    @Test
    void chromeVersionTest() {
        given()
                .log().uri()
        .when()
                .get("https://selenoid.autotests.cloud/status")
        .then()
                .log().body()
                .statusCode(200)
                .body("browsers.chrome", hasKey("127.0"))
                .body("browsers.chrome", hasKey("128.0"));
    }


    // Проверка json schema
    @Test
    void statusSchemaTest() {
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/status.response.schema.json"));
    }

    // Проверка содержимого поля total, с проверкой status и json schema.
    @Test
    void fieldTotal5withSchemaTest() {
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/status.response.schema.json"))
                .body("total", is(5));
    }

}