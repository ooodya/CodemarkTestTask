package com.zaytsev.codemarkTestTask.service;

import com.zaytsev.codemarkTestTask.domain.Role;
import com.zaytsev.codemarkTestTask.dto.RoleDTO;

public interface RoleConversionService
{
	Role convertToRole(RoleDTO roleDTO);
	
	RoleDTO convertToRoleDTO(Role role);
}
