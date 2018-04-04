package com.imooc.security.core.properties;

/**
 * 单个client参数封装类
 * 
 * @author longxn
 *
 */
public class OAuth2ClientProperties {
	private String clientId;
	private String clientSecret;
	//需要设置过期时间, 否则默认是0, 0表示不过期
	private Integer accessTokenValiditySeconds = 7200;//过期时间

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public Integer getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}

	public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

}
