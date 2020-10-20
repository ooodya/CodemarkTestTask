package com.zaytsev.codemarkTestTask.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserTests
{
	@Test
	public void constructorShouldSetFields()
	{
		String name = "Petr";
		String login = "First";
		String password = "123";
		
		User user = new User(name, login, password);
		
		assertEquals(name, user.getName());
		assertEquals(login, user.getLogin());
		assertEquals(password, user.getPassword());
	}
}
