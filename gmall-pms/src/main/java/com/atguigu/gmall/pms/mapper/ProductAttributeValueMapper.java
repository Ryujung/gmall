package com.atguigu.gmall.pms.mapper;

import com.atguigu.gmall.pms.entity.ProductAttribute;
import com.atguigu.gmall.pms.entity.ProductAttributeValue;
import com.atguigu.gmall.to.es.EsProductAttributeValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 存储产品参数信息的表 Mapper 接口
 *
 * @author RyuJung
 * @since 2020-02-19
 */
public interface ProductAttributeValueMapper extends BaseMapper<ProductAttributeValue> {

    List<EsProductAttributeValue> selectProductBasicAttrAndValue(Long id);
	
	List<ProductAttribute> selectProductSaleAttrName(Long id);
}
