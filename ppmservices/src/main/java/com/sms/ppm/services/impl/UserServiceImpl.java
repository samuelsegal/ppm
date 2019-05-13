package com.sms.ppm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sms.ppm.domain.PPMUser;
import com.sms.ppm.exceptions.UserNameExistsException;
import com.sms.ppm.repositories.PPMUserRepository;
import com.sms.ppm.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private PPMUserRepository pPMUserRepository;
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(PPMUserRepository pPMUserRepository, BCryptPasswordEncoder passwordEncoder) {
		super();
		this.pPMUserRepository = pPMUserRepository;
		this.passwordEncoder = passwordEncoder;
	}	
	
	@Override
	public PPMUser saveUser(PPMUser pPMUser) {
		log.info("Creating new user: " + pPMUser);
		pPMUser.setPassword(passwordEncoder.encode(pPMUser.getPassword()));
		pPMUser.setConfirmPassword("");
		try{
			return pPMUserRepository.save(pPMUser);
		}catch (Exception e) {
			throw new UserNameExistsException("Username '" + pPMUser.getUsername() + "' already exists");
		}
	}


	@Override
	public Iterable<PPMUser> getAllUsers() {
		return pPMUserRepository.findAll();
	}

	@Override
	public PPMUser getUserByUsername(String username) {
		return pPMUserRepository.findByUsername(username);
	}

	
}
