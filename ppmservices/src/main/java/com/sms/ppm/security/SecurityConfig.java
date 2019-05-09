package com.sms.ppm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true
		)
@Profile(value = {"prod"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	

	@Autowired
	private JwtAutheticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.headers().frameOptions().sameOrigin() //To enable H2 Database
			.and()
			.authorizeRequests()
			.antMatchers(
				"/",
				"favicon.ico",
				"/**/*.png",
				"/**/*.png",
				"/**/*.png",
				"/**/*.png",
				"/**/*.png",
				"/**/*.png",
				"/**/*.png"
			).permitAll()
			.antMatchers("/api/users/**").permitAll()
			.anyRequest().authenticated();
			
			
	}
}


