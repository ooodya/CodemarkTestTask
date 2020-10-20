package com.zaytsev.codemarkTestTask.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zaytsev.codemarkTestTask.domain.User;

@SpringBootTest
public class UserServiceImplTests
{
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
	public void init()
	{
		userService.save(user1);
		userService.save(user2);
	}
	
	@AfterEach
	public void delete()
	{
		userService.delete(user1);
		userService.delete(user2);
	}
	
	@Test
	public void findAllShouldReturnAllUsers()
	{
		int userSize = 2;
		List<User> users = userService.findAll();
		
		assertEquals(userSize, users.size());
	}
}
