package com.atguigu.gmall.vo.product;


import com.atguigu.gmall.pms.entity.ProductCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class PmsProductCategoryWithChildrenItem extends ProductCategory implements Serializable {

    /**
     * 每个菜单中的所有子菜单项组成的列表
     */
    private List<ProductCategory> children;

}
