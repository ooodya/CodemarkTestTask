package com.zaytsev.codemarkTestTask.service;

import org.springframework.stereotype.Service;

import com.zaytsev.codemarkTestTask.domain.Role;
import com.zaytsev.codemarkTestTask.domain.RoleDTO;

@Service
public class RoleToFromDTOConversionServiceImpl implements RoleToFromDTOConversionService
{

	@Override
	public Role convertToRole(RoleDTO roleDTO)
	{
		return new Role(roleDTO.getName());
	}

	@Override
	public RoleDTO convertToRoleDTO(Role role)
	{
		return new RoleDTO(role.getName());
	}

}
