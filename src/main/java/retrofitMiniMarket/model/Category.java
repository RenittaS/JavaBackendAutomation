package retrofitMiniMarket.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Builder
@Data
public class Category {

    private Long id;
    private ArrayList<Product> products;
    private String title;
}
