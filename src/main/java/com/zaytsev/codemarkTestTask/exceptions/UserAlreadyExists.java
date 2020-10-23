package com.zaytsev.codemarkTestTask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserAlreadyExists extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5098313874969677341L;
	
	public UserAlreadyExists(String login)
	{
		super("User with login " + login + " already exists");
	}

}
