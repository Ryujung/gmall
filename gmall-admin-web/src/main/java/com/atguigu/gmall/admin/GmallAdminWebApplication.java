package com.atguigu.gmall.admin;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * VO  : Value Object/View Object --视图对象,
 *          ①把专门要交给前端的数据封装成VO  -- 提交的vo
 *          ②用户提交的数据封装成VO往下传   --  上传的vo
 * DAO : Database Access Object--数据库访问对象,专门对数据库进行CRUD的对象
 * POJO: Plain Old Java Object --古老的单纯的Java对象,JavaBean,用来封装数据
 * DO  : Data Object --数据对象,等同于POJO
 *       Database Object --专门用于封装数据库表的实体类
 * TO  : Transfer Object --传输对象
 *       ①服务之间互相调用,数据传输封装对象
 *       ②对Dao数据进行二次封装,称为某些业务所需的对象(组合成新的对象)
 * DTO : Data Transfer Object --同上
 *
 * @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
 * 不进行数据源的自动配置
 *
 * 如果导入的依赖,引入了自动配置场景
 * 1.这个场景默认配置生效,我们就必须配置它
 * 2.如果不想配置
 *      ①pom文件排除依赖
 *      ②注解方式排除掉这个场景的自动配置类
 */
@EnableDubbo
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GmallAdminWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallAdminWebApplication.class, args);
    }

}
