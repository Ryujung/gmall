package com.atguigu.gmall.ums.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.ums.entity.Admin;
import com.atguigu.gmall.ums.mapper.AdminMapper;
import com.atguigu.gmall.ums.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author RyuJung
 * @since 2020-02-19
 */
@Component
@Service  //dubbo的注解,用于暴露服务
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    @Override
    public Admin login(String username, String password) {
        //使用Spring的加密工具包将密码转为md5格式
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());

        QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>()
                .eq("username", username).eq("password", md5Password);

        Admin admin = adminMapper.selectOne(queryWrapper);

        return admin;
    }

    @Override
    public Admin getUserInfo(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username));
    }
}
