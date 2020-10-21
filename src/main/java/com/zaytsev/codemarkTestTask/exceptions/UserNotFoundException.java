package com.zaytsev.codemarkTestTask.exceptions;

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
