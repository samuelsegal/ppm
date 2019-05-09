package com.sms.ppm.services;

import com.sms.ppm.domain.User;

public interface UserService {
	public User saveUser(User user);
	Iterable<User> getAllUsers();
}
