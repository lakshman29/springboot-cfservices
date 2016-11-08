package com.def.accounts;

import com.def.accounts.controllers.AuthenticationController;
import com.def.accounts.domain.AuthenticationRequest;
import com.def.accounts.exceptions.AuthenticationException;
import com.def.accounts.services.AccountService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationControllerTest {

	private static String API_ROLE = "API_USER";
	
	MockMvc mockMvc;

	@InjectMocks
	AuthenticationController controller;
	
	@Mock
	AccountService service;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

	    this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	@Test
	public void doLoginGet()  throws Exception {
		mockMvc.perform(get("/login")).andExpect(status().isMethodNotAllowed());
	}
	
	@Test
	public void doLoginPost() throws Exception {
		when(service.login(ServiceTestConfiguration.USER_ID, ServiceTestConfiguration.PASSWORD)).thenReturn(ServiceTestConfiguration.loginResponse());
		
		AuthenticationRequest request = new AuthenticationRequest();
		request.setPassword(ServiceTestConfiguration.PASSWORD);
		request.setUsername(ServiceTestConfiguration.USER_ID);
		
		mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(convertObjectToJson(request)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.authToken").value(ServiceTestConfiguration.AUTH_TOKEN))
		.andExpect(jsonPath("$.accountid").value(ServiceTestConfiguration.PROFILE_ID.intValue()))
		.andDo(print());
	}
	@Test(expected=org.springframework.web.util.NestedServletException.class)
	public void doLoginPostBadPassword() throws Exception {
		when(service.login(ServiceTestConfiguration.USER_ID, ServiceTestConfiguration.BAD_PASSWORD)).thenThrow(new AuthenticationException("Login failed for user: "
					+ ServiceTestConfiguration.USER_ID));
		
		AuthenticationRequest request = new AuthenticationRequest();
		request.setPassword(ServiceTestConfiguration.BAD_PASSWORD);
		request.setUsername(ServiceTestConfiguration.USER_ID);
		
		mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(convertObjectToJson(request)))
		.andExpect(status().isCreated())
		.andDo(print());
	}
	
	@Test
	public void doLogoutPostNoUser()  throws Exception {
		mockMvc.perform(post("/logout"))
		.andExpect(status().isNotFound())
		.andDo(print());
	}
	@Test
	public void doLogoutGet()  throws Exception {
		mockMvc.perform(get("/logout/"+ServiceTestConfiguration.USER_ID))
		.andExpect(status().isOk())
		.andDo(print());
	}
	
	
	@Test
	public void doLogoutGetNoUser()  throws Exception {

		mockMvc.perform(get("/logout"))
		.andExpect(status().isNotFound())
		.andDo(print());
		
		//assertNull(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}
	
	private byte[] convertObjectToJson(AuthenticationRequest request) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        return mapper.writeValueAsBytes(request);
	}
	
	@Test
	
	public void doSendMailTest() throws Exception{
		mockMvc.perform(post("/sendMail").param("from", "lakshman29@gmail.com").
				param("to", "lakshman.rengarajan@altimetrik.com").param("subject", "testsendmail")
				.content("welcome to sendmail service"))
		//.andExpect(status().isOk())
		.andDo(print());
		
	}
	
}
