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
class UserOfferControllerTest {

    @LocalServerPort
    private var port: Int = 0


    @Test
    fun `test publish new offer functionality`() {
        val requestBody = mapOf("cryptoSymbol" to "AXSUSDT",
                "cryptoMounts" to 10.0,
                "cryptoPrice" to  4.70,
                "userId" to  "2",
                "offerType" to  "BUY" )

        RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .`when`()
                .post("/publish")
                .then()
                .statusCode(201)
                .header("Content-Type", "application/json")
                .body("cryptoSymbol", equalTo( "AXSUSDT"))
                .body("cryptoMounts", equalTo( 10.0F))
                .body("cryptoPrice", equalTo( 4.70F))
                .body("argsMounts", equalTo( 40643.25F))
                .body("userName", equalTo( "Joaquin"))
                .body("userLastName", equalTo( "Villanueva"))
                .body("offerType", equalTo( "BUY"))

    }

    @Test
    fun `test all offers functionality`() {

        RestAssured.given()
                .port(port)
                .`when`()
                .get("/offers")
                .then()
                .statusCode(200)
                .header("Content-Type", "application/json")
                .body("data.size()", equalTo(3))
                .body("[0].userOfferId", equalTo("1"))
                .body("[0].cryptoSymbol", equalTo("ALICEUSDT"))
                .body("[0].userLastName", equalTo("Dias"))
                .body("[0].offerType", equalTo("SELL"))
                .body("[1].userOfferId", equalTo("2"))
                .body("[1].cryptoSymbol", equalTo("AAVEUSDT"))
                .body("[1].userLastName", equalTo("Dias"))
                .body("[1].offerType", equalTo("BUY"))
                .body("[2].userOfferId", equalTo("3"))
                .body("[2].cryptoSymbol", equalTo("NEOUSDT"))
                .body("[2].userLastName", equalTo("Villanueva"))
                .body("[2].offerType", equalTo("BUY"))

    }

    @Test
    fun `I return all offers from a user`() {

        RestAssured.given()
                .port(port)
                .pathParam("userId", "2")
                .`when`()
                .get("/offers/{userId}")
                .then()
                .statusCode(200)
                .body("data.size()", equalTo(1))
                .body("[0].userOfferId", equalTo("3"))
                .body("[0].cryptoSymbol", equalTo("NEOUSDT"))
                .body("[0].userLastName", equalTo("Villanueva"))
                .body("[0].offerType", equalTo("BUY"))

    }


}*/
