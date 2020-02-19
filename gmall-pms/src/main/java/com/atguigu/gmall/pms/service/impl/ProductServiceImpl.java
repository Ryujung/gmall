package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.entity.Product;
import com.atguigu.gmall.pms.mapper.ProductMapper;
import com.atguigu.gmall.pms.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author RyuJung
 * @since 2020-02-19
 */
@Service
@com.alibaba.dubbo.config.annotation.Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
