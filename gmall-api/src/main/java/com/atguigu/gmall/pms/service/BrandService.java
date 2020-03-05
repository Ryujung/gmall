package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.Brand;
import com.atguigu.gmall.vo.product.PageInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author RyuJung
 * @since 2020-02-19
 */
public interface BrandService extends IService<Brand> {
    /**
     * 显示品牌的分页信息
     * @param keyword 查询关键字
     * @param pageNum 当前页
     * @param pageSize 页面大小
     * @return 携带页面信息的Vo
     */
    PageInfoVo brandPageInfo(String keyword, Integer pageNum, Integer pageSize);
}
