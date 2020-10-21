package com.zaytsev.codemarkTestTask.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zaytsev.codemarkTestTask.domain.User;
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
	public List<User> findAll()
	{
		return userService.findAll();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{login}")
	public User findUserByLogin(@PathVariable String login)
	{
		return userService.findByLogin(login).orElseThrow(() -> new UserNotFoundException(login));
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/add")
	public Answer create(@RequestBody User user)
	{
		userService.save(user);
		return new Answer(true);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{login}")
	public Answer update(@RequestBody User user, @PathVariable String login)
	{
		userService.save(user);
		
		Set<String> errors = new HashSet<>();
		errors.add("fail1");
		errors.add("Vse ploho");
		
		return new Answer(false, errors);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{login}")
	public void delete(@PathVariable String login)
	{
		Optional<User> user = userService.findByLogin(login);
		user.ifPresent(userService::delete);
	}
}
