package com.sms.ppm.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

/**
 * @author samuelsegal
 * Class to assist in getting localized message, need to test that internationalization works
 */
@Component
public class Messages {

	@Autowired
	private MessageSource messageSource;
	
	private MessageSourceAccessor messageSourceAccessor;
	
	@PostConstruct
	private void init() {
		messageSourceAccessor = new MessageSourceAccessor(messageSource);
		
	}
	
	public String get(String property) {
		return messageSourceAccessor.getMessage(property);
	}
}
