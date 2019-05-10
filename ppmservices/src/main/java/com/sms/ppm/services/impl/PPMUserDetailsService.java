package com.sms.ppm.services.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sms.ppm.domain.PPMUser;
import com.sms.ppm.repositories.PPMUserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PPMUserDetailsService implements UserDetailsService {

	@Autowired
	private PPMUserRepository ppmUserRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("Loading by username: {}", username);
		PPMUser user = ppmUserRepository.findByUsername(username);
		log.debug("FOund user: {}", user);
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		return user;
	}
	
	@Transactional
	public PPMUser loadUserById(Long id) throws UsernameNotFoundException{
		log.debug("Loading user by id: {}", id);
		PPMUser user = ppmUserRepository.getById(id);
		log.debug("Found user: {}", user);
		if(user == null) {
			throw new UsernameNotFoundException("FIX ME need user id exception");
		}
		return user;
	}

}
