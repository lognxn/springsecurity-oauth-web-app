package com.imooc.web.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.imooc.web.filter.TimeFilter;
import com.imooc.web.interceptor.TimeInterceptor;

/**
 * web的配置类, 相当于web.xml, 可以在改类实现filter配置
 * 需要添加@Configuration声明该类是一个配置类, 用于spring进行识别
 * 如果要添加interceptor, 需要继承WebMvcConfigurerAdapter, 且重写addInterceptors方法来添加我们定义的interceptor
 * @author longxn
 *
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
	
	@Autowired
	private TimeInterceptor timeInterceptor;
	
	/**
	 * 配置异步线程的支持
	 * 当需要拦截异步请求的时候, 同步的拦截器是拦截不到的, 需要在该方法中添加拦截器
	 */
//	@Override
//	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//		configurer.
//	};
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(timeInterceptor);
	};
	
	@Bean
	public FilterRegistrationBean timeFilter(){
		FilterRegistrationBean registrationBean =  new FilterRegistrationBean();
		TimeFilter timeFilter = new TimeFilter();
		registrationBean.setFilter(timeFilter);
		List<String> urls = new ArrayList<>();
		//添加url,只要匹配上该url的都会执行该filter
		urls.add("/*");
		registrationBean.setUrlPatterns(urls);
		return registrationBean;
	}
}
