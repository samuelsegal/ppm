package com.sms.ppm.services.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sms.ppm.domain.PPMUser;
import com.sms.ppm.repositories.PPMUserRepository;

@Service
public class PPMUserDetailsService implements UserDetailsService {

	@Autowired
	private PPMUserRepository ppmUserRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		PPMUser user = ppmUserRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		return user;
	}
	
	@Transactional
	public UserDetails loadUserById(Long id) throws UsernameNotFoundException{
		PPMUser user = ppmUserRepository.getById(id);
		
		if(user == null) {
			throw new UsernameNotFoundException("FIX ME need user id exception");
		}
		return user;
	}

}
