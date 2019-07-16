package com.jisucloud.clawler.regagent.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@ComponentScan("com.jisucloud.clawler.regagent")
public class PersistenceContext {

	@Bean
	@ConditionalOnMissingBean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws Exception {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

	// @Bean
	// public RedisConnectionFactory connectionFactory() {
	// JedisPoolConfig poolConfig = new JedisPoolConfig();
	// poolConfig.setMaxTotal(maxActive);
	// poolConfig.setMaxIdle(maxIdle);
	// poolConfig.setMaxWaitMillis(maxWait);
	// poolConfig.setMinIdle(minIdle);
	// poolConfig.setTestOnBorrow(true);
	// poolConfig.setTestOnReturn(false);
	// poolConfig.setTestWhileIdle(true);
	// JedisClientConfiguration clientConfig =
	// JedisClientConfiguration.builder().usePooling().poolConfig(poolConfig)
	// .and().readTimeout(Duration.ofMillis(redisTimeout)).build();
	//
	// // 单点redis
	// RedisStandaloneConfiguration redisConfig = new
	// RedisStandaloneConfiguration();
	// // 哨兵redis
	// // RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration();
	// // 集群redis
	// // RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
	// redisConfig.setHostName(redisHost);
	// redisConfig.setPassword(RedisPassword.of(redisAuth));
	// redisConfig.setPort(redisPort);
	// redisConfig.setDatabase(redisDb);
	//
	// return new JedisConnectionFactory(redisConfig, clientConfig);
	// }

}
