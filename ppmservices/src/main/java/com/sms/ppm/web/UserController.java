package com.sms.ppm.web;

import static com.sms.ppm.util.PPMSecurityConstants.TOKEN_PREFIX;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.ppm.domain.PPMUser;
import com.sms.ppm.security.JWTTokenProvider;
import com.sms.ppm.security.payload.JWTLoginSuccessResponse;
import com.sms.ppm.security.payload.LoginRequest;
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
	@Autowired
	private JWTTokenProvider jwtTokenProvider;
	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("")
	Iterable<PPMUser> getAllUsers() {
		return userService.getAllUsers();
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationService.mapValidationService(result);
		if (errorMap != null) {
			return errorMap;
		}
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(), 
						loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
		
		return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
		
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody PPMUser ppmUser, BindingResult result) {
		userValidator.validate(ppmUser, result);
		ResponseEntity<?> errorMap = mapValidationService.mapValidationService(result);
		if (errorMap != null) {
			return errorMap;
		}
		PPMUser newUser = userService.saveUser(ppmUser);
		return new ResponseEntity<PPMUser>(newUser, HttpStatus.CREATED);
	}
}
