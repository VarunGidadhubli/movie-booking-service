package com.movie.booking.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.movie.booking.service.RateLimitService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

	private final RateLimitService rateLimitService;

	public RateLimitFilter(RateLimitService rateLimitService) {
		this.rateLimitService = rateLimitService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String clientKey = request.getRemoteAddr();
		boolean allowed = rateLimitService.allowRequest(clientKey);

		if (!allowed) {
			response.setStatus(429);
			response.getWriter().write("Too many requests. Please try again later.");
			return;
		}
	}

}
