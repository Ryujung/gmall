package com.atguigu.gmall.to.es;

import com.atguigu.gmall.pms.entity.SkuStock;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author RyuJung
 * @date 2020-3-8 13:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EsSkuProductInfo extends SkuStock implements Serializable {

    private String skuTitle;//sku的特定标题
    /**
     * 每个sku特有的的属性和值
     */
    private List<EsProductAttributeValue> esProductAttributeValues;
}
