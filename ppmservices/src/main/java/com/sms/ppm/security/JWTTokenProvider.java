package com.sms.ppm.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.sms.ppm.domain.PPMUser;
import com.sms.ppm.util.PPMSecurityConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
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
}
