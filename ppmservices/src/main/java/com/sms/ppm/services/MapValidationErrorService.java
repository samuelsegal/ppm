package com.sms.ppm.services;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface MapValidationErrorService {
    ResponseEntity<?> mapValidationService(BindingResult result);
}
