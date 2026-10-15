package com.movie.booking.config;

import java.util.Collections;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.movie.booking.entity.User;
import com.movie.booking.repository.UserRepository;

@Component
public class DatabaseRoleJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

	private final UserRepository userRepository;

	public DatabaseRoleJwtAuthenticationConverter(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public AbstractAuthenticationToken convert(Jwt jwt) {

		Long userId = Long.valueOf(jwt.getSubject());
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalStateException("JWT user does not exist"));

		String roleName = user.getRole().getName().toUpperCase();

		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roleName);

		return new JwtAuthenticationToken(jwt, Collections.singletonList(authority), jwt.getSubject());
	}
}