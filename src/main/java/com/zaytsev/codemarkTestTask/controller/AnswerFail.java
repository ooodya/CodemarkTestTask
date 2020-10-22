package com.zaytsev.codemarkTestTask.controller;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerFail
{

	private final boolean success = false;
	
	private Set<String> errors = new HashSet<>();
	
}
