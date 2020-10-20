package com.zaytsev.codemarkTestTask.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zaytsev.codemarkTestTask.domain.User;
import com.zaytsev.codemarkTestTask.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService
{

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll()
	{
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findById(Long id)
	{
		return userRepository.findById(id);
	}

	@Override
	public User save(User user)
	{
		return userRepository.save(user);
	}

	@Override
	public void delete(User user)
	{
		userRepository.delete(user);
	}

}
