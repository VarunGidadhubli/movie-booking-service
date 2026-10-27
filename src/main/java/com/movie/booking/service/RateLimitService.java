package com.movie.booking.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.BucketProxy;
import io.github.bucket4j.distributed.proxy.ProxyManager;

@Service
public class RateLimitService {

	private final ProxyManager<String> proxyManager;

	private BucketConfiguration createBucketConfiguration(long capacity, Duration refillDuration) {

		return BucketConfiguration.builder()
				.addLimit(limit -> limit.capacity(capacity).refillGreedy(capacity, refillDuration)).build();
	}

	public RateLimitService(ProxyManager<String> proxyManager) {
		this.proxyManager = proxyManager;
	}

	public BucketProxy resolveBucket(String key, long capacity, Duration refillDuration) {
		BucketConfiguration bucketConfiguration = createBucketConfiguration(capacity, refillDuration);
		return proxyManager.getProxy(key, () -> bucketConfiguration);
	}

	public boolean allowRequest(String key, long capacity, Duration refillDuration) {

		BucketProxy bucketProxy = resolveBucket(key, capacity, refillDuration);
		return bucketProxy.tryConsume(1);
	}
}