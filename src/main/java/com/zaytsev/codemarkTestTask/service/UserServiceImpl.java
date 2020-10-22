package com.zaytsev.codemarkTestTask.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zaytsev.codemarkTestTask.domain.Role;
import com.zaytsev.codemarkTestTask.domain.User;
import com.zaytsev.codemarkTestTask.domain.UserDTO;
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
	
	@Autowired
	private UserToFromDTOConversionService convService;
		
	@Override
	@Transactional(readOnly = true)
	public List<UserDTO> findAll()
	{
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		
		List<UserDTO> userDTOs = users.stream().map(convService::convertToDTO).collect(Collectors.toList());
		
		return userDTOs;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<UserDTO> findByLogin(String login)
	{
		Optional<User> user = userRepository.findByLogin(login);
		if (!user.isEmpty())
		{
			user.get().getRoles();
		}
		
		Optional<UserDTO> userDTO = user.map(convService::convertToDTO);
		
		return userDTO;
	}

	@Override
	public void save(UserDTO userDTO)
	{
		User user = convService.convertToUser(userDTO);
		
		for (Role role: user.getRoles())
		{
			Optional<Role> persistedRole = roleRepository.findByName(role.getName());
			
			if (persistedRole.isEmpty())
			{
				roleRepository.save(role);
			}
		}
		
		userRepository.save(user);
	}

	@Override
	public void delete(UserDTO userDTO)
	{
		User user = convService.convertToUser(userDTO);
		
		userRepository.delete(user);
	}

}
