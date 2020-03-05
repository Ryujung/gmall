package com.atguigu.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.pms.entity.Product;
import com.atguigu.gmall.pms.mapper.ProductMapper;
import com.atguigu.gmall.pms.service.ProductService;
import com.atguigu.gmall.vo.product.PageInfoVo;
import com.atguigu.gmall.vo.product.PmsProductQueryParam;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author RyuJung
 * @since 2020-02-19
 */
@Component
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public PageInfoVo productPageInfo(PmsProductQueryParam param) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();

        //判断各个查询条件是否存在
        if (param.getBrandId() != null) {
            //品牌id存在
            queryWrapper.eq("brand_id", param.getBrandId());
        }
        if (!StringUtils.isEmpty(param.getKeyword())) {
            queryWrapper.like("name", param.getKeyword());
        }
        if (!StringUtils.isEmpty(param.getProductCategoryId())) {
            queryWrapper.eq("product_category_id", param.getProductCategoryId());
        }
        if (!StringUtils.isEmpty(param.getProductSn())) {
            queryWrapper.like("product_sn", param.getProductSn());
        }
        if (param.getPublishStatus() != null) {
            queryWrapper.eq("publish_status", param.getPublishStatus());
        }
        if (param.getVerifyStatus() != null) {
            queryWrapper.eq("verify_status", param.getVerifyStatus());
        }

        //通过所有的查询条件,通过mapper查询出符合结果的内容, 封装到IPage对象中
        IPage<Product> page = productMapper.selectPage(new Page<>(param.getPageNum(), param.getPageSize()), queryWrapper);

        //根据返回参数的类型,再将IPage中的信息重新封装进Vo中
        return new PageInfoVo(page.getTotal(), page.getPages(), param.getPageSize(), page.getRecords(), page.getCurrent());
    }
}
