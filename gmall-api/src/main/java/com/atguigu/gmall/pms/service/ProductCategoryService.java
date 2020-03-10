package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.ProductCategory;
import com.atguigu.gmall.vo.product.PmsProductCategoryWithChildrenItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * @author RyuJung
 * @since 2020-02-19
 */
public interface ProductCategoryService extends IService<ProductCategory> {
    /**
     * 查询菜单以及其子菜单
     * @param i
     * @return
     */
    List<PmsProductCategoryWithChildrenItem> listCategoryWithChildren(Integer i);
}
