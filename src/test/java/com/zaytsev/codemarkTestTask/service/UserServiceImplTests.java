package com.zaytsev.codemarkTestTask.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zaytsev.codemarkTestTask.domain.Role;
import com.zaytsev.codemarkTestTask.dto.RoleDTO;
import com.zaytsev.codemarkTestTask.dto.UserDTO;
import com.zaytsev.codemarkTestTask.repository.RoleRepository;

@SpringBootTest
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
		
		userService.save(userDTO1);
		userService.save(userDTO2);
		
		int userSize = 2;
		List<UserDTO> usersDTOs = userService.findAll();
		
		assertEquals(userSize, usersDTOs.size());
		
		userService.delete(userDTO1);
		userService.delete(userDTO2);
	}
	
	@Test
	@DisplayName("findAll returns users without roles")
	public void findAllShouldReturnAllUsersWithoutRoles()
	{
		Set<RoleDTO> roles = new HashSet<>();
		roles.add(new RoleDTO("role1"));
		roles.add(new RoleDTO("role2"));
		
		UserDTO userDTO1 = new UserDTO("name1", "login1", "password1", roles);
		
		userService.save(userDTO1);
		
		List<UserDTO> usersDTOs = userService.findAll();
		
		assertEquals(0, usersDTOs.get(0).getRoleDTOs().size());
		
		userService.delete(userDTO1);
	}
	
	@Test
    @DisplayName("User can be found")
    void canBeFoundAfterSaved() 
	{
		UserDTO userDTO = new UserDTO("name", "login", "password", new HashSet<RoleDTO>());
        userService.save(userDTO);
        
        UserDTO found = userService.findByLogin(userDTO.getLogin()).get();
        assertEquals("login", found.getLogin());
        assertEquals("name", found.getName());
        assertEquals("password", found.getPassword());
        
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
		
		UserDTO userDTO = new UserDTO("name", "login", "password", roleDTOs);
		userService.save(userDTO);
		
		Role foundRole1 = roleRepository.findByName("pirat").get();
		Role foundRole2 = roleRepository.findByName("MainUser").get();	
		
		assertNotNull(foundRole1.getId());
		assertNotNull(foundRole2.getId());
		
		userService.delete(userDTO);
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
		
		UserDTO userDTO = new UserDTO("name", "login", "password", roleDTOs);
		
		userService.save(userDTO);
		
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
		UserDTO userDTO = new UserDTO("name", "login", "password", new HashSet<RoleDTO>());
		userService.save(userDTO);
		
		Optional<UserDTO> found = userService.findByLogin(userDTO.getLogin());		
		assertFalse(found.isEmpty());
		
		userService.delete(userDTO);
		
		Optional<UserDTO> foundAfterDelete = userService.findByLogin(userDTO.getLogin());		
		assertTrue(foundAfterDelete.isEmpty());
	}
}
