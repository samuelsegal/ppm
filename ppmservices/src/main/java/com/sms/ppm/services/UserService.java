package com.sms.ppm.services;

import com.sms.ppm.domain.PPMUser;

public interface UserService {
	public PPMUser saveUser(PPMUser pPMUser);
	Iterable<PPMUser> getAllUsers();
	PPMUser getUserByUsername(String username);
}
