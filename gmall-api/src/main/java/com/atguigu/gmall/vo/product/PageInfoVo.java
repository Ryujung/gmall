package com.atguigu.gmall.vo.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author RyuJung
 * @date 2020-2-20 12:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "返回的分页信息模型", description = "包含分页的信息")
public class PageInfoVo implements Serializable {
    @ApiModelProperty("总记录数")
    private Long total;

    @ApiModelProperty("总页码")
    private Long totalPage;

    @ApiModelProperty("页面大小")
    private Long pageSize;

    @ApiModelProperty("分页查出的数据")
    private List<? extends Object> list;

    @ApiModelProperty("当前页")
    private Long pageNum;
}
