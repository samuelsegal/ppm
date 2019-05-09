package com.sms.ppm.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sms.ppm.domain.PPMUser;
import com.sms.ppm.util.Messages;

@Component
public class UserValidator implements Validator {

	@Autowired 
	private Messages messages;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return PPMUser.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PPMUser pPMUser = (PPMUser) target;
		if (pPMUser.getPassword() == null) {
			errors.rejectValue("password", "error.password.null", messages.get("error.password.null"));
		} else if (pPMUser.getPassword().length() < 4) {
			errors.rejectValue("password", "error.password.length", "Password must be at least 4 charaters");
		}
		if (pPMUser.getConfirmPassword() == null) {
			errors.rejectValue("confirmPassword", "error.confirm.password.null", messages.get("error.confirm.password.null"));
		} else if (!pPMUser.getConfirmPassword().equals(pPMUser.getPassword())) {
			errors.rejectValue("confirmPassword", "error.password.match",
					"Passwword does not match confirmed password");
		}
	}

}
