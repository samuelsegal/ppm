package com.sms.ppm.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.ppm.domain.PPMUser;
import com.sms.ppm.services.MapValidationErrorService;
import com.sms.ppm.services.UserService;
import com.sms.ppm.validator.UserValidator;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private MapValidationErrorService mapValidationService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserValidator userValidator;
	
	@GetMapping("")
	Iterable<PPMUser> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody PPMUser pPMUser, BindingResult result){
		userValidator.validate(pPMUser, result);
		ResponseEntity<?> errorMap = mapValidationService.mapValidationService(result);
		if(errorMap != null) {
			return errorMap;
		}
		PPMUser newUser = userService.saveUser(pPMUser);
		return new ResponseEntity<PPMUser>(newUser, HttpStatus.CREATED);
	}
}
