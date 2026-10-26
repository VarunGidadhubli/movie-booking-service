package com.movie.booking.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.lettuce.Bucket4jLettuce;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;

@Configuration
public class RateLimitConfig {

	/*
	 * Tells Spring to call the object's shutdown() method when the application shuts down, so resources can be cleaned up. Used for redis client
	 * The type of object this method will return. RedisClient is provided by the Lettuce library. bucket4jRedisClient() — the method that creates our Redis client.
	 */
	@Bean(destroyMethod = "shutdown")
	RedisClient bucket4jRedisClient() {

		return RedisClient.create("redis://localhost:6379");//creates a Lettuce Redis client configured to connect to Redis.
	}

	@Bean(destroyMethod = "close")//tells Spring to close the Redis connection when the application shuts down, releasing its resources.
	//The Lettuce type representing an active Redis connection. specifies the data types: for Redis keys and redis values whch holds Bucket4j's serialized bucket state
	StatefulRedisConnection<String, byte[]> bucket4jRedisConnection(RedisClient bucket4jRedisClient) {
		return bucket4jRedisClient.connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE));
	}

	@Bean
	ProxyManager<String> rateLimitProxyManager(StatefulRedisConnection<String, byte[]> bucket4jRedisConnection) {
		
		return Bucket4jLettuce.casBasedBuilder(bucket4jRedisConnection)
				.expirationAfterWrite(ExpirationAfterWriteStrategy
						.basedOnTimeForRefillingBucketUpToMax(Duration.ofMinutes(2)))
				.build();
	}
}