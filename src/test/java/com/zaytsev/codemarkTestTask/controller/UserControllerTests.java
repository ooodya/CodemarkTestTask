package com.zaytsev.codemarkTestTask.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.zaytsev.codemarkTestTask.configuration.TestConfiguration;
import com.zaytsev.codemarkTestTask.domain.User;
import com.zaytsev.codemarkTestTask.domain.UserList;
import com.zaytsev.codemarkTestTask.service.UserService;

@SpringBootTest(classes = TestConfiguration.class)
public class UserControllerTests
{
	private static final String URL_GET_ALL_USERS = "http://localhost:8080/user/getall";
	private static final String URL_GET_USER_BY_ID = "http://localhost:8080/user/{id}";
	private static final String URL_UPDATE_USER = "http://localhost:8080/user/{id}";
	private static final String URL_CREATE_USER = "http://localhost:8080/user/add";
	private static final String URL_DELETE_USER = "http://localhost:8080/user/{id}";
	
	@Autowired
	private RestTemplate restTemplate;
	
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
	public void testFindAll()
	{
		int userSize = 2;
		UserList userList = restTemplate.getForObject(URL_GET_ALL_USERS, UserList.class);
		
		assertEquals(userSize, userList.getUsers().size());
	}
	
	@Test
	public void testFindById()
	{
		User user = restTemplate.getForObject(URL_GET_USER_BY_ID, User.class, 1);
		
		assertNotNull(user);
	}
	
	@Test
	public void testUpdate()
	{
		User user = restTemplate.getForObject(URL_GET_USER_BY_ID, User.class, 1);
		String newName = "newName";
		user.setName(newName);
	
		restTemplate.put(URL_UPDATE_USER, user, 1);
		
		User updatedUser = restTemplate.getForObject(URL_GET_USER_BY_ID, User.class, 1);
		
		assertEquals(newName, updatedUser.getName());
	}
	
	@Test
	public void testCreate()
	{
		User user = new User("Alex", "al", "M670912");
		
		restTemplate.postForObject(URL_CREATE_USER, user, User.class);
		
		int userSize = 3;
		UserList userList = restTemplate.getForObject(URL_GET_ALL_USERS, UserList.class);
		
		assertEquals(userSize, userList.getUsers().size());
	}
	
	@Test
	public void testDelete()
	{
		restTemplate.delete(URL_DELETE_USER, 1);
		
		int userSize = 1;
		UserList userList = restTemplate.getForObject(URL_GET_ALL_USERS, UserList.class);
		
		assertEquals(userSize, userList.getUsers().size());
	}
}
