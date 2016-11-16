package com.def.accounts;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

public class NotificationControllerTest {
	MockMvc mockMvc;
	@Test
	public void testSendMail() throws Exception {
		mockMvc.perform(post("/sendMail").param("from", "lakshman29@gmail.com").
				param("to", "lakshman.rengarajan@altimetrik.com").param("subject", "testsendmail").param("body", "welcome to sendmail service"))				
		.andDo(print());
	}

}
