package com.atguigu.gmall.admin.aop;

import com.atguigu.gmall.to.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一处理所有异常给前端返回500的json
 * 当编写环绕通知或者异常通知时,一般都需要抛出去
 *      ①抛出去,别做处理
 *      ②在方法上声明异常,不做catch处理
 *
 * @author RyuJung
 * @date 2020-2-20 20:03
 */
@Slf4j
//@ControllerAdvice
//@ResponseBody
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ArithmeticException.class})
    public Object handlerException(Exception e) {
        log.error("系统全局异常感知,信息:{} ", e.getStackTrace());

        return new CommonResult().validateFailed("数学没学好");
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public Object handlerException02(Exception e) {
        log.error("系统全局异常感知,信息:{} ", e.getStackTrace());

        return new CommonResult().validateFailed("空指针啦!!");
    }
}
