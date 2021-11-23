package retrofitMiniMarket.api;

import java.io.IOException;
import java.util.List;

import retrofitMiniMarket.model.Category;
import retrofitMiniMarket.model.Product;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MiniMarketApiService {

    private final MiniMarketApi api;

    public MiniMarketApiService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8189/market/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(MiniMarketApi.class);
    }

    public Category getCategory(long id) throws IOException {
        Response<Category> response = api.getCategory(id).execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new RuntimeException(response.errorBody().string());
        }
    }

    public List<Product> getProducts() throws IOException {
        return api.getProducts()
                .execute()
                .body();
    }

    public Long createProduct(Product product) throws IOException {
        return api.createProduct(product)
                .execute()
                .body()
                .getId();
    }

    public Product updateProduct(Product product) throws IOException {
        return api.updateProduct(product)
                .execute()
                .body();
    }

    public Product getProduct(long id) throws IOException {
        Response<Product> response = api.getProduct(id).execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new RuntimeException(response.errorBody().string());
        }
    }

    public void deleteProduct(long id) throws IOException {
        api.deleteProduct(id).execute();
    }

}
