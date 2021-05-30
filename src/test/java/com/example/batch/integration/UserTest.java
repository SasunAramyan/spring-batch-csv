package com.example.batch.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {

  @LocalServerPort
  private int port;

  @Before
  public void setUp() throws Exception {
    RestAssured.port = port;
  }

  @Test
  public void getAll() throws Exception {
    given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)

        .when()
        .get("/user")
        .then()
        .statusCode(200)
        .assertThat().body("$", hasSize(200))
        .assertThat().body("[0].firstName", equalTo("Leslie"))
        .assertThat().body("[0].lastName", equalTo("Browning"))
        .assertThat().body("[0].date", equalTo("2020-04-15"))
        .assertThat().body("[0].id", equalTo(1))

        .assertThat().body("[99].firstName", equalTo("Felicia"))
        .assertThat().body("[99].lastName", equalTo("Fuller"))
        .assertThat().body("[99].date", equalTo("2021-10-12"))
        .assertThat().body("[99].id", equalTo(100))

        .assertThat().body("[199].firstName", equalTo("Margaret"))
        .assertThat().body("[199].lastName", equalTo("Holmes"))
        .assertThat().body("[199].date", equalTo("2020-04-05"))
        .assertThat().body("[199].id", equalTo(200));

  }
}
