package com.sms.ppm.security;

import static com.sms.ppm.util.PPMSecurityConstants.HEADER_TOKEN;
import static com.sms.ppm.util.PPMSecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sms.ppm.domain.PPMUser;
import com.sms.ppm.services.impl.PPMUserDetailsServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @author samuelsegal Filter to handle tokens for security.
 *         OncePerRequestFilter is extended to assist in Stateless RESTFull API
 *         security.
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTTokenProvider provider;

	@Autowired
	private PPMUserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String jwt = parseTokenFromRequest(request);
			if (jwt != null) {
				log.debug("Bearer token provided: {}", jwt);
				// Get user from token
				Long userid = provider.getUserIdFromToken(jwt);

				// Get user with details from UserDetails Service
				PPMUser userDetails = userDetailsService.loadUserById(userid);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, Collections.emptyList());

				// Set details for the username pw authentication
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// Set the authentication for context of this request
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}else {
				log.debug("No bearer token provided");
			}
		} catch (Exception e) {
			log.error("Error in setting Authetication: {}", e.getMessage(), e);
		}
		filterChain.doFilter(request, response);
	}

	private String parseTokenFromRequest(HttpServletRequest request) {
		String token = request.getHeader(HEADER_TOKEN);
		if (token != null && token.startsWith(TOKEN_PREFIX)) {
			return token.substring(7, token.length());
		}
		return null;

	}

}
