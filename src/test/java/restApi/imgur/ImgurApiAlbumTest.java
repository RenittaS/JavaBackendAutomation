package restApi.imgur;

import dto.AccountInfoResponse;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImgurApiAlbumTest {

    static ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .expectBody("success", is(true))
            .expectBody("data", is(true))
            .build();

    static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setAuth(oauth2(ImgurApiParams.getTOKEN()))
            .build();


    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = Endpoints.getBaseUrl();
        RestAssured.filters(new AllureRestAssured());
    }

    @DisplayName("Get Account base")
    @Test
    @Order(1)
    void getAccountInfoSimpleTest() {
        String url = given().spec(requestSpecification)
                .when()
                .get("https://api.imgur.com/3/account/" + ImgurApiParams.getUserName())
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.url");
        assertThat(url, equalTo(ImgurApiParams.getUserName()));
    }

    @DisplayName("Get Account base using POJO deserialization")
    @Test
    @Order(2)
    void getAccountInfoSimpleTestJson() {
        AccountInfoResponse response = given().spec(requestSpecification)
                .when()
                .get("https://api.imgur.com/3/account/" + ImgurApiParams.getUserName())
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(AccountInfoResponse.class);

        assertThat(response.getStatus(), equalTo(200));
        assertThat(response.getData().getId(), equalTo(Integer.valueOf("156513005")));
        assertThat(response.getData().getUrl(), containsString(ImgurApiParams.getUserName()));
    }

    @DisplayName("Status code is 200")
    @Test
    @Order(3)
    void testStatusCode() {
        given().spec(requestSpecification)
                .when()
                .log().all()
                .expect()
                .statusCode(200)
                .log().all()
                .when()
                .get(baseURI);
    }

    @DisplayName("Response time is less than 5000ms")
    @Test
    @Order(4)
    void testResponseTime() {
        given().spec(requestSpecification)
                .when()
                .log().all()
                .expect()
                .time(lessThan (5000L))
                .log().all()
                .when()
                .get(baseURI);
    }

    @DisplayName("Id contains correct albumHash")
    @Test
    @Order(5)
    void testId() {
        given().spec(requestSpecification)
                .when()
                .log().all()
                .expect()
                .body("data.id", is(ImgurApiParams.getAlbumHash()))
                .log().all()
                .when()
                .get(baseURI);
    }

    @DisplayName("Title contains correct data")
    @Test
    @Order(6)
    void testTitle() {
        given().spec(requestSpecification)
                .when()
                .log().all()
                .expect()
                .body("data.title", is("My new work album"))
                .log().all()
                .when()
                .get(baseURI);
    }

    @DisplayName("Description contains correct data")
    @Test
    @Order(7)
    void testDescription() {
        given().spec(requestSpecification)
                .when()
                .log().all()
                .expect()
                .body("data.description", is("This albums contains a lot of pop art pictures. Be prepared to " +
                        "be surprised. Kisses!"))
                .log().all()
                .when()
                .get(baseURI);
    }

    @DisplayName("Set favourite Album is success")
    @Test
    @Order(8)
    void testSetFavouriteAlbum() {
        given().spec(requestSpecification)
                .when()
                .log().all()
                .expect()
                .body("success", is(true))
                .log().all()
                .when()
                .post(baseURI + Endpoints.getFavouriteUrl());
    }

    @DisplayName("Update Album title")
    @Test
    @Order(9)
    void testUpdateTitle() {
        given().spec(requestSpecification)
                .when()
                .formParam("title", "My new work album")
                .log().all()
                .expect()
                .spec(responseSpecification)
                .log().all()
                .when()
                .put(baseURI);
    }

    @DisplayName("Get album image")
    @Test
    @Order(10)
    void testAlbumImage() {
        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
                .expectBody("data.title", is("The best pop art image"))
                .expectBody("data.id", is(ImgurApiParams.getImageHash()))
                .build();

        given().spec(requestSpecification)
                .when()
                .log().all()
                .expect()
                .spec(responseSpecification)
                .log().all()
                .when()
                .get(baseURI + Endpoints.getAlbumUrl());
    }

    @DisplayName("Add image to Album")
    @Test
    @Order(11)
    void testAddImage() {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setAuth(oauth2(ImgurApiParams.getTOKEN()))
                .addFormParam("ids[]", "zNRHIng")
                .build();

        given().spec(requestSpecification)
                .when()
                .log().all()
                .expect()
                .spec(responseSpecification)
                .log().all()
                .when()
                .post(baseURI + Endpoints.getAddImageUrl());
    }

    @DisplayName("Remove image from Album")
    @Test
    @Order(12)
    void testRemoveImage() {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setAuth(oauth2(ImgurApiParams.getTOKEN()))
                .addFormParam("ids[]", "zNRHIng")
                .build();

        given().spec(requestSpecification)
                .when()
                .log().all()
                .expect()
                .spec(responseSpecification)
                .log().all()
                .when()
                .post(baseURI + Endpoints.getRemoveImageUrl());
    }

    @AfterAll
    static void tearDown() {
        given().spec(requestSpecification)
                .when()
                .formParam("data.title", "My new work album")
                .log().all()
                .expect()
                .spec(responseSpecification)
                .log().all()
                .when()
                .put(baseURI);
    }
}
