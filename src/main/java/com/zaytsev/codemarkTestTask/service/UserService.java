package com.zaytsev.codemarkTestTask.service;

import java.util.List;
import java.util.Optional;

import com.zaytsev.codemarkTestTask.domain.User;

public interface UserService
{
	List<User> findAll();
	User save(User user);
	void delete(User user);
	Optional<User> findByLogin(String login);
}
