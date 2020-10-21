package com.zaytsev.codemarkTestTask.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zaytsev.codemarkTestTask.domain.Role;
import com.zaytsev.codemarkTestTask.domain.User;
import com.zaytsev.codemarkTestTask.repository.RoleRepository;
import com.zaytsev.codemarkTestTask.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService
{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

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
	public Optional<User> findByLogin(String login)
	{
		return userRepository.findByLogin(login);
	}


	@Override
	public User save(User user)
	{
		User saved = userRepository.save(user);
		
		for (Role role: user.getRoles())
		{
			Optional<Role> persistedRole = roleRepository.findByName(role.getName());
			
			if (persistedRole.isEmpty())
			{
				roleRepository.save(role);
			}
		}
		
		return saved;
	}

	@Override
	public void delete(User user)
	{
		userRepository.delete(user);
	}

}
