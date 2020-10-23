package com.zaytsev.codemarkTestTask.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.zaytsev.codemarkTestTask.domain.Role;
import com.zaytsev.codemarkTestTask.dto.RoleDTO;

public class RoleConversionServiceImplTests
{
	RoleConversionServiceImpl roleConvService = new RoleConversionServiceImpl();
	
	@Test
	@DisplayName("Role can be converted roleDTO")
	public void roleCanBeConvertedToRoleDTO()
	{
		Role role  = new Role("role1");
		
		RoleDTO roleDTO = roleConvService.convertToDTO(role);
		
		assertEquals(role.getName(), roleDTO.getName());
	}
	
	@Test
	@DisplayName("roleDTO can be converted role")
	public void roleDTOCanBeConvertedToRole()
	{
		RoleDTO roleDTO  = new RoleDTO("role1");
		
		Role role = roleConvService.convertToRole(roleDTO);
		
		assertEquals(roleDTO.getName(), role.getName());
	}
	
	@Test
	@DisplayName("null RoleDTO returns null")
	public void nullRoleDTOIsConvertedNull()
	{
		Role role = roleConvService.convertToRole(null);
		
		assertEquals(null, role);
	}
	
	
	@Test
	@DisplayName("null Role returns null")
	public void nullRoleIsConvertedNull()
	{
		RoleDTO roleDTO = roleConvService.convertToDTO(null);
		
		assertEquals(null, roleDTO);
	}
	
}
