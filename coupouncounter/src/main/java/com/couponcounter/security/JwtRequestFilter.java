package com.couponcounter.security;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.couponcounter.service.SecurityService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	CustomUserDetailService customUserService;

	@Autowired
	SecurityService securityService;

	@Value("${authentication-test.auth.accessTokenCookieName}")
	private String accessTokenCookieName;

	@Value("${authentication-test.auth.refreshTokenCookieName}")
	private String refreshTokenCookieName;

	public static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();
	public static final ThreadLocal<String> CLIENT_TYPE = new ThreadLocal<>();

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		REQUEST_ID.set("" + UUID.randomUUID());
		CLIENT_TYPE.set(securityService.getClient(httpServletRequest));        
		
		try {
			String jwt = securityService.getJwtToken(httpServletRequest);			
			jwtProvider.validateToken(jwt);
			String username = jwtProvider.extractUserName(jwt);
			securityService.isUserAuthenticated(username);
			UserDetails userDetails = customUserService.loadUserByUsername(username);
			if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

		} catch (ExpiredJwtException ex) {
			ex.getLocalizedMessage();
		} catch (Exception ex) {
			ex.getLocalizedMessage();
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

}
