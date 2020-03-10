package com.atguigu.gmall.admin.aop;

import com.atguigu.gmall.to.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * 切面编写
 * 1.导入切面场景
 * 2.编写切面
 * ①@Aspect,@Component注解
 * ①切入点表达式
 * ②通知类型(前置,返回,后置,异常,环绕)
 * 正常执行: 前置通知-> 返回通知-> 后置通知
 * 异常执行: 前置通知-> 异常通知
 * <p>
 * 环绕通知:四合一...
 * 这里利用aop完成统一的数据校验,数据校验的结果返回给前端
 */
@Slf4j
@Aspect
@Component
public class DataValidAspect {

    @Around("execution(* com.atguigu.gmall.admin..*Controller.*(..))")
    public Object validAround(ProceedingJoinPoint joinPoint) {
        Object proceed;
        log.debug("校验切面进行校验工作...."+ joinPoint);
        try {
            //前置
            Object[] args = joinPoint.getArgs();
            for (Object arg: args) {
                if (arg instanceof BindingResult) {
                    BindingResult result = (BindingResult) arg;

                    int errorCount = result.getErrorCount();

                    if (errorCount > 0) {
                        List<ObjectError> allErrors = result.getAllErrors();
                        log.debug("检测到参数有无效值:{}, 即将返回",allErrors);
                        //检测到有错误
                        /*List<FieldError> fieldErrors = result.getFieldErrors();
                        fieldErrors.forEach((fieldError) -> {
                            String field = fieldError.getField();
                            log.debug("属性{},传来的值{},校验时出错,出错的消息为{}"
                                    , field, fieldError.getRejectedValue(), fieldError.getDefaultMessage());*/
                        return new CommonResult().validateFailed(result);
                        }
                    }
                }

            proceed = joinPoint.proceed(joinPoint.getArgs());
            //返回
            log.debug("校验成功,以将参数放行执行方法");

        } catch (Throwable throwable) {
            //异常发生时的处理, 这里直接抛出去, 给GlobalExceptionHandler进行统一异常处理
            throw new RuntimeException(throwable);
        } finally {
            //后置
        }
        return proceed;
    }
}

