package com.zaytsev.codemarkTestTask.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zaytsev.codemarkTestTask.domain.Role;
import com.zaytsev.codemarkTestTask.domain.User;
import com.zaytsev.codemarkTestTask.dto.RoleDTO;
import com.zaytsev.codemarkTestTask.dto.UserDTO;

import lombok.Data;

@Service
@Data
public class UserConversionServiceImpl implements UserConversionService
{
	@Autowired
	private RoleConversionService roleConvService;
	
	@Override
	public User convertToUser(UserDTO userDTO)
	{
		if (userDTO == null)
			return null;
		
		Set<Role> roles = userDTO.getRoleDTOs().stream().map(roleConvService::convertToRole).collect(Collectors.toSet());
		
		User user = new User(userDTO.getName(), userDTO.getLogin(), userDTO.getPassword(), roles);
		
		return user;
	}

	@Override
	public UserDTO convertToDTO(User user)
	{
		if (user == null)
			return null;
		
		Set<RoleDTO> roleDTOs = user.getRoles().stream().map(roleConvService::convertToRoleDTO).collect(Collectors.toSet());
		
		UserDTO userDTO = new UserDTO(user.getName(), user.getLogin(), user.getPassword(), roleDTOs);
		
		return userDTO;
	}

	@Override
	public List<UserDTO> convertToListOfDTO(List<User> users)
	{
		if (users == null)
		{
			return new ArrayList<UserDTO>();
		}
		
		List<UserDTO> userDTOs = new ArrayList<>();
		for (User user : users)
		{
			userDTOs.add(new UserDTO(user.getName(), user.getLogin(), user.getPassword(), new HashSet<>()));
		}
		return userDTOs;
	}

}
