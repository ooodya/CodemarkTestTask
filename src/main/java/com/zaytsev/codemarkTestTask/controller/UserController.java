package com.zaytsev.codemarkTestTask.controller;

import java.util.List;
import java.util.Optional;

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
	@GetMapping("/{id}")
	public User findUserById(@PathVariable Long id)
	{
		return userService.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/add")
	public User create(@RequestBody User user)
	{
		return userService.save(user);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{id}")
	public void update(@RequestBody User user, @PathVariable Long id)
	{
		userService.save(user);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id)
	{
		Optional<User> user = userService.findById(id);
		user.ifPresent(userService::delete);
	}
}
