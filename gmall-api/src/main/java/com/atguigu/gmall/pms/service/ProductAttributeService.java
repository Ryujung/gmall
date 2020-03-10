package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.ProductAttribute;
import com.atguigu.gmall.vo.product.PageInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品属性参数表 服务类
 * </p>
 *
 * @author RyuJung
 * @since 2020-02-19
 */
public interface ProductAttributeService extends IService<ProductAttribute> {
    /**
     * 查询当前属性分类下的所有销售属性和基本参数
     *
     * @param cid 对应数据表中的 `product_attribute_category_id` 字段
     * @param type 对应数据表中的 `type` 属性
     * @param pageSize 页面大小
     * @param pageNum 当前页
     * @return 返回封装的分页信息对象
     */
    PageInfoVo getProductAttributes(Long cid, Integer type, Integer pageSize, Integer pageNum);
}
