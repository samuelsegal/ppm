package com.sms.ppm.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sms.ppm.exceptions.InvalidLoginResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAutheticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		log.error("Invalid Login Reponse"+request.getRemoteUser());
		InvalidLoginResponse invalidLoginResponse = new InvalidLoginResponse();
		String jsonReponse = new Gson().toJson(invalidLoginResponse);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(401);
		response.getWriter().print(jsonReponse);
	}

}
