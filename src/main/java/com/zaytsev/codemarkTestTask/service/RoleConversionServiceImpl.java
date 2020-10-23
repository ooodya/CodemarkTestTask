package com.zaytsev.codemarkTestTask.service;

import org.springframework.stereotype.Service;

import com.zaytsev.codemarkTestTask.domain.Role;
import com.zaytsev.codemarkTestTask.dto.RoleDTO;

@Service
public class RoleConversionServiceImpl implements RoleConversionService
{

	@Override
	public Role convertToRole(RoleDTO roleDTO)
	{
		if (roleDTO == null)
			return null;
		return new Role(roleDTO.getName());
	}

	@Override
	public RoleDTO convertToDTO(Role role)
	{
		if (role == null)
			return null;
		return new RoleDTO(role.getName());
	}

}
