package com.atguigu.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.pms.entity.*;
import com.atguigu.gmall.pms.mapper.*;
import com.atguigu.gmall.pms.service.ProductService;
import com.atguigu.gmall.to.es.EsProduct;
import com.atguigu.gmall.to.es.EsProductAttributeValue;
import com.atguigu.gmall.to.es.EsSkuProductInfo;
import com.atguigu.gmall.vo.product.PageInfoVo;
import com.atguigu.gmall.vo.product.PmsProductParam;
import com.atguigu.gmall.vo.product.PmsProductQueryParam;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private ProductAttributeValueMapper productAttributeValueMapper;

    @Resource
    private ProductFullReductionMapper productFullReductionMapper;

    @Resource
    private ProductLadderMapper productLadderMapper;

    @Resource
    private SkuStockMapper skuStockMapper;

    //当前线程共享变量
    private ThreadLocal<Long> productId = new ThreadLocal<>();

    @Override
    public Product getProductById(Long id) {
        return productMapper.selectById(id);
    }

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

    /**
     * pms_product 保存商品基本信息
     * pms_product_attribute_value 商品属性值
     * pms_product_attribute 保存商品对应的所有属性
     * pms_product_full_reduction 保存商品的满减信息
     * pms_product_ladder 阶梯价格表
     * pms_sku_stock 库存sku表
     * <p>
     * 事务考虑:
     * 1. 哪些东西是一定要回滚的,哪些东西即使出错了也不必要回滚
     * 商品的核心信息(基本数据, sku)保存时不要受到别的信息出现异常导致回滚
     * 核心信息不出异常则不用回滚
     * <p>
     * 2. 通过事务的传播行为来实现功能
     * REQUIRED 必须 : 必须要有事务, 如果没有就新建一个事务
     * REQUIREDS_NEW 必须有新事务 : 必须要新建一个事务,不与其他事务共用
     *
     * @param productParam 商品的全部参数信息
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveProduct(PmsProductParam productParam) {
        ProductServiceImpl currentProxy = (ProductServiceImpl) AopContext.currentProxy();
        //保存商品基本信息
        currentProxy.saveBaseInfo(productParam);

        currentProxy.saveSkuStockList(productParam);

        currentProxy.saveProductAttributeValue(productParam);

        currentProxy.saveProductFullReduction(productParam);

        currentProxy.saveProductLadder(productParam);
    }

    @Override
    public void updatePublishStatus(List<Long> ids, Integer publishStatus) {
        ids.forEach(id -> {
            Product product = getProductById(id);
            //JavaBean都应该用包装类型,防止默认值覆盖数据库信息
            Product tempProduct = new Product();
            tempProduct.setId(id);
            tempProduct.setPublishStatus(publishStatus);

            //MyBatis-Plus的默认更新策略为不更新null值,只更新有效的值
            productMapper.updateById(tempProduct);

            //对于es要保存商品信息(sku为单位),所以要查出商品的sku,给es中保存,sku分为基本信息和特有信息
            List<SkuStock> skuStocks =
                    skuStockMapper.selectList(new QueryWrapper<SkuStock>().eq("product_id", id));
            //将根据id查询的每一个sku对象(列表)封装到EsSkuProductInfo的对应的对象中
            ArrayList<EsSkuProductInfo> esSkuProductInfos = new ArrayList<>(skuStocks.size());

            //创建将要保存到es中的数据模型,并拷贝product的属性值作为基础信息
            EsProduct esProduct = new EsProduct();
            BeanUtils.copyProperties(product, esProduct);

            //查出当前商品的sku属性
            List<ProductAttribute> skuAttributeNames = productAttributeValueMapper.selectProductSaleAttrName(id);
            //遍历每一个sku对象
            skuStocks.forEach(skuStock -> {
                EsSkuProductInfo esSkuProductInfo = new EsSkuProductInfo();
                BeanUtils.copyProperties(skuStock, esSkuProductInfo);

                //查出这个sku所有销售属性对应的值
                String subTitle = esProduct.getName();
                if (!StringUtils.isEmpty(skuStock.getSp1())) {
                    subTitle += " " + skuStock.getSp1();
                }
                if (!StringUtils.isEmpty(skuStock.getSp2())) {
                    subTitle += " " + skuStock.getSp2();
                }
                if (!StringUtils.isEmpty(skuStock.getSp3())) {
                    subTitle += " " + skuStock.getSp3();
                }
                //sku的特有标题
                esSkuProductInfo.setSkuTitle(subTitle);

                List<EsProductAttributeValue> esProductAttributeValues = new ArrayList<>();
                esProductAttributeValues.forEach(esProductAttributeValue -> {
                    EsProductAttributeValue value = new EsProductAttributeValue();
                    value.setId(esProductAttributeValue.getId());
                    value.setName(esProductAttributeValue.getName());
                    value.setProductAttributeId(esProductAttributeValue.getProductAttributeId());
                    value.setType(esProductAttributeValue.getType());


                    esProductAttributeValues.add(esProductAttributeValue);
                });

                esSkuProductInfo.setEsProductAttributeValues(esProductAttributeValues);
                esSkuProductInfos.add(esSkuProductInfo);
            });

            esProduct.setEsSkuProductInfos(esSkuProductInfos);


            //TODO esProduct.setStock();实时信息,暂时不处理, 后面再单独处理
            //复制公共属性信息,查出该商品的公共属性
            List<EsProductAttributeValue> esProductAttributeValues = productAttributeValueMapper.selectProductBasicAttrAndValue(id);
            //将商品所有的sku信息拿出来

        });

    }

    //保存商品基本信息
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    public void saveBaseInfo(PmsProductParam productParam) {
        Product product = new Product();
        BeanUtils.copyProperties(productParam, product);
        productMapper.insert(product);

        productId.set(product.getId());

        log.debug("保存的商品基本信息完成,商品id为 {}", productId.get());
    }

    //pms_sku_stock 库存sku表
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveSkuStockList(PmsProductParam productParam) {
        List<SkuStock> skuStockList = productParam.getSkuStockList();
        for (int i = 1; i <= skuStockList.size(); i++) {
            SkuStock skuStock = skuStockList.get(i - 1);
            skuStock.setProductId(productId.get());

            //skuCode允许为空,当sku为空时,则自动生成默认的skuCode值
            if (StringUtils.isEmpty(skuStock.getSkuCode())) {
                //生成规则: 商品id + _ + skuStockList的索引角标i
                skuStock.setSkuCode(productId.get() + "_" + i);
            }
            skuStockMapper.insert(skuStock);
        }
    }

    //pms_product_ladder 阶梯价格表
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveProductLadder(PmsProductParam productParam) {
        List<ProductLadder> productLadderList = productParam.getProductLadderList();
        productLadderList.forEach(ladder -> {
            ladder.setProductId(productId.get());
            productLadderMapper.insert(ladder);
        });
    }

    //pms_product_full_reduction 保存商品的满减信息
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveProductFullReduction(PmsProductParam productParam) {
        List<ProductFullReduction> productFullReductionList = productParam.getProductFullReductionList();
        productFullReductionList.forEach(reduction -> {
            reduction.setProductId(productId.get());
            productFullReductionMapper.insert(reduction);
        });
    }

    //保存pms_product_attribute_value 商品属性值表
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveProductAttributeValue(PmsProductParam productParam) {
        List<ProductAttributeValue> productAttributeValueList = productParam.getProductAttributeValueList();
        productAttributeValueList.forEach(item -> {
            item.setProductId(productId.get());
            System.out.println("保存pms_product_attribute_value属性: " + item);
            productAttributeValueMapper.insert(item);
        });
    }

}
