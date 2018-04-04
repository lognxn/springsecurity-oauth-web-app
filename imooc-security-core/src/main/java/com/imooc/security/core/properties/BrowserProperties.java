package com.imooc.security.core.properties;
/**
 * 读取application.properties配置文件中的imooc.security.browser.loginPage属性
 * @author longxn
 *
 */
public class BrowserProperties {
	//默认登录页
	private String loginPage = "/imooc-signIn.html";
	//默认返回方式
	private LoginType loginType = LoginType.JSON;
	
	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	
	
	
}
