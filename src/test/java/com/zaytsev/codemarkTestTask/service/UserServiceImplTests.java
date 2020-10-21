package com.zaytsev.codemarkTestTask.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import com.zaytsev.codemarkTestTask.domain.User;
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
		User user1 = new User("name1", "login1", "password1");
		User user2 = new User("name2", "login2", "password2");
		
		userService.save(user1);
		userService.save(user2);
		
		int userSize = 2;
		List<User> users = userService.findAll();
		
		assertEquals(userSize, users.size());
		
		userService.delete(user1);
		userService.delete(user2);
	}
	
	@Test
    @DisplayName("User can be found")
    void canBeFoundAfterSaved() 
	{
        User user = new User("name", "login", "password");
        User saved = userService.save(user);
        User found = userService.findByLogin(saved.getLogin()).get();
        assertEquals("login", found.getLogin());
        assertEquals("name", found.getName());
        assertEquals("password", found.getPassword());
        
        userService.delete(saved);
    }
	
	@Test
    @DisplayName("User is saved with roles")
	void canBeSavedWithRoles()
	{
		Role role1 = new Role("pirat");
		Role role2 = new Role("MainUser");
		Set<Role> roles = new HashSet<>();
		roles.add(role1);
		roles.add(role2);
		
		User user = new User("name", "login", "password");
		user.setRoles(roles);
		userService.save(user);
		
		Role foundRole1 = roleRepository.findByName("pirat").get();
		Role foundRole2 = roleRepository.findByName("MainUser").get();
		
		assertNotNull(foundRole1.getId());
		assertNotNull(foundRole2.getId());
		
		userService.delete(user);
	}
	
	@Test
    @DisplayName("User can be deleted")
	void canBeDeleted()
	{
		User user = new User("name", "login", "password");
		User saved = userService.save(user);
		Optional<User> found = userService.findByLogin(saved.getLogin());
		
		assertFalse(found.isEmpty());
		
		userService.delete(user);
		
		Optional<User> foundAfterDelete = userService.findByLogin(saved.getLogin());
		
		assertTrue(foundAfterDelete.isEmpty());
	}
}
