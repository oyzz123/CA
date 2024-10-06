package com.campus.aspect;

import com.campus.annotation.AutoFill;
import com.constant.AutoFillConstant;
import com.context.BaseContext;
import com.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面:实现公共字段自动填充处理逻辑
 */
//@Aspect
//@Component
@Slf4j
public class AutoFillAspect {

    /**
     * 切入点
     */
    @Pointcut("execution(* com.campus.mapper.*.*(..)) && @annotation(com.campus.annotation.AutoFill)")
    public void autoFillPointCut(){}

    @Before("autoFillPointCut()")
    public void save1(JoinPoint joinPoint) {
        log.info("开始进行公共字段自动填充...");

        //获取到当前被拦截的方法的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();

        //获取当前被拦截方法的参数--实体对象
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length ==0) {
            return;
        }
        Object entiy = args[0];
        //准备赋值数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        //根据当前不同的操作类型进行不同操作(反射)
        if(operationType == OperationType.INSERT) {
            try {
                Method setCreateTime = entiy.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME,LocalDateTime.class);
                Method setCreateUser = entiy.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Integer.class);
                Method setUpdateTime = entiy.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entiy.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Integer.class);

                //通过反射为对象属性赋值
                setCreateTime.invoke(entiy,now);
                setCreateUser.invoke(entiy,currentId);
                setUpdateTime.invoke(entiy,now);
                setUpdateUser.invoke(entiy,currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (operationType == OperationType.UPDATE) {
            //为两个公共字段赋值
            try {
                Method setUpdateTime = entiy.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entiy.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Integer.class);

                //通过反射为对象属性赋值
                setUpdateTime.invoke(entiy,now);
                setUpdateUser.invoke(entiy,currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
