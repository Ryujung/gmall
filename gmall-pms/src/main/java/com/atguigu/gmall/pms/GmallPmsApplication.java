package com.atguigu.gmall.pms;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1.整合dubbo
 * 2.整合MybatiesPlus
 *
 * logstash整合
 *  ①导入jar包
 *  ②导入日志配置
 *  ③在Kibana中创建相应日志的索引,就可以可视化检索了
 */
@EnableDubbo
@MapperScan(basePackages = "com.atguigu.gmall.pms.mapper")
@SpringBootApplication
public class GmallPmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallPmsApplication.class, args);
    }

}
