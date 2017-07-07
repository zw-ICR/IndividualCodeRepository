package com.myproject.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WebRequestLogAspect {
	/**
	 * 定义切入点
	 */
	@Pointcut("@annotation(com.myproject.aop.LogAnnotation)")
	public void webRequestLog(){
		
	}

	
	/**
	 * 定义返回型通知
	 * 注意：@annotation(logAnnotation)用来获取注解里面的内容
	 * @param joinPoint
	 * @param logAnnontation
	 */
	@AfterReturning("webRequestLog() && @annotation(logAnnotation)")
	public void doAfterReturn(JoinPoint joinPoint,LogAnnotation logAnnotation){
		System.out.println("logAnnotation=" + logAnnotation.value());//获取注解里面的值
	}
	
	/**
	 * 定义异常型通知
	 * 注意：@annonation(logAnnonation)用来获取注解里面的内容
	 * @param joinPoint
	 * @param logAnnonation
	 * @param t
	 */
	@AfterThrowing(pointcut = "webRequestLog() && @annotation(logAnnotation)",throwing="t")
	public void doAfterThrow(JoinPoint joinPoint,LogAnnotation logAnnotation,Throwable t){
		System.out.println("logAnnotation=" + logAnnotation.value());
		t.printStackTrace();
	}

}
