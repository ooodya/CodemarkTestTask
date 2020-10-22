package com.zaytsev.codemarkTestTask.service;

import com.zaytsev.codemarkTestTask.domain.Role;
import com.zaytsev.codemarkTestTask.domain.RoleDTO;

public interface RoleToFromDTOConversionService
{
	Role convertToRole(RoleDTO roleDTO);
	
	RoleDTO convertToRoleDTO(Role role);
}
