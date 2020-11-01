package com.zaytsev.codemarkTestTask.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaytsev.codemarkTestTask.dto.RoleDTO;
import com.zaytsev.codemarkTestTask.dto.UserDTO;
import com.zaytsev.codemarkTestTask.service.UserService;

import lombok.SneakyThrows;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("login")
public class UserControllerTests
{
	private static final String URL_GET_ALL_USERS = "http://localhost:8080/user/getall";
	private static final String URL_ADD_USER = "http://localhost:8080/user/add";
	private static final String URL_UPDATE_USER = "http://localhost:8080/user/update";
	private static final String URL_GET_USER_BY_LOGIN = "http://localhost:8080/user/{login}";

	@Autowired
	private MockMvc mvc;

	@Autowired
	private UserService userService;

	@Autowired
	private MessageSource messageSource;

	private final String name1 = "Chelovek";
	private final String login1 = "NeChelovek";
	private final String password1 = "Pass1";

	private final String name2 = "Julia";
	private final String login2 = "MelkiyMultic";
	private final String password2 = "Pass2";

	private final UserDTO userDTO1 = new UserDTO(name1, login1, password1, new HashSet<>());
	private final UserDTO userDTO2 = new UserDTO(name2, login2, password2, new HashSet<>());

	@BeforeEach
	public void setUp()
	{
		userService.add(userDTO1);
		userService.add(userDTO2);
	}

	@AfterEach
	public void cleanUp()
	{
		userService.delete(userDTO1);
		userService.delete(userDTO2);
	}

	@Test
	@DisplayName("Find all should return all users")
	@SneakyThrows
	public void testFindAll()
	{
		final MvcResult mvcResult = mvc.perform(get(URL_GET_ALL_USERS)).andExpect(status().isOk()).andReturn();
		final List<UserDTO> usersDTOs = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>()
		{
		});
		assertEquals(userDTO1, usersDTOs.get(0));
		assertEquals(userDTO2, usersDTOs.get(1));
	}

	@Test
	@DisplayName("Finding excisting user should return correct user")
	@SneakyThrows
	public void canFindUser()
	{
		final MvcResult mvcResult = mvc.perform(get(URL_GET_USER_BY_LOGIN, login1)).andExpect(status().isOk())
				.andReturn();
		final UserDTO userDTO = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(),
				UserDTO.class);

		assertEquals(userDTO1, userDTO);
	}

	@Test
	@DisplayName("Finding user with incorrect login should return 404")
	@SneakyThrows
	public void cantFindUserWithInvalidLogin()
	{
		final String invalidLogin = "adsasdqwewq123123cvbcbcvbcvb";

		mvc.perform(get(URL_GET_USER_BY_LOGIN, invalidLogin)).andExpect(status().isNotFound())
				.andExpect(content().bytes(new byte[0]));
	}

	@Test
	@DisplayName("Saving valid user should return boolean true in json")
	@SneakyThrows
	public void savingValidUserShouldReturnAnswerOk()
	{
		UserDTO userDTO = new UserDTO("name", "login", "Password1", new HashSet<>());

		final MvcResult mvcResult = mvc
				.perform(post(URL_ADD_USER).contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(userDTO)))
				.andExpect(status().isCreated()).andReturn();
		final AnswerOk answer = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(),
				AnswerOk.class);
		assertTrue(answer.isSuccess());

		userService.delete(userDTO);
	}

	@Test
	@DisplayName("Updating user should return boolean true in json")
	@SneakyThrows
	public void updatingValidUserShouldReturnAnswerOk()
	{
		Set<RoleDTO> roleDTOs = new HashSet<>();
		roleDTOs.add(new RoleDTO("role1"));
		roleDTOs.add(new RoleDTO("role2"));
		userDTO1.setRoleDTOs(roleDTOs);

		final MvcResult mvcResult = mvc
				.perform(put(URL_UPDATE_USER).contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(userDTO1)))
				.andExpect(status().isOk()).andReturn();
		final AnswerOk answer = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(),
				AnswerOk.class);
		assertTrue(answer.isSuccess());
	}

	@Test
	@DisplayName("Updating not existing user should return 404")
	@SneakyThrows
	public void updatingUserWithInvalidLoginShouldReturn404()
	{
		UserDTO userDTO = new UserDTO("name", "login", "Password1", new HashSet<>());

		mvc.perform(put(URL_UPDATE_USER).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(userDTO))).andExpect(status().isNotFound())
				.andExpect(content().bytes(new byte[0]));
	}

	@Test
	@DisplayName("Saving user without name should return correct error message")
	@SneakyThrows
	public void savingUserWithoutNameLoginPasswordShouldReturnCorrectErrorMessages()
	{
		UserDTO userDTO = new UserDTO("", "", "", new HashSet<>());

		final MvcResult mvcResult = mvc
				.perform(post(URL_ADD_USER).contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(userDTO)))
				.andExpect(status().isBadRequest()).andReturn();
		final AnswerFail answer = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(),
				AnswerFail.class);

		assertTrue(answer.getErrors().contains(messageSource.getMessage("validation.user.name.empty", null, null)));
		assertTrue(answer.getErrors().contains(messageSource.getMessage("validation.user.login.empty", null, null)));
		assertTrue(answer.getErrors().contains(messageSource.getMessage("validation.user.password.empty", null, null)));
	}

	@Test
	@DisplayName("Saving user with invalid password should return correct error message")
	@SneakyThrows
	public void savingUserWithInvalidPasswordShouldReturnCorrectErrorMessage()
	{
		UserDTO userDTO = new UserDTO("name", "login", "asdqwe", new HashSet<>());

		final MvcResult mvcResult = mvc
				.perform(post(URL_ADD_USER).contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(userDTO)))
				.andExpect(status().isBadRequest()).andReturn();
		final AnswerFail answer = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(),
				AnswerFail.class);

		assertTrue(answer.getErrors()
				.contains(messageSource.getMessage("validation.user.password.constraints", null, null)));
	}

	@Test
	@DisplayName("Saving user with existing login should return 409")
	@SneakyThrows
	public void savingUserWithExistingLoginShouldReturn409()
	{
		mvc.perform(post(URL_ADD_USER).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(userDTO1))).andExpect(status().isConflict())
				.andExpect(content().bytes(new byte[0]));
	}

}
