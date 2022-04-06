package retrofitMiniMarketApiTest;

import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.*;
import retrofitMiniMarket.api.MiniMarketApiService;
import retrofitMiniMarket.model.Category;
import retrofitMiniMarket.model.Product;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MiniMarketApiTest {

    private static MiniMarketApiService apiService;
    private static Gson gson;
    private static Long id = 0L;

    @BeforeAll
    static void beforeAll() {
        apiService = new MiniMarketApiService();
        gson = new Gson();
    }

    @DisplayName("Get Category by ID (existed)")
    @Test
    @Order(1)
    void testGetCategoryByIdCategoryExist() throws IOException {
        Category category = apiService.getCategory(1);
        Assertions.assertEquals(1, category.getId());
        Assertions.assertEquals("Food", category.getTitle());
    }

    @DisplayName("Get Category by ID (not existed)")
    @Test
    @Order(2)
    void testGetCategoryByIdCategoryNotExist() {
        Assertions.assertThrows(RuntimeException.class, () -> apiService.getCategory(100));
    }

    @DisplayName("Get Products")
    @Test
    @Order(3)
    void testGetProducts() throws IOException {
        Type type = new TypeToken<ArrayList<Product>>() {
        }.getType();
        String json = getJsonResource("src/test/resources/testGetProducts/expected.json");
        List<Product> expected = gson.fromJson(json, type);
        List<Product> actually = apiService.getProducts();
        Assertions.assertEquals(expected.size(), actually.size());
        for (int i = 0; i < expected.size(); i++) {
            assertProduct(expected.get(i), actually.get(i));
        }
    }

    @DisplayName("Create New Product")
    @Test
    @Order(4)
    void testCreateNewProduct() throws IOException {
        Product product = Product.builder()
                .categoryTitle("Food")
                .price(300)
                .title("Fish")
                .build();
        id = apiService.createProduct(product);
        Product expected = apiService.getProduct(id);
        Assertions.assertEquals(id, expected.getId());
    }

    @DisplayName("Update Product")
    @Test
    @Order(5)
    void testUpdateProduct() throws IOException {
        Product product = Product.builder()
                .id(3L)
                .categoryTitle("Food")
                .price(560)
                .title("Fish")
                .build();
        apiService.updateProduct(product);
        Product expected = apiService.getProduct(3L);
        Assertions.assertEquals(560, expected.getPrice());
    }

    @DisplayName("Get Product by ID (existed)")
    @Test
    @Order(6)
    void testGetProductByIdProductExist() throws IOException {
        Product product = apiService.getProduct(1);
        Assertions.assertEquals(1L, product.getId());
        Assertions.assertEquals("Milk", product.getTitle());
        Assertions.assertEquals("Food", product.getCategoryTitle());
    }

    @DisplayName("Get Product by ID (not existed)")
    @Test
    @Order(7)
    void testGetProductByIdProductNotExists() {
        Assertions.assertThrows(RuntimeException.class, () -> apiService.getProduct(100));
    }

    @DisplayName("Delete Product by ID")
    @Test
    @Order(8)
    void testDeleteById() {
        Assertions.assertThrows(EOFException.class, () -> apiService.deleteProduct(id));
        Assertions.assertThrows(RuntimeException.class, () -> apiService.getProduct(id));
    }

    String getJsonResource(String source) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(source));
        return new String(bytes, StandardCharsets.UTF_8);
    }

    void assertProduct(Product expected, Product actually) {
        Assertions.assertEquals(expected.getId(), actually.getId());
        Assertions.assertEquals(expected.getTitle(), actually.getTitle());
        Assertions.assertEquals(expected.getCategoryTitle(), actually.getCategoryTitle());
        Assertions.assertEquals(expected.getPrice(), actually.getPrice());
    }

    @AfterAll
    static void testUpdateProductBack() throws IOException {
        Product product = Product.builder()
                .id(3L)
                .categoryTitle("Food")
                .price(360)
                .title("Fish")
                .build();
        apiService.updateProduct(product);
    }
}
