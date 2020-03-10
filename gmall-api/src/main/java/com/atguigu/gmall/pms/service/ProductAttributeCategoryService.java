package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.ProductAttributeCategory;
import com.atguigu.gmall.vo.product.PageInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 产品属性分类表 服务类
 * </p>
 *
 * @author RyuJung
 * @since 2020-02-19
 */
public interface ProductAttributeCategoryService extends IService<ProductAttributeCategory> {
    /**
     * 分页查询所有的属性分类
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    PageInfoVo productAttributeCategoryPageInfo(Integer pageSize, Integer pageNum);
}
