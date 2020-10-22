package com.zaytsev.codemarkTestTask.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zaytsev.codemarkTestTask.dto.UserDTO;
import com.zaytsev.codemarkTestTask.exceptions.UserNotFoundException;
import com.zaytsev.codemarkTestTask.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController
{
	@Autowired
	private UserService userService;

	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/getall")
	public List<UserDTO> findAll()
	{
		return userService.findAll();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{login}")
	public UserDTO findUserByLogin(@PathVariable String login)
	{
		return userService.findByLogin(login).orElseThrow(() -> new UserNotFoundException(login));
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/add")
	public AnswerOk create(@Valid @RequestBody UserDTO userDTO)
	{
		userService.save(userDTO);
		return new AnswerOk();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/update")
	public AnswerOk update(@Valid @RequestBody UserDTO userDTO)
	{
		userService.save(userDTO);		
		
		return new AnswerOk();
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{login}")
	public void delete(@PathVariable String login)
	{
		Optional<UserDTO> userDTO = userService.findByLogin(login);
		userDTO.ifPresent(userService::delete);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public AnswerFail handleConstraintValidationExceptions(MethodArgumentNotValidException ex)
	{
		Set<String> errors = ex.getBindingResult().getAllErrors().stream()
				.map((error) -> error.getDefaultMessage())
					.collect(Collectors.toSet());
		
		return new AnswerFail(errors) ;
	}
	
}
