package mybatis;

import mybatis.db.dao.CategoriesMapper;
import mybatis.db.dao.ProductsMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisDbService {

    private SqlSession session;

    //Подключаемся к БД, используя mybatis-comfig
    public MyBatisDbService() {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
                .build(getClass().getResourceAsStream("mybatis-config.xml"));
        session = sqlSessionFactory.openSession();
    }

    public ProductsMapper getProductsMapper() {
        return session.getMapper(ProductsMapper.class);
    }

    public CategoriesMapper getCategoriesMapper() {
        return session.getMapper(CategoriesMapper.class);
    }

}
