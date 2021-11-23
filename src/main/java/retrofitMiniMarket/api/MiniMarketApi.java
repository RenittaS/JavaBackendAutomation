package retrofitMiniMarket.api;

import retrofitMiniMarket.model.Category;
import retrofitMiniMarket.model.Product;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface MiniMarketApi {

    @GET("api/v1/categories/{id}")
    Call<Category> getCategory(@Path("id") Long id);

    @GET("api/v1/products")
    Call<List<Product>> getProducts();

    @POST("api/v1/products")
    Call<Product> createProduct(@Body Product product);

    @PUT("api/v1/products")
    Call<Product> updateProduct(@Body Product product);

    @GET("api/v1/products/{id}")
    Call<Product> getProduct(@Path("id") Long id);

    @DELETE("api/v1/products/{id}")
    Call<Object> deleteProduct(@Path("id") Long id);
}
