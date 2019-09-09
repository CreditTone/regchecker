package com.jisucloud.clawler.regagent.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

@Configuration
@ComponentScan("com.jisucloud.clawler.regagent")
public class PersistenceContext {
	
	public static String MONGODB_SERVICE = "mongodb://root:jisucloud123!@dds-m5e5cc5952acde541218-pub.mongodb.rds.aliyuncs.com:3717,dds-m5e5cc5952acde542128-pub.mongodb.rds.aliyuncs.com:3717/admin?replicaSet=mgset-13005959";
	public static String MONGODB_DATABASE = "deep007";
	
	private MongoClient mongoClient;

	@Bean
	@ConditionalOnMissingBean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws Exception {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}
	
	
	@Bean
	public MongoDatabase mongoDb() {
		if (mongoClient == null) {
			MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
			// build the connection options
			builder.maxConnectionIdleTime(150000);
			builder.maxConnectionLifeTime(150000);
			builder.connectTimeout(3000);
			builder.socketTimeout(6000);
			builder.serverSelectionTimeout(3000);
			MongoClientURI mongoClientURI = new MongoClientURI(MONGODB_SERVICE, builder);
			mongoClient = new MongoClient(mongoClientURI);
		}
		return mongoClient.getDatabase(MONGODB_DATABASE);
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
