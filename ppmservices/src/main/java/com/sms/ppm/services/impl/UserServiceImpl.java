package com.sms.ppm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sms.ppm.domain.PPMUser;
import com.sms.ppm.exceptions.UserNameExistsException;
import com.sms.ppm.repositories.UserRepository;
import com.sms.ppm.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public PPMUser saveUser(PPMUser pPMUser) {
		log.info("Creating new user: " + pPMUser);
		pPMUser.setPassword(passwordEncoder.encode(pPMUser.getPassword()));
		pPMUser.setConfirmPassword("");
		try{
			return userRepository.save(pPMUser);
		}catch (Exception e) {
			throw new UserNameExistsException("Username '" + pPMUser.getUsername() + "' already exists");
		}
	}

	@Override
	public Iterable<PPMUser> getAllUsers() {
		return userRepository.findAll();
	}

}
