package com.atguigu.gmall.ums;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class GmallUmsApplicationTests {

    @Test
    void contextLoads() {
        //查看123456的md5加密样式
        System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes()));
    }

}
