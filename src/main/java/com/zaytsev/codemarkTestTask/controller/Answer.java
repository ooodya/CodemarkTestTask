package com.zaytsev.codemarkTestTask.controller;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Answer
{
	private final boolean validationResult;
	
	private Set<String> errors = new HashSet<>();
	
	public Answer(boolean validationResult)
	{
		this.validationResult = validationResult;
	}
}
