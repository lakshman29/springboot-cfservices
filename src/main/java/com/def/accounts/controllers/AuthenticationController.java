package com.def.accounts.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.def.accounts.domain.AuthenticationRequest;
import com.def.accounts.services.AccountService;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

import java.util.Map;
import java.util.Optional;


//import org.springframework.security.core.context.SecurityContextHolder;

/**
 * REST controller for the accounts microservice.
 * Provides the following endpoints:
 * <p><ul>
 * <li>POST <code>/login</code> login request.
 * <li>GET <code>/logout/{userId}</code> logs out the account with given user id.
 * </ul><p>
 * @author David Ferreira Pinto
 *
 */
@RestController
public class AuthenticationController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	/**
	 * the service to delegate to.
	 */
	@Autowired
	private AccountService service;
	
	/**
	 * Logins in the user from the authentication request passed in body.
	 * 
	 * @param authenticationRequest The request with username and password.
	 * @return HTTP status CREATED if successful.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseStatus( HttpStatus.CREATED )
	@ResponseBody
	public Map<String, Object> login(@RequestBody AuthenticationRequest authenticationRequest) {
		logger.debug("AuthenticationController.login: login request for username: " + authenticationRequest.getUsername());
		Map<String, Object> authenticationResponse = this.service.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		return authenticationResponse;// authToken and accountId;
	}

	/**
	 * Logs out the user.
	 * 
	 * @param userId The user id to log out.
	 */
	@RequestMapping(value = "/logout/{user}", method = RequestMethod.GET)
	@ResponseStatus( HttpStatus.OK )
	@ResponseBody
	public void logout(@PathVariable("user") final String userId) {
		logger.debug("AuthenticationController.logout: logout request for userid: " + userId);
		this.service.logout(userId);
	}
	
	/**
	 * To ensure no one does login through HTTP GET.
	 * returns METHOD_NOT_ALLOWED.
	 *//*
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseStatus( HttpStatus.METHOD_NOT_ALLOWED )
	public void get() {
		
	}*/
	
	
	  /*@RequestMapping(value = "/login", method = RequestMethod.GET)
	    public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
		  logger.debug("Getting login page, error={}", error);
	        return new ModelAndView("login", "error", error);
	    }*/
	
	
	
	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	@ResponseStatus( HttpStatus.CREATED )
	@ResponseBody
	public ResponseEntity<String> sendMail(@RequestParam String body,@RequestParam(value = "from") final String from,@RequestParam(value = "to") final String to, @RequestParam(value = "subject") final String subject,UriComponentsBuilder builder) {
		logger.debug("AuthenticationController.sendMail: mail request from: " + from+"to :"+to);
		String username=System.getenv("vcap.services.sendgrid.credentials.username");
		String password=System.getenv("vcap.services.sendgrid.credentials.password");
		SendGrid sendGrid = new SendGrid("TqEs6wy0mT","MaUiqBXQ1RUB9091");
		//SendGrid sendGrid = new SendGrid(username, password);
		SendGrid.Email email = new SendGrid.Email();
		
		email.addTo(to);
		email.setFrom(from);
		email.setSubject(subject);
		email.setText(body);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(builder.path("/sendMail").build().toUri());
		
		try {
			 SendGrid.Response response=sendGrid.send(email);
			 return new ResponseEntity<>(responseHeaders,HttpStatus.OK);
		     		     
		    } catch (SendGridException e) {
		    	return new ResponseEntity<>(responseHeaders,HttpStatus.EXPECTATION_FAILED);
		    }
		
		
		
	}
}
