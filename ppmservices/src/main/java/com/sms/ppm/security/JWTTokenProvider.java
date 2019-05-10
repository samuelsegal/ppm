package com.sms.ppm.security;

import static com.sms.ppm.util.PPMSecurityConstants.SECRET_KEY;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.sms.ppm.domain.PPMUser;
import com.sms.ppm.util.PPMSecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author samuelsegal
 * JWT Token provider. Used to generate and validate tokens.
 */
@Component
@Slf4j
public class JWTTokenProvider {

	public String generateToken(Authentication authentication) {
		PPMUser user = (PPMUser) authentication.getPrincipal();
		Date now = new Date(System.currentTimeMillis());
		Date expireDate = new Date(now.getTime() + PPMSecurityConstants.EXPIRATION_TIME);
		
		String userid = String.valueOf(user.getId());
		
		Map<String, Object> claims = new HashMap<>();
		
		claims.put("id", String.valueOf(user.getId()));
		claims.put("username", user.getUsername());
		claims.put("fullname", user.getFullname());
		
		return Jwts.builder()
				.setSubject(userid)
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, PPMSecurityConstants.SECRET_KEY)
				.compact();
	}
	
	/**
	 * Validate the token
	 * @param token
	 * @return
	 */
	public boolean validateToken(String token) {

			try {
				Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
					| IllegalArgumentException e) {
				log.error("Error validating token: {}", e.getMessage());
			}

		return false;
	}
	
	/**
	 * Get user id from the claim included in JWT
	 * @param token
	 * @return
	 */
	public Long getUserIdFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
		return Long.valueOf(String.valueOf(claims.get("id")));
	}
}
