package restApi.imgur;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.Matchers.lessThan;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImgurApiAlbumTest {

    static ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .expectBody("success", is(true))
            .expectBody("data", is(true))
            .build();

    static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setAuth(oauth2(ImgurApiParams.TOKEN))
            .build();


    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = Endpoints.BASE_URI;
    }

    @DisplayName("Status code is 200")
    @Test
    @Order(1)
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
    @Order(2)
    void testResponseTime() {
        given().spec(requestSpecification)
                .when()
                .log().all()
                .then()
                .time(lessThan (5000L))
                .log().all()
                .when()
                .get(baseURI);
    }

    @DisplayName("Id contains correct albumHash")
    @Test
    @Order(3)
    void testId() {
        given().spec(requestSpecification)
                .when()
                .log().all()
                .expect()
                .body("data.id", is(ImgurApiParams.ALBUM_HASH))
                .log().all()
                .when()
                .get(baseURI);
    }

    @DisplayName("Title contains correct data")
    @Test
    @Order(4)
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
    @Order(5)
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
    @Order(6)
    void testSetFavouriteAlbum() {
        given().spec(requestSpecification)
                .when()
                .log().all()
                .expect()
                .body("success", is(true))
                .log().all()
                .when()
                .post(baseURI + Endpoints.FAVOURITE_URL);
    }

    @DisplayName("Update Album title")
    @Test
    @Order(7)
    void testUpdateTitle() {
        given().spec(requestSpecification)
                .when()
                .log().all()
                .formParam("title", "My new work album")
                .expect()
                .log().all()
                .spec(responseSpecification)
                .when()
                .put(baseURI);
    }

    @DisplayName("Get album image")
    @Test
    @Order(8)
    void testAlbumImage() {
        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
                .expectBody("data.title", is("The best pop art image"))
                .expectBody("data.id", is(ImgurApiParams.IMAGE_HASH))
                .build();

        given().spec(requestSpecification)
                .when()
                .log().all()
                .expect()
                .spec(responseSpecification)
                .log().all()
                .when()
                .get(baseURI + Endpoints.ALBUM_URL);
    }

    @DisplayName("Add image to Album")
    @Test
    @Order(9)
    void testAddImage() {
//        RequestSpecification imageRequestSpecification = new RequestSpecBuilder()
//                .addHeader("Authorization", ImgurApiParams.TOKEN)
//                .addFormParam("ids[]", "zNRHIng")
//                .build();

        given().spec(requestSpecification)
                .when()
                .log().all()
                //.spec(imageRequestSpecification)
                .formParam("ids[]", "zNRHIng")
                .expect()
                .log().all()
                .spec(responseSpecification)
                .when()
                .post(baseURI + Endpoints.ADD_IMAGE_URL);
    }

    @DisplayName("Remove image from Album")
    @Test
    @Order(10)
    void testRemoveImage() {
        given().spec(requestSpecification)
                .when()
                .log().all()
                .formParam("ids[]", "zNRHIng")
                .expect()
                .log().all()
                .spec(responseSpecification)
                .when()
                .post(baseURI + Endpoints.REMOVE_IMAGE_URL);
    }

    @AfterAll
    static void tearDown() {
        given().spec(requestSpecification)
                .when()
                .log().all()
                .formParam("data.title", "My new work album")
                .expect()
                .log().all()
                .spec(responseSpecification)
                .when()
                .put(baseURI);
    }
}
