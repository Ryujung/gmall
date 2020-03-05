package com.atguigu.gmall.pms.config;

import io.shardingjdbc.core.api.MasterSlaveDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 数据源配置类
 *
 * @author RyuJung
 * @date 2020-2-19 19:12
 */
@Configuration
public class PmsDataSourceConfig {
    @Bean
    public DataSource dataSource() throws IOException, SQLException {
        File file = ResourceUtils.getFile("classpath:sharding-jdbc.yml");

        DataSource dataSource = MasterSlaveDataSourceFactory.createDataSource(file);

        return dataSource;
    }
}
