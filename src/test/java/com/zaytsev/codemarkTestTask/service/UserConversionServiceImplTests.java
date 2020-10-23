package com.zaytsev.codemarkTestTask.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
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
	
	@Test
	@DisplayName("null UserDTO return null)")
	public void nullUserDTOIsConvertedToNull()
	{
		User user = userConvService.convertToUser(null);
		
		assertEquals(null, user);
	}
	
	@Test
	@DisplayName("null User return null)")
	public void nullUserIsConvertedToNull()
	{
		UserDTO userDTO = userConvService.convertToDTO(null);
		
		assertEquals(null, userDTO);
	}
	
	@Test
	@DisplayName("null List of users return empty DTO list)")
	public void nullUserListReturnsEmptyUserDTOList()
	{
		List<UserDTO> userDTOs = userConvService.convertToListOfDTO(null);
		
		assertEquals(0, userDTOs.size());
	}
	
	@Test
	@DisplayName("correct List of users can be converted to list of DTOs)")
	public void userListCanBeConvertedToUserDTOList()
	{
		Set<Role> roles = new HashSet<>();
		roles.add(new Role("role1"));
		roles.add(new Role("role2"));

		User user1 = new User("name1", "login1", "password1", roles);
		User user2 = new User("name2", "login2", "password2", roles);
		
		List<UserDTO> userDTOs = userConvService.convertToListOfDTO(List.of(user1, user2));
		
		assertEquals(2, userDTOs.size());
		
		assertEquals("name1", userDTOs.get(0).getName());
		assertEquals("login1", userDTOs.get(0).getLogin());
		assertEquals("password1", userDTOs.get(0).getPassword());
		assertEquals(0, userDTOs.get(0).getRoleDTOs().size());
		
		assertEquals("name2", userDTOs.get(1).getName());
		assertEquals("login2", userDTOs.get(1).getLogin());
		assertEquals("password2", userDTOs.get(1).getPassword());
		assertEquals(0, userDTOs.get(1).getRoleDTOs().size());
	}
}
