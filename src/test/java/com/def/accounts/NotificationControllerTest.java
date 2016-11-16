package com.def.accounts;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.def.accounts.controllers.AuthenticationController;
import com.sendgrid.SendGrid;

public class NotificationControllerTest {
	MockMvc mockMvc;
	@InjectMocks
	AuthenticationController controller;
	@Mock
	SendGrid sendGrid;
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

	    this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	@Test
	public void testSendMail() throws Exception {
		mockMvc.perform(post("/sendMail").param("from", "lakshman29@gmail.com").
				param("to", "lakshman.rengarajan@altimetrik.com").param("subject", "testsendmail").param("body", "welcome to sendmail service"))				
		.andDo(print());
	}

}
