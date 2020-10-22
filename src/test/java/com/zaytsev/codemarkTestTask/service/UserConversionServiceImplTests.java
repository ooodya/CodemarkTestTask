package com.zaytsev.codemarkTestTask.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.zaytsev.codemarkTestTask.domain.Role;
import com.zaytsev.codemarkTestTask.domain.User;
import com.zaytsev.codemarkTestTask.dto.RoleDTO;
import com.zaytsev.codemarkTestTask.dto.UserDTO;

public class UserConversionServiceImplTests
{
	private UserConversionServiceImpl userConvService = new UserConversionServiceImpl()
	{
		{
			this.setRoleConvService(new RoleConversionServiceImpl());
		}
	};

	@Test
	@DisplayName("User can be converted to UserDTO")
	public void userCanBeConvertedToUserDTO()
	{
		Set<Role> roles = new HashSet<>();
		roles.add(new Role("role1"));
		roles.add(new Role("role2"));

		User user = new User("name", "login", "password", roles);

		UserDTO userDTO = userConvService.convertToDTO(user);

		assertEquals(user.getName(), userDTO.getName());
		assertEquals(user.getLogin(), userDTO.getLogin());
		assertEquals(user.getPassword(), userDTO.getPassword());

		assertEquals(user.getRoles().size(), userDTO.getRoleDTOs().size());
	}

	@Test
	@DisplayName("UserDTO can be converted to User")
	public void userDTOCanBeConvertedToUser()
	{
		Set<RoleDTO> roleDTOs = new HashSet<>();
		roleDTOs.add(new RoleDTO("role1"));
		roleDTOs.add(new RoleDTO("role2"));

		UserDTO userDTO = new UserDTO("name", "login", "password", roleDTOs);

		User user = userConvService.convertToUser(userDTO);

		assertEquals(userDTO.getName(), user.getName());
		assertEquals(userDTO.getLogin(), user.getLogin());
		assertEquals(userDTO.getPassword(), user.getPassword());

		assertEquals(userDTO.getRoleDTOs().size(), user.getRoles().size());
	}
}
