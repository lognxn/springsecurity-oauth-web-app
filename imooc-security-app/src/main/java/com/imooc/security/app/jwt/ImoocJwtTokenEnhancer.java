package com.imooc.security.app.jwt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * jwt扩展类, 实现对jwt进行扩展
 * @author longxn
 */
public class ImoocJwtTokenEnhancer implements TokenEnhancer {

	/** 配置jwt的扩展信息 */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<>();
		info.put("company", "imooc");
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);// 添加扩展信息
		return accessToken;
	}

	
}
