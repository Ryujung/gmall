package com.atguigu.gmall.pms;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.pms.entity.Brand;
import com.atguigu.gmall.pms.entity.Product;
import com.atguigu.gmall.pms.service.BrandService;
import com.atguigu.gmall.pms.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class GmallPmsApplicationTests {

    @Resource
    ProductService productService;

    @Resource
    BrandService brandService;

    @Test
    void contextLoads() {
        Product product = productService.getById(1);
        System.out.println(product);
    }

    @Test //测试读写分离
    void testMaster(){
//        Brand brand = new Brand();
//        brand.setName("哈哈哈");

//        brandService.save(brand);

        Brand b = brandService.getById(53);
        System.out.println("保存成功: "+ b);
        //查询的结果为slaver-02保存的值 测试成功
    }

}
