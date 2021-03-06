package com.imooc.security.browser;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.support.SimpleResponse;
/**
 * 处理需要登录认证请求的类
 * @author longxn
 *
 */
@RestController
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)//返回状态码
public class BrowserSecurityController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Autowired
	private SecurityProperties securityProperties;
	
	/**
	 * 当需要登录时, 跳转到这里
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/authentication/require")
	public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if(savedRequest != null){
			//引发跳转请求的url
			String targetUrl = savedRequest.getRedirectUrl();
			logger.info("引发跳转的请求是:{}",targetUrl);
			if(StringUtils.endsWithIgnoreCase(targetUrl, ".html")){
				//通过securityProperties获取子项目下设置的登录页面
				redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());//设置跳转到的登录页
			}
		}
		return new SimpleResponse("访问的服务需要身份认证, 请引导用户到登录页");
		
	}
	
}
