package com.zaytsev.codemarkTestTask.exceptions;

public class UserNotFoundException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1031993738196224561L;

	public UserNotFoundException(Long id)
	{
		super("Could not find user " + id);
	}
}
