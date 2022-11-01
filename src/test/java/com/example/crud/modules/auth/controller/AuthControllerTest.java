package com.example.crud.modules.auth.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.crud.modules.auth.dto.ActiveUserDto;
import com.example.crud.modules.auth.dto.ForgotDto;
import com.example.crud.modules.auth.dto.ReqLoginDto;
import com.example.crud.modules.auth.dto.ResetPasswordDto;
import com.example.crud.modules.auth.dto.UserDto;
import com.example.crud.modules.auth.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AuthController.class})
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
	@Autowired
	private AuthController authController;

	@MockBean
	private AuthService authService;

	/**
	 * Method under test: {@link AuthController#activeUser(ActiveUserDto)}
	 */
	@Test
	void testActiveUser() throws Exception {
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/active-account")
				.contentType(MediaType.APPLICATION_JSON);

		ObjectMapper objectMapper = new ObjectMapper();
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content(objectMapper.writeValueAsString(new ActiveUserDto("jane.doe@example.org", "Pin")));
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController).build().perform(requestBuilder);
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	/**
	 * Method under test: {@link AuthController#forgot(ForgotDto)}
	 */
	@Test
	void testForgot() throws Exception {
		ForgotDto forgotDto = new ForgotDto();
		forgotDto.setEmail("jane.doe@example.org");
		String content = (new ObjectMapper()).writeValueAsString(forgotDto);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/forgot")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
				.build()
				.perform(requestBuilder);
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	/**
	 * Method under test: {@link AuthController#forgot(ResetPasswordDto)}
	 */
	@Test
	void testForgot2() throws Exception {
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.patch("/reset-password")
				.contentType(MediaType.APPLICATION_JSON);

		ObjectMapper objectMapper = new ObjectMapper();
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content(objectMapper.writeValueAsString(new ResetPasswordDto("Pin", "iloveyou")));
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
				.build()
				.perform(requestBuilder);
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	/**
	 * Method under test: {@link AuthController#getAllUsers(UserDto)}
	 */
	@Test
	void testGetAllUsers() throws Exception {
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/register")
				.contentType(MediaType.APPLICATION_JSON);

		ObjectMapper objectMapper = new ObjectMapper();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content(objectMapper.writeValueAsString(new UserDto("jane.doe@example.org", "iloveyou",
						Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()), new Long[]{1L})));
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
				.build()
				.perform(requestBuilder);
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	/**
	 * Method under test: {@link AuthController#getAllUsers(UserDto)}
	 */
	@Test
	void testGetAllUsers2() throws Exception {
		java.sql.Date date = mock(java.sql.Date.class);
		when(date.getTime()).thenReturn(10L);
		UserDto value = new UserDto("jane.doe@example.org", "iloveyou", date, new Long[]{1L});

		String content = (new ObjectMapper()).writeValueAsString(value);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
				.build()
				.perform(requestBuilder);
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	/**
	 * Method under test: {@link AuthController#login(ReqLoginDto)}
	 */
	@Test
	void testLogin() throws Exception {
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON);

		ObjectMapper objectMapper = new ObjectMapper();
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content(objectMapper.writeValueAsString(new ReqLoginDto("jane.doe@example.org", "iloveyou")));
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
				.build()
				.perform(requestBuilder);
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}

