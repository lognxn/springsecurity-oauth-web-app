package com.imooc.security.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.imooc.security.app.jwt.ImoocJwtTokenEnhancer;
import com.imooc.security.core.properties.SecurityProperties;

/** 将token存入到redis中 */
@Configuration
public class TokenStoreConfig {

	/** bean由core项目的redis config产生 */
	@Autowired
	private RedisConnectionFactory connectionFactory;

	/**
	 * ConditionalOnProperty的意思是配置文件中有"imooc.security.oauth2.XXX.storeType=redis"时生效
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(prefix = "imooc.security.oauth2", name = "storeType", havingValue = "redis")
	public TokenStore redisTokenStore() {
		return new RedisTokenStore(connectionFactory);
	}

	/**
	 * JWT相关的类, 配置后就可以使用jwt令牌 和上面spring有两个TokenStore的实例,
	 * ConditionalOnProperty的意思是配置文件中有"imooc.security.oauth2.XXX.storeType=jwt"的时候或者没配置的时候这个TokenStore生效
	 */
	@Configuration
	@ConditionalOnProperty(prefix = "imooc.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
	public static class JwtTokenConfig {
		@Autowired
		private SecurityProperties securityProperties;

		@Bean
		public TokenStore jwtTokenStore() {
			return new JwtTokenStore(jwtAccessTokenConverter());
		}

		@Bean
		public JwtAccessTokenConverter jwtAccessTokenConverter() {
			JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
			// 设置安全密钥
			accessTokenConverter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
			return accessTokenConverter;
		}

		/**
		 * 自定义jwt扩展 可以给该注解传入参数例如@ConditionOnMissingBean(name =
		 * "example")，这个表示如果name为“example”的bean存在，这该注解修饰的代码块不执行。
		 */
		@Bean
		@ConditionalOnMissingBean(name = "jwtTokenEnhancer")
		public TokenEnhancer jwtTokenEnhancer() {
			return new ImoocJwtTokenEnhancer();
		}
	}

}
