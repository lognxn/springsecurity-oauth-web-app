package com.imooc.web.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
/**
 * filter是javaEE规范, 拦截所有的请求, Interceptor是spring的规范, 拦截的是访问controller的请求
 * 要interceptor起作用需要两步
 * 1.在类上加@Compenet注解
 * 2.在WebConfig中配置
 * @author longxn
 *
 */
@Component
public class TimeInterceptor implements HandlerInterceptor {

	/**
	 * 在controller方法之前执行
	 * @param handler 被拦截的controller执行的方法
	 * @return false:不再执行controller的方法
	 * 		   true:接下来执行controller的方法
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle");
		System.out.println(((HandlerMethod)handler).getBean().getClass().getName());
		System.out.println(((HandlerMethod)handler).getMethod());
		request.setAttribute("startTime", new Date().getTime());
		return true;
	}

	/** 执行controller方法成功返回后执行, 如果controller抛出异常, 那么不执行该方法 
	 @param handler 被拦截的controller
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("postHandle");
		Long  start = (Long)request.getAttribute("startTime");
		System.out.println("time interceptor耗时:"+(new Date().getTime() - start));
	}

	/** 不管拦截成功还是失败都会执行
	 * @param handler 被拦截的controller
	 * @param ex controller抛出异常后的异常信息, 如果不抛出异常则为null. 如果controller抛出的异常被ControllerExceptionHandler匹配了的话, ex也为null.
	 *  */
	@Override
	
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("afterCompletion");
		Long  start = (Long)request.getAttribute("startTime");
		System.out.println("time interceptor耗时:"+(new Date().getTime() - start));
		System.out.println("ex is" + ex);
	}

}
