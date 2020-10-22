package com.zaytsev.codemarkTestTask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1031993738196224561L;

	public UserNotFoundException(String login)
	{
		super("Could not find user " + login);
	}
}
