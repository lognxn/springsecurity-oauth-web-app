package com.imooc.security.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.imooc.security.core.properties.OAuth2ClientProperties;
import com.imooc.security.core.properties.SecurityProperties;

/**
 * 通过注解@EnableAuthorizationServer开启了一个授权服务器, demo项目是继承了这个项目, demo项目就是一个授权服务器了
 * 如果继承AuthorizationServerConfigurerAdapter类后, 需要手动写token的代码
 * 
 * @author longxn
 */
@Configuration
@EnableAuthorizationServer
public class ImoocAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private TokenStore tokenStore;
	@Autowired(required = false)
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	@Autowired(required = false)
	private TokenEnhancer jwtTokenEnhancer;
	/**
	 * 继承AuthorizationServerConfigurerAdapter后需要手动配置
	 * 指定tokenStore实现将session保存到redis中
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore)
				.authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService);
		//配置jwt, 通过enhancerChain来扩展
		if(jwtAccessTokenConverter != null && jwtTokenEnhancer != null){
			TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
			List<TokenEnhancer> enhancers = new ArrayList<>();
			enhancers.add(jwtTokenEnhancer);//扩展增强信息
			enhancers.add(jwtAccessTokenConverter);//jwt加密和签名
			enhancerChain.setTokenEnhancers(enhancers);
			endpoints.tokenEnhancer(enhancerChain)
				.accessTokenConverter(jwtAccessTokenConverter);
		}
	}

	/**
	 * 指定向哪些应用去发放令牌
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		/** 配置方式一: 直接配置方式 */
		// 	clients.inMemory().withClient("imooc")//这里指定后, demo项目那就不用配置clientId了
		// .secret("imoocsecret")
		// .accessTokenValiditySeconds(7_200)
		// .authorizedGrantTypes("refresh_token","password")//指定支持token的类型,
		// 还有授权码的(authorization_code), 和简化模式(implicit)
		// .scopes("all","read","write")//支持的scope,client上传的scope必须是这里的其中之一,客户端可以不带scope上来,
		// 那么scope就是这里配置的scope
		// .and()
		// .withClient("XXX")
		// .secret("YYY");//没配置scopes时客户端必须指定scope访问

		/** 配置方式二: 通过配置文件方式 */
		InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
		OAuth2ClientProperties[] oAuth2Clients = securityProperties.getOauth2().getClients();
		if (ArrayUtils.isNotEmpty(oAuth2Clients)) {
			for (OAuth2ClientProperties config : oAuth2Clients) {
				builder.withClient(config.getClientId()).secret(config.getClientSecret())
						.accessTokenValiditySeconds(config.getAccessTokenValiditySeconds())
						.refreshTokenValiditySeconds(securityProperties.getOauth2().getRefreshTokenValiditySeconds())
						.authorizedGrantTypes("refresh_token", "password").scopes("all", "read", "write");
//						.authorizedGrantTypes("refresh_token").scopes("all", "read", "write");
			}
		}
	}
}
