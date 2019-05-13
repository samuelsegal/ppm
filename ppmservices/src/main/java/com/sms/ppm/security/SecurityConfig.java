package com.sms.ppm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sms.ppm.services.impl.PPMUserDetailsService;
import com.sms.ppm.util.PPMSecurityConstants;

/**
 * @author samuelsegal Security configuration. Configure AuthemticationManager
 *         to use PPMUserDetailsService and BCryptPasswordEncoder. Configure URL
 *         based security and other security configurations such as CORS, CSRF,
 *         session management(Stateless for RESTFull API services).
 *         Add JwtAuthenticationFilter to filter chain
 *         Enable method level security as well.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true
		)
@Profile(value = {"prod", "test"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	

	@Autowired
	private JwtAutheticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private PPMUserDetailsService ppmUserDetailsService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//configure authentication manager to use ppmUserDetailService and bcrypt encoder
		auth.userDetailsService(ppmUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}


	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		//Overriding to add @Bean(BeanIds.AUTHENTICATION_MANAGER) to assure our authentication is available to context
		return super.authenticationManager();
	}


	/**
	 * @return
	 * Provide app context with JwtAuthenticationFilter
	 */
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

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
			.antMatchers(PPMSecurityConstants.WEB_RESOURCES_URL).permitAll()
			.antMatchers(PPMSecurityConstants.SIGN_UP_URL).permitAll()
			.anyRequest().authenticated();		
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		super.configure(http);
	}
}


