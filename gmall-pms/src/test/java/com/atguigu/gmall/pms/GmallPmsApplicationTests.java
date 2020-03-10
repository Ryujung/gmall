package com.atguigu.gmall.pms;

import com.atguigu.gmall.pms.entity.Brand;
import com.atguigu.gmall.pms.entity.Product;
import com.atguigu.gmall.pms.service.BrandService;
import com.atguigu.gmall.pms.service.ProductService;
import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@SpringBootTest
class GmallPmsApplicationTests {

    @Resource
    ProductService productService;

    @Resource
    BrandService brandService;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    JestClient jestClient;


    @Test
    void contextLoads() {
        Product product = productService.getById(1);
        System.out.println(product);
    }

    @Test
        //测试读写分离
    void testMaster() {
//        Brand brand = new Brand();
//        brand.setName("哈哈哈");
//
//        brandService.save(brand);

        Brand b = brandService.getById(54);
        System.out.println("保存成功: " + b);
        //查询的结果为slaver-02保存的值 测试成功
    }

    @Test
    void testStringRedistemplate() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("hello", "world");

        System.out.println("向redis中缓存的数据为: " + ops.get("hello"));
    }

    @Test
    void testRedistemplate() {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set("hello", "world");

        System.out.println("向redis中缓存的数据为: " + ops.get("hello"));
    }

    @Test
    void testElasticsearchClusterByJest() {
        System.out.println(jestClient);
    }

    /**
     * 基本步骤:
     * 1. Action action = new Crud.Builder(数据).其他构建信息.build();
     * 2. DocumentResult execute = jestClient.execute(action);
     * 3. 获取结果DocumentResult数据即可
     */
    @Test
    void testJest() throws IOException {
        User user = new User("zhangsan", 23);

        Index index = new Index.Builder(user)
                .index("user")
                .type("info")
                .build();

        DocumentResult execute = jestClient.execute(index);

        System.out.println("执行成功? ==>" + execute.isSucceeded());
    }

    @Test
    void testQuery() throws IOException {
        String queryJson = "{\"query\":{\"match_all\":{}}}";
        Search search = new Search.Builder(queryJson).addIndex("user").build();
        SearchResult result = jestClient.execute(search);

        System.out.println("总记录:"+result.getTotal()+", 最大得分"+result.getMaxScore());

        List<SearchResult.Hit<User, Void>> hits = result.getHits(User.class);
        hits.forEach((hit)-> {
            User user = hit.source;
        System.out.println("所有数据"+user);
        });
    }

}

@AllArgsConstructor
@ToString
class User {
    private String name;
    private Integer age;
}