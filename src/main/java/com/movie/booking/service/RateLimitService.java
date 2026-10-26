package com.movie.booking.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.BucketProxy;
import io.github.bucket4j.distributed.proxy.ProxyManager;

@Service
public class RateLimitService {

	private final ProxyManager<String> proxyManager;
	
	private BucketConfiguration createBucketConfiguration() {

		return BucketConfiguration.builder()
				.addLimit(limit -> limit.capacity(10).
						refillGreedy(10, Duration.ofMinutes(1)))
				.build();
	}
	
	public RateLimitService(ProxyManager<String> proxyManager) {
		this.proxyManager = proxyManager;
	}
	
	public BucketProxy resolveBucket(String key) {
		return proxyManager.builder()
				.build(key, this::createBucketConfiguration);
	}
	
	public Boolean allowRequest(String key) {
		BucketProxy bucketProxy = resolveBucket(key);
		return bucketProxy.tryConsume(1);
	}
}