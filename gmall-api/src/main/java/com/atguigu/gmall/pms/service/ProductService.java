package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.Product;
import com.atguigu.gmall.vo.product.PageInfoVo;
import com.atguigu.gmall.vo.product.PmsProductQueryParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author RyuJung
 * @since 2020-02-19
 */
public interface ProductService extends IService<Product> {

    /**
     * 根据复杂查询条件,返回分页数据
     * @param productQueryParam
     * @return
     */
    PageInfoVo productPageInfo(PmsProductQueryParam productQueryParam);
}
