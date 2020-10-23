package com.zaytsev.codemarkTestTask.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zaytsev.codemarkTestTask.domain.Role;
import com.zaytsev.codemarkTestTask.domain.User;
import com.zaytsev.codemarkTestTask.dto.UserDTO;
import com.zaytsev.codemarkTestTask.exceptions.UserAlreadyExists;
import com.zaytsev.codemarkTestTask.exceptions.UserNotFoundException;
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
	private UserConversionService convService;
		
	@Override
	@Transactional(readOnly = true)
	public List<UserDTO> findAll()
	{
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		
		List<UserDTO> userDTOs = convService.convertToListOfDTO(users);
		
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
	public void delete(UserDTO userDTO)
	{
		User user = convService.convertToUser(userDTO);
		
		userRepository.delete(user);
	}

	@Override
	public void update(UserDTO userDTO)
	{
		if (userRepository.findByLogin(userDTO.getLogin()).isEmpty())
		{
			throw new UserNotFoundException(userDTO.getLogin());
		}
		
		save(userDTO);
	}

	@Override
	public void add(UserDTO userDTO)
	{
		if (userRepository.findByLogin(userDTO.getLogin()).isPresent())
		{
			throw new UserAlreadyExists(userDTO.getLogin());
		}
		
		save(userDTO);
	}
	
	private void save(UserDTO userDTO)
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

}
