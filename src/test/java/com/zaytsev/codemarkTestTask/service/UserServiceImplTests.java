package com.zaytsev.codemarkTestTask.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zaytsev.codemarkTestTask.domain.Role;
import com.zaytsev.codemarkTestTask.dto.RoleDTO;
import com.zaytsev.codemarkTestTask.dto.UserDTO;
import com.zaytsev.codemarkTestTask.exceptions.UserAlreadyExists;
import com.zaytsev.codemarkTestTask.repository.RoleRepository;

@SpringBootTest
@Transactional
public class UserServiceImplTests
{
	@Autowired
	private UserService userService;

	@Autowired
	private RoleRepository roleRepository;

	@Test
	@DisplayName("All users can be found")
	public void findAllShouldReturnAllUsers()
	{
		UserDTO userDTO1 = new UserDTO("name1", "login1", "password1", new HashSet<RoleDTO>());
		UserDTO userDTO2 = new UserDTO("name2", "login2", "password2", new HashSet<RoleDTO>());

		userService.add(userDTO1);
		userService.add(userDTO2);

		int userSize = 2;
		List<UserDTO> usersDTOs = userService.findAll();

		assertEquals(userSize, usersDTOs.size());

		userService.delete(userDTO1);
		userService.delete(userDTO2);
	}

	@Test()
	@DisplayName("User with existing login is not saved")
	public void UserWithExistingLoginIsNotSaved()
	{
		UserDTO userDTO1 = new UserDTO("name3", "login3", "password3", new HashSet<RoleDTO>());
		userService.add(userDTO1);

		assertThrows(UserAlreadyExists.class, () ->
		{
			userService.add(new UserDTO("name4", "login3", "password4", new HashSet<RoleDTO>()));
		});

		userService.delete(userDTO1);
	}

	@Test
	@DisplayName("findAll returns users without roles")
	public void findAllShouldReturnAllUsersWithoutRoles()
	{
		Set<RoleDTO> roles = new HashSet<>();
		roles.add(new RoleDTO("role1"));
		roles.add(new RoleDTO("role2"));

		UserDTO userDTO1 = new UserDTO("name5", "login5", "password5", roles);

		userService.add(userDTO1);

		List<UserDTO> usersDTOs = userService.findAll();

		assertEquals(0, usersDTOs.get(0).getRoleDTOs().size());

		userService.delete(userDTO1);
	}

	@Test
	@DisplayName("User can be found")
	void canBeFoundAfterSaved()
	{
		UserDTO userDTO = new UserDTO("name6", "login6", "password6", new HashSet<RoleDTO>());
		userService.add(userDTO);

		UserDTO found = userService.findByLogin(userDTO.getLogin()).get();
		assertEquals("login6", found.getLogin());
		assertEquals("name6", found.getName());
		assertEquals("password6", found.getPassword());

		userService.delete(userDTO);
	}

	@Test
	@DisplayName("User is saved with roles")
	void canBeSavedWithRoles()
	{
		RoleDTO roleDTO1 = new RoleDTO("pirat");
		RoleDTO roleDTO2 = new RoleDTO("MainUser");
		Set<RoleDTO> roleDTOs = new HashSet<>();
		roleDTOs.add(roleDTO1);
		roleDTOs.add(roleDTO2);

		UserDTO userDTO = new UserDTO("name7", "login7", "password7", roleDTOs);
		userService.add(userDTO);

		Role foundRole1 = roleRepository.findByName("pirat").get();
		Role foundRole2 = roleRepository.findByName("MainUser").get();

		assertNotNull(foundRole1.getId());
		assertNotNull(foundRole2.getId());

		userService.delete(userDTO);
	}

	@Test
	@DisplayName("User can be updated with roles")
	void canBeUpdated()
	{
		RoleDTO roleDTO1 = new RoleDTO("pirat");
		RoleDTO roleDTO2 = new RoleDTO("MainUser");
		Set<RoleDTO> roleDTOs = new HashSet<>();
		roleDTOs.add(roleDTO1);
		roleDTOs.add(roleDTO2);
		UserDTO userDTO = new UserDTO("name8", "login8", "password8", roleDTOs);
		userService.add(userDTO);

		RoleDTO roleDTO3 = new RoleDTO("newRole");
		RoleDTO roleDTO4 = new RoleDTO("newRoleToo");
		Set<RoleDTO> newRoles = new HashSet<>();
		newRoles.add(roleDTO3);
		newRoles.add(roleDTO4);
		UserDTO userDtoForUpdate = new UserDTO("newName", "login", "newPassword", newRoles);
		userService.add(userDtoForUpdate);

		final Optional<UserDTO> updatedOpt = userService.findByLogin("login");
		assertTrue(updatedOpt.isPresent());
		UserDTO updated = updatedOpt.get();
		assertEquals("login", updated.getLogin());
		assertEquals("newName", updated.getName());
		assertEquals("newPassword", updated.getPassword());
		final Set<String> roleNames = updated.getRoleDTOs().stream().map(role -> role.getName())
				.collect(Collectors.toSet());

		assertTrue(roleNames.contains(roleDTO3.getName()));
		assertTrue(roleNames.contains(roleDTO4.getName()));

		assertFalse(roleNames.contains(roleDTO1.getName()));
		assertFalse(roleNames.contains(roleDTO2.getName()));

		userService.delete(updated);
	}

	@Test
	@DisplayName("User is found with roles")
	void canBeFoundWithRoles()
	{
		RoleDTO roleDTO1 = new RoleDTO("pirat");
		RoleDTO roleDTO2 = new RoleDTO("MainUser");
		Set<RoleDTO> roleDTOs = new HashSet<>();
		roleDTOs.add(roleDTO1);
		roleDTOs.add(roleDTO2);

		UserDTO userDTO = new UserDTO("name9", "login9", "password9", roleDTOs);

		userService.add(userDTO);

		UserDTO found = userService.findByLogin(userDTO.getLogin()).get();

		assertNotEquals(0, found.getRoleDTOs().size());
		assertTrue(found.getRoleDTOs().contains(roleDTO1));
		assertTrue(found.getRoleDTOs().contains(roleDTO2));

		userService.delete(userDTO);
	}

	@Test
	@DisplayName("User can be deleted")
	void canBeDeleted()
	{
		UserDTO userDTO = new UserDTO("name10", "login10", "password10", new HashSet<RoleDTO>());
		userService.add(userDTO);

		Optional<UserDTO> found = userService.findByLogin(userDTO.getLogin());
		assertFalse(found.isEmpty());

		userService.delete(userDTO);

		Optional<UserDTO> foundAfterDelete = userService.findByLogin(userDTO.getLogin());
		assertTrue(foundAfterDelete.isEmpty());
	}
}
