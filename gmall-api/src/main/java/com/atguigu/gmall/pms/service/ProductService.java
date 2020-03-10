package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.Product;
import com.atguigu.gmall.vo.product.PageInfoVo;
import com.atguigu.gmall.vo.product.PmsProductParam;
import com.atguigu.gmall.vo.product.PmsProductQueryParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
     * 根据id查询商品的详细信息
     *
     * @param id 商品id
     * @return 商品信息封装的实体对象
     */
    Product getProductById(Long id);

    /**
     * 根据复杂查询条件,返回分页数据
     *
     * @param productQueryParam 复杂查询条件封装的实对象
     * @return 分页数据封装的实体Vo对象
     */
    PageInfoVo productPageInfo(PmsProductQueryParam productQueryParam);

    /**
     * 保存商品数据
     *
     * @param productParam 要保存的商品所有属性封装的对象
     */
    void saveProduct(PmsProductParam productParam);

    /**
     * 批量更新商品上下架的信息
     *
     * @param ids           要修改上下架信息的id列表
     * @param publishStatus 上下架参数  0 下架, 1 上架
     */
    void updatePublishStatus(List<Long> ids, Integer publishStatus);
}
