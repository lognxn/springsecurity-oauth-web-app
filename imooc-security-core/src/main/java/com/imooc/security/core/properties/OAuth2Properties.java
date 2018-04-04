package com.imooc.security.core.properties;

/**
 * token中client管理类
 * 
 * @author longxn
 */
public class OAuth2Properties {
	
	private String jwtSigningKey = "imooc";
	
	private int refreshTokenValiditySeconds = 2592000;
	
	public int getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}

	public void setRefreshTokenValiditySeconds(int refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}

	private OAuth2ClientProperties[] clients = {};

	public String getJwtSigningKey() {
		return jwtSigningKey;
	}

	public void setJwtSigningKey(String jwtSigningKey) {
		this.jwtSigningKey = jwtSigningKey;
	}

	public OAuth2ClientProperties[] getClients() {
		return clients;
	}

	public void setClients(OAuth2ClientProperties[] clients) {
		this.clients = clients;
	}
}
