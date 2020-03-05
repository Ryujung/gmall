package com.atguigu.gmall.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 用户登录参数
 * Created by atguigu 4/26.
 * 使用JSR303的步骤
 * 1.在需要校验的属性上加相应的注解{@Pattern(regex="")可以使用复杂正则校验}
 * 2.开启Spring对校验注解的支持 在需要校验的参数上加{@Valid}注解,并在类前加{@Validated}注解
 * 3.如何感知校验结果?  给开启校验的JavaBean对象后紧跟BindingResult对象
 *
 * 能使用的注解:
 *      ①Hibernate的constraints校验包都可以
 *      ②JSR303规定的都可以
 */
@Data
public class UmsAdminParam {

    @ApiModelProperty(value = "用户名", required = true)
    @NotEmpty(message = "用户名不能为空")
    @Length(min=6,max=18,message="用户名长度必须是6-18位")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "用户头像")
    private String icon;

    @ApiModelProperty(value = "邮箱")
    @Email(message = "邮箱格式不合法")
    private String email;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "备注")
    private String note;
}
