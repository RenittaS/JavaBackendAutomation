package mybatis;

import mybatis.db.dao.CategoriesMapper;
import mybatis.db.dao.ProductsMapper;
import mybatis.db.model.Categories;
import mybatis.db.model.CategoriesExample;
import mybatis.db.model.Products;
import mybatis.db.model.ProductsExample;

import java.util.List;

public class MySelects {
    public static void main(String[] args) {

        MyBatisDbService dbService = new MyBatisDbService();
        ProductsMapper mapper = dbService.getProductsMapper();
        CategoriesMapper mapper1 = dbService.getCategoriesMapper();

//        1. Добавить в БД через код 1 категорию
        Categories testCat = new Categories();
        testCat.setTitle("New Category");
        mapper1.insert(testCat);

        CategoriesExample example1 = new CategoriesExample();
        example1.createCriteria()
                .andIdGreaterThanOrEqualTo(0L);
        List<Categories> categories = mapper1.selectByExample(example1);
        System.out.println("1. Добавить в БД через код 1 категорию");
        System.out.println(categories);

//        1. Добавить в БД через код 3 продукта
        Products product1 = new Products();
        product1.setPrice(500);
        product1.setTitle("Eggs");
        product1.setCategoryId(1L);
        mapper.insertSelective(product1);

        Products product2 = new Products();
        product2.setPrice(1500);
        product2.setTitle("Chicken");
        product2.setCategoryId(1L);
        mapper.insertSelective(product2);

        Products product3 = new Products();
        product3.setPrice(900);
        product3.setTitle("Pizza");
        product3.setCategoryId(1L);
        mapper.insertSelective(product3);

        ProductsExample example2 = new ProductsExample();
        example2.createCriteria()
                .andPriceGreaterThan(0);

        List<Products> products2 = mapper.selectByExample(example2);
        System.out.println("1. Добавить в БД через код 3 продукта");
        System.out.println(products2);

//        2. Найти все продукты 1 категории
        ProductsExample example3 = new ProductsExample();
        example3.createCriteria()
                .andCategoryIdEqualTo(1L);

        List<Products> products3 = mapper.selectByExample(example3);
        System.out.println("2. Найти все продукты 1 категории");
        System.out.println(products3);

//        3. Найти все продукты дешевле 1000
        ProductsExample example4 = new ProductsExample();
        example4.createCriteria()
                .andPriceLessThan(1000);

        List<Products> products4 = mapper.selectByExample(example4);
        System.out.println("3. Найти все продукты дешевле 1000");
        System.out.println(products4);

//        4. Найти все продукты от a до h
        ProductsExample example5 = new ProductsExample();
        example5.createCriteria()
                .andTitleBetween("A", "H");

        List<Products> products5 = mapper.selectByExample(example5);
        System.out.println("4. Найти все продукты от a до h");
        System.out.println(products5);

    }
}
