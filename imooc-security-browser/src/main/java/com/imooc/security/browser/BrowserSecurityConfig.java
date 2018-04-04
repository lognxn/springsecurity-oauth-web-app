package com.imooc.security.browser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.imooc.security.browser.authentication.ImoocAuthenticationFailureHandler;
import com.imooc.security.core.properties.SecurityProperties;

/**
 * 登录配置页, 将默认的登录修改成自定义的表单登录
 * @author longxn
 *
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter  {
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler imoocAuthenticationFailureHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.httpBasic()//登录方式:基本
		http.formLogin()//登录方式:表单
			.loginPage("/authentication/require")//指定登录页面
			.loginProcessingUrl("/authentication/form")//登录请求地址, 告诉UsernamePasswordAuthenticationFilter的登录请求地址, 该filter默认的登录地址是/login
			.successHandler(imoocAuthenticationSuccessHandler)
			.failureHandler(imoocAuthenticationFailureHandler)
			.and()
			.authorizeRequests()//进行授权
				.antMatchers("/authentication/require",securityProperties
						.getBrowser().getLoginPage(),"/code/image").permitAll() //放通登录页面, 否则不停的跳到登录页面会导致请求过多
//				.antMatchers(HttpMethod.GET, "").hasAuthority("write")
//				.antMatchers("").access("")
				.anyRequest()//任何请求
				.authenticated()//都需要认证
				.and()
			.csrf().disable();//暂时关闭csrf
		
	}
	
}
