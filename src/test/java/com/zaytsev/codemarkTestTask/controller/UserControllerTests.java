package com.zaytsev.codemarkTestTask.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaytsev.codemarkTestTask.domain.User;
import com.zaytsev.codemarkTestTask.service.UserService;

import lombok.SneakyThrows;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests
{
	private static final String URL_GET_ALL_USERS = "http://localhost:8080/user/getall";
	private static final String URL_ADD_USER = "http://localhost:8080/user/add";

	@Autowired
	private MockMvc mvc;

	@Autowired
	private UserService userService;

	private String name1 = "Gleb";
	private String login1 = "Zabor";
	private String password1 = "123";

	private String name2 = "Julia";
	private String login2 = "MelkiyMultic";
	private String password2 = "456";

	private User user1 = new User(name1, login1, password1);
	private User user2 = new User(name2, login2, password2);

	@BeforeEach
	public void setUp()
	{
		userService.save(user1);
		userService.save(user2);
	}

	@AfterEach
	public void cleanUp()
	{
		userService.delete(user1);
		userService.delete(user2);
	}

	@Test
	@DisplayName("Find all should return all users")
	@SneakyThrows
	public void testFindAll()
	{
		final MvcResult mvcResult = mvc.perform(get(URL_GET_ALL_USERS)).andExpect(status().isOk()).andReturn();
		final List<User> users = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(),
				new TypeReference<List<User>>()
				{
				});
		assertEquals(user1, users.get(0));
		assertEquals(user2, users.get(1));
	}
	
	@Test
	@DisplayName("Saving valid user should return boolean true in json")
	@SneakyThrows
	public void savingValidUserShouldReturnAnswerOk()
	{
		User user = new User("name", "login", "password");
		//mvc.perform(post(URL_ADD_USER, user)).andExpect(jsonPath("$[0].validationResult", is(true)));
		
		final MvcResult mvcResult = mvc.perform(post(URL_ADD_USER, user)).andReturn();
		final Answer answer = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Answer.class);
		assertEquals(true, answer.isValidationResult());
	}

}
