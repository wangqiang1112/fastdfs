package com.aspect;

import com.alibaba.fastjson.JSON;

import com.annotation.SysLog;
import com.entiy.SysLogEntity;
import com.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    // 定义Pointcut，Pointcut的名称 就是simplePointcut，此方法不能有返回值，该方法只是一个标示
    // @annotation 指定自定义注解
    @Pointcut("@annotation(com.annotation.SysLog)")
    public void logPointCut() {

    }

    @Before("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLogEntity sysLog = new SysLogEntity();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if(syslog != null){
            //注解上的描述
            sysLog.setOperation(syslog.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();

        //保存系统日志
        sysLogService.save(sysLog);
    }

   /*@AfterReturning(returning="rvt", pointcut="@annotation(com.cf.utils.annotation.SysLog)")
   public Object AfterExec(JoinPoint joinPoint,Object rvt){

   }*/
}
