package com.imooc.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
/**
 * @Aspect声明是一个注解
 * @Component将类加入到spring容器
 * 1. 声明一个切入类
 * 2. 声明一个切入点
 * 3. 声明一个
 * @author longxn
 *
 */
@Aspect
@Component
public class TimeAspect {
	/**	* com.imooc.web.controller.UserController.*(..)) 该类下的所有方法都起作用
	 	* com.imooc.web.controller.*.*(..)) controller包下所有类和方法
	 	* com.imooc.web.controller..*.*(..)) controller包和子包下所有类和方法
		除了around还有before, after
	 * @throws Throwable 
	*/
	@Around("execution(* com.imooc.web.controller.UserController.*(..))")
	public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable{
		System.out.println("time aspect start");
		Object[] args = pjp.getArgs();
		//controller的方法执行时传入的参数
		for (Object arg : args) {
			System.out.println("agr is " + arg);
		}
		/*proceed执行被切片后执行的方法, 相当于执行filter的过滤器链
		切片完成后会返回回来
		object为controller方法执行完毕后返回的值, 如getInfo方法返回的是User对象, query方法返回的是List<User>*/
		Object object = pjp.proceed();
		System.out.println(object);
		System.out.println("time aspect end");
		return object;
	}
}
