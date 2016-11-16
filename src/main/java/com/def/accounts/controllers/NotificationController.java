package com.def.accounts.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

@RestController
public class NotificationController {
	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResponseEntity<String> sendMail(@RequestParam String body,
			@RequestParam(value = "from") final String from,
			@RequestParam(value = "to") final String to,
			@RequestParam(value = "subject") final String subject,
			UriComponentsBuilder builder) {
		logger.debug("AuthenticationController.sendMail: mail request from: "
				+ from + "to :" + to);
		String username = System
				.getenv("vcap.services.sendgrid.credentials.username");
		logger.info("username: " + username);

		String password = System
				.getenv("vcap.services.sendgrid.credentials.password");
		logger.info("password: " + password);
		SendGrid sendGrid = new SendGrid("TqEs6wy0mT", "MaUiqBXQ1RUB9091");
		// SendGrid sendGrid = new SendGrid(username, password);

		SendGrid.Email email = new SendGrid.Email();

		email.addTo(to);
		email.setFrom(from);
		email.setSubject(subject);
		email.setText(body);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(builder.path("/sendMail").build().toUri());

		try {
			SendGrid.Response response = sendGrid.send(email);
			return new ResponseEntity<>(responseHeaders, HttpStatus.OK);

		} catch (SendGridException e) {
			return new ResponseEntity<>(responseHeaders,
					HttpStatus.EXPECTATION_FAILED);
		}

	}
}
