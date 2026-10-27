package com.movie.booking.filter;

import java.io.IOException;
import java.time.Duration;

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

		String requestPath = request.getServletPath();
		String httpMethod = request.getMethod();
		String clientKey = request.getRemoteAddr();

		boolean allowed = true;

		if (httpMethod.equals("POST") && requestPath.equals("/api/auth/login")) {

			allowed = rateLimitService.allowRequest("login:" + clientKey, 5, Duration.ofMinutes(1));

		} else if (httpMethod.equals("POST") && requestPath.matches("/api/bookings/[^/]+")) {

			allowed = rateLimitService.allowRequest("booking:" + clientKey, 10, Duration.ofMinutes(1));
		}

		if (!allowed) {
			response.setStatus(429);
			response.getWriter().write("Too many requests. Please try again later.");
			return;
		}

		filterChain.doFilter(request, response);
	}

}
