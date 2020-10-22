package com.zaytsev.codemarkTestTask.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.zaytsev.codemarkTestTask.domain.Role;
import com.zaytsev.codemarkTestTask.domain.RoleDTO;

public class RoleToFromDTOConversionServiceImplTests
{
	RoleToFromDTOConversionServiceImpl roleConvService = new RoleToFromDTOConversionServiceImpl();
	
	@Test
	@DisplayName("Role can be converted roleDTO")
	public void roleCanBeConvertedToRoleDTO()
	{
		Role role  = new Role("role1");
		
		RoleDTO roleDTO = roleConvService.convertToRoleDTO(role);
		
		assertEquals(role.getName(), roleDTO.getName());
	}
}
