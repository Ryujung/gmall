package com.atguigu.gmall.admin.ums.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.to.CommonResult;
import com.atguigu.gmall.ums.entity.MemberLevel;
import com.atguigu.gmall.ums.service.MemberLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author RyuJung
 * @date 2020-3-5 19:49
 */
@CrossOrigin
@RestController
@RequestMapping("/memberLevel")
@Api(tags = "UmsMenberLevelController", description = "会员等级管理")
public class UmsMemberLevelController {

    @Reference
    private MemberLevelService memberLevelService;

    /**
     * 查询会员等级列表
     *
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取会员列表")
    public Object memberList() {
        List<MemberLevel> list = memberLevelService.list();
        return new CommonResult().success(list);
    }
}
