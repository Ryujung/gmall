package com.atguigu.gmall.pms.mapper;

import com.atguigu.gmall.pms.entity.ProductCategory;
import com.atguigu.gmall.vo.product.PmsProductCategoryWithChildrenItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 产品分类 Mapper 接口
 * </p>
 *
 * @author RyuJung
 * @since 2020-02-19
 */
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {
    /**
     * 根据id查询菜单的所有子菜单列表
     *
     * @param categoryId 要查询的菜单id
     * @return 该id下的所有子菜单
     */
    List<PmsProductCategoryWithChildrenItem> listCategoryWithChildren(Integer categoryId);
}
