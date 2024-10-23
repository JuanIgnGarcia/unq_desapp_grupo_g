package com.example.demo.webservice
/*

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.equalTo


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserControllerTest {

        @LocalServerPort
        private var port: Int = 0


        @Test
        fun `test user register functionality`() {
            val requestBody = mapOf("name" to "Julian",
            "lastName" to "Alvarez",
            "email" to  "Julian.Alvarez@example.com",
            "address" to  "123 Main Street",
            "password" to  "StrongPassword2!",
            "cvuMercadoPago" to "1111111111111111111111",
            "cryptoAddress" to  "11111111")

            RestAssured.given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .`when`()
                    .post("/register")
                    .then()
                    .statusCode(201)
                    .header("Content-Type", "application/json")
                    .body("id", equalTo( "3"))
                    .body("nameUser", equalTo( "Julian"))
                    .body("lastName", equalTo( "Alvarez"))
                    .body("reputation", equalTo( 0))

        }

    @Test
    fun `test all user app functionality`() {

        RestAssured.given()
                .port(port)
                .`when`()
                .get("/users")
                .then()
                .statusCode(200)
                .header("Content-Type", "application/json")
                .body("data.size()", equalTo(2))
                .body("[0].id", equalTo("1"))
                .body("[0].nameUser", equalTo("Marcos"))
                .body("[0].lastName", equalTo("Dias"))
                .body("[0].reputation", equalTo(0))
                .body("[1].id", equalTo("2"))
                .body("[1].nameUser", equalTo("Joaquin"))
                .body("[1].lastName", equalTo("Villanueva"))
                .body("[1].reputation", equalTo(0))

    }

}*/
