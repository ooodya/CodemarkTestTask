package com.zaytsev.codemarkTestTask.service;

import com.zaytsev.codemarkTestTask.domain.User;
import com.zaytsev.codemarkTestTask.domain.UserDTO;

public interface UserToFromDTOConversionService
{
	User convertToUser(UserDTO userDTO);
	
	UserDTO convertToDTO(User user);
}
