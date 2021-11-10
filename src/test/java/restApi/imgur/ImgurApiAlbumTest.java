package restApi.imgur;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.Matchers.lessThan;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImgurApiAlbumTest {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI =
                ImgurApiParams.API_URL + "/" + ImgurApiParams.API_VER + "/album/" + ImgurApiParams.ALBUM_HASH;
    }

    @DisplayName("Status code is 200")
    @Test
    @Order(1)
    void testStatusCode() {
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .statusCode(is(200))
                .log()
                .all()
                .when()
                .get(baseURI);
    }

    @DisplayName("Response time is less than 5000ms")
    @Test
    @Order(2)
    void testResponseTime() {
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .then()
                .time(lessThan (5000l))
                .log()
                .all()
                .when()
                .get(baseURI);
    }

    @DisplayName("Id contains correct albumHash")
    @Test
    @Order(3)
    void testId() {
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .body("data.id", is(ImgurApiParams.ALBUM_HASH))
                .log()
                .all()
                .when()
                .get(baseURI);
    }

    @DisplayName("Title contains correct data")
    @Test
    @Order(4)
    void testTitle() {
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .body("data.title", is("My new work album"))
                .log()
                .all()
                .when()
                .get(baseURI);
    }

    @DisplayName("Description contains correct data")
    @Test
    @Order(5)
    void testDescription() {
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .body("data.description", is("This albums contains a lot of pop art pictures. Be prepared to be surprised. Kisses!"))
                .log()
                .all()
                .when()
                .get(baseURI);
    }

    @DisplayName("Set favourite Album is success")
    @Test
    @Order(6)
    void testSetFavouriteAlbum() {
        String url = "/favorite";
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .body("success", is(true))
                .log()
                .all()
                .when()
                .post(url);
    }

    @DisplayName("Update Album title")
    @Test
    @Order(7)
    void testUpdateTitle() {
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .formParam("title", "!!! NEW Title !!!")
                .expect()
                .log()
                .all()
                .body("success", is(true))
                .body("data", is(true))
                .when()
                .put(baseURI);
    }

    @DisplayName("Get album image")
    @Test
    @Order(8)
    void testAlbumImage() {
        String url = "/image/" + ImgurApiParams.IMAGE_HASH;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .body("data.title", is("The best pop art image"))
                .body("data.id", is(ImgurApiParams.IMAGE_HASH))
                .log()
                .all()
                .when()
                .get(url);
    }

    @DisplayName("Add image to Album")
    @Test
    @Order(9)
    void testAddImage() {
        String url = "/add";
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .formParam("ids[]", "zNRHIng")
                .expect()
                .log()
                .all()
                .body("success", is(true))
                .body("data", is(true))
                .when()
                .post(url);
    }

    @DisplayName("Remove image from Album")
    @Test
    @Order(10)
    void testRemoveImage() {
        String url = "/remove_images";
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .formParam("ids[]", "zNRHIng")
                .expect()
                .log()
                .all()
                .body("success", is(true))
                .body("data", is(true))
                .when()
                .post(url);
    }

    @AfterAll
    static void tearDown() {
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .formParam("title", "My new work album")
                .expect()
                .log()
                .all()
                .body("success", is(true))
                .body("data", is(true))
                .when()
                .put(baseURI);
    }
}
